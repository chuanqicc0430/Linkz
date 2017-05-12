package cn.net.cvtt.textfilter;

import java.util.ArrayList;
import java.util.List;

import cn.net.cvtt.lian.common.util.Flags;
import cn.net.cvtt.lian.common.util.StringUtils;

public class HarmonizedWords {

	private int id;

	private Flags<FilterTypeEnum> filterType = new Flags<FilterTypeEnum>(FilterTypeEnum.None.intValue());

	private String word = StringUtils.EMPTY;

	// 此处的修饰去除，使这个加载配置的部分尽量单纯

	private FilterModeEnum filterMode = FilterModeEnum.None;

	private boolean enabled;

	private String relateWords = StringUtils.EMPTY;

	private byte treatment = 0;

	public String[] getRelateWords() {
		if (StringUtils.isNullOrEmpty(relateWords)) {
			return new String[0];
		}
		List<String> words = new ArrayList<String>();
		for (String word : relateWords.split(",")) {
			if (!StringUtils.isNullOrEmpty(word)) {
				words.add(word);
			}
		}
		return words.toArray(new String[1]);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Flags<FilterTypeEnum> getFilterType() {
		return filterType;
	}

	public void setFilterType(Flags<FilterTypeEnum> filterType) {
		this.filterType = filterType;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public FilterModeEnum getFilterMode() {
		return filterMode;
	}

	public void setFilterMode(FilterModeEnum filterMode) {
		this.filterMode = filterMode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public byte getTreatment() {
		return treatment;
	}

	public void setTreatment(byte treatment) {
		this.treatment = treatment;
	}

	public void setRelateWords(String relateWords) {
		this.relateWords = relateWords;
	}
}
