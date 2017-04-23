package cn.net.cvtt.imps.user.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CommonResult implements Serializable {
	private int code = 200;
	private String desc = "SUCCESS";

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

	@Override
	public String toString() {
		return String.format("CommonResult [code=%s, desc=%s]", code, desc);
	}
	
}
