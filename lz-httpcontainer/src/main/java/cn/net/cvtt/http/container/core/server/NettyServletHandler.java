package cn.net.cvtt.http.container.core.server;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.http.container.core.NettyServletContextHandler;
import cn.net.cvtt.http.container.core.NettyServletHolder;
import cn.net.cvtt.http.container.core.adaptor.NettyServletRequest;
import cn.net.cvtt.http.container.core.adaptor.NettyServletResponse;
import cn.net.cvtt.http.container.core.context.NettyAppContextMaker;
import cn.net.cvtt.http.container.core.exception.PermissionDeniedException;
import cn.net.cvtt.http.container.core.exception.ResourceNotFoundException;
import cn.net.cvtt.http.container.core.interceptor.NettyServletInterceptor;
import cn.net.cvtt.http.container.core.utils.ServletLogUtil;
import cn.net.cvtt.http.container.core.utils.UriUtil;
import cn.net.cvtt.resource.route.context.ApplicationCtx;
import cn.net.cvtt.resource.route.context.ApplicationTx;
import cn.net.cvtt.resource.route.context.NullContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * 核心handler，处理客户端请求，编解码，执行拦截器过滤器，执行业务逻辑，处理应答等等
 * 
 * @author
 *
 */

public class NettyServletHandler extends ChannelInboundHandlerAdapter {

	private final static Logger LOGGER = LoggerFactory.getLogger(NettyServletHandler.class);

	private final ChannelGroup allChannels;
	private NettyServletContextHandler nettyServletContextHandler;

