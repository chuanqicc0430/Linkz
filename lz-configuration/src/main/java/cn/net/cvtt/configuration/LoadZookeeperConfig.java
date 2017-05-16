package cn.net.cvtt.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取配置文件
 * 
 * @author zongchuanqi
 */
public class LoadZookeeperConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoadZookeeperConfig.class);
	private static Map<String, String> values = new HashMap<String, String>();
	private static final String ZK_CONFIG_SETTING = "zkConfigSetting";
	private static Object syncLock = new Object();
	private static LoadZookeeperConfig instance = null;

	public static LoadZookeeperConfig getInstance() {
		if (instance == null) {
			synchronized (syncLock) {
				if (instance == null) {
					instance = new LoadZookeeperConfig();
				}
			}
		}
		return instance;
	}

	private LoadZookeeperConfig() {
		try {
			// 这里只从数据库加载zookeeper配置，并没有发起订阅
			Properties properties = ConfigurationManager.loadProperties(ZK_CONFIG_SETTING, null, null);
			values.put("host", properties.getProperty("host"));
			values.put("rootPath", properties.getProperty("rootPath"));
		} catch (ConfigurationException e) {
			LOGGER.error("load zkConfigSetting.properties error ", e);
		}
	}

	/**
	 * 获取 properties 文件中指定key 的值
	 * 
	 * @param key
	 * @return 字符串
	 * @throws ConfigurationException
	 */
	public String getPropValue(String key) throws ConfigurationException {
		String value = values.get(key);
		if (value == null)
			throw new ConfigurationException();
		return value;
	}
}
