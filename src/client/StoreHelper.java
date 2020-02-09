package client;

import javax.swing.*;

public class StoreHelper {

	private JTextArea chatArea;
	private JScrollPane scrollPane;

	public StoreHelper(){
		chatArea = new JTextArea();
		scrollPane = new JScrollPane(chatArea);
		chatArea.setLineWrap(true);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chatArea.setVisible(true);
		chatArea.setText("");
	}

	public void aggiungi(String msg){
		chatArea.append(msg+"\n");
	}

	public String getText(){
		return chatArea.getText();
	}

	public void setText(String c){
		chatArea.setText(c);
	}

}
