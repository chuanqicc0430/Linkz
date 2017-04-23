package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;

/**
 * Float类型的常量池中元素
 * 
 * @author 
 * 
 */
public class ConstantFloatInfo extends CPInfo {

	private float number;

	/**
	 * 构造方法将自己注册成为一个Float类型的常量池信息类
	 */
	public ConstantFloatInfo(ClassFile classFile) {
		super(classFile, ConstantType.CONSTANT_FLOAT);
	}

	/**
	 * 构造方法将自己注册成为一个Float类型的常量池信息类
	 */
	public ConstantFloatInfo(ClassFile classFile, float number) {
		super(classFile, ConstantType.CONSTANT_FLOAT);
		this.number = number;
		super.setLength(5);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		number = Float.intBitsToFloat(input.readInt());
		super.setLength(input.getOffset() - getLength());
	}

	/**
	 * 将当前元素转换为byte数组
	 * 
	 * @return
	 */
	@Override
	public byte[] toByteArray() {
		int value = Float.floatToIntBits(number);
		byte[] buffer = new byte[5];
		buffer[0] = (byte) ConstantType.CONSTANT_FLOAT.getTag();
		buffer[1] = (byte) ((value >>> 24) & 0xFF);
		buffer[2] = (byte) ((value >>> 16) & 0xFF);
		buffer[3] = (byte) ((value >>> 8) & 0xFF);
		buffer[4] = (byte) ((value >>> 0) & 0xFF);
		return buffer;
	}

	public float getValue() {
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
