package client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;


public class Chat extends JFrame {

	private JPanel contentPane;
	private JTextArea chatTextArea;
	private JScrollPane chatScrollable;
	private JTextArea sendTextArea;
	private JList list;
	private ChatController chatController;


	public Chat(ChatController chatController) {

		this.chatController = chatController;

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 350);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 158, 223));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		chatTextArea = new JTextArea();
		chatTextArea.setBackground(Color.WHITE);
		chatTextArea.setFont(new Font("Verdana", Font.PLAIN, 12));
		chatScrollable = new JScrollPane(chatTextArea);
		chatScrollable.setBounds(10, 50, 320, 200);
		chatTextArea.setLineWrap(true);
		chatScrollable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatScrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(chatScrollable);

		sendTextArea = new JTextArea();
		sendTextArea.setFont(new Font("Verdana", Font.PLAIN, 12));
		JScrollPane sendTextScrollable = new JScrollPane(sendTextArea);
		sendTextScrollable.setBounds(10, 260, 250, 50);
		sendTextArea.setLineWrap(true);
		sendTextScrollable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sendTextScrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(sendTextScrollable);

		JLabel label = new JLabel("UNICHAT");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Comfortaa", Font.BOLD, 40));
		label.setBounds(10, 10, 320, 40);
		contentPane.add(label);

		JButton btnSend = new JButton("SEND");
		btnSend.setForeground(Color.BLACK);
		btnSend.setFont(new Font("Comfortaa", Font.PLAIN, 10));
		btnSend.setBackground(Color.WHITE);
		btnSend.setBounds(265, 260, 65, 50);
		contentPane.add(btnSend);

		list = new JList();
		list.setBackground(Color.WHITE);
		list.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		list.setFont(new Font("Comfortaa", Font.PLAIN, 15));
		list.setBounds(370, 50, 280, 200);
		contentPane.add(list);

		JLabel lblPeopleOnline = new JLabel("PEOPLE ONLINE");
		lblPeopleOnline.setHorizontalAlignment(SwingConstants.CENTER);
		lblPeopleOnline.setForeground(Color.WHITE);
		lblPeopleOnline.setFont(new Font("Comfortaa", Font.BOLD, 20));
		lblPeopleOnline.setBounds(410, 25, 200, 20);
		contentPane.add(lblPeopleOnline);

		JButton btnWrite = new JButton("WRITE");
		btnWrite.setForeground(Color.BLACK);
		btnWrite.setFont(new Font("Comfortaa", Font.PLAIN, 10));
		btnWrite.setBackground(Color.WHITE);
		btnWrite.setBounds(560, 260, 90, 50);
		contentPane.add(btnWrite);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("logo.png"));
		label_1.setBounds(463, 243, 100, 70);
		contentPane.add(label_1);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					chatController.closeSocket();
			}
		});

		btnSend.addActionListener(chatController.new SendHandler());

		btnWrite.addActionListener(chatController.new WriteHandler());
	}

	public String getTextToSend(){
		return sendTextArea.getText();
	}

	public void setTextToSend(String s){
		sendTextArea.setText(s);
	}

	public void aggiungiTesto(String msg){
		chatTextArea.append(msg+"\n");
	}

	public String getListSelectedValue(){
		return (String)list.getSelectedValue();
	}

	public void setArea(String area){
		chatTextArea.setText(area);
	}

	public String getArea(){
		return chatTextArea.getText();
	}


	public void aggiornaLista(String[] users){
		list.setModel(new AbstractListModel() {
			String[] values = users;

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
	}
}