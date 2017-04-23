package cn.net.cvtt.imps.lianzi.config.helper;

import cn.net.cvtt.configuration.ConfigTable;
import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationManager;
import cn.net.cvtt.imps.lianzi.config.tables.CFG_ProtocolErrorRsp;
import cn.net.cvtt.lian.common.initialization.Initializer;

public class ClientResourceHelper {

	private static ConfigTable<Integer, CFG_ProtocolErrorRsp> resoueces;

	@Initializer
	public static void initialize() throws Exception {
		ConfigurationManager.subscribeConfigUpdate(ConfigType.TABLE, "CFG_ClientResourceCode", null);
		resoueces = ConfigurationManager.loadTable(Integer.class, CFG_ProtocolErrorRsp.class, "CFG_ClientResourceCode", 
				new ConfigUpdateAction<ConfigTable<Integer, CFG_ProtocolErrorRsp>>() {
			public void run(ConfigTable<Integer, CFG_ProtocolErrorRsp> table) throws Exception {
				resoueces = table;
			}
		});
	}
	
	public static CFG_ProtocolErrorRsp getClientResourceCode(int code) {
		return resoueces.get(code);
	}
}
