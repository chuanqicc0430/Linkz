package cn.net.cvtt.imps.notify.notifybody;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.net.cvtt.imps.lianzi.enums.NotifyType;
import cn.net.cvtt.lian.common.util.StringUtils;

public class VersionChangedBatchNotifyBody extends NotifyBody {
	private int type;
	private Map<Long,Long> value;
	private String actionType;
	
	public VersionChangedBatchNotifyBody(){
		
	}

	public VersionChangedBatchNotifyBody(String[] targets, int type, Map<Long,Long> value, String actionType) {
		super(NotifyType.CMD_BATCH_VERSION_CHANGED_NOTIFY.intValue(), targets);
		this.type = type;
		this.value = value;
		this.actionType = actionType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Map<Long, Long> getValue() {
		return value;
	}

	public void setValue(Map<Long, Long> value) {
		this.value = value;
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
		if (value != null && !value.isEmpty()) {
			try {
				ext.put("value", new ObjectMapper().writeValueAsString(value));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!StringUtils.isNullOrEmpty(actionType)) {
			ext.put("actionType", actionType);
		}
		setExt(ext);
	}

}
