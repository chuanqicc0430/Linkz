package cn.net.cvtt.imps.lianzi.entity.lian;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

public class OrgMembers extends ProtoEntity {
	/**
	 * 联ID
	 */
	@ProtoMember(1)
	private Map<Long, List<LianMember>> lianMembers;
	/**
	 * 所属组织ID
	 */
	@ProtoMember(2)
	private List<OrgAloneMember> orgAloneMembers;

	public Map<Long, List<LianMember>> getLianMembers() {
		return lianMembers;
	}

	public void setLianMembers(Map<Long, List<LianMember>> lianMembers) {
		this.lianMembers = lianMembers;
	}

	public List<OrgAloneMember> getOrgAloneMembers() {
		return orgAloneMembers;
	}

	public void setOrgAloneMembers(List<OrgAloneMember> orgAloneMembers) {
		this.orgAloneMembers = orgAloneMembers;
	}

	public List<String> getOrgMemberEaseUserNames() {
		List<String> easeUserNames = new ArrayList<>();
		Set<String> easeUserNameSet = new HashSet<>();
		if (lianMembers != null && !lianMembers.isEmpty()) {
			for (Map.Entry<Long, List<LianMember>> entry : lianMembers.entrySet()) {
				for (LianMember member : entry.getValue()) {
					easeUserNameSet.add(member.getEaseUserName());
				}
			}
		}
		if (orgAloneMembers != null && !orgAloneMembers.isEmpty()) {
			for (OrgAloneMember member : orgAloneMembers) {
				easeUserNameSet.add(member.getEaseUserName());
			}
		}
		easeUserNames.addAll(easeUserNameSet);
		return easeUserNames;
	}

	public List<Long> getOrgMemberUserIds() {
		List<Long> userIds = new ArrayList<>();
		Set<Long> userIdSet = new HashSet<>();
		if (lianMembers != null && !lianMembers.isEmpty()) {
			for (Map.Entry<Long, List<LianMember>> entry : lianMembers.entrySet()) {
				for (LianMember member : entry.getValue()) {
					userIdSet.add(member.getUserId());
				}
			}
		}
		if (orgAloneMembers != null && !orgAloneMembers.isEmpty()) {
			for (OrgAloneMember member : orgAloneMembers) {
				userIdSet.add(member.getUserId());
			}
		}
		userIds.addAll(userIdSet);
		return userIds;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrgMembers [lianMembers=");
		builder.append(lianMembers);
		builder.append(", orgAloneMembers=");
		builder.append(orgAloneMembers);
		builder.append("]");
		return builder.toString();
	}

}
