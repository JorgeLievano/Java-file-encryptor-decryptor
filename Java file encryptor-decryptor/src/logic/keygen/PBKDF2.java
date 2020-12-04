package logic.keygen;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PBKDF2 {
	/**
	 * Method to generate a key with the PBKDF2 algorithm and HmacSHA1 as
	 * pseudorandom function
	 *
	 * @param password password given by the user
	 * @param salt     random byte sequence
	 * @param c        number of iterations
	 * @param dklen    desired lenght of the key
	 * @return byte array of the key generated
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public byte[] pbkdf2(char[] password, byte[] salt, int c, int dklen)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		PBEKeySpec keySpec = new PBEKeySpec(password, salt, c, dklen);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey key = keyFactory.generateSecret(keySpec);
		return key.getEncoded();
	}
}
