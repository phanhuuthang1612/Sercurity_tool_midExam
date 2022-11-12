package model;

public class WrongKeySizeException extends Throwable {
	String message;

	public WrongKeySizeException(String message) {
		this.message = message;
	}

	public WrongKeySizeException(int keySize) {
		this.message = "Key size must be " + keySize + " byte";
	}

	public String getMessage() {
		return message;
	}

}
