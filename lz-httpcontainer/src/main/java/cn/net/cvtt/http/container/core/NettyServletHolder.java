package cn.net.cvtt.http.container.core;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.http.container.app.bean.HttpApplication;
import cn.net.cvtt.resource.route.context.ApplicationCtx;
import cn.net.cvtt.resource.route.context.ApplicationTx;

/**
 * 相当于application的代理,解耦netty server和application
 * 
 * @author 
 *
 */
public class NettyServletHolder {
	private final static Logger LOGGER = LoggerFactory.getLogger(NettyServletHolder.class);

	private HttpApplication<? extends ApplicationCtx> application;
	private UriHandlerMapper uriHandlerMapper;

	public HttpApplication<? extends ApplicationCtx> getApplication() {
		return application;
	}

	public void setApplication(HttpApplication<? extends ApplicationCtx> application) {
		this.application = application;
	}

	public UriHandlerMapper getUriHandlerMapper() {
		return uriHandlerMapper;
	}

	public void setUriHandlerMapper(UriHandlerMapper uriHandlerMapper) {
		this.uriHandlerMapper = uriHandlerMapper;
	}

	@SuppressWarnings("unchecked")
	public void passAction(@SuppressWarnings("rawtypes") ApplicationTx tx) throws ServletException {
		try {
			application.process(tx);
		} catch (Exception e) {
			LOGGER.error("do application process error", e);
			throw new ServletException("invoke application process with inner error", e);
		}
	}

}
