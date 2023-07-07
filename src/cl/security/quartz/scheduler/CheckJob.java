package cl.security.quartz.scheduler;

import static cl.security.utils.LoaderUtil.getInstatiatedStatusClasses;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cl.security.database.DatabaseConnection;
import cl.security.database.utils.QueryEnum;
import cl.security.observer.listeners.CheckMessagesDB;
import cl.security.status.strategy.StatusStrategy;
import cl.security.status.strategy.deal.ApplicationStatus;

public class CheckJob implements Job {

	private Connection con;
	private Statement stmt;
	private ResultSet rs = null;
	public static Map<String, StatusStrategy> status = new HashMap<String, StatusStrategy>();

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		try {
			con = DatabaseConnection.getInstance().getConnection();

			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			rs = stmt.executeQuery(QueryEnum.VERIFY_MESSAGES.query);

			if (rs.next()) {

				StatusStrategy strategy;

				CheckMessagesDB checkMessages = new CheckMessagesDB();

				while (checkMessages.isTimeToExecute()) {

					// Se llena el hashmap con las clases estrategias que estan en el package
					// cl.security.status.strategy.status
					// No se deben crear clases que no sean estrategia dentro de ese package ni
					// tampoco packages dentro de ese package
					status = getInstatiatedStatusClasses();

					// El DealStatus sería un hilo donde se setea la estrategia que puede ser MLS o
					// KGR
					ApplicationStatus appStatus = new ApplicationStatus();
					
					String dataBaseName = checkMessages.getParams().getDataBaseName();
					
					// Definiendo estrategia para el objeto que calza con el nombre que viene de la base de datos
					strategy = status.get(dataBaseName.toLowerCase());
					new Thread(appStatus.process(strategy, checkMessages)).start();

				}
			}

		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		
		

	}

}
