package cn.net.cvtt.imps.authtoken.util;

import java.util.Properties;

import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationManager;
import cn.net.cvtt.lian.common.initialization.InitialException;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.lian.common.util.StringUtils;
import cn.net.cvtt.resource.route.ResourceFactory;

/**
 * 
 * @author zongchuanqi
 * 
 */
public class AuthConfigHelper {
	private static AuthConfig config = new AuthConfig();

	@Initializer
	public static void initialize() throws Exception {
		InitialUtil.init(ResourceFactory.class);
		ConfigurationManager.subscribeConfigUpdate(ConfigType.TEXT, "cAuthConfig", null);

		ConfigurationManager.loadProperties("cAuthConfig", null, new ConfigUpdateAction<Properties>() {
			@Override
			public void run(Properties p) throws Exception {
				String cKey = p.getProperty("cKey");
				if (!StringUtils.isNullOrEmpty(cKey)) {
					config.setcKey(cKey);
				}
				String cTimeout = p.getProperty("cTimeout");
				if (!StringUtils.isNullOrEmpty(cTimeout)) {
					config.setcTimeout(Integer.parseInt(cTimeout));
				}
				
				System.out.println(cKey+"  ______-------------------");
				System.out.println(cTimeout+"  ______-------------------");
			}
		});
//		synchronized (AuthConfigHelper.class) {
//			System.out.println("********************************");
//			AuthConfigHelper.class.wait();
//		}
	}

	public static AuthConfig getConfig() {
		if (config == null) {
			throw new NullPointerException("AuthConfig is null.");
		}
		return config;
	}
	
	public static void main(String[] args) throws InitialException {
		InitialUtil.init(AuthConfigHelper.class);
	}

}