package cn.net.cvtt.imps.user.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum UserConfigType implements EnumInteger {
	MachineChangedVerify(1);

	private final int value;

	UserConfigType(int value) {
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}

}
