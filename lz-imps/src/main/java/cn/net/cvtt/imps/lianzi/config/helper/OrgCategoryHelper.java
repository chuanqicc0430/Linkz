package cn.net.cvtt.imps.lianzi.config.helper;

import cn.net.cvtt.configuration.ConfigTable;
import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationManager;
import cn.net.cvtt.imps.lianzi.config.tables.CFG_OrgCategory;
import cn.net.cvtt.lian.common.initialization.InitialException;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.resource.route.ResourceFactory;

public class OrgCategoryHelper {

	private static ConfigTable<String, CFG_OrgCategory> orgCategorys;

	@Initializer
	public static void initialize() throws Exception {
		ConfigurationManager.subscribeConfigUpdate(ConfigType.TABLE, "CFG_OrgCategory", null);
		orgCategorys = ConfigurationManager.loadTable(String.class, CFG_OrgCategory.class, "CFG_OrgCategory", 
				new ConfigUpdateAction<ConfigTable<String, CFG_OrgCategory>>() {
			public void run(ConfigTable<String, CFG_OrgCategory> table) throws Exception {
				orgCategorys = table;
			}
		});
	}
	
	public static CFG_OrgCategory getOrgCategory(String orgCategoryCode) {
		return orgCategorys.get(orgCategoryCode);
	}
	
	public static void main(String[] args) {
		try {
			InitialUtil.init(ResourceFactory.class,OrgCategoryHelper.class);
		} catch (InitialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(OrgCategoryHelper.getOrgCategory("A0201").toString());
	}
}
