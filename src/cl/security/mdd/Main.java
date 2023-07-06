package cl.security.mdd;

import static cl.security.utils.LoaderUtil.getInstatiatedStatusClasses;

import java.util.HashMap;
import java.util.Map;

import cl.security.observer.listeners.DealStatusListener;
import cl.security.observer.listeners.ListenerManager;
import cl.security.status.strategy.StatusStrategy;
import cl.security.status.strategy.deal.DealStatus;

public class Main {

	public static Map<String, StatusStrategy> status = new HashMap<String, StatusStrategy>();

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		StatusStrategy strategy;

		ListenerManager listener = new ListenerManager();
		
		DealStatusListener dealListener = new DealStatusListener();
		
		// Patron de diseño Observer. Esto es para que la logica de revision de data en la tabla esté en un publisher
		// Se suscribe a un evento llamado verify_db
		listener.events.subscribe("verify_db", dealListener);
		
		// Esta llamada es para setear un boolean que informa si se debe ejecutar todo el flujo o no en el while abajo
		listener.verifyContentTable();
		
		while(dealListener.isTimeToExecute()) {
			
			// Se llena el hashmap con las clases estrategias que estan en el package cl.security.status.strategy.status
			// No se deben crear clases que no sean estrategia dentro de ese package ni tampoco packages dentro de ese package
			status = getInstatiatedStatusClasses();
			
			// El DealStatus sería un hilo donde se setea la estrategia que puede ser MLS o KGR
			DealStatus deal = new DealStatus();
			
			// Definiendo estrategia para MLSStatus
			strategy = status.get("mls");
			new Thread(deal.process(strategy)).start();
			
			// Cambiando estrategia para KGRStatus
			strategy = status.get("kgr");
			new Thread(deal.process(strategy)).start();
			
			
		}


	}

}
