package cl.security.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.PooledConnection;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerPooledConnection;
import com.microsoft.sqlserver.jdbc.SQLServerXADataSource;

import cl.security.utils.Constants;

public class DatabaseConnection {

	private static DatabaseConnection instance;
	
	SQLServerXADataSource XADataSource1 = new SQLServerXADataSource();

	private Connection connection;
	//private String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Kustom;encrypt=true;trustServerCertificate=true";
	private String url = Constants.URL;
	//private String username = "sa";
	private String username = Constants.USERKONDOR;
	//private String password = "lbt-m14.2";
	private String password = Constants.PASSWORDKONDOR;
	String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";

	private DatabaseConnection() throws SQLException {
		
		XADataSource1.setURL(url);
		XADataSource1.setUser(username);
		XADataSource1.setPassword(password);
		//XADataSource1.setDatabaseName("tempdb");
		XADataSource1.setDatabaseName("Kustom");
		PooledConnection pc = XADataSource1.getPooledConnection();
		
		//PropertyConfigurator.configure(Constants.LOG4J);
		//Logger log = Logger.getLogger(DatabaseConnection.class);
		
		try {
//			this.connection = DriverManager.getConnection(url, username, password);
			this.connection = pc.getConnection();
			System.out.println("Database Connection Creation Success ");
			//log.info("Database Connection Creation Success ");
			
		} catch (Exception ex) {
			System.out.println("Database Connection Creation Failed : " + ex.getMessage());
			//log.error("Database Connection Creation Failed : " + ex.getMessage());
		}
	}
//	
	public void shutdown() {
		try {
			this.connection.close();
			instance = null;
		} catch (SQLException e) {
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
