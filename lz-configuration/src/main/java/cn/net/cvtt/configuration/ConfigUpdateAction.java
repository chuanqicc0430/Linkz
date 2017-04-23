package cn.net.cvtt.configuration;

/**
 * 配置更新的回调类 和Action<E>相比，允许异常抛出
 * 
 * @author
 */
public interface ConfigUpdateAction<E> {
	void run(E e) throws Exception;
}
