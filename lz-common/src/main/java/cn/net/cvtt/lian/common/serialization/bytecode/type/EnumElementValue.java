package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

/**
 * 用于描述枚举类型的值
 * 
 * @author 
 * 
 */
public class EnumElementValue extends ElementValue {

	private char tag;

	private int typeNameIndex;
	private int constNameIndex;

	public EnumElementValue(char tag) {
		this.tag = tag;
	}

	public EnumElementValue(char tag, int typeNameIndex, int constNameIndex) {
		this.tag = tag;
		this.typeNameIndex = typeNameIndex;
		this.constNameIndex = constNameIndex;
		super.setLength(5);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		super.setOffset(input.getOffset() - 1);
		typeNameIndex = input.readUnsignedShort();
		constNameIndex = input.readUnsignedShort();
		super.setLength(input.getOffset() - super.getOffset());
	}

	@Override
	public int writeTo(ClassFile classFile, int index, OperationType operationType) throws InvalidByteCodeException,
			IOException {
		int offset = super.writeTo(classFile, classFile.getCorrectionOffset(index), operationType,
				new EnumElementValue(tag), 1);
		classFile.putCorrectionOffset(index, offset);
		return offset;
	}

	@Override
	public byte[] toByteArray() {
		int offset = 0;
		byte[] buffer = new byte[super.getLength()];
		buffer[offset++] = (byte) tag;
		buffer[offset++] = (byte) ((typeNameIndex >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((typeNameIndex >>> 0) & 0xFF);
		buffer[offset++] = (byte) ((constNameIndex >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((constNameIndex >>> 0) & 0xFF);
		return buffer;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" EnumElementValue:{ ");
		sb.append(" tag = ").append(tag);
		sb.append(" typeNameIndex = ").append(typeNameIndex);
		sb.append(" constNameIndex = ").append(constNameIndex);
		return sb.toString();
	}
}
