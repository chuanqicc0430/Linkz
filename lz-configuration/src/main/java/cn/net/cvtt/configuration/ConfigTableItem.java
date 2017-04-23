package cn.net.cvtt.configuration;

/**
 * 当声明一个ConfigTable的时候, 用
 * 
 * @author 
 */
public abstract class ConfigTableItem
{
	/**
	 * 
	 * 当需要在获取玩配置后进行Item的某些后置操作时, 重载这个方法
	 */
	public void afterLoad() throws Exception
	{
		//
		// Do Nothing in BaseClass
	}
}
