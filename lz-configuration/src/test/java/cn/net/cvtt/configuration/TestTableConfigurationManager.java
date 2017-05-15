package cn.net.cvtt.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.configuration.spi.ZookeeperConfigurator;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.resource.route.ResourceFactory;

public class TestTableConfigurationManager {

	public static void main(String[] args) throws Exception {
		initResource();
		try {
			ConfigurationManager.loadTable(Integer.class, CFG_ConfigEx.class, "CFG_ConfigEx", new ConfigUpdateAction<ConfigTable<Integer, CFG_ConfigEx>>() {

				@Override
				public void run(ConfigTable<Integer, CFG_ConfigEx> table) throws Exception {
					LOGGER.info("*****************Config change*******************");
					for (CFG_ConfigEx configEx : table.getValues()) {
						System.out.println(configEx.toString());
					}
				}
			});
			synchronized (TestTableConfigurationManager.class) {
				TestTableConfigurationManager.class.wait();
			}
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void initResource() throws Exception {
		InitialUtil.init(ResourceFactory.class);
		ConfigurationManager.setConfigurator(new ZookeeperConfigurator());
		ConfigurationManager.subscribeConfigUpdate(ConfigType.TABLE, "CFG_ConfigEx", null);
	}

	private static Logger LOGGER = LoggerFactory.getLogger(TestTableConfigurationManager.class);
	
}
