package pl.java.dao;

public class MysqlDAOFactory extends DAOFactory{

	@Override
	public BookDAO getBookDAO() {
		// TODO Auto-generated method stub
		return new MysqlBookDAO();
	}

	@Override
	public UserDAO getUserDAO() {
		// TODO Auto-generated method stub
		return new MysqlUserDAO();
	}

}
