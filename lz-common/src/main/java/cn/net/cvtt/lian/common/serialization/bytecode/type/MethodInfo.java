package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

/**
 * 这是字节码文件ClassFile中方法区域，此区域存储了所有类方法或类实例方法，这个类描述了方法的详细信息，
 * 包括需要执行哪些操作码来完成这个方法所表达的含义
 * 
 * @author 
 * 
 */
public class MethodInfo extends AbstractClassElement {

	/** 方法访问修饰符，例如public、private、final、static、synchronized等 */
	private int accessFlags;

	/** 方法的名称，指向常量池中的utf8结构 */
	private int nameIndex;

	/** 方法的详细描述，此描述通过指定的语法表达了 方法的入参和返回参数 */
	private int descriptorIndex;

	/** 方法的属性数量 */
	private int attributesCount;

	/** 方法的具体属性，这个很重要，例如其中包含的CodeAttribute，其中包含了方法执行步骤的操作码、exception等 */
	private Attribute[] attributes;

	private ClassFile classFile;

	public MethodInfo(ClassFile classFile) {
		this.classFile = classFile;
	}

	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		accessFlags = input.readUnsignedShort();
		nameIndex = input.readUnsignedShort();
		descriptorIndex = input.readUnsignedShort();
		attributesCount = input.readUnsignedShort();
		attributes = new Attribute[attributesCount];
		for (int i = 0; i < attributesCount; i++) {
			int nameIndex = input.readUnsignedShort();
			String attributeName = ((ConstantUTF8Info) classFile.getConstant_pool()[nameIndex]).getValue();
			Attribute attribute = AttributeType.get(attributeName).getAttribute(classFile, nameIndex);
			attribute.read(input);
			attributes[i] = attribute;
		}
		super.setLength(input.getOffset() - super.getOffset());
	}

	@Override
	public int writeTo(ClassFile classFile, int index, OperationType operationType) throws InvalidByteCodeException,
			IOException {
		int offset = super.writeTo(classFile, classFile.getCorrectionOffset(index), operationType, new MethodInfo(
				classFile), 0);
		classFile.putCorrectionOffset(index, offset);
		return offset;
	}

	/**
	 * 将当前元素转换为byte数组
	 * 
	 * @return
	 */
	@Override
	public byte[] toByteArray() {
		int offset = 0;
		byte[] buffer = new byte[super.getLength()];
		buffer[offset++] = (byte) ((accessFlags >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((accessFlags >>> 0) & 0xFF);
		buffer[offset++] = (byte) ((nameIndex >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((nameIndex >>> 0) & 0xFF);
		buffer[offset++] = (byte) ((descriptorIndex >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((descriptorIndex >>> 0) & 0xFF);
		buffer[offset++] = (byte) ((attributesCount >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((attributesCount >>> 0) & 0xFF);
		for (int i = 0; i < attributesCount; i++) {
			byte[] temp = attributes[i].toByteArray();
			System.arraycopy(temp, 0, buffer, offset, temp.length);
			offset += temp.length;
		}
		return buffer;
	}

	public ConstantUTF8Info getName() {
		return (ConstantUTF8Info) classFile.getConstant_pool()[nameIndex];
	}

	public ConstantUTF8Info getDescriptor() {
		return (ConstantUTF8Info) classFile.getConstant_pool()[descriptorIndex];
	}

	public ClassFile getClassFile() {
		return classFile;
	}

	public int getAccessFlags() {
		return accessFlags;
	}

	public int getNameIndex() {
		return nameIndex;
	}

	public int getDescriptorIndex() {
		return descriptorIndex;
	}

	public int getAttributesCount() {
		return attributesCount;
	}

	public Attribute[] getAttributes() {
		return attributes;
	}

	public void setAccessFlags(int accessFlags) {
		this.accessFlags = accessFlags;
	}

	public void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}

	public void setDescriptorIndex(int descriptorIndex) {
		this.descriptorIndex = descriptorIndex;
	}

	public void setAttributesCount(int attributesCount) {
		this.attributesCount = attributesCount;
	}

	public void setAttributes(Attribute[] attributes) {
		this.attributes = attributes;
	}

	public void setClassFile(ClassFile classFile) {
		this.classFile = classFile;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(MethodInfo.class.getSimpleName());
		sb.append(" : {");
		sb.append("accessFlags : [").append(accessFlags).append("] ");
		sb.append(" ; ");
		sb.append("[").append(nameIndex).append("]");
		sb.append(getName());
		sb.append(" ; ");
		sb.append("[").append(descriptorIndex).append("]");
		sb.append(getDescriptor());
		sb.append(" ; ");
		sb.append("[").append(attributesCount).append("]; ");
		sb.append("attributesCount : [").append(attributesCount).append("] ");
		if (attributes != null) {
			sb.append("\r\n");
			for (int i = 0; i < attributes.length; i++) {
				sb.append("attributes[").append(i).append("] ");
				sb.append(attributes[i]).append("\r\n");
			}
		}
		sb.append("}");
		return sb.toString();
	}

	public Attribute getAttribute(AttributeType attributeType) {
		if (attributes == null) {
			return null;
		}
		for (Attribute attribute : attributes) {
			if (attribute.getName().equals(attributeType.getName())) {
				return attribute;
			}
		}
		return null;
	}

	public CodeAttribute getCodeAttribute() {
		for (int i = 0; i < attributesCount; i++) {
			if (AttributeType.get(attributes[i].getName()) == AttributeType.CODE) {
				return (CodeAttribute) attributes[i];
			}
		}
		return null;
	}

	/**
	 * 设置修正的偏移长度
	 * 
	 * @param correctOffset
	 */
	public void setCorrectOffset(int correctOffset) {
		setLength(getLength() + correctOffset);
	}

}
