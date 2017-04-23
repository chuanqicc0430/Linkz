package cn.net.cvtt.lian.common.serialization.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.lian.common.serialization.Codec;
import cn.net.cvtt.lian.common.serialization.Serializer;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoFieldType;
import cn.net.cvtt.lian.common.serialization.protobuf.util.ClassUtils;

/**
 * 
 * <b>描述: </b>protobuf格式序列化编解码器，用于将一个Java对象转换为Google protobuf格式的数据流或字节数组，该类遵循
 * {@link Codec}的接口
 * <p>
 * <b>功能: </b>将一个Java对象转换为Google protobuf格式的数据流或字节数组，实现序列化及反序列化的接口
 * <p>
 * <br>
 * 支持范围: </b>{@link ProtoFieldType}中标识的除 {@link Object}
 * 外的其他全部类型,以及这些类型的混合情况，针对混合情况的多个字段放入一个类中，这个类必须继承自{@link ProtoEntity}
 * <p>
 * <b>用法: </b>本类建议配合{@link Serializer}使用，当一个类想使用protobuf格式进行序列化时，必须为其加入
 * {@link ProtoMember}注解
 * <p>
 * 
 * @author 
 * 
 */
public class AnnotatedProtobufCodec implements Codec {
	private Class<?> clazz;
	private static final Logger LOGGER = LoggerFactory.getLogger(AnnotatedProtobufCodec.class);

	/**
	 * 构造方法
	 * 
	 * @param clazz
	 */
	public AnnotatedProtobufCodec(Class<?> clazz) {
		this.clazz = clazz;
	}

	/*
	 * @see cn.net.cvtt.lian.common.serialization.Codec#encode(java.lang.Object,
	 * java.io.OutputStream)
	 */
	@Override
	public void encode(Object obj, OutputStream stream) throws IOException {
		ProtoManager.writeTo(obj, stream);
	}

	/*
	 * @see cn.net.cvtt.lian.common.serialization.Codec#encode(java.lang.Object)
	 */
	@Override
	public byte[] encode(Object obj) throws IOException {
		return ProtoManager.toByteArray(obj);
	}

	/*
	 * @see cn.net.cvtt.lian.common.serialization.Codec#decode(java.io.InputStream, int)
	 */
	@Override
	public <E> E decode(InputStream stream, int length) throws IOException {
		byte[] buffer = new byte[length];
		stream.read(buffer, 0, length);
		return this.<E> decode(buffer);
	}

	/*
	 * @see cn.net.cvtt.lian.common.serialization.Codec#decode(byte[])
	 */
	@Override
	public <E> E decode(byte[] buffer) throws IOException {
		try {
			return (E) ProtoManager.parseFrom(ClassUtils.newClassInstance(clazz), buffer);
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage());
			throw new IOException("decode failed" + clazz.getName(), e);
		} catch (IllegalAccessException e) {
			LOGGER.error(e.getMessage());
			throw new IOException("decode failed" + clazz.getName(), e);
		} catch (InstantiationException e) {
			LOGGER.error(e.getMessage());
			throw new IOException("decode failed" + clazz.getName(), e);
		}
	}

	@Override
	public String codecName() {
		// TODO Auto-generated method stub
		return "protobuf";
	}
}
