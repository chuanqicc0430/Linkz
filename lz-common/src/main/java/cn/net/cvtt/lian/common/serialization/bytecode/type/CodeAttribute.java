package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;
import java.util.Arrays;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;

/**
 * 这是一个Code类型的属性类，这个属性用于Method中，存储了方法代码在编译后产生的JVM操作码，实现了方法的执行细节
 * 
 * @author 
 * 
 */
public class CodeAttribute extends Attribute {

	/** 最大栈深度 */
	private int maxStack;

	/** 局部变量的最大索引值 */
	private int maxLocals;

	/** code数组的长度 */
	private int codeLength;

	/** 这个数组中存储了方法每一行代码编译后产生的操作码以及操作值，JVM虚拟机通过这些操作码和值来实现方法的行为 */
	private byte[] codes;

	/** 此方法中拥有的异常处理的数量(包括finally，因为在规范中finally是catchType=0的情况) */
	private int exceptionLength;

	/** 此方法中所有的异常处理信息 (同样包括finally) */
	private ExceptionTable[] exceptions;

	/** 此Code属性的内部属性数量 */
	private int attributeCount;

	/** 此Code属性的具体内部属性,例如常见的LineNumberTable、LocalVariableTable等 */
	private Attribute[] attributes;

	public CodeAttribute(ClassFile classFile, int nameIndex, String name) {
		super(classFile, nameIndex, name);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		maxStack = input.readUnsignedShort();
		maxLocals = input.readUnsignedShort();
		codeLength = input.readInt();
		codes = new byte[codeLength];
		input.readFully(codes);
		exceptionLength = input.readUnsignedShort();
		exceptions = new ExceptionTable[exceptionLength];
		if (exceptions != null) {
			for (int i = 0; i < exceptions.length; i++) {
				ExceptionTable exceptionTable = new ExceptionTable(this);
				exceptionTable.read(input);
				exceptions[i] = exceptionTable;
			}
		}

		attributeCount = input.readUnsignedShort();
		attributes = new Attribute[attributeCount];
		for (int i = 0; i < attributeCount; i++) {
			int nameIndex = input.readUnsignedShort();
			String attributeName = ((ConstantUTF8Info) super.getClassFile().getConstant_pool()[nameIndex]).getValue();
			AttributeType attributeType = AttributeType.get(attributeName);
			Attribute attribute = attributeType != null ? attributeType.getAttribute(super.getClassFile(), nameIndex)
					: new DefaultAttribute(super.getClassFile(), nameIndex, "DefaultAttribute");
			attribute.read(input);
			attributes[i] = attribute;
		}

	}

