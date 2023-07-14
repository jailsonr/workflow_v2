package cl.security.mdd.dao;

import java.sql.Connection;
import java.sql.SQLException;

import cl.security.database.DatabaseConnection;
import cl.security.model.Params;

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
	
	public abstract void createKisFile();
	
	public abstract Repair build(Params p, String reparo);
	
	public abstract Repair queryUpdateRepair(int dealId, int kdbTablesId,
			String repKGR, String repMLS,String envBO);

}
