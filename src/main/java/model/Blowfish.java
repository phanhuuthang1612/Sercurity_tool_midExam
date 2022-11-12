package model;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Blowfish implements ICippher {
	public static void main(String[] args) throws NoSuchPaddingException, InvalidAlgorithmParameterException,
			NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
			WrongKeySizeException, InvalidInputException, UnsupportedEncodingException {
		Blowfish blowfish_text = new Blowfish();
		String enct = blowfish_text.enCrypt("Hello".getBytes(), "aaaaa");

		System.out.println(enct);

		byte[] encryptedBytes = Base64.getDecoder().decode(enct);
		byte[] decryptedBytes = blowfish_text.deCrypt(encryptedBytes, "aaaaa");
		String decryptedText = new String(decryptedBytes, "UTF-8");
		System.out.println(decryptedText);

	}

	@Override
	public int getKeySize() {
		// TODO Auto-generated method stub
		return 56;
	}

	@Override
	public void setKeySize(int sizeKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public String enCrypt(byte[] data, String key) throws WrongKeySizeException, InvalidInputException {
		Cipher cipher;
		byte[] encrypted = null;
		try {
			if (key.length() <= 4 || key.length() > 56) {
				throw new WrongKeySizeException("Key size length must from 4 to 56 byte");
			}
			cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "Blowfish"));
			encrypted = cipher.doFinal(data);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Base64.getEncoder().encodeToString(encrypted);

	}

	@Override
	public byte[] deCrypt(byte[] data, String key) throws WrongKeySizeException, InvalidInputException {

		Cipher cipher;
		byte[] decrypted = null;
		try {
			if (key.length() <= 4 || key.length() > 56) {
				throw new WrongKeySizeException("Key size length must from 4 to 56 byte");
			}
			cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "Blowfish"));
			decrypted = cipher.doFinal(data);

		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decrypted;

	}
}