package ui;

import javax.swing.JComponent;

public interface IInput {
	
	public void setOnValueChangedCallback(InputValueChangedCallback onValueChangedCallback);
	
	public JComponent render();

	public byte[] getValue();
	
	public void clearValue();
	
	public void setEnabled(boolean isEnable);
	public void setText(String text) ;
}
