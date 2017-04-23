package cn.net.cvtt.http.container.core.interceptor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * 自定义拦截器,可以成为日志，监控等的切入点
 * 
 * @author 
 *
 */
public interface NettyServletInterceptor {

	void onRequestReceived(ChannelHandlerContext ctx, HttpRequest e);

	void onRequestSuccessed(ChannelHandlerContext ctx, HttpRequest e, HttpResponse response);

	void onRequestFailed(ChannelHandlerContext ctx, Throwable e);

}
