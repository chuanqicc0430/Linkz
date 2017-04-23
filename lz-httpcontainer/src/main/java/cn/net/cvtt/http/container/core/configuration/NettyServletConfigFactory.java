package cn.net.cvtt.http.container.core.configuration;

import java.util.Properties;

import cn.net.cvtt.configuration.ConfigParams;
import cn.net.cvtt.configuration.ConfigurationException;
import cn.net.cvtt.configuration.ConfigurationManager;
import cn.net.cvtt.lian.common.util.StringUtils;

/**
 * 加载netty server的配置，FAE支持按照key和param获取配置，因此可以根据不同service自定义配置，主要是端口需要不一样</br>
 * 服务启动这个配置一般不会动，暂不支持配置热更新
 * 
 * @author zongchuanqi
 *
 */
public class NettyServletConfigFactory {

	private final static String NETTY_SERVER_CONFIG = "httpcontainer";

	public static Properties getProperties(String serviceName) throws ConfigurationException {
		ConfigParams configParams = new ConfigParams();
		configParams.put("service", serviceName);

		Properties properties = ConfigurationManager.loadProperties(NETTY_SERVER_CONFIG, configParams, null);
		return properties;
	}

	/**
	 * @param serviceName
	 * @return nettyServletConfiguration
	 * @throws ConfigurationException
	 */
	public static NettyServletConfig createConfiguration(String serviceName) throws ConfigurationException {
		Properties properties = getProperties(serviceName);
		NettyServletConfig nettyServletConfiguration = new NettyServletConfig();

		// 特殊处理一下，如果是localhost只能本机访问，所以不指定ip的话，最好把本机变为null
		if (properties.getProperty("ip").equals("localhost")) {
			nettyServletConfiguration.setIp(null);
		} else {
			nettyServletConfiguration.setIp(properties.getProperty("ip"));
		}
		nettyServletConfiguration.setPort(Integer.valueOf(properties.getProperty("port")));

		String readIdle = properties.getProperty("readIdle");
		if (!StringUtils.isNullOrEmpty(readIdle)) {
			nettyServletConfiguration.setReadIdle(Integer.valueOf(readIdle));
		}
		String writeIdle = properties.getProperty("writeIdle");
		if (!StringUtils.isNullOrEmpty(writeIdle)) {
			nettyServletConfiguration.setWriteIdle(Integer.valueOf(writeIdle));
		}
		String workerOutTime = properties.getProperty("workerOutTime");
		if (!StringUtils.isNullOrEmpty(workerOutTime)) {
			nettyServletConfiguration.setWorkerOutTime(Integer.valueOf(workerOutTime));
		}
		String servletThreadSize = properties.getProperty("servletThreadSize");
		if (!StringUtils.isNullOrEmpty(servletThreadSize)) {
			nettyServletConfiguration.setServletThreadSize(Integer.valueOf(servletThreadSize));
		}
		String maxChunkSize = properties.getProperty("maxChunkSize");
		if (!StringUtils.isNullOrEmpty(maxChunkSize)) {
			nettyServletConfiguration.setMaxChunkContentSize(Integer.valueOf(maxChunkSize));
		}

		return nettyServletConfiguration;
	}
	
	
	public static NettyServletConfig createConfigurationByLocal(String serviceName,String ip,int port,String readIdle,String writeIdle,String workerOutTime,String servletThreadSize,String maxChunkSize) throws ConfigurationException {
	 
		NettyServletConfig nettyServletConfiguration = new NettyServletConfig();

		// 特殊处理一下，如果是localhost只能本机访问，所以不指定ip的话，最好把本机变为null
		if (ip.equals("localhost")) {
			nettyServletConfiguration.setIp(null);
		} else {
			nettyServletConfiguration.setIp(ip);
		}
		nettyServletConfiguration.setPort(Integer.valueOf(port));

		 
		if (!StringUtils.isNullOrEmpty(readIdle)) {
			nettyServletConfiguration.setReadIdle(Integer.valueOf(readIdle));
		}
		 
		if (!StringUtils.isNullOrEmpty(writeIdle)) {
			nettyServletConfiguration.setWriteIdle(Integer.valueOf(writeIdle));
		}
		 
		if (!StringUtils.isNullOrEmpty(workerOutTime)) {
			nettyServletConfiguration.setWorkerOutTime(Integer.valueOf(workerOutTime));
		}
		 
		if (!StringUtils.isNullOrEmpty(servletThreadSize)) {
			nettyServletConfiguration.setServletThreadSize(Integer.valueOf(servletThreadSize));
		}
		 
		if (!StringUtils.isNullOrEmpty(maxChunkSize)) {
			nettyServletConfiguration.setMaxChunkContentSize(Integer.valueOf(maxChunkSize));
		}

		return nettyServletConfiguration;
	}
}
