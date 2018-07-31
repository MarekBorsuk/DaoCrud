package pl.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pl.java.model.User;
import pl.java.util.ConnectionProvider;

public class MysqlUserDAO implements UserDAO {
	
	private final static String CREATE = "INSERT INTO user(pesel, firstname, lastname) VALUES(?, ?, ?);";
	private final static String READ = "SELECT pesel, firstname, lastname FROM user WHERE pesel = ?;";
	private final static String UPDATE = "UPDATE user SET pesel=?, firstname=?, lastname=? WHERE pesel = ?;";
	private final static String DELETE = "DELETE FROM user WHERE pesel=?;";
	
	@Override
	public boolean create(User user) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		boolean result = false;
		
		try {
			connection = ConnectionProvider.getConnection();
			preparedStatement = connection.prepareStatement(CREATE);
			preparedStatement.setString(1, user.getPesel());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				result = true;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			releaseResourcep(preparedStatement,null,connection);
		}
		
		return result;
	}

	@Override
	public User read(String pesel) {
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet resultSet = null;
		User resultUser = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			preStmt = conn.prepareStatement(READ);
			preStmt.setString(1, pesel);
			resultSet = preStmt.executeQuery();
			if (resultSet.next()) {
				resultUser = new User();
				resultUser.setPesel(resultSet.getString("pesel"));
				resultUser.setFirstName(resultSet.getString("firstname"));
				resultUser.setLastName(resultSet.getString("lastname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			releaseResourcep(preStmt, resultSet, conn);
		}		
		return resultUser;
		
	}

	@Override
	public boolean update(User user) {
		Connection conn = null;
		PreparedStatement preStmt = null;
		boolean result = false;
		try {
			conn = ConnectionProvider.getConnection();
			preStmt = conn.prepareStatement(UPDATE);
			preStmt.setString(1, user.getPesel());
			preStmt.setString(2, user.getFirstName());
			preStmt.setString(3, user.getLastName());
			preStmt.setString(4, user.getPesel());
			int rowsAffected = preStmt.executeUpdate();
			if (rowsAffected>0) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			releaseResourcep(preStmt, null, conn);
		}
		return result;
	}

	@Override
	public boolean delete(User user) {
		Connection conn = null;
		PreparedStatement preStmt = null;
		boolean result = false;
		try {
			conn = ConnectionProvider.getConnection();
			preStmt = conn.prepareStatement(DELETE);
			preStmt.setString(1, user.getPesel());
			int rowsAffected = preStmt.executeUpdate();
			if (rowsAffected > 0) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			releaseResourcep(preStmt, null, conn);
		}
		return result;
	}
	

	private void releaseResourcep(PreparedStatement preSta, ResultSet resultSet, Connection conn) {
		try {
			if (preSta != null && !preSta.isClosed()) {
				preSta.close();
			}
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}
