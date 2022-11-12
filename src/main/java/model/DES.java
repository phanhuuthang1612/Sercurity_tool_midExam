package model;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class DES implements ICippher {

	@Override
	public String enCrypt(byte[] data, String key) throws WrongKeySizeException, InvalidInputException {
		if (key == null || key.isEmpty())
			throw new WrongKeySizeException(getKeySize());
		byte[] cipherText = null;
		try {
			Cipher cipher = Cipher.getInstance("DES");
			SecretKeySpec k = new SecretKeySpec(key.getBytes(), "DES");
			cipher.init(Cipher.ENCRYPT_MODE, k);
			cipherText = cipher.doFinal(data);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InvalidInputException();
		}

		catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WrongKeySizeException(getKeySize());
		}

		catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InvalidInputException();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InvalidInputException();
		}
//		System.out.println(cipherText);
		return Base64.getEncoder().encodeToString(cipherText);
	}

	@Override
	public byte[] deCrypt(byte[] data, String key) throws WrongKeySizeException, InvalidInputException {
		if (key == null || key.isEmpty())
			throw new WrongKeySizeException(getKeySize());

		try {
			System.out.println(key);
			Cipher cipher = Cipher.getInstance("DES");
			SecretKeySpec k = new SecretKeySpec(key.getBytes(), "DES");
			cipher.init(Cipher.DECRYPT_MODE, k);
			return cipher.doFinal(data);
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

		} catch (IllegalArgumentException e) {
			// TODO: handle exception
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
	public int getKeySize() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public void setKeySize(int sizeKey) {
		// TODO Auto-generated method stub

	}

}
