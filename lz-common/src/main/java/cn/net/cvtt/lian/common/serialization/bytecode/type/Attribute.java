package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

/**
 * 属性表的抽象基类，根据JavaSE 7虚拟机规范，所有的属性类均符合以下格式
 * 
 * @author 
 * 
 */
public abstract class Attribute extends AbstractClassElement {

	/** 标识此属性的长度(u4 attribute_length) */
	protected int attributeLength;
	protected int nameIndex;

	private ClassFile classFile;

	/** 指向常量池的名称索引 */
	private String name;

	public Attribute(ClassFile classFile, int nameIndex, String name) {
		this.classFile = classFile;
		this.name = name;
		this.nameIndex = nameIndex;
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		super.setOffset(input.getOffset() - 2);
		attributeLength = input.readInt();
	}

	/**
	 * 
	 * 按照操作类型的要求，将自身写入ClassFile中、修改ClassFile为自己或在ClassFile中删除自身<br>
	 * 这里有一个默认的约束，就是既然修改了ClassFile中的某一个元素，那么一定要及时更新以及返回因为本次新增所导致的索引的变更值
	 * 
	 * @param classFile
	 *            被写入到此classFile中
	 * @param index
	 *            此元素对应classFile的byte[] buffer位置偏移量
	 * @param operationType
	 *            操作类型(新增、修改、删除)
	 * @return 返回本次操作对classFile中后面字节码产生的偏移量,可为下次操作起到偏移量修正的作用
	 */
	public int writeTo(ClassFile classFile, int index, OperationType operationType) throws InvalidByteCodeException,
			IOException {
		int newIndex = classFile.getCorrectionOffset(index);

		int nameIndex = (classFile.getBuffer()[newIndex] << 8) + (classFile.getBuffer()[newIndex + 1] << 0);
		ClassFileElement element = AttributeType.get(
				((ConstantUTF8Info) classFile.getConstant_pool()[nameIndex]).getValue()).getAttribute(classFile,
				nameIndex);
		int offset = super.writeTo(classFile, newIndex, operationType, element, 2);
		classFile.putCorrectionOffset(index, offset);
		return offset;
	}

	public int getAttributeLength() {
		return attributeLength;
	}

	public ClassFile getClassFile() {
		return classFile;
	}

	public int getNameIndex() {
		return nameIndex;
	}

	public String getName() {
		return name;
	}

	/**
	 * 获得当前属性所占用的byte长度，加6的原因是属性长度没有计算他的第一位和第二位
	 */
	@Override
	public int getLength() {
		return getAttributeLength() + 6;
	}

}
