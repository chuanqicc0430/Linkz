package cn.net.cvtt.lian.common.serialization.bytecode.util;

import java.io.DataInputStream;
import java.io.InputStream;

/**
 * 这是一个DataInputStream的类型的类，起到将其他inputstream转换为可以计数的inputstream的功能
 * 
 * @author 
 * 
 */
public class DataInputStreamDecoration extends DataInputStream {

	public DataInputStreamDecoration(InputStream input) {
		this(input, 0);
	}

	public DataInputStreamDecoration(InputStream input, int offset) {
		super(new ReadNumberInputSterm(input, offset));
	}

	public int getOffset() {
		return ((ReadNumberInputSterm) super.in).getOffset();
	}
}
