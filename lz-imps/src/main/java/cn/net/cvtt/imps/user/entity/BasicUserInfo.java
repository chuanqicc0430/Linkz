package cn.net.cvtt.imps.user.entity;

import java.util.Date;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

public class BasicUserInfo extends ProtoEntity {
	/*
	 * 用户ID，也是联子账号
	 */
	@ProtoMember(1)
	private long userId;
	/*
	 * 姓名
	 */
	@ProtoMember(2)
	private String name;
	/*
	 * 性别
	 */
	@ProtoMember(3)
	private int gender;
	/*
	 * 密码
	 */
	@ProtoMember(4)
	private String password;
	/*
	 * 环信ID
	 */
	@ProtoMember(5)
	private String easeUserName;
	/*
	 * 环信密码
	 */
	@ProtoMember(6)
	private String easeUserPwd;
	/*
	 * 手机号
	 */
	@ProtoMember(7)
	private long mobileNo;
	/*
	 * 头像的文件下载url地址
	 */
	@ProtoMember(8)
	private String portrait;
	/*
	 * 身份标识，see UserStatusFlags.class
	 */
	@ProtoMember(9)
	private int userStatusFlags;

	/*
	 * 工作地点，CN.bj.10
	 */
	@ProtoMember(10)
	private String workPlace;

	/*
	 * 注册时间
	 */
	@ProtoMember(11)
	private Date registerTime;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEaseUserName() {
		return easeUserName;
	}

	public void setEaseUserName(String easeUserName) {
		this.easeUserName = easeUserName;
	}

	public String getEaseUserPwd() {
		return easeUserPwd;
	}

	public void setEaseUserPwd(String easeUserPwd) {
		this.easeUserPwd = easeUserPwd;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public int getUserStatusFlags() {
		return userStatusFlags;
	}

	public void setUserStatusFlags(int userStatusFlags) {
		this.userStatusFlags = userStatusFlags;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	@Override
	public String toString() {
		return String.format("BasicUserInfo [userId=%s, name=%s, gender=%s, password=%s, easeUserName=%s, easeUserPwd=%s, mobileNo=%s, portrait=%s, userStatusFlags=%s, workPlace=%s, registerTime=%s]", userId, name, gender, password, easeUserName, easeUserPwd, mobileNo, portrait, userStatusFlags, workPlace, registerTime);
	}

}
