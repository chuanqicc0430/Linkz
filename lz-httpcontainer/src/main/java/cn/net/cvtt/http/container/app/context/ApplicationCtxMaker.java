package cn.net.cvtt.http.container.app.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.http.container.core.context.NettyAppContextMaker;
import cn.net.cvtt.http.container.core.exception.PermissionDeniedException;
import cn.net.cvtt.lian.common.util.StringUtils;
import cn.net.cvtt.resource.route.context.ApplicationCtx;
import cn.net.cvtt.resource.route.context.NullContext;
import cn.net.cvtt.resource.route.context.UserContext;
import cn.net.cvtt.resource.route.context.UserContextBasicInfo;
import cn.net.cvtt.resource.route.context.UserContextClientInfo;

/**
 * 解析 HttpRequest，构造UserContext上下文
 * 
 * @author zongchuanqi
 *
 */
public class ApplicationCtxMaker implements NettyAppContextMaker<ApplicationCtx> {
	private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationCtxMaker.class);
	private static Map<String, UserContextClientInfo> clientMap = new HashMap<String, UserContextClientInfo>();
	private static Map<String, UserContextBasicInfo> userMap = new HashMap<String, UserContextBasicInfo>();

	@Override
	public ApplicationCtx buildContext(HttpServletRequest httpServletRequest) throws PermissionDeniedException {
		String clientInfoHeader = httpServletRequest.getHeader("Client-Info");
		if (StringUtils.isNullOrEmpty(clientInfoHeader)) {
			// throw new PermissionDeniedException("CLIENT_INFO_IS_NULL");
			return NullContext.INSTANCE;
		}
		UserContext context = new UserContext();

//		UserContextClientInfo clientInfo = clientMap.get(clientInfoHeader);
//		if (clientInfo == null) {
//			clientInfo = new UserContextClientInfo();
//			String[] clientArray = clientInfoHeader.split("/");
//			clientInfo.setClientType(ClientType.valueOf(ClientType.class, clientArray[0]).intValue());
//			clientInfo.setClientVersion(clientArray[1]);
//			clientInfo.setClientIp(httpServletRequest.getRemoteAddr());
//			clientMap.put(clientInfoHeader, clientInfo);
//		}
//		context.setClientInfo(clientInfo);
//
//		String accessToken = httpServletRequest.getHeader("Access-Token");
//		if (!StringUtils.isNullOrEmpty(accessToken)) {
//			UserContextBasicInfo userInfo = userMap.get(accessToken);
//			if (userInfo == null) {
//				userInfo = new UserContextBasicInfo();
//				try {
//					SignInInfo info = AuthKernalcHelper.getInstance().decryptSignInInfo(accessToken);
//					if (info.isExpired()) {
//						throw new PermissionDeniedException("ACCESS_TOKEN_EXPIRED");
//					}
//					userInfo.setUserId(info.getUserId());
//					userInfo.setMobileNo(info.getMobileNo());
//					userInfo.setUserStatusFlag(info.getUserStatusFlags());
//				} catch (PermissionDeniedException e) {
//					LOGGER.error(String.format("decrypt from access_token sin info expired : %s", accessToken), e);
//					throw e;
//				} catch (Exception e) {
//					LOGGER.error(String.format("decrypt from access_token error : %s", accessToken), e);
//				}
//			}
//			context.setUserInfo(userInfo);
//			context.setContextUri(userInfo.getUserId());
//		}
		return context;
	}

}
