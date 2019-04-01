package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Polaczenie {
	/**
	* Metoda s³u¿y do nawi¹zania po³¹czenia z baz¹ danych
	*
	* @param adress
	*
	            - adres bazy danych
	* @param dataBaseName
	*
	            - nazwa bazy
	* @param userName
	*            - login do bazy
	* @param password
	*            - has³o do bazy
	* @return - po³¹czenie z baz¹
	*/
	public static Connection connectToDatabase(String adress,
	        String dataBaseName, String userName, String password) {
	    System.out.print("\nLaczenie z baza danych:");
	    String baza = "jdbc:mysql://" + adress + "/" + dataBaseName;
	    // objasnienie opisu bazy:
	    // jdbc: - mechanizm laczenia z baza (moze byc inny, np. odbc)
	    // mysql: - rodzaj bazy
	    // adress - adres serwera z baza (moze byc tez w nazwy)
	    // dataBaseName - nazwa bazy
	    java.sql.Connection connection = null;
	    try {
	        connection = DriverManager.getConnection(baza, userName, password);
	    } catch (SQLException e) {
	        System.out.println("B³¹d przy pol¹czeniu z baz¹!");
	        
	    }
	    return connection;
	}
	
	/**
     * Metoda s³u¿y do po³¹czenia z MySQL bez wybierania konkretnej bazy
     *
     * @return referencja do uchwytu bazy danych
     */
    public static Connection getConnection(String adres, int port) {
 
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "");
 
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + adres + ":" + port + "/",
                    connectionProps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connected to database");
        return conn;
    }
}
