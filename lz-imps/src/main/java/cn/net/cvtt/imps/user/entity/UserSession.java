package cn.net.cvtt.imps.user.entity;

import java.util.Date;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

public class UserSession extends ProtoEntity {
	/**
	 * 登陆点ID
	 */
	@ProtoMember(1)
	private String epId;
	/**
	 * 保活时间
	 */
	@ProtoMember(2)
	private long keepAliveTime;
	/**
	 * 登录凭证
	 */
	@ProtoMember(3)
	private String credential;
	/**
	 * 机器设备码
	 */
	@ProtoMember(4)
	private String machineCode;

	public String getEpId() {
		return epId;
	}

	public void setEpId(String epId) {
		this.epId = epId;
	}

	public long getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public boolean isOnline() {
		return new Date().getTime() - this.keepAliveTime < 310 * 1000;
	}

}
