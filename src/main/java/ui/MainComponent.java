package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.TextUI;

import huuthang.cryptotool.ActionApp;

public class MainComponent extends JPanel {
	public JPanel panelOperation, panelRecipe, panelIn_Output, panelInput, panelOutput;
	public JButton btnGenerateKey;
	public JLabel fileInput, fileOutput, lbDeleteInput, lbCopyOutput;
	public JRadioButton btnDecrypt, btnEncrypt;
	public JTextField tfKey, tfIV;
	public JComboBox<String> typeKey, typeIV;
	public JTextArea textOutput;
	public JLabel labelOpe, labelOperation;
	public JList<String> jList;
	public IInput input;

	public MainComponent() throws Exception {
		ActionApp action = new ActionApp(this);

		panelOperation = new JPanel();
		labelOperation = new JLabel("Operations");
		panelOperation.setLayout(new BorderLayout());
		panelOperation.add(labelOperation, BorderLayout.NORTH);
		panelOperation.setBackground(Color.yellow);
// add list operation		
		JListFilterOperation jlistOpera = new JListFilterOperation();
		panelOperation.add(jlistOpera.listOperation(), BorderLayout.CENTER);
		jList = jlistOpera.getJList();
		String opeChoose = "";

		// panel 2
		panelRecipe = new JPanel();
		panelRecipe.setLayout(new BorderLayout());
		JLabel labelRecipe = new JLabel("Recipe:");
		panelRecipe.add(labelRecipe, BorderLayout.NORTH);
		// panel render fllow cipher

		jList.getSelectionModel().addListSelectionListener(action);

		// panel 3
		GridLayout gl3 = new GridLayout();
		panelIn_Output = new JPanel();
		panelIn_Output.setLayout(new GridLayout(2, 1));
		panelInput = new JPanel();
		panelIn_Output.add(panelInput, gl3);
		panelInput.setLayout(new BorderLayout());
		JLabel labelInput = new JLabel("Input");
		JPanel panelLabelInput = new JPanel();
		panelLabelInput.setLayout(new BorderLayout());
	//	fileInput = new JLabel(new ImageIcon("openFile.png"), SwingConstants.RIGHT);
		fileInput = new JLabel("Open File", SwingConstants.RIGHT);
		fileInput.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		fileInput.setToolTipText("Choose file");
		fileInput.setName("OPEN");
		fileInput.addMouseListener(action);
	//	lbDeleteInput = new JLabel(new ImageIcon("delete.png"), SwingConstants.LEFT);
		lbDeleteInput = new JLabel("Delete",SwingConstants.LEFT);
		lbDeleteInput.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		lbDeleteInput.setToolTipText("Delete");
		lbDeleteInput.setName("DEL");
		lbDeleteInput.addMouseListener(action);
		JPanel lbInput = new JPanel();
		lbInput.add(lbDeleteInput);
		lbInput.add(fileInput);
		panelLabelInput.add(labelInput, BorderLayout.WEST);
		panelLabelInput.add(lbInput, BorderLayout.EAST);
		panelInput.add(panelLabelInput, BorderLayout.NORTH);

		input = new TextInput();
		input.setOnValueChangedCallback(action);
		input.setEnabled(false);
		JScrollPane areaScrollPaneIn = new JScrollPane(input.render());
		areaScrollPaneIn.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPaneIn.setPreferredSize(new Dimension(50, 50));
		panelInput.add(areaScrollPaneIn, BorderLayout.CENTER);
		panelOutput = new JPanel();
		panelIn_Output.add(panelOutput, gl3);
		panelOutput.setLayout(new BorderLayout());
		JLabel labelOutput = new JLabel("Output");
		
	//	lbCopyOutput
	//	fileOutput = new JLabel(new ImageIcon("saveFile.png"), SwingConstants.RIGHT);
		fileOutput = new JLabel("Save", SwingConstants.RIGHT);
		fileOutput.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		fileOutput.setName("Save");
		fileOutput.setToolTipText("Save file");
		fileOutput.addMouseListener(action);
		lbCopyOutput = new JLabel("Copy",SwingConstants.LEFT);
		lbCopyOutput.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		lbCopyOutput.setToolTipText("Copy");
		lbCopyOutput.setName("Copy");
		lbCopyOutput.addMouseListener(action);
		JPanel panelOutput2 =new JPanel();
		panelOutput2.add(fileOutput);
		panelOutput2.add(lbCopyOutput);
		JPanel panelLabelOutput = new JPanel();
		panelLabelOutput.setLayout(new BorderLayout());
		panelLabelOutput.add(labelOutput, BorderLayout.WEST);
		panelLabelOutput.add(panelOutput2, BorderLayout.EAST);
		panelOutput.add(panelLabelOutput, BorderLayout.NORTH);
		textOutput = new JTextArea();
		Font boldFont = new Font(textOutput.getFont().getName(), Font.PLAIN, textOutput.getFont().getSize());
		textOutput.setFont(boldFont);
//		textOutput.setLineWrap(true);
		textOutput.setEnabled(false);
		JScrollPane areaScrollPaneOut = new JScrollPane(textOutput);
//		areaScrollPaneOut.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		areaScrollPaneOut.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panelOutput.add(areaScrollPaneOut, BorderLayout.CENTER);

		GridLayout gl = new GridLayout(1, 3);
		this.setLayout(gl);
		this.add(panelOperation, gl);
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(panelRecipe, BorderLayout.NORTH);
		this.add(p, gl);
		this.add(panelIn_Output, gl);

	}

}
