package cn.net.cvtt.imps.lianzi.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum NotifyButtonType implements EnumInteger {
	UNKNOWN(-1), BUTTON_TIP(1), BUTTON_DISPOSE(2), BUTTON_ENTER(3);

	private final int value;

	NotifyButtonType(int value) {
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}

}
