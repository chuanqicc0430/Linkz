package cn.net.cvtt.imps.lianzi.entity.track;

import java.sql.SQLException;
import java.util.Date;

import cn.net.cvtt.resource.database.DataRow;

public class LianMemberTrack {
	private long id;
	private long lianId;
	private long memberId;
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

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getAccessType() {
		return accessType;
	}

	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}

	/**
	 * @return the operationType
	 */
	public int getOperationType() {
		return operationType;
	}

	/**
	 * @param operationType the operationType to set
	 */
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

	public void buildLianMemberTrack(DataRow row) throws SQLException {
		this.id = row.getLong("Id");
		this.lianId = row.getLong("LianId");
		this.memberId = row.getLong("MemberId");
		this.userId = row.getLong("UserId");
		this.accessType = row.getInt("AccessType");
		this.operationType = row.getInt("OperationType");
		this.operator = row.getLong("Operator");
		this.recordTime = row.getDateTime("RecordTime");
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TRACK_LianMember [id=");
		builder.append(id);
		builder.append(", lianId=");
		builder.append(lianId);
		builder.append(", memberId=");
		builder.append(memberId);
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
