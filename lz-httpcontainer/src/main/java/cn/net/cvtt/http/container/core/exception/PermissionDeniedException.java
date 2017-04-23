package cn.net.cvtt.http.container.core.exception;

import javax.servlet.ServletException;

/**
 * 权限不够，被拦截器拦截
 * 
 * @author 
 * 
 */
public class PermissionDeniedException extends ServletException {

	public PermissionDeniedException(Exception e) {
		super(e);
	}

	public PermissionDeniedException(String message) {
		super(message);
	}

	private static final long serialVersionUID = -996660072304759364L;

}
