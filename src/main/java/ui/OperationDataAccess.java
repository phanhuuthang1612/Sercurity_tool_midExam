package ui;

import org.fluttercode.datafactory.impl.DataFactory;
import java.util.ArrayList;
import java.util.List;

public class OperationDataAccess {

	public static List<String> getAlgo() {
		List<String> list = new ArrayList<>();
		String[] depts = { "DES", "AES", "RC4", "TWOFISH", "BLOWFISH", "MD5", "RSA" };
		DataFactory df = new DataFactory();
		for (int i = 0; i < depts.length; i++) {
			list.add(depts[i]);
		}
		return list;
	}
}
