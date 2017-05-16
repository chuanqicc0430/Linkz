package cn.net.cvtt.http.container.app.context;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.http.container.core.context.NettyAppContextMaker;
import cn.net.cvtt.http.container.core.exception.PermissionDeniedException;
import cn.net.cvtt.lian.common.util.StringUtils;
import cn.net.cvtt.resource.route.context.ApplicationCtx;
import cn.net.cvtt.resource.route.context.NullContext;
import cn.net.cvtt.resource.route.context.UserContext;

/**
 * 解析 HttpRequest，构造ApplicationCtx上下文，如客户端接入信息等，可以在此自定义
 * 
 * @author zongchuanqi
 *
 */
public class ApplicationCtxMaker implements NettyAppContextMaker<ApplicationCtx> {
	private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationCtxMaker.class);

	@Override
	public ApplicationCtx buildContext(HttpServletRequest httpServletRequest) throws PermissionDeniedException {
		String clientInfoHeader = httpServletRequest.getHeader("Client-Info");
		if (StringUtils.isNullOrEmpty(clientInfoHeader)) {
			return NullContext.INSTANCE;
		}
		UserContext context = new UserContext();

//		UserContextClientInfo clientInfo = new UserContextClientInfo();
//		String[] clientArray = clientInfoHeader.split("/");
//		clientInfo.setClientType(ClientType.valueOf(ClientType.class, clientArray[0]).intValue());
//		clientInfo.setClientVersion(clientArray[1]);
//		clientInfo.setClientIp(httpServletRequest.getRemoteAddr());
//		context.setClientInfo(clientInfo);
//
//		String accessTokenHeader = httpServletRequest.getHeader("Access-Token");
//		if (!StringUtils.isNullOrEmpty(accessTokenHeader)) {
//			UserContextBasicInfo userInfo = new UserContextBasicInfo();
//			try {
//				SignInInfo info = AuthKernalcHelper.getInstance().decryptSignInInfo(accessTokenHeader);
//				if (info.isExpired()) {
//					LOGGER.warn("access token expired : " + info.toString());
//					throw new PermissionDeniedException("ACCESS_TOKEN_EXPIRED");
//				}
//				userInfo.setUserId(info.getUserId());
//				userInfo.setMobileNo(info.getMobileNo());
//				userInfo.setUserStatusFlag(info.getUserStatusFlags());
//			} catch (PermissionDeniedException e) {
//				LOGGER.error(String.format("decrypt from access_token sin info expired : %s", accessTokenHeader), e);
//				throw e;
//			} catch (Exception e) {
//				LOGGER.error(String.format("decrypt from access_token error : %s", accessTokenHeader), e);
//			}
//			context.setUserInfo(userInfo);
//			context.setContextUri(userInfo.getUserId());
//		}
		return context;
	}

}
