package cn.net.cvtt.imps.lianzi.entity.user;

import java.sql.SQLException;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;
import cn.net.cvtt.lian.common.util.DateUtil;
import cn.net.cvtt.resource.database.DataRow;

public class UserInfo extends ProtoEntity {
	@ProtoMember(1)
	private long userId;
	@ProtoMember(2)
	private long mobileNo;
	@ProtoMember(3)
	private String password;
	@ProtoMember(4)
	private String easeUserName;
	@ProtoMember(5)
	private String easeUserPwd;
	@ProtoMember(6)
	private String name;
	@ProtoMember(7)
	private int gender;
	@ProtoMember(8)
	private String portrait;
	@ProtoMember(9)
	private String workPlace;
	@ProtoMember(10)
	private int userStatusFlags;
	@ProtoMember(11)
	private String registerTime;

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

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public int getUserStatusFlags() {
		return userStatusFlags;
	}

	public void setUserStatusFlags(int userStatusFlags) {
		this.userStatusFlags = userStatusFlags;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public void buildUserInfoFromDTable(DataRow row) throws SQLException {
		this.userId = row.getLong("UserId");
		this.mobileNo = row.getLong("MobileNo");
		this.password = row.getString("Password");
		this.easeUserName = row.getString("EaseUserName");
		this.easeUserPwd = row.getString("EaseUserPwd");
		this.name = row.getString("Name");
		this.gender = row.getInt("Gender");
		this.portrait = row.getString("Portrait");
		this.workPlace = row.getString("WorkPlace");
		this.userStatusFlags = row.getInt("UserStatusFlags");
		if (row.getDateTime("RegisterTime") != null) {
			this.registerTime = DateUtil.formatDate(row.getDateTime("RegisterTime"), DateUtil.DEFAULT_DATETIME_HYPHEN_FORMAT);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserInfo [userId=");
		builder.append(userId);
		builder.append(", mobileNo=");
		builder.append(mobileNo);
		builder.append(", password=");
		builder.append(password);
		builder.append(", easeUserName=");
		builder.append(easeUserName);
		builder.append(", easeUserPwd=");
		builder.append(easeUserPwd);
		builder.append(", name=");
		builder.append(name);
		builder.append(", gender=");
		builder.append(gender);
		builder.append(", portrait=");
		builder.append(portrait);
		builder.append(", workPlace=");
		builder.append(workPlace);
		builder.append(", userStatusFlags=");
		builder.append(userStatusFlags);
		builder.append(", registerTime=");
		builder.append(registerTime);
		builder.append("]");
		return builder.toString();
	}

}
