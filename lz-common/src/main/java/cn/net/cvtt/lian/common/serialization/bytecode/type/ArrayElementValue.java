package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

public class ArrayElementValue extends ElementValue {

	private char tag;

	private int numValues;

	private ElementValue[] values;

	public ArrayElementValue(char tag) {
		this.tag = tag;
	}

	public ArrayElementValue(char tag, ElementValue[] values) {
		this.tag = tag;
		this.numValues = values != null ? values.length : 0;
		this.values = values;
		int length = 3;
		for (int i = 0; i < numValues; i++) {
			length += values[i].getLength();
		}
		super.setLength(length);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		super.setOffset(input.getOffset() - 1);
		numValues = input.readUnsignedShort();
		values = new ElementValue[numValues];
		for (int i = 0; i < numValues; i++) {
			values[i] = ElementValueType.get((char) input.readByte()).newElementValue();
			values[i].read(input);
		}
		super.setLength(input.getOffset() - super.getOffset());
	}

	@Override
	public int writeTo(ClassFile classFile, int index, OperationType operationType) throws InvalidByteCodeException,
			IOException {
		int offset = super.writeTo(classFile, classFile.getCorrectionOffset(index), operationType,
				new ArrayElementValue(tag), 1);
		classFile.putCorrectionOffset(index, offset);
		return offset;
	}

	@Override
	public byte[] toByteArray() {
		int offset = 0;
		byte[] buffer = new byte[super.getLength()];
		buffer[offset++] = (byte) tag;
		buffer[offset++] = (byte) ((numValues >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((numValues >>> 0) & 0xFF);
		for (int i = 0; i < numValues; i++) {
			byte[] temp = values[i].toByteArray();
			System.arraycopy(temp, 0, buffer, offset, temp.length);
			offset += temp.length;
		}
		return buffer;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" LineNumberTableAttribute:{ ");
		sb.append("tag = ").append(tag);
		sb.append("; numValues = ").append(numValues);
		sb.append(" [");
		for (int i = 0; i < numValues; i++) {
			sb.append(values[i]);
			sb.append(", ");
		}
		sb.append("] ");
		sb.append("}");
		return sb.toString();
	}
}
