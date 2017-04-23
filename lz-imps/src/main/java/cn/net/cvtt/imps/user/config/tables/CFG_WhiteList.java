package cn.net.cvtt.imps.user.config.tables;

import cn.net.cvtt.configuration.ConfigTableField;
import cn.net.cvtt.configuration.ConfigTableItem;

public class CFG_WhiteList extends ConfigTableItem {

	@ConfigTableField(value = "Id", isKeyField = true)
	private int id;

	@ConfigTableField("MobileNo")
	private long mobileNo;

	@ConfigTableField("Enabled")
	private int enabled;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return String.format("CFG_WhiteList [id=%s, mobileNo=%s, enabled=%s]", id, mobileNo, enabled);
	}

}
