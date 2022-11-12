package huuthang.cryptotool;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.datatransfer.MimeTypeParseException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.activation.MimetypesFileTypeMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import io.WriteFile;
import model.CipherFactory;
import model.ICippher;
import model.InvalidInputException;
import model.WrongKeySizeException;
import ui.FileInput;
import ui.IInput;
import ui.IPanelRecipeChoose;
import ui.InputValueChangedCallback;
import ui.JTextFieldRegularPopupMenu;
import ui.MainComponent;
import ui.PanelAsymmetricKey;
import ui.PanelHash;
import ui.PanelSymmetricKey;
import ui.SystemClipboard;
import ui.TextInput;

public class ActionApp implements ListSelectionListener, KeyListener, MouseListener, InputValueChangedCallback {
	MainComponent mainComponent;
	CipherFactory cipherFactory = null;
	String currentAlgo;
	String type = "En";
	byte[] output = null;
	IPanelRecipeChoose panelRecipeChoose = null;

	public ActionApp(MainComponent mainComponent) throws Exception {
		this.mainComponent = mainComponent;
		cipherFactory = new CipherFactory();

	}

	private void doCrypto() {
		String key = panelRecipeChoose.getKey();//
		System.out.println(key);
		ICippher ci = null;
		try {
			ci = cipherFactory.chooseAlgo(currentAlgo);
			if (currentAlgo.equals("RSA")) {
				int keySize = ((PanelAsymmetricKey) panelRecipeChoose).getKeySize();
				ci.setKeySize(keySize);
			}
			if (mainComponent.input.getValue().length == 0) {
				mainComponent.textOutput.setText("");
				return;
			}
			String deEnType = panelRecipeChoose.getTypeDe_En();
			if (currentAlgo.equals("MD5")) {
				byte[] textIn = mainComponent.input.getValue();
				output = ci.enCrypt(textIn, key).getBytes();
				mainComponent.textOutput.setText(new String(output, "UTF-8"));

			} else if (deEnType.equals("De")) {
				byte[] textIn = null;
				try {
					System.out.println("de");
					textIn = Base64.getDecoder().decode(mainComponent.input.getValue());

				} catch (Exception e) {
					textIn = mainComponent.input.getValue();
				}
				output = ci.deCrypt(textIn, key);
				String text = new String(output, "UTF-8");
				mainComponent.textOutput.setText(text);
			} else if (panelRecipeChoose.getTypeDe_En().equals("En")) {
				System.out.println("en");
				byte[] textIn = mainComponent.input.getValue();
				System.out.println(ci);
				System.out.println(textIn);
				System.out.println(key);
				;
				output = ci.enCrypt(textIn, key).getBytes();
				mainComponent.textOutput.setText(new String(output, "UTF-8"));
			}
		} catch (IllegalArgumentException e1) {
			mainComponent.textOutput.setText("Invalid Input");
			e1.printStackTrace();
		} catch (InvalidInputException e1) {
			mainComponent.textOutput.setText("Invalid Input");
			e1.printStackTrace();
		} catch (WrongKeySizeException e1) {
			mainComponent.textOutput.setText(e1.getMessage());
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if (!e.getValueIsAdjusting()) {
			mainComponent.input.clearValue();
			mainComponent.textOutput.setText("");

			String p = mainComponent.jList.getSelectedValue();
//			opeChoose = p;
			System.out.println(p);
			if (panelRecipeChoose != null) {
				mainComponent.panelRecipe.remove(1);
			}
			if (p.equals("MD5")) {
//				panelRecipeChoose = new
				panelRecipeChoose = new PanelHash(p);
			} else if (p.equals("RSA")) {
				panelRecipeChoose = new PanelAsymmetricKey(p);
				panelRecipeChoose.setOnValueChangedCallback(this);

			} else {
				panelRecipeChoose = new PanelSymmetricKey(p);
				panelRecipeChoose.setOnValueChangedCallback(this);
			}

			mainComponent.panelRecipe.add(panelRecipeChoose.render(), BorderLayout.CENTER);
			mainComponent.revalidate();
			currentAlgo = p;
			mainComponent.input.setEnabled(true);
			mainComponent.textOutput.setEnabled(true);

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		doCrypto();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		String labelName = e.getComponent().getName();
		JFileChooser fc = new JFileChooser();
		int returnVal = -1;
		if (labelName.equals("OPEN")) {
			returnVal = fc.showOpenDialog(mainComponent);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				IInput in = new FileInput(file);
				mainComponent.input = in;
				// mainComponent.textOutput.setText(file.getAbsolutePath());
				mainComponent.panelInput.remove(1);
				mainComponent.panelInput.add(in.render(), BorderLayout.CENTER);
				mainComponent.validate();
				doCrypto();

			}
		} else if (labelName.equals("Save")) {
			if (output == null) {
				return;
			}
			returnVal = fc.showSaveDialog(mainComponent);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					File file = fc.getSelectedFile();

					FileOutputStream outputStream = new FileOutputStream(file);
					DataOutputStream dataOutStream = new DataOutputStream(new BufferedOutputStream(outputStream));
					dataOutStream.write(output);
					dataOutStream.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		} else if (labelName.equals("DEL")) {
			IInput in = new TextInput();
			in.setOnValueChangedCallback(this);
			mainComponent.input = in;
			mainComponent.textOutput.setText("");
			mainComponent.panelInput.remove(1);
			mainComponent.panelInput.add(in.render(), BorderLayout.CENTER);
			mainComponent.validate();
			doCrypto();
		} else if (labelName.equals("Copy")) {
			SystemClipboard.copy(mainComponent.textOutput.getText());
			JOptionPane.showMessageDialog(mainComponent, "Coppied");

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		mainComponent.fileInput.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mainComponent.fileOutput.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mainComponent.lbDeleteInput.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mainComponent.lbCopyOutput.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onValueChanged(byte[] value) {
		doCrypto();
	}

}
