package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;

/**
 * String类型的常量
 * 
 * @author 
 * 
 */
public class ConstantStringInfo extends CPInfo {

	private int nameIndex;

	/**
	 * 构造方法将自己注册成为一个String类型的常量池信息类
	 */
	public ConstantStringInfo(ClassFile classFile) {
		super(classFile, ConstantType.CONSTANT_STRING);
	}

	/**
	 * 构造方法将自己注册成为一个String类型的常量池信息类
	 */
	public ConstantStringInfo(ClassFile classFile, int nameIndex) {
		super(classFile, ConstantType.CONSTANT_STRING);
		this.nameIndex = nameIndex;
		super.setLength(3);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		nameIndex = input.readUnsignedShort();
		super.setLength(input.getOffset() - getLength());
	}

	/**
	 * 将当前元素转换为byte数组
	 * 
	 * @return
	 */
	@Override
	public byte[] toByteArray() {
		byte[] buffer = new byte[3];
		buffer[0] = (byte) ConstantType.CONSTANT_STRING.getTag();
		buffer[1] = (byte) ((nameIndex >>> 8) & 0xFF);
		buffer[2] = (byte) ((nameIndex >>> 0) & 0xFF);
		return buffer;
	}

	public int getNameIndex() {
		return nameIndex;
	}

	public ConstantUTF8Info getName() {
		return (ConstantUTF8Info) super.getClassFile().getConstant_pool()[nameIndex];
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.getTagVerbose());
		sb.append("(tag = ").append(super.getTag()).append(") : {");
		sb.append("[").append(nameIndex).append("]");
		sb.append(getName());
		sb.append("}");
		return sb.toString();
	}
}
