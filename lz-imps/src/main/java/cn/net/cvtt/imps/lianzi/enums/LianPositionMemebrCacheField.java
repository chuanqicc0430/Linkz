package cn.net.cvtt.imps.lianzi.enums;

public enum LianPositionMemebrCacheField {
	LIANID("lianId"),
	POSIITONID("positionId"),
	POSITIONANME("positionName"),
	POSITIONMEMBER("positionMember"),
	UPDATETIME("updateTime"),
	;
	
	private String value;

	LianPositionMemebrCacheField(String value) {
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
