package cn.net.cvtt.http.container.core.interceptor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * 暂时没有用到，只是把channel放到内存了，
 * 
 * @author 
 *
 */
public class ChannelManager implements NettyServletInterceptor {

    @Override
    public void onRequestFailed(ChannelHandlerContext ctx, Throwable e) {
        ChannelThreadLocal.unset();
    }

    @Override
    public void onRequestReceived(ChannelHandlerContext ctx, HttpRequest e) {
        ChannelThreadLocal.set(ctx.channel());
    }

    @Override
    public void onRequestSuccessed(ChannelHandlerContext ctx, HttpRequest e,HttpResponse response) {
        ChannelThreadLocal.unset();
    }

}
