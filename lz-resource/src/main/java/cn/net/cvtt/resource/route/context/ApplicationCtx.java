package cn.net.cvtt.resource.route.context;

import java.io.IOException;

/**
 * 用户上下文context基类
 * 
 * @author zongchuanqi
 */
public abstract class ApplicationCtx {
	/**
	 * 
	 * 获取ContextUri
	 * @return
	 */
	public abstract ContextUri getContextUri();

	/**
	 * 
	 * 从数据中解码
	 * 
	 * @param datas
	 * @throws IOException
	 */
	public abstract void decode(byte[] datas) throws Exception;

	/**
	 * 
	 * 按需编码
	 * 
	 * @param demand
	 * @return
	 */
	public abstract byte[] encode(int demands) throws IOException;

}
