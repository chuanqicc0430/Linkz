package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;

/**
 * 这是一个默认的属性类，当某一个属性类无法解析时，此类上场，此类为全能型选手，所有属性通吃
 * 
 * @author 
 * 
 */
public class DefaultAttribute extends Attribute {

	private byte[] buffer;

	public DefaultAttribute(ClassFile classFile, int nameIndex, String name) {
		super(classFile, nameIndex, name);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		buffer = new byte[super.getAttributeLength()];
		input.readFully(buffer);
	}

	/**
	 * 将当前元素转换为byte数组
	 * 
	 * @return
	 */
	@Override
	public byte[] toByteArray() {
		int offset = 0;
		byte[] buffer = new byte[getLength()];
		buffer[offset++] = (byte) ((nameIndex >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((nameIndex >>> 0) & 0xFF);
		buffer[offset++] = (byte) ((attributeLength >>> 24) & 0xFF);
		buffer[offset++] = (byte) ((attributeLength >>> 16) & 0xFF);
		buffer[offset++] = (byte) ((attributeLength >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((attributeLength >>> 0) & 0xFF);
		System.arraycopy(this.buffer, 0, buffer, offset, this.buffer.length);
		return buffer;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("DefaultAttribute:{");
		sb.append("attributeName = ").append(getName());
		sb.append("; attributeLength = ").append(attributeLength);
		sb.append("}");
		return sb.toString();
	}

}
