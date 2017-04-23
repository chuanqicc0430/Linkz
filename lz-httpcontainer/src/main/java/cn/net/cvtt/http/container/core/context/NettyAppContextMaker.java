package cn.net.cvtt.http.container.core.context;

import javax.servlet.http.HttpServletRequest;

import cn.net.cvtt.http.container.core.exception.PermissionDeniedException;
import cn.net.cvtt.resource.route.context.ApplicationCtx;

/**
 * 构造自定义上下文context
 * 
 * @author 
 * 
 */
public interface NettyAppContextMaker<C extends ApplicationCtx> {

	public C buildContext(HttpServletRequest httpServletRequest) throws PermissionDeniedException;
}
