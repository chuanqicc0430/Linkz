package cn.net.cvtt.imps.lianzi.config.tables;

import cn.net.cvtt.configuration.ConfigTableField;
import cn.net.cvtt.configuration.ConfigTableItem;

public class CFG_Region extends ConfigTableItem {

	@ConfigTableField(value = "RegionId", isKeyField = true)
	private String regionId;

	@ConfigTableField("RegionFullName")
	private String regionFullName;

	@ConfigTableField("RegionShortName")
	private String regionShortName;

	@ConfigTableField("ParentRegionId")
	private String parentRegionId;

	@ConfigTableField("LevelNum")
	private int levelNum;

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionFullName() {
		return regionFullName;
	}

	public void setRegionFullName(String regionFullName) {
		this.regionFullName = regionFullName;
	}

	public String getRegionShortName() {
		return regionShortName;
	}

	public void setRegionShortName(String regionShortName) {
		this.regionShortName = regionShortName;
	}

	public String getParentRegionId() {
		return parentRegionId;
	}

	public void setParentRegionId(String parentRegionId) {
		this.parentRegionId = parentRegionId;
	}

	public int getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}

	@Override
	public String toString() {
		return String.format("CFG_Region [regionId=%s, regionFullName=%s, regionShortName=%s, parentRegionId=%s, levelNum=%s]", regionId, regionFullName, regionShortName, parentRegionId, levelNum);
	}

}
