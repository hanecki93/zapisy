package jdbc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PlikDoTablicy {
	public static ArrayList<String> toArray(String fileName) throws IOException {
		String str;
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"));
		ArrayList<String> tab = new ArrayList<String>();
		while ((str = in.readLine()) != null) {

			if (!Character.isLetter(str.charAt(0))) {
				str = str.substring(1);
			}

			tab.add(str);
		}
		in.close();
		return tab;
	}

}
