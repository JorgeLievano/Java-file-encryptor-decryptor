package gui;

import javax.swing.JButton;
import javax.swing.JPanel;

import control.ActionController;

public class ActionsPanel extends JPanel {

	private JButton fileBtn;
	private JButton encryptBtn;
	private JButton decryptBtn;
	private ActionController controller;

	public ActionsPanel() {
		fileBtn = new JButton(ActionController.FILE);

		encryptBtn = new JButton(ActionController.ENCRYPT);

		decryptBtn = new JButton(ActionController.DECRYPT);

		add(fileBtn);
		add(encryptBtn);
		add(decryptBtn);
	}

	public void setController(ActionController controller) {
		this.controller = controller;
		fileBtn.addActionListener(controller);
		encryptBtn.addActionListener(controller);
		decryptBtn.addActionListener(controller);
	}

}
