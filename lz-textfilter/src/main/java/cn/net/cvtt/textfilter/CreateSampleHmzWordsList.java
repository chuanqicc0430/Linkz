package cn.net.cvtt.textfilter;

import java.util.ArrayList;
import java.util.List;

import cn.net.cvtt.lian.common.util.Flags;

public class CreateSampleHmzWordsList {

	private static String[] wordsArray = new String[] { "做原子弹的方法","九 一八", "做鸡", "做爱性感贴图论坛", "做爱小说", "周小川潜逃", "朱镕基", "主席病危", "中央内斗", "中央权力被架空", "雪山狮子旗" };

	public static List<HarmonizedWords> createList() {
		List<HarmonizedWords> list = new ArrayList<HarmonizedWords>();
		for (int i = 0; i < wordsArray.length; i++) {
			HarmonizedWords h = new HarmonizedWords();
			h.setWord(wordsArray[i]);
			h.setEnabled(true);
			h.setFilterMode(FilterModeEnum.Filtered);
			h.setFilterType(new Flags<FilterTypeEnum>(FilterTypeEnum.PersonalInfo));
			h.setTreatment((byte) 30);
			h.setRelateWords("");

			list.add(h);
		}

		return list;
	}
}
