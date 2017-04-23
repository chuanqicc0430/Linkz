package cn.net.cvtt.imps.lianzi.job;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.configuration.ConfigurationException;
import cn.net.cvtt.configuration.ConfigurationManager;

/**
 * 读取配置文件
 * 
 * @author zongchuanqi
 */
public class LoadLtsJobClientConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoadLtsJobClientConfig.class);
	private static Map<String, String> values = new HashMap<String, String>();
	private static final String JOBTRACKER_CONFIG_SETTING = "lts-jobclient";
	private static Object syncLock = new Object();
	private static LoadLtsJobClientConfig instance = null;

	public static LoadLtsJobClientConfig getInstance() {
		if (instance == null) {
			synchronized (syncLock) {
				if (instance == null) {
					instance = new LoadLtsJobClientConfig();
				}
			}
		}
		return instance;
	}

	private LoadLtsJobClientConfig() {
		try {
			Properties properties = ConfigurationManager.loadProperties(JOBTRACKER_CONFIG_SETTING, null, null);
			values.put("lts.jobclient.cluster-name", properties.getProperty("lts.jobclient.cluster-name"));
			values.put("lts.jobclient.registry-address", properties.getProperty("lts.jobclient.registry-address"));
			values.put("lts.jobclient.node-group", properties.getProperty("lts.jobclient.node-group"));
			values.put("lts.jobclient.use-retry-client", properties.getProperty("lts.jobclient.use-retry-client"));
			values.put("lts.jobclient.configs.job.fail.store", properties.getProperty("lts.jobclient.configs.job.fail.store"));
		} catch (ConfigurationException e) {
			LOGGER.error("load lts-jobclient.properties error ", e);
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
