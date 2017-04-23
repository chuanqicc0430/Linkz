package cn.net.cvtt.http.container.core.exception;

/**
 * 请求Uri未找到对应的application，要么是请求uri写错了，要么是application注册失败了
 * 
 * @author 
 * 
 */
public class ResourceNotFoundException extends Exception {

	public ResourceNotFoundException(String message) {
		super(message);
	}

	private static final long serialVersionUID = -7502711347588945177L;

}
