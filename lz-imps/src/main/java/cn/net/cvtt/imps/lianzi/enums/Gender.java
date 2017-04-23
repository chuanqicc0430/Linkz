package cn.net.cvtt.imps.lianzi.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum Gender implements EnumInteger {
	UNKNOWN(-1), MALE(1), FEMALE(2);

	private final int value;

	Gender(int value) {
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}

}
