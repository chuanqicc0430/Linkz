package cn.net.cvtt.imps.quota;

import cn.net.cvtt.lian.common.util.StringUtils;

public enum QuotaType {
	NONE (false,StringUtils.EMPTY),
	/*
	 * 每天 发送短信验证码上限
	 */
	SENDSMS_ONEDAY_LIMIT(true,"qsmsodl::%s"),
	/*
	 * 发送短信验证码频率限制
	 */
	SENDSMS_ONEMINUTE_FREQUENCY(true,"qsmsomf::%s"),
	/*
	 * 通知一键提醒未读人上限次数
	 */
	NOTICE_SENDTOUNREAD_LIMIT(true,"noticeSendLimt::%s"),
	/*
	 * 通知一键提醒未读人频率
	 */
	NOTICE_SENDTOUNREAD_CYCLE(true,"noticeSendCycle::%s"),
	;
	
	
	
	
	private boolean isBaseOnUser = false;
	private String keyFormatter = StringUtils.EMPTY;
	
	QuotaType(boolean value , String keyFormatter){
		this.isBaseOnUser = value;
		this.keyFormatter = keyFormatter;
	}
	
	public static QuotaType parse(String value){
		QuotaType type = Enum.valueOf(QuotaType.class, value);
		return type == null ? NONE : type;
	}
	
	public String getKeyFormatter(){
		return keyFormatter;
	}

	public boolean getIsBaseOnUser() {
		return isBaseOnUser;
	}

}
