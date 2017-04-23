package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;

/**
 * invokedynamic类型的常量，这个是JavaSE 7中新增加的指令invokedynamic
 * 
 * @author 
 * 
 */
public class ConstantInvokeDynamicInfo extends CPInfo {

	/**
	 * 对当前Class文件中引导方法表的bootstrap_methods[]数组的有效索引。
	 */
	private int bootstrapMethodAttrIndex;

	/**
	 * 指向当前常量池中的CONSTANT_NameAndType_info类型常量，用于标识方法名和方法描述
	 */
	private int nameAndTypeIndex;

	/**
	 * 构造方法将自己注册成为一个invokedynamic类型的常量池信息类，这个是JavaSE 7中新增加的指令invokedynamic
	 */
	public ConstantInvokeDynamicInfo(ClassFile classFile) {
		super(classFile, ConstantType.CONSTANT_METHODHANDLE);
	}

	/**
	 * 构造方法将自己注册成为一个invokedynamic类型的常量池信息类，这个是JavaSE 7中新增加的指令invokedynamic
	 */
	public ConstantInvokeDynamicInfo(ClassFile classFile, int bootstrapMethodAttrIndex, int nameAndTypeIndex) {
		super(classFile, ConstantType.CONSTANT_METHODHANDLE);
		this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
		this.nameAndTypeIndex = nameAndTypeIndex;
		super.setLength(5);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		bootstrapMethodAttrIndex = input.readUnsignedShort();
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
		buffer[0] = (byte) ConstantType.CONSTANT_METHODHANDLE.getTag();
		buffer[1] = (byte) ((bootstrapMethodAttrIndex >>> 8) & 0xFF);
		buffer[2] = (byte) ((bootstrapMethodAttrIndex >>> 0) & 0xFF);
		buffer[3] = (byte) ((nameAndTypeIndex >>> 8) & 0xFF);
		buffer[4] = (byte) ((nameAndTypeIndex >>> 0) & 0xFF);
		return buffer;
	}

	// TODO 等完善了Attribute时在此处开启并返回ClassFile中属性表BootstrapMethods属性的对应索引值
	// public final int getBootstrap_method_attr() {
	// return super.getClassFile().get;
	// }

	public int getBootstrapMethodAttrIndex() {
		return bootstrapMethodAttrIndex;
	}

	public int getNameAndTypeIndex() {
		return nameAndTypeIndex;
	}

	public ConstantNameAndTypeInfo getNameAndType() {
		return (ConstantNameAndTypeInfo) super.getClassFile().getConstant_pool()[nameAndTypeIndex];
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.getTagVerbose());
		sb.append("(tag = ").append(super.getTag()).append(") : {");
		sb.append("[").append(bootstrapMethodAttrIndex).append("]");
		sb.append(getBootstrapMethodAttrIndex());
		sb.append("; ");
		sb.append("[").append(nameAndTypeIndex).append("]");
		sb.append(getNameAndType());
		sb.append("}");
		return sb.toString();
	}
}
