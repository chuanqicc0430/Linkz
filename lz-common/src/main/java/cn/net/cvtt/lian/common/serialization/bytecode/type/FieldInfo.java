package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

/**
 * 这是字节码文件ClassFile中字段区域，此区域存储了所有类或类实例字段，这个类描述了字段的详细信息
 * 
 * @author 
 * 
 */
public class FieldInfo extends AbstractClassElement {

	/** 字段的访问修饰符，例如public、private、final、static等 */
	private int accessFlags;

	/** 字段名称，指向常量池的utr8结构 */
	private int nameIndex;

	/** 字段描述，指向常量池的utr8结构 */
	private int descriptorIndex;

	/** 字段属性数量 */
	private int attributesCount;

	/** 字段有哪些属性，例如如果该字段是static的，那么可能会有一个ConstantValue属性来描述他的初始值 */
	private Attribute[] attributes;

	private ClassFile classFile;

	public FieldInfo(ClassFile classFile) {
		this.classFile = classFile;
	}

	@Override
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
		int offset = super.writeTo(classFile, classFile.getCorrectionOffset(index), operationType, new FieldInfo(
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

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(FieldInfo.class.getSimpleName());
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
		sb.append("}");
		return sb.toString();
	}

}
