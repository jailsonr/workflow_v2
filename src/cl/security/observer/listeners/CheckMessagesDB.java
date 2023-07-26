package cl.security.observer.listeners;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import cl.security.database.DatabaseConnection;
import cl.security.database.utils.QueryEnum;
import cl.security.model.Params;

public class CheckMessagesDB {

	private Connection con;
	private Statement stmt;
	private ResultSet rs = null;
	private Params params;
	private Set<Params> paramSet = new HashSet<Params>();
	Logger log = Logger.getLogger(CheckMessagesDB.class);

	public CheckMessagesDB() {
		try {
			con = DatabaseConnection.getInstance().getConnection();

		} catch (SQLException e) {
			log.error("No se pudo obtener conexión ");
		}
	}

	public void buildParams() {

		try {

			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			rs = stmt.executeQuery(QueryEnum.VERIFY_MESSAGES.query);

			log.info("Ejecutando " + QueryEnum.VERIFY_MESSAGES.query);

			System.out.println("Verify_Messages");

			while (rs.next()) {

				String arr[] = rs.getString(3).split("\\s+");

				params = new Params(arr[0], Integer.parseInt(arr[1].trim()), Integer.parseInt(arr[2].trim()));

				paramSet.add(params);

			}

		} catch (SQLException e) {
			log.error("No se pudo ejecutar query: " + QueryEnum.VERIFY_MESSAGES.query);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Set<Params> getParamSet() {
		return paramSet;
	}

	public ResultSet getRs() {
		return rs;
	}

}
