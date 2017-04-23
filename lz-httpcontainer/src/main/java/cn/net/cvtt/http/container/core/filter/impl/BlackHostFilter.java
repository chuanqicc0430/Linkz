package cn.net.cvtt.http.container.core.filter.impl;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cn.net.cvtt.http.container.core.configuration.NettyFilterConfig;
import cn.net.cvtt.http.container.core.filter.BaseNettyFilter;
import cn.net.cvtt.http.container.core.filter.FilterThreadLocal;

/**
 * host黑名单或者白名单之类的
 * 
 * @author 
 *
 */
public class BlackHostFilter extends FilterThreadLocal implements BaseNettyFilter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPass() {
		// TODO Auto-generated method stub
		return true;
	}

	/*private final static Logger LOGGER = LoggerFactory.getLogger(BlackHostFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		String remoteHost = servletRequest.getRemoteAddr();
		try {
			if (NettyFilterConfig.getInstance().isBlackHost(remoteHost)) {
				setIsPass(false);
				return;
			}
		} catch (CUException e) {
			LOGGER.error("Get is black host error", e);
		}
		setIsPass(true);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isPass() {
		return getPass();
	}
*/
}
