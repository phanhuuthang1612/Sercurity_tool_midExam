package ui;

import javax.swing.*;

import helper.Helper;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

public class JListFilterOperation {
	public static JList<String> jList;

	public static JPanel listOperation() {
		List<String> opera = OperationDataAccess.getAlgo();
		DefaultListModel<String> model = new DefaultListModel<>();
		opera.forEach(model::addElement);
		jList = new JList<String>(model);
		jList.setCellRenderer(createListRenderer());
		JPanel panel = JListFilterDecorator.decorate(jList, JListFilterOperation::algoFilter);
		return panel;
	}

	public JList<String> getJList() {
		return jList;
	}

	private static boolean algoFilter(String emp, String str) {
		return emp.toLowerCase().contains(str.toLowerCase());
	}

	private static ListCellRenderer createListRenderer() {
		return new DefaultListCellRenderer() {
			private Color background = new Color( 255,255,102);
			private Color defaultBackground = (Color) UIManager.get("List.background");

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (c instanceof JLabel) {
					JLabel label = (JLabel) c;

					String emp = (String) value;
					label.setToolTipText("<html><p width=\"350px\">" + Helper.getProperty(emp) + "</p></html>");
					label.setText(emp);
					if (!isSelected) {
						label.setBackground(index % 2 == 0 ? background : defaultBackground);
					}
				}
				return c;
			}
		};
	}
}