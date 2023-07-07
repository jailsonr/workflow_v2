package cl.security.status.strategy;

import java.sql.Connection;
import java.sql.SQLException;

import cl.security.database.DatabaseConnection;
import cl.security.model.Params;

public interface StatusStrategy {
	
	default Connection getConn() {
		try {
			return DatabaseConnection.getInstance().getConnection();
		} catch (SQLException e) {
		}
		return null;
	}
	
	public String statusFromCustomWindow(Params p);
	public void acceptanceLogger(Params p);

}
