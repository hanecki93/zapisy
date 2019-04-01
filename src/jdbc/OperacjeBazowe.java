package jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class OperacjeBazowe {

	/**
	 * tworzenie obiektu Statement przesy�aj�cego zapytania do bazy connection
	 *
	 * @param connection
	 *            - po��czenie z baz�
	 * @return obiekt Statement przesy�aj�cy zapytania do bazy
	 */
	public static Statement createStatement(java.sql.Connection connection) {
		try {
			return connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		;
		return null;
	}

	/**
	 * Wykonanie kwerendy i przes�anie wynik�w do obiektu ResultSet
	 *
	 * @param s
	 *            - Statement
	 * @param sql
	 *            - zapytanie
	 * @return wynik
	 */
	public static ResultSet executeQuery(Statement s, String sql) {
		try {
			return s.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Wy�wietla dane uzyskane zapytaniem select
	 *
	 * @param r
	 *            - wynik zapytania
	 */
	public static void printDataFromQuery(ResultSet r) {
		ResultSetMetaData rsmd;
		try {
			rsmd = r.getMetaData();
			int numcols = rsmd.getColumnCount(); // pobieranie liczby column
			// wyswietlanie nazw kolumn:
			for (int i = 1; i <= numcols; i++) {
				System.out.print("\t" + rsmd.getColumnLabel(i) + "\t|");
			}
			System.out.print("\n____________________________________________________________________________\n");
			/**
			 * r.next() - przej�cie do kolejnego rekordu (wiersza) otrzymanych wynik�w
			 */
			// wyswietlanie kolejnych rekordow:
			while (r.next()) {
				for (int i = 1; i <= numcols; i++) {
					Object obj = r.getObject(i);
					if (obj != null)
						System.out.print("\t" + obj.toString() + "\t|");
					else
						System.out.print("\t");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			System.out.println("Bl�d odczytu z bazy! " + e.toString());
			System.exit(3);
		}
	}

	public static int executeUpdate(Statement s, String sql) {
		try {
			return s.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
