package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;

/**
 * 运行时注解存储在属性表的内容解析
 * 
 * @author 
 * 
 */
public class RuntimeVisibleAnnotationsAttribute extends Attribute {

	private int numAnnotations;

	private Annotation[] annotations;

	public RuntimeVisibleAnnotationsAttribute(ClassFile classFile, int nameIndex, String name) {
		super(classFile, nameIndex, name);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		numAnnotations = input.readUnsignedShort();
		annotations = new Annotation[numAnnotations];
		for (int i = 0; i < numAnnotations; i++) {
			annotations[i] = new Annotation();
			annotations[i].read(input);
		}
		super.setLength(input.getOffset() - super.getOffset());
	}

	@Override
	public byte[] toByteArray() {
		int offset = 0;
		byte[] buffer = new byte[getLength()];
		buffer[offset++] = (byte) ((nameIndex >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((nameIndex >>> 0) & 0xFF);

		buffer[offset++] = (byte) ((attributeLength >>> 24) & 0xFF);
		buffer[offset++] = (byte) ((attributeLength >>> 16) & 0xFF);
		buffer[offset++] = (byte) ((attributeLength >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((attributeLength >>> 0) & 0xFF);

		buffer[offset++] = (byte) ((numAnnotations >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((numAnnotations >>> 0) & 0xFF);
		for (int i = 0; i < numAnnotations; i++) {
			byte[] temp = annotations[i].toByteArray();
			System.arraycopy(temp, 0, buffer, offset, temp.length);
			offset += temp.length;
		}

		return buffer;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" RuntimeVisibleAnnotations:{ ");
		sb.append("attributeName = ").append(getName());
		sb.append("; attributeLength = ").append(attributeLength);
		sb.append(" [");
		for (int i = 0; i < numAnnotations; i++) {
			sb.append(annotations[i]);
			sb.append(", ");
		}
		sb.append("] ");
		sb.append("}");
		return sb.toString();
	}

	public int getNumAnnotations() {
		return numAnnotations;
	}

	public Annotation[] getAnnotations() {
		return annotations;
	}

}
