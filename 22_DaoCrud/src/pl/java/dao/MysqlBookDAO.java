package pl.java.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import pl.java.model.Book;
import pl.java.util.ConnectionProvider;

public class MysqlBookDAO implements BookDAO{
	private final static String CREATE = "INSERT INTO book(isbn, title, description) VALUES(:isbn, :title, :description);";
	private final static String READ = "SELECT isbn, title, description FROM book WHERE isbn = :isbn;";
	private final static String UPDATE = "UPDATE book SET isbn=:isbn, title=:title, description=:description WHERE isbn = :isbn;";
	private final static String DELETE = "DELETE FROM book WHERE isbn=:isbn;";

	private NamedParameterJdbcTemplate template;
	
	public MysqlBookDAO() {
		template = new NamedParameterJdbcTemplate(ConnectionProvider.getDSInstance());
	}
	
	@Override
	public boolean create(Book book) {
//		Connection conn = null;
//		PreparedStatement prepStmt = null;
//		boolean result = false;
//		try {
//			conn = ConnectionProvider.getConnection();
//			prepStmt = conn.prepareStatement(CREATE);
//			prepStmt.setString(1, book.getIsbn());
//			prepStmt.setString(2, book.getTitle());
//			prepStmt.setString(3, book.getDescription());
//			int rowsAffected = prepStmt.executeUpdate();
//			if (rowsAffected > 0) {
//				result = true;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			releaseResources(prepStmt, null, conn);
//		}
//		return result;
		BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(book);
		int rowsAffected = template.update(CREATE, beanParamSource);
		boolean result = false;
		if (rowsAffected>0) {
			result = true;
		}
		return result;
	}

	@Override
	public Book read(String isbn) {
//		Connection conn = null;
//		PreparedStatement prepStmt = null;
//		ResultSet resultSet = null;
//		Book resultBook = null;
//		try {
//			conn = ConnectionProvider.getConnection();
//			prepStmt = conn.prepareStatement(READ);
//			prepStmt.setString(1, isbn);
//			resultSet = prepStmt.executeQuery();
//			if(resultSet.next()) {
//				resultBook = new Book();
//				resultBook.setIsbn(resultSet.getString("isbn"));
//				resultBook.setTitle(resultSet.getString("title"));
//				resultBook.setDescription(resultSet.getString("description"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			releaseResources(prepStmt, resultSet, conn);
//		}
//		return resultBook;
		Book resultBook = null;
		SqlParameterSource namedParameters = new MapSqlParameterSource("isbn",isbn);
		List<Book> bookList = template.query(READ, namedParameters, BeanPropertyRowMapper.newInstance(Book.class));
		if (bookList.get(0) != null) {
			resultBook = bookList.get(0);
		}
		return resultBook;
	}

	@Override
	public boolean update(Book book) {
//		Connection conn = null;
//		PreparedStatement prepStmt = null;
//		boolean result = false;
//		try {
//			conn = ConnectionProvider.getConnection();
//			prepStmt = conn.prepareStatement(UPDATE);
//			prepStmt.setString(1, book.getIsbn());
//			prepStmt.setString(2, book.getTitle());
//			prepStmt.setString(3, book.getDescription());
//			prepStmt.setString(4, book.getIsbn());
//			int rowsAffected = prepStmt.executeUpdate();
//			if (rowsAffected > 0) {
//				result = true;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			releaseResources(prepStmt, null, conn);
//		}
//		return result;
		BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(book);
		int rowsAffected = template.update(UPDATE, beanParamSource);
		boolean result = false;
		if (rowsAffected > 0) {
			result = true;
		}
		return result;
	}

	@Override
	public boolean delete(Book book) {
		SqlParameterSource namedParameter = new MapSqlParameterSource("isbn", book.getIsbn());
		int rowsAffected = template.update(DELETE, namedParameter);
		boolean result = false;
		if (rowsAffected > 0 ) {
			result = true;
		}
		return result;
	}

//	private void releaseResources(PreparedStatement prepStmt, ResultSet res,
//			Connection conn) {
//		try {
//			if (prepStmt != null && !prepStmt.isClosed()) {
//				prepStmt.close();
//			}
//			if (res != null && !res.isClosed()) {
//				res.close();
//			}
//			if (conn != null && !conn.isClosed()) {
//				conn.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//	}
}
