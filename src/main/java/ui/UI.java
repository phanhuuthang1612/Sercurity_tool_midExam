package ui;

import javax.swing.JFrame;

public class UI extends JFrame {
	private MainComponent sw;

	public UI() throws Exception {
		sw = new MainComponent();
		this.add(sw);
		setTitle("Crypt-Tool");
		setDefaultCloseOperation(3);
		setResizable(true);
		setSize(1000, 600);
		setLocationRelativeTo(null);
	//	setVisible(true);
		setResizable(false);

	}

}
