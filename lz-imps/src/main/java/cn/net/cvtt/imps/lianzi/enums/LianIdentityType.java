package cn.net.cvtt.imps.lianzi.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum LianIdentityType implements EnumInteger {
	LIAN_CHIEF(1), // 联长
	LIAN_SECRETRY(2), // 联秘
	LIAN_COUNCIL(3), // 联委会
	;
	private final int value;

	LianIdentityType(int value) {
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}

}
