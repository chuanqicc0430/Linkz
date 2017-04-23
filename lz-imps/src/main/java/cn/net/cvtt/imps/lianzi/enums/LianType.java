package cn.net.cvtt.imps.lianzi.enums;

public enum LianType {
	JF_GENERAL_LIAN("A99"), // 工作职能通用联
	JF_LEADERSHIP_LIAN("A00"), // 总办联
	JF_HR_LIAN("A01"), // 人事专用联
	JF_FINANCE_LIAN("A02"), // 财务专用联
	JF_EXECUTIVE_LIAN("A03"), // 行政管理专用联
	JF_OFFICE_LIAN("A04"), // 办公室（文秘）专用联
	VM_GENERAL_LIAN("B99"), // 会员通用联
	VM_COMMERCE_LIAN("B01"), // 商会管理专用联
	VM_ASSOCIATION_LIAN("B02"), // 协会会员管理专用联
	VM_PARK_ENTERPRISE_LIAN("B03"),// 园区企业会员管理专用联
	VM_FEDERATION_LIAN("B04"),// 工商联管理专用联
	VM_OTHERORG_LIAN("B05"),// 其他社团组织会员管理专用联
	VM_SCHOOLORG_LIAN("B06"),// 校友会会员管理专用联
	VM_ENTERPRISE_LIAN("B07"),// 企业客户会员管理专用联
	;

	private final String value;

	LianType(String value) {
		this.value = value;
	}

	public String stringValue() {
		return value;
	}
}
