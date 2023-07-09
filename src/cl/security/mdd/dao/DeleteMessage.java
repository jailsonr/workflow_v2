package cl.security.mdd.dao;

import java.sql.Connection;
import java.sql.SQLException;

import cl.security.database.DatabaseConnection;

public class DeleteMessage {
	
	public static void deleteMessage() {
		try {
			Connection con = DatabaseConnection.getInstance().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
