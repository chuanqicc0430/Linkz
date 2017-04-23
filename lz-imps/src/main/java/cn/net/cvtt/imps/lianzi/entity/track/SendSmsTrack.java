package cn.net.cvtt.imps.lianzi.entity.track;

import java.sql.SQLException;
import java.util.Date;

import cn.net.cvtt.resource.database.DataRow;

public class SendSmsTrack {
	private long id;
	private long mobileNo;
	private int serviceType;
	private String smsContent;
	private String sendResult;
	private int retryCount;
	private Date sendTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public int getServiceType() {
		return serviceType;
	}

	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getSendResult() {
		return sendResult;
	}

	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public void buildSmsSendTrack(DataRow row) throws SQLException {
		this.id = row.getLong("Id");
		this.mobileNo = row.getLong("MobileNo");
		this.serviceType = row.getInt("ServiceType");
		this.smsContent = row.getString("SmsContent");
		this.sendResult = row.getString("SendResult");
		this.retryCount = row.getInt("RetryCount");
		this.sendTime = row.getDateTime("SendTime");
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TRACK_SendSms [id=");
		builder.append(id);
		builder.append(", mobileNo=");
		builder.append(mobileNo);
		builder.append(", serviceType=");
		builder.append(serviceType);
		builder.append(", smsContent=");
		builder.append(smsContent);
		builder.append(", sendResult=");
		builder.append(sendResult);
		builder.append(", retryCount=");
		builder.append(retryCount);
		builder.append(", sendTime=");
		builder.append(sendTime);
		builder.append("]");
		return builder.toString();
	}

}
