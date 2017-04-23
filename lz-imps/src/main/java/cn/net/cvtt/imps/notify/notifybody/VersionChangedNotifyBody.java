package cn.net.cvtt.imps.notify.notifybody;

import java.util.HashMap;
import java.util.Map;

import cn.net.cvtt.lian.common.util.StringUtils;

public class VersionChangedNotifyBody extends NotifyBody {
	private int type;
	private long value;
	private long actionId;
	private String actionType;
	
	public VersionChangedNotifyBody(){
		
	}
	
	public VersionChangedNotifyBody(int type, long value, long actionId, String actionType) {
		super();
		this.type = type;
		this.value = value;
		this.actionId = actionId;
		this.actionType = actionType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public long getActionId() {
		return actionId;
	}

	public void setActionId(long actionId) {
		this.actionId = actionId;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	@Override
	public void putExtMap() {
		Map<String, String> ext = new HashMap<String, String>();
		ext.put("type", String.valueOf(type));
		ext.put("value", String.valueOf(value));
		if (actionId != 0) {
			ext.put("actionId", String.valueOf(actionId));
		}
		if (!StringUtils.isNullOrEmpty(actionType)) {
			ext.put("actionType", actionType);
		}
		setExt(ext);
	}

}
