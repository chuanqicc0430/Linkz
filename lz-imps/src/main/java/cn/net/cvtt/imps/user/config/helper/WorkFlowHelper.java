package cn.net.cvtt.imps.user.config.helper;

import cn.net.cvtt.configuration.ConfigTable;
import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationManager;
import cn.net.cvtt.configuration.spi.ZookeeperConfigurator;
import cn.net.cvtt.imps.user.config.enums.WorkFlowType;
import cn.net.cvtt.imps.user.config.tables.CFG_WorkFlow;
import cn.net.cvtt.lian.common.initialization.InitialException;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.resource.route.ResourceFactory;

public class WorkFlowHelper {

	private static ConfigTable<Integer, CFG_WorkFlow> workFlows;

	@Initializer
	public static void initialize() throws Exception {
		ConfigurationManager.subscribeConfigUpdate(ConfigType.TABLE, "CFG_WorkFlow", null);
		workFlows = ConfigurationManager.loadTable(Integer.class, CFG_WorkFlow.class, "CFG_WorkFlow", 
				new ConfigUpdateAction<ConfigTable<Integer, CFG_WorkFlow>>() {
			public void run(ConfigTable<Integer, CFG_WorkFlow> table) throws Exception {
				workFlows = table;
			}
		});
	}
	
	public static CFG_WorkFlow getWorkFlowByFlowType(WorkFlowType type) {
		for (CFG_WorkFlow wf : workFlows.getValues()) {
			if (wf.getFlowType() == type) {
				return wf;
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws InitialException {
		ConfigurationManager.setConfigurator(new ZookeeperConfigurator());
		InitialUtil.init(ResourceFactory.class,WorkFlowHelper.class);
		System.out.println(WorkFlowHelper.getWorkFlowByFlowType(WorkFlowType.CREATE_LIAN_AUDIT).toString());
	}

}
