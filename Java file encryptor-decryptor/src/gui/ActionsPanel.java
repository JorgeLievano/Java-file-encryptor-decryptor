package gui;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ActionsPanel extends JPanel {

	public final static String FILE = "FILE";
	public final static String ENCRYPT = "ENCRYPT";
	public final static String DECRYPT = "DECRYPT";
	private JButton fileBtn;
	private JButton encryptBtn;
	private JButton decryptBtn;

	public ActionsPanel() {

		fileBtn = new JButton(FILE);

		encryptBtn = new JButton(ENCRYPT);

		decryptBtn = new JButton(DECRYPT);

		add(fileBtn);

		add(encryptBtn);
		add(decryptBtn);
	}

}
