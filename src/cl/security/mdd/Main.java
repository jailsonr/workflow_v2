package cl.security.mdd;


import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import cl.security.quartz.scheduler.CheckJob;
import cl.security.utils.Constants;

public class Main {

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		JobDetail j = JobBuilder.newJob(CheckJob.class).build();

		// INTERVAL_TIME es de 5 segundos. Cambiar para que lo obtenga del archivo properties
		Trigger t = TriggerBuilder.newTrigger().withIdentity("CroneTrigger")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(Constants.INTERVAL_TIME).repeatForever()).build();
		
		try {
			Scheduler s = StdSchedulerFactory.getDefaultScheduler();
			s.start();
			s.scheduleJob(j, t);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
