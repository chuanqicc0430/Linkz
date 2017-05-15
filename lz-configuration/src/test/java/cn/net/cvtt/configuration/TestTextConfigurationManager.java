package cn.net.cvtt.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.configuration.spi.ZookeeperConfigurator;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.resource.route.ResourceFactory;


public class TestTextConfigurationManager {
	
	public static void main(String[] args) throws Exception {
		initResource();
		try {
			ConfigurationManager.loadText("lianziconfig", null, new ConfigUpdateAction<String>() {
				@Override
				public void run(String e) throws Exception {
					LOGGER.info("*****************Config change*******************");
					System.out.println(e);
				}
			});
			synchronized (TestTextConfigurationManager.class) {
				TestTextConfigurationManager.class.wait();
			}
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void initResource() throws Exception {
		InitialUtil.init(ResourceFactory.class);
		ConfigurationManager.setConfigurator(new ZookeeperConfigurator());
		ConfigurationManager.subscribeConfigUpdate(ConfigType.TEXT, "lianziconfig", null);
	}

	private static Logger LOGGER = LoggerFactory.getLogger(TestTextConfigurationManager.class);
}
