package cl.security.mdd.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import cl.security.database.DatabaseConnection;
import cl.security.database.utils.QueryEnum;
import cl.security.model.Params;

public class DeleteMessage {


	public static void deleteMessage(Params p) {
		try {
			Connection con = DatabaseConnection.getInstance().getConnection();

			CallableStatement cs = null;

			String query = QueryEnum.MESSAGES_IN_PROGRESS_DELETE.query;

			try {

				cs = con.prepareCall(query);

				cs.setInt(1, p.getKdbTablesId());
				cs.setInt(2, p.getDealsId());
				cs.setString(3, p.getDataBaseName().toUpperCase());

				System.out.println("Ejecutando DELETE_MESSAGE");
				cs.executeUpdate();

			} catch (SQLException e) {
				System.out.println("error 2: " + e);
			} finally {
				System.out.println("Se eliminó de la tabla WKF_MessagesInProgress " + p.getDealsId());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
