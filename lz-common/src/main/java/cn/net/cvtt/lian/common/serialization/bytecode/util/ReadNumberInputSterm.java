package cn.net.cvtt.lian.common.serialization.bytecode.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 具有记录当前读取数量的一个输入流
 * 
 * @author 
 * 
 */
public class ReadNumberInputSterm extends FilterInputStream {

	private int offset;

	public ReadNumberInputSterm(InputStream input) {
		this(input, 0);
	}

	public ReadNumberInputSterm(InputStream input, int offset) {
		super(input);
		this.offset = offset;
	}

	public int read() throws IOException {
		int b = super.in.read();
		this.offset++;
		return b;
	}

	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	public int read(byte[] b, int offset, int len) throws IOException {
		int count = in.read(b, offset, len);
		this.offset += count;
		return count;

	}

	public long skip(long n) throws IOException {
		long count = in.skip(n);
		this.offset += (int) count;
		return count;
	}

	public boolean markSupported() {
		return false;
	}

	public int getOffset() {
		return this.offset;
	}

}
