package cn.net.cvtt.http.container.core.server;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.http.container.core.NettyServletContextHandler;
import cn.net.cvtt.http.container.core.configuration.NettyServletConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty server引擎，启动和关闭server，注册handler
 * 
 * @author 
 *
 */
public class NettyServletEngine {

	private final static Logger LOGGER = LoggerFactory.getLogger(NettyServletEngine.class);

	private volatile Channel serverChannel;
	private NettyServletChannelInitializer nettyServletChannelInitializer;
	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	private EventLoopGroup workerGroup = new NioEventLoopGroup();

	private NettyServletConfig nettyServletConfiguration;
	private NettyServletContextHandler nettyServletContextHandler;

	public NettyServletEngine(NettyServletConfig nettyServletConfiguration, NettyServletContextHandler nettyServletContextHandler) {
		this.nettyServletConfiguration = nettyServletConfiguration;
		this.nettyServletContextHandler = nettyServletContextHandler;
	}

	public Channel startServer() {

		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_REUSEADDR, true);

		nettyServletChannelInitializer = new NettyServletChannelInitializer(nettyServletConfiguration.getServletThreadSize(), nettyServletConfiguration.getReadIdle(), nettyServletConfiguration.getWriteIdle(), nettyServletConfiguration.getMaxChunkContentSize(), nettyServletContextHandler, this);

		bootstrap.childHandler(nettyServletChannelInitializer);
		InetSocketAddress address = null;
		if (nettyServletConfiguration.getIp() == null) {
			address = new InetSocketAddress(nettyServletConfiguration.getPort());
		} else {
			address = new InetSocketAddress(nettyServletConfiguration.getIp(), nettyServletConfiguration.getPort());
		}
		try {
			ChannelFuture sync = bootstrap.bind(address).sync();
			LOGGER.debug(String.format("NettyServletEngine------------start listen port %s", nettyServletConfiguration.getPort()));
			return sync.channel();
		} catch (InterruptedException ex) {
			LOGGER.error("NettyServletEngine init failure", ex);
			return null;
		}

	}

	public void shutdown() {
		if (nettyServletChannelInitializer != null) {
			nettyServletChannelInitializer.shutdown();
		}

		if (serverChannel != null) {
			serverChannel.close();
		}

		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}
}
