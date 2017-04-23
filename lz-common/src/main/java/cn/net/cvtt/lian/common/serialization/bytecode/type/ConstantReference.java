package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;

public abstract class ConstantReference extends CPInfo {

	/** Class类型信息索引,对应常量表中的CONSTANT_Class_info */
	private int classIndex;

	/** 字段、方法、接口信息索引，对应常量表中的ConstantNameAndTypeInfo */
	private int nameAndTypeIndex;

	public ConstantReference(ClassFile classFile, ConstantType type) {
		super(classFile, type);
	}

	public ConstantReference(ClassFile classFile, ConstantType type, int classIndex, int nameAndTypeIndex) {
		super(classFile, type);
		this.classIndex = classIndex;
		this.nameAndTypeIndex = nameAndTypeIndex;
		super.setLength(5);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		classIndex = input.readUnsignedShort();
		nameAndTypeIndex = input.readUnsignedShort();
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
		buffer[0] = (byte) getConstantType().getTag();
		buffer[1] = (byte) ((classIndex >>> 8) & 0xFF);
		buffer[2] = (byte) ((classIndex >>> 0) & 0xFF);
		buffer[3] = (byte) ((nameAndTypeIndex >>> 8) & 0xFF);
		buffer[4] = (byte) ((nameAndTypeIndex >>> 0) & 0xFF);
		return buffer;
	}

	protected abstract ConstantType getConstantType();

	public int getClassIndex() {
		return classIndex;
	}

	public int getNameAndTypeIndex() {
		return nameAndTypeIndex;
	}

	public ConstantClassInfo getConstantClassInfo() {
		return (ConstantClassInfo) super.getClassFile().getConstant_pool()[classIndex];
	}

	public ConstantNameAndTypeInfo getNameAndTypeInfo() {
		return (ConstantNameAndTypeInfo) super.getClassFile().getConstant_pool()[nameAndTypeIndex];
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.getTagVerbose());
		sb.append("(tag = ").append(super.getTag()).append(") : {");
		sb.append("[").append(classIndex).append("]");
		sb.append(getConstantClassInfo());
		sb.append("; ");
		sb.append("[").append(nameAndTypeIndex).append("]");
		sb.append(getNameAndTypeInfo());
		sb.append("}");
		return sb.toString();
	}

}
