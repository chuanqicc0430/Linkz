package cn.net.cvtt.lian.common.serialization.protobuf;

import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoBuilderCodeGenerator;

/**
 * <b>描述: </b>这是一个用于构建序列化辅助类的抽象类
 * <p>
 * 如A类想要进行序列化，那么当调用{@link ProtoManager}
 * 的序列化或反序列化方法时将A传入，序列化组件会为A类生成一个继承自此类的序列化辅助类的代码AProtoBuilder
 * 、AProtoBuilderFactory ，AProtoBuilder就是为A类特别定制生成的序列化辅助类，这个类会继承自此类。<br>
 * 这里序列化辅助类的创建逻辑使用了抽象工厂的模式，产品族既实现自此接口的的具体序列化辅助类，而工厂族则是实现自
 * {@link ProtoBuilderFactory}接口的工厂类，产品族和工厂族都是由代码生成器
 * {@link ProtoBuilderCodeGenerator}在初次使用时自动生成并继承自对应的接口或抽象类，最终动态组合进序列化的逻辑中。
 * <p>
 * <b>功能: </b>为每个可序列化类型提供一个标准的接口
 * <p>
 * <b>用法: </b>此类一般被封装在{@link ProtoManager}
 * 的方法中，如果想直接获得此类的实例且调用相应的序列化或反序列化方法，可如下操作
 * 
 * <pre>
 * //序列化示例
 * ProtoEntity args = ...//想要序列化的类型
 * ProtoBuilder builder = ProtoManager.getProtoBuilder(args);
 * byte[] buffer = builder.toByteArray();
 * 
 * //反序列化示例
 * InputStream input = ...//待反序列化的二进制流
 * builder.parseFrom(input);
 * args.getXXX();//取出反序列化后的数据
 * </pre>
 * 
 * @author 
 * 
 * @param <T>
 */
public abstract class ProtoBuilder<T extends ProtoEntity> {

	protected T data;

	/**
	 * 构造方法，构造时需要传递将要序列化或反序列化的Java对象
	 * 
	 * @param data
	 *            将要序列化或反序列化的Java对象
	 */
	public ProtoBuilder(T data) {
		this.data = data;
	}

	/**
	 * 获得被操作的Java对象
	 * 
	 * @return
	 */
	public T getData() {
		return data;
	}

	/**
	 * 从byte数组读取数据到当前的实例
	 * 
	 * @param buffer
	 */
	public void parseFrom(final byte[] buffer) throws IOException {
		parseFrom(CodedInputStream.newInstance(buffer));
	}

	/**
	 * 从流中读取数据到当前的实例
	 * 
	 * @param input
	 */
	public void parseFrom(final InputStream input) throws IOException {
		parseFrom(CodedInputStream.newInstance(input));
	}

	/**
	 * 从流中读取数据到当前的实例
	 * 
	 * @param input
	 */
	public abstract void parseFrom(final CodedInputStream input) throws IOException;

	/**
	 * 把内容写入byte[]中并返回
	 * 
	 */
	public byte[] toByteArray() throws IOException {
		final byte[] result = new byte[getSerializedSize()];
		final CodedOutputStream output = CodedOutputStream.newInstance(result);
		writeTo(output);
		output.checkNoSpaceLeft();
		return result;
	}

	/**
	 * 把内容写入输出流中
	 * 
	 * @param output
	 */
	public void writeTo(final OutputStream output) throws IOException {
		final int bufferSize = CodedOutputStream.computePreferredBufferSize(getSerializedSize());
		final CodedOutputStream codedOutput = CodedOutputStream.newInstance(output, bufferSize);
		writeTo(codedOutput);
		codedOutput.flush();
	}

	/**
	 * 把内容写入输出流中
	 * 
	 * @param output
	 */
	public abstract void writeTo(final CodedOutputStream output) throws IOException;

	/**
	 * 获得将要序列化的长度
	 * 
	 * @return
	 */
	public abstract int getSerializedSize();

}
