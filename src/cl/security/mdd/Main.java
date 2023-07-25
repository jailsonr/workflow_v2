package cl.security.mdd;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cl.security.utils.Constants;
import cl.security.quartz.scheduler.CheckJob;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



public class Main {

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		//PropertyConfigurator.configure(Constants.LOG4J);
		//Logger log = Logger.getLogger(Main.class);
		
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		
		//log.info("-------------------------------------");
		//log.info("----------Inicio Workflow------------");
		//log.info("-------------------------------------");
//
//		// INTERVAL_TIME es de 5 segundos. Cambiar para que lo obtenga del archivo properties
		executorService.scheduleAtFixedRate(new CheckJob(), 0, 5, TimeUnit.SECONDS);
		
		//new Thread(new CheckJob()).start();
		
	}

}
