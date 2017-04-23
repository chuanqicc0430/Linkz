package cn.net.cvtt.resource.route.context;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserContextBasicInfo implements Serializable{
	private long userId;
	private long mobileNo;
	private int userStatusFlag;
	
	public UserContextBasicInfo(){
	
	}
	
	public UserContextBasicInfo(long userId){
		this.userId = userId;
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

	public int getUserStatusFlag() {
		return userStatusFlag;
	}

	public void setUserStatusFlag(int userStatusFlag) {
		this.userStatusFlag = userStatusFlag;
	}

	@Override
	public String toString() {
		return String.format("UserContextBasicInfo [userId=%s, mobileNo=%s, userStatusFlag=%s]", userId, mobileNo, userStatusFlag);
	}

}
