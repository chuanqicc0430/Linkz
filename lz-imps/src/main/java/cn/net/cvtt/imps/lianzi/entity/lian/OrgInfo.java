package cn.net.cvtt.imps.lianzi.entity.lian;

import java.sql.SQLException;
import java.util.List;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;
import cn.net.cvtt.lian.common.util.DateUtil;
import cn.net.cvtt.resource.database.DataRow;

public class OrgInfo extends ProtoEntity {
	/**
	 * 组织ID
	 */
	@ProtoMember(1)
	private long orgId;
	/**
	 * 组织全称
	 */
	@ProtoMember(2)
	private String orgFullName;
	/**
	 * 组织简称
	 */
	@ProtoMember(3)
	private String orgShortName;
	/**
	 * 组织状态：1：审核中；2：未通过待修改；3：已修改待审核；4：已通过审核
	 */
	@ProtoMember(4)
	private int orgStatus;
	/**
	 * 组织类型
	 */
	@ProtoMember(5)
	private String orgType;
	/**
	 * 组织办公驻地
	 */
	@ProtoMember(6)
	private String officeAddress;

	/**
	 * 组织详细地址
	 */
	@ProtoMember(7)
	private String detailAddress;
	/**
	 * 组织LOGO
	 */
	@ProtoMember(8)
	private String orgPortrait;
	/**
	 * 组织LOGO
	 */
	@ProtoMember(9)
	private List<String> orgIndustry;
	/**
	 * 组织邮箱
	 */
	@ProtoMember(10)
	private String orgEmail;
	/**
	 * 组织联系方式
	 */
	@ProtoMember(11)
	private String orgContactPhone;
	/**
	 * 创建者
	 */
	@ProtoMember(12)
	private long creatorUserId;
	/**
	 * 创建时间
	 */
	@ProtoMember(13)
	private String createTime;
	/**
	 * 是否开通联网站
	 */
	@ProtoMember(14)
	private int orgNetFlag;

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
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

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getOrgPortrait() {
		return orgPortrait;
	}

	public void setOrgPortrait(String orgPortrait) {
		this.orgPortrait = orgPortrait;
	}

	public List<String> getOrgIndustry() {
		return orgIndustry;
	}

	public void setOrgIndustry(List<String> orgIndustry) {
		this.orgIndustry = orgIndustry;
	}

	public String getOrgEmail() {
		return orgEmail;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	public String getOrgContactPhone() {
		return orgContactPhone;
	}

	public void setOrgContactPhone(String orgContactPhone) {
		this.orgContactPhone = orgContactPhone;
	}

	public long getCreatorUserId() {
		return creatorUserId;
	}

	public void setCreatorUserId(long creatorUserId) {
		this.creatorUserId = creatorUserId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getOrgNetFlag() {
		return orgNetFlag;
	}

	public void setOrgNetFlag(int orgNetFlag) {
		this.orgNetFlag = orgNetFlag;
	}

	public void buildOrgInfoFromDTable(DataRow row) throws SQLException {
		this.setOrgId(row.getLong("OrgId"));
		this.setOrgFullName(row.getString("OrgFullName"));
		this.setOrgShortName(row.getString("OrgShortName"));
		this.setOrgStatus(row.getInt("OrgStatus"));
		this.setOrgType(row.getString("OrgType"));
		this.setOfficeAddress(row.getString("OfficeAddress"));
		this.setDetailAddress(row.getString("DetailAddress"));
		this.setOrgPortrait(row.getString("OrgPortrait"));
		this.setOrgEmail(row.getString("OrgEmail"));
		this.setOrgContactPhone(row.getString("OrgContactPhone"));
		this.setCreatorUserId(row.getLong("Creator"));
		this.setCreateTime(DateUtil.formatDate(row.getDateTime("CreateTime"), DateUtil.DEFAULT_DATETIME_HYPHEN_FORMAT));
		this.setOrgNetFlag(row.getInt("OrgNetFlag"));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrgInfo [orgId=");
		builder.append(orgId);
		builder.append(", orgFullName=");
		builder.append(orgFullName);
		builder.append(", orgShortName=");
		builder.append(orgShortName);
		builder.append(", orgStatus=");
		builder.append(orgStatus);
		builder.append(", orgType=");
		builder.append(orgType);
		builder.append(", officeAddress=");
		builder.append(officeAddress);
		builder.append(", detailAddress=");
		builder.append(detailAddress);
		builder.append(", orgPortrait=");
		builder.append(orgPortrait);
		builder.append(", orgIndustry=");
		builder.append(orgIndustry);
		builder.append(", orgEmail=");
		builder.append(orgEmail);
		builder.append(", orgContactPhone=");
		builder.append(orgContactPhone);
		builder.append(", creatorUserId=");
		builder.append(creatorUserId);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", orgNetFlag=");
		builder.append(orgNetFlag);
		builder.append("]");
		return builder.toString();
	}

}
