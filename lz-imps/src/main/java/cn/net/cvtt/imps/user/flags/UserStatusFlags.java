package cn.net.cvtt.imps.user.flags;

import cn.net.cvtt.lian.common.util.EnumInteger;
import cn.net.cvtt.lian.common.util.EnumParser;

public enum UserStatusFlags implements EnumInteger {

	PRE_NORMAL(0x1), // 还未完成注册的预注册用户
	NORMAL(0x2), // 普通用户
	VIP(0x4), // VIP用户
	SUPER_VIP(0x8), // 超级VIP用户
	CREATE_ORG(0x10), // 创建过组织
	CREATE_LIAN(0x20), // 创建过联
	LIAN_SECRETRY(0x40), // 担任过联秘
	LIAN_CHIEF(0x80), // 担任过联长
	ORG_LEADER_LIAN_SECRETRY(0x100), // 担任过总秘
	ORG_LEADER_LIAN_CHIEF(0x200),// 担任过大联长

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
