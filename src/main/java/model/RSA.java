package model;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class RSA implements ICippher {
	static SecureRandom srandom = new SecureRandom();
	private int keySize = 2024;

	@Override
	public int getKeySize() {
		// TODO Auto-generated method stub
		System.out.println("RSA keySize" + keySize);
		return keySize;
	}

	public PrivateKey readPrivateKey(String key)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, WrongKeySizeException {
		if (key == null || key.isEmpty() || key.length() != keySize)
			throw new WrongKeySizeException(keySize);
		PrivateKey privateKey = null;
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key.getBytes()));
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			privateKey = keyFactory.generatePrivate(keySpec);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return privateKey;

	}

	public PublicKey readPublicKey(String key)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, WrongKeySizeException {
		if (key == null || key.isEmpty() || key.length() != keySize)
			throw new WrongKeySizeException(keySize);

		PublicKey publicKey = null;

		try {
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(key.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(keySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return publicKey;
	}

	private boolean isPub(String key) {
		return key.substring(0, 3).equals("Pub");
	}

	private String getKey(String text) {
		return text.substring(3, text.length());
	}

	@Override
	public String enCrypt(byte[] data, String textkey) throws WrongKeySizeException, InvalidInputException {
		byte[] obuf = null;

		try {
			Key pvt;
			if (!isPub(textkey)) {
				pvt = readPrivateKey(getKey(textkey));
			} else {
				pvt = readPublicKey(getKey(textkey));
			}

			KeyGenerator key = KeyGenerator.getInstance("AES");
			key.init(128);
			SecretKey skey = key.generateKey();
			byte[] iv = new byte[128 / 8];
			srandom.nextBytes(iv);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);

			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pvt);
			byte[] o1 = cipher.doFinal(skey.getEncoded());

			System.err.println("IV length: " + iv.length);
			Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ci.init(Cipher.ENCRYPT_MODE, skey, ivSpec);

			System.out.println("data length: " + data.length);

			byte[] o2 = ci.update(data, 0, data.length);
			if (o2 == null)
				o2 = new byte[0];
			byte[] o3 = ci.doFinal();
			if (o3 == null)
				o3 = new byte[0];

			int length = o1.length + iv.length + o2.length + o3.length;
			System.out.println("o1 length: " + o1.length);
			System.out.println("iv length: " + iv.length);
			System.out.println("o2 length: " + o2.length);
			System.out.println("o3 length: " + o3.length);

			obuf = new byte[length];
			System.arraycopy(o1, 0, obuf, 0, o1.length);
			System.arraycopy(iv, 0, obuf, o1.length, iv.length);
			System.arraycopy(o2, 0, obuf, o1.length + iv.length, o2.length);
			System.arraycopy(o3, 0, obuf, o1.length + iv.length + o2.length, o3.length);
			System.out.println("Out length: " + obuf.length);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
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
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Base64.getEncoder().encodeToString(obuf);
	}

	@Override
	public byte[] deCrypt(byte[] data, String textkey) throws WrongKeySizeException, InvalidInputException {

		byte[] obuf = null;
		System.out.println(data.length);

		try {
			Key pub;
			if (isPub(textkey)) {
				pub = readPublicKey(getKey(textkey));
			} else {
				pub = readPrivateKey(getKey(textkey));
			}

			SecretKeySpec skey = null;

			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, pub);

			byte keyB[] = cipher.doFinal(data, 0, 256);
			skey = new SecretKeySpec(keyB, "AES");

			IvParameterSpec ivSpec = new IvParameterSpec(data, 256, 128 / 8);
			Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ci.init(Cipher.DECRYPT_MODE, skey, ivSpec);

			byte[] o1 = ci.update(data, 272, data.length - 272);
			if (o1 == null)
				o1 = new byte[0];
			byte[] o2 = ci.doFinal();
			if (o2 == null)
				o2 = new byte[0];

			int length = o1.length + o2.length;

			obuf = new byte[length];
			System.arraycopy(o1, 0, obuf, 0, o1.length);
			System.arraycopy(o2, 0, obuf, o1.length, o2.length);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println(data);
		System.out.println(textkey);
		System.out.println("res: " + obuf);

		return obuf;
	}

	@Override
	public void setKeySize(int sizeKey) {
		keySize = sizeKey;

	}
}
