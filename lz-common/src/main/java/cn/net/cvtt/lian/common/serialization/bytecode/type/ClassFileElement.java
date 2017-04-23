package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

/**
 * class在经过解析后，会表示为各种元素，此类就是这些元素的通用接口，这些元素应该都具有这些属性或方法
 * 
 * @author 
 * 
 */
public interface ClassFileElement {

	/**
	 * 从流中读取内容到当前元素中
	 * 
	 * @param input
	 * @throws InvalidByteCodeException
	 * @throws IOException
	 */
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException;

	/**
	 * 
	 * 按照操作类型的要求，将自身写入ClassFile中、修改ClassFile为自己或在ClassFile中删除自身<br>
	 * 这里有一个默认的约束，就是既然修改了ClassFile中的某一个元素，那么一定要及时更新以及返回因为本次新增所导致的索引的变更值
	 * 
	 * @param classFile
	 *            被写入到此classFile中
	 * @param offset
	 *            此元素对应classFile的byte[] buffer位置偏移量
	 * @param operationType
	 *            操作类型(新增、修改、删除)
	 * @return 返回本次操作对classFile中后面字节码产生的偏移量,可为下次操作起到偏移量修正的作用
	 */
	public int writeTo(ClassFile classFile, int offset, OperationType operationType) throws InvalidByteCodeException,
			IOException;

	/**
	 * 将元素输出为byte数组
	 * 
	 * @return
	 */
	public byte[] toByteArray();

	/**
	 * 获取当前元素在整个Class中的偏移量
	 * 
	 * @return
	 */
	public int getOffset();

	/**
	 * 获取当前元素长度
	 * 
	 * @return
	 */
	public int getLength();

}
