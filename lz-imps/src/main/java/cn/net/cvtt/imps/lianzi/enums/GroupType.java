package cn.net.cvtt.imps.lianzi.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum GroupType implements EnumInteger {
	NONE(0), GROUP_COUNCIL(1), // 联委会
	GROUP_COMPLETE(2), // 全联
	GROUP_DISCUSSION(3),// 会员联-联委会与单个会员组成的临时讨论组
	;

	private final int value;

	GroupType(int value) {
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}
}