	public NettyServletHandler(NettyServletChannelInitializer nettyServletChannelInitializer, NettyServletContextHandler nettyServletContextHandler) {
		allChannels = nettyServletChannelInitializer.getAllChannels();
		this.nettyServletContextHandler = nettyServletContextHandler;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		LOGGER.debug(String.format("NettyServletEngine---Opening new channel: %s", ctx.channel()));
		allChannels.add(ctx.channel());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		FullHttpRequest httpRequest = (FullHttpRequest) msg;

		// 拦截器
		interceptorReceived(ctx, httpRequest);
		// 输出请求日志
		ServletLogUtil.recordRequestLog(httpRequest);

		if (HttpHeaders.is100ContinueExpected(httpRequest)) {
			ctx.write(new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
		}

		// build request and response
		FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		String contextPath = UriUtil.parseContextPath(httpRequest.getUri());

		NettyServletRequest nettyServletRequest = new NettyServletRequest(httpRequest, contextPath);
		NettyServletResponse nettyServletResponse = new NettyServletResponse(httpResponse);

		// 过滤器
		doFilter(nettyServletRequest, nettyServletResponse);
		// 构造servlet请求参数
		NettyServletHolder nettyServletHolder = nettyServletContextHandler.getHandler(contextPath, httpRequest.getProtocolVersion(), httpRequest.getMethod(), httpRequest, httpResponse);
		// 响应调度,处理业务逻辑
		doHandler(ctx, nettyServletHolder, httpRequest, httpResponse, nettyServletRequest, nettyServletResponse);

		interceptorSuccessed(ctx, httpRequest, httpResponse);

		// 响应请求，或者关闭连接
		nettyServletResponse.getWriter().flush();
		boolean keepAlive = HttpHeaders.isKeepAlive(httpRequest);
		if (keepAlive) {
			httpResponse.headers().set(Names.CONTENT_LENGTH, httpResponse.content().readableBytes());
			httpResponse.headers().set(Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
		}

		ChannelFuture future = ctx.write(httpResponse);

		if (!keepAlive) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
		// 应答日志
		ServletLogUtil.recordResponseLog(httpRequest.getUri(), httpResponse);
	}

	/**
	 * 
	 * @param ctx
	 * @param nettyServletResponse
	 * @param nettyServletRequest
	 * @param httpResponse
	 * @param httpRequest
	 * @param nettyServletHolder
	 * @throws IOException
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void doHandler(ChannelHandlerContext ctx, NettyServletHolder nettyServletHolder, FullHttpRequest httpRequest, FullHttpResponse httpResponse, NettyServletRequest nettyServletRequest, NettyServletResponse nettyServletResponse) throws Exception {
		if (nettyServletHolder != null) {
			NettyAppContextMaker<? extends ApplicationCtx> nettyAppContextMaker = nettyServletContextHandler.getNettyAppContextMaker();
			if (nettyAppContextMaker != null) {
				@SuppressWarnings("rawtypes")
				ApplicationTx tx = new ApplicationTx(nettyServletRequest, nettyServletResponse);
				tx.setContext(nettyAppContextMaker.buildContext(nettyServletRequest));
				nettyServletHolder.passAction(tx);
			} else {
				ApplicationTx<NullContext> tx = new ApplicationTx<NullContext>(nettyServletRequest, nettyServletResponse);
				nettyServletHolder.passAction(tx);
			}
		} else {
			throw new ResourceNotFoundException("NO_NETTY_SERVLET_HANDLER_FOUND");
		}

	}

	/**
	 * @param ctx
	 * @param httpRequest
	 */
	private void interceptorReceived(ChannelHandlerContext ctx, FullHttpRequest httpRequest) {
		if (nettyServletContextHandler.getInterceptors() != null) {
			for (NettyServletInterceptor interceptor : nettyServletContextHandler.getInterceptors()) {
				interceptor.onRequestReceived(ctx, httpRequest);
			}
		}
	}

	/**
	 * @param ctx
	 * @param httpRequest
	 */
	private void interceptorSuccessed(ChannelHandlerContext ctx, FullHttpRequest httpRequest, FullHttpResponse httpResponse) {
		if (nettyServletContextHandler.getInterceptors() != null) {
			for (NettyServletInterceptor interceptor : nettyServletContextHandler.getInterceptors()) {
				interceptor.onRequestSuccessed(ctx, httpRequest, httpResponse);
			}
		}
	}

	/**
	 * @param ctx
	 * @param httpRequest
	 */
	private void interceptorFailed(ChannelHandlerContext ctx, Throwable e) {
		if (nettyServletContextHandler.getInterceptors() != null) {
			for (NettyServletInterceptor interceptor : nettyServletContextHandler.getInterceptors()) {
				interceptor.onRequestFailed(ctx, e);
			}
		}
	}

	/**
	 * @param httpRequest
	 * @param httpResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doFilter(NettyServletRequest nettyServletRequest, NettyServletResponse nettyServletResponse) throws PermissionDeniedException {
		FilterChain filterChain = nettyServletContextHandler.getFilter();
		if (filterChain != null) {
			try {
				filterChain.doFilter(nettyServletRequest, nettyServletResponse);
			} catch (Exception e) {
				LOGGER.error("NettyServlet---------filter not pass", e);
				throw new PermissionDeniedException(e);
			}
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		LOGGER.error("NettyServletEngine---------handler error", cause);
		// 异常拦截
		interceptorFailed(ctx, cause);

		Channel ch = ctx.channel();
		if (cause instanceof IllegalArgumentException) {
			ch.close();
		} else {
			if (cause instanceof TooLongFrameException) {
				sendError(ctx, HttpResponseStatus.BAD_REQUEST);
				return;
			} else if (cause instanceof PermissionDeniedException) {
				sendError(ctx, HttpResponseStatus.UNAUTHORIZED);
				return;
			} else if (cause instanceof ResourceNotFoundException) {
				sendError(ctx, HttpResponseStatus.SERVICE_UNAVAILABLE);
				return;
			} else if (cause instanceof ServletException) {
				sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
				return;
			}
			if (ch.isActive()) {
				sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
			}
		}
		ctx.close();
	}

	private void sendError(ChannelHandlerContext ctx, HttpResponseStatus rspStatus) {
		ByteBuf content = Unpooled.copiedBuffer("Failure: " + rspStatus.toString() + "\r\n", CharsetUtil.UTF_8);
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, rspStatus, content);
		response.headers().set(Names.CONTENT_TYPE, "application/json; charset=UTF-8");
		// response.headers().set(Names.CONTENT_LENGTH, 0);
		ctx.write(response).addListener(ChannelFutureListener.CLOSE);
		LOGGER.error(String.format("NettyServlet---------Response Error is %s", response));
	}

}
