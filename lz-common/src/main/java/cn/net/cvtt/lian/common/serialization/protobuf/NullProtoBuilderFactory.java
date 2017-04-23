package cn.net.cvtt.lian.common.serialization.protobuf;

import cn.net.cvtt.lian.common.serialization.protobuf.util.Formater;

/**
 * 
 * <b>描述: </b>这是一个空的{@link ProtoBuilderFactory}
 * <p>
 * <b>功能: </b>
 * 当序列化代码生成器在生成A类的序列化辅助类出错时，会转而创建一个该类对象，用于外部再次序列A类时能够直接提供一个快速的、统一的、有效的出错提示。
 * <p>
 * <b>用法: </b>无需用户创建，由序列化组件自行管理与使用
 * <p>
 * 
 * @author 
 * 
 * @param <T>
 */
public class NullProtoBuilderFactory<T> implements ProtoBuilderFactory {

	private Throwable throwable;

	/**
	 * 构造一个具有特定异常的NullProtoBuilderFactory对象
	 * 
	 * @param throwable
	 */
	public NullProtoBuilderFactory(Throwable throwable) {
		this.throwable = throwable;
	}

	@SuppressWarnings("hiding")
	@Override
	public <T extends ProtoEntity> ProtoBuilder<T> newProtoBuilder(T t) {
		throw new RuntimeException(String.format("%s generated code failed,please check %s class.Details:\n%s", t
				.getClass().getName(), t.getClass().getName(), Formater.formaError(throwable)));
	}
}
