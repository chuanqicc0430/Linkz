package cn.net.cvtt.imps.lianzi.config.tables;

import cn.net.cvtt.configuration.ConfigTableField;
import cn.net.cvtt.configuration.ConfigTableItem;

public class CFG_OrgIndustryLevel extends ConfigTableItem {

	@ConfigTableField(value = "IndustryId", isKeyField = true)
	private String industryId;

	@ConfigTableField("IndustryName")
	private String industryName;

	@ConfigTableField("ParentId")
	private String parentId;

	@ConfigTableField("LevelNum")
	private int levelNum;

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}

	@Override
	public String toString() {
		return String.format("CFG_OrgIndustryLevel [industryId=%s, industryName=%s, parentId=%s, levelNum=%s]", industryId, industryName, parentId, levelNum);
	}

	@Override
	public int hashCode() {
		String tmpIndustryId = this.industryId.replace("X", "9");
		String industryNum = tmpIndustryId.substring(1, tmpIndustryId.length() - 1);
		return Integer.parseInt(industryNum) / levelNum;
	}

}
