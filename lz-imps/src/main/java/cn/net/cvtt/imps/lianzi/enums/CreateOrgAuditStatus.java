package cn.net.cvtt.imps.lianzi.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum CreateOrgAuditStatus implements EnumInteger {
	NONE(0), AUDITING(1), REJECTED(2), RECOMMIT(3), PASSED(4), ABANDON(5),;

	private final int value;

	CreateOrgAuditStatus(int value) {
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}
}
