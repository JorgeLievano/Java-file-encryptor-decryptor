package gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HashPanel extends JPanel {

	private JLabel sourceHashLabel;
	private JTextField sourceHashTfield;
	private JLabel decryptedHashLabel;
	private JTextField decryptedHashTfield;

	public HashPanel() {
		setLayout(new GridLayout(2, 2));
		sourceHashLabel = new JLabel("Source Hash: ");
		sourceHashTfield = new JTextField();
		sourceHashTfield.setEditable(false);
		decryptedHashLabel = new JLabel("Decrypted Hash: ");
		decryptedHashTfield = new JTextField();
		decryptedHashTfield.setEditable(false);
		add(sourceHashLabel);
		add(sourceHashTfield);
		add(decryptedHashLabel);
		add(decryptedHashTfield);
	}

	public JTextField getDecryptedHashTfield() {
		return decryptedHashTfield;
	}

	public JTextField getSourceHashTfield() {
		return sourceHashTfield;
	}
}
