package cn.net.cvtt.imps.lianzi.entity.lian;

import java.sql.SQLException;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;
import cn.net.cvtt.lian.common.util.DateUtil;
import cn.net.cvtt.resource.database.DataRow;

public class LianInfo extends ProtoEntity {
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
	 * 联委会环信群ID
	 */
	@ProtoMember(4)
	private String councilEaseGroupId;
	/**
	 * 全联环信群ID
	 */
	@ProtoMember(5)
	private String completeEaseGroupId;
	/**
	 * 联类型
	 */
	@ProtoMember(6)
	private String lianType;
	/**
	 * 上级联
	 */
	@ProtoMember(7)
	private long parentLianId;
	/**
	 * 联公约（守则）
	 */
	@ProtoMember(8)
	private String lianRules;
	/**
	 * 联状态：1.审核中，2.未通过审核，3.已重新提报待审核，4.已通过审核
	 */
	@ProtoMember(9)
	private int workFlowStatus;
	/**
	 * 联审核流程任务ID
	 */
	@ProtoMember(10)
	private String workFlowTaskId;
	/**
	 * 联审核流程任务ID
	 */
	@ProtoMember(11)
	private String workFlowRemark;
	/**
	 * 会员联-允许加入的身份设定
	 */
	@ProtoMember(12)
	private int identityForJoin;
	/**
	 * 联是否启用
	 */
	@ProtoMember(13)
	private int enabled;
	/**
	 * 创建者
	 */
	@ProtoMember(14)
	private long creator;
	/**
	 * 创建时间
	 */
	@ProtoMember(15)
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

	public String getCouncilEaseGroupId() {
		return councilEaseGroupId;
	}

	public void setCouncilEaseGroupId(String councilEaseGroupId) {
		this.councilEaseGroupId = councilEaseGroupId;
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

	public long getParentLianId() {
		return parentLianId;
	}

	public void setParentLianId(long parentLianId) {
		this.parentLianId = parentLianId;
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

	public String getWorkFlowTaskId() {
		return workFlowTaskId;
	}

	public void setWorkFlowTaskId(String workFlowTaskId) {
		this.workFlowTaskId = workFlowTaskId;
	}

	public String getWorkFlowRemark() {
		return workFlowRemark;
	}

	public void setWorkFlowRemark(String workFlowRemark) {
		this.workFlowRemark = workFlowRemark;
	}

	public int getIdentityForJoin() {
		return identityForJoin;
	}

	public void setIdentityForJoin(int identityForJoin) {
		this.identityForJoin = identityForJoin;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
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

	public void buildLianInfoFromDTable(DataRow row) throws SQLException {
		this.setLianId(row.getLong("LianId"));
		this.setOrgId(row.getLong("OrgId"));
		this.setLianName(row.getString("LianName"));
		// this.setCouncilEaseGroupId(row.getString("CouncilEaseGroupId"));
		// this.setCompleteEaseGroupId(row.getString("CompleteEaseGroupId"));
		this.setLianType(row.getString("LianType"));
		this.setParentLianId(row.getLong("ParentLianId"));
		this.setLianRules(row.getString("LianRules"));
		this.setWorkFlowStatus(row.getInt("WorkFlowStatus"));
		this.setWorkFlowTaskId(row.getString("WorkFlowTaskId"));
//		this.setWorkFlowRemark(row.getString(""));
		this.setIdentityForJoin(row.getInt("IdentityForJoin"));
		this.setEnabled(row.getBoolean("Enabled") ? 1 : 0);
		this.setCreator(row.getLong("Creator"));
		this.setCreateTime(DateUtil.formatDate(row.getDateTime("CreateTime"), DateUtil.DEFAULT_DATETIME_HYPHEN_FORMAT));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LianInfo [lianId=");
		builder.append(lianId);
		builder.append(", orgId=");
		builder.append(orgId);
		builder.append(", lianName=");
		builder.append(lianName);
		builder.append(", councilEaseGroupId=");
		builder.append(councilEaseGroupId);
		builder.append(", completeEaseGroupId=");
		builder.append(completeEaseGroupId);
		builder.append(", lianType=");
		builder.append(lianType);
		builder.append(", parentLianId=");
		builder.append(parentLianId);
		builder.append(", lianRules=");
		builder.append(lianRules);
		builder.append(", workFlowStatus=");
		builder.append(workFlowStatus);
		builder.append(", workFlowTaskId=");
		builder.append(workFlowTaskId);
		builder.append(", workFlowRemark=");
		builder.append(workFlowRemark);
		builder.append(", identityForJoin=");
		builder.append(identityForJoin);
		builder.append(", enabled=");
		builder.append(enabled);
		builder.append(", creator=");
		builder.append(creator);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append("]");
		return builder.toString();
	}

}
