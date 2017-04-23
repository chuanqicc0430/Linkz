package cn.net.cvtt.imps.lianzi.config.tables;

import cn.net.cvtt.configuration.ConfigTableField;
import cn.net.cvtt.configuration.ConfigTableItem;

public class CFG_ClientResource extends ConfigTableItem {

	@ConfigTableField(value = "code", isKeyField = true)
	private int code;

	@ConfigTableField("desc")
	private String desc;

	@ConfigTableField("msg")
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CFG_ClientResourceCode [code=");
		builder.append(code);
		builder.append(", desc=");
		builder.append(desc);
		builder.append(", msg=");
		builder.append(msg);
		builder.append("]");
		return builder.toString();
	}

}
