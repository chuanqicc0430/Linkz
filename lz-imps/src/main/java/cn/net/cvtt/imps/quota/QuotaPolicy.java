package cn.net.cvtt.imps.quota;

import cn.net.cvtt.imps.user.flags.UserStatusFlags;


public class QuotaPolicy {

	private QuotaType quotaName;
	/*
	 * 配额周期，单位秒
	 */
	private int duration;
	/*
	 * 普通用户额度
	 */
	private int commonValue;
	/*
	 * VIP用户额度
	 */
	private int vipValue;
	/*
	 * SVIP用户额度
	 */
	private int svipValue;
	/*
	 * 是否启用
	 */
	private int enable;
	
	public QuotaPolicy() {
		super();
	}
	
	public QuotaPolicy(QuotaType type , int duration , int commonValue , int vipValue , int svipValue){
		this.setQuotaName(type);
		this.setDuration(duration);
		this.setCommonValue(commonValue);
		this.setVipValue(vipValue);
		this.setSvipValue(svipValue);
		this.setEnable(1);
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public QuotaType getQuotaName() {
		return quotaName;
	}

	public void setQuotaName(QuotaType quotaName) {
		this.quotaName = quotaName;
	}


	public int getCommonValue() {
		return commonValue;
	}

	public void setCommonValue(int commonValue) {
		this.commonValue = commonValue;
	}

	public int getVipValue() {
		return vipValue;
	}

	public void setVipValue(int vipValue) {
		this.vipValue = vipValue;
	}

	public int getSvipValue() {
		return svipValue;
	}

	public void setSvipValue(int svipValue) {
		this.svipValue = svipValue;
	}
	
	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public int getLimit(int userStatus){
		int limit = commonValue;
		if((userStatus & UserStatusFlags.SUPER_VIP.intValue()) > 0){
			limit = svipValue;
		}
		else if((userStatus & UserStatusFlags.VIP.intValue()) > 0){
			limit = vipValue;
		}
		
		return limit;
	}

}
