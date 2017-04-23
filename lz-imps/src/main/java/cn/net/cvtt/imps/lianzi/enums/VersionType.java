package cn.net.cvtt.imps.lianzi.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum VersionType implements EnumInteger {
	V_LIAN_INFO(1), 
	V_ORG_INFO(2), 
	V_LIAN_MEMBERS(3), 
	V_ORG_ALONEMEMBERS(4), 
	V_LIAN_POSITIONS(5), 
	V_USER_FRIENDLYORG(6), 
	V_USER_FOLLOWORG(7), 
	V_APP_NOTICEINFO(8), 
	V_USER_INFO(9);

	private int intValue;

	private VersionType(int intValue) {
		this.intValue = intValue;
	}

	@Override
	public int intValue() {
		return this.intValue;
	}

}
