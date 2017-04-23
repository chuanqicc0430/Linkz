package cn.net.cvtt.imps.lianzi.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum GroupChatTipType implements EnumInteger {
	UNKNOWN(-1), COMPLETE_GROUP(1), COUNCIL_GROUP(2);

	private final int value;

	GroupChatTipType(int value) {
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}

}
