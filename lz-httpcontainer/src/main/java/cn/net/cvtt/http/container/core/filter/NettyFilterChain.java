package cn.net.cvtt.http.container.core.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cn.net.cvtt.http.container.core.exception.PermissionDeniedException;

/**
 * 自定义过滤器，比如对特定的IP限制登录等
 * 
 * @author 
 *
 */
public class NettyFilterChain implements FilterChain {
	private List<BaseNettyFilter> filters;

	/**
	 * @param filters
	 */
	public NettyFilterChain(List<BaseNettyFilter> filters) {
		this.filters = filters;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		for (BaseNettyFilter filter : filters) {
			filter.doFilter(request, response, this);
			if (filter.isPass()) {
				continue;
			} else {
				throw new PermissionDeniedException("not pass auth filter");
			}
		}
	}
}
