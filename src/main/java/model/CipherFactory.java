package model;

public class CipherFactory {

	public ICippher chooseAlgo(String name) throws Exception {
		switch (name) {
		case "DES":
			return new DES();
		case "AES":
			return new AES();
		case "MD5":
			return new MD5();
		case "RC4":
			return new RC4();
		case "RSA":
			return new RSA();
		case "TWOFISH":
			return new TwofishImp();
		case "BLOWFISH":
			return new Blowfish();

		default:
			break;

		}
		return null;
	}
}
