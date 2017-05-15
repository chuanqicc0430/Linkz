package cn.net.cvtt.configuration;

import cn.net.cvtt.configuration.resource.ResdbConfigHelper;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.resource.route.ResourceFactory;

public class TestZookeeperConfigurator {
	public static void main(String[] args) {
		try {
			InitialUtil.init(ResourceFactory.class);
			ConfigParams configParams = new ConfigParams();
			configParams.put("service", "lz-lianzi");
			String text = ResdbConfigHelper.getConfigText("httpcontainer", configParams).getText();
			System.out.println(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
