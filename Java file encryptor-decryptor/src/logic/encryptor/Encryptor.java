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

	public void AESEncrypt(byte[] key, File input, File out, byte[] iv)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException, BadPaddingException {

		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
		FileInputStream fileInputStream = new FileInputStream(input);

		int bufferSize = 128;
		byte[] buffer = new byte[bufferSize];

		FileOutputStream fileOutputStream = new FileOutputStream(out, true);

		while ((bufferSize = Math.min(128, fileInputStream.available())) == 128) {
			fileInputStream.read(buffer);
			fileOutputStream.write(cipher.update(buffer));

		}
		buffer = new byte[bufferSize];
		fileInputStream.read(buffer);
		fileOutputStream.write(cipher.doFinal(buffer));

		fileInputStream.close();
		fileOutputStream.close();
	}
}
