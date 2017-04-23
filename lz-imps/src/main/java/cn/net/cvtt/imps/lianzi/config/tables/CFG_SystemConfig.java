package cn.net.cvtt.imps.lianzi.config.tables;

import cn.net.cvtt.configuration.ConfigTableField;
import cn.net.cvtt.configuration.ConfigTableItem;

public class CFG_SystemConfig extends ConfigTableItem {

	@ConfigTableField(value = "Id", isKeyField = true)
	private String id;

	@ConfigTableField("ConfigType")
	private String configType;

	@ConfigTableField("ConfigName")
	private String configName;

	@ConfigTableField("ConfigValue")
	private String configValue;

	@ConfigTableField("Status")
	private int status;

	@ConfigTableField("ClientType")
	private int clientType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getClientType() {
		return clientType;
	}

	public void setClientType(int clientType) {
		this.clientType = clientType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CFG_SystemConfig [id=");
		builder.append(id);
		builder.append(", configType=");
		builder.append(configType);
		builder.append(", configName=");
		builder.append(configName);
		builder.append(", configValue=");
		builder.append(configValue);
		builder.append(", status=");
		builder.append(status);
		builder.append(", clientType=");
		builder.append(clientType);
		builder.append("]");
		return builder.toString();
	}

}
