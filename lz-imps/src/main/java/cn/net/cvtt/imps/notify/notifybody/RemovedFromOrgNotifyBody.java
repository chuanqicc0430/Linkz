package cn.net.cvtt.imps.notify.notifybody;

import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.StringUtils;

import cn.net.cvtt.imps.lianzi.enums.NotifyType;

public class RemovedFromOrgNotifyBody extends NotifyBody {
	private long orgId;
	private String tips;

	public RemovedFromOrgNotifyBody(String[] targets, long orgId, String tips) {
		super(NotifyType.CMD_REMOVE_FROM_ORG_NOTIFY.intValue(), targets);
		this.orgId = orgId;
		this.tips = tips;
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

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	@Override
	public void putExtMap() {
		Map<String, String> ext = new HashMap<String, String>();
		ext.put("orgId", String.valueOf(orgId));
		if (!StringUtils.isNullOrEmpty(tips)) {
			ext.put("tips", tips);
		}
		setExt(ext);
	}

}
