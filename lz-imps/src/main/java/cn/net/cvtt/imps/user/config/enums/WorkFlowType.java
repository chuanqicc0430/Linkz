package cn.net.cvtt.imps.user.config.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum WorkFlowType implements EnumInteger {
	NONE(0), 
	CREATE_LIAN_AUDIT(1), 
	LIAN_ADD_MEMBER_BATCH_AUDIT(2), 
	LIAN_DUTIES_MODIFY_AUDIT(3), 
	LIAN_RULES_MODIFY_AUDIT(4),
	APPLICATION_NOTICE_FLOW(5),
	APPLICATION_NOTICE_REPORT_FLOW(6);

	private final int value;

	WorkFlowType(int value) {
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}
}
