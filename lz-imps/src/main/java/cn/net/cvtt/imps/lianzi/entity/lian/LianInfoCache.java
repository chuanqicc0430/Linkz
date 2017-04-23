package cn.net.cvtt.imps.lianzi.entity.lian;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

public class LianInfoCache extends ProtoEntity {
	/**
	 * 联ID
	 */
	@ProtoMember(1)
	private long lianId;
	/**
	 * 所属组织ID
	 */
	@ProtoMember(2)
	private long orgId;
	/**
	 * 联名称
	 */
	@ProtoMember(3)
	private String lianName;
	/**
	 * 联委会群ID
	 */
	@ProtoMember(4)
	private long councilGroupId;
	/**
	 * 联委会环信群ID
	 */
	@ProtoMember(5)
	private String councilEaseGroupId;
	/**
	 * 全联群ID
	 */
	@ProtoMember(6)
	private long completeGroupId;
	/**
	 * 全联环信群ID
	 */
	@ProtoMember(7)
	private String completeEaseGroupId;

	/**
	 * 联类型
	 */
	@ProtoMember(8)
	private String lianType;
	/**
	 * 上级联
	 */
	@ProtoMember(9)
	private int parentLianId;
	/**
	 * 联职责
	 */
	@ProtoMember(10)
	private String lianDuties;
	/**
	 * 联章程标题
	 */
	@ProtoMember(11)
	private String lianRulesTitle;
	/**
	 * 联章程
	 */
	@ProtoMember(12)
	private String lianRules;
	/**
	 * 联状态：1.待提交申请，2.待审核，3.已通过
	 */
	@ProtoMember(13)
	private int workFlowStatus;
	/**
	 * 会员联-允许加入的身份设定
	 */
	@ProtoMember(14)
	private int identityForJoin;
	/**
	 * 创建者
	 */
	@ProtoMember(15)
	private long creator;
	/**
	 * 创建时间
	 */
	@ProtoMember(16)
	private String createTime;

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

	public String getLianName() {
		return lianName;
	}

	public void setLianName(String lianName) {
		this.lianName = lianName;
	}

	public long getCouncilGroupId() {
		return councilGroupId;
	}

	public void setCouncilGroupId(long councilGroupId) {
		this.councilGroupId = councilGroupId;
	}

	public String getCouncilEaseGroupId() {
		return councilEaseGroupId;
	}

	public void setCouncilEaseGroupId(String councilEaseGroupId) {
		this.councilEaseGroupId = councilEaseGroupId;
	}

	public long getCompleteGroupId() {
		return completeGroupId;
	}

	public void setCompleteGroupId(long completeGroupId) {
		this.completeGroupId = completeGroupId;
	}

	public String getCompleteEaseGroupId() {
		return completeEaseGroupId;
	}

	public void setCompleteEaseGroupId(String completeEaseGroupId) {
		this.completeEaseGroupId = completeEaseGroupId;
	}

	public String getLianType() {
		return lianType;
	}

	public void setLianType(String lianType) {
		this.lianType = lianType;
	}

	public int getParentLianId() {
		return parentLianId;
	}

	public void setParentLianId(int parentLianId) {
		this.parentLianId = parentLianId;
	}

	public String getLianDuties() {
		return lianDuties;
	}

	public void setLianDuties(String lianDuties) {
		this.lianDuties = lianDuties;
	}

	public String getLianRulesTitle() {
		return lianRulesTitle;
	}

	public void setLianRulesTitle(String lianRulesTitle) {
		this.lianRulesTitle = lianRulesTitle;
	}

	public String getLianRules() {
		return lianRules;
	}

	public void setLianRules(String lianRules) {
		this.lianRules = lianRules;
	}

	public int getWorkFlowStatus() {
		return workFlowStatus;
	}

	public void setWorkFlowStatus(int workFlowStatus) {
		this.workFlowStatus = workFlowStatus;
	}

	public int getIdentityForJoin() {
		return identityForJoin;
	}

	public void setIdentityForJoin(int identityForJoin) {
		this.identityForJoin = identityForJoin;
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
		return String.format("LianInfoCache [lianId=%s, orgId=%s, lianName=%s, councilGroupId=%s, councilEaseGroupId=%s, completeGroupId=%s, completeEaseGroupId=%s, lianType=%s, parentLianId=%s, lianDuties=%s, lianRulesTitle=%s, lianRules=%s, workFlowStatus=%s, identityForJoin=%s, creator=%s, createTime=%s]", lianId, orgId, lianName, councilGroupId, councilEaseGroupId, completeGroupId, completeEaseGroupId, lianType, parentLianId, lianDuties, lianRulesTitle, lianRules, workFlowStatus, identityForJoin, creator, createTime);
	}

}
