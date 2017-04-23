package cn.net.cvtt.imps.lianzi.entity.track;

import java.sql.SQLException;
import java.util.Date;

import cn.net.cvtt.resource.database.DataRow;

public class CreateOrgTrack {
	private long id;
	private long orgId;
	private int status;
	private String rejectAuthField;
	private Date recordTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRejectAuthField() {
		return rejectAuthField;
	}

	public void setRejectAuthField(String rejectAuthField) {
		this.rejectAuthField = rejectAuthField;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public void buildCreateOrgTrack(DataRow row) throws SQLException {
		this.id = row.getLong("Id");
		this.orgId = row.getLong("OrgId");
		this.status = row.getInt("Status");
		this.rejectAuthField = row.getString("RejectAuthField");
		this.recordTime = row.getDateTime("RecordTime");
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TRACK_CreateOrg [id=");
		builder.append(id);
		builder.append(", orgId=");
		builder.append(orgId);
		builder.append(", status=");
		builder.append(status);
		builder.append(", rejectAuthField=");
		builder.append(rejectAuthField);
		builder.append(", recordTime=");
		builder.append(recordTime);
		builder.append("]");
		return builder.toString();
	}

}
