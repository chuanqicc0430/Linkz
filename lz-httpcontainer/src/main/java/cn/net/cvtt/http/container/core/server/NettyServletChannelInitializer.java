package cn.net.cvtt.http.container.core.server;

import cn.net.cvtt.http.container.core.NettyServletContextHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;

/**
 * 其实是一个ChannelInboundHandler，用来设置ChannelPipeline的channel
 * 
 * @author 
 *
 */
public class NettyServletChannelInitializer extends ChannelInitializer<Channel> {

	private final EventExecutorGroup applicationExecutor;
	private final ChannelGroup allChannels = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

	private int maxChunkContentSize;
	private int readIdle;
	private int writeIdle;
	private NettyServletContextHandler nettyServletContextHandler;

	/**
	 * @param servletThreadSize
	 * @param maxChunkContentSize
	 * @param nettyServletEngine
	 */
	public NettyServletChannelInitializer(int servletThreadSize, int maxChunkContentSize, int readIdle, int writeIdle, NettyServletContextHandler nettyServletContextHandler, NettyServletEngine nettyServletEngine) {
		this.maxChunkContentSize = maxChunkContentSize;
		this.readIdle = readIdle;
		this.writeIdle = writeIdle;
		this.applicationExecutor = new DefaultEventExecutorGroup(servletThreadSize);
		this.nettyServletContextHandler = nettyServletContextHandler;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = getDefaulHttpChannelPipeline(ch);
		pipeline.addLast(applicationExecutor, "handler", new NettyServletHandler(this, nettyServletContextHandler));
	}

	public ChannelGroup getAllChannels() {
		return allChannels;
	}

	public void shutdown() {
		allChannels.close().awaitUninterruptibly();
		applicationExecutor.shutdownGracefully();
	}

	/**
	 * @param ch
	 * @return
	 */
	private ChannelPipeline getDefaulHttpChannelPipeline(Channel channel) {
		ChannelPipeline pipeline = channel.pipeline();

		pipeline.addLast("decoder", new HttpRequestDecoder());
		pipeline.addLast("aggregator", new HttpObjectAggregator(maxChunkContentSize));
		pipeline.addLast("encoder", new HttpResponseEncoder());
		pipeline.addLast("deflater", new HttpContentCompressor());
		pipeline.addLast("idle", new IdleStateHandler(readIdle, writeIdle, 0));

		return pipeline;
	}

}
