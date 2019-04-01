package sdpwj.gui;

import javax.swing.JFrame;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ZapisyServerGUI {
	public static int token = 30;
	public static int najmniej_miejsc = 30;
	public static int najwiecej_miejsc = 0;
	// uruchomienie serwera odbywaj¹ce siê w tle
	Runnable r = new Runnable() {
		public void run() {
			int port = 3000;
			ServerSocket serverSocket = null;
			try {
				// tworzymy socket
				serverSocket = new ServerSocket(port);
				ExecutorService exec = Executors.newCachedThreadPool();
				while (true) {
					// czekamy na zg³oszenie klienta ...
					Socket socket = serverSocket.accept();
					// tworzymy w¹tek dla danego po³¹czenia i uruchamiamy go
					exec.execute(new ServerTCPCall(socket).getFt());
				}
			} catch (Exception e) {
				System.err.println(e);
			} finally {
				if (serverSocket != null)
					try {
						serverSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}

		}
	};

	// utwo¿enie listy przedmiotow
	public ArrayList<Przedmiot> przedmioty = new ArrayList<Przedmiot>();
	int i = 0;
	private JFrame frmServer;
	private JTextField textField;
	static Przedmioty t;
	JButton btnUruchom = new JButton("Uruchom");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ZapisyServerGUI window = new ZapisyServerGUI();
					window.frmServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ZapisyServerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmServer = new JFrame();
		frmServer.setResizable(false);
		frmServer.setTitle("Server");
		frmServer.setBounds(100, 100, 450, 300);
		frmServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServer.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Utw\u00F3rz");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// laczanie z baza danych
				jdbc.LadowanieSterownika.ladujSterownik();

				Connection connection = jdbc.Polaczenie.getConnection("localhost", 3306);
				Statement st = jdbc.OperacjeBazowe.createStatement(connection);

				if (connection != null) {
					System.out.print(" polaczenie OK\n");

					// utworzenie bazy
					if (jdbc.OperacjeBazowe.executeUpdate(st, "create Database zapisy;") != -1)
						System.out.println("Baza utworzona");
					else
						System.out.println("Baza nieutworzona!");

					if (jdbc.OperacjeBazowe.executeUpdate(st, "USE zapisy;") != -1)
						System.out.println("Baza wybrana");
					else
						System.out.println("Baza niewybrana!");

					int liczbaPrzedmiotow = Integer.valueOf(textField.getText());

					try {
						for (int x = 1; x <= liczbaPrzedmiotow; x++) {
							// obiekty pomocnicze dla przedmiotu
							Map<Integer, String> godziny = new TreeMap<>();
							ArrayList<Integer> iloœæ_wolnych = new ArrayList<Integer>();

							String nazwa = "p" + x + ".txt";
							ArrayList<String> s = jdbc.PlikDoTablicy.toArray(nazwa);
							int i = 1;
							String tabele = "";

							int rozmiar = 0;
							while (i < s.size()) {
								// wypelnienie mapy przdmiotow wartosciami
								String nast_pozycja = s.get(i);
								godziny.put(i, nast_pozycja);
								String liczba_wolnych = nast_pozycja.substring(nast_pozycja.length() - 2);
								rozmiar = Integer.parseInt(liczba_wolnych);

								// Wyliczenie liczby rekordow juz stworzonych
								int val = 0;
								ResultSet z = jdbc.OperacjeBazowe.executeQuery(st,
										"select  count(`" + nast_pozycja + "`)  from " + s.get(0) + " ;");
								if (z.next()) {
									val = ((Number) z.getObject(1)).intValue();
								}

								iloœæ_wolnych.add(rozmiar - val);
								if ((token > (rozmiar - val)) && ((rozmiar - val) > 0)) {
									token = rozmiar - val;
								}
								najmniej_miejsc = token;

								if ((rozmiar - val) > najwiecej_miejsc) {
									najwiecej_miejsc = rozmiar - val;
								}

								tabele += s.get(i) + "  VARCHAR(50),";
								i++;
							}
							System.out.println(token);
							tabele = tabele.substring(0, tabele.length() - 1);
							// tworzenie obiektu przedmiot dla danego przedmiotu

							Przedmiot temp = new Przedmiot(s.get(0), godziny, iloœæ_wolnych);

							if (jdbc.OperacjeBazowe.executeUpdate(st,
									"CREATE TABLE " + s.get(0) + "(" + tabele + ");") != -1) {
								System.out.println("Tabela utworzona");
								przedmioty.add(temp);
								for (int y = 0; y < rozmiar; y++) {
									jdbc.OperacjeBazowe.executeUpdate(st,
											" INSERT INTO " + s.get(0) + " ( " + s.get(1) + " ) VALUES ( null);");

								}
							}

							else {
								System.out.println("Tabela nie utworzona!");
								przedmioty.add(temp);
							}
						}
						System.out.println(przedmioty);
						t = new Przedmioty(przedmioty);
						btnUruchom.setEnabled(true);
						btnNewButton.setEnabled(false);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Statement s = jdbc.OperacjeBazowe.createStatement(connection);
					/*
					 * String sql = "Select * from angielski"; ResultSet r =
					 * jdbc.OperacjeBazowe.executeQuery(s, sql);
					 * jdbc.OperacjeBazowe.printDataFromQuery(r);
					 */

					// zamkniecie polaczenia
					jdbc.Zamkniecie.closeConnection(connection, s);
				}

			}
		});
		btnNewButton.setBounds(30, 30, 89, 23);
		frmServer.getContentPane().add(btnNewButton);

		JLabel lblLiczbaPrzedmiot = new JLabel("Liczba przedmiot\u00F3w:");
		lblLiczbaPrzedmiot.setBounds(10, 11, 122, 14);
		frmServer.getContentPane().add(lblLiczbaPrzedmiot);

		textField = new JTextField();
		textField.setBounds(141, 8, 29, 20);
		frmServer.getContentPane().add(textField);
		textField.setColumns(10);

		btnUruchom.setEnabled(false);

		btnUruchom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnUruchom.setEnabled(false);
				ExecutorService executor = Executors.newCachedThreadPool();
				executor.submit(r);
			}
		});
		btnUruchom.setBounds(30, 60, 89, 23);
		frmServer.getContentPane().add(btnUruchom);
	}
}
