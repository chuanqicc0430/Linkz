package cn.net.cvtt.textfilter.smartTextFilter.normalizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NormalizerUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(NormalizerUtil.class);

	private static HashMap<Character, Character> fan2Jian;

	private static HashMap<Character, Character> jian2Fan;

	public NormalizerUtil() {

		if (fan2Jian == null || jian2Fan == null) {
			load("complex.txt", "simple.txt");
		}

	};

	private void load(String pathOfFanti, String pathOfJianti) {
		fan2Jian = new HashMap<Character, Character>();
		jian2Fan = new HashMap<Character, Character>();
		StringBuffer jiantiBuffer = getDictionary(pathOfJianti);

		StringBuffer fantiBuffer = getDictionary(pathOfFanti);

		for (int i = 0; i < jiantiBuffer.length(); i++) {
			Character fan = fantiBuffer.charAt(i);

			Character jian = jiantiBuffer.charAt(i);

			fan2Jian.put(fan, jian);

			jian2Fan.put(jian, fan);

		}

	}

	public StringBuffer getDictionary(String path) {
		BufferedReader bufferReader = new BufferedReader(
				new InputStreamReader(NormalizerUtil.class.getClassLoader()
						.getResourceAsStream(path)));
		String line;
		StringBuffer readAll = new StringBuffer();
		try {
			while ((line = bufferReader.readLine()) != null) {
				readAll.append(line);
			}
		} catch (IOException e) {
			logger.error("read file error ", e);
			return null;
		} finally {
			if (bufferReader != null)
				try {
					bufferReader.close();
				} catch (IOException e) {
					logger.error("close error ", e);
				}
		}
		return readAll;
	}

	/**
	 * 简繁体互相转换 方法
	 * 
	 * @param from
	 *            字符串
	 * @param type
	 *            转换类型
	 * @return 返回 转换后的 字符
	 */
	public char[] translate(char[] from, String type) {

		if ("fan2Jian".equals(type)) {
			for (int k = 0; k < from.length; k++) {
				char come = from[k];
				if (fan2Jian.containsKey(come)) {
					from[k] = fan2Jian.get(come);
				}
			}
		} else {
			for (int k = 0; k < from.length; k++) {

				char come = from[k];

				if (jian2Fan.containsKey(come)) {
					from[k] = jian2Fan.get(come);
				}
			}
		}
		return from;
	}

	/**
	 * 全角转半角
	 * 
	 * @param input
	 *            String.
	 * @return 半角字符串
	 */
	public static String ToDBC(String input) {

		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);
			}
		}
		return new String(c);
	}

	/**
	 * 大小转 小写 方法
	 * 
	 * @param str
	 *            字符串
	 * @return 返回小写 字母
	 */
	public static String bigToSmall(String str) {
		/*
		 * StringBuffer sb = new StringBuffer(str); for (int i = 0; i <
		 * sb.length(); i++) { char c = sb.charAt(i); if
		 * (!Character.isLetter((int)c)) continue; if (c <= 'Z' && c >= 'A')
		 * sb.setCharAt(i, Character.toLowerCase(c)); } return sb.toString();
		 */
		return str.toLowerCase();
	}

	/**
	 * 半角转全角
	 * 
	 * @param input
	 *            String.
	 * @return 全角字符串.
	 */
	public static String ToSBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + 65248);

			}
		}
		return new String(c);
	}

	/**
	 * 规范化 字符
	 * 
	 * @param input
	 * @return 返回 格式化的字符
	 */
	public static String normalizeString(char[] input) {

		StringBuilder str = new StringBuilder();
		for (char ch : input) {
			if (ch != '\u0000') {
				str.append(ch);
			}
		}

		// 全角转半角
		String result = ToDBC(new String(str.toString().toCharArray()));
		// 大写转小写
		result = bigToSmall(result);

		return result;
	}

	/**
	 * 判断是否为 unicode 标点符号
	 * 
	 * @param ch
	 *            字符
	 * @return 是-true，否-false
	 */
	public static boolean isPunctuation(char ch) {
		int type = Character.getType(ch);
		if (Character.CONNECTOR_PUNCTUATION == type)
			return true;
		if (Character.DASH_PUNCTUATION == type)
			return true;
		if (Character.END_PUNCTUATION == type)
			return true;
		if (Character.FINAL_QUOTE_PUNCTUATION == type)
			return true;
		if (Character.START_PUNCTUATION == type)// open
			return true;
		if (Character.INITIAL_QUOTE_PUNCTUATION == type)
			return true;
		if (Character.OTHER_PUNCTUATION == type)
			return true;
		else
			return false;

	}

	/**
	 * 判断是否为 unicode 分隔符
	 * 
	 * @param ch
	 *            字符
	 * @return 是-true，否-false
	 */
	public static boolean isSeparator(char ch) {
		int type = Character.getType(ch);
		if (Character.SPACE_SEPARATOR == type)
			return true;
		if (Character.LINE_SEPARATOR == type)
			return true;
		if (Character.PARAGRAPH_SEPARATOR == type)
			return true;
		else
			return false;
	}

	/**
	 * 判断是否为 Unicode 属于符号字符
	 * 
	 * @param ch
	 *            字符
	 * @return 是-true，否-false
	 */
	public static boolean isSymbol(char ch) {
		int type = Character.getType(ch);
		if (Character.MATH_SYMBOL == type)
			return true;
		if (Character.CURRENCY_SYMBOL == type)
			return true;
		if (Character.MODIFIER_SYMBOL == type)
			return true;
		if (Character.OTHER_SYMBOL == type)
			return true;
		else
			return false;

	}
}
