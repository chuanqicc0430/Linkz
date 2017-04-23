package cn.net.cvtt.imps.lianzi.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum OrgAuthField implements EnumInteger {
	ORGFULLNAME(1), 
	SOCIALCREDITCODE(2), 
	SOCIALCREDITPIC(3),
	BUSINESSLICENSECODE(4),
	BUSINESSLICENSEPIC(5),
	ORGANIZATIONCODE(6),
	ORGANIZATIONPIC(7),
	AUTHAPPLYLETTERPIC(8),
	CREATORNAME(9),
	CREATORIDENTITYCARD(10),
	CREATORIDENTITYCARDPIC(11),
	;

	private final int value;

	OrgAuthField(int value) {
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}
}
