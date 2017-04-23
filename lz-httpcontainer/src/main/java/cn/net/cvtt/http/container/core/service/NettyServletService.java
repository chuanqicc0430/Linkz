package cn.net.cvtt.http.container.core.service;

import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;

import cn.net.cvtt.http.container.app.bean.HttpApplication;
import cn.net.cvtt.http.container.core.configuration.NettyServletConfig;
import cn.net.cvtt.http.container.core.context.NettyAppContextMaker;
import cn.net.cvtt.http.container.core.interceptor.NettyServletInterceptor;
import cn.net.cvtt.resource.route.context.ApplicationCtx;

/**
 * Servlet netty的实现
 * 
 * @author 
 * 
 */
public interface NettyServletService {
	// 设置上下文生成工厂
	public void registerContextFactory(NettyAppContextMaker<? extends ApplicationCtx> nettyAppContextMaker);

	// 设置过滤器
	public void setFilterChain(FilterChain filterChain);

	// 设置拦截器，日志，session
	public void setInterceptor(List<NettyServletInterceptor> interceptors);

	// 增加和删除Application
	public void addApplication(HttpApplication<? extends ApplicationCtx> application);

	public void addApplications(Set<HttpApplication<? extends ApplicationCtx>> applicationSet);

	public void removeApplication(HttpApplication<? extends ApplicationCtx> application);

	public void removeApplications(Set<HttpApplication<? extends ApplicationCtx>> applicationSet);

	// 启动和停止
	public void start(NettyServletConfig nettyServletConfiguration);

	public void stop();
}
