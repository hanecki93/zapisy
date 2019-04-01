package sdpwj.gui;


import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ServerTCPCall implements Callable<String> {
	Socket mySocket;
	FutureTaskCallback<String> ft;

	public ServerTCPCall(Socket socket) {
		mySocket = socket;
		ft = new FutureTaskCallback<String>(this);
	}

	public FutureTaskCallback<String> getFt() {
		return ft;
	}

	@Override
	public String call() throws IOException {
		String txt = mySocket.getInetAddress().getHostName();

		InetAddress clientAddress = mySocket.getInetAddress();



		String clientAddressString = clientAddress.getHostAddress();
		System.out.println(clientAddressString);

		// wyslanie obiektu
		ObjectOutputStream ous;
		try {
			if((ZapisyServerGUI.token>0) && (ZapisyServerGUI.najwiecej_miejsc>0)) {
			ous = new ObjectOutputStream(mySocket.getOutputStream());
			ous.writeObject(ZapisyServerGUI.t);
			ZapisyServerGUI.token--;}
			else
				mySocket.close();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			// pobieranie obiektu

			Wybory w = new Wybory(null, null);
			while (w.nazwisko == null) {
				ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
				w = ((Wybory) ois.readObject());
			}

			// updatowanie bazy danych
			Connection connection2 = jdbc.Polaczenie.getConnection("localhost", 3306);
			Statement st2 = jdbc.OperacjeBazowe.createStatement(connection2);

			if (connection2 != null)
				System.out.print(" polaczenie OK\n");
			if (jdbc.OperacjeBazowe.executeUpdate(st2, "USE zapisy;") != -1) {
				System.out.println("Baza wybrana");

				for (int x = 0; x < w.godziny.size(); x++) {
					String nazwa = ZapisyServerGUI.t.przedmioty.get(x).nazwa;
					String godzina = w.godziny.get(x);
					String nazwisko = w.nazwisko;

					if (jdbc.OperacjeBazowe.executeUpdate(st2,
							"UPDATE " + nazwa + " SET " + godzina + "='" + nazwisko + "' WHERE "+ godzina  +" IS NULL LIMIT 1;") != -1) {
						
						
						//uaktualnienie tablicy z iloscia wolnych miejsc
						for(int a=1;a<=ZapisyServerGUI.t.przedmioty.get(x).iloœæ_wolnych.size();a++) {
						if(ZapisyServerGUI.t.przedmioty.get(x).godziny.get(a).equals(godzina) == true)
						{
							int d = ZapisyServerGUI.t.przedmioty.get(x).iloœæ_wolnych.get(a-1);
							d--;
							ZapisyServerGUI.t.przedmioty.get(x).iloœæ_wolnych.set(a-1, d);
							if(ZapisyServerGUI.najmniej_miejsc>d)
							{
								ZapisyServerGUI.najmniej_miejsc--;
								ZapisyServerGUI.token--;
							}
						}
						

						}
				


						System.out.println("Update wykonany");
					}

					else
						System.out.println("Blad");

				}
			} else
				System.out.println("Baza niewybrana!");

			mySocket.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		ZapisyServerGUI.token++;
		return "Socket " + txt + " is closed.";
	}
}

/**
 * @author S³awek Klasa rozsze¿aj¹ca klasê FutureTask
 * @param <T>
 */
class FutureTaskCallback<T> extends FutureTask<T> {
	public FutureTaskCallback(Callable<T> callable) {
		super(callable);
	}

	/**
	 * Metoda uruchamiana po zakoñczeniu wykonywania zadania
	 */
	public void done() {
		String msg = "Wynik: ";
		if (isCancelled())
			msg += "Anulowane.";
		else {
			try {
				msg += get();
			} catch (Exception exc) {
				msg += exc.toString();
			}
		}
		System.out.println("\n" + msg + "\n");
	}
}
