package cn.net.cvtt.imps.lianzi.config.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.net.cvtt.configuration.ConfigTable;
import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationManager;
import cn.net.cvtt.imps.lianzi.config.tables.CFG_Region;
import cn.net.cvtt.lian.common.initialization.InitialException;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.lian.common.util.StringUtils;
import cn.net.cvtt.resource.route.ResourceFactory;

public class RegionHelper {

	private static ConfigTable<String, CFG_Region> regions;
	private static Map<String, List<CFG_Region>> parentRegions = new HashMap<String, List<CFG_Region>>();
	private static Map<String,String> nameRegions = new HashMap<String,String>();
	private static List<CFG_Region> provinces = new ArrayList<>();

	@Initializer
	public static void initialize() throws Exception {
		ConfigurationManager.subscribeConfigUpdate(ConfigType.TABLE, "CFG_Region",null);
		regions = ConfigurationManager.loadTable(String.class, CFG_Region.class, "CFG_Region", new ConfigUpdateAction<ConfigTable<String, CFG_Region>>() {
			public void run(ConfigTable<String, CFG_Region> table) throws Exception {
				for (CFG_Region region : table.getValues()) {
					if (!"0".equals(region.getParentRegionId())) {
						List<CFG_Region> mapList = parentRegions.get(region.getParentRegionId());
						if (mapList == null) {
							mapList = new ArrayList<CFG_Region>();
						}
						mapList.add(region);
						parentRegions.put(region.getParentRegionId(), mapList);
					}

				}
				regions = table;
				for(CFG_Region region : regions.getValues()){
					switch (region.getLevelNum()){
						case 1:
							nameRegions.put(region.getRegionFullName(),region.getRegionId());
							provinces.add(region);
							break;
						case 2:
							String sName = regions.get(region.getParentRegionId()).getRegionFullName()+region.getRegionFullName();
							nameRegions.put(sName,region.getRegionId());
							break;
						case 3:
							String tName = regions.get(regions.get(region.getParentRegionId()).getParentRegionId()).getRegionFullName()+
									regions.get(region.getParentRegionId()).getRegionFullName()+region.getRegionFullName();
							nameRegions.put(tName,region.getRegionId());

					}
				}
			}
		});
	}

	public static CFG_Region getRegionByRegionId(String regionId) {
		return regions.get(regionId);
	}

	public static List<String> getRegionIdList(String regionFullName) {
		String regionId= nameRegions.get(regionFullName);
		if(!StringUtils.isNullOrEmpty(regionId)){
			List<CFG_Region> regionList = getRegionAndSubRegion(regionId);
			List<String> regionIdList = new ArrayList<>();
			for(CFG_Region region:regionList){
				regionIdList.add(region.getRegionId());
			}
			return regionIdList;
		}
		return null;
	}

//	public static List<String> getAllRegion(){
//		Enumeration<String> e = regions.getKeys();
//		List<String> region = new ArrayList<String>();
//		while(e.hasMoreElements()){
//			region.add(e.nextElement());
//		}
//		return region;
//	}

	public static String getRegionNameByRegionId(String regionId)
	{		 
			if (!StringUtils.isNullOrEmpty(regionId)) {
				CFG_Region region = getRegionByRegionId(regionId);
				if (region.getLevelNum() == 1) {
					return region.getRegionFullName();					 
				} else if (region.getLevelNum() == 2) {
					CFG_Region secondTopRegion = getRegionByRegionId(region.getParentRegionId());
					return String.format("%s-%s", secondTopRegion.getRegionFullName(), region.getRegionFullName());
				} else if (region.getLevelNum() == 3) {
					CFG_Region secondTopRegion = getRegionByRegionId(region.getParentRegionId());
					CFG_Region topRegion = getRegionByRegionId(secondTopRegion.getParentRegionId());
					return String.format("%s-%s-%s", topRegion.getRegionFullName(), secondTopRegion.getRegionFullName(), region.getRegionFullName());
				}
			}
			return null; 
	}
	
	public static List<CFG_Region> getRegionAndSubRegion(String regionId) {
		List<CFG_Region> regionList = new ArrayList<CFG_Region>();
		CFG_Region topRegion = getRegionByRegionId(regionId);
		regionList.add(topRegion);
		if (topRegion.getLevelNum() == 1) {
			List<CFG_Region> secontRegionList = parentRegions.get(topRegion.getRegionId());
			if (secontRegionList != null && !secontRegionList.isEmpty()) {
				regionList.addAll(secontRegionList);
				for (CFG_Region secondLvel : secontRegionList) {
					List<CFG_Region> thirdLevelList = parentRegions.get(secondLvel.getRegionId());
					if (thirdLevelList != null && !thirdLevelList.isEmpty()) {
						regionList.addAll(thirdLevelList);
					}
				}
			}
		} else if (topRegion.getLevelNum() == 2) {
			List<CFG_Region> secontLevelList = parentRegions.get(topRegion.getRegionId());
			if (secontLevelList != null && !secontLevelList.isEmpty()) {
				regionList.addAll(secontLevelList);
			}
		}
		return regionList;
	}

	public static List<CFG_Region> getSubRegion(String regionId) {
		return (parentRegions.containsKey(regionId))? new ArrayList<>(parentRegions.get(regionId)):null;

	}

	public static List<CFG_Region> getProvinces() {
		return new ArrayList<>(provinces);
	}

	public static void main(String[] args) {
		try {
			InitialUtil.init(ResourceFactory.class, RegionHelper.class);
		} catch (InitialException e) {
			e.printStackTrace();
		}
//		List<String> list = getAllRegion();
		List<CFG_Region> listaa = getProvinces();
		if(listaa==null){
			System.out.print("null");
		}else{
			for(CFG_Region r:listaa){
				System.out.println(r.getParentRegionId());
			}
		}
	}

}
