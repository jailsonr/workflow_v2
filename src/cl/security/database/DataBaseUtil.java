package cl.security.database;

import static java.lang.String.format;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBaseUtil {
	
	private String CREATE_TABLE_STMT = "CREATE TABLE %s (";
	private String VARCHAR = " varchar(255)";
	private String COMMA = " ,";
	
	private String INSERT_INTO = "insert into %s values (";
	
	private String DROP_TABLE = "IF EXISTS(select * from sysobjects where name='%s') drop table %s";

	
	Connection con;
	Statement stmt;
	
	public DataBaseUtil() {
		try {
			con = DatabaseConnection.getInstance().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dropTableIfExists(String table) {
		
		DROP_TABLE = format(DROP_TABLE, table, table);
		
		try {
			stmt = con.createStatement();
			boolean execute = stmt.execute(DROP_TABLE);
			if (execute) System.out.println("DROPED: " + table);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Table Name: " + table);
		}
		
	}

	public void createTable(String table, String[] columns) {

		StringBuilder query = new StringBuilder();

		query.append(String.format(CREATE_TABLE_STMT, table));

		for (String column : columns) {

			query.append(column + VARCHAR + COMMA);

		}

		int size = query.length();

		query.deleteCharAt(size - 1);

		query.append(");");
		
		try {
			stmt = con.createStatement();
			stmt.execute(query.toString());
			System.out.println("CREATED: " + table);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Table Name: " + table);
		}

	}
	
	public void insertInto(String table, List<String> values) {

		StringBuilder query = new StringBuilder();

		
		List<String> inserts = new ArrayList<>();

		for (String value : values) {

			query.append(String.format(INSERT_INTO, table));
			
			for (String v: value.split(";")) {
				query.append("'" + v + "'");
				query.append(COMMA);
			}
			int size = query.length();
			
			query.deleteCharAt(size - 1);
			
			query.append(");");
			
			inserts.add(query.toString().substring(0, query.toString().length()));
			
			query.setLength(0);
		}
		
		try {
			stmt = con.createStatement();
			
			inserts.forEach(s -> {
				try {
					stmt.addBatch(s);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
			stmt.executeBatch();
			System.out.println("insert into: " + table);
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Table Name: " + table);
		}

	}

	public static void main(String[] args) {
		
		
		DataBaseUtil util = new DataBaseUtil();
		
//		util.dropTableIfExists();
//		
//		util.createTable();
		
//
//		String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
//		String url;
//		url = "jdbc:sqlserver://localhost:1433;DatabaseName=tempdb;encrypt=true;trustServerCertificate=true";
//
//		Connection con;
//		Statement stmt;
//		try {
//			con = DriverManager.getConnection(url, "sa", "lbt-m14.2");
//			stmt = con.createStatement();
//			String query = "insert into Persons(PersonId, FirstName, Address, City) values(1, 'Jailson', 'Manzanares 11', 'Madrid')";
//			stmt.execute(query);
//			con.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//		}

	}

}
