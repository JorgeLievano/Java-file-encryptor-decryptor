package gui;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import control.ActionController;

public class MainWindow extends JFrame {

	private ActionsPanel actionsPanel;
	private ActionController actionController;
	private HashPanel hashPanel;
	private FileInfoPanel fileInfoPanel;

	public MainWindow() {
		setSize(700, 300);
		setResizable(true);
		setTitle("File encrypter - decrypter :: Lievano Jorge");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		// Dimension dimpref = new Dimension(400, 200);

		actionsPanel = new ActionsPanel();
		hashPanel = new HashPanel();
		fileInfoPanel = new FileInfoPanel();

		add(actionsPanel, BorderLayout.NORTH);
		add(fileInfoPanel, BorderLayout.CENTER);
		add(hashPanel, BorderLayout.SOUTH);
	}

	public void asControllable(ActionController actionController) {
		this.actionController = actionController;
		actionsPanel.setController(this.actionController);
	}

	public File chooseFile() {
		JFileChooser chooser = new JFileChooser("./");
		int option = chooser.showOpenDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}

		throw new RuntimeException();
	}

	public void infoReporter(String info, int type) {
		switch (type) {
		case 0:
			JOptionPane.showMessageDialog(this, info, "Information", JOptionPane.INFORMATION_MESSAGE);
			break;
		case 1:
			JOptionPane.showMessageDialog(this, info, "Information", JOptionPane.ERROR_MESSAGE);
			break;
		}

	}

	public String requestPassword() {
		String password = JOptionPane.showInputDialog("Type a password");
		return password;
	}

	public void setDecryptedHash(String hash) {
		hashPanel.getDecryptedHashTfield().setText(hash);
	}

	public void setFileInfo(String path) {
		fileInfoPanel.getPathTField().setText(path);
		fileInfoPanel.updateUI();
	}

	public void setSourceHash(String hash) {
		hashPanel.getSourceHashTfield().setText(hash);
	}
}
