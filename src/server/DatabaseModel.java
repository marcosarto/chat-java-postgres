package server;

import java.sql.*;

public class DatabaseModel {
	private DataBaseConn dbConn;
	private Connection connection;

	public DatabaseModel(){
		dbConn = new DataBaseConn();

		try {
			connection = dbConn.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	boolean registrazione(String username, String password) throws SQLException{
		//Controllo che il nome utente non esista gia`
		if (controllaUserEsistente(username))
			return false;

		//Prendo la query dal file properties creata per la registrazione e sostituisco ai due parametri indicati con
		//'?' con username e password da inserire.
		PreparedStatement statement = connection.prepareStatement(dbConn.getQuery("registrazione"));
		statement.setString(1,username);
		statement.setString(2,password);
		statement.execute();
		return true;
	}

	private boolean controllaUserEsistente(String username) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(dbConn.getQuery("usernames"));
		ResultSet resultSet = statement.executeQuery();
		while(resultSet.next()){
			if(username.equalsIgnoreCase(resultSet.getString("username")))
				return true;
		}
		return false;
	}

	boolean login(String username,String password) throws SQLException{

		PreparedStatement statement = connection.prepareStatement(dbConn.getQuery("login"));
		ResultSet resultSet = statement.executeQuery();

		//Potrei fare il controllo tramite sql ma implementato in questo modo e` piu facile un eventuale modifica del
		//comportamento del metodo login
		while(resultSet.next()){
			if(username.equalsIgnoreCase(resultSet.getString("username"))
			&& password.equals(resultSet.getString("password")))
				return true;
		}

		return false;
	}
}
