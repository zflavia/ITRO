package ro.uvt.fmi.itro.login;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class TestDeSters {

	
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] digest = md.digest("admin".getBytes());
//		StringBuilder sb = new StringBuilder();
//		for (byte b : digest) {
//		    sb.append(Integer.toHexString(b & 0xff));
//		}
//		System.out.println(sb.toString());
//		String base64 = Base64.getEncoder().encodeToString(sb.toString().getBytes());
//		System.out.println(base64);
		
		 String base64 = Base64.getEncoder().encodeToString(digest);
		System.out.println(base64);
		
		System.out.println(createPasswordHash("SHA-256", "BASE64", "UTF-8", "admin"));
		
	}
	
	
	public static String createPasswordHash(final String hashAlgorithm, final String hashEncoding, final String hashCharset, final String password) {

		// TODO@dan: assure default method usage in the whole platform

		String passwordHash = null;
		byte passBytes[];
		try {
			if (hashCharset == null) {
				passBytes = password.getBytes();
			} else {
				passBytes = password.getBytes(hashCharset);
			}
		} catch (UnsupportedEncodingException uee) {
			passBytes = password.getBytes();
		}
		try {
			byte hash[] = MessageDigest.getInstance(hashAlgorithm).digest(passBytes);
			if (hashEncoding.equalsIgnoreCase("BASE64")) {
				passwordHash = encodeBase64(hash);

			} else if (hashEncoding.equalsIgnoreCase("HEX")) {
				passwordHash = encodeBase16(hash);
			}
		} catch (Exception e) {
		}
		return passwordHash;
	}
	
	public static String encodeBase16(final byte bytes[]) {
		StringBuffer sb = new StringBuffer(bytes.length * 2);
		for (byte b : bytes) {

			char c = (char)(b >> 4 & 0xf);
			if (c > '\t') {
				c = (char)((c - 10) + 97);
			} else {
				c += '0';
			}
			sb.append(c);
			c = (char)(b & 0xf);
			if (c > '\t') {
				c = (char)((c - 10) + 97);
			} else {
				c += '0';
			}
			sb.append(c);
		}

		return sb.toString();
	}


	public static String encodeBase64(final byte bytes[]) {
		String base64 = null;
		try {
			base64 = new String(Base64.getEncoder().encode(bytes));
		} catch (Exception e) {
		}
		return base64;
	}


}
