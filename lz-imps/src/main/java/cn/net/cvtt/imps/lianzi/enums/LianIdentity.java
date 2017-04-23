package cn.net.cvtt.imps.lianzi.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum LianIdentity implements EnumInteger {
	LIAN_MEMBER(0x1), // 普通联成员
	LIAN_CREATOR(0x2), // 联创建者
	LIAN_CHIEF(0x4), // 联长
	LIAN_SECRETRY(0x8), // 联秘
	LIAN_COUNCIL(0x10), // 联委会成员
	
	CREATE_LIAN_LEADER(LIAN_MEMBER.intValue() | LIAN_CREATOR.intValue() | LIAN_CHIEF.intValue() | LIAN_SECRETRY.intValue() | LIAN_COUNCIL.intValue()),
	;

	private final int value;

	LianIdentity(int value) {
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}
	
	public static void main(String[] args) {
//		System.out.println(LianIdentity.LIAN_SECRETRY.intValue() | (LianIdentity.LIAN_CHIEF.intValue()));
		System.out.println(21 ^ LianIdentity.LIAN_CHIEF.intValue() ^ LianIdentity.LIAN_COUNCIL.intValue());
	}

}
