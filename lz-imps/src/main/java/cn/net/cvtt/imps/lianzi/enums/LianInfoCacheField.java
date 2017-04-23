package cn.net.cvtt.imps.lianzi.enums;

public enum LianInfoCacheField {
	LIANID("lianId"),
	ORGID("orgId"),
	LIANNAME("lianName"),
	COUNCILEASEGROUPID("councilEaseGroupId"),
	COMPLETEEASEGROUPID("completeEaseGroupId"),
	LIANTYPE("lianType"),
	PARENTLIANID("parentLianId"),
	LIANRULES("lianRules"),
	WORKFLOWSTATUS("workFlowStatus"),
	WORKFLOWTASKID("workFlowTaskId"),
	WORKFLOWREMARK("workFlowRemark"),
	IDENTITYFORJOIN("identityForJoin"),
	CREATOR("creator"),
	CREATETIME("createTime");

	private String value;

	LianInfoCacheField(String value) {
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
