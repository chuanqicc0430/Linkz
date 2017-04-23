package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

public class ClassElementValue extends ElementValue {

	private char tag;

	private int classInfoIndex;

	public ClassElementValue(char tag) {
		this.tag = tag;
	}

	public ClassElementValue(char tag, int classInfoIndex) {
		this.tag = tag;
		this.classInfoIndex = classInfoIndex;
		super.setLength(3);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		super.setOffset(input.getOffset() - 1);
		classInfoIndex = input.readUnsignedShort();
		super.setLength(input.getOffset() - super.getOffset());
	}

	@Override
	public int writeTo(ClassFile classFile, int index, OperationType operationType) throws InvalidByteCodeException,
			IOException {
		int offset = super.writeTo(classFile, classFile.getCorrectionOffset(index), operationType,
				new ClassElementValue(tag), 1);
		classFile.putCorrectionOffset(index, offset);
		return offset;
	}

	@Override
	public byte[] toByteArray() {
		int offset = 0;
		byte[] buffer = new byte[super.getLength()];
		buffer[offset++] = (byte) tag;
		buffer[offset++] = (byte) ((classInfoIndex >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((classInfoIndex >>> 0) & 0xFF);
		return buffer;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" ClassElementValue:{ ");
		sb.append(" tag = ").append(tag);
		sb.append(" classInfoIndex = ").append(classInfoIndex);
		return sb.toString();
	}
}
