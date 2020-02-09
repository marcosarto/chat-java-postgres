package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatController implements Runnable {
	Chat c;
	Socket s;
	String user;
	String password;
	String destinatario = "&HOME";
	String precDestinatario = null;
	SendHelper sendHelper;
	private Map<String, StoreHelper> store = new HashMap<>();
	private BufferedReader bufferedReader;

	public ChatController(Socket s, String user, String password) {
		this.s = s;
		sendHelper = new SendHelper();
		try {

			sendHelper.setPw(new PrintWriter(s.getOutputStream()));
			bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}

		StoreHelper h = new StoreHelper();
		store.put("&HOME", h);

		c = new Chat(this);
		c.setVisible(true);
		this.user = user;
		this.password = password;

		Thread t = new Thread(this);
		t.start();
	}

	public void closeSocket() {
		try {
			s.close();
		} catch (IOException e) {
		}
	}

	public void cambiaChat(String destinatario) {
		precDestinatario = this.destinatario;
		this.destinatario = destinatario;
	}

	public void run() {
		String recived;

		while (true) {
			try {

				if ((recived = bufferedReader.readLine()) != null) {
					System.out.println(recived);
					if (recived.startsWith("&")) {
						String[] messaggioDiviso = recived.split("!");
						if (messaggioDiviso[0].equals(destinatario)) {
							c.aggiungiTesto(messaggioDiviso[1]);
						} else if (store.containsKey(messaggioDiviso[0])) {
							store.get(messaggioDiviso[0]).aggiungi(messaggioDiviso[1]);
							if (!messaggioDiviso[0].equals("&HOME")) {
								if (destinatario.equals("&HOME"))
									c.aggiungiTesto
											(">> Hai ricevuto un messaggio da : " + messaggioDiviso[0].substring(1));
								else
									store.get("&HOME").aggiungi
											(">> Hai ricevuto un messaggio da : " + messaggioDiviso[0].substring(1));
							}
						} else {
							StoreHelper s = new StoreHelper();
							s.aggiungi(messaggioDiviso[1]);
							store.put(messaggioDiviso[0], s);
							if (!messaggioDiviso[0].equals("&HOME")) {
								if (destinatario.equals("&HOME"))
									c.aggiungiTesto
											(">> Hai ricevuto un messaggio da : " + messaggioDiviso[0].substring(1));
								else
									store.get("&HOME").aggiungi
											(">> Hai ricevuto un messaggio da : " + messaggioDiviso[0].substring(1));
							}
						}
					} else if (recived.startsWith("#")) {
						recived = recived.substring(1);
						ArrayList<String> users = new ArrayList<>();
						users.add("HOME");
						do {
							if(!recived.equals(user)&&!recived.equals("-"))
								users.add(recived);
							recived = bufferedReader.readLine().substring(1);
						} while (!recived.equals("-"));
						c.aggiornaLista(users.toArray(new String[users.size()]));
					}
				}
			} catch (IOException e) {
			}
		}
	}

	class SendHandler implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			String toSend = c.getTextToSend();
			sendHelper.setDest(destinatario);
			sendHelper.send(toSend);
			c.setTextToSend("");
		}
	}

	class RefreshHandler implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			sendHelper.sendSenzaDesinatario("/users");
		}
	}

	class WriteHandler implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String destinatario = "&" + c.getListSelectedValue();
			cambiaChat(destinatario);
			if (store.containsKey(destinatario)) {
				store.get(precDestinatario).setText(c.getArea());
				c.setArea(store.get(destinatario).getText());
			} else {
				store.get(precDestinatario).setText(c.getArea());
				StoreHelper h = new StoreHelper();
				store.put(destinatario, h);
				c.setArea(h.getText());
			}
		}
	}
}
