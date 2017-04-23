package cn.net.cvtt.imps.user.entity;

import java.util.List;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

public class ExtUserInfo extends ProtoEntity{
	/*
	 * 用户配置列表
	 */
	@ProtoMember(1)
	private List<UserConfig> configList;


	public List<UserConfig> getConfigList() {
		return configList;
	}

	public void setConfigList(List<UserConfig> configList) {
		this.configList = configList;
	}

}
