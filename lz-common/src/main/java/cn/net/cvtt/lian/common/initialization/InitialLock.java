package cn.net.cvtt.lian.common.initialization;

/**
 * 
 * <b>描述: </b>启动双检锁
 * <p>
 * <b>功能: </b>双检锁,保证任务只被执行一次
 * <p>
 * <b>用法: </b>
 * <p>
 * 
 * @author 
 * 
 */
public class InitialLock
{
	private boolean inited = false;
	private Object syncRoot = new Object();
	
	public InitialLock()
	{
	}
	
	public void doInit(Runnable initer)
	{
		if (inited)
			return;
		
		synchronized (syncRoot) {
			if (!inited) {
				initer.run();
				inited = true;
			}
		}
	}
}
