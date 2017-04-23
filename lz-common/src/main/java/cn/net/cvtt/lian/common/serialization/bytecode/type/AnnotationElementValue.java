package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

/**
 * 用于描述注解类型的值
 * 
 * @author 
 * 
 */
public class AnnotationElementValue extends ElementValue {

	private char tag;

	private Annotation annotation;

	public AnnotationElementValue(char tag) {
		this.tag = tag;
	}

	public AnnotationElementValue(char tag, Annotation annotation) {
		this.tag = tag;
		this.annotation = annotation;
		super.setLength(1 + (annotation != null ? annotation.getLength() : 0));
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		super.setOffset(input.getOffset() - 1);
		annotation = new Annotation();
		annotation.read(input);
		super.setLength(input.getOffset() - super.getOffset());
	}

	@Override
	public int writeTo(ClassFile classFile, int index, OperationType operationType) throws InvalidByteCodeException,
			IOException {
		int offset = super.writeTo(classFile, classFile.getCorrectionOffset(index), operationType,
				new AnnotationElementValue(tag), 1);
		classFile.putCorrectionOffset(index, offset);
		return offset;
	}

	@Override
	public byte[] toByteArray() {
		int offset = 0;
		byte[] buffer = new byte[super.getLength()];
		buffer[offset++] = (byte) tag;
		byte[] temp = annotation.toByteArray();
		System.arraycopy(temp, 0, buffer, offset, temp.length);
		return buffer;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" AnnotationElementValue:{ ");
		sb.append("tag = ").append(tag);
		sb.append("; annotation = ").append(annotation);
		return sb.toString();
	}

}
