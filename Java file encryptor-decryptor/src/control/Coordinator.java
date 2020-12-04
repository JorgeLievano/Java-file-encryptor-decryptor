package control;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import gui.MainWindow;

public class Coordinator {

	private MainWindow mainWindow;
	private ActionController actionController;
	private Control control;

	private File input;

	public Coordinator() {
		mainWindow = new MainWindow();

		actionController = new ActionController();
		mainWindow.asControllable(actionController);
		control = new Control();
	}

	/**
	 * load file from file explorer and set in the state
	 */
	public void chooseFile() {
		input = mainWindow.chooseFile();
		mainWindow.setFileInfo(input.getAbsolutePath());
	}

	/**
	 * Compose the needed data and methods to decrypt the input file in the state
	 */
	public void decrypt() {
		// verify input is not null
		if (input == null) {
			mainWindow.infoReporter("You must to load a file", 1);
		} else {
			// request for user password
			String password = mainWindow.requestPassword();
			// set the output file name
			String outName = input.getName().replaceFirst(".cif", "");
			String outPath = "./decrypted/" + outName;

			try {
				// decrypt file
				control.decrypt(input, password, outPath);
				// get and compare hashs from source encrypte file and decrypted out file
				File decrypted = new File(outPath);
				byte[] decryptedHash = control.calculateSHA1(decrypted);
				byte[] encryptedSourceHash = control.getSHA1FromEncrypted(input);
				String encryptHashString = control.hashToString(encryptedSourceHash);
				String decryptHashString = control.hashToString(decryptedHash);

				mainWindow.setSourceHash(encryptHashString);
				mainWindow.setDecryptedHash(decryptHashString);
				String msg = "Decryption finished.";
				// show if file is trustworthy
				if (encryptHashString.equals(decryptHashString)) {
					msg += " The file keep its integrity";
				} else {
					msg += " The file looks not secure, it could be modified";
				}
				mainWindow.infoReporter(msg, 0);

			} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException
					| IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException
					| NoSuchPaddingException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Initialize components
	 */
	public void deploy() {
		actionController.setCoordinator(this);
		control.createDirectories();
		mainWindow.setVisible(true);
	}

	/**
	 * method to compose the needed data and methods to encrypt the input file
	 */
	public void encrypt() {
		// verify the input is not null
		if (input == null) {
			mainWindow.infoReporter("You must to load a file", 1);
		} else {
			// request to the user for a source password
			String password = mainWindow.requestPassword();
			try {
				// genrate de encrypted file
				control.encrypt(input, password);
				mainWindow.infoReporter("The encryption finished", 0);
			} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
					| IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
