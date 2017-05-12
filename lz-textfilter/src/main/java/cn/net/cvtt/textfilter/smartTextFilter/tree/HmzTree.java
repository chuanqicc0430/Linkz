package cn.net.cvtt.textfilter.smartTextFilter.tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.net.cvtt.lian.common.util.Flags;
import cn.net.cvtt.lian.common.util.StringUtils;
import cn.net.cvtt.textfilter.FilterModeEnum;
import cn.net.cvtt.textfilter.FilterTypeEnum;
import cn.net.cvtt.textfilter.HarmonizedWords;
import cn.net.cvtt.textfilter.smartTextFilter.context.FilterContext;
import cn.net.cvtt.textfilter.smartTextFilter.context.HmzContext;
import cn.net.cvtt.textfilter.smartTextFilter.context.HmzContextExact;
import cn.net.cvtt.textfilter.smartTextFilter.context.HmzContextFiltered;
import cn.net.cvtt.textfilter.smartTextFilter.context.HmzContextLanguage;
import cn.net.cvtt.textfilter.smartTextFilter.context.HmzContextPhrase;
import cn.net.cvtt.textfilter.smartTextFilter.normalizer.ConfigNormalizer;

public class HmzTree {

	private HmzNode rootExact;
	private HmzNode rootPhrase;
	private HmzNode rootFiltered;
	private HmzNode rootLanguage;

	public HmzTree(List<HarmonizedWords> tables) throws IOException {
		rootExact = new HmzNode(Character.MIN_VALUE, Byte.MIN_VALUE);
		rootPhrase = new HmzNode(Character.MIN_VALUE, Byte.MIN_VALUE);
		rootFiltered = new HmzNode(Character.MIN_VALUE, Byte.MIN_VALUE);
		rootLanguage = new HmzNode(Character.MIN_VALUE, Byte.MIN_VALUE);

		loadCodeTable(tables);
	}

	public FilterContext createContext() {
		HmzContext[] context = { new HmzContextExact(rootExact), new HmzContextPhrase(rootPhrase), new HmzContextFiltered(rootFiltered), new HmzContextLanguage(rootLanguage) };

		return new FilterContext(context);
	}

	private void loadCodeTable(List<HarmonizedWords> tables) throws IOException {
		for (HarmonizedWords words : tables) {
			if (!words.isEnabled() || words.getFilterType().intValue() == FilterTypeEnum.None.intValue()) {
				continue;
			}
			String main = words.getWord().trim();
			if (StringUtils.isNullOrEmpty(main)) {
				continue;
			}

			HmzNode root = null;
			int ct = 1;
			boolean composite = false;

			FilterModeEnum mode = words.getFilterMode();
			switch (mode) {
			case Exact:
				root = rootExact;
				break;
			case Phrase:
				root = rootPhrase;
			case Filtered:
				root = rootFiltered;
				break;
			case Language:
				root = rootLanguage;
				break;
			case Composite:
			case MasterSlave:
				boolean flag = false;
				try {
					ct = Integer.parseInt(main);
					flag = true;
				} catch (Exception e) {
				}
				if (!flag || ct < 1) {
					continue;
				}
				root = rootFiltered;
				composite = true;
				break;
			default:
				continue;
			}

			String[] reWords = words.getRelateWords();
			if (reWords.length > 0) {
				for (int i = 0; i < reWords.length; ++i) {
					String relate = reWords[i].trim();
					if (StringUtils.isNullOrEmpty(relate)) {
						continue;
					}
					if (!composite) {
						ct = 2;
					}
					loadWord(root, relate, words.getFilterType(), words.getTreatment(), new HmzIdentity(words, relate, composite, ct));
				}
			}

			if (!composite) {
				loadWord(root, main, words.getFilterType(), words.getTreatment(), new HmzIdentity(words, main, true, ct));
			}
		}
	}

	private List<HmzNode> loadWord(HmzNode root, String text, Flags<FilterTypeEnum> type, byte treatment, HmzIdentity identity) throws IOException {
		List<HmzNode> leafs = new ArrayList<HmzNode>();

		ConfigNormalizer normalizer = new ConfigNormalizer(text);
		leafs.add(root);
		for (List<List<Character>> chars = normalizer.read(); chars != null; chars = normalizer.read()) {
			List<HmzNode> paths = new ArrayList<HmzNode>();
			// 此处 将chars " " 的 元素 去掉
			for (int i = 0; i < chars.size(); ++i) {
				List<Character> charr = chars.get(i);
				for (int j = 0; j < leafs.size(); ++j) {
					HmzNode path = leafs.get(j);

					for (int k = 0; k < charr.size(); ++k) {
						// for (int k = 0; k < 1; ++k) {
						HmzNode node = path.getNexts().get(charr.get(k));
						if (node == null) {
							node = new HmzNode(charr.get(k), treatment);
							path.getNexts().put(charr.get(k), node);
						}
						node.addType(type);
						path = node;
					}

					paths.add(path);
				}
			}

			leafs = paths;
			// }
		}
		for (int i = 0; i < leafs.size(); ++i) {
			if (!(leafs.get(i) == root))
				leafs.get(i).getIdentities().add(identity);
		}
		return leafs;
	}

}
