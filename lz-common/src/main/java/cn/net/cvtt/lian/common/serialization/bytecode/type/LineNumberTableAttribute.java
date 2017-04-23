package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

/**
 * 被调试器用于确定源文件中行号表示的内容在Java虚拟机的code[]数组中对应的部分。在Code属性的属性表中，
 * LineNumberTable属性可以按照任意顺序出现
 * 
 * @author 
 * 
 */
public class LineNumberTableAttribute extends Attribute {

	private int lineNumberTableLength;

	private LineNumberTable[] lineNumberTables;

	public LineNumberTableAttribute(ClassFile classFile, int nameIndex, String name) {
		super(classFile, nameIndex, name);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		lineNumberTableLength = input.readUnsignedShort();
		lineNumberTables = new LineNumberTable[lineNumberTableLength];
		for (int i = 0; i < lineNumberTableLength; i++) {
			lineNumberTables[i] = new LineNumberTable();
			lineNumberTables[i].read(input);
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

		buffer[offset++] = (byte) ((lineNumberTableLength >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((lineNumberTableLength >>> 0) & 0xFF);
		for (int i = 0; i < lineNumberTableLength; i++) {
			byte[] temp = lineNumberTables[i].toByteArray();
			System.arraycopy(temp, 0, buffer, offset, temp.length);
			offset += temp.length;
		}

		return buffer;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" LineNumberTableAttribute:{ ");
		sb.append("attributeName = ").append(getName());
		sb.append("; attributeLength = ").append(attributeLength);
		sb.append(" [");
		for (int i = 0; i < lineNumberTableLength; i++) {
			sb.append(lineNumberTables[i]);
			sb.append(", ");
		}
		sb.append("] ");
		sb.append("}");
		return sb.toString();
	}

	public static class LineNumberTable extends AbstractClassElement {
		private int startPc;
		private int lineNumber;

		public LineNumberTable() {
			super.setLength(4);
		}

		public LineNumberTable(int startPc, int lineNumber) {
			this.startPc = startPc;
			this.lineNumber = lineNumber;
			super.setLength(4);
		}

		@Override
		public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
			super.read(input);
			this.startPc = input.readUnsignedShort();
			this.lineNumber = input.readUnsignedShort();
			super.setLength(input.getOffset() - super.getOffset());
		}

		@Override
		public int writeTo(ClassFile classFile, int index, OperationType operationType)
				throws InvalidByteCodeException, IOException {
			int offset = super.writeTo(classFile, classFile.getCorrectionOffset(index), operationType,
					new LineNumberTable(), 0);
			classFile.putCorrectionOffset(index, offset);
			return offset;
		}

		@Override
		public byte[] toByteArray() {
			byte[] buffer = new byte[4];
			buffer[0] = (byte) ((startPc >>> 8) & 0xFF);
			buffer[1] = (byte) ((startPc >>> 0) & 0xFF);
			buffer[2] = (byte) ((lineNumber >>> 8) & 0xFF);
			buffer[3] = (byte) ((lineNumber >>> 0) & 0xFF);
			return buffer;

		}

		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("startPc = ");
			sb.append(startPc);
			sb.append(", ");
			sb.append("lineNumber = ");
			sb.append(lineNumber);
			return sb.toString();
		}

		public final int getStartPc() {
			return startPc;
		}

		public final void setStartPc(int startPc) {
			this.startPc = startPc;
		}

		public final int getLineNumber() {
			return lineNumber;
		}

		public final void setLineNumber(int lineNumber) {
			this.lineNumber = lineNumber;
		}

	}

	public int getLineNumberTableLength() {
		return lineNumberTableLength;
	}

	public void setLineNumberTableLength(int lineNumberTableLength) {
		this.lineNumberTableLength = lineNumberTableLength;
	}

	public LineNumberTable[] getLineNumberTables() {
		return lineNumberTables;
	}

	public void setLineNumberTables(LineNumberTable[] lineNumberTables) {
		int length = 8;
		this.lineNumberTables = lineNumberTables;
		this.lineNumberTableLength = lineNumberTables.length;
		if (lineNumberTables != null) {
			length += (4 * lineNumberTables.length);
		}
		super.setLength(length);
		super.attributeLength = length - 6;

	}

}
