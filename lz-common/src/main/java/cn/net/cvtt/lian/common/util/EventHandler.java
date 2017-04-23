package cn.net.cvtt.lian.common.util;

/**
 * 事件处理器 
 * 
 * @author 
 */
public interface EventHandler<E>
{
	void run(Object sender, E e);
}
