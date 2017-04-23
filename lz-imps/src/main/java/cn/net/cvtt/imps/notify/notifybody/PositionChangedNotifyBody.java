package cn.net.cvtt.imps.notify.notifybody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.StringUtils;

import cn.net.cvtt.imps.lianzi.enums.NotifyType;

public class PositionChangedNotifyBody extends NotifyBody {
	private List<Long> positionId;
	private long lianId;
	private String actionType;
	private String tips;

	public PositionChangedNotifyBody(String targets, long lianId, String actionType, List<Long> positionList, String tips) {
		super(NotifyType.CMD_POSITION_CHANGED_NOTIFY.intValue(), new String[] { targets });
		this.actionType = actionType;
		this.lianId = lianId;
		this.positionId = positionList;
		this.tips = tips;
	}

	public long getLianId() {
		return lianId;
	}

	public void setLianId(long lianId) {
		this.lianId = lianId;
	}

	/**
	 * @return the positionId
	 */
	public List<Long> getPositionId() {
		return positionId;
	}

	/**
	 * @param positionId
	 *            the positionId to set
	 */
	public void setPositionId(List<Long> positionId) {
		this.positionId = positionId;
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
		try {
			ext.put("positionId", new ObjectMapper().writeValueAsString(positionId));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!StringUtils.isNullOrEmpty(tips)) {
			ext.put("tips", tips);
		}
		setExt(ext);
	}

}
