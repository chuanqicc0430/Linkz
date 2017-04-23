package cn.net.cvtt.imps.lianzi.enums;

public enum LianMemberCacheField {
	LIANID("lianId"),
	USERID("userId"),
	MEMBERID("memberId"),
	EASEUSERNAME("easeUserName"),
	MOBILENO("mobileNo"),
	NAME("name"),
	PORTRAIT("portrait"),
	LIANNICKNAME("lianNickName"),
	IDENTITY("identity"),
	CONTACTPHONE("contactPhone"),
	EMAIL("email"),
	JOINORGID("joinOrgId"),
	JOINORGNAME("joinOrgName"),
	JOINORGPORTRAIT("joinOrgPortrait"),
	JOINORGSTATUS("joinOrgStatus"),
	JOINTIME("joinTime");

	private String value;

	LianMemberCacheField(String value) {
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
