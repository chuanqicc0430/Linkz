package cn.net.cvtt.imps.lianzi.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum ActionType implements EnumInteger {
	ADD(1), UPDATE(2), DELETE(3),;

	private int intValue;

	private ActionType(int intValue) {
		this.intValue = intValue;
	}

	@Override
	public int intValue() {
		return this.intValue;
	}

}
