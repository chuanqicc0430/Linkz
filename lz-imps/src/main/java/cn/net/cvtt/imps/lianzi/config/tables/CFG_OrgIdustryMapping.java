package cn.net.cvtt.imps.lianzi.config.tables;

import cn.net.cvtt.configuration.ConfigTableField;
import cn.net.cvtt.configuration.ConfigTableItem;

public class CFG_OrgIdustryMapping extends ConfigTableItem {

	@ConfigTableField(value = "IndustryId")
	private String industryId;

	@ConfigTableField("OrgCategoryId")
	private String orgCategoryId;

	@ConfigTableField("OrgCategoryName")
	private String orgCategoryName;

	@ConfigTableField("TopIndustryId")
	private String topIndustryId;

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getOrgCategoryId() {
		return orgCategoryId;
	}

	public void setOrgCategoryId(String orgCategoryId) {
		this.orgCategoryId = orgCategoryId;
	}

	public String getOrgCategoryName() {
		return orgCategoryName;
	}

	public void setOrgCategoryName(String orgCategoryName) {
		this.orgCategoryName = orgCategoryName;
	}

	public String getTopIndustryId() {
		return topIndustryId;
	}

	public void setTopIndustryId(String topIndustryId) {
		this.topIndustryId = topIndustryId;
	}

	@Override
	public String toString() {
		return String.format("CFG_OrgIdustryMapping [industryId=%s, orgCategoryId=%s, orgCategoryName=%s, topIndustryId=%s]", industryId, orgCategoryId, orgCategoryName, topIndustryId);
	}

}
