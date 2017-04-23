package cn.net.cvtt.imps.lianzi.config.tables;

import cn.net.cvtt.configuration.ConfigTableField;
import cn.net.cvtt.configuration.ConfigTableItem;

public class CFG_ProtocolErrorRsp extends ConfigTableItem {

	@ConfigTableField(value = "ResponseCode", isKeyField = true)
	private int responseCode;

	@ConfigTableField("ResponseDesc")
	private String responseDesc;

	@ConfigTableField("ClientMsg")
	private String clientMsg;
	
	@ConfigTableField("ClientType")
	private int clientType;

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDesc() {
		return responseDesc;
	}

	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}

	public String getClientMsg() {
		return clientMsg;
	}

	public void setClientMsg(String clientMsg) {
		this.clientMsg = clientMsg;
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
		builder.append("CFG_ProtocolErrorRsp [responseCode=");
		builder.append(responseCode);
		builder.append(", responseDesc=");
		builder.append(responseDesc);
		builder.append(", clientMsg=");
		builder.append(clientMsg);
		builder.append(", clientType=");
		builder.append(clientType);
		builder.append("]");
		return builder.toString();
	}

}
