package cn.net.cvtt.imps.lianzi.entity.track;

import java.sql.SQLException;
import java.util.Date;

import cn.net.cvtt.resource.database.DataRow;

public class LianCouncilTrack {
	private long id;
	private long lianId;
	private long orgId;
	private long userId;
	private int accessType;
	private int operationType;
	private long operator;
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

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getAccessType() {
		return accessType;
	}

	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}

	public int getOperationType() {
		return operationType;
	}

	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}

	public long getOperator() {
		return operator;
	}

	public void setOperator(long operator) {
		this.operator = operator;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public void buildLianCouncilTrack(DataRow row) throws SQLException {
		this.id = row.getLong("Id");
		this.lianId = row.getLong("LianId");
		this.orgId = row.getLong("OrgId");
		this.userId = row.getLong("UserId");
		this.accessType = row.getInt("AccessType");
		this.operationType = row.getInt("OperationType");
		this.operator = row.getLong("Operator");
		this.recordTime = row.getDateTime("RecordTime");
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TRACK_LianCouncil [id=");
		builder.append(id);
		builder.append(", lianId=");
		builder.append(lianId);
		builder.append(", orgId=");
		builder.append(orgId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", accessType=");
		builder.append(accessType);
		builder.append(", operationType=");
		builder.append(operationType);
		builder.append(", operator=");
		builder.append(operator);
		builder.append(", recordTime=");
		builder.append(recordTime);
		builder.append("]");
		return builder.toString();
	}

}
