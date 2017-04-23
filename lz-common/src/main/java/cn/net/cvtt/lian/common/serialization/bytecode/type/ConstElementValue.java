package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

/**
 * 普通常量类型的元素值
 * 
 * @author 
 * 
 */
public class ConstElementValue extends ElementValue {

	private char tag;

	private int constValueIndex;

	public ConstElementValue(char tag) {
		this.tag = tag;
	}

	public ConstElementValue(char tag, int constValueIndex) {
		this.tag = tag;
		this.constValueIndex = constValueIndex;
		super.setLength(3);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		super.setOffset(input.getOffset() - 1);
		constValueIndex = input.readUnsignedShort();
		super.setLength(input.getOffset() - super.getOffset());
	}

	@Override
	public int writeTo(ClassFile classFile, int index, OperationType operationType) throws InvalidByteCodeException,
			IOException {
		int offset = super.writeTo(classFile, classFile.getCorrectionOffset(index), operationType,
				new ConstElementValue(tag), 1);
		classFile.putCorrectionOffset(index, offset);
		return offset;
	}

	@Override
	public byte[] toByteArray() {
		int offset = 0;
		byte[] buffer = new byte[super.getLength()];
		buffer[offset++] = (byte) tag;
		buffer[offset++] = (byte) ((constValueIndex >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((constValueIndex >>> 0) & 0xFF);
		return buffer;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" ConstElementValue:{ ");
		sb.append(" tag = ").append(tag);
		sb.append(" constValueIndex = ").append(constValueIndex);
		return sb.toString();
	}
}
