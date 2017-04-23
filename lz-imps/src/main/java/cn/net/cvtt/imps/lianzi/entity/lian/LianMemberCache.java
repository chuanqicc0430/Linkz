package cn.net.cvtt.imps.lianzi.entity.lian;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

public class LianMemberCache extends ProtoEntity {
	/**
	 * 联ID
	 */
	@ProtoMember(1)
	private long lianId;
	/**
	 * 用户ID
	 */
	@ProtoMember(2)
	private long userId;
	/**
	 * 用户环信账户
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
	 * 联昵称
	 */
	@ProtoMember(7)
	private String lianNickName;
	/**
	 * 身份
	 */
	@ProtoMember(8)
	private int identity;
	/**
	 * 联系方式
	 */
	@ProtoMember(9)
	private String contactPhone;
	/**
	 * 邮箱
	 */
	@ProtoMember(10)
	private String email;
	/**
	 * 会员联-以组织身份加入时代表的组织
	 */
	@ProtoMember(11)
	private long joinOrgId;
	/**
	 * 会员联-以组织身份加入时代表的组织名称
	 */
	@ProtoMember(12)
	private String joinOrgName;
	/**
	 * 会员联-以组织身份加入时代表的组织LOGO
	 */
	@ProtoMember(13)
	private String joinOrgPortrait;
	/**
	 * 加入时间
	 */
	@ProtoMember(14)
	private String joinTime;

	public long getLianId() {
		return lianId;
	}

	public void setLianId(long lianId) {
		this.lianId = lianId;
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

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

	@Override
	public String toString() {
		return String.format("LianMemberCache [lianId=%s, userId=%s, easeUserName=%s, mobileNo=%s, name=%s, portrait=%s, lianNickName=%s, identity=%s, contactPhone=%s, email=%s, joinOrgId=%s, joinOrgName=%s, joinOrgPortrait=%s, joinTime=%s]", lianId, userId, easeUserName, mobileNo, name, portrait, lianNickName, identity, contactPhone, email, joinOrgId, joinOrgName, joinOrgPortrait, joinTime);
	}

}
