package cn.net.cvtt.resource.route.context;

/**
 * ContextUri定位标准 一般的形式如下: id:1231232
 * 
 * 分为 id: protocol 1231232 value
 * 
 * 
 * @author
 */
public abstract class ContextUri {
	public abstract String getProtocol();

	public abstract String getValue();

	protected abstract void setValue(String value);

	public abstract String toString();

	public abstract int getRouteHash();

	/**
	 * 
	 * 解析属于ContextUri格式的ContextUri数据
	 * 
	 * @param <E>
	 * @param clazz
	 * @param uri
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static <E extends ContextUri> E parse(Class<E> clazz, String uri) {
		// 解析id:xxxx
		int first = uri.indexOf(':');
		if (first < 0) {
			throw new IllegalArgumentException("except ':' but not found:" + uri);
		}

		String protocol = uri.substring(0, first);
		E u;

		try {
			u = (E) clazz.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException("Failed to initialize uri class: " + clazz, e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Illegal uri class: " + clazz, e);
		}

		if (!protocol.equals(u.getProtocol())) {
			throw new IllegalArgumentException("Uri type not match " + u.getProtocol() + " with " + uri);
		}

		String value = uri.substring(first + 1);
		u.setValue(value);

		return u;
	}
}
