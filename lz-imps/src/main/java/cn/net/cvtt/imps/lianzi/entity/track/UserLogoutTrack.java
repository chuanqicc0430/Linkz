package cn.net.cvtt.imps.lianzi.entity.track;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;

import cn.net.cvtt.resource.database.DataRow;

@SuppressWarnings("serial")
public class UserLogoutTrack implements Serializable {
	private long id;
	private long userId;
	private long mobileNo;
	private int logoutType;
	private String endpointId;
	private int clientType;
	private String clientVersion;
	private String clientIp;
	private Date logoutTime;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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

	public int getLogoutType() {
		return logoutType;
	}

	public void setLogoutType(int logoutType) {
		this.logoutType = logoutType;
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

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public void buildUserLogoutTrack(DataRow row) throws SQLException {
		this.id = row.getLong("Id");
		this.userId = row.getLong("UserId");
		this.mobileNo = row.getLong("MobileNo");
		this.logoutType = row.getInt("LogoutType");
		this.endpointId = row.getString("EndpointId");
		this.clientType = row.getInt("ClientType");
		this.clientVersion = row.getString("ClientVersion");
		this.clientIp = row.getString("ClientIp");
		this.logoutTime = row.getDateTime("LogoutTime");
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TrackUserLogoutArgs [id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", mobileNo=");
		builder.append(mobileNo);
		builder.append(", logoutType=");
		builder.append(logoutType);
		builder.append(", endpointId=");
		builder.append(endpointId);
		builder.append(", clientType=");
		builder.append(clientType);
		builder.append(", clientVersion=");
		builder.append(clientVersion);
		builder.append(", clientIp=");
		builder.append(clientIp);
		builder.append(", logoutTime=");
		builder.append(logoutTime);
		builder.append("]");
		return builder.toString();
	}

}