	/**
	 * 将当前元素转换为byte数组
	 * 
	 * @return
	 */
	@Override
	public byte[] toByteArray() {
		byte[] buffer = new byte[getLength()];
		int offset = 0;
		buffer[offset++] = (byte) ((nameIndex >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((nameIndex >>> 0) & 0xFF);

		buffer[offset++] = (byte) ((attributeLength >>> 24) & 0xFF);
		buffer[offset++] = (byte) ((attributeLength >>> 16) & 0xFF);
		buffer[offset++] = (byte) ((attributeLength >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((attributeLength >>> 0) & 0xFF);

		buffer[offset++] = (byte) ((maxStack >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((maxStack >>> 0) & 0xFF);
		buffer[offset++] = (byte) ((maxLocals >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((maxLocals >>> 0) & 0xFF);
		buffer[offset++] = (byte) ((codeLength >>> 24) & 0xFF);
		buffer[offset++] = (byte) ((codeLength >>> 16) & 0xFF);
		buffer[offset++] = (byte) ((codeLength >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((codeLength >>> 0) & 0xFF);
		System.arraycopy(codes, 0, buffer, offset, codes.length);
		offset += codes.length;
		buffer[offset++] = (byte) ((exceptionLength >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((exceptionLength >>> 0) & 0xFF);
		for (int i = 0; i < exceptionLength; i++) {
			byte[] temp = exceptions[i].toByteArray();
			System.arraycopy(temp, 0, buffer, offset, temp.length);
			offset += temp.length;
		}
		buffer[offset++] = (byte) ((attributeCount >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((attributeCount >>> 0) & 0xFF);
		for (int i = 0; i < attributeCount; i++) {
			byte[] temp = attributes[i].toByteArray();
			System.arraycopy(temp, 0, buffer, offset, temp.length);
			offset += temp.length;
		}
		return buffer;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CodeAttribute:{");
		sb.append("attributeName = ").append(getName());
		sb.append("; attributeLength = ").append(attributeLength);
		sb.append("; maxStack = ").append(maxStack);
		sb.append("; maxLocals = ").append(maxLocals);
		sb.append("; codeLength = ").append(codeLength);
		sb.append("; codes = ").append(Arrays.toString(codes));
		sb.append("; exceptionLength = ").append(exceptionLength);
		if (exceptions != null) {
			sb.append("\r\n");
			for (int i = 0; i < exceptions.length; i++) {
				sb.append("exceptions[").append(i).append("] ");
				sb.append(exceptions[i]).append("\r\n");
			}
		}
		sb.append("; attributeCount = ").append(attributeCount);
		sb.append(" [");
		for (int i = 0; i < attributeCount; i++) {
			sb.append(attributes[i]);
			sb.append(", ");
		}
		sb.append("]");
		sb.append("}");
		return sb.toString();
	}

	public void setCodes(byte[] codes) {
		this.codes = codes;
		super.attributeLength = super.attributeLength + (codes.length - this.codeLength);
		this.codeLength = codes.length;
	}

	public void setAttributes(Attribute[] attributes) {
		int original = 0;
		if (attributes != null && this.attributes.length > 0) {
			for (Attribute attribute : this.attributes) {
				original += attribute.getLength();
			}
		}
		int counter = 0;
		int newLength = 0;
		if (attributes != null && attributes.length > 0) {
			for (Attribute attribute : attributes) {
				counter++;
				newLength += attribute.getLength();
			}
		}
		this.attributeCount = counter;
		this.attributes = attributes;
		this.attributeLength = this.attributeLength + (newLength - original);
	}

	public int getMaxStack() {
		return maxStack;
	}

	public int getMaxLocals() {
		return maxLocals;
	}

	public int getCodeLength() {
		return codeLength;
	}

	public byte[] getCodes() {
		return codes;
	}

	public int getExceptionLength() {
		return exceptionLength;
	}

	public ExceptionTable[] getExceptions() {
		return exceptions;
	}

	public int getAttributeCount() {
		return attributeCount;
	}

	public Attribute[] getAttributes() {
		return attributes;
	}

	public void setExceptionLength(int exceptionLength) {
		this.exceptionLength = exceptionLength;
	}

	public void setExceptions(ExceptionTable[] exceptions) {
		if (this.exceptions != null && this.exceptions.length > 0) {
			for (ExceptionTable exception : this.exceptions) {
				super.attributeLength -= exception.getLength();
			}
		}
		if (exceptions != null && exceptions.length > 0) {
			for (ExceptionTable exception : exceptions) {
				super.attributeLength += exception.getLength();
			}
		}
		this.exceptions = exceptions;
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

	/**
	 * 添加一个attribute
	 * 
	 * @param attribute
	 */
	public void addAttribute(Attribute attribute) {
		if (attribute == null) {
			return;
		}

		if (attributes == null || attributes.length == 0) {
			attributes = new Attribute[1];
			attributes[0] = attribute;
		} else {
			Attribute[] attributesTemp = new Attribute[attributeCount + 1];
			System.arraycopy(attributes, 0, attributesTemp, 0, attributes.length);
			attributesTemp[attributesTemp.length - 1] = attribute;
			attributes = attributesTemp;
		}
		super.attributeLength = attributeLength + attribute.getLength();
		attributeCount++;
	}

	/**
	 * 移除一个attribute
	 * 
	 * @param attributeType
	 */
	public void removeAttribute(AttributeType attributeType) {
		if (attributes == null || attributeType == null) {
			return;
		}
		int index = -1;
		for (int i = 0; i < attributeCount; i++) {
			if (attributes[i].getName().equals(attributeType.getName())) {
				index = i;
				break;
			}
		}
		if (index != -1 && attributeCount > 1) {
			super.attributeLength = attributeLength - attributes[index].getLength();
			Attribute[] attributesTemp = new Attribute[attributeCount - 1];
			System.arraycopy(attributes, 0, attributesTemp, 0, index);
			if (index + 1 < attributeCount) {
				System.arraycopy(attributes, index + 1, attributesTemp, index, attributesTemp.length);
			}
			attributes = attributesTemp;
			attributeCount--;
		} else if (index != -1 && attributeCount == 1) {
			super.attributeLength = attributeLength - attributes[index].getLength();
			attributes = new Attribute[0];
			attributeCount--;
		}
	}

	/**
	 * 设置修正的偏移长度
	 * 
	 * @param correctOffset
	 */
	public void setCorrectOffset(int correctOffset) {
		setLength(getLength() + correctOffset);
		attributeLength = attributeLength + correctOffset;
	}
}
