package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import logic.encryptor.Encryptor;
import logic.filehash.SHA;
import logic.keygen.PBKDF2;

public class Control {

	private PBKDF2 pbkdf2 = new PBKDF2();
	private SHA sha = new SHA();
	private Encryptor encryptor = new Encryptor();

	public void encrypt(File input, String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[24];
		random.nextBytes(salt);
		byte[] key = pbkdf2.pbkdf2(password.toCharArray(), salt, 5000, 128);
		byte[] fileHash = sha.sha_1(input);

		File output = new File("../encrypted/" + input.getName() + ".cif");

		FileOutputStream fileOutputStream = new FileOutputStream(output);
		fileOutputStream.write(fileHash);
		fileOutputStream.close();

		encryptor.AESEncrypt(key, input, output, fileHash);
	}

}
