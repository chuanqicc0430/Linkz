package cn.net.cvtt.imps.user.config.helper;

import cn.net.cvtt.configuration.ConfigTable;
import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationManager;
import cn.net.cvtt.imps.user.config.tables.CFG_WhiteList;
import cn.net.cvtt.lian.common.initialization.InitialException;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.resource.route.ResourceFactory;

public class WhiteListHelper {

	private static ConfigTable<Integer, CFG_WhiteList> whiteList;

	@Initializer
	public static void initialize() throws Exception {
		ConfigurationManager.subscribeConfigUpdate(ConfigType.TABLE, "CFG_WhiteList", null);
		whiteList = ConfigurationManager.loadTable(Integer.class, CFG_WhiteList.class, "CFG_WhiteList", 
				new ConfigUpdateAction<ConfigTable<Integer, CFG_WhiteList>>() {
			public void run(ConfigTable<Integer, CFG_WhiteList> table) throws Exception {
				whiteList = table;
			}
		});
	}
	
	public static boolean isWhiteMobileNo(long mobileNo) {
		for (CFG_WhiteList cfg : whiteList.getValues()) {
			if(cfg.getEnabled() == 1 && cfg.getMobileNo() == mobileNo){
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) throws InitialException {
//		ConfigurationManager.setConfigurator(new ZKConfigurator());
		InitialUtil.init(ResourceFactory.class,WhiteListHelper.class);
//		System.out.println(IdPoolHelper.isReservedNumber(10000));
		System.out.println(WhiteListHelper.isWhiteMobileNo(18710156508L));
	}

}
