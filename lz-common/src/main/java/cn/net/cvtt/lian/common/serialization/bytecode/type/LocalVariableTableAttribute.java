package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

public class LocalVariableTableAttribute extends Attribute {

	private int lineVariableTableLength;

	private LocalVariableTable[] lineVariableTables;

	public LocalVariableTableAttribute(ClassFile classFile, int nameIndex, String name) {
		super(classFile, nameIndex, name);
		super.setLength(8);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		lineVariableTableLength = input.readUnsignedShort();
		lineVariableTables = new LocalVariableTable[lineVariableTableLength];
		for (int i = 0; i < lineVariableTableLength; i++) {
			lineVariableTables[i] = new LocalVariableTable();
			lineVariableTables[i].read(input);
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

		buffer[offset++] = (byte) ((lineVariableTableLength >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((lineVariableTableLength >>> 0) & 0xFF);
		for (int i = 0; i < lineVariableTableLength; i++) {
			byte[] temp = lineVariableTables[i].toByteArray();
			System.arraycopy(temp, 0, buffer, offset, temp.length);
			offset += temp.length;
		}

		return buffer;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" LocalVariableTableAttribute:{ ");
		sb.append("attributeName = ").append(getName());
		sb.append("; attributeLength = ").append(attributeLength);
		sb.append(" [");
		for (int i = 0; i < lineVariableTableLength; i++) {
			sb.append(lineVariableTables[i]);
			sb.append(", ");
		}
		sb.append("] ");
		sb.append("}");
		return sb.toString();
	}

	public static class LocalVariableTable extends AbstractClassElement {
		private int startPc;
		private int lengthPc;
		private int nameIndex;
		private int descriptorIndex;
		private int index;

		public LocalVariableTable() {
			super.setLength(10);
		}

		@Override
		public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
			super.read(input);
			this.startPc = input.readUnsignedShort();
			this.lengthPc = input.readUnsignedShort();
			this.nameIndex = input.readUnsignedShort();
			this.descriptorIndex = input.readUnsignedShort();
			this.index = input.readUnsignedShort();
			super.setLength(input.getOffset() - super.getOffset());
		}

		@Override
		public int writeTo(ClassFile classFile, int index, OperationType operationType)
				throws InvalidByteCodeException, IOException {
			int offset = super.writeTo(classFile, classFile.getCorrectionOffset(index), operationType,
					new LocalVariableTable(), 0);
			classFile.putCorrectionOffset(index, offset);
			return offset;
		}

		@Override
		public byte[] toByteArray() {
			byte[] buffer = new byte[10];
			buffer[0] = (byte) ((startPc >>> 8) & 0xFF);
			buffer[1] = (byte) ((startPc >>> 0) & 0xFF);
			buffer[2] = (byte) ((lengthPc >>> 8) & 0xFF);
			buffer[3] = (byte) ((lengthPc >>> 0) & 0xFF);
			buffer[4] = (byte) ((nameIndex >>> 8) & 0xFF);
			buffer[5] = (byte) ((nameIndex >>> 0) & 0xFF);
			buffer[6] = (byte) ((descriptorIndex >>> 8) & 0xFF);
			buffer[7] = (byte) ((descriptorIndex >>> 0) & 0xFF);
			buffer[8] = (byte) ((index >>> 8) & 0xFF);
			buffer[9] = (byte) ((index >>> 0) & 0xFF);
			return buffer;

		}

		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("startPc = ");
			sb.append(startPc);
			sb.append(", ");
			sb.append("lengthPc = ");
			sb.append(lengthPc);
			sb.append(", ");
			sb.append("nameIndex = ");
			sb.append(nameIndex);
			sb.append(", ");
			sb.append("descriptorIndex = ");
			sb.append(descriptorIndex);
			sb.append(", ");
			sb.append("index = ");
			sb.append(index);
			return sb.toString();
		}

		public int getStartPc() {
			return startPc;
		}

		public void setStartPc(int startPc) {
			this.startPc = startPc;
		}

		public int getLengthPc() {
			return lengthPc;
		}

		public void setLengthPc(int lengthPc) {
			this.lengthPc = lengthPc;
		}

		public int getNameIndex() {
			return nameIndex;
		}

		public void setNameIndex(int nameIndex) {
			this.nameIndex = nameIndex;
		}

		public int getDescriptorIndex() {
			return descriptorIndex;
		}

		public void setDescriptorIndex(int descriptorIndex) {
			this.descriptorIndex = descriptorIndex;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

	public int getLineVariableTableLength() {
		return lineVariableTableLength;
	}

	public void setLineVariableTableLength(int lineVariableTableLength) {
		this.lineVariableTableLength = lineVariableTableLength;
	}

	public LocalVariableTable[] getLineVariableTables() {
		return lineVariableTables;
	}

	public void setLineVariableTables(LocalVariableTable[] lineVariableTables) {
		int length = 8;
		this.lineVariableTables = lineVariableTables;
		if (lineVariableTables != null) {
			length += (10 * lineVariableTables.length);
		}
		super.setLength(length);
		super.attributeLength = length - 6;
	}
}
