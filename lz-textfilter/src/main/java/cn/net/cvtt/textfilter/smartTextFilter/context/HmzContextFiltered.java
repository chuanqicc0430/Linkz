package cn.net.cvtt.textfilter.smartTextFilter.context;

import java.util.HashMap;

import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.lian.common.util.StringUtils;
import cn.net.cvtt.textfilter.smartTextFilter.normalizer.NormalizerUtil;
import cn.net.cvtt.textfilter.smartTextFilter.tree.HmzNode;

public class HmzContextFiltered extends HmzContext {
	private static HashMap<Character, Boolean> preDefined = new HashMap<Character, Boolean>();
	private static HashMap<Character, Boolean> ignore = new HashMap<Character, Boolean>();

	@Initializer
	public static void initialize() {
		HashMap<Character, Boolean> list = new HashMap<Character, Boolean>();
		list.put('.', true);
		list.put(',', true);
		list.put('(', true);
		list.put(')', true);
		list.put('`', true);
		list.put('?', true);
		list.put('{', true);
		list.put('}', true);
		list.put('[', true);
		list.put(']', true);
		list.put('|', true);
		list.put(':', true);
		list.put(';', true);
		list.put('’', true);
		list.put('<', true);
		list.put('>', true);
		list.put('?', true);
		list.put('/', true);
		list.put('、', true);
		list.put('_', true);
		list.put('《', true);
		list.put('》', true);
		list.put('『', true);
		list.put('』', true);
		list.put('【', true);
		list.put('】', true);
		list.put('▏', true);
		list.put('▎', true);
		list.put('▍', true);
		list.put('▕', true);
		list.put('ㄧ', true);
		list.put('︳', true);
		list.put('-', true);
		list.put(' ', true);
		list.put('\\', true);
		list.put('"', true);
		list.put('\'', true);
		list.put('!', true);
		list.put('\n', true);
		list.put('\r', true);
		list.put('@', true);
		list.put('#', true);
		list.put('$', true);
		list.put('%', true);
		list.put('^', true);
		list.put('&', true);
		list.put('￥', true);
		list.put('*', true);
		list.put('…', true);
		list.put('┼', true);
		list.put('┽', true);
		list.put('┾', true);
		list.put('┿', true);
		list.put('╀', true);
		list.put('╁', true);
		list.put('╂', true);
		list.put('╃', true);
		list.put('╄', true);
		list.put('╅', true);
		list.put('╆', true);
		list.put('╇', true);
		list.put('╉', true);
		list.put('╊', true);
		list.put('╈', true);
		list.put('╋', true);
		list.put('╪', true);
		list.put('╫', true);
		list.put('╬', true);
		list.put('±', true);
		list.put('+', true);
		ignore = preDefined = list;
	}

	public static void loadExtraIgnoreCharacters(String exIgnore) {
		if (StringUtils.isNullOrEmpty(exIgnore)) {
			ignore = preDefined;
		} else {
			HashMap<Character, Boolean> list = new HashMap<Character, Boolean>();
			for (int i = 0; i < exIgnore.length(); ++i) {
				list.put(exIgnore.charAt(i), true);
			}
			ignore = list;
		}
	}

	public HmzContextFiltered(HmzNode root) {
		super(root);
	}

	protected boolean special(char c, char e) {
		if (Character.isWhitespace(c))
			return true;
		if (Character.isISOControl(c))
			return true;
		if (NormalizerUtil.isPunctuation(c))
			return true;
		if (NormalizerUtil.isSeparator(c))
			return true;
		if (NormalizerUtil.isSymbol(c))
			return true;

		if (ignore.containsKey(c))
			return true;

		return false;
	}
}
