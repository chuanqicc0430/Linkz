package cn.net.cvtt.lian.common.util;

/**
 * 断言失败异常
 * 
 * @author 
 */
public class AssertException extends RuntimeException
{
	private static final long serialVersionUID = -7224224972517328627L;
	
	public AssertException(String message, String condition)
	{
		super("Assert " + condition + ":" + message);
				
	}
}
