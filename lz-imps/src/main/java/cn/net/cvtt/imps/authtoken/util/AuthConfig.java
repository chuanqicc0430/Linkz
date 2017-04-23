package cn.net.cvtt.imps.authtoken.util;

/**
 * 
 * @author zongchuanqi
 * 
 */
public class AuthConfig {
	private String cKey = "cvtt.net.cn";
	private int cTimeout = 60;

	public String getcKey() {
		return cKey;
	}

	public void setcKey(String cKey) {
		this.cKey = cKey;
	}

	public int getcTimeout() {
		return cTimeout;
	}

	public void setcTimeout(int cTimeout) {
		this.cTimeout = cTimeout;
	}

}