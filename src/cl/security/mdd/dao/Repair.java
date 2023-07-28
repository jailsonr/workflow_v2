package cl.security.mdd.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import cl.security.database.DatabaseConnection;
import cl.security.model.Params;
import cl.security.utils.Constants;
import cl.security.utils.PropertiesUtil;

public abstract class Repair {

	public Params p;
	public String reparo;

	Repair() {
	}

	protected Connection getConn() {
		try {
			return DatabaseConnection.getInstance().getConnection();
		} catch (SQLException e) {
		}
		return null;
	}

	public abstract void createKisFile(Params p);

	public abstract Repair build(Params p, String reparo);

	public abstract Repair queryUpdateRepair(int dealId, int kdbTablesId, String repKGR, String repMLS, String envBO);

	public void deleteMessage(Params p) {
		// PropertyConfigurator.configure(Constants.LOG4J);
		// Logger log = Logger.getLogger(Repair.class);
		System.out.println("Eliminando mensaje de la tabla");
		// log.info("Eliminando mensaje de la tabla");
		DeleteMessage.deleteMessage(p);
	}

	public boolean queryUpdateWKFDealsList(int dealId, int kdbTableId, int transactionId) {

		String queryUpdateRepair = "UPDATE " + PropertiesUtil.DEAL + " SET Status = 'T' WHERE DealId = " + dealId
				+ " AND KdbTableId = " + kdbTableId + " AND TransactionId = " + transactionId;

		// PropertyConfigurator.configure(Constants.LOG4J);
		// Logger log = Logger.getLogger(Repair.class);

		try (Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();) {
			stmt.executeUpdate(queryUpdateRepair);
			System.out.println("Ejecutando " + PropertiesUtil.DEAL + " DealId: " + dealId);
			// log.info("Ejecutando " + PropertiesUtil.DEAL + " DealId: " + dealId);

		} catch (SQLException e) {
		}
		return true;

	}

}
