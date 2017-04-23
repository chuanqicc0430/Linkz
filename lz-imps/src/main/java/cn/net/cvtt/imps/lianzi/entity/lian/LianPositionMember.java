package cn.net.cvtt.imps.lianzi.entity.lian;

import java.sql.SQLException;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;
import cn.net.cvtt.lian.common.util.DateUtil;
import cn.net.cvtt.resource.database.DataRow;

public class LianPositionMember extends ProtoEntity {
	/**
	 * 联ID
	 */
	@ProtoMember(1)
	private long lianId;
	/**
	 * 所属组织ID
	 */
	@ProtoMember(2)
	private long positionId;
	/**
	 * 联名称
	 */
	@ProtoMember(3)
	private String positionName;
	/**
	 * 联委会环信群ID
	 */
	@ProtoMember(4)
	private long positionMember;
	/**
	 * 全联环信群ID
	 */
	@ProtoMember(5)
	private String updateTime;

	public long getLianId() {
		return lianId;
	}

	public void setLianId(long lianId) {
		this.lianId = lianId;
	}

	public long getPositionId() {
		return positionId;
	}

	public void setPositionId(long positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public long getPositionMember() {
		return positionMember;
	}

	public void setPositionMember(long positionMember) {
		this.positionMember = positionMember;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public void buildFromDataRow(DataRow row) throws SQLException{
		this.lianId = row.getLong("LianId");
		this.positionId = row.getLong("PositionId");
		this.positionName = row.getString("PositionName");
		this.positionMember = row.getLong("PositionMember");
		this.updateTime = DateUtil.formatDate(row.getDateTime("UpdateTime"), DateUtil.DEFAULT_DATETIME_HYPHEN_FORMAT);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LianPositionMember [lianId=");
		builder.append(lianId);
		builder.append(", positionId=");
		builder.append(positionId);
		builder.append(", positionName=");
		builder.append(positionName);
		builder.append(", positionMember=");
		builder.append(positionMember);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append("]");
		return builder.toString();
	}

}
