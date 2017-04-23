package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

/**
 * class文件在解析后出现的常量池的抽象基类，每种常量类型均继承自此类
 * 
 * @author 
 * 
 */
public abstract class CPInfo extends AbstractClassElement {

	/** 根据虚拟机中的class规范定义，ClassFile是一个用于描述整个Class文件结构的类 */
	private ClassFile classFile;

	/** 用于描述常量池中常量类型的枚举 */
	private ConstantType type;

	public CPInfo(ClassFile classFile, ConstantType type) {
		this.classFile = classFile;
		this.type = type;
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		super.setOffset(input.getOffset() - 1);
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
		ClassFileElement element = ConstantType.valueOf(classFile.getBuffer()[newIndex]).newCpInfo(classFile);
		int offset = super.writeTo(classFile, newIndex, operationType, element, 1);
		classFile.putCorrectionOffset(index, offset);
		return offset;
	}

	public int getTag() {
		return type.getTag();
	}

	public String getTagVerbose() {
		return type.getTagVerbose();
	}

	public ClassFile getClassFile() {
		return classFile;
	}

}
