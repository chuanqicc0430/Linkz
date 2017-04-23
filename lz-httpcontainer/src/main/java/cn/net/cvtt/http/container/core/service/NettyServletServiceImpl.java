package cn.net.cvtt.http.container.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.http.container.app.bean.HttpApplication;
import cn.net.cvtt.http.container.core.NettyServletContextHandler;
import cn.net.cvtt.http.container.core.NettyServletHolder;
import cn.net.cvtt.http.container.core.UriHandlerMapper;
import cn.net.cvtt.http.container.core.configuration.NettyServletConfig;
import cn.net.cvtt.http.container.core.context.NettyAppContextMaker;
import cn.net.cvtt.http.container.core.interceptor.ChannelManager;
import cn.net.cvtt.http.container.core.interceptor.NettyServletInterceptor;
import cn.net.cvtt.http.container.core.server.NettyServletEngine;
import cn.net.cvtt.lian.common.http.message.HttpMethod;
import cn.net.cvtt.lian.common.http.message.HttpPrefix;
import cn.net.cvtt.resource.route.context.ApplicationCtx;

/**
 * handler和application对接的纽带:</br> 1.启动netty server,2.管理Application，对接到http handler
 * 
 * @author 
 * 
 */
public class NettyServletServiceImpl implements NettyServletService {

	private final static Logger LOGGER = LoggerFactory.getLogger(NettyServletServiceImpl.class);
	private NettyServletEngine nettyServletEngine;
	private NettyServletContextHandler nettyServletContextHandler;
	private boolean isShutDown = false;
	private FilterChain filterChain;
	private List<NettyServletInterceptor> interceptors;

	public NettyServletServiceImpl() {
		nettyServletContextHandler = new NettyServletContextHandler();
	}

	@Override
	public void registerContextFactory(NettyAppContextMaker<? extends ApplicationCtx> nettyAppContextMaker) {
		nettyServletContextHandler.setNettyAppContextMaker(nettyAppContextMaker);
	}

	@Override
	public void setFilterChain(FilterChain filterChain) {
		this.filterChain = filterChain;

	}

	@Override
	public void setInterceptor(List<NettyServletInterceptor> interceptors) {
		this.interceptors = interceptors;

	}

	@Override
	public void addApplication(HttpApplication<? extends ApplicationCtx> application) {
		initApplication(application);
	}

	@Override
	public void addApplications(Set<HttpApplication<? extends ApplicationCtx>> applicationSet) {
		for (HttpApplication<? extends ApplicationCtx> application : applicationSet) {
			initApplication(application);
		}
	}

	@Override
	public void removeApplication(HttpApplication<? extends ApplicationCtx> application) {

	}

	@Override
	public void removeApplications(Set<HttpApplication<? extends ApplicationCtx>> applicationSet) {

	}

	@Override
	public void start(NettyServletConfig nettyServletConfiguration) {
		if (filterChain != null) {
			nettyServletContextHandler.setFilter(filterChain);
		}

		if (interceptors != null) {
			ChannelManager channelManager = new ChannelManager();
			interceptors.add(channelManager);
			nettyServletContextHandler.setInterceptors(interceptors);
		} else {
			ChannelManager channelManager = new ChannelManager();
			interceptors = new ArrayList<NettyServletInterceptor>();
			interceptors.add(channelManager);
			nettyServletContextHandler.setInterceptors(interceptors);
		}

		nettyServletEngine = new NettyServletEngine(nettyServletConfiguration, nettyServletContextHandler);
		nettyServletEngine.startServer();
		isShutDown = false;
	}

	/**
	 * 注册到nettyhandler bridge，为netty解析完uri，找对应的handler提供方法响应
	 * 
	 * @param application
	 * @return
	 */
	private void initApplication(HttpApplication<? extends ApplicationCtx> application) {
		LOGGER.debug(String.format("NettyServletEngine---init Application name is %s", application.getClass().getSimpleName()));
		NettyServletHolder nettyServletHolder = new NettyServletHolder();
		nettyServletHolder.setApplication(application);

		HttpPrefix httpPrefix = application.getClass().getAnnotation(HttpPrefix.class);
		// 请求URI
		String uriStr = httpPrefix.path();
		// 协议
		String[] protocals = httpPrefix.protocal();
		for(String protocal : protocals)
		{
			io.netty.handler.codec.http.HttpVersion httpVersion = io.netty.handler.codec.http.HttpVersion.valueOf(protocal);
			// method
			HttpMethod[] methodArray = httpPrefix.method();
			UriHandlerMapper uriHandlerMapper = null;
			for (HttpMethod method : methodArray) {
				io.netty.handler.codec.http.HttpMethod httpMethod = io.netty.handler.codec.http.HttpMethod.valueOf(method.toString());
				uriHandlerMapper = new UriHandlerMapper(uriStr, httpVersion, httpMethod);
				nettyServletHolder.setUriHandlerMapper(uriHandlerMapper);
				nettyServletContextHandler.addHandler(uriHandlerMapper, nettyServletHolder);
			}
		}
	}

	/**
	 * 防止关闭多次
	 */
	@Override
	public void stop() {
		if (!isShutDown) {
			nettyServletEngine.shutdown();
		}
		isShutDown = true;
	}
}
