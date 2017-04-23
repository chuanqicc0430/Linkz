package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;

/**
 * MethodHandle类型的常量
 * 
 * @author 
 * 
 */
public class ConstantMethodHandleInfo extends CPInfo {

	/**
	 * reference_kind项的值必须在1至9之间(包括1和9)，它决定了方法句柄的类型，也就是reference_index指向的常量池中的类型<br>
	 */
	private int referenceKind;

	/**
	 * reference_index是一个指向常量池constant_pool的索引，当reference_kind为下面的值时，
	 * 此处常量表的类型是不同的，具体如下: <br>
	 * 1(REF_getField)、2(REF_getStatic)、3(REF_putField)或4(REF_putStatic)
	 * 是CONSTANT_Fieldref_info类型<br>
	 * 5(REF_invokeVirtual)、6(REF_invokeStatic)、7(REF_invokeSpecial)或8(
	 * REF_newInvokeSpecial)是CONSTANT_Methodref_info类型<br>
	 * 9(REF_invokeInterface)是CONSTANT_InterfaceMethodref_info类型<br>
	 */
	private int referenceIndex;

	/**
	 * 构造方法将自己注册成为一个MethodHandle类型的常量池信息类
	 */
	public ConstantMethodHandleInfo(ClassFile classFile) {
		super(classFile, ConstantType.CONSTANT_METHODHANDLE);
	}

	/**
	 * 构造方法将自己注册成为一个MethodHandle类型的常量池信息类
	 */
	public ConstantMethodHandleInfo(ClassFile classFile, int referenceKind, int referenceIndex) {
		super(classFile, ConstantType.CONSTANT_METHODHANDLE);
		this.referenceKind = referenceKind;
		this.referenceIndex = referenceIndex;
		super.setLength(4);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		referenceKind = input.readUnsignedByte();
		referenceIndex = input.readUnsignedShort();
		super.setLength(input.getOffset() - getLength());
	}

	/**
	 * 将当前元素转换为byte数组
	 * 
	 * @return
	 */
	@Override
	public byte[] toByteArray() {
		byte[] buffer = new byte[4];
		buffer[0] = (byte) ConstantType.CONSTANT_METHODHANDLE.getTag();
		buffer[1] = (byte) referenceKind;
		buffer[2] = (byte) ((referenceIndex >>> 8) & 0xFF);
		buffer[3] = (byte) ((referenceIndex >>> 0) & 0xFF);
		return buffer;
	}

	public int getReferenceKind() {
		return referenceKind;
	}

	public int getReferenceIndex() {
		return referenceIndex;
	}

	public ConstantUTF8Info getReference() {
		return (ConstantUTF8Info) super.getClassFile().getConstant_pool()[referenceIndex];
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.getTagVerbose());
		sb.append("(tag = ").append(super.getTag()).append(") : {");
		sb.append("[").append(referenceIndex).append("]");
		sb.append(getReference());
		sb.append("}");
		return sb.toString();
	}
}
