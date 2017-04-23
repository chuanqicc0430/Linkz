package cn.net.cvtt.resource.database.pool;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidPoolAdapter extends ConnectionPoolAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(DruidPoolAdapter.class);

	DruidPoolAdapter(Properties configs, DBConnectionPoolType type) throws Exception {
		super(configs, type);
		/*
		 * 在外界没有配置的情况下，预设一些Druid的参数
		 */
		if (!configs.containsKey("initialSize")) {
			configs.setProperty("initialSize", "1");
		}
		if (!configs.containsKey("maxActive")) {
			configs.setProperty("maxActive", "500");
		}
//		if (!configs.containsKey("maxIdle")) {
//			configs.setProperty("maxIdle", "2");
//		}
		if (!configs.containsKey("minIdle")) {
			configs.setProperty("minIdle", "1");
		}
		if (!configs.containsKey("maxWait")) {
			configs.setProperty("maxWait", "60000");
		}
		if (!configs.containsKey("timeBetweenEvictionRunsMillis")) {
			configs.setProperty("timeBetweenEvictionRunsMillis", "60000");
		}
		if (!configs.containsKey("minEvictableIdleTimeMillis")) {
			configs.setProperty("minEvictableIdleTimeMillis", "300000");
		}
		if (!configs.containsKey("validationQuery")) {
			configs.setProperty("validationQuery", "SELECT 'x'");
		}
		if (!configs.containsKey("testWhileIdle")) {
			configs.setProperty("testWhileIdle", "true");
		}
		if (!configs.containsKey("testOnBorrow")) {
			configs.setProperty("testOnBorrow", "false");
		}
		if (!configs.containsKey("testOnReturn")) {
			configs.setProperty("testOnReturn", "false");
		}
		if (!configs.containsKey("poolPreparedStatements")) {
			configs.setProperty("poolPreparedStatements", "false");
		}
		if (!configs.containsKey("maxPoolPreparedStatementPerConnectionSize")) {
			configs.setProperty("maxPoolPreparedStatementPerConnectionSize", "20");
		}

		dataSource = new DruidDataSource();
		// 通过反射动态设定数据源属性
		Class<DruidDataSource> clazz = DruidDataSource.class;
		Method[] methods = null;
		String key = null;
		String val = null;
		String methodName = null;
		Enumeration<?> eunm = configs.propertyNames();
		while (eunm.hasMoreElements()) {
			key = (String) eunm.nextElement();
			val = configs.getProperty(key);
			methodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
			methods = clazz.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					try {
						if (method.getParameterTypes()[0].getName() == "int") {
							method.invoke(dataSource, Integer.parseInt(val));
						} else if (method.getParameterTypes()[0].getName() == "long") {
							method.invoke(dataSource, Long.parseLong(val));
						} else if (method.getParameterTypes()[0].getName() == "float") {
							method.invoke(dataSource, Float.parseFloat(val));
						} else if (method.getParameterTypes()[0].getName() == "double") {
							method.invoke(dataSource, Double.parseDouble(val));
						} else if (method.getParameterTypes()[0].getName() == "boolean") {
							method.invoke(dataSource, Boolean.parseBoolean(val));
						} else if (method.getParameterTypes()[0].getName() == "java.lang.String") {
							method.invoke(dataSource, val);
						} else {
							method.invoke(dataSource, val);
						}
					} catch (Exception e) {
						LOGGER.error("init database error{}", e);
						throw e;
					}
					break;
				}
			}
		}
	}
}
