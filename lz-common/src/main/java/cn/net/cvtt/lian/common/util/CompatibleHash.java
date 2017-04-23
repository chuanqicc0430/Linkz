package cn.net.cvtt.lian.common.util;

import java.util.Date;

/**
 * 
 * <b>描述: </b>hash算法工具类，与C#兼容 ,除非用于与C#程序进行兼容，否则不需要使用此类
 * <p>
 * <b>功能: </b>为了与C#兼容而编写的hash算法工具类
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * int hashCode = CompatibleHash.compatibleGetHashCode(new Date());
 * </pre>
 * <p>
 * 
 * @author 
 * 
 */
public class CompatibleHash
{
	public static int compatibleGetHashCode(int n)
	{
		return n;
	}

	public static int compatibleGetHashCode(short n)
	{
		return n;
	}

	public static int compatibleGetHashCode(Object obj)
	{
		if (obj instanceof String)
			return CompatibleGetHashCode((String) obj);
		if (obj instanceof Date)
			return CompatibleGetHashCode((Date) obj);
		return obj.hashCode();
	}

	public static int compatibleGetHashCode(byte n)
	{
		return (int) n | ((int) n << 8) | ((int) n << 16) | ((int) n << 24);
	}

	public static int compatibleGetHashCode(long n)
	{
		return ((int) n) ^ ((int) (n >> 32));
	}

	public static int CompatibleGetHashCode(Date time)
	{
		long internalTicks = time.getTime() * 10000;
		return (((int) internalTicks) ^ ((int) (internalTicks >> 32)));
	}


	/**
	 * 
	 * 为了X32和X64的兼容, 和32bit的实现一样，与C#程序产生的hashCode一样
	 * @param s
	 * @return
	 */
	public static int CompatibleGetHashCode(String s)
	{
		int num = 0x15051505;
		int num2 = num;

		int np;
		int j = 0;
		int length = s.length();
		byte[] c = s.getBytes();
		for (int i = length; i > 0; i -= 4) {
			np = (length <= j) ? 0 : c[j] | ((length > j + 1 ? c[j + 1] : 0) << 16);
			num = (((num << 5) + num) + (num >> 0x1b)) ^ np;
			if (i <= 2)
				break;

			j += 2;
			np = (length <= j) ? 0 : c[j] | ((length > j + 1 ? c[j + 1] : 0) << 16);
			num2 = (((num2 << 5) + num2) + (num2 >> 0x1b)) ^ np;
			j += 2;
		}
		return (num + (num2 * 0x5d588b65));
	}
}
