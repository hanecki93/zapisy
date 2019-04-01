package jdbc;

import java.sql.SQLException;



public class Zamkniecie {
	/**
	 * Zamykanie po³¹czenia z baz¹ danych
	 *
	 * @param connection
	 *            - po³¹czenie z baz¹
	 * @param s
	 *            - obiekt przesy³aj¹cy zapytanie do bazy
	 */
	public static void closeConnection(java.sql.Connection connection, java.sql.Statement s) {
	    System.out.print("\nZamykanie polaczenia z baza¹:");
	    try {
	        s.close();
	        connection.close();
	    } catch (SQLException e) {
	        System.out
	                .println("Bl¹d przy zamykaniu pol¹czenia " + e.toString());
	        System.exit(4);
	    }
	    System.out.print(" zamkniêcie OK");
	}
}
