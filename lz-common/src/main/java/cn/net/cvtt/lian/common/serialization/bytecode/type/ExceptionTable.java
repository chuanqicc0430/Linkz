package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

/**
 * 异常类型表
 * 
 * @author 
 * 
 */
public class ExceptionTable extends AbstractClassElement {

	private int startPc;
	private int endPc;
	private int handlerPc;
	private int catchType;

	private CodeAttribute codeAttribute;

	public ExceptionTable(CodeAttribute codeAttribute) {
		this.codeAttribute = codeAttribute;
		super.setLength(8);
	}

	public ExceptionTable(CodeAttribute codeAttribute, int startPc, int endPc, int handlerPc, int catchType) {
		this.codeAttribute = codeAttribute;
		this.startPc = startPc;
		this.endPc = endPc;
		this.handlerPc = handlerPc;
		this.catchType = catchType;
		super.setLength(8);
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		startPc = input.readUnsignedShort();
		endPc = input.readUnsignedShort();
		handlerPc = input.readUnsignedShort();
		catchType = input.readUnsignedShort();
		super.setLength(input.getOffset() - super.getOffset());
	}

	@Override
	public int writeTo(ClassFile classFile, int index, OperationType operationType) throws InvalidByteCodeException,
			IOException {
		int offset = super.writeTo(classFile, classFile.getCorrectionOffset(index), operationType, new ExceptionTable(
				null), 0);
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
		byte[] buffer = new byte[8];
		buffer[0] = (byte) ((startPc >>> 8) & 0xFF);
		buffer[1] = (byte) ((startPc >>> 0) & 0xFF);
		buffer[2] = (byte) ((endPc >>> 8) & 0xFF);
		buffer[3] = (byte) ((endPc >>> 0) & 0xFF);
		buffer[4] = (byte) ((handlerPc >>> 8) & 0xFF);
		buffer[5] = (byte) ((handlerPc >>> 0) & 0xFF);
		buffer[6] = (byte) ((catchType >>> 8) & 0xFF);
		buffer[7] = (byte) ((catchType >>> 0) & 0xFF);
		return buffer;
	}

	public void setStartPc(int startPc) {
		this.startPc = startPc;
	}

	public void setEndPc(int endPc) {
		this.endPc = endPc;
	}

	public void setHandlerPc(int handlerPc) {
		this.handlerPc = handlerPc;
	}

	public void setCatchType(int catchType) {
		this.catchType = catchType;
	}

	public void setCodeAttribute(CodeAttribute codeAttribute) {
		this.codeAttribute = codeAttribute;
	}

	public int getStartPc() {
		return startPc;
	}

	public int getEndPc() {
		return endPc;
	}

	public int getHandlerPc() {
		return handlerPc;
	}

	public int getCatchType() {
		return catchType;
	}

	public byte getHandlerCode() {
		return codeAttribute.getCodes()[handlerPc];
	}

	public ConstantClassInfo getCatch() {
		if (catchType == 0) {
			return null;
		}
		return (ConstantClassInfo) codeAttribute.getClassFile().getConstant_pool()[catchType];
	}

	public CodeAttribute getCodeAttribute() {
		return codeAttribute;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(ExceptionTable.class.getSimpleName());
		sb.append(" : {");
		sb.append("startPc : [").append(startPc).append("] ");
		sb.append(" ; ");
		sb.append("endPc : [").append(endPc).append("] ");
		sb.append(" ; ");
		sb.append("handlerPc : [").append(handlerPc).append("] ");
		sb.append(" ; ");
		sb.append("catchType : [").append(catchType).append("] ");
		sb.append(" ; ");
		sb.append("catch : [").append(getCatch()).append("] ");
		sb.append(" ; ");
		sb.append("}");
		return sb.toString();
	}
}
