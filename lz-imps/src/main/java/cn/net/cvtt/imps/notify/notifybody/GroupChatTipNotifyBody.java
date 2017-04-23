package cn.net.cvtt.imps.notify.notifybody;

import java.util.HashMap;
import java.util.Map;

import cn.net.cvtt.imps.lianzi.enums.NotifyType;

public class GroupChatTipNotifyBody extends NotifyBody {
	private int type;
	private long lianId;
	private String lianName;
	private String tips;

	public GroupChatTipNotifyBody(String targets, long lianId,String lianName, int type, String tips) {
		super(NotifyType.CMD_GROUP_CHAT_TIP_NOTIFY.intValue(), new String[] { targets });
		setTargetType("chatgroups");
		this.type = type;
		this.lianId = lianId;
		this.lianName = lianName;
		this.tips = tips;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getLianId() {
		return lianId;
	}

	public void setLianId(long lianId) {
		this.lianId = lianId;
	}

	/**
	 * @return the lianName
	 */
	public String getLianName() {
		return lianName;
	}

	/**
	 * @param lianName the lianName to set
	 */
	public void setLianName(String lianName) {
		this.lianName = lianName;
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
		ext.put("type", String.valueOf(type));
		ext.put("lianId", String.valueOf(lianId));
		ext.put("lianName", lianName);
		ext.put("tips", tips);
		setExt(ext);
	}

}
