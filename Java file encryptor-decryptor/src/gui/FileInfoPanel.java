package gui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileInfoPanel extends JPanel {

	private JLabel pathLabel;
	private JTextField pathTField;

	public FileInfoPanel() {
		setLayout(new FlowLayout());
		pathLabel = new JLabel("Path: ");
		pathTField = new JTextField();
		pathTField.setEditable(false);

		add(pathLabel);
		add(pathTField);
	}

	public JTextField getPathTField() {
		return pathTField;
	}

}
