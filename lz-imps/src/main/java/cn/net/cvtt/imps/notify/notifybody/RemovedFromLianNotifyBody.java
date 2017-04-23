package cn.net.cvtt.imps.notify.notifybody;

import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.StringUtils;

import cn.net.cvtt.imps.lianzi.enums.NotifyType;

public class RemovedFromLianNotifyBody extends NotifyBody {
	private long lianId;
	private String tips;

	public RemovedFromLianNotifyBody(String[] targets, long lianId, String tips) {
		super(NotifyType.CMD_REMOVE_FROM_LIAN_NOTIFY.intValue(), targets);
		this.lianId = lianId;
		this.tips = tips;
	}

	public long getLianId() {
		return lianId;
	}

	public void setLianId(long lianId) {
		this.lianId = lianId;
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
		ext.put("lianId", String.valueOf(lianId));
		if (!StringUtils.isNullOrEmpty(tips)) {
			ext.put("tips", tips);
		}
		setExt(ext);
	}

}
