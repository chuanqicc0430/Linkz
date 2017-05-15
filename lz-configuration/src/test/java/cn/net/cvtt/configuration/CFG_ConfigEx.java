package cn.net.cvtt.configuration;

public class CFG_ConfigEx extends ConfigTableItem {

	@ConfigTableField(value = "ConfigExId", isKeyField = true)
	private int configExId;

	@ConfigTableField("ConfigExName")
	private String configExName;

	@ConfigTableField("ConfigExDefault")
	private String configExDefault;

	public int getConfigExId() {
		return configExId;
	}

	public void setConfigExId(int configExId) {
		this.configExId = configExId;
	}

	public String getConfigExName() {
		return configExName;
	}

	public void setConfigExName(String configExName) {
		this.configExName = configExName;
	}

	public String getConfigExDefault() {
		return configExDefault;
	}

	public void setConfigExDefault(String configExDefault) {
		this.configExDefault = configExDefault;
	}

	@Override
	public String toString() {
		return String.format("CFG_ConfigEx [configExId=%s, configExName=%s, configExDefault=%s]", configExId, configExName, configExDefault);
	}

}
