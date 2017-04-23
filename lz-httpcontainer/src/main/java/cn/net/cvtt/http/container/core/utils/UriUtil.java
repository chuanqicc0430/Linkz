package cn.net.cvtt.http.container.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解析客户端请求地址
 * 
 * @author 
 *
 */
public class UriUtil {
	private final static Logger logger = LoggerFactory.getLogger(UriUtil.class);
	private final static String REGEXEXP = "^(?<url>.+?)($|\\?)";

	public static String parseContextPath(String nettyRequestUri) {
		String contextPath = null;

		Pattern pattern = Pattern.compile(REGEXEXP);
		Matcher matcher = pattern.matcher(nettyRequestUri);
		try {
			while (matcher.find()) {
				contextPath = matcher.group(1);
				break;
			}
		} catch (Exception e) {
			logger.error("parse netty request uri failure when match regexexp", e);
		}

		return contextPath;
	}

	public static void main(String[] args) {
		String contextPath = UriUtil.parseContextPath("/aa/bb/fff?aa=ffff");
		System.out.println(contextPath);

		contextPath = UriUtil.parseContextPath("/aa/bb");
		System.out.println(contextPath);
	}
}
