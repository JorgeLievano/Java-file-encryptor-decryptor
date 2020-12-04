package logic.decryptor;

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

public class Decryptor {
	/**
	 * Methof to decrypt a file with aes in cbc mode with the given secret key
	 *
	 * @param key   key generated with PKDF2
	 * @param input file input
	 * @param out   file output
	 * @param iv    initialization vector
	 * @param off   bytes to skip
	 * @throws IOException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public void aes_cbc_decrypt(byte[] key, File input, File out, byte[] iv, int off)
			throws IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
		// Specifications to generate the secret key
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		// iv parameter
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		// decrypter cipher mode configuration with the selected algorithm, mode,
		// padding, iv and key specification
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
		//// open the input file stream
		FileInputStream fileInputStream = new FileInputStream(input);
		// skip heads bytes that contained hash, salt and iv
		fileInputStream.skip(off);
		// set reading buffer block
		int bufferSize = 128;
		byte[] buffer = new byte[bufferSize];

		// open the file output stream to write decrypted data
		FileOutputStream fileOutputStream = new FileOutputStream(out);

		// Iterative reading and decrypt by blocks
		while ((bufferSize = Math.min(128, fileInputStream.available())) == 128) {
			fileInputStream.read(buffer);
			fileOutputStream.write(cipher.update(buffer));

		}
		// finish decryption and padding
		buffer = new byte[bufferSize];
		fileInputStream.read(buffer);
		fileOutputStream.write(cipher.doFinal(buffer));
		// close file I/O streams
		fileInputStream.close();
		fileOutputStream.close();
	}

}
