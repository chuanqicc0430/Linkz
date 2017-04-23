package cn.net.cvtt.imps.lianzi.config.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.net.cvtt.configuration.ConfigTable;
import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationManager;
import cn.net.cvtt.imps.lianzi.config.tables.CFG_OrgIndustryLevel;
import cn.net.cvtt.lian.common.initialization.InitialException;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.resource.route.ResourceFactory;

public class OrgIndustryLevelHelper {
	
	private static ConfigTable<String, CFG_OrgIndustryLevel> industries;
	private static Map<String, List<CFG_OrgIndustryLevel>> parentIndustries = new HashMap<String, List<CFG_OrgIndustryLevel>>();
 
	@Initializer
	public static void initialize() throws Exception {
		ConfigurationManager.subscribeConfigUpdate(ConfigType.TABLE, "CFG_OrgIndustryLevel", null);
		industries = ConfigurationManager.loadTable(String.class, CFG_OrgIndustryLevel.class, "CFG_OrgIndustryLevel", new ConfigUpdateAction<ConfigTable<String, CFG_OrgIndustryLevel>>() {
			public void run(ConfigTable<String, CFG_OrgIndustryLevel> table) throws Exception {
				for (CFG_OrgIndustryLevel industry : table.getValues()) {
					if (!"0".equals(industry.getParentId())) {
						List<CFG_OrgIndustryLevel> mapList = parentIndustries.get(industry.getParentId());
						if (mapList == null) {
							mapList = new ArrayList<CFG_OrgIndustryLevel>();
						}
						mapList.add(industry);
						parentIndustries.put(industry.getParentId(), mapList);
					}
				}
				industries = table;
			}
		});
	}

	public static CFG_OrgIndustryLevel getOrgIdustryByIndustryId(String industryId) {
		return industries.get(industryId);
	}
	
	public static boolean isOrgIndustry(String industryId) {
		CFG_OrgIndustryLevel level = industries.get(industryId);
		if(level == null){
			return false;
		}
		return true;
	}

	public static List<CFG_OrgIndustryLevel> getOrgIndustryAndSubIndustry(String industryId) {
		List<CFG_OrgIndustryLevel> topList = new ArrayList<CFG_OrgIndustryLevel>();
		CFG_OrgIndustryLevel topIndustry = getOrgIdustryByIndustryId(industryId);
		if (topIndustry.getLevelNum() == 1) {
			List<CFG_OrgIndustryLevel> secontLevelList = parentIndustries.get(topIndustry.getIndustryId());
			if (secontLevelList != null && !secontLevelList.isEmpty()) {
				for (CFG_OrgIndustryLevel secondLvel : secontLevelList) {
					List<CFG_OrgIndustryLevel> thirdLevelList = parentIndustries.get(secondLvel.getIndustryId());
					if (thirdLevelList != null && !thirdLevelList.isEmpty()) {
						for (CFG_OrgIndustryLevel thirdLvel : thirdLevelList) {
							List<CFG_OrgIndustryLevel> fourthLevelList = parentIndustries.get(thirdLvel.getIndustryId());
							if (fourthLevelList != null && !fourthLevelList.isEmpty()) {
								topList.addAll(fourthLevelList);
							}
						}
					}
				}
			}
		} else if (topIndustry.getLevelNum() == 2) {
			List<CFG_OrgIndustryLevel> secontLevelList = parentIndustries.get(topIndustry.getIndustryId());
			if (secontLevelList != null && !secontLevelList.isEmpty()) {
				for (CFG_OrgIndustryLevel secondLvel : secontLevelList) {
					List<CFG_OrgIndustryLevel> thirdLevelList = parentIndustries.get(secondLvel.getIndustryId());
					if (thirdLevelList != null && !thirdLevelList.isEmpty()) {
						topList.addAll(thirdLevelList);
					}
				}
			}
		} else if (topIndustry.getLevelNum() == 3) {
			List<CFG_OrgIndustryLevel> secontLevelList = parentIndustries.get(topIndustry.getIndustryId());
			if (secontLevelList != null && !secontLevelList.isEmpty()) {
				topList.addAll(secontLevelList);
			}else{
				topList.add(topIndustry);
			}
		}
		return topList;
	}

	
	/**
	 * 获取根节点行业的所有子集，包括根节点
	 * @param rootNote（根节点标志，第一次调用为根节点，递归调用时不是根节点）
	 * @param industryId
	 * @param indutrys
	 * @return
	 */
	public static List<CFG_OrgIndustryLevel> getAllSubIndutryIds(Boolean rootNote, String industryId, List<CFG_OrgIndustryLevel> indutrys) {
		CFG_OrgIndustryLevel thisIndustry = industries.get(industryId);
		List<CFG_OrgIndustryLevel> levels = parentIndustries.get(industryId);
		if (rootNote) {
			indutrys.add(thisIndustry);
		}
		if (levels == null || thisIndustry.getLevelNum() == 4) {
			return indutrys;
		}
		if (levels != null && !levels.isEmpty()) {
			indutrys.addAll(levels);
			for (CFG_OrgIndustryLevel cfg_OrgIndustryLevel : levels) {
				getAllSubIndutryIds(false, cfg_OrgIndustryLevel.getIndustryId(), indutrys);
			}
		}
		return indutrys;
	}

	public static List<CFG_OrgIndustryLevel> getIndustriesByKeyword(String keyword){
		List<CFG_OrgIndustryLevel> list = new ArrayList<>();
		for(CFG_OrgIndustryLevel level : industries.getValues()){
			if(level.getIndustryName().indexOf(keyword)>=0){
				list.add(level);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		try {
			InitialUtil.init(ResourceFactory.class,  OrgIndustryLevelHelper.class);
		} catch (InitialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = 0;
		List<CFG_OrgIndustryLevel> list = null;
		System.out.println(System.currentTimeMillis()+"----------------------------------");
		for(CFG_OrgIndustryLevel level : industries.getValues()){
			if(level.getIndustryName().indexOf("计算机")>=0){
				list.add(level);
			}
		}
		System.out.println(System.currentTimeMillis()+"----------------------------------"+list.toString());

		// System.out.println(list.size());
//		for (CFG_OrgIndustryLevel ind : list1) {
//			System.out.println(ind.getIndustryId());
//		}
	}
}
