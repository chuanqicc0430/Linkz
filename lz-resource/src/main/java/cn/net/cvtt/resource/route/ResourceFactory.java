package cn.net.cvtt.resource.route;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import cn.net.cvtt.resource.database.DataRow;
import cn.net.cvtt.resource.database.DataTable;
import cn.net.cvtt.resource.database.Database;
import cn.net.cvtt.resource.database.DatabaseManager;
import cn.net.cvtt.resource.database.DatabaseProxy;
import cn.net.cvtt.resource.database.DatabaseResource;
import cn.net.cvtt.resource.database.pool.DBConnectionPoolType;
import cn.net.cvtt.resource.redis.RedisClient;
import cn.net.cvtt.resource.redis.RedisKey;
import cn.net.cvtt.resource.redis.RedisProxy;
import cn.net.cvtt.resource.redis.RedisResource;
import cn.net.cvtt.resource.route.locator.GlobalLocator;
import cn.net.cvtt.resource.route.locator.HashLocator;
import cn.net.cvtt.resource.route.locator.LocatorType;
import cn.net.cvtt.resource.route.locator.ResourceLocator;
import cn.net.cvtt.resource.route.resource.Resource;
import cn.net.cvtt.resource.route.resource.ResourceGroup;
import cn.net.cvtt.resource.route.resource.ResourceProxy;
import cn.net.cvtt.resource.route.resource.ResourceType;
import cn.net.cvtt.resource.route.resource.ResourceUri;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.lian.common.util.DictionaryList;

/**
 * 在AppBean中获取访问资源的代理 工厂类
 * 
 * @author
 */
public class ResourceFactory {
	private static Hashtable<String, ResourceProxy> proxys;
	private static Hashtable<String, ResourceGroup<?>> resources;

	@Initializer
	public static void initialize() throws Exception {
		InputStream inputStream = ResourceFactory.class.getClassLoader().getResourceAsStream("resourceDb.properties");
		Properties props = new Properties();
		props.load(inputStream);

		Database db = DatabaseManager.getDatabase("resourcedb", props, DBConnectionPoolType.Druid);
		String resourceSql = "select * from RES_Resource";
		DataTable resourceTable = db.executeTable(resourceSql);

		DictionaryList<String, Resource> list = new DictionaryList<String, Resource>();
		if (resourceTable != null && resourceTable.getRowCount() > 0) {
			for (DataRow row : resourceTable.getRows()) {
				String resourceName = row.getString("ResourceName");
				String resourceType = row.getString("ResourceType");
				int resourceIndex = row.getInt("ResourceIndex");
				String config = row.getString("Config");

				ResourceUri r = new ResourceUri(resourceName, resourceType, resourceIndex, config);
				if (resourceType.equals(ResourceType.DATABASE.name())) {
					list.put(resourceName, new DatabaseResource(r));
				} else if (resourceType.equals(ResourceType.REDIS.name())) {
					list.put(resourceName, new RedisResource(r));
				}
			}
			Hashtable<String, ResourceGroup<?>> temp = new Hashtable<String, ResourceGroup<?>>();
			for (String k : list.keys()) {
				temp.put(k, new ResourceGroup<Resource>(list.get(k)));
			}
			resources = temp;
		}

		String policySql = "select * from RES_ResourcePolicy";
		DataTable polictTable = db.executeTable(policySql);

		Hashtable<String, ResourceProxy> tempProxys = new Hashtable<String, ResourceProxy>();
		if (polictTable != null && polictTable.getRowCount() > 0) {
			for (DataRow row : polictTable.getRows()) {
				String resourceName = row.getString("ResourceName");
				String resourceType = row.getString("ResourceType");
				String protocol = row.getString("Protocol");
				String locator = row.getString("Locator");
				String locatorParams = row.getString("LocatorParams");

				ResourceGroup<? extends Resource> group = resources.get(resourceName);
				ResourceLocator resourceLocator = null;
				if (locator.equals(LocatorType.GLOBAL.name())) {
					resourceLocator = new GlobalLocator();
				} else if (locator.equals(LocatorType.IDHASH.name())) {
					resourceLocator = new HashLocator();
				}
				resourceLocator.setParams(group, locatorParams);

				ResourceProxy proxy = tempProxys.get(resourceName);

				if (proxy == null) {
					if (resourceType.equals(ResourceType.DATABASE.name())) {
						proxy = new DatabaseProxy();
					} else if (resourceType.equals(ResourceType.REDIS.name())) {
						proxy = new RedisProxy();
					}
					tempProxys.put(resourceName, proxy);
				}
				proxy.addLocator(protocol, resourceLocator);
			}
			proxys = tempProxys;
		}
	}

	public static DatabaseProxy getDatabaseProxy(String name) {
		DatabaseProxy proxy = (DatabaseProxy) proxys.get(name);
		if (proxy == null)
			throw new IllegalArgumentException("Unknown database:" + name);
		return proxy;
	}

	public static RedisProxy getRedisProxy(String name) {
		RedisProxy proxy = (RedisProxy) proxys.get(name);
		if (proxy == null)
			throw new IllegalArgumentException("Unknown redis:" + name);
		return proxy;
	}

	public static void main(String[] args) throws Exception {
		InitialUtil.init(ResourceFactory.class);
		
		DatabaseProxy dbProxy = ResourceFactory.getDatabaseProxy("UPDB");
		String sql = "SELECT * FROM UP_User where UserId = ?";
		DataTable dt = dbProxy.executeTable(0, sql, 10001);
		for(DataRow row : dt.getRows()){
			System.out.println(row.getLong("UserId"));
			System.out.println(row.getString("Name"));
		}
		
		RedisProxy redisProxy = ResourceFactory.getRedisProxy("LZUserRedis");
		RedisClient p = redisProxy.getRedisClient(0);
		System.out.println(p.getStr(RedisKey.EMCHAT_AUTH_TOKEN_KEY));
	}
}
