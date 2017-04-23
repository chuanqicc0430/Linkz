package cn.net.cvtt.lian.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <b>描述: </b>异步调用转同步调用辅助类<br>
 * <p>
 * <b>功能: </b>用于将异步调用转为同步调用
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * SyncInvoker invoker = new SyncInvoker();<br>
 * caller.invoke(invoker.getCallback());<br>
 * if (invoker.waitFor(1000)) {<br>
 * 	
 * }
 * </pre>
 * <p>
 * 
 * @author 
 * @author 
 * 
 * @param <T>
 */
public class SyncInvoker<T>
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncInvoker.class);
	
	private final Object syncRoot = new Object();
	private Action<T> callback;
	
	private T result;
	private boolean gotResult = false;

	public SyncInvoker()
	{
		callback = new Action<T>() {
			@Override
			public void run(T r)
			{
				result = r;
				synchronized (syncRoot) {
					gotResult = true;
					syncRoot.notify();
				}
			}
		};
	}

	/**
	 * 
	 * 在时间内等待回调, 回调后，或超时后返回
	 * 
	 * @param ms 毫秒
	 * @return 超时返回: false
	 */
	public boolean waitFor(long ms)
	{
		try {
			synchronized (syncRoot) {
				long remains = ms;
				long b = System.currentTimeMillis();
				while (!gotResult) {
					syncRoot.wait(remains);
					long e = System.currentTimeMillis();
					remains -= e - b;
					if (remains <= 0)
						break;
				}
			}
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage());
		}
		return gotResult;
	}

	public Action<T> getCallback()
	{
		return callback;
	}
	
	public T getResult()
	{
		return result;
	}
}
