package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FileInput implements IInput {
	private File file;
	private URL pathImg;

	public FileInput(File file) {
		this.file = file;
		pathImg = this.getClass().getResource("/file.png");
	}

	@Override
	public void setOnValueChangedCallback(InputValueChangedCallback onValueChangedCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public JComponent render() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		try {
			String mimetype = new MimetypesFileTypeMap().getContentType(file);
			String type = mimetype.split("/")[0];
			if(type.equals("image")) {
				Image image = ImageIO.read(file);
				if (image == null) {
					image = ImageIO.read(this.getClass().getResource("/file.png"));
				}
				ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_DEFAULT));
				panel.add(new JLabel(imageIcon), BorderLayout.WEST);
				Path source = Paths.get(file.getAbsolutePath());
				String typeFile = Files.probeContentType(source);

				String infoFile = "<html>Name: " + file.getName() + "<br>" + "Size: " + file.length() + " bytes<br>"
						+ "Type: " + typeFile + "<html>";
				panel.add(new JLabel(infoFile), BorderLayout.CENTER);
			}else {
				Path source = Paths.get(file.getAbsolutePath());
				String typeFile = Files.probeContentType(source);

				String infoFile = "<html>Name: " + file.getName() + "<br>" + "Size: " + file.length() + " bytes<br>"
						+ "Type: " + typeFile + "<html>";
				panel.add(new JLabel(infoFile), BorderLayout.CENTER);
			}
			
		} catch (IOException ex) {

		}
		return panel;
	}

	@Override
	public byte[] getValue() {
		try {
			FileInputStream inputStream = new FileInputStream(file);
			byte[] inputBytes = new byte[(int) file.length()];
			inputStream.read(inputBytes);
			inputStream.close();
			System.out.println(inputBytes.length);
			return inputBytes;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void clearValue() {
		file = null;
	}

	@Override
	public void setEnabled(boolean isEnable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		
	}



}
