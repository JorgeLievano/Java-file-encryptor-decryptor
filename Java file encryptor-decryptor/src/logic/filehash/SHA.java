package logic.filehash;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {
	/**
	 * Allow convert a byte array representation of a file's SHA1 hash to an hex
	 * string representation.
	 *
	 * @param hash byte array hash.
	 * @return hash hex representation in string format.
	 */
	public String hashByteToString(byte[] hash) {
		String s = "";

		for (byte b : hash) {
			s += String.format("%02X", b);
		}
		return s;

	}

	/**
	 * Method to calculate the SHA1 hash of a file.
	 *
	 * @param input source file.
	 * @return byte array representation of the hash.
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public byte[] sha_1(File input) throws NoSuchAlgorithmException, IOException {

		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		try (InputStream inputStream = new FileInputStream(input);
				DigestInputStream digestInputStream = new DigestInputStream(inputStream, digest)) {
			while (digestInputStream.read() != -1) {

			}
		}
		return digest.digest();
	}

}
