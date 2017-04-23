package cn.net.cvtt.resource.route.context;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserContextClientInfo implements Serializable{
	private int clientType;
	private String clientVersion;
	private String clientIp;

	public int getClientType() {
		return clientType;
	}

	public void setClientType(int clientType) {
		this.clientType = clientType;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	@Override
	public String toString() {
		return String.format("UserContextClientInfo [clientType=%s, clientVersion=%s, clientIp=%s]", clientType, clientVersion, clientIp);
	}

}
