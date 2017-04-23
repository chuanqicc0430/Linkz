package cn.net.cvtt.lian.common.serialization.protobuf;

import cn.net.cvtt.lian.common.serialization.Codec;
import cn.net.cvtt.lian.common.serialization.CodecFactory;
import cn.net.cvtt.lian.common.serialization.Serializer;

/**
 * 
 * <b>描述: </b>用于创建Protobuf序列化编解码器{@link ProtobufCodec}的工厂类，关于序列化编解码器的工厂类，请参见
 * {@link ProtobufCodecFactory};
 * <p>
 * <b>功能: </b>创建Protobuf序列化编解码器的工厂类
 * <p>
 * <b>用法: </b>本类作为抽象工厂的一部分，是配合{@link Serializer}使用，单独使用时无意义， 不建议直接操作此类，正确调用应从
 * {@link Serializer}开始。
 * <p>
 * 
 * @author 
 * 
 */
public class ProtobufCodecFactory extends CodecFactory {
	public ProtobufCodecFactory() {
		super("protobuf");
	}

	/*
	 * @see cn.net.cvtt.lian.common.serialization.CodecFactory#getCodec(java.lang.Class)
	 */
	@Override
	public Codec getCodec(Class<?> clazz) {
		// 如果属于PB的可序列化范围，则使用PB的Codec，否则返回空
		if (ProtoManager.checkScope(clazz)) {
			return new AnnotatedProtobufCodec(clazz);
		} else {
			return null;
		}
	}
}
