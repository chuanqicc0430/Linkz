package cn.net.cvtt.imps.lianzi.enums;

public enum UserInfoCacheField {
	USERID("userId"),
	MOBILENO("mobileNo"),
	PASSWORD("password"),
	EASEUSERNAME("easeUserName"),
	EASEUSERPWD("easeUserPwd"),
	NAME("name"),
	GENDER("gender"),
	PORTRAIT("portrait"),
	WORKPLACE("workPlace"),
	USERSTATUSFLAGS("userStatusFlags"),
	REGISTERTIME("registerTime"),
	USERCONFIG("userConfig"),
	;

	private String value;

	UserInfoCacheField(String value) {
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
