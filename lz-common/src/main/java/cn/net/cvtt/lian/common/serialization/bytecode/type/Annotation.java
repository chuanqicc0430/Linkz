package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

/**
 * 用于class中注解元素的解析
 * 
 * @author 
 * 
 */
public class Annotation extends AbstractClassElement {

	private int typeIndex;

	private int numElementValuePairs;

	private ElementValuePair[] elementValuePairs;

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		typeIndex = input.readUnsignedShort();
		numElementValuePairs = input.readUnsignedShort();
		elementValuePairs = new ElementValuePair[numElementValuePairs];
		for (int i = 0; i < numElementValuePairs; i++) {
			elementValuePairs[i] = new ElementValuePair();
			elementValuePairs[i].read(input);
		}
		super.setLength(input.getOffset() - super.getOffset());
	}

	@Override
	public int writeTo(ClassFile classFile, int index, OperationType operationType) throws InvalidByteCodeException,
			IOException {
		int offset = super.writeTo(classFile, classFile.getCorrectionOffset(index), operationType, new Annotation(), 0);
		classFile.putCorrectionOffset(index, offset);
		return offset;
	}

	@Override
	public byte[] toByteArray() {
		int offset = 0;
		byte[] buffer = new byte[super.getLength()];
		buffer[offset++] = (byte) ((typeIndex >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((typeIndex >>> 0) & 0xFF);
		buffer[offset++] = (byte) ((numElementValuePairs >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((numElementValuePairs >>> 0) & 0xFF);
		for (int i = 0; i < numElementValuePairs; i++) {
			byte[] temp = elementValuePairs[i].toByteArray();
			System.arraycopy(temp, 0, buffer, offset, temp.length);
			offset += temp.length;
		}
		return buffer;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" Annotation:{ ");
		sb.append(" typeIndex = ").append(typeIndex);
		sb.append(" numElementValuePairs = ").append(numElementValuePairs);
		sb.append(" [");
		for (int i = 0; i < numElementValuePairs; i++) {
			sb.append(elementValuePairs[i]);
			sb.append(", ");
		}
		sb.append("] ");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 用于Attribute内部的键值对的值
	 * 
	 * @author 
	 * 
	 */
	public static class ElementValuePair extends AbstractClassElement {

		private int elementNameIndex;

		private ElementValue value;

		public ElementValuePair() {

		}

		public ElementValuePair(int elementNameIndex, ElementValue value) {
			this.elementNameIndex = elementNameIndex;
			this.value = value;
			super.setLength(2 + (value != null ? value.getLength() : 0));
		}

		@Override
		public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
			super.read(input);
			elementNameIndex = input.readUnsignedShort();
			value = ElementValueType.get((char) input.readByte()).newElementValue();
			value.read(input);
			super.setLength(input.getOffset() - super.getOffset());
		}

		@Override
		public int writeTo(ClassFile classFile, int index, OperationType operationType)
				throws InvalidByteCodeException, IOException {
			int offset = super.writeTo(classFile, classFile.getCorrectionOffset(index), operationType,
					new ElementValuePair(), 0);
			classFile.putCorrectionOffset(index, offset);
			return offset;
		}

		@Override
		public byte[] toByteArray() {
			int offset = 0;
			byte[] buffer = new byte[super.getLength()];
			buffer[offset++] = (byte) ((elementNameIndex >>> 8) & 0xFF);
			buffer[offset++] = (byte) ((elementNameIndex >>> 0) & 0xFF);
			byte[] temp = value.toByteArray();
			System.arraycopy(temp, 0, buffer, offset, temp.length);
			return buffer;
		}

		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append(" ElementValuePair:{ ");
			sb.append(" elementNameIndex = ").append(elementNameIndex);
			sb.append(" ElementValue = ").append(value);
			return sb.toString();
		}

		public int getElementNameIndex() {
			return elementNameIndex;
		}

		public ElementValue getValue() {
			return value;
		}

	}

	public final int getTypeIndex() {
		return typeIndex;
	}

	public final int getNumElementValuePairs() {
		return numElementValuePairs;
	}

	public final ElementValuePair[] getElementValuePairs() {
		return elementValuePairs;
	}

}
