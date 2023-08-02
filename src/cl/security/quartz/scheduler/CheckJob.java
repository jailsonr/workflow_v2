package cl.security.quartz.scheduler;

import static cl.security.utils.LoaderUtil.getInstatiatedStatusClasses;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.security.observer.listeners.CheckMessagesDB;
import cl.security.status.strategy.StatusStrategy;
import cl.security.status.strategy.deal.ApplicationStatus;


public class CheckJob implements Runnable {
	
	Logger log = Logger.getLogger(CheckJob.class);

	public static Map<String, StatusStrategy> status = new HashMap<String, StatusStrategy>();

	@Override
	public void run() {

		CheckMessagesDB checkMessages = new CheckMessagesDB();
		checkMessages.buildParams();

		checkMessages.getParamSet().forEach(param -> {
			
			StatusStrategy strategy;
			
			try {
				
				status = getInstatiatedStatusClasses();
				log.debug("Obtencion de clases de estados");
				System.out.println("Obtencion de clases de estados");
				
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				
				e.printStackTrace();
				log.error("Error obtencion de clases de estados" + e.getMessage());
				System.out.println("Error obtencion de clases de estados" + e.getMessage());
				
			}

			ApplicationStatus appStatus = new ApplicationStatus();
			String dataBaseName = param.getDataBaseName();
			strategy = status.get(dataBaseName.toLowerCase());
			log.debug("Definiendo estrategia " + strategy.toString());
			System.out.println("Definiendo estrategia " + strategy.toString());
			
			new Thread(appStatus.process(strategy, param)).start();
			
		});

	}

}
