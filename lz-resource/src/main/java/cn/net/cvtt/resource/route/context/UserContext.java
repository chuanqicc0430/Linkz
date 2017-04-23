package cn.net.cvtt.resource.route.context;

import java.io.Serializable;

import cn.net.cvtt.resource.route.ResourceFactory;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;

/**
 * UserContext
 * 
 * @author zongchuanqi
 */
@SuppressWarnings("serial")
public final class UserContext extends ApplicationCtx implements Serializable {
	private UserContextBasicInfo userInfo;
	private UserContextClientInfo clientInfo;
	private UserContextUri contextUri;

	@Initializer
	public static void initialize() throws Exception {
		InitialUtil.init(ResourceFactory.class);
	}

	public UserContext() {

	}

	public UserContext(long userId) {
		UserContextBasicInfo userBasicInfo = new UserContextBasicInfo(userId);
		setUserInfo(userBasicInfo);
	}

	public long getUserId() {
		return userInfo.getUserId();
	}

	public UserContextBasicInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserContextBasicInfo userInfo) {
		this.userInfo = userInfo;
	}

	public UserContextClientInfo getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(UserContextClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	public void setContextUri(long userId) {
		this.contextUri = new UserContextUri(userId);
	}

	@Override
	public ContextUri getContextUri() {
		return contextUri;
	}

	@Override
	public void decode(byte[] buffer) {
	}

	@Override
	public byte[] encode(int demands) {
		return encode(0);
	}

	@Override
	public String toString() {
		return String.format("UserContext [%s, %s, %s]", userInfo == null ? "null" : userInfo.toString(), clientInfo == null ? "null" : clientInfo.toString(), contextUri == null ? "null" : contextUri.toString());
	}
}
