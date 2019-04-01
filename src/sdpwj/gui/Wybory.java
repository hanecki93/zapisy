package sdpwj.gui;

import java.io.Serializable;
import java.util.ArrayList;


public class Wybory implements Serializable {
	private static final long serialVersionUID = 1L;
	String nazwisko = "";
	ArrayList<String> godziny;
	public Wybory(String nazwisko, ArrayList<String> godziny) {
		this.nazwisko = nazwisko;
		this.godziny = godziny;
	}
	public String getNazwisko() {
		return nazwisko;
	}
	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}
	public ArrayList<String> getGodziny() {
		return godziny;
	}
	public void setGodziny(ArrayList<String> godziny) {
		this.godziny = godziny;
	}
	@Override
	public String toString() {
		return "Wybory [nazwisko=" + nazwisko + ", godziny=" + godziny + "]";
	}
	
}
