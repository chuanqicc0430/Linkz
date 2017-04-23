package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

/**
 * ClassFile中内部类的属性表
 * 
 * @author 
 * 
 */
public class InnerClassesAttribute extends Attribute {

	/** 标识内部类的成员数量 */
	private int numberOfClasses;

	/** 具体的内部类列表 */
	private InnerClass[] classes;

	public InnerClassesAttribute(ClassFile classFile, int nameIndex, String name) {
		super(classFile, nameIndex, name);

	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		numberOfClasses = input.readUnsignedShort();
		classes = new InnerClass[numberOfClasses];
		for (int i = 0; i < numberOfClasses; i++) {
			InnerClass classTemp = new InnerClass(super.getClassFile());
			classTemp.read(input);
			classes[i] = classTemp;
		}
	}

	/**
	 * 将当前元素转换为byte数组
	 * 
	 * @return
	 */
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

		buffer[offset++] = (byte) ((numberOfClasses >>> 8) & 0xFF);
		buffer[offset++] = (byte) ((numberOfClasses >>> 0) & 0xFF);
		for (int i = 0; i < numberOfClasses; i++) {
			byte[] temp = classes[i].toByteArray();
			System.arraycopy(temp, 0, buffer, offset, temp.length);
			offset += temp.length;
		}

		return buffer;
	}

	/**
	 * 具体的内部类信息
	 * 
	 * @author 
	 * 
	 */
	public static class InnerClass extends AbstractClassElement {

		/** 是一个对常量池CONSTANT_Class_info类型的索引，标识一个类或一个接口，另外三项是用于描述这个类的 */
		private int innerClassInfoIndex;

		/** 如果当前类是一个内部类，那么他指向他的外部类，否则他为0 */
		private int outerClassInfoIndex;

		/** 内部类的名字，指向常量池UTF8的索引，如果这个内部类是匿名类，则 索引值为0 */
		private int innerNameIndex;

		/** 访问权限修饰符 */
		private int innerClassAccessFlags;

		private ClassFile classFile;

		public InnerClass(ClassFile classFile) {
			this.classFile = classFile;
		}

		public InnerClass(ClassFile classFile, int innerClassInfoIndex, int outerClassInfoIndex, int innerNameIndex,
				int innerClassAccessFlags) {
			this.classFile = classFile;
			this.innerClassInfoIndex = innerClassInfoIndex;
			this.outerClassInfoIndex = outerClassInfoIndex;
			this.innerNameIndex = innerNameIndex;
			this.innerClassAccessFlags = innerClassAccessFlags;
			super.setLength(8);
		}

		public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
			super.read(input);
			innerClassInfoIndex = input.readUnsignedShort();
			outerClassInfoIndex = input.readUnsignedShort();
			innerNameIndex = input.readUnsignedShort();
			innerClassAccessFlags = input.readUnsignedShort();
			super.setLength(input.getOffset() - super.getOffset());
		}

		@Override
		public int writeTo(ClassFile classFile, int index, OperationType operationType)
				throws InvalidByteCodeException, IOException {
			int offset = super.writeTo(classFile, classFile.getCorrectionOffset(index), operationType, new InnerClass(
					classFile), 2);
			classFile.putCorrectionOffset(index, offset);
			return offset;
		}

		/**
		 * 将元素输出为byte数组
		 * 
		 * @return
		 */
		public byte[] toByteArray() {
			byte[] buffer = new byte[8];
			buffer[0] = (byte) ((innerClassInfoIndex >>> 8) & 0xFF);
			buffer[1] = (byte) ((innerClassInfoIndex >>> 0) & 0xFF);
			buffer[2] = (byte) ((outerClassInfoIndex >>> 8) & 0xFF);
			buffer[3] = (byte) ((outerClassInfoIndex >>> 0) & 0xFF);
			buffer[4] = (byte) ((innerNameIndex >>> 8) & 0xFF);
			buffer[5] = (byte) ((innerNameIndex >>> 0) & 0xFF);
			buffer[6] = (byte) ((innerClassAccessFlags >>> 8) & 0xFF);
			buffer[7] = (byte) ((innerClassAccessFlags >>> 0) & 0xFF);
			return buffer;

		}

		public int getInnerClassInfoIndex() {
			return innerClassInfoIndex;
		}

		void setInnerClassInfoIndex(int innerClassInfoIndex) {
			this.innerClassInfoIndex = innerClassInfoIndex;
		}

		public int getOuterClassInfoIndex() {
			return outerClassInfoIndex;
		}

		void setOuterClassInfoIndex(int outerClassInfoIndex) {
			this.outerClassInfoIndex = outerClassInfoIndex;
		}

		public int getInnerNameIndex() {
			return innerNameIndex;
		}

		void setInnerNameIndex(int innerNameIndex) {
			this.innerNameIndex = innerNameIndex;
		}

		public int getInnerClassAccessFlags() {
			return innerClassAccessFlags;
		}

		void setInnerClassAccessFlags(int innerClassAccessFlags) {
			this.innerClassAccessFlags = innerClassAccessFlags;
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("InnerClass:{");
			sb.append("innerClassInfoIndex = ").append(innerClassInfoIndex);
			sb.append("; outerClassInfoIndex = ").append(outerClassInfoIndex);
			sb.append("; innerNameIndex = ").append(innerNameIndex);
			sb.append("; innerClassAccessFlags = ").append(innerClassAccessFlags);
			sb.append("; innerClassInfo{").append(classFile.getConstant_pool()[innerClassInfoIndex]);
			sb.append("}; outerClassInfo{").append(classFile.getConstant_pool()[outerClassInfoIndex]);
			sb.append("}; innerName{").append(classFile.getConstant_pool()[innerNameIndex]);
			sb.append("}");
			return sb.toString();
		}

	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("InnerClassesAttribute:{");
		sb.append("attributeName = ").append(getName());
		sb.append("; attributeLength = ").append(attributeLength);
		sb.append("; numberOfClasses = ").append(numberOfClasses);
		if (classes != null) {
			sb.append("\r\n");
			for (int i = 0; i < classes.length; i++) {
				sb.append("classes[").append(i).append("] ");
				sb.append(classes[i]).append("\r\n");
			}
		}
		sb.append("}");
		return sb.toString();
	}

	public int getNumberOfClasses() {
		return numberOfClasses;
	}

	void setNumberOfClasses(int numberOfClasses) {
		this.numberOfClasses = numberOfClasses;
	}

	public InnerClass[] getClasses() {
		return classes;
	}

	void setClasses(InnerClass[] classes) {
		this.classes = classes;
	}

}
