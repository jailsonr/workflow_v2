package cl.security.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import cl.security.utils.Constants;

public class DatabaseConnection {

	Logger log = Logger.getLogger(DatabaseConnection.class);

//	SQLServerXADataSource XADataSource1 = new SQLServerXADataSource();

	private static DataSource dataSource = null;

//	private Connection connection;
	// private static String url =
	// "jdbc:sqlserver://localhost:1433;DatabaseName=Kustom;encrypt=true;trustServerCertificate=true";
	private static String url = Constants.URL;
	// private static String username = "sa";
	private static String username = Constants.USERKONDOR;
//	private static String password = "lbt-m14.2";
	private static String password = Constants.PASSWORDKONDOR;
	private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static int maximumPoolSize = Integer.parseInt(Constants.MAXIMUM_POOL_SIZE);
	private static int connectionTimeout = Integer.parseInt(Constants.CONNECTION_TIMEOUT);
	private static int idleTimeout = Integer.parseInt(Constants.IDLE_TIMEOUT);

//	private DatabaseConnection() throws SQLException {
//
////		dataSource.setDriverClassName(driver);
////		dataSource.setJdbcUrl(url);
////		dataSource.setUsername(username);
////		dataSource.setPassword(password);
////
////		dataSource.setMinimumIdle(100);
////		dataSource.setMaximumPoolSize(10000);
////		dataSource.setLoginTimeout(3);
//
////		XADataSource1.setURL(url);
////		XADataSource1.setUser(username);
////		XADataSource1.setPassword(password);
////		//XADataSource1.setDatabaseName("tempdb");
////		XADataSource1.setDatabaseName("Kustom");
//
//		// PropertyConfigurator.configure(Constants.LOG4J);
//		// Logger log = Logger.getLogger(DatabaseConnection.class);
//
//		try {
//			log.info("Conectándose a la BD");
////			this.connection = DriverManager.getConnection(url, username, password);
//			this.connection = dataSource.getConnection();
////			this.connection = pc.getConnection();
//			System.out.println("Database Connection Creation Success ");
//			log.info("Database Connection Creation Success");
//			// log.info("Database Connection Creation Success ");
//
//		} catch (Exception ex) {
//			log.error("Database Connection Creation Failed : " + ex.getMessage());
//			System.out.println("Database Connection Creation Failed : " + ex.getMessage());
//
//			// log.error("Database Connection Creation Failed : " + ex.getMessage());
//		}
//	}
//	

	private static void createDataSource() {
		HikariConfig hikariConfig = getHikariConfig();
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		dataSource = hikariDataSource;
	}

	private static HikariConfig getHikariConfig() {
		HikariConfig hikaConfig = new HikariConfig();
		hikaConfig.setJdbcUrl(url);
		hikaConfig.setUsername(username);
		hikaConfig.setPassword(password);
		hikaConfig.setDriverClassName(driver);
		hikaConfig.setPoolName("sql-server-pool-1");
		hikaConfig.setMaximumPoolSize(maximumPoolSize);
		hikaConfig.setConnectionTimeout(Duration.ofSeconds(connectionTimeout).toMillis());
		hikaConfig.setIdleTimeout(Duration.ofMinutes(idleTimeout).toMillis());
		return hikaConfig;
	}

//	public Connection getConnection() {
//		return connection;
//	}
//
//	public static DatabaseConnection getInstance() throws SQLException {
//		if (instance == null) {
//			instance = new DatabaseConnection();
//		} else if (getConnection().isClosed()) {
//			instance = new DatabaseConnection();
//		}
//
//		return instance;
//	}

	private static DataSource getDataSource() {
		if (null == dataSource) {
			System.out.println("No DataSource is available. We will create a new one.");
			createDataSource();
		}
		return dataSource;
	}

	public static Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}

//	public static void execute(Connection con) {
//
////		Statement stmt;
//		ResultSet rs = null;
//
//		try (Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,
//				ResultSet.HOLD_CURSORS_OVER_COMMIT);) {
//
//			rs = stmt.executeQuery(QueryEnum.VERIFY_MESSAGES.query);
//
//			System.out.println("Executed " + QueryEnum.VERIFY_MESSAGES.query);
//
//			while (rs.next()) {
//
//				String arr[] = rs.getString(3).split("\\s+");
//
//				for (String s : arr) {
//					System.out.print(Thread.currentThread().getId() + " " + s + " ");
//
//				}
//				System.out.println();
//
//			}
//
//			// stmt.close();
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//
//		}
//	}

//	public static void main(String[] args) {
//
//		// PROBANDO EL POOL DE CONEXIONES
//		try {
//			Connection con = DatabaseConnection.getConnection();
//			System.out.println("Database conexion created");
//
//			new Thread(() -> execute(con)).start();
//			new Thread(() -> execute(con)).start();
//			new Thread(() -> execute(con)).start();
//			new Thread(() -> execute(con)).start();
//			new Thread(() -> execute(con)).start();
//			new Thread(() -> execute(con)).start();
//			new Thread(() -> execute(con)).start();
//			new Thread(() -> execute(con)).start();
//			new Thread(() -> execute(con)).start();
//			new Thread(() -> execute(con)).start();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

}
