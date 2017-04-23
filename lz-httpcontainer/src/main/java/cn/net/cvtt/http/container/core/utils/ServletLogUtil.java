package cn.net.cvtt.http.container.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.lian.common.util.Guid;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * 打印请求和应答日志
 * 
 * @author
 *
 */
public class ServletLogUtil {
	private final static Logger LOGGER = LoggerFactory.getLogger(ServletLogUtil.class);
	// 生成一个transactionId以供查询，线程内唯一
	private final static ThreadLocal<String> transactionId = new ThreadLocal<String>() {
		@Override
		protected String initialValue() {
			// TODO Auto-generated method stub
			return Guid.randomGuid().toStr();
		}
	};

	public static void recordRequestLog(FullHttpRequest httpRequest) {
		if (LOGGER.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append(httpRequest.toString()).append("\r\n");
			sb.append("transactionId----").append(transactionId.get()).append("\r\n");
			if (!httpRequest.getUri().contains("/dfs/")) {
				ByteBuf contentBuf = httpRequest.content();
				byte[] bytes = new byte[contentBuf.readableBytes()];
				int readerIndex = contentBuf.readerIndex();
				contentBuf.getBytes(readerIndex, bytes);
				sb.append(new String(bytes));
			}
			LOGGER.info(String.format("NettyServer------------receive request : %s", sb.toString()));
		}

	}

	public static void recordResponseLog(String requestUri, FullHttpResponse httpResponse) {
		if (LOGGER.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append(httpResponse.toString()).append("\r\n");
			sb.append("transactionId----").append(transactionId.get()).append("\r\n");
			if (!requestUri.contains("/dfs/")) {
				ByteBuf contentBuf = httpResponse.content();
				byte[] bytes = new byte[contentBuf.readableBytes()];
				int readerIndex = contentBuf.readerIndex();
				contentBuf.getBytes(readerIndex, bytes);
				sb.append(new String(bytes));
			}
			LOGGER.info(String.format("NettyServer------------return response : %s", sb.toString()));
		}
	}
}
