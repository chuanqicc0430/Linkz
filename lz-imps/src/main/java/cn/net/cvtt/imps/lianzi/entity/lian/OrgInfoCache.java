package cn.net.cvtt.imps.lianzi.entity.lian;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

public class OrgInfoCache extends ProtoEntity {
	/**
	 * 组织ID
	 */
	@ProtoMember(1)
	private int orgId;
	/**
	 * 组织全称ID
	 */
	@ProtoMember(2)
	private String orgFullName;
	/**
	 * 组织简称
	 */
	@ProtoMember(3)
	private String orgShortName;
	/**
	 * 组织总秘
	 */
	@ProtoMember(4)
	private long orgLeaderSecretry;
	/**
	 * 组织状态：1：审核中；2：未通过待修改；3：已修改待审核；4：已通过审核
	 * 
	 */
	@ProtoMember(5)
	private int orgStatus;
	/**
	 * 组织类型
	 */
	@ProtoMember(6)
	private String orgType;
	/**
	 * 组织办公常驻地
	 */
	@ProtoMember(7)
	private String orgRegion;
	/**
	 * 详细地址
	 */
	@ProtoMember(8)
	private String orgDetailAddress;
	/**
	 * 组织LOGO
	 */
	@ProtoMember(9)
	private String orgPortrait;
	/**
	 * 组织邮箱
	 */
	@ProtoMember(10)
	private String orgEmail;
	/**
	 * 组织联系方式
	 */
	@ProtoMember(11)
	private String orgPhone;
	/**
	 * 创建者
	 */
	@ProtoMember(12)
	private long creator;
	/**
	 * 创建时间
	 */
	@ProtoMember(13)
	private String createTime;

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getOrgFullName() {
		return orgFullName;
	}

	public void setOrgFullName(String orgFullName) {
		this.orgFullName = orgFullName;
	}

	public String getOrgShortName() {
		return orgShortName;
	}

	public void setOrgShortName(String orgShortName) {
		this.orgShortName = orgShortName;
	}

	public long getOrgLeaderSecretry() {
		return orgLeaderSecretry;
	}

	public void setOrgLeaderSecretry(long orgLeaderSecretry) {
		this.orgLeaderSecretry = orgLeaderSecretry;
	}

	public int getOrgStatus() {
		return orgStatus;
	}

	public void setOrgStatus(int orgStatus) {
		this.orgStatus = orgStatus;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgRegion() {
		return orgRegion;
	}

	public void setOrgRegion(String orgRegion) {
		this.orgRegion = orgRegion;
	}

	public String getOrgDetailAddress() {
		return orgDetailAddress;
	}

	public void setOrgDetailAddress(String orgDetailAddress) {
		this.orgDetailAddress = orgDetailAddress;
	}

	public String getOrgPortrait() {
		return orgPortrait;
	}

	public void setOrgPortrait(String orgPortrait) {
		this.orgPortrait = orgPortrait;
	}

	public String getOrgEmail() {
		return orgEmail;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	public String getOrgPhone() {
		return orgPhone;
	}

	public void setOrgPhone(String orgPhone) {
		this.orgPhone = orgPhone;
	}

	public long getCreator() {
		return creator;
	}

	public void setCreator(long creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return String.format("OrgInfoCache [orgId=%s, orgFullName=%s, orgShortName=%s, orgLeaderSecretry=%s, orgStatus=%s, orgType=%s, orgRegion=%s, orgDetailAddress=%s, orgPortrait=%s, orgEmail=%s, orgPhone=%s, creator=%s, createTime=%s]", orgId, orgFullName, orgShortName, orgLeaderSecretry, orgStatus, orgType, orgRegion, orgDetailAddress, orgPortrait, orgEmail, orgPhone, creator, createTime);
	}

}
