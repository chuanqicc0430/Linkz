package cn.net.cvtt.lian.common.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * 将ByteBffer转换成InputStream
 * 
 * @author 
 */
public class BinaryBackedInputStream extends InputStream
{
	private ByteBuffer buf;

	public BinaryBackedInputStream(ByteBuffer buf)
	{
		this.buf = buf;
	}

	public synchronized int read() throws IOException
	{
		if (!buf.hasRemaining()) {
			return -1;
		}
		return buf.get() & 0xFF;
	}

	public synchronized int read(byte[] bytes, int off, int len) throws IOException
	{
		if (buf.remaining() == 0) {
			return -1; 
		} 
		
		len = Math.min(len, buf.remaining());
		buf.get(bytes, off, len);
		return len;
	}
}
