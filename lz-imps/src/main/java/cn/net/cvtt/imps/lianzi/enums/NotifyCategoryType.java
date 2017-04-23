package cn.net.cvtt.imps.lianzi.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum NotifyCategoryType implements EnumInteger {
	UNKNOWN(-1), PERSONAL_NOTE(1), ORGANIZATION_NOTE(2), URGENTBOX_NOTE(3);

	private final int value;

	NotifyCategoryType(int value) {
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}

}
