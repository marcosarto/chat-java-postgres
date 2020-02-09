package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Server {
	//L'arrayList clientHandlers contiene in ogni momento tutti gli utenti connessi alla chat
	private Map<String, ClientHandler> clientHandlers = new HashMap<>();
	private DatabaseModel database;

	public Server(int port, int nConnessioni) {
		try {
			database = new DatabaseModel();
			ServerSocket serverSocket = new ServerSocket(port, nConnessioni);
			Socket s;
			while (true) {
				s = serverSocket.accept();
				System.out.println("Accetto client");
				ClientHandler clientHandler = new ClientHandler(s);
				Thread t = new Thread(clientHandler);
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Errore creazione server");
		}
	}

	private void removeFromHandlers(String user) {
		clientHandlers.remove(user);
		System.out.println(user + " e` uscito, rimangono " + clientHandlers.size());
	}

	private void tellEveryOne(String msg) {
		System.out.println("Stampo messaggio ricevuto");
		for (ClientHandler clientHandler : clientHandlers.values()) {
			clientHandler.inviaMessaggioAlClient(msg);
		}
	}

	/*
	 * Per ogni client viene creata una classe ClientHandler che controlla quando il client scrive qualcosa e lo inoltra
	 * a tutti gli altri client connessi a questo server
	 * */
	class ClientHandler implements Runnable {
		BufferedReader bufferedReader;
		PrintWriter printWriter;
		Socket socket;
		String username;

		private ClientHandler(Socket socket) {
			this.socket = socket;
			try {
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				printWriter = new PrintWriter(socket.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {

			try {
				if (!handShake()) {
					inviaMessaggioAlClient("err"); //Notifico il client dell'errore
					return; //Credenziali errate o primo messaggio non conforme al protocollo termino la connessione
				} else {
					clientHandlers.put(username, this);
					inviaUtentiConnessi();
				}

				//Arrivati a questo punto login o registrazione sono andati a buon fine, l'utente inizia a interagire
				String msg;

				while ((msg = bufferedReader.readLine()) != null) {
					msg = Emoji.checkForEmojis(msg); // sostituisce emoticons con emojis
					System.out.println(msg);


					if (msg.startsWith("/users"))
						inviaUtentiConnessi();
					else {
						String toSend;
						String toSendMittente;
						String[] messaggioDiviso = msg.substring(1).split("!");
						DateFormat dateFormat = new SimpleDateFormat("HH:mm");
						Date date = new Date();
						if (messaggioDiviso[0].equals("HOME")) {
							toSend = String.format("&%s![ %s ] %s : %s",
									messaggioDiviso[0],
									dateFormat.format(date),
									username,
									messaggioDiviso[1]);
							tellEveryOne(toSend);
						} else if (clientHandlers.containsKey(messaggioDiviso[0])) {
							toSend = String.format("&%s![ %s ] %s : %s",
									username,
									dateFormat.format(date),
									username,
									messaggioDiviso[1]);
							toSendMittente = String.format("&%s![ %s ] %s : %s",
									messaggioDiviso[0],
									dateFormat.format(date),
									username,
									messaggioDiviso[1]);
							clientHandlers.get(messaggioDiviso[0]).inviaMessaggioAlClient(toSend);
							inviaMessaggioAlClient(toSendMittente);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			//In una chiusura orderly leggendo lo stream in entrata con readLine viene restituito null
			//con read verrebbe restituito -1, quindi quando esce dal while il client ha chiuso il socket
			removeFromHandlers(username); //rimuovo il client dagli ascoltatori
			inviaUtentiConnessi();
			tellEveryOne("&HOME! >> L'utente " + username + " ha lasciato la chat");

		}

		private boolean handShake() throws IOException, SQLException {
			String option;

			option = bufferedReader.readLine();

			switch (option) {
				case "login":
					return loginClient();
				case "registrazione":
					return registrazione();
				default:
					return false;
			}
		}

		private boolean loginClient() throws IOException, SQLException {

			//Il protocollo con cui comunicano client e server prevede che le prime due stringhe inviate dal client
			//siano username e passowrd, la prima stringa del server informera` il client dell'esito di login
			username = bufferedReader.readLine();
			String password = bufferedReader.readLine();

			if (database.login(username, password)) {
				inviaMessaggioAlClient("ok");
				tellEveryOne("&HOME! >> L'utente " + username + " si e` unito alla chat");
				return true;
			}

			return false;
		}

		private boolean registrazione() throws IOException, SQLException {
			username = bufferedReader.readLine();
			String password = bufferedReader.readLine();

			if (database.registrazione(username, password)) {
				inviaMessaggioAlClient("ok");
				tellEveryOne("&HOME! >> L'utente " + username + " si e` unito alla chat");
				return true;
			}
			return false;
		}

		private void inviaUtentiConnessi() {
			for (ClientHandler c : clientHandlers.values()) {
				for (String user : clientHandlers.keySet()) {

					c.inviaMessaggioAlClient("#" + user);
				}
				c.inviaMessaggioAlClient("#-");
			}
		}

		private void inviaMessaggioAlClient(String msg) {
			System.out.println(msg);
			printWriter.println(msg);
			printWriter.flush();
		}

	}
}

