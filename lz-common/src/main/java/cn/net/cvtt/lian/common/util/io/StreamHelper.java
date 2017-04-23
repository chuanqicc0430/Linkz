package cn.net.cvtt.lian.common.util.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * I/O流帮助类
 * 
 * @author 
 */
public class StreamHelper
{
	/**
	 * 
	 * 读一个流，读到想要的字节数
	 * @param in
	 * @param buffer
	 * @param offset
	 * @param count
	 * @throws IOException
	 */
	public static void safeRead(InputStream in, byte[] buffer, int offset, int count) throws IOException
	{
		while (count > 0) {
			int readed = in.read(buffer, offset, count);
			if (readed < 0)
				throw new EOFException();
			offset += readed;
			count -= readed; 
		}
	}
}
