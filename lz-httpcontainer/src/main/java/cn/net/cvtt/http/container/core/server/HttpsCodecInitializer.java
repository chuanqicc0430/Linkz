package cn.net.cvtt.http.container.core.server;

import javax.net.ssl.SSLEngine;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

public class HttpsCodecInitializer extends ChannelInitializer<Channel> {
	private final SslContext context;

	public HttpsCodecInitializer(SslContext context) {
		this.context = context;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		SSLEngine engine = context.newEngine(ch.alloc());
		pipeline.addFirst("ssl", new SslHandler(engine)); 

		pipeline.addLast("codec", new HttpServerCodec());
	}
}
