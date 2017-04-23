package cn.net.cvtt.lian.common.serialization.protobuf.generator;

/**
 * 
 * <b>描述: </b>序列化辅助类代码生成时异常,当代码生成过程中出现问题时，此异常被抛出
 * <p>
 * <b>功能: </b>用于标识出序列化辅助类代码生成时的异常
 * <p>
 * <b>用法: </b>外部调用者在接受到此类异常时，需要及时处理，因为此类异常代表着一个类型的Java类无法被成功的序列化或反序列化
 * <p>
 * 
 * @author 
 * 
 */
public class ProtoCodeGeneratorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7681643427601666941L;

	/**
	 * 默认构造方法
	 */
	public ProtoCodeGeneratorException() {

	}

	/**
	 * 构造方法
	 * 
	 * @param message
	 */
	public ProtoCodeGeneratorException(String message) {
		super(message);
	}

	/**
	 * 构造方法
	 * 
	 * @param message
	 * @param cause
	 */
	public ProtoCodeGeneratorException(String message, Throwable cause) {
		super(message, cause);
	}
}
