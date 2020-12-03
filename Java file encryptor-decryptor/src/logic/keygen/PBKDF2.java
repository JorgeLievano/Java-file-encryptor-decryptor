package logic.keygen;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PBKDF2 {

	public byte[] pbkdf2(char[] password, byte[] salt, int c, int dklen)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		PBEKeySpec keySpec = new PBEKeySpec(password, salt, c, dklen);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey key = keyFactory.generateSecret(keySpec);
		return key.getEncoded();
	}
}
