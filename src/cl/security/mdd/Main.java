package cl.security.mdd;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cl.security.quartz.scheduler.CheckJob;

public class Main {

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

		// INTERVAL_TIME es de 5 segundos. Cambiar para que lo obtenga del archivo properties
		executorService.scheduleAtFixedRate(new CheckJob(), 0, 5, TimeUnit.SECONDS);

	}

}
