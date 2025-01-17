package logic.encryptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {

	/**
	 * Method to encrypt a file with AES in CBC mode.
	 *
	 * @param key   128 bits key
	 * @param input input file
	 * @param out   output encrypted file
	 * @param iv    initialization vector
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public void AESEncrypt(byte[] key, File input, File out, byte[] iv)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException, BadPaddingException {
		// Specifications to generate the secret key
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		// cipher configuration wit the selected algorithm, mode, padding, iv and key
		// specification
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
		// open the input file stream
		FileInputStream fileInputStream = new FileInputStream(input);

		// define buffer block size to read the input
		int bufferSize = 128;
		byte[] buffer = new byte[bufferSize];

		// Set file out stream to append information (it had data before as the source
		// hash and Iv)
		FileOutputStream fileOutputStream = new FileOutputStream(out, true);
		// iterative reading of the input and encryption by blocks
		while ((bufferSize = Math.min(128, fileInputStream.available())) == 128) {
			fileInputStream.read(buffer);
			fileOutputStream.write(cipher.update(buffer));

		}
		// finish encryption and padding
		buffer = new byte[bufferSize];
		fileInputStream.read(buffer);
		fileOutputStream.write(cipher.doFinal(buffer));
		// close file I/O streams
		fileInputStream.close();
		fileOutputStream.close();
	}
}
