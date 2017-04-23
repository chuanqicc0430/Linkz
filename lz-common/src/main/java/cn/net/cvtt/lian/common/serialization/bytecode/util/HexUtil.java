package cn.net.cvtt.lian.common.serialization.bytecode.util;

public class HexUtil {

	public static String toHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(toHexString(b[i]).toUpperCase());
			sb.append(" ");
		}
		return sb.toString();
	}

	public static String toHexString(byte b) {
		String hex = Integer.toHexString(b & 0xFF).toUpperCase();
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		return hex;
	}
}
