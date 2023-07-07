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
import cl.security.status.strategy.deal.DealStatus;

public class CheckJob implements Job {

	private Connection con;
	private Statement stmt;
	private ResultSet rs = null;
	public static Map<String, StatusStrategy> status = new HashMap<String, StatusStrategy>();

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
//
//		try {
//			con = DatabaseConnection.getInstance().getConnection();
//
//			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,
//					ResultSet.HOLD_CURSORS_OVER_COMMIT);
//
//			rs = stmt.executeQuery(QueryEnum.VERIFY_MESSAGES.query);
//
//			if (rs.next()) {
//
//				StatusStrategy strategy;
//
//				CheckMessagesDB checkMessages = new CheckMessagesDB();
//
//				while (checkMessages.isTimeToExecute()) {
//
//					// Se llena el hashmap con las clases estrategias que estan en el package
//					// cl.security.status.strategy.status
//					// No se deben crear clases que no sean estrategia dentro de ese package ni
//					// tampoco packages dentro de ese package
//					status = getInstatiatedStatusClasses();
//
//					// El DealStatus sería un hilo donde se setea la estrategia que puede ser MLS o
//					// KGR
//					DealStatus deal = new DealStatus();
//
//					// Definiendo estrategia para MLSStatus
//					strategy = status.get("mls");
//					new Thread(deal.process(strategy)).start();
//
//					// Cambiando estrategia para KGRStatus
//					strategy = status.get("kgr");
//					new Thread(deal.process(strategy)).start();
//
////				setDataBaseName(rs.getString(3).split("\\s+")[0]);
//				}
//			}
//
//		} catch (SQLException | ClassNotFoundException | InstantiationException
//				| IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		StatusStrategy strategy;

		// Se llena el hashmap con las clases estrategias que estan en el package
		// cl.security.status.strategy.status
		// No se deben crear clases que no sean estrategia dentro de ese package ni
		// tampoco packages dentro de ese package
		try {
			status = getInstatiatedStatusClasses();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// El DealStatus sería un hilo donde se setea la estrategia que puede ser MLS o
		// KGR
		DealStatus deal = new DealStatus();
		
		// Definiendo estrategia para MLSStatus
		strategy = status.get("mls");
		new Thread(deal.process(strategy)).start();
		
		if (new Random().nextInt() %2 == 0) {
			
			// Cambiando estrategia para KGRStatus
			strategy = status.get("kgr");
			new Thread(deal.process(strategy)).start();
			
		}

		
		

	}

}
