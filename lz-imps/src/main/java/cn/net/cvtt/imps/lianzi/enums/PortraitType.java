package cn.net.cvtt.imps.lianzi.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum PortraitType implements EnumInteger {
	PERSONAL(1), LIAN(2), ORG(3), OTHER(99);

	private final int value;

	PortraitType(int value) {
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}

}
