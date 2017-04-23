package cn.net.cvtt.resource.database.pool;

import java.util.Properties;

public class ConnectionPoolAdapterFactory {
		
	private static ConnectionPoolAdapterFactory instance;

	private ConnectionPoolAdapterFactory()
	{
		
	}
	
	public synchronized static ConnectionPoolAdapterFactory getInstance()
	{
		if(instance == null)
			instance = new ConnectionPoolAdapterFactory();
		
		return instance;
	}
	
	public ConnectionPoolAdapter getConnectionPoolAdapter(Properties config,DBConnectionPoolType poolType) throws Exception
	{
		ConnectionPoolAdapter adapter = null;
		switch(poolType)
		{
//			case  C3p0:
//				adapter = new C3p0PoolAdapter(config,poolType);
//				break;
//			case TomcatPool:
//				adapter = new TomcatPoolAdapter(config,poolType);
//				break;
			case Druid:
				adapter = new DruidPoolAdapter(config,poolType);
				break;
			default:
				throw new IllegalArgumentException("unsupported DBConnectionPoolType:"+poolType);
		}
		
		return adapter;
	}
}
