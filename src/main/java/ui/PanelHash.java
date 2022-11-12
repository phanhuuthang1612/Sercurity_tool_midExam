package ui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelHash implements IPanelRecipeChoose {
	String type;
	JPanel p;

	public PanelHash(String type) {
		// TODO Auto-generated constructor stub
		this.type = type;
		p = new JPanel();
	}

	@Override
	public JComponent render() {
		p.setLayout(new BorderLayout());
		JLabel lb = new JLabel(type);
		p.add(lb, BorderLayout.WEST);
		return p;
	}

	@Override
	public String getTypeDe_En() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnValueChangedCallback(InputValueChangedCallback onValueChangedCallback) {
		// TODO Auto-generated method stub
		
	}

}
