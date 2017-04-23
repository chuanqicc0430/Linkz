package cn.net.cvtt.lian.common.serialization.protobuf;

import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoBuilderCodeGenerator;

/**
 * <b>描述: </b>序列化辅助类{@link ProtoBuilder}的实例创建工厂
 * <p>
 * 如A类想要进行序列化，那么当调用{@link ProtoManager}
 * 的序列化或反序列化方法时将A传入，序列化组件会为A类生成一个继承自此类的序列化辅助类的代码AProtoBuilder
 * 、AProtoBuilderFactory
 * ，AProtoBuilderFactory就是为A类特别定制生成的序列化辅助类AProtoBuilder的工厂类，这个类会继承自此类。<br>
 * 这里序列化辅助类的创建逻辑使用了抽象工厂的模式，产品族既实现自此接口的的具体序列化辅助类，而工厂族则是实现自
 * {@link ProtoBuilderFactory}接口的工厂类，产品族和工厂族都是由代码生成器
 * {@link ProtoBuilderCodeGenerator}在初次使用时自动生成并继承自对应的接口或抽象类，最终动态组合进序列化的逻辑中。
 * <p>
 * <b>功能: </b>为每个序列化辅助类提供一个公共的标准的实例创建接口 ，这样可以保证多线程情况下每个序列化辅助类
 * {@link ProtoBuilder} 不会互相影响
 * <p>
 * <b>用法: </b>此类一般被封装在序列化组件的逻辑中，外部无需获得此类或调用此类的方法
 * 
 * @author 
 * 
 */
public interface ProtoBuilderFactory {

	/**
	 * 创建一个序列化辅助类的实例对象
	 * 
	 * @param t
	 * @return
	 */
	public <T extends ProtoEntity> ProtoBuilder<T> newProtoBuilder(T t);

}
