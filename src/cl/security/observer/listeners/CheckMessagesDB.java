package cl.security.observer.listeners;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cl.security.database.DatabaseConnection;
import cl.security.database.utils.QueryEnum;
import cl.security.model.Params;

public class CheckMessagesDB implements EventListener {

	private boolean isTimeToExecute = false;

	private Connection con;
	private Statement stmt;
	private ResultSet rs = null;
	private Params params;

	public CheckMessagesDB() {
		try {
			con = DatabaseConnection.getInstance().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setIfIsTimeToExecute() {
		// Hardcode - Aqui de sebe ir a la BD a revisar si hay data en la tabla y de ser
		// asi se setea a true

		try {

			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,
					ResultSet.HOLD_CURSORS_OVER_COMMIT);

			rs = stmt.executeQuery(QueryEnum.VERIFY_MESSAGES.query);

			if (rs.next()) {
				setTimeToExecute(true);
				
				String arr[] = rs.getString(3).split("\\s+");
				
				params = new Params(arr[0], Integer.parseInt(arr[1].trim()), Integer.parseInt(arr[2].trim()));
			} else {
				setTimeToExecute(false);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Params getParams() {
		return params;
	}

	public boolean isTimeToExecute() {
		return isTimeToExecute;
	}

	private void setTimeToExecute(boolean isTimeToExecute) {
		this.isTimeToExecute = isTimeToExecute;
	}

}
