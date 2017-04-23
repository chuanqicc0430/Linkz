package cn.net.cvtt.imps.notify.notifybody;

import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.StringUtils;

import cn.net.cvtt.imps.lianzi.enums.NotifyType;

public class LianIdentityChangedNotifyBody extends NotifyBody {
	private int identityType;
	private long lianId;
	private long orgId;
	private String actionType;
	private String tips;

	public LianIdentityChangedNotifyBody(String[] targets, long lianId, long orgId, String actionType, int identityType, String tips) {
		super(NotifyType.CMD_IDENTITY_CHANGED_NOTIFY.intValue(), targets);
		this.actionType = actionType;
		this.lianId = lianId;
		this.orgId = orgId;
		this.identityType = identityType;
		this.tips = tips;
	}

	public int getIdentityType() {
		return identityType;
	}

	public void setIdentityType(int identityType) {
		this.identityType = identityType;
	}

	public long getLianId() {
		return lianId;
	}

	public void setLianId(long lianId) {
		this.lianId = lianId;
	}

	/**
	 * @return the orgId
	 */
	public long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	@Override
	public void putExtMap() {
		Map<String, String> ext = new HashMap<String, String>();
		ext.put("actionType", String.valueOf(actionType));
		ext.put("lianId", String.valueOf(lianId));
		ext.put("orgId", String.valueOf(orgId));
		ext.put("identityType", String.valueOf(identityType));
		if (!StringUtils.isNullOrEmpty(tips)) {
			ext.put("tips", tips);
		}
		setExt(ext);
	}

}
