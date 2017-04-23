package cn.net.cvtt.imps.lianzi.enums;

public enum NotifyButtonTips {
	TIP_BUTTON_TIP("知道了"), 
	TIP_BUTTON_DISPOSE("立即处理"), 
	TIP_BUTTON_ENTER("进入");

	private final String value;

	NotifyButtonTips(String value) {
		this.value = value;
	}

	public String strValue() {
		return value;
	}

}
