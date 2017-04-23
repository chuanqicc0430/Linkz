package cn.net.cvtt.imps.user.config.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum CommonAuditStatus implements EnumInteger {
	NONE(0), COMMIT(1), AUDIT(2), COMPLETE(3),;

	private final int value;

	CommonAuditStatus(int value) {
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}
}
