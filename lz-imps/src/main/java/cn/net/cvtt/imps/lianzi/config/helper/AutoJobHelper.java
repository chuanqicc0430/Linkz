package cn.net.cvtt.imps.lianzi.config.helper;

import cn.net.cvtt.configuration.ConfigTable;
import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationManager;
import cn.net.cvtt.imps.lianzi.config.tables.CFG_AutoJob;
import cn.net.cvtt.lian.common.initialization.InitialException;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.resource.route.ResourceFactory;

public class AutoJobHelper {
	private static ConfigTable<String, CFG_AutoJob> orgAutoJobs;

	@Initializer
	public static void initialize() throws Exception {
		ConfigurationManager.subscribeConfigUpdate(ConfigType.TABLE, "cfg_AutoJob", null);
		orgAutoJobs = ConfigurationManager.loadTable(String.class, CFG_AutoJob.class, "cfg_AutoJob", 
				new ConfigUpdateAction<ConfigTable<String, CFG_AutoJob>>() {
			public void run(ConfigTable<String, CFG_AutoJob> table) throws Exception {
				orgAutoJobs = table;
			}
		});
	}
	
	public static CFG_AutoJob getAutoJob(String orgAutoName) {
		return orgAutoJobs.get(orgAutoName);
	}
	
	public static void main(String[] args) {
		try {
			InitialUtil.init(ResourceFactory.class,AutoJobHelper.class);
		} catch (InitialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(AutoJobHelper.getAutoJob("name").toString());
	}
}
