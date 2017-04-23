package cn.net.cvtt.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.resource.route.ResourceFactory;

/**
 * 读取配置文件
 * 
 * @author zongchuanqi
 */
public class LoadZkConfigProperties {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoadZkConfigProperties.class);
	private static Map<String, String> values = new HashMap<String, String>();
	private static final String ZK_CONFIG_SETTING = "zkConfigSetting";
	private static Object syncLock = new Object();
	private static LoadZkConfigProperties instance = null;

	public static LoadZkConfigProperties getInstance() {
		if (instance == null) {
			synchronized (syncLock) {
				if (instance == null) {
					instance = new LoadZkConfigProperties();
				}
			}
		}
		return instance;
	}

	private LoadZkConfigProperties() {
		try {
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

	public static void main(String[] args) {
		try{
			InitialUtil.init(ResourceFactory.class);
			System.out.println(LoadZkConfigProperties.getInstance().getPropValue("host"));
			System.out.println(LoadZkConfigProperties.getInstance().getPropValue("rootPath"));
		}catch(Exception e){
			
		}
	}
}
