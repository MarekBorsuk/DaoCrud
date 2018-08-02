package pl.java.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionProvider {
	private static DataSource dataSource;
	
	public static Connection getConnection() throws SQLException {
		return getDSInstance().getConnection();
	}
	//musis by� publiczne aby udostpni� DataSource dla Spring JDBC
	public static DataSource getDSInstance() {
		if(dataSource == null) {
			try {
				Context initContext = new InitialContext();
				Context envContext = (Context) initContext.lookup("java:comp/env");
				dataSource = (DataSource) envContext.lookup("jdbc/library");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return dataSource;
	}
}