package cn.net.cvtt.imps.authtoken;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;
import cn.net.cvtt.lian.common.util.StringUtils;

/**
 * {@link TokenValidateResult} 心跳应答
 * 
 * @author zongchuanqi@feinno.com
 */
public class TokenValidateResult extends ProtoEntity {
	@ProtoMember(1)
	private int resultCode = 200;// 返回码

	@ProtoMember(2)
	private String resultDesc = StringUtils.EMPTY;// 提示语或者错误描述

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
}
