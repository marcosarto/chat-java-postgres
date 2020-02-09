package client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeClient extends JFrame {

	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeClient frame = new HomeClient();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HomeClient() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 340);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		HomeClient c=this;
		
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
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Login frame = new Login(c);
					frame.setVisible(true);
					frame.setResizable(false);
					setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnLogin.setBackground(Color.WHITE);
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setFont(new Font("Comfortaa", Font.PLAIN, 10));
		btnLogin.setBounds(10, 290, 100, 21);
		contentPane.add(btnLogin);
		
		JButton btnRegister = new JButton("REGISTER");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Registration frame2 = new Registration(c);
					frame2.setVisible(true);
					frame2.setResizable(false);
					setVisible(false);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnRegister.setForeground(Color.BLACK);
		btnRegister.setFont(new Font("Comfortaa", Font.PLAIN, 10));
		btnRegister.setBackground(Color.WHITE);
		btnRegister.setBounds(120, 290, 100, 21);
		contentPane.add(btnRegister);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setIcon(new ImageIcon("Background.png"));
		lblNewLabel.setBounds(0, 0, 865, 320);
		contentPane.add(lblNewLabel);
	}
}
