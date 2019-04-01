package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Polaczenie {
	/**
	* Metoda s�u�y do nawi�zania po��czenia z baz� danych
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
	*            - has�o do bazy
	* @return - po��czenie z baz�
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
	        System.out.println("B��d przy pol�czeniu z baz�!");
	        
	    }
	    return connection;
	}
	
	/**
     * Metoda s�u�y do po��czenia z MySQL bez wybierania konkretnej bazy
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
