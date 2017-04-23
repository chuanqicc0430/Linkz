package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;

/**
 * Integer类型的常量
 * 
 * @author 
 * 
 */
public class ConstantIntegerInfo extends CPInfo {

	private int number;

	/**
	 * 构造方法将自己注册成为一个integer类型的常量池信息类
	 */
	public ConstantIntegerInfo(ClassFile classFile) {
		super(classFile, ConstantType.CONSTANT_INTEGER);
	}

	/**
	 * 构造方法将自己注册成为一个integer类型的常量池信息类
	 */
	public ConstantIntegerInfo(ClassFile classFile, int number) {
		super(classFile, ConstantType.CONSTANT_INTEGER);
		this.number = number;
		super.setLength(5);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		number = input.readInt();
		super.setLength(input.getOffset() - getLength());
	}

	/**
	 * 将当前元素转换为byte数组
	 * 
	 * @return
	 */
	@Override
	public byte[] toByteArray() {
		byte[] buffer = new byte[5];
		buffer[0] = (byte) ConstantType.CONSTANT_INTEGER.getTag();
		buffer[1] = (byte) ((number >>> 24) & 0xFF);
		buffer[2] = (byte) ((number >>> 16) & 0xFF);
		buffer[3] = (byte) ((number >>> 8) & 0xFF);
		buffer[4] = (byte) ((number >>> 0) & 0xFF);
		return buffer;
	}

	public int getValue() {
		return number;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.getTagVerbose());
		sb.append("(tag = ").append(super.getTag()).append(") : ");
		sb.append(getValue());
		return sb.toString();
	}

}
