package cl.security.mdd;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import cl.security.quartz.scheduler.CheckJob;
import cl.security.utils.Constants;

public class Main {

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		PropertyConfigurator.configure(Constants.LOG4J);
		Logger log = Logger.getLogger(Main.class);

		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		
		log.info("Iniciando nueva ejecución Workflow");

		executorService.scheduleAtFixedRate(new CheckJob(), 0, 5, TimeUnit.SECONDS);

	}

}
