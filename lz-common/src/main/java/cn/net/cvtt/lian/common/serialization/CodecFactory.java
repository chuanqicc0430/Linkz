package cn.net.cvtt.lian.common.serialization;

/**
 * <b>描述: </b>
 * 序列化编解码器使用了抽象工厂的设计模式，因此该类作为工厂类的抽象类，对外提供了工厂类的抽象接口，用于从每个具体的工厂类获取对应的序列化编解码器
 * ，关于序列化编解码器可以参见 {@link Codec}
 * <p>
 * <b>功能:
 * </b>因为序列化编解码器分为两种(protobuf与json)，对应的工厂类也有两种(protobufFactory与jsonFactory)，
 * 因此本类是为了工厂类的统一化而制定的，这样做符合了抽象工厂的设计模式，可以为各种序列化编解码器在使用时提供统一的创建方法
 * <p>
 * <b>用法: </b>该类为继承使用，继承者必须实现其{@link CodecFactory#getCodec(Class)}方法
 * <p>
 * 
 * @author 
 * 
 */
public abstract class CodecFactory {
	private String name;

	/**
	 * 构造方法
	 * 
	 * @param name
	 *            序列化编解码器工厂类的名称
	 */
	public CodecFactory(String name) {
		this.name = name;
	}

	/**
	 * 获得当前序列化编解码器工厂类的名称
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * 返回一个确定类型的序列化器, 如果不能处理则返回null
	 * 
	 * @param clazz
	 *            待序列化的Java类型
	 * @return
	 */
	public abstract Codec getCodec(Class<?> clazz);
}
