package cn.net.cvtt.imps.lianzi.enums;

public enum OrgInfoCacheField {
	ORGID("orgId"),
	ORGFULLNAME("orgFullName"),
	ORGSHORTNAME("orgShortName"),
	ORGSTATUS("orgStatus"),
	ORGTYPE("orgType"),
	OFFICEADDRESS("officeAddress"),
	DETAILADDRESS("detailAddress"),
	ORGINDUSTRY("orgIndustry"),
	ORGPORTRAIT("orgPortrait"),
	ORGEMAIL("orgEmail"),
	ORGCONTACTPHONE("orgContactPhone"),
	CREATOR("creatorUserId"),
	CREATETIME("createTime"),
	ORGNETFLAG("orgNetFlag")
	;

	private String value;

	OrgInfoCacheField(String value) {
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
