package helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Helper {

	private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes();
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#^%*()|";

	private static final String FILE_CONFIG = "/properties.txt";

	public static String bytesToHex(byte[] bytes) {
		byte[] hexChars = new byte[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars, StandardCharsets.UTF_8);
	}

	public static String randomString(int length) {
		StringBuilder builder = new StringBuilder();

		while (length-- != 0) {

			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());

			builder.append(ALPHA_NUMERIC_STRING.charAt(character));

		}
		return builder.toString();
	}

	public static String getProperty(String key) {

		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			String currentDir = System.getProperty("user.dir");
			inputStream = new FileInputStream(currentDir+ FILE_CONFIG);
			
//			inputStream = Helper.class.getClassLoader().getResourceAsStream(FILE_CONFIG);

			// load properties from file
			properties.load(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

			// get property by name
			return properties.getProperty(key);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// close objects
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	public static void main(String[] args) {
		System.out.println(getProperty("MD5"));
	}
}
