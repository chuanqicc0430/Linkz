package cn.net.cvtt.imps.lianzi.config.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.net.cvtt.configuration.ConfigTable;
import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationManager;
import cn.net.cvtt.imps.lianzi.config.tables.CFG_SystemConfig;
import cn.net.cvtt.lian.common.initialization.Initializer;

public class SystemConfigHelper {

	private static List<CFG_SystemConfig> configList = new ArrayList<>();
	private static Map<String, List<CFG_SystemConfig>> systemConfigMap = new HashMap<>();

	@Initializer
	public static void initialize() throws Exception {
		ConfigurationManager.subscribeConfigUpdate(ConfigType.TABLE, "CFG_SystemConfig", null);
		ConfigurationManager.loadTable(Integer.class, CFG_SystemConfig.class, "CFG_SystemConfig", new ConfigUpdateAction<ConfigTable<Integer, CFG_SystemConfig>>() {
			public void run(ConfigTable<Integer, CFG_SystemConfig> table) throws Exception {
				for (CFG_SystemConfig cfg : table.getValues()) {
					configList.add(cfg);
					List<CFG_SystemConfig> mapList = systemConfigMap.get(cfg.getConfigType());
					if (mapList == null) {
						mapList = new ArrayList<CFG_SystemConfig>();
					}
					mapList.add(cfg);
					systemConfigMap.put(cfg.getConfigType(), mapList);
				}

			}
		});
	}

	public static List<CFG_SystemConfig> getSystemConfigs() {
		return configList;
	}

	public static Map<String, List<CFG_SystemConfig>> getSystemConfigMap() {
		return systemConfigMap;
	}

}
