package model;

import java.io.File;

public interface ICippher {
	public int getKeySize();

	public void setKeySize(int sizeKey);

	public String enCrypt(byte[] data, String key) throws WrongKeySizeException, InvalidInputException;

	public byte[] deCrypt(byte[] data, String key) throws WrongKeySizeException, InvalidInputException;

}
