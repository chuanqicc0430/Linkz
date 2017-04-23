package cn.net.cvtt.imps.lianzi.entity.lian;

import java.sql.SQLException;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;
import cn.net.cvtt.lian.common.util.DateUtil;
import cn.net.cvtt.resource.database.DataRow;

public class OrgAloneMember extends ProtoEntity {
	/**
	 * userId
	 */
	@ProtoMember(1)
	private long orgId;
	/**
	 * userId
	 */
	@ProtoMember(2)
	private long userId;
	/**
	 * 环信ID
	 */
	@ProtoMember(3)
	private String easeUserName;
	/**
	 * 手机号
	 */
	@ProtoMember(4)
	private long mobileNo;
	/**
	 * 姓名
	 */
	@ProtoMember(5)
	private String name;
	/**
	 * 头像
	 */
	@ProtoMember(6)
	private String portrait;
	/**
	 * 操作时间
	 */
	@ProtoMember(7)
	private String joinTime;

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEaseUserName() {
		return easeUserName;
	}

	public void setEaseUserName(String easeUserName) {
		this.easeUserName = easeUserName;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

	public void buildLianMemberByDataRow(DataRow row) throws SQLException {
		this.orgId = row.getLong("OrgId");
		this.userId = row.getLong("UserId");
		this.easeUserName = row.getString("EaseUserName");
		this.mobileNo = row.getLong("MobileNo");
		this.name = row.getString("Name");
		this.portrait = row.getString("Portrait");
		this.joinTime = DateUtil.formatDate(row.getDateTime("JoinTime"), DateUtil.DEFAULT_DATETIME_HYPHEN_FORMAT);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrgAloneMember [orgId=");
		builder.append(orgId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", easeUserName=");
		builder.append(easeUserName);
		builder.append(", mobileNo=");
		builder.append(mobileNo);
		builder.append(", name=");
		builder.append(name);
		builder.append(", portrait=");
		builder.append(portrait);
		builder.append(", joinTime=");
		builder.append(joinTime);
		builder.append("]");
		return builder.toString();
	}

}
