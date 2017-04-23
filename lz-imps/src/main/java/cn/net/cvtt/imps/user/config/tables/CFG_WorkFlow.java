package cn.net.cvtt.imps.user.config.tables;

import cn.net.cvtt.configuration.ConfigTableField;
import cn.net.cvtt.configuration.ConfigTableItem;
import cn.net.cvtt.imps.user.config.enums.WorkFlowType;

public class CFG_WorkFlow extends ConfigTableItem {

	@ConfigTableField(value = "Id", isKeyField = true)
	private int id;

	@ConfigTableField("FlowType")
	private WorkFlowType flowType;

	@ConfigTableField("FlowDefinition")
	private String flowDefinition;

	@ConfigTableField("FlowDesc")
	private String flowDesc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public WorkFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(WorkFlowType flowType) {
		this.flowType = flowType;
	}

	public String getFlowDefinition() {
		return flowDefinition;
	}

	public void setFlowDefinition(String flowDefinition) {
		this.flowDefinition = flowDefinition;
	}

	public String getFlowDesc() {
		return flowDesc;
	}

	public void setFlowDesc(String flowDesc) {
		this.flowDesc = flowDesc;
	}

	@Override
	public String toString() {
		return String.format("CFG_WorkFlow [id=%s, flowType=%s, flowDefinition=%s, flowDesc=%s]", id, flowType, flowDefinition, flowDesc);
	}

}
