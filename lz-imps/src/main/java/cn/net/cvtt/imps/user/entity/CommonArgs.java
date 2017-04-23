package cn.net.cvtt.imps.user.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CommonArgs implements Serializable {
	private long userId = 0;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return String.format("CommonArgs [userId=%s]", userId);
	}
}
