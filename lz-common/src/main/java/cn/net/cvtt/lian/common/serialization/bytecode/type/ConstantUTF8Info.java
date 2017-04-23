package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;

/**
 * UTF-8类型的常量
 * 
 * @author 
 * 
 */
public class ConstantUTF8Info extends CPInfo {

	private String body;

	/**
	 * 构造方法将自己注册成为一个UTF-8类型的常量池信息类
	 */
	public ConstantUTF8Info(ClassFile classFile) {
		super(classFile, ConstantType.CONSTANT_UTF8);
	}

	/**
	 * 构造方法将自己注册成为一个UTF-8类型的常量池信息类
	 */
	public ConstantUTF8Info(ClassFile classFile, String body) {
		super(classFile, ConstantType.CONSTANT_UTF8);
		this.body = body;
		ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(byteArrayInputStream);
		try {
			outputStream.writeUTF(body);
			super.setLength(byteArrayInputStream.toByteArray().length + 1);// 加1是当前标志位
		} catch (IOException e) {
			super.setLength(body.getBytes().length + 2 + 1);
		}
	}

	@Override
	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		super.read(input);
		body = input.readUTF();
		super.setLength(input.getOffset() - getOffset());
	}

	/**
	 * 将当前UTF8元素转换为byte数组
	 * 
	 * @return
	 */
	@Override
	public byte[] toByteArray() {
		ByteArrayOutputStream byteArrayOutputStream = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			DataOutputStream outputStream = new DataOutputStream(byteArrayOutputStream);
			outputStream.writeByte(ConstantType.CONSTANT_UTF8.getTag());
			outputStream.writeUTF(body);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return byteArrayOutputStream != null ? byteArrayOutputStream.toByteArray() : null;
	}

	public String getValue() {
		return body;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		// sb.append(super.getTagVerbose());
		// sb.append("(tag = ").append(super.getTag()).append(") : ");
		sb.append(getValue());
		return sb.toString();
	}
}
