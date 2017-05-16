package cn.net.cvtt.http.container.app.bean;

import cn.net.cvtt.resource.route.context.ApplicationCtx;
import cn.net.cvtt.resource.route.context.ApplicationTx;

/**
 * http application,http app 基类
 * 
 * @author zongchuanqi
 */
public abstract class HttpApplication<C extends ApplicationCtx> {
	/**
	 * {在这里补充功能说明}
	 * 
	 * @param 
	 */
	protected HttpApplication() {

	}

	public abstract void load() throws Exception;

	public abstract void unload() throws Exception;

	public abstract void process(ApplicationTx<C> tx) throws Exception;

}