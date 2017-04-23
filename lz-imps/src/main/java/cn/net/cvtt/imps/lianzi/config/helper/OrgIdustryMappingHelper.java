package cn.net.cvtt.imps.lianzi.config.helper;

import cn.net.cvtt.configuration.ConfigTable;
import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationManager;
import cn.net.cvtt.imps.lianzi.config.tables.CFG_OrgIdustryMapping;
import cn.net.cvtt.lian.common.initialization.InitialException;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.resource.route.ResourceFactory;

public class OrgIdustryMappingHelper {

	private static ConfigTable<Integer, CFG_OrgIdustryMapping> mappings;

	@Initializer
	public static void initialize() throws Exception {
		ConfigurationManager.subscribeConfigUpdate(ConfigType.TABLE, "CFG_OrgIdustryMapping", null);
		mappings = ConfigurationManager.loadTable(Integer.class, CFG_OrgIdustryMapping.class, "CFG_OrgIdustryMapping", 
				new ConfigUpdateAction<ConfigTable<Integer, CFG_OrgIdustryMapping>>() {
			public void run(ConfigTable<Integer, CFG_OrgIdustryMapping> table) throws Exception {
				mappings = table;
			}
		});
	}
	
	public static CFG_OrgIdustryMapping getOrgIdustryMappingByIndustryId(String industryId) {
		for (CFG_OrgIdustryMapping mapping : mappings.getValues()) {
			if (mapping.getIndustryId().equals(industryId)) {
				return mapping;
			}
		}
		return null;
	}
	
	public static CFG_OrgIdustryMapping getOrgIdustryMappingByOrgCategoryId(String orgCategoryId) {
		for (CFG_OrgIdustryMapping mapping : mappings.getValues()) {
			if (mapping.getOrgCategoryId().equals(orgCategoryId)) {
				return mapping;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		try {
			InitialUtil.init(ResourceFactory.class,OrgIdustryMappingHelper.class);
		} catch (InitialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(OrgIdustryMappingHelper.getOrgIdustryMappingByIndustryId("S9421000D").toString());
	}
	
}
