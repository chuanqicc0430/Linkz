package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;

/**
 * MethodType类型的常量
 * 
 * @author 
 * 
 */
public class ConstantMethodTypeInfo extends CPInfo {

	/**
	 * descriptor_index项的值是指向常量池中CONSTANT_Utf8_info类型数据的索引，用于标识一个方法的描述符<br>
	 */
	private int descriptorIndex;

	/**
	 * 构造方法将自己注册成为一个MethodType类型的常量池信息类
	 */
	public ConstantMethodTypeInfo(ClassFile classFile) {
		super(classFile, ConstantType.CONSTANT_METHODTYPE);
	}

	/**
	 * 构造方法将自己注册成为一个MethodType类型的常量池信息类
	 */
	public ConstantMethodTypeInfo(ClassFile classFile, int descriptorIndex) {
		super(classFile, ConstantType.CONSTANT_METHODTYPE);
		this.descriptorIndex = descriptorIndex;
		super.setLength(3);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		descriptorIndex = input.readUnsignedShort();
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
		buffer[0] = (byte) ConstantType.CONSTANT_METHODTYPE.getTag();
		buffer[1] = (byte) ((descriptorIndex >>> 8) & 0xFF);
		buffer[2] = (byte) ((descriptorIndex >>> 0) & 0xFF);
		return buffer;
	}

	public final int getDescriptorIndex() {
		return descriptorIndex;
	}

	public ConstantUTF8Info getDescriptor() {
		return (ConstantUTF8Info) super.getClassFile().getConstant_pool()[descriptorIndex];
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.getTagVerbose());
		sb.append("(tag = ").append(super.getTag()).append(") : {");
		sb.append("[").append(descriptorIndex).append("]");
		sb.append(getDescriptor());
		sb.append("}");
		return sb.toString();
	}
}
