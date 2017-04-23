package cn.net.cvtt.lian.common.serialization.kryo;

import cn.net.cvtt.lian.common.serialization.Codec;
import cn.net.cvtt.lian.common.serialization.CodecFactory;
import cn.net.cvtt.lian.common.serialization.json.JsonCodec;
import cn.net.cvtt.lian.common.serialization.json.JsonContract;

/**
 * kryo serialization  
 * Only applicable to java object
 * @author renwei
 *
 */
public class KryoCodecFactory extends CodecFactory {

	public KryoCodecFactory() {
		super("kryo");
	}

	/*
	 * @see cn.net.cvtt.lian.common.serialization.CodecFactory#getCodec(java.lang.Class)
	 */
	/**
	 * {在这里补充功能说明}
	 * 
	 * @param clazz
	 * @return
	 */
	@Override
	public Codec getCodec(Class<?> clazz) {
		if (clazz.getAnnotation(KryoContract.class) == null) {
			return null;
		} else {
			return new KryoCodec(clazz);
		}
	}

}
