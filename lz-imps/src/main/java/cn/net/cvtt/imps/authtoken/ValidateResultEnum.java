package cn.net.cvtt.imps.authtoken;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum ValidateResultEnum implements EnumInteger {
	OK(200, "Success"),
	CREDENTIAL_EXPIRED(401,"Credential expired"),
	USER_NOT_FOUND(404,"UserInfo not found"),
	CREDENTIAL_ERROR(405,"Error Credential"),
	
	UAN_ARGS_ERROR(410,""),
	UAN_KS_EXPIRED(411,""),
	UAN_KS_NOTEXIST(412,""),
	UAN_SQN_ERROR(413,""),
	UAN_MAC_ERROR(414,""),
	UAN_USER_NOTFOUND(415,""),
	UAN_BTID_NOTEXIST(416,""),
	UAN_USERSESSION_NOTEXIST(417,""),
	UAN_OTHER_ERROR(418,""),
	PROCESS_ERROR(500,"Process error");

	private int code;
	private String desc;

	/**
	 * @param targetUrl
	 * @param actionStr
	 * @param name
	 */
	private ValidateResultEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public int intValue() {
		return this.code;
	}

	/**
	 * @return the targetUrl
	 */
	public String getDesc() {
		return this.desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
