package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;

/**
 * long类型的常量池中元素
 * 
 * @author 
 * 
 */
public class ConstantLongInfo extends CPInfo {

	/** 高位 */
	protected int highBytes;
	/** 低位 */
	protected int lowBytes;

	/**
	 * 构造方法将自己注册成为一个long类型的常量池信息类
	 */
	public ConstantLongInfo(ClassFile classFile) {
		super(classFile, ConstantType.CONSTANT_LONG);
	}

	/**
	 * 构造方法将自己注册成为一个long类型的常量池信息类
	 */
	public ConstantLongInfo(ClassFile classFile, long number) {
		super(classFile, ConstantType.CONSTANT_LONG);
		highBytes = (int) (number >>> 32 & 0xFFFFFFFFL);
		lowBytes = (int) (number & 0xFFFFFFFFL);
		super.setLength(9);
	}

	/**
	 * 构造方法将自己注册成为一个long类型的常量池信息类
	 */
	public ConstantLongInfo(ClassFile classFile, int highBytes, int lowBytes) {
		super(classFile, ConstantType.CONSTANT_LONG);
		this.highBytes = highBytes;
		this.lowBytes = lowBytes;
		super.setLength(9);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		// class byte中是以两个4*byte存储long或double
		highBytes = input.readInt();
		lowBytes = input.readInt();
		super.setLength(input.getOffset() - getLength());
	}

	/**
	 * 将当前元素转换为byte数组
	 * 
	 * @return
	 */
	@Override
	public byte[] toByteArray() {
		byte[] buffer = new byte[9];
		buffer[0] = (byte) ConstantType.CONSTANT_LONG.getTag();
		buffer[1] = (byte) ((highBytes >>> 24) & 0xFF);
		buffer[2] = (byte) ((highBytes >>> 16) & 0xFF);
		buffer[3] = (byte) ((highBytes >>> 8) & 0xFF);
		buffer[4] = (byte) ((highBytes >>> 0) & 0xFF);
		buffer[5] = (byte) ((lowBytes >>> 24) & 0xFF);
		buffer[6] = (byte) ((lowBytes >>> 16) & 0xFF);
		buffer[7] = (byte) ((lowBytes >>> 8) & 0xFF);
		buffer[8] = (byte) ((lowBytes >>> 0) & 0xFF);
		return buffer;
	}

	public long getValue() {
		return (long) highBytes << 32 | ((long) lowBytes & 0x7FFFFFFF);
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
