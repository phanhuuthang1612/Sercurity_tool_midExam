package model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 implements ICippher {

	@Override
	public int getKeySize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String enCrypt(byte[] data, String key) throws WrongKeySizeException, InvalidInputException {
		// TODO Auto-generated method stub

		MessageDigest complete;
		String res = null;
		try {
			complete = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = complete.digest(data);

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public byte[] deCrypt(byte[] data, String key) throws WrongKeySizeException, InvalidInputException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKeySize(int sizeKey) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		MD5 m = new MD5();
		try {
			String t = m.enCrypt("123".getBytes(), "");
			System.out.println(t);
		} catch (WrongKeySizeException | InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
