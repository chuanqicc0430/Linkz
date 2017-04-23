package cn.net.cvtt.imps.lianzi.entity.lian;

import java.sql.SQLException;
import java.util.List;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;
import cn.net.cvtt.lian.common.util.DateUtil;
import cn.net.cvtt.resource.database.DataRow;

public class LianMember extends ProtoEntity {
	/**
	 * lianId
	 */
	@ProtoMember(1)
	private long lianId;
	/**
	 * lianId
	 */
	@ProtoMember(2)
	private long memberId;
	/**
	 * userId
	 */
	@ProtoMember(3)
	private long userId;
	/**
	 * 环信ID
	 */
	@ProtoMember(4)
	private String easeUserName;
	/**
	 * 手机号
	 */
	@ProtoMember(5)
	private long mobileNo;
	/**
	 * 姓名
	 */
	@ProtoMember(6)
	private String name;
	/**
	 * 头像
	 */
	@ProtoMember(7)
	private String portrait;
	/**
	 * 在联中的昵称
	 */
	@ProtoMember(8)
	private String lianNickName;
	/**
	 * 在联中的身份
	 */
	@ProtoMember(9)
	private int identity;
	/**
	 * 用户在联中的授权设置
	 */
	@ProtoMember(10)
	private int permission;
	/**
	 * 联系方式
	 */
	@ProtoMember(11)
	private String contactPhone;
	/**
	 * 邮箱
	 */
	@ProtoMember(12)
	private String email;
	/**
	 * 当用户以组织身份加入本联时，用户代表的组织ID
	 */
	@ProtoMember(13)
	private long joinOrgId;
	/**
	 * 当用户以组织身份加入本联时，用户代表的组织简称
	 */
	@ProtoMember(14)
	private String joinOrgName;
	/**
	 * 当用户以组织身份加入本联时，用户代表的组织LOGO
	 */
	@ProtoMember(15)
	private String joinOrgPortrait;
	/**
	 * 当用户以组织身份加入本联时，用户代表的组织认证状态
	 */
	@ProtoMember(16)
	private int joinOrgStatus;
	/**
	 * 加入时间
	 */
	@ProtoMember(17)
	private String joinTime;
	/**
	 * 担任的岗位
	 */
	@ProtoMember(18)
	private List<Long> positionList;

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

	public String getLianNickName() {
		return lianNickName;
	}

	public void setLianNickName(String lianNickName) {
		this.lianNickName = lianNickName;
	}

	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getJoinOrgId() {
		return joinOrgId;
	}

	public void setJoinOrgId(long joinOrgId) {
		this.joinOrgId = joinOrgId;
	}

	public String getJoinOrgName() {
		return joinOrgName;
	}

	public void setJoinOrgName(String joinOrgName) {
		this.joinOrgName = joinOrgName;
	}

	public String getJoinOrgPortrait() {
		return joinOrgPortrait;
	}

	public void setJoinOrgPortrait(String joinOrgPortrait) {
		this.joinOrgPortrait = joinOrgPortrait;
	}

	public int getJoinOrgStatus() {
		return joinOrgStatus;
	}

	public void setJoinOrgStatus(int joinOrgStatus) {
		this.joinOrgStatus = joinOrgStatus;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

	public List<Long> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<Long> positionList) {
		this.positionList = positionList;
	}

	public void buildLianMemberByDataRow(DataRow row) throws SQLException {
		this.lianId = row.getLong("LianId");
		this.memberId = row.getLong("MemberId");
		this.userId = row.getLong("UserId");
		this.easeUserName = row.getString("EaseUserName");
		this.mobileNo = row.getLong("MobileNo");
		this.name = row.getString("Name");
		this.portrait = row.getString("Portrait");
		this.lianNickName = row.getString("LianNickName");
		this.identity = row.getInt("Identity");
		this.permission = row.getInt("Permission");
		this.contactPhone = row.getString("ContactPhone");
		this.email = row.getString("Email");
		this.joinOrgId = row.getLong("JoinOrgId");
		this.joinOrgName = row.getString("JoinOrgName");
		this.joinOrgPortrait = row.getString("JoinOrgPortrait");
		this.joinOrgStatus = row.getInt("JoinOrgStatus");
		this.joinTime = DateUtil.formatDate(row.getDateTime("JoinTime"), DateUtil.DEFAULT_DATETIME_HYPHEN_FORMAT);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LianMember [lianId=");
		builder.append(lianId);
		builder.append(", memberId=");
		builder.append(memberId);
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
		builder.append(", lianNickName=");
		builder.append(lianNickName);
		builder.append(", identity=");
		builder.append(identity);
		builder.append(", permission=");
		builder.append(permission);
		builder.append(", contactPhone=");
		builder.append(contactPhone);
		builder.append(", email=");
		builder.append(email);
		builder.append(", joinOrgId=");
		builder.append(joinOrgId);
		builder.append(", joinOrgName=");
		builder.append(joinOrgName);
		builder.append(", joinOrgPortrait=");
		builder.append(joinOrgPortrait);
		builder.append(", joinOrgStatus=");
		builder.append(joinOrgStatus);
		builder.append(", joinTime=");
		builder.append(joinTime);
		builder.append(", positionList=");
		builder.append(positionList);
		builder.append("]");
		return builder.toString();
	}

}
