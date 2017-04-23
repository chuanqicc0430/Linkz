package cn.net.cvtt.imps.lianzi.config.tables;

import cn.net.cvtt.configuration.ConfigTableField;
import cn.net.cvtt.configuration.ConfigTableItem;

public class CFG_OrgCategory extends ConfigTableItem {

	@ConfigTableField(value = "OrgCategoryCode", isKeyField = true)
	private String orgCategoryCode;

	@ConfigTableField("OrgCategoryName")
	private String orgCategoryName;

	@ConfigTableField("ParentOrgCategoryCode")
	private String parentOrgCategoryCode;

	@ConfigTableField("FullCategoryId")
	private String fullCategoryId;

	public String getOrgCategoryCode() {
		return orgCategoryCode;
	}

	public void setOrgCategoryCode(String orgCategoryCode) {
		this.orgCategoryCode = orgCategoryCode;
	}

	public String getOrgCategoryName() {
		return orgCategoryName;
	}

	public void setOrgCategoryName(String orgCategoryName) {
		this.orgCategoryName = orgCategoryName;
	}

	public String getParentOrgCategoryCode() {
		return parentOrgCategoryCode;
	}

	public void setParentOrgCategoryCode(String parentOrgCategoryCode) {
		this.parentOrgCategoryCode = parentOrgCategoryCode;
	}

	public String getFullCategoryId() {
		return fullCategoryId;
	}

	public void setFullCategoryId(String fullCategoryId) {
		this.fullCategoryId = fullCategoryId;
	}

	@Override
	public String toString() {
		return String.format("CFG_OrgCategory [orgCategoryCode=%s, orgCategoryName=%s, parentOrgCategoryCode=%s, fullCategoryId=%s]", orgCategoryCode, orgCategoryName, parentOrgCategoryCode, fullCategoryId);
	}

}
