package sdpwj.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Przedmiot implements Serializable{
private static final long serialVersionUID = 1L;
	Map<Integer, String> godziny = new TreeMap<>();
	String nazwa = "";
	ArrayList<Integer> iloœæ_wolnych= new ArrayList<Integer>();

	


	public Przedmiot(String nazwa, Map<Integer, String> godziny ,ArrayList<Integer> iloœæ_wolnych){
		this.nazwa = nazwa;
		this.godziny = godziny;
		this.iloœæ_wolnych = iloœæ_wolnych;
	}


	public Map<Integer, String> getGodziny() {
		return godziny;
	}


	@Override
	public String toString() {
		return "Przedmiot [godziny=" + godziny + ", nazwa=" + nazwa + ", iloœæ_wolnych=" + iloœæ_wolnych + "]";
	}


	public void setGodziny(Map<Integer, String> godziny) {
		this.godziny = godziny;
	}


	public String getNazwa() {
		return nazwa;
	}


	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}


	public ArrayList<Integer> getIloœæ_wolnych() {
		return iloœæ_wolnych;
	}


	public void setIloœæ_wolnych(ArrayList<Integer> iloœæ_wolnych) {
		this.iloœæ_wolnych = iloœæ_wolnych;
	}



}
