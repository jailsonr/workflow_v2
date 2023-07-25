package cl.security.quartz.scheduler;

import static cl.security.utils.LoaderUtil.getInstatiatedStatusClasses;
import java.util.HashMap;
import java.util.Map;
import cl.security.observer.listeners.CheckMessagesDB;
import cl.security.status.strategy.StatusStrategy;
import cl.security.status.strategy.deal.ApplicationStatus;

public class CheckJob implements Runnable {

	public static Map<String, StatusStrategy> status = new HashMap<String, StatusStrategy>();

	@Override
	public void run() {

		CheckMessagesDB checkMessages = new CheckMessagesDB();
		checkMessages.buildParams();

		checkMessages.getParamSet().forEach(param -> {
			// Se llena el hashmap con las clases estrategias que estan en el package
			// cl.security.status.strategy.status
			// No se deben crear clases que no sean estrategia dentro de ese package ni
			// tampoco packages dentro de ese package

			StatusStrategy strategy;
			try {
				status = getInstatiatedStatusClasses();
				System.out.println("StatusStrategy");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// El DealStatus sería un hilo donde se setea la estrategia que puede ser MLS o
			// KGR
			ApplicationStatus appStatus = new ApplicationStatus();

			String dataBaseName = param.getDataBaseName();
			
			System.out.println("appStatus");

			// Definiendo estrategia para el objeto que calza con el nombre que viene de la
			// base de datos
			strategy = status.get(dataBaseName.toLowerCase());
			System.out.println("strategy 1 " + strategy);
			System.out.println("strategy 2 " + strategy.toString());
			new Thread(appStatus.process(strategy, param)).start();
		});

	}

}
