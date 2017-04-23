package cn.net.cvtt.lian.common.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.net.cvtt.lian.common.serialization.json.JsonCodecFactory;
import cn.net.cvtt.lian.common.serialization.kryo.KryoCodecFactory;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtobufCodecFactory;
import cn.net.cvtt.lian.common.util.StringUtils;
import freemarker.template.utility.StringUtil;

/**
 * 
 * <b>描述: </b>序列化器
 * <p>
 * <b>功能: </b>用来将一个对象序列化到流中，或从流中反序列化出对象
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * 序列化
 * RpcRequestHeader rpcRequestHeader = ...
 * byte[] buffer = Serializer.encode(rpcRequestHeader);
 * 
 * 反序列化
 * rpcRequestHeader = Serializer.decode(RpcRequestHeader.class,buffer);
 * 
 * 获得一个类的序列化编解码器
 * Codec codec = Serializer.getCodec(RpcRequestHeader.class);
 * 
 * 添加一个序列化编解码器的工厂类{@link CodecFactory}到当前的序列化器中，此接口的作用是用于扩展序列化方式，在扩展序列化方式时需要关注，详情参见{@link CodecFactory}
 * Serializer.addFactory(new JsonCodecFactory());
 * </pre>
 * <p>
 * 
 * @author 
 * 
 */
public class Serializer {
	private static List<CodecFactory> codecFactorys;
	private static Map<Class<?>, Codec> codecs;

	static {
		codecFactorys = new ArrayList<CodecFactory>();

		// 预初始化两个Factory
		addFactory(new ProtobufCodecFactory());
		addFactory(new JsonCodecFactory());
        addFactory(new KryoCodecFactory());
		codecs = new ConcurrentHashMap<Class<?>, Codec>();
	}

	/**
	 * 添加一个{@link CodecFactory}到当前的序列化器中来，这个方法一般被用于自定义新的序列化方式时使用
	 * 
	 * @param e
	 *            序列化编解码器工厂类
	 */
	public static void addFactory(CodecFactory e) {
		codecFactorys.add(e);
	}

	/**
	 * 将一个对象序列化后写入到输出流中
	 * 
	 * @param obj
	 *            待序列化的对象
	 * @param stream
	 *            输出流
	 * @throws IOException
	 */
	public static void encode(Object obj, OutputStream stream) throws IOException {
		encode(obj, "", stream);
	}

	/**
	 * 将一个对象使用某一种编解码器写入到流中
	 * 
	 * @param obj
	 *            待序列化的对象
	 * @param codecName
	 *            编解码器工厂类的名称(protobuf或json)，对应{@link CodecFactory#getName()}
	 * @param stream
	 *            输出流
	 * @throws IOException
	 */
	public static void encode(Object obj, String codecName, OutputStream stream) throws IOException {
		if (obj == null)
			throw new IllegalArgumentException("encode Object can't be null");

		Class<?> clazz = obj.getClass();
		Codec codec = getCodec(clazz, codecName);
		codec.encode(obj, stream);
	}

	/**
	 * 将一个对象序列化后以字节数组方式存储，并将这个数组返回
	 * 
	 * @param obj
	 *            待序列化的数组
	 * @return
	 * @throws IOException
	 */
	public static byte[] encode(Object obj) throws IOException {
		return encode(obj, "");
	}

	/**
	 * 将一个对象使用某一种编解码器序列化后写入到字节数组中
	 * 
	 * @param obj
	 *            待序列化的数组
	 * @param codecName
	 *            编解码器工厂类的名称(protobuf或json)，对应{@link CodecFactory#getName()}
	 * @return
	 * @throws IOException
	 */
	public static byte[] encode(Object obj, String codecName) throws IOException {
		if (obj == null)
			throw new IllegalArgumentException("encode Object can't be null");

		Class<?> clazz = obj.getClass();
		Codec codec = getCodec(clazz, codecName);

		return codec.encode(obj);
	}

	/**
	 * 从输入流中读取指定长度的字节，反序列化后写入指定类型的对象中
	 * 
	 * @param clazz
	 *            待反序列化的类型
	 * @param stream
	 *            数据源
	 * @param length
	 *            读取长度(读取的字节数量)
	 * @return
	 * @throws IOException
	 */
	public static <E> E decode(Class<E> clazz, InputStream stream, int length) throws IOException {
		return decode(clazz, null, stream, length);
	}

	/**
	 * 从输入流中读取指定长度的字节，在用指定的序列化编码解码器反序列化后写入指定类型的对象中
	 * 
	 * @param clazz
	 *            待反序列化的类型
	 * @param codecName
	 *            编解码器工厂类的名称(protobuf或json)，对应{@link CodecFactory#getName()}
	 * @param stream
	 *            数据源
	 * @param length
	 *            读取长度(读取的字节数量)
	 * @return
	 * @throws IOException
	 */
	public static <E> E decode(Class<E> clazz, String codecName, InputStream stream, int length) throws IOException {
		Codec codec = getCodec(clazz, codecName);
		Object obj = codec.decode(stream, length);
		return (E) obj;
	}

	/**
	 * 从字节数组中反序列化出内容，存储到指定的对象中
	 * 
	 * @param clazz
	 *            待反序列化的类型
	 * @param buffer
	 *            字节数组
	 * @return
	 * @throws IOException
	 */
	public static <E> E decode(Class<E> clazz, byte[] buffer) throws IOException {
		return decode(clazz, null, buffer);
	}

	/**
	 * 从字节数组中以指定的序列化编解码器反序列化出内容，存储到指定的对象中
	 * 
	 * @param clazz
	 *            待反序列化的类型
	 * @param codecName
	 * @param buffer
	 * @return
	 * @throws IOException
	 */
	public static <E> E decode(Class<E> clazz, String codecName, byte[] buffer) throws IOException {
		Codec codec = getCodec(clazz, codecName);
		Object obj = codec.decode(buffer);
		return (E) obj;
	}

	/**
	 * 
	 * 获取特定类的默认编解码器
	 * 
	 * @param clazz
	 * @return
	 */
	public static Codec getCodec(Class<?> clazz) {
		return getCodec(clazz, "");
	}

	/**
	 * 
	 * 获取特定类的编解码器
	 * 
	 * @param clazz
	 *            类型
	 * @param codecName
	 *            编解码器名(例如: "protobuf", "json"), 为""则自动根据类型判断
	 * @return
	 */
	public static Codec getCodec(Class<?> clazz, String codecName) {
		Codec codec = codecs.get(clazz);
		if (codec == null) {
			for (CodecFactory f : codecFactorys) {
				if (codecName != null && !codecName.equals("")) {
					if (!f.getName().equals(codecName)) {
						continue;
					}
				}
				if (codec == null) {
					codec = f.getCodec(clazz);
				}
				/*
				 * else { throw new
				 * IllegalArgumentException("ambiguity codec for class " +
				 * clazz.getName()); }
				 */
			}
		}
		if (codec == null) {
			throw new IllegalArgumentException("missing codec for class " + clazz.getName());
		}
		codecs.put(clazz, codec);
		return codec;
	}
}
