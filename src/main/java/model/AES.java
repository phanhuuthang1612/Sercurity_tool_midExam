package model;

import java.nio.charset.Charset;
import java.util.Random;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AES implements ICippher {

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#^%*()|";

	private static SecretKeySpec setKey(String myKey) {
		MessageDigest sha = null;
		try {
			byte[] key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			return secretKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getKeySize() {
		// TODO Auto-generated method stub
		return 16;
	}

	@Override
	public String enCrypt(byte[] data, String key) throws WrongKeySizeException, InvalidInputException {
		if (key == null || key.isEmpty() || key.length() != 16)
			throw new WrongKeySizeException(getKeySize());
		SecretKeySpec s = setKey(key);
		Cipher cipher = null;
		byte[] b = null;
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, s);
			b = cipher.doFinal(data);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InvalidInputException();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WrongKeySizeException(getKeySize());
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InvalidInputException();
		}
		return Base64.getEncoder().encodeToString(b);

	}

	@Override
	public byte[] deCrypt(byte[] data, String key) throws WrongKeySizeException, InvalidInputException {
		if (key == null || key.isEmpty()|| key.length() != 16)
			throw new WrongKeySizeException(getKeySize());
		SecretKeySpec s = setKey(key);
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, s);
			return cipher.doFinal(data);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InvalidInputException();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InvalidInputException();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InvalidInputException();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InvalidInputException();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InvalidInputException();
		}
		return null;

	}

	@Override
	public void setKeySize(int sizeKey) {
		// TODO Auto-generated method stub

	}
}