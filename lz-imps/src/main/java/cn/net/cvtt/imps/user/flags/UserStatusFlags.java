package cn.net.cvtt.imps.user.flags;

import cn.net.cvtt.lian.common.util.EnumInteger;
import cn.net.cvtt.lian.common.util.EnumParser;

public enum UserStatusFlags implements EnumInteger {

	PRE_NORMAL(0x1), // 预注册用户
	NORMAL(0x2), // 普通用户
	VIP(0x4), // VIP用户
	SUPER_VIP(0x8), // 超级VIP用户

	;

	private final int value;

	UserStatusFlags(int value) {
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}

	public static UserStatusFlags parse(int value) {
		UserStatusFlags userStatus = (UserStatusFlags) EnumParser.parseInt(UserStatusFlags.class, value);
		if (userStatus == null) {
			userStatus = UserStatusFlags.NORMAL;
		}
		return userStatus;
	}
}
