package cn.net.cvtt.textfilter;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.configuration.ConfigurationException;
import cn.net.cvtt.configuration.ConfigurationManager;

/**
 * 加载配置
 * 
 * @author 
 */
public class LoadTextFilterProperties {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoadTextFilterProperties.class);
	private static Map<String, String> values = new HashMap<String, String>();
	private static final String TEXTFILTER_CONFIG_SETTING = "textfilter";
	private static Object syncLock = new Object();
	private static LoadTextFilterProperties instance = null;

	public static LoadTextFilterProperties getInstance() {
		if (instance == null) {
			synchronized (syncLock) {
				if (instance == null) {
					instance = new LoadTextFilterProperties();
				}
			}
		}
		return instance;
	}

	private LoadTextFilterProperties() {
		try {
			Properties properties = ConfigurationManager.loadProperties(TEXTFILTER_CONFIG_SETTING, null, null);
			values.put("NormalizeConfusableCharacter", properties.getProperty("NormalizeConfusableCharacter"));
			values.put("FeatureDigestCycleTime", properties.getProperty("FeatureDigestCycleTime"));
			values.put("FeatureDigestPeriod", properties.getProperty("FeatureDigestPeriod"));
			values.put("SpecialCharacters", properties.getProperty("SpecialCharacters"));
			values.put("FeatureDigestEnabled", properties.getProperty("FeatureDigestEnabled"));
		} catch (ConfigurationException e) {
			LOGGER.error("load textfilter.properties error ", e);
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
