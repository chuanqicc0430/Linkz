package cn.net.cvtt.imps.lianzi.entity.track;

import java.sql.SQLException;
import java.util.Date;

import cn.net.cvtt.resource.database.DataRow;

public class LianPositionTrack {
	private long id;
	private long lianId;
	private long orgId;
	private long positionId;
	private int operationType;
	private String positionName;
	private String positionMemberName;
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

	public long getPositionId() {
		return positionId;
	}

	public void setPositionId(long positionId) {
		this.positionId = positionId;
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

	/**
	 * @return the positionName
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * @param positionName the positionName to set
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	/**
	 * @return the positionMemberName
	 */
	public String getPositionMemberName() {
		return positionMemberName;
	}

	/**
	 * @param positionMemberName the positionMemberName to set
	 */
	public void setPositionMemberName(String positionMemberName) {
		this.positionMemberName = positionMemberName;
	}

	/**
	 * @return the operator
	 */
	public long getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(long operator) {
		this.operator = operator;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public void buildLianPositionTrack(DataRow row) throws SQLException {
		this.id = row.getLong("Id");
		this.lianId = row.getLong("LianId");
		this.orgId = row.getLong("OrgId");
		this.positionId = row.getLong("PositionId");
		this.operationType = row.getInt("OperationType");
		this.positionName = row.getString("PositionName");
		this.positionMemberName = row.getString("PositionMemberName");
		this.operator = row.getLong("Operator");
		this.recordTime = row.getDateTime("RecordTime");
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TRACK_LianPosition [id=");
		builder.append(id);
		builder.append(", lianId=");
		builder.append(lianId);
		builder.append(", orgId=");
		builder.append(orgId);
		builder.append(", positionId=");
		builder.append(positionId);
		builder.append(", operationType=");
		builder.append(operationType);
		builder.append(", positionName=");
		builder.append(positionName);
		builder.append(", positionMemberName=");
		builder.append(positionMemberName);
		builder.append(", operator=");
		builder.append(operator);
		builder.append(", recordTime=");
		builder.append(recordTime);
		builder.append("]");
		return builder.toString();
	}

}
