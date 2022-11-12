package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class PanelAsymmetricKey implements IPanelRecipeChoose, KeyListener, ActionListener {
	private JPanel pAsymmetric;
	private JTextArea jfprivateKey, jfpublicKey;
	private String publicKey;
	private String privateKey;
	InputValueChangedCallback onValueChangedCallback;
	private String typeDe_En;
	private String typeKey;
	private static String keySize;
	JComboBox<Object> jcbSizeKey;

	public int getKeySize() {
		return Integer.parseInt(keySize.split(" ")[0]);
	}

	public void generator() throws Exception {

		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(getKeySize());
		KeyPair kp = kpg.generateKeyPair();

		byte[] pri = kp.getPrivate().getEncoded();
		byte[] pub = kp.getPublic().getEncoded();

		System.out.println(kp.getPrivate().getFormat());
		System.out.println(kp.getPublic().getFormat());

		publicKey = Base64.getEncoder().encodeToString(pub);
		privateKey = Base64.getEncoder().encodeToString(pri);

	}

	public PanelAsymmetricKey(String algo) {
		typeDe_En = "En";
		typeKey = "public";
		onValueChangedCallback = null;
		pAsymmetric = new JPanel();
		pAsymmetric.setLayout(new BorderLayout());

		JPanel panelGenerateKey = new JPanel();
		panelGenerateKey.setLayout(new BorderLayout());
		JPanel sizeKey_BtnGenKey = new JPanel();

		String titleAlgoKeysize = "Select " + algo + " Key Size: ";
		JLabel lbSizeKey = new JLabel(titleAlgoKeysize);
		String[] sizeKey = { "515 bit", "1024 bit", "2048 bit" };
		jcbSizeKey = new JComboBox<Object>(sizeKey);
		jcbSizeKey.addActionListener(this);
		jcbSizeKey.setActionCommand("KeySize");
		jcbSizeKey.setSelectedIndex(2);
		sizeKey_BtnGenKey.setLayout(new GridLayout(3, 1));
		sizeKey_BtnGenKey.add(lbSizeKey);
		sizeKey_BtnGenKey.add(jcbSizeKey);

		String btnGenKey = "Generate " + algo + " Key Pair";
		JButton gen = new JButton(btnGenKey);
		gen.setActionCommand("Gen");
		sizeKey_BtnGenKey.add(gen);

		System.out.println(sizeKey_BtnGenKey.getWidth());
		JPanel pKey = new JPanel();
		pKey.setLayout(new GridLayout(2, 1));

		jfprivateKey = new JTextArea(5, 10);
		Font boldFont = new Font(jfprivateKey.getFont().getName(), Font.PLAIN, jfprivateKey.getFont().getSize());
		jfprivateKey.setFont(boldFont);
		JScrollPane spPrivateKey = new JScrollPane(jfprivateKey);
		JPanel pPrivate = new JPanel();
		pPrivate.setLayout(new BorderLayout());
		pPrivate.add(new JLabel("Private Key"), BorderLayout.NORTH);
		pPrivate.add(spPrivateKey, BorderLayout.CENTER);
		jfprivateKey.addKeyListener(this);
		jfprivateKey.setName("pri");
		jfpublicKey = new JTextArea(5, 10);
		Font b = new Font(jfpublicKey.getFont().getName(), Font.PLAIN, jfpublicKey.getFont().getSize());
		jfpublicKey.setFont(b);
		jfpublicKey.setName("pub");
		JScrollPane spPublicKey = new JScrollPane(jfpublicKey);
		JPanel pPublic = new JPanel();
		pPublic.setLayout(new BorderLayout());
		pPublic.add(new JLabel("Public Key"), BorderLayout.NORTH);
		pPublic.add(spPublicKey, BorderLayout.CENTER);
		pPublic.addKeyListener(this);

		pKey.add(pPrivate);
		pKey.add(pPublic);

		panelGenerateKey.add(sizeKey_BtnGenKey, BorderLayout.NORTH);
		panelGenerateKey.add(pKey, BorderLayout.CENTER);
		panelGenerateKey.setBackground(Color.blue);
		JPanel typeAndKey = new JPanel();
//		typeAndKey.set
		JRadioButton de = new JRadioButton("Decrypt");
		de.setActionCommand("De");
		de.addActionListener(this);
		JRadioButton en = new JRadioButton("Encrypt");
		en.setActionCommand("En");
		en.setSelected(true);
		en.addActionListener(this);

		ButtonGroup btn = new ButtonGroup();
		btn.add(de);
		btn.add(en);

		JPanel type = new JPanel();

		type.add(en);
		type.add(de);

		JPanel typeKey = new JPanel();
		typeKey.add(new JLabel("RSA Key Type: "));

		JRadioButton publicK = new JRadioButton("Public Key");
		publicK.setActionCommand("public");
		publicK.addActionListener(this);
		JRadioButton privateK = new JRadioButton("Private Key");
		privateK.setActionCommand("private");
		privateK.addActionListener(this);
		publicK.setSelected(true);

		ButtonGroup btnTypeKey = new ButtonGroup();
		btnTypeKey.add(publicK);
		btnTypeKey.add(privateK);

		typeKey.add(publicK);
		typeKey.add(privateK);

		JPanel key = new JPanel();
		key.setLayout(new BorderLayout());
		key.add(new JLabel("Enter Public/Private key"), BorderLayout.NORTH);
		JTextArea enterKey = new JTextArea(5, 10);
		JScrollPane spEnterKey = new JScrollPane(enterKey);

		key.add(spEnterKey, BorderLayout.CENTER);
		JPanel chooseBtn = new JPanel();
		chooseBtn.setLayout(new BorderLayout());
		chooseBtn.add(type, BorderLayout.NORTH);
		chooseBtn.add(typeKey, BorderLayout.WEST);

		typeAndKey.setLayout(new BorderLayout());
		typeAndKey.add(chooseBtn, BorderLayout.NORTH);
//		typeAndKey.add(key, BorderLayout.CENTER);
		pAsymmetric.add(panelGenerateKey, BorderLayout.NORTH);
		pAsymmetric.add(typeAndKey, BorderLayout.CENTER);
		pAsymmetric.setBorder(new EmptyBorder(10, 10, 10, 10));

		gen.addActionListener(this);
	}

	@Override
	public JComponent render() {
		// TODO Auto-generated method stub
		return pAsymmetric;
	}

	@Override
	public String getTypeDe_En() {
		// TODO Auto-generated method stub
		return typeDe_En;
	}

	@Override
	public String getKey() {
		System.out.println("gọi...");
		System.out.println("key type " + typeKey);
		if (typeKey.equals("public")) {
			return "Pub" + publicKey;
		} else if (typeKey.equals("private")) {
			return "Pri" + privateKey;
		}
		return null;
	}

	@Override
	public void setOnValueChangedCallback(InputValueChangedCallback onValueChangedCallback) {
		// TODO Auto-generated method stub
		this.onValueChangedCallback = onValueChangedCallback;

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
		if (e.getComponent().getName().equals("pri")) {
			if (onValueChangedCallback != null) {
				onValueChangedCallback.onValueChanged(jfprivateKey.getText().getBytes());
			}
		} else if (e.getComponent().getName().equals("pub")) {
			if (onValueChangedCallback != null) {
				onValueChangedCallback.onValueChanged(jfpublicKey.getText().getBytes());
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		if (name.equals("Gen")) {

			try {
				generator();
				jfpublicKey.setText(PanelAsymmetricKey.this.publicKey);
				jfprivateKey.setText(PanelAsymmetricKey.this.privateKey);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (name.equals("De") || name.equals("En")) {
			if (onValueChangedCallback != null) {
				onValueChangedCallback.onValueChanged(null);
			}
			typeDe_En = name;
		} else if (name.equals("public") || name.equals("private")) {
			if (onValueChangedCallback != null) {
				onValueChangedCallback.onValueChanged(null);
			}

			typeKey = name;
		} else if (name.equals("KeySize")) {
			keySize = jcbSizeKey.getSelectedItem().toString();
			System.out.println("key size: " + Integer.parseInt(keySize.split(" ")[0]));
		}
		System.out.println("Type de en: " + typeDe_En);
		System.out.println("typeKey: " + typeKey);
	}

}
