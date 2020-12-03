package logic.filehash;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {

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
