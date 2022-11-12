package huuthang.cryptotool;

import java.awt.EventQueue;

import ui.UI;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		new UI();
	}
}
