package sdpwj.gui;

import java.io.Serializable;
import java.util.ArrayList;

class Przedmioty implements Serializable{
	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

public ArrayList<Przedmiot> przedmioty;	
		
		public Przedmioty( ArrayList<Przedmiot> przedmioty) {
			this.przedmioty = przedmioty;
		
	}

		@Override
		public String toString() {
			return "A [przedmioty=" + przedmioty + "]";
		}

		
	}