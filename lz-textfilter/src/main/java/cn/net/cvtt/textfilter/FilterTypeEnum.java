package cn.net.cvtt.textfilter;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum FilterTypeEnum implements EnumInteger {
	None(0x0000), PersonalInfo(0x0001), OrgInfo(0x0002), LianInfo(0x0004), DialogMessage(0x0008), GroupMessage(0x0010), NoticeInfo(0x0020), HtmlContent(0x8000);

	int value;

	private FilterTypeEnum(int v) {
		value = v;
	}

	@Override
	public int intValue() {
		return value;
	}

	public static FilterTypeEnum intConvert(int value) {
		switch (value) {
		case 0x0000:
			return None;
		case 0x0001:
			return PersonalInfo;
		case 0x0002:
			return OrgInfo;
		case 0x0004:
			return LianInfo;
		case 0x0008:
			return DialogMessage;
		case 0x0010:
			return GroupMessage;
		case 0x0020:
			return NoticeInfo;
		case 0x8000:
			return HtmlContent;
		default:
			return null;
		}
	}

	public static void main(String[] args) {
		int defaultValue = FilterTypeEnum.PersonalInfo.intValue() | FilterTypeEnum.OrgInfo.intValue() | FilterTypeEnum.LianInfo.intValue() | FilterTypeEnum.DialogMessage.intValue() | FilterTypeEnum.GroupMessage.intValue() | FilterTypeEnum.NoticeInfo.intValue();
		System.out.println(defaultValue);

	}
}
