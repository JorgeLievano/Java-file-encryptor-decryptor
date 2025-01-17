package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import logic.decryptor.Decryptor;
import logic.encryptor.Encryptor;
import logic.filehash.SHA;
import logic.keygen.PBKDF2;

public class Control {

	public final int SALT_SIZE = 24; // size in bytes
	public final int IV_SIZE = 16; // size in bytes
	public final int HASH_SHA1_SIZE = 20;// size in bytes

	private PBKDF2 pbkdf2 = new PBKDF2();
	private SHA sha = new SHA();
	private Encryptor encryptor = new Encryptor();
	private Decryptor decryptor = new Decryptor();

	/**
	 * Method to calculate de SHA1 hash of a input file
	 *
	 * @param input source file to calculate de hash
	 * @return byte array representation of the SHA1 hash
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public byte[] calculateSHA1(File input) throws NoSuchAlgorithmException, IOException {
		byte[] fileHash = sha.sha_1(input);
		return fileHash;
	}

	/**
	 * Method to create default directories to manage encrypted and decrypted files
	 * generated by the tool.
	 */
	public void createDirectories() {
		try {
			Files.createDirectories(Paths.get("./encrypted"));
			Files.createDirectories(Paths.get("./decrypted"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method to decrypt a given file input
	 *
	 * @param input    encrypted file
	 * @param password password given by user
	 * @param outPath  path to write the decrypted file
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchPaddingException
	 */
	public void decrypt(File input, String password, String outPath)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
		// open the input file stream to read data
		FileInputStream fileInputStream = new FileInputStream(input);
		byte[] salt = new byte[SALT_SIZE];
		byte[] iv = new byte[IV_SIZE];

		// skip the hash data
		fileInputStream.skip(HASH_SHA1_SIZE);
		// read salt bytes to decryption
		fileInputStream.read(salt);
		// read iv bytes to decryption
		fileInputStream.read(iv);
		// close file input stream to allow decryption
		fileInputStream.close();

		// generate sec key with PBKDF2 and the salt param
		byte[] key = pbkdf2.pbkdf2(password.toCharArray(), salt, 5000, 128);

		// decrypt file
		File output = new File(outPath);
		decryptor.aes_cbc_decrypt(key, input, output, iv, HASH_SHA1_SIZE + IV_SIZE + SALT_SIZE);
	}

	/**
	 * Method to generate the needed data to encrypt a file
	 *
	 * @param input    origin file
	 * @param password string given by user
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public void encrypt(File input, String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		// use secure random to generate values
		SecureRandom random = new SecureRandom();
		// generate salt
		byte[] salt = new byte[24];
		random.nextBytes(salt);
		// generate Sec key with PBKDF2
		byte[] key = pbkdf2.pbkdf2(password.toCharArray(), salt, 5000, 128);
		// generate a iv
		byte[] iv = new byte[16];
		random.nextBytes(iv);
		// set out file name
		File output = new File("./encrypted/" + input.getName() + ".cif");
		// calculate de SHA1 hash
		byte[] fileHash = sha.sha_1(input);
		// open the out file stream
		FileOutputStream fileOutputStream = new FileOutputStream(output);
		// write the hash bytes in the output file before encrypting data
		fileOutputStream.write(fileHash);
		// write the salt bytes in the output file before encrypting data
		fileOutputStream.write(salt);
		// write the iv bytes in the output file before encrypting data
		fileOutputStream.write(iv);
		// close the out file stream to allow the encryption
		fileOutputStream.close();

		// encrypt the file
		encryptor.AESEncrypt(key, input, output, iv);
	}

	/**
	 * Method to get the hash byte array of the origin file in the encrypted file
	 *
	 * @param input encrypted file
	 * @return byte array representation of the hash
	 * @throws IOException
	 */
	public byte[] getSHA1FromEncrypted(File input) throws IOException {
		byte[] hash = new byte[20];
		FileInputStream fileInputStream = new FileInputStream(input);
		fileInputStream.read(hash);
		fileInputStream.close();
		return hash;
	}

	/**
	 * Convert a byte array representation of a hash in a hex representation in
	 * string format
	 *
	 * @param hash byte array
	 * @return hex string
	 */
	public String hashToString(byte[] hash) {
		return sha.hashByteToString(hash);
	}

}
