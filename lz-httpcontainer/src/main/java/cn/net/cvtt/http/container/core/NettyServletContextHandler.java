package cn.net.cvtt.http.container.core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.FilterChain;

import cn.net.cvtt.http.container.core.context.NettyAppContextMaker;
import cn.net.cvtt.http.container.core.interceptor.NettyServletInterceptor;
import cn.net.cvtt.resource.route.context.ApplicationCtx;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;

/**
 * 
 * @author 
 *
 */
public class NettyServletContextHandler {

	private Map<UriHandlerMapper, NettyServletHolder> nettyServletHolders;
	private FilterChain filterChain;
	private NettyAppContextMaker<? extends ApplicationCtx> nettyAppContextMaker;
	private List<NettyServletInterceptor> interceptors;

	public NettyServletContextHandler() {
		nettyServletHolders = new ConcurrentHashMap<UriHandlerMapper, NettyServletHolder>();
	}

	public void addHandler(UriHandlerMapper uriHandlerMapper, NettyServletHolder nettyServletHolder) {
		nettyServletHolders.put(uriHandlerMapper, nettyServletHolder);
	}

	public NettyServletHolder getHandler(String uri, HttpVersion httpVersion, HttpMethod httpMethod, HttpRequest httpRequest, FullHttpResponse httpResponse) throws Exception {
		UriHandlerMapper uriHandlerMapper = new UriHandlerMapper(uri, httpVersion, httpMethod);
		NettyServletHolder nettyServletHolder = nettyServletHolders.get(uriHandlerMapper);
		return nettyServletHolder;
	}

	/**
	 * @return
	 */
	public FilterChain getFilter() {
		return filterChain;
	}

	public void setFilter(FilterChain filterChain) {
		this.filterChain = filterChain;
	}

	/**
	 * @return
	 */
	public NettyAppContextMaker<? extends ApplicationCtx> getNettyAppContextMaker() {
		return nettyAppContextMaker;
	}

	/**
	 * @param nettyAppContextMaker
	 *            the nettyAppContextMaker to set
	 */
	public void setNettyAppContextMaker(NettyAppContextMaker<? extends ApplicationCtx> nettyAppContextMaker) {
		this.nettyAppContextMaker = nettyAppContextMaker;
	}

	/**
	 * @param interceptors
	 */
	public void setInterceptors(List<NettyServletInterceptor> interceptors) {
		this.interceptors = interceptors;
	}

	/**
	 * @return the interceptors
	 */
	public List<NettyServletInterceptor> getInterceptors() {
		return interceptors;
	}

}
