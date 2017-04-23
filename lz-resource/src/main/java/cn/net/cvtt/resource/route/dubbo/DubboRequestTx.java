package cn.net.cvtt.resource.route.dubbo;

import cn.net.cvtt.resource.route.context.ApplicationCtx;

/**
 * DubboRequestTx
 * 
 * @author
 */
@SuppressWarnings("hiding")
public class DubboRequestTx<ApplicationCtx, A> {
	private ApplicationCtx context;
	private A request;
	
	public DubboRequestTx(ApplicationCtx context, A request) {
		super();
		this.context = context;
		this.request = request;
	}

	/**
	 * @return the context
	 */
	public ApplicationCtx getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(ApplicationCtx context) {
		this.context = context;
	}

	/**
	 * @return the request
	 */
	public A getRequest() {
		return request;
	}

	/**
	 * @param request
	 *            the request to set
	 */
	public void setRequest(A request) {
		this.request = request;
	}
}
