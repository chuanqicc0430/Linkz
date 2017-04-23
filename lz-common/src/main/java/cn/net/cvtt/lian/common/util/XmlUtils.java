package cn.net.cvtt.lian.common.util;

/**
 * 
 * <b>描述: </b>顾名思义，XML的工具类，为XML格式的字符串提供了特殊字符转换的操作
 * <p>
 * <b>功能: </b>为XML格式的字符串提供了特殊字符转换的操作
 * <p>
 * <b>用法: </b>正常的工具类调用方式
 * <p>
 * 
 * @author 
 * 
 */
public class XmlUtils {
	public static String encode(String s) {
		return encode(s, true);
	}

	public static String encode(String s, boolean maskInvaildCharacter) {
		if (StringUtils.isNullOrEmpty(s))
			return s;

		StringBuilder ret = null;
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);

			String ts = null;
			boolean invalidChar = false;

			if (isValidCharCode(ch) || ch == '\t' || ch == '\r' || ch == '\n') {
				switch (ch) {
				case '<':
					ts = "&lt;";
					break;
				case '>':
					ts = "&gt;";
					break;
				case '\"':
					ts = "&quot;";
					break;
				case '\'':
					ts = "&apos;";
					break;
				case '&':
					ts = "&amp;";
					break;
				case '\n':
					ts = "&#xa;";
					break;
				case '\r':
					ts = "&#xd;";
					break;

				default:
					break;
				}
			} else {
				invalidChar = true;
			}

			if (ret == null && (ts != null || invalidChar)) {
				ret = new StringBuilder();
				ret.append(s.substring(0, i));
			}

			if (ts != null) {
				ret.append(ts);
			} else if (invalidChar) {
				if (!maskInvaildCharacter)
					ret.append("&#x").append((Integer.toHexString((int) ch))).append(";");

			} else if (ret != null) {
				ret.append(ch);
			}
		}

		if (ret == null)
			return s;
		else
			return ret.toString();
	}
	
	public static String decode(String str) {
		str = str.replace("&lt;", "<");
		str = str.replace("&gt;", ">");
		str = str.replace("&quot;", "\"");
		str = str.replace("&apos;", "\'");
		str = str.replace("&amp;", "&");
		str = str.replace("&#xd;","\r");
		str = str.replace("&#xa;","\n");
		
		return str;
	}

	private static boolean isValidCharCode(int code) {
		return (0x0020 <= code && code <= 0xD7FF) || (0x000A == code) || (0x0009 == code) || (0x000D == code) || (0xE000 <= code && code <= 0xFFFD) || (0x10000 <= code && code <= 0x10ffff);
	}
}
