package cn.net.cvtt.imps.lianzi.entity.lian;

import java.sql.SQLException;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;
import cn.net.cvtt.lian.common.util.DateUtil;
import cn.net.cvtt.resource.database.DataRow;

public class LianPosition extends ProtoEntity {
	@ProtoMember(1)
	private long lianId;
	/**
	 * 岗位ID
	 */
	@ProtoMember(2)
	private long positionId;
	/**
	 * 岗位名称
	 */
	@ProtoMember(3)
	private String positionName;
	
	@ProtoMember(4)
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
	
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void buildLianPosition(DataRow dr) throws SQLException{
		this.lianId = dr.getLong("LianId");
		this.positionId = dr.getLong("PositionId");
		this.positionName = dr.getString("PositionName");
		this.updateTime = DateUtil.formatDate(dr.getDateTime("UpdateTime"), DateUtil.DEFAULT_DATETIME_HYPHEN_FORMAT);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LianPosition [lianId=");
		builder.append(lianId);
		builder.append(", positionId=");
		builder.append(positionId);
		builder.append(", positionName=");
		builder.append(positionName);
		builder.append("]");
		return builder.toString();
	}

}
