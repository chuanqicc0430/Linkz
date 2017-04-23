package cn.net.cvtt.imps.lianzi.entity.track;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;

import cn.net.cvtt.resource.database.DataRow;

@SuppressWarnings("serial")
public class UserLoginTrack implements Serializable {
	private long id;
	private long userId;
	private long mobileNo;
	private int loginType;
	private String endpointId;
	private int clientType;
	private String clientVersion;
	private String clientIp;
	private String machineCode;
	private String osVersion;
	private String phoneModel;
	private String oemTag;
	private Date loginTime;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	public String getEndpointId() {
		return endpointId;
	}

	public void setEndpointId(String endpointId) {
		this.endpointId = endpointId;
	}

	public int getClientType() {
		return clientType;
	}

	public void setClientType(int clientType) {
		this.clientType = clientType;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getOemTag() {
		return oemTag;
	}

	public void setOemTag(String oemTag) {
		this.oemTag = oemTag;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	public void buildUserLoginTrack(DataRow row) throws SQLException {
		this.id = row.getLong("Id");
		this.userId = row.getLong("UserId");
		this.mobileNo = row.getLong("MobileNo");
		this.loginType = row.getInt("LoginType");
		this.endpointId = row.getString("EndpointId");
		this.clientType = row.getInt("ClientType");
		this.clientVersion = row.getString("ClientVersion");
		this.clientIp = row.getString("ClientIp");
		this.machineCode = row.getString("MachineCode");
		this.osVersion = row.getString("OsVersion");
		this.phoneModel = row.getString("PhoneModel");
		this.oemTag = row.getString("OemTag");
		this.loginTime = row.getDateTime("LoginTime");
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TrackUserLoginArgs [id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", mobileNo=");
		builder.append(mobileNo);
		builder.append(", loginType=");
		builder.append(loginType);
		builder.append(", endpointId=");
		builder.append(endpointId);
		builder.append(", clientType=");
		builder.append(clientType);
		builder.append(", clientVersion=");
		builder.append(clientVersion);
		builder.append(", clientIp=");
		builder.append(clientIp);
		builder.append(", machineCode=");
		builder.append(machineCode);
		builder.append(", osVersion=");
		builder.append(osVersion);
		builder.append(", phoneModel=");
		builder.append(phoneModel);
		builder.append(", oemTag=");
		builder.append(oemTag);
		builder.append(", loginTime=");
		builder.append(loginTime);
		builder.append("]");
		return builder.toString();
	}

}
