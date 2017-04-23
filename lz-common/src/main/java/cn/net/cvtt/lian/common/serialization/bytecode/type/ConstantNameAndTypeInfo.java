package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;

/**
 * NameAndType类型的常量池中元素
 * 
 * @author 
 * 
 */
public class ConstantNameAndTypeInfo extends CPInfo {

	/** 这是一个指向常量池的CONSTANT_Utf8_info结构的表示字段或方法的非限定名 */
	protected int nameIndex;
	/** 同样也是指向常量池CONSTANT_Utf8_info结构的表示一个字段或方法的描述符 */
	protected int descriptorIndex;

	/**
	 * 构造方法将自己注册成为一个NameAndType类型的常量池信息类
	 */
	public ConstantNameAndTypeInfo(ClassFile classFile) {
		super(classFile, ConstantType.CONSTANT_NAMEANDTYPE);
	}

	/**
	 * 构造方法将自己注册成为一个NameAndType类型的常量池信息类
	 */
	public ConstantNameAndTypeInfo(ClassFile classFile, int nameIndex, int descriptorIndex) {
		super(classFile, ConstantType.CONSTANT_NAMEANDTYPE);
		this.nameIndex = nameIndex;
		this.descriptorIndex = descriptorIndex;
		super.setLength(5);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		nameIndex = input.readUnsignedShort();
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
		byte[] buffer = new byte[5];
		buffer[0] = (byte) ConstantType.CONSTANT_NAMEANDTYPE.getTag();
		buffer[1] = (byte) ((nameIndex >>> 8) & 0xFF);
		buffer[2] = (byte) ((nameIndex >>> 0) & 0xFF);
		buffer[3] = (byte) ((descriptorIndex >>> 8) & 0xFF);
		buffer[4] = (byte) ((descriptorIndex >>> 0) & 0xFF);
		return buffer;
	}

	/** 这是一个指向常量池的CONSTANT_Utf8_info结构的表示字段或方法的非限定名 */
	public ConstantUTF8Info getName() {
		return (ConstantUTF8Info) super.getClassFile().getConstant_pool()[nameIndex];
	}

	/** 同样也是指向常量池CONSTANT_Utf8_info结构的表示一个字段或方法的描述符 */
	public ConstantUTF8Info getDescriptor() {
		return (ConstantUTF8Info) super.getClassFile().getConstant_pool()[descriptorIndex];
	}

	public int getNameIndex() {
		return nameIndex;
	}

	public int getDescriptorIndex() {
		return descriptorIndex;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.getTagVerbose());
		sb.append("(tag = ").append(super.getTag()).append(") : {");
		sb.append("[").append(nameIndex).append("] name : ");
		sb.append(getName());
		sb.append("; ");
		sb.append("[").append(descriptorIndex).append("] descriptor : ");
		sb.append(getDescriptor());
		sb.append("}");
		return sb.toString();
	}

}
