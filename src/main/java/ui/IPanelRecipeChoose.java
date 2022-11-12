package ui;

import javax.swing.JComponent;

public interface IPanelRecipeChoose {
	public JComponent render();

	public String getTypeDe_En();

	public String getKey();

	public void setOnValueChangedCallback(InputValueChangedCallback onValueChangedCallback);

}
