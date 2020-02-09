package client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField nomeUtente;
	private JTextField password;

	public Login(HomeClient c) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 340);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("UNICHAT");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Comfortaa", Font.BOLD, 40));
		lblNewLabel_1.setBounds(20, 20, 200, 40);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblThinkSmartlyAct = new JLabel("Think smartly, act better.");
		lblThinkSmartlyAct.setForeground(Color.WHITE);
		lblThinkSmartlyAct.setFont(new Font("Comfortaa", Font.PLAIN, 12));
		lblThinkSmartlyAct.setBounds(40, 60, 160, 20);
		contentPane.add(lblThinkSmartlyAct);
		
		JLabel lblRegister = new JLabel("Insert your data:");
		lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegister.setForeground(Color.WHITE);
		lblRegister.setFont(new Font("Comfortaa", Font.BOLD, 20));
		lblRegister.setBounds(20, 90, 200, 20);
		contentPane.add(lblRegister);
		
		nomeUtente = new JTextField();
		JButton btnConfirm = new JButton("LOGIN");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				   String indirizzoServer = "localhost";
				   String user = nomeUtente.getText();
				   String psw = password.getText();

				   try {
				    Socket s = new Socket(indirizzoServer, 5000);
				    BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				    PrintWriter pr = new PrintWriter(s.getOutputStream());
				    
				    pr.println("login");
				    pr.println(user);
				    pr.println(psw);
				    pr.flush();

				    if (br.readLine().equalsIgnoreCase("ok")) {
				    	try {
							new ChatController(s,user,psw);
							setVisible(false);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
				    }
				    else
				     JOptionPane.showMessageDialog(null, "User not found.");
				   } catch (Exception exc) {
				    JOptionPane.showMessageDialog(null, "Connection failed.");
				   }
				
			}
		});
		btnConfirm.setBackground(Color.WHITE);
		btnConfirm.setForeground(Color.BLACK);
		btnConfirm.setFont(new Font("Comfortaa", Font.PLAIN, 10));
		btnConfirm.setBounds(10, 290, 100, 21);
		contentPane.add(btnConfirm);
		
		JButton btnBack = new JButton("UNDO");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					c.setVisible(true);
					setVisible(false);
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		btnBack.setForeground(Color.BLACK);
		btnBack.setFont(new Font("Comfortaa", Font.PLAIN, 10));
		btnBack.setBackground(Color.WHITE);
		btnBack.setBounds(120, 290, 100, 21);
		contentPane.add(btnBack);
		nomeUtente.setText("Username");
		nomeUtente.setToolTipText("");
		nomeUtente.setBackground(Color.WHITE);
		nomeUtente.setForeground(Color.LIGHT_GRAY);
		nomeUtente.setFont(new Font("Comfortaa", Font.PLAIN, 12));
		nomeUtente.setBounds(20, 125, 200, 20);
		contentPane.add(nomeUtente);
		nomeUtente.setColumns(10);
		nomeUtente.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				nomeUtente.setText("");
			}
		});
		
		password = new JTextField();
		password.setBackground(Color.WHITE);
		password.setText("Password");
		password.setForeground(Color.LIGHT_GRAY);
		password.setFont(new Font("Comfortaa", Font.PLAIN, 12));
		password.setColumns(10);
		password.setBounds(20, 155, 200, 20);
		contentPane.add(password);
		password.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				password.setText("");
			}
		});
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setIcon(new ImageIcon("Background.png"));
		lblNewLabel.setBounds(0, 0, 865, 320);
		contentPane.add(lblNewLabel);
	}
}