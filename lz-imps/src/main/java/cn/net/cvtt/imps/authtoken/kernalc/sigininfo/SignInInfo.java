package cn.net.cvtt.imps.authtoken.kernalc.sigininfo;

import java.security.SecureRandom;
import java.util.Date;

import cn.net.cvtt.lian.common.util.DateUtil;

public class SignInInfo {

	public static SignInInfo Info;

	private static SecureRandom random = new SecureRandom();

	public SignInInfo() {
		nonce = random.nextInt(10000000);
		createTime = new Date();
		expireTime = new Date(new Date().getTime() + 10 * 60 * 1000);
	}

	// 新增方法，判断凭证是否过期，如果返回true则凭证不可用。
	public boolean isExpired() {
		return getExpireTime().getTime() <= DateUtil.getUTCNow().getTime();
	}

	// 用户的UserId
	private long userId;
	// 用户的手机号
	private long mobileNo;
	// 随机数
	private int nonce;

	// 凭证生成时间
	private Date createTime;

	// 凭证过期时间
	private Date expireTime;

	// 用户帐号状态
	private int userStatusFlags;

	// 用户登录的客户端IP
	private long userIp;

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

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public int getUserStatusFlags() {
		return userStatusFlags;
	}

	public void setUserStatusFlags(int userStatusFlags) {
		this.userStatusFlags = userStatusFlags;
	}

	public long getUserIp() {
		return userIp;
	}

	public void setUserIp(long userIp) {
		this.userIp = userIp;
	}

	// 序列化用户信息为字节数组
	public byte[] toBinary() throws Exception {

		return SignInInfoSerializer.serialize(this);

	}

	public static SignInInfo fromBinary(byte[] buffer, boolean isJavaUtc) throws Exception {
		return SignInInfoSerializer.deSerialize(buffer, 0, isJavaUtc);
	}

	// 从字节数组反序列化得到用户信息
	public static SignInInfo fromBinary(byte[] buffer) throws Exception {
		return fromBinary(buffer, false);
	}

	@Override
	public String toString() {
		return String.format("SignInInfo [userId=%s, mobileNo=%s, nonce=%s, createTime=%s, expireTime=%s, userStatusFlags=%s, userIp=%s]", userId, mobileNo, nonce, createTime, expireTime, userStatusFlags, userIp);
	}
	
}
