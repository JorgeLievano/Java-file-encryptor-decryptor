package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainWindow extends JFrame {

	private ActionsPanel actionsPanel;

	public MainWindow() {
		setSize(400, 300);
		setResizable(false);
		setTitle("File encrypter - decrypter :: Lievano Jorge");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		Dimension dimpref = new Dimension(400, 200);

		actionsPanel = new ActionsPanel();

		JPanel s = new JPanel();
		s.setLayout(new GridLayout(2, 2));
		JLabel sourceHashLabel = new JLabel("Source Hash: ");
		JTextField sourceHashTfield = new JTextField();
		sourceHashTfield.setEditable(false);
		JLabel decryptedHashLabel = new JLabel("Decrypted Hash: ");
		JTextField decryptedHashTfield = new JTextField();
		decryptedHashTfield.setEditable(false);
		s.add(sourceHashLabel);
		s.add(sourceHashTfield);
		s.add(decryptedHashLabel);
		s.add(decryptedHashTfield);

		add(actionsPanel, BorderLayout.NORTH);
		add(s, BorderLayout.SOUTH);
	}

}
