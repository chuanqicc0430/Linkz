package cn.net.cvtt.imps.user.entity;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

public class UserConfig extends ProtoEntity {
	@ProtoMember(1)
	private int configType = 0;
	@ProtoMember(2)
	private String configName = "";
	@ProtoMember(3)
	private String configValue = "";

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		if (o.getClass() == UserConfig.class) {
			UserConfig ex = (UserConfig) o;
			return ex.configType == configType;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 31 * configType;
	}

	@Override
	public String toString() {
		return "UserConfig[configType=" + configType + ",configName=" + configName + ",configValue=" + configValue + "]";
	}

	/**
	 * @return the configType
	 */
	public int getConfigType() {
		return configType;
	}

	/**
	 * @param configType
	 *            the configType to set
	 */
	public void setConfigType(int configType) {
		this.configType = configType;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	/**
	 * @return the configValue
	 */
	public String getConfigValue() {
		return configValue;
	}

	/**
	 * @param configValue
	 *            the configValue to set
	 */
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

}
