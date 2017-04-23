package cn.net.cvtt.imps.lianzi.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum KickoffType implements EnumInteger {
	OTHER_DEVICE_KICKED(1), 
	USER_BE_BLOCKED(2),
	MOBILE_CHANGE(3),
	PASSWORD_CHANGE(4),
	SERVER_MAINTAIN(5)
	;

	private int intValue;

	private KickoffType(int intValue) {
		this.intValue = intValue;
	}

	@Override
	public int intValue() {
		return this.intValue;
	}

}
