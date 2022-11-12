package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WelcomeScreen extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeScreen frame = new WelcomeScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WelcomeScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 613, 300);
		setSize(600, 400);
		setLocationRelativeTo(null);
	//	setVisible(true);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel lblSubjectName = new JLabel("CRYTOGRAPHY AND INFORMATION SECURITY");
		lblSubjectName.setBackground(new Color(240, 240, 240));
		lblSubjectName.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel.add(lblSubjectName);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		
		JLabel lblStraight = new JLabel("_________________________________________________________________________________________________");
		panel_1.add(lblStraight);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2);
		
		JLabel lblWelcome = new JLabel("WELCOME TO CRYTO TOOL");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_2.add(lblWelcome);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3);
		
		JButton btnStart = new JButton("  Start  ");
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dispose();
					new UI().setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_3.add(btnStart);
	}

}
