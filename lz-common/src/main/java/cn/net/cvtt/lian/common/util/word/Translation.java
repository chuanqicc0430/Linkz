package cn.net.cvtt.lian.common.util.word;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <b>描述: </b>用于字集转换的工具类可以在GBK到BIG5之间进行字符串的转换
 * <p>
 * <b>功能: </b>用于字集转换的工具类可以在GBK到BIG5之间进行字符串的转换
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * Translation.trans.translate(TranslationWordInfo.BIG5_WORD, TranslationWordEnum.GBKTOBIG5)
 * </pre>
 * <p>
 * 
 * @author 
 * @see TranslationWordInfo
 * @see TranslationWordEnum
 */
public class Translation {
	private static Map<Character, Character> gbkMap = new HashMap<Character, Character>();

	private static Map<Character, Character> big5Map = new HashMap<Character, Character>();

	private StringBuffer big5Buffer;
	private StringBuffer gbkBuffer;

	public static Translation trans = new Translation();

	private Translation() {
		formMap();
	}

	private void formMap() {
		gbkBuffer = new StringBuffer(TranslationWordInfo.GBK_WORD);
		big5Buffer = new StringBuffer(TranslationWordInfo.BIG5_WORD);
		int k = gbkBuffer.length();
		Character fan = null;
		Character jian = null;
		for (int i = 0; i < k; i++) {
			jian = gbkBuffer.charAt(i);
			fan = big5Buffer.charAt(i);
			big5Map.put(fan, jian);
			gbkMap.put(jian, fan);
		}

	}

	/**
	 * 用于将一个字符串转换成指定编码格式的字符串
	 * 
	 * @param from
	 * @param type
	 * @return
	 */
	public String translate(String from, TranslationWordEnum type) {
		StringBuffer buffer = new StringBuffer(from);
		int length = from.length();
		char come;
		switch (type.getCode()) {
		case 0:
			for (int k = 0; k < length; k++) {
				come = from.charAt(k);
				if (gbkMap.containsKey(come)) {
					buffer.setCharAt(k, gbkMap.get(come));
				}
			}
			break;
		case 1:
			for (int k = 0; k < length; k++) {
				come = from.charAt(k);
				if (big5Map.containsKey(come)) {
					buffer.setCharAt(k, big5Map.get(come));
				}
			}
			break;
		}
		return buffer.toString();
	}
}
