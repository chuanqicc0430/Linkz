package cn.net.cvtt.imps.user.config.tables;

import cn.net.cvtt.configuration.ConfigTableField;
import cn.net.cvtt.configuration.ConfigTableItem;

public class CFG_IdPool extends ConfigTableItem {

	@ConfigTableField(value = "Id", isKeyField = true)
	private int id;

	@ConfigTableField("Number")
	private long number;

	@ConfigTableField("IsUsed")
	private int isUsed;

	@ConfigTableField("Enabled")
	private int enabled;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return String.format("CFG_IdPool [id=%s, number=%s, isUsed=%s, enabled=%s]", id, number, isUsed, enabled);
	}

}
