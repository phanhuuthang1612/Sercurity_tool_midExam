package model;

import gnu.crypto.cipher.Twofish;
import java.lang.reflect.Array;
import java.security.InvalidKeyException;
import java.util.Base64;

public class TwofishImp implements ICippher {
	/*
	 * public static void main(String argv[]) throws Exception { String key =
	 * "1234567812345678"; String plaintext = "I am text to be hidden away"; String
	 * cipher = encrypt(plaintext,key); System.out.println(cipher); String
	 * cplaintext = decrypt(cipher,key); System.out.println(cplaintext); }
	 */
	public static void main(String argv[]) throws Exception, WrongKeySizeException, InvalidInputException {
		String key = "12345678ãzcvsfd"; // 16 chu
		String plaintext = "I am text to be hidden away";
		
		TwofishImp t = new TwofishImp();
		
		byte[] data = plaintext.getBytes();
		String encryptedText = t.enCrypt(data, key);
		System.out.println(encryptedText);
		
		byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
		byte[] decryptedBytes = t.deCrypt(encryptedBytes, key);
		String decryptedText = new String(decryptedBytes, "UTF-8");
		System.out.println(decryptedText);
	}

	@Override
	public String enCrypt(byte[] data, String key) throws WrongKeySizeException, InvalidInputException {
		if (key == null || key.isEmpty()|| key.length() != 16)
			throw new WrongKeySizeException(getKeySize());
		byte[] plainText;
		byte[] encryptedText;
		Twofish twofish = new Twofish();
// create a key
		byte[] keyBytes = key.getBytes();
		Object keyObject = null;
		try {
			keyObject = twofish.makeKey(keyBytes, 16);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//make the length of the text a multiple of the block size
		if ((data.length % 16) != 0) {
			int deficiencyByteData = 16 - data.length % 16; // so byte thieu
			int sizeData = data.length;
			plainText = new byte[sizeData + deficiencyByteData];
			System.arraycopy(data, 0, plainText, 0, sizeData);
		} else {
			plainText = data;
		}
// initialize byte arrays for plain/encrypted text
		encryptedText = new byte[plainText.length];
// encrypt text in 8-byte chunks
		for (int i = 0; i < Array.getLength(plainText); i += 16) {
			twofish.encrypt(plainText, i, encryptedText, i, keyObject, 16);
		}
		String encryptedString = Base64.getEncoder().encodeToString(encryptedText);
		return encryptedString;
	}

	@Override
	public byte[] deCrypt(byte[] data, String key) throws WrongKeySizeException, InvalidInputException {
		if (key == null || key.isEmpty()|| key.length() != 16)
			throw new WrongKeySizeException(getKeySize());
		byte[] encryptedText;
		byte[] decryptedText;
		Twofish twofish = new Twofish();
//create the key
		byte[] keyBytes = key.getBytes();
		Object keyObject = null;
		try {
			keyObject = twofish.makeKey(keyBytes, 16);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//make the length of the string a multiple of
//the block size
		if ((data.length % 16) != 0) {
			int deficiencyByteData = 16 - data.length % 16; // so byte thieu
			int sizeData = data.length;
			encryptedText = new byte[sizeData + deficiencyByteData];
			System.arraycopy(data, 0, encryptedText, 0, sizeData);
		} else {
			encryptedText = data;
		}
//initialize byte arrays that will hold encrypted/decrypted
//text
//		encryptedText = Base64Coder.decodeLines(cookieValue);
		decryptedText = new byte[encryptedText.length];
//Iterate over the byte arrays by 16-byte blocks and decrypt.
		for (int i = 0; i < Array.getLength(encryptedText); i += 16) {
			twofish.decrypt(encryptedText, i, decryptedText, i, keyObject, 16);
		}
		return decryptedText;
	}

	@Override
	public int getKeySize() {
		// TODO Auto-generated method stub
		System.out.println("KEY SIZE 16");
		return 16;
	}

	@Override
	public void setKeySize(int sizeKey) {
		// TODO Auto-generated method stub
		
	}
}