package cl.security.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.PooledConnection;

import org.apache.log4j.Logger;

import com.microsoft.sqlserver.jdbc.SQLServerXADataSource;

import cl.security.utils.Constants;

public class DatabaseConnection {

	private static DatabaseConnection instance;
	Logger log = Logger.getLogger(DatabaseConnection.class);

	SQLServerXADataSource XADataSource1 = new SQLServerXADataSource();

	private Connection connection;
	private String url = Constants.URL;
	private String username = Constants.USERKONDOR;
	private String password = Constants.PASSWORDKONDOR;
	String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";

	private DatabaseConnection() throws SQLException {

		XADataSource1.setURL(url);
		XADataSource1.setUser(username);
		XADataSource1.setPassword(password);
		XADataSource1.setDatabaseName("Kustom");
		PooledConnection pc = XADataSource1.getPooledConnection();

		try {
			this.connection = pc.getConnection();
			log.info("Database Connection Creation Success");
			System.out.println("Database Connection Creation Success ");
		} catch (Exception ex) {
			log.error("Database Connection Creation Failed : " + ex.getMessage());
			System.out.println("Database Connection Creation Failed : " + ex.getMessage());
		}
	}

	public void shutdown() {
		try {
			this.connection.close();
			instance = null;
			log.info("Database Connection Close Success");
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("Database Connection Close Failed : " + e.getMessage());
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public static DatabaseConnection getInstance() throws SQLException {
		if (instance == null) {
			instance = new DatabaseConnection();
		} else if (instance.getConnection().isClosed()) {
			instance = new DatabaseConnection();
		}
		return instance;
	}

	public static void main(String[] args) {
		try {
			new DatabaseConnection().getInstance().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}