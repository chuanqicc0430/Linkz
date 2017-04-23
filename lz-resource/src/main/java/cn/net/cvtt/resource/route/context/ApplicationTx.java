package cn.net.cvtt.resource.route.context;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.lian.common.crypt.Base64;
import cn.net.cvtt.lian.common.util.StringUtils;

/**
 * ApplicationTx
 * 
 * @author
 */
public class ApplicationTx<C extends ApplicationCtx> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationTx.class);
	private C context = null;
	private byte[] contextData;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public ApplicationTx(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		contextData = base64decode(request.getHeader("ContextData"));
	}

	public C context() {
		return context;
	}

	public void setContext(C context) {
		this.context = context;
	}

	/**
	 * {在这里补充功能说明}
	 * 
	 * @param header
	 * @return
	 */
	private byte[] base64decode(String header) {
		return Base64.decode(header); // 暂不用decodeFast
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	/*
	 * @see com.feinno.appengine.AppTxWithContext#extractContextData()
	 */
	/**
	 * {在这里补充功能说明}
	 * 
	 * @return
	 */
	protected byte[] extractContextData() {
		return contextData;
	}

	public void processSucceed(String responseStr) {
		response.setStatus(200);
		response.setHeader("Content-Type", "application/json");
		response.setHeader("Content-Language", "en");
		if (!StringUtils.isNullOrEmpty(responseStr)) {
			response.setHeader("Content-Length", String.valueOf(responseStr.length()));
			try {
				response.getWriter().write(responseStr);
			} catch (IOException e) {
				LOGGER.error("Send success response error!", e);
			}
		}

	}

//	public void processFailed(int code) {
//		response.setStatus(202);
//		response.setHeader("Content-Type", "application/json");
//		CFG_ProtocolErrorRsp resource = ProtocolErrorRspHelper.getProtocolErrorRsp(code);
//		String responseBody = "";
//		if (resource == null) {
//			responseBody = String.format("{\"code\":\"%s\",\"desc\":\"SERVICE_ERROR\",\"msg\":\"网络异常，请检查网络设置\"}", code);
//		} else {
//			responseBody = String.format("{\"code\":\"%s\",\"desc\":\"%s\",\"msg\":\"%s\"}", code, resource.getResponseDesc(), resource.getClientMsg());
//		}
//		response.setHeader("Content-Length", String.valueOf(responseBody.length()));
//		response.setHeader("Content-Language", "en");
//		try {
//			response.getWriter().write(responseBody);
//		} catch (IOException e) {
//			LOGGER.error("Send failed response error!", e);
//		}
//	}

	public void processFailed(String responseBody) {
		response.setStatus(202);
		response.setHeader("Content-Type", "application/json");
		response.setHeader("Content-Language", "en");
		if (!StringUtils.isNullOrEmpty(responseBody)) {
			response.setHeader("Content-Length", String.valueOf(responseBody.length()));
			try {
				response.getWriter().write(responseBody);
			} catch (IOException e) {
				LOGGER.error("Send failed response error!", e);
			}
		}
	}
}
