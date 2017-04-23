package cn.net.cvtt.resource.route.context;

import java.io.Serializable;

import cn.net.cvtt.lian.common.util.ObjectHelper;

/**
 * 表示用户定位信息的IdUri 形如 :
 * 
 * id:1001
 * 
 * @author
 */
@SuppressWarnings("serial")
public class UserContextUri extends ContextUri implements Serializable{
	private long userId;

	public UserContextUri() {

	}

	public UserContextUri(long userId) {
		this.userId = userId;
	}

	/*
	 * @see com.feinno.appengine.ContextUri#getProtocol()
	 */
	/**
	 * {在这里补充功能说明}
	 * 
	 * @return
	 */
	@Override
	public String getProtocol() {
		return "id";
	}

	/*
	 * @see com.feinno.appengine.ContextUri#getValue()
	 */
	/**
	 * {在这里补充功能说明}
	 * 
	 * @return
	 */
	@Override
	public String getValue() {
		return Long.toString(userId);
	}

	/*
	 * @see com.feinno.appengine.ContextUri#setValue(java.lang.String)
	 */
	/**
	 * {在这里补充功能说明}
	 * 
	 * @param value
	 */
	@Override
	protected void setValue(String value) {
		userId = Long.parseLong(value);
	}

	public long getUserId() {
		return userId;
	}

	/**
	 * 
	 * {在这里补充功能说明}
	 */
	@Override
	public String toString() {
		return String.format("id:%s", userId);
	}

	public static void main(String[] args) {
//		UserContextUri r = parse(UserContextUri.class, "id:11");
//		System.out.println(r.toString());
		UserContextUri uri = new UserContextUri(314324647657645234L);
		System.out.println(uri.getRouteHash());
	}

	@Override
	public int getRouteHash() {
		return ObjectHelper.compatibleGetHashCode(userId);
	}
}
