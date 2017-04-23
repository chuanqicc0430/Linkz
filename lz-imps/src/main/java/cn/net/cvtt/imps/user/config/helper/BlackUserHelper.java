package cn.net.cvtt.imps.user.config.helper;

import cn.net.cvtt.configuration.ConfigTable;
import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationManager;
import cn.net.cvtt.imps.user.config.tables.CFG_BlackUser;
import cn.net.cvtt.lian.common.initialization.InitialException;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.resource.route.ResourceFactory;

public class BlackUserHelper {

	private static ConfigTable<Integer, CFG_BlackUser> whiteList;

	@Initializer
	public static void initialize() throws Exception {
		ConfigurationManager.subscribeConfigUpdate(ConfigType.TABLE, "CFG_BlackUser", null);
		whiteList = ConfigurationManager.loadTable(Integer.class, CFG_BlackUser.class, "CFG_BlackUser", 
				new ConfigUpdateAction<ConfigTable<Integer, CFG_BlackUser>>() {
			public void run(ConfigTable<Integer, CFG_BlackUser> table) throws Exception {
				whiteList = table;
			}
		});
	}
	
	public static boolean isBlackUser(long mobileNo) {
		for (CFG_BlackUser cfg : whiteList.getValues()) {
			if(cfg.getMobileNo() == mobileNo){
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) throws InitialException {
		InitialUtil.init(ResourceFactory.class,BlackUserHelper.class);
	}

}
