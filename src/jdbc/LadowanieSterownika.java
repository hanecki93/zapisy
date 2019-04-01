package jdbc;

public class LadowanieSterownika {
	/**
	 * Metoda ³aduje sterownik jdbc
	 *
	 * @return true/false
	 */
public	static boolean ladujSterownik() {
	    // LADOWANIE STEROWNIKA
	    System.out.print("Sprawdzanie sterownika:");
	    try {
	        Class.forName("com.mysql.jdbc.Driver").newInstance();
	        System.out.println("Znaleziono sterownik");
	        return true;
	    } catch (Exception e) {
	        System.out.println("Blad przy ladowaniu sterownika bazy!");
	        return false;
	    }
	}
}
