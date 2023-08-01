package cl.security.mdd.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cl.security.database.DatabaseConnection;
import cl.security.database.utils.QueryEnum;
import cl.security.model.Params;

public class DeleteMessage {

	static Logger log = Logger.getLogger(DeleteMessage.class);

	public static void deleteMessage(Params p) {
		Connection con = null;
		try {
			con = DatabaseConnection.getInstance().getConnection();
		} catch (SQLException e1) {
			log.error("No se pudo obtener conexion");
			System.out.println("No se pudo obtener conexion");
		}

		String query = QueryEnum.MESSAGES_IN_PROGRESS_DELETE.query;

		try (CallableStatement cs = con.prepareCall(query);) {

			cs.setInt(1, p.getKdbTablesId());
			cs.setInt(2, p.getDealsId());
			cs.setString(3, p.getDataBaseName().toUpperCase());

			log.info("Executed " + QueryEnum.MESSAGES_IN_PROGRESS_DELETE.query);
			System.out.println("Executed " + QueryEnum.MESSAGES_IN_PROGRESS_DELETE.query);

			cs.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
			log.error("Not executed " + QueryEnum.MESSAGES_IN_PROGRESS_DELETE.query + ".Error: " + e.getMessage());
			System.out.println("Not executed " + QueryEnum.MESSAGES_IN_PROGRESS_DELETE.query + ".Error: " + e.getMessage());

		} finally {

			log.info("Se eliminó de la tabla WKF_MessagesInProgress " + p.getDealsId());
			System.out.println("Se eliminó de la tabla WKF_MessagesInProgress " + p.getDealsId());

		}

	}

}
