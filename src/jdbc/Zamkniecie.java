package jdbc;

import java.sql.SQLException;



public class Zamkniecie {
	/**
	 * Zamykanie po��czenia z baz� danych
	 *
	 * @param connection
	 *            - po��czenie z baz�
	 * @param s
	 *            - obiekt przesy�aj�cy zapytanie do bazy
	 */
	public static void closeConnection(java.sql.Connection connection, java.sql.Statement s) {
	    System.out.print("\nZamykanie polaczenia z baza�:");
	    try {
	        s.close();
	        connection.close();
	    } catch (SQLException e) {
	        System.out
	                .println("Bl�d przy zamykaniu pol�czenia " + e.toString());
	        System.exit(4);
	    }
	    System.out.print(" zamkni�cie OK");
	}
}
