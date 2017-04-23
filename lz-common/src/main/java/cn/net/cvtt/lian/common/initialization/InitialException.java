package cn.net.cvtt.lian.common.initialization;

/**
 * 
 * <b>描述: </b>在初始化失败时，此类型异常会被抛出
 * <p>
 * <b>功能: </b>用于标识初始化失败的异常，详见{@link InitialUtil#init(Class...)}
 * <p>
 * <b>用法: </b>常规的异常捕捉
 * <p>
 * 
 * 
 */
public class InitialException extends Exception {
	private static final long serialVersionUID = -1184386321171609754L;

	public InitialException(Class<?> clazz, Throwable e) {
		super("@Initializer failed for " + clazz.getName(), e);
	}
}
