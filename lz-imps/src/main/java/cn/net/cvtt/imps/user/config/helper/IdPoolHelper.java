package cn.net.cvtt.imps.user.config.helper;

import cn.net.cvtt.configuration.ConfigTable;
import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationManager;
import cn.net.cvtt.configuration.spi.ZookeeperConfigurator;
import cn.net.cvtt.imps.user.config.tables.CFG_IdPool;
import cn.net.cvtt.lian.common.initialization.InitialException;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.resource.route.ResourceFactory;

public class IdPoolHelper {

	private static ConfigTable<Integer, CFG_IdPool> idPools;

	@Initializer
	public static void initialize() throws Exception {
		ConfigurationManager.subscribeConfigUpdate(ConfigType.TABLE, "CFG_IdPool", null);
		idPools = ConfigurationManager.loadTable(Integer.class, CFG_IdPool.class, "CFG_IdPool", 
				new ConfigUpdateAction<ConfigTable<Integer, CFG_IdPool>>() {
			public void run(ConfigTable<Integer, CFG_IdPool> table) throws Exception {
				idPools = table;
			}
		});
	}
	
	public static boolean isReservedNumber(long num) {
		for (CFG_IdPool cfg : idPools.getValues()) {
			if(cfg.getEnabled() == 1 && cfg.getNumber() == num){
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) throws InitialException {
		ConfigurationManager.setConfigurator(new ZookeeperConfigurator());
		InitialUtil.init(ResourceFactory.class,IdPoolHelper.class);
//		System.out.println(IdPoolHelper.isReservedNumber(10000));
		long userId = 10000;
		do {
			++userId;
		} while (IdPoolHelper.isReservedNumber(userId));
		
		System.out.println(userId);
	}

}
