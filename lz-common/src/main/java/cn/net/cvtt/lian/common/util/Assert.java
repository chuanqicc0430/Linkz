package cn.net.cvtt.lian.common.util;

/**
 * Assert辅助类
 * @see AssertException
 * @author 
 */
public class Assert
{
	public static void isTrue(boolean condition)
	{
		isTrue("", condition);
	}
	
	public static void isTrue(String message, boolean condition)
	{
		if (!condition) {
			throw new AssertException(message, "isTrue");
		}
	}

}
