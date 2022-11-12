package ui;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JTextArea;

public class TextInput implements IInput {
	private InputValueChangedCallback onValueChangedCallback;
	boolean isEnable;
	JTextArea textArea;

	public TextInput(String text) {
	init(text);
	}

	public TextInput() {
		init("");
	}
	
	private void init(String text) {
		this.onValueChangedCallback = null;
		this.isEnable = true;
		
		textArea = new JTextArea();
		Font boldFont = new Font(textArea.getFont().getName(), Font.PLAIN, textArea.getFont().getSize());
		textArea.setText(text);
		textArea.setFont(boldFont);
		textArea.setLineWrap(true);
		textArea.setEnabled(isEnable);
		textArea.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (onValueChangedCallback != null) {
					onValueChangedCallback.onValueChanged(text.getBytes());
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public JComponent render() {
		// TODO Auto-generated method stub
		return textArea;
	}

	@Override
	public byte[] getValue() {
		// TODO Auto-generated method stub
		return textArea.getText().getBytes();
	}

	@Override
	public void setOnValueChangedCallback(InputValueChangedCallback onValueChangedCallback) {
		this.onValueChangedCallback = onValueChangedCallback;
		
	}

	@Override
	public void clearValue() {
		// TODO Auto-generated method stub
		this.textArea.setText("");
	}

	@Override
	public void setEnabled(boolean isEnable) {
		// TODO Auto-generated method stub
		this.isEnable = isEnable;
		this.textArea.setEnabled(isEnable);
	}

	@Override
	public void setText(String text) {
		textArea.setText(text);
		
	}

}
