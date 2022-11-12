package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import helper.Helper;
import model.CipherFactory;
import model.ICippher;

public class PanelSymmetricKey implements IPanelRecipeChoose, KeyListener, ActionListener {
	JPanel panelOperation, panelRecipe, panelDe_EnCrypt, panelKey, panelIV, p223, p224, panelIn_Output, panelInput,
			panelOutput, panelOperaChoose, panelBtnDe_En, panelRecipeChoose;
	JLabel labelOpe;
	JRadioButton btnDecrypt, btnEncrypt;
	JTextField tfKey;
	JButton btnGenerateKey;
	String type;
	String key;
	String type_De_En;
	CipherFactory cipherFactory = null;
	InputValueChangedCallback onValueChangedCallback;

	public PanelSymmetricKey(String type) {
		this.type = type;
		onValueChangedCallback = null;
		cipherFactory = new CipherFactory();
		init();
	}

	private String generator() throws Exception {

		ICippher cipher = cipherFactory.chooseAlgo(type);
		if (type.equals("RC4") || type.equals("BLOWFISH")) {
			Random rd = new Random();
			int sizeKey = rd.nextInt(cipher.getKeySize() - 10) + 8;
			return Helper.randomString(sizeKey);

		}
		return Helper.randomString(cipher.getKeySize());
	}

	private void init() {
		type_De_En = "En";

		panelRecipeChoose = new JPanel();
		panelRecipeChoose.setLayout(new BorderLayout());

		panelOperaChoose = new JPanel();
		panelOperaChoose.setLayout(new GridLayout(3, 1));

		btnDecrypt = new JRadioButton("Decrypt");
		btnDecrypt.setActionCommand("De");
		btnDecrypt.addActionListener(this);

		btnEncrypt = new JRadioButton("Encrypt");
		btnEncrypt.setSelected(true);
		btnEncrypt.setActionCommand("En");
		btnEncrypt.addActionListener(this);

		panelBtnDe_En = new JPanel();
		panelBtnDe_En.add(btnEncrypt);
		panelBtnDe_En.add(btnDecrypt);

		panelRecipeChoose.add(panelOperaChoose, BorderLayout.NORTH);
		panelDe_EnCrypt = new JPanel();

//		panelDe_EnCrypt.setBackground(Color.GRAY);
		panelDe_EnCrypt.setLayout(new BorderLayout());

		panelKey = new JPanel();
		panelKey.setLayout(new BorderLayout());
		tfKey = new JTextField();
		tfKey.addKeyListener(this);

		JLabel key = new JLabel("Key");
		panelKey.add(key, BorderLayout.NORTH);
		key.setFont(new Font("Helvetica", Font.PLAIN, 12));
		panelKey.add(tfKey, BorderLayout.CENTER);
//		String[] typeEncrypt = { "UTF8", "HEX", "BAS64" };
//		typeKey = new JComboBox(typeEncrypt);
		JPanel k = new JPanel();
		k.setLayout(new BorderLayout());
		k.setBackground(Color.green);
		btnGenerateKey = new JButton("Gennerate");
		btnGenerateKey.setActionCommand("Gen");
		btnGenerateKey.addActionListener(this);
		// k.add(typeKey, BorderLayout.WEST);
		k.add(btnGenerateKey, BorderLayout.EAST);
		panelKey.add(k, BorderLayout.EAST);

		panelDe_EnCrypt.add(panelKey, BorderLayout.NORTH);
//		panelIV = new JPanel();
//		panelIV.setLayout(new BorderLayout());
//		JLabel IV = new JLabel("IV");
//		IV.setFont(new Font("Helvetica", Font.PLAIN, 12));
//		panelIV.add(IV, BorderLayout.NORTH);
//		tfIV = new JTextField();
//		panelIV.add(tfIV, BorderLayout.CENTER);
//		typeIV = new JComboBox(typeEncrypt);
//		panelIV.add(typeIV, BorderLayout.EAST);

//		panelDe_EnCrypt.add(panelIV, BorderLayout.CENTER);
		ButtonGroup btn = new ButtonGroup();
		btn.add(btnDecrypt);
		btn.add(btnEncrypt);

		tfKey.setText("");
		labelOpe = new JLabel();
		panelOperaChoose.add(labelOpe);
		panelOperaChoose.add(panelBtnDe_En);
		panelOperaChoose.add(panelDe_EnCrypt);
	}

	@Override
	public JComponent render() {

		System.out.println(type);
		labelOpe.setText(type);

		return panelOperaChoose;
	}

	@Override
	public String getTypeDe_En() {
		// TODO Auto-generated method stub
		return type_De_En;
	}

	public void setOnValueChangedCallback(InputValueChangedCallback onValueChangedCallback) {
		this.onValueChangedCallback = onValueChangedCallback;

	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return tfKey.getText();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String t = e.getActionCommand();
		try {
			ICippher cipher = cipherFactory.chooseAlgo(type);
			if (t.equals("Gen")) {
				System.out.println(cipher);
				key = generator();
				System.out.println("key action: " + key);
				tfKey.setText(key);
			} else {
				type_De_En = t;
				if (onValueChangedCallback != null) {
					onValueChangedCallback.onValueChanged(null);
				}

			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (onValueChangedCallback != null) {
			onValueChangedCallback.onValueChanged(tfKey.getText().getBytes());
		}

	}

}
