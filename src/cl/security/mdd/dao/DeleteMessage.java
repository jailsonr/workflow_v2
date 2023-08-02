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
			
			e1.getStackTrace();
			log.error("No se pudo obtener conexion. Error: " + e1.getMessage());

		}

		String query = QueryEnum.MESSAGES_IN_PROGRESS_DELETE.query;

		try (CallableStatement cs = con.prepareCall(query);) {

			cs.setInt(1, p.getKdbTablesId());
			cs.setInt(2, p.getDealsId());
			cs.setString(3, p.getDataBaseName().toUpperCase());

			log.info("Ejecutando " + query + " " + p.getKdbTablesId() + ", " + p.getDealsId() + ", " + p.getDataBaseName().toUpperCase());
			System.out.println("Executed " + query + " " + p.getKdbTablesId() + ", " + p.getDealsId() + ", " + p.getDataBaseName().toUpperCase());

			cs.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
			log.error("Not executed " + query + " " + p.getKdbTablesId() + ", " + p.getDealsId() + ", " + p.getDataBaseName().toUpperCase() + ". Error: " + e.getMessage());

		} finally {

			log.info("Se eliminó de la tabla WKF_MessagesInProgress " + p.getDataBaseName().toUpperCase() + " " + p.getKdbTablesId() + " " + p.getDealsId());

		}

	}

}
