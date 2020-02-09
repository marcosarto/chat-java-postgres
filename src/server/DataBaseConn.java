package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DataBaseConn {
	private Properties queryProperties;
	private Properties dbProperties;
	private String postgresUrl;
	private String user;
	private String password;

	/*
	* Vengono usati due file di properties per poter modificare il comportamento del programma anche dopo averne creato
	* il jar.
	* db.properties : contiene i parametri per la connessione al database e le credenziali di accesso
	* query.properties : contiene le query utili al funzionamento del programma, il controllo del login viene fatto
	* 					nel codice java perche` si potesse implementare l'hashing e il salt.
	* */
	public DataBaseConn(){
		try (InputStream dbPropFile = new FileInputStream("db.properties");
			 InputStream queryPropFile = new FileInputStream("query.properties");
		) {

			queryProperties = new Properties();
			dbProperties = new Properties();
			dbProperties.load(dbPropFile);
			queryProperties.load(queryPropFile);
			postgresUrl = "jdbc:postgresql://" + dbProperties.getProperty("host") + ":"
					+ dbProperties.getProperty("port") + "/" + dbProperties.getProperty("dbName");
			user = dbProperties.getProperty("user");
			password = dbProperties.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException{
		return DriverManager.getConnection(postgresUrl, user, password);
	}

	public String getQuery(String queryId) {
		return queryProperties.getProperty(queryId);
	}

}
