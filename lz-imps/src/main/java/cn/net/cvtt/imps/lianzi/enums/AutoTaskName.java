package cn.net.cvtt.imps.lianzi.enums;

public enum AutoTaskName {
	NET_VIP_LIAN("T_NET_VIPLIAN_BROADCAST"),
	NET_PEER("T_NET_PEER_BRAODCAST"),
	NET_UP_STREAM("T_NET_UPSTREAM_BROADCAST"),
	NET_DOWN_STREAM("T_NET_DOWNSTRAM_BROADCAST");
	private String value;
	private AutoTaskName(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
