package cn.net.cvtt.imps.lianzi.entity.track;

import java.sql.SQLException;
import java.util.Date;

import cn.net.cvtt.resource.database.DataRow;

public class CreateLianTrack {
	private long id;
	private long lianId;
	private long orgId;
	private long operator;
	private int workFlowStatus;
	private String workFlowTaskId;
	private String workFlowRemark;
	private Date recordTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLianId() {
		return lianId;
	}

	public void setLianId(long lianId) {
		this.lianId = lianId;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public long getOperator() {
		return operator;
	}

	public void setOperator(long operator) {
		this.operator = operator;
	}

	public int getWorkFlowStatus() {
		return workFlowStatus;
	}

	public void setWorkFlowStatus(int workFlowStatus) {
		this.workFlowStatus = workFlowStatus;
	}

	public String getWorkFlowTaskId() {
		return workFlowTaskId;
	}

	public void setWorkFlowTaskId(String workFlowTaskId) {
		this.workFlowTaskId = workFlowTaskId;
	}

	public String getWorkFlowRemark() {
		return workFlowRemark;
	}

	public void setWorkFlowRemark(String workFlowRemark) {
		this.workFlowRemark = workFlowRemark;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public void buildCreateLianTrack(DataRow row) throws SQLException {
		this.id = row.getLong("Id");
		this.lianId = row.getLong("LianId");
		this.orgId = row.getLong("OrgId");
		this.operator = row.getLong("Operator");
		this.workFlowStatus = row.getInt("WorkFlowStatus");
		this.workFlowTaskId = row.getString("WorkFlowTaskId");
		this.workFlowRemark = row.getString("WorkFlowRemark");
		this.recordTime = row.getDateTime("RecordTime");
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TRACK_CreateLian [id=");
		builder.append(id);
		builder.append(", lianId=");
		builder.append(lianId);
		builder.append(", orgId=");
		builder.append(orgId);
		builder.append(", operator=");
		builder.append(operator);
		builder.append(", workFlowStatus=");
		builder.append(workFlowStatus);
		builder.append(", workFlowTaskId=");
		builder.append(workFlowTaskId);
		builder.append(", workFlowRemark=");
		builder.append(workFlowRemark);
		builder.append(", recordTime=");
		builder.append(recordTime);
		builder.append("]");
		return builder.toString();
	}

}
