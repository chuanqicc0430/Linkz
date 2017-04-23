package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

/**
 * class在经过解析后，会表示为各种元素，那么此类就是这些元素的抽象实现
 * 
 * @author 
 * 
 */
public abstract class AbstractClassElement implements ClassFileElement {

	/**
	 * 当前元素在class文件中的偏移量(如果当前元素有标记，那么标记也同样计入偏移量范围)
	 */
	private int offset;

	/**
	 * 当前元素所占用的byte长度(如果当前元素有标记，那么标记也同样计入长度)
	 */
	private int length;

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		this.setOffset(input.getOffset());
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
	protected int writeTo(ClassFile classFile, int index, OperationType operationType, ClassFileElement element,
			int skipNumber) throws InvalidByteCodeException, IOException {
		byte[] newByteArray = null;
		byte[] original = classFile.getBuffer();
		switch (operationType) {
		case ADD:// 添加就是在他指定的索引位置插入当前元素的byte数组
			newByteArray = insert(original, index);
			break;
		case MODIFY:
			// 修改其实是一个删除再新增的过程
			newByteArray = remove(element, original, index, skipNumber);
			newByteArray = insert(newByteArray, index);
			break;
		case DELETE: // 删除就是将某一offset下的元素截取掉
			newByteArray = remove(element, original, index, skipNumber);
			break;

		default:
			throw new RuntimeException("UnsportOperation OperationType");
		}
		classFile.setBuffer(newByteArray);
		return newByteArray.length - original.length;
	}

	/**
	 * 将当前元素插入至某一个位置
	 * 
	 * @param original
	 * @param offset
	 * @return
	 */
	private byte[] insert(byte[] original, int offset) {
		byte[] thisByteArray = toByteArray();
		byte[] newByteArray = new byte[original.length + getLength()];
		System.arraycopy(original, 0, newByteArray, 0, offset);
		System.arraycopy(thisByteArray, 0, newByteArray, offset, thisByteArray.length);
		System.arraycopy(original, offset, newByteArray, offset + thisByteArray.length, original.length - offset);
		return newByteArray;
	}

	/**
	 * 删除当前元素
	 * 
	 * @param original
	 * @return
	 * @throws InvalidByteCodeException
	 * @throws IOException
	 */
	private byte[] remove(ClassFileElement element, byte[] original, int offset, int skipNumber)
			throws InvalidByteCodeException, IOException {
		// 删除就是将某一offset下的元素截取掉
		DataInputStreamDecoration input = new DataInputStreamDecoration(new ByteArrayInputStream(original, offset,
				original.length - offset));
		// 不用在这里判断索引类型，ClassFileElement已经被传入了,所以将前面的索引跳过
		input.skipBytes(skipNumber);
		element.read(input);
		int originalElementLength = element.getLength();
		byte[] newByteArray = new byte[original.length - originalElementLength];
		System.arraycopy(original, 0, newByteArray, 0, offset);
		System.arraycopy(original, offset + originalElementLength, newByteArray, offset, original.length - offset
				- originalElementLength);
		return newByteArray;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public int getLength() {
		return length;
	}

	protected void setOffset(int offset) {
		this.offset = offset;
	}

	protected void setLength(int length) {
		this.length = length;
	}
}
