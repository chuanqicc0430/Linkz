package cn.net.cvtt.imps.lianzi.config.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.net.cvtt.configuration.ConfigTable;
import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationManager;
import cn.net.cvtt.imps.lianzi.config.tables.CFG_OrgIndustryLevel;
import cn.net.cvtt.imps.lianzi.config.tables.CFG_UpDownIndustry;
import cn.net.cvtt.lian.common.initialization.InitialException;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.resource.route.ResourceFactory;

public class UpDownIndustryHelper {

	private static ConfigTable<String, CFG_UpDownIndustry> upDownIndustry;
	private static HashMap<String, List<String>> upStreamIndustry;
	private static HashMap<String, List<String>> downStreamIndustry;

	@Initializer
	public static void initialize() throws Exception {
		InitialUtil.init(OrgIndustryLevelHelper.class);
		upStreamIndustry = new HashMap<String, List<String>>();
		downStreamIndustry = new HashMap<String, List<String>>();
		ConfigurationManager.subscribeConfigUpdate(ConfigType.TABLE, "CFG_UpDownIndustry", null);
		upDownIndustry = ConfigurationManager.loadTable(String.class, CFG_UpDownIndustry.class, "CFG_UpDownIndustry", new ConfigUpdateAction<ConfigTable<String, CFG_UpDownIndustry>>() {
			public void run(ConfigTable<String, CFG_UpDownIndustry> table) throws Exception {
				upDownIndustry = table;
				produceIndustryData(upDownIndustry);
			}
		});
		produceIndustryData(upDownIndustry);
	}

	public static List<String> getUpstreamIndustry(String industryId) {
		List<String> upstreamIndustry = new ArrayList<>();
		Set<String> industrySet = new HashSet<>();
		CFG_OrgIndustryLevel industryLevel = OrgIndustryLevelHelper.getOrgIdustryByIndustryId(industryId);
		if(industryLevel.getLevelNum() != 4){
			List<CFG_OrgIndustryLevel> usubtl=OrgIndustryLevelHelper.getOrgIndustryAndSubIndustry(industryId);
			for(CFG_OrgIndustryLevel o : usubtl){
				List<String> list = upStreamIndustry.get(o.getIndustryId());
				if(list != null && !list.isEmpty()){
					industrySet.addAll(list);
				}
			}
		}else{
			List<String> list = upStreamIndustry.get(industryId);
			if(list != null && !list.isEmpty()){
				industrySet.addAll(list);
			}
		}
		upstreamIndustry.addAll(industrySet);
		return upstreamIndustry;
	}

	public static List<String> getDownstreamIndustry(String industryId) {
		List<String> downstreamIndustry = new ArrayList<>();
		Set<String> industrySet = new HashSet<>();
		CFG_OrgIndustryLevel industryLevel = OrgIndustryLevelHelper.getOrgIdustryByIndustryId(industryId);
		if(industryLevel.getLevelNum() != 4){
			List<CFG_OrgIndustryLevel> usubtl=OrgIndustryLevelHelper.getOrgIndustryAndSubIndustry(industryId);
			for(CFG_OrgIndustryLevel o : usubtl){
				List<String> list = downStreamIndustry.get(o.getIndustryId());
				if(list != null && !list.isEmpty()){
					industrySet.addAll(list);
				}
			}
		}else{
			List<String> list = downStreamIndustry.get(industryId);
			if(list != null && !list.isEmpty()){
				industrySet.addAll(list);
			}
		}
		downstreamIndustry.addAll(industrySet);
		return downstreamIndustry;
	}

	private static void produceIndustryData(ConfigTable<String, CFG_UpDownIndustry> data) throws Exception {
		try {
			HashMap<String, List<String>> tempDownStreamIndustry = new HashMap<String, List<String>>();
			HashMap<String, List<String>> tempUpStreamIndustry = new HashMap<String, List<String>>();
			Collection<CFG_UpDownIndustry> e = data.getHashtable().values();
			for (CFG_UpDownIndustry industry : e) {
				List<String> ul;
				if (tempDownStreamIndustry.containsKey(industry.getUpStreamID())) {
					ul = tempDownStreamIndustry.get(industry.getUpStreamID());
				} else {
					ul = new ArrayList<String>();
				}
				CFG_OrgIndustryLevel usubtl=OrgIndustryLevelHelper.getOrgIdustryByIndustryId(industry.getDownStreamID());
				ul.add(usubtl.getIndustryId());
				
				tempDownStreamIndustry.put(industry.getUpStreamID(), ul);
				
				List<String> dl;
				if (tempUpStreamIndustry.containsKey(industry.getDownStreamID())) {
					dl = tempUpStreamIndustry.get(industry.getDownStreamID());
				} else {
					dl = new ArrayList<String>();
				}
			    
				//dl.add(industry.getUpStreamID());
				CFG_OrgIndustryLevel dsubtl=OrgIndustryLevelHelper.getOrgIdustryByIndustryId(industry.getUpStreamID());
				dl.add(dsubtl.getIndustryId());
				tempUpStreamIndustry.put(industry.getDownStreamID(), dl);
			}
			downStreamIndustry = tempDownStreamIndustry;
			upStreamIndustry = tempUpStreamIndustry;
		} catch (Exception ex) {
			throw ex;
		}
	}

	public static void main(String[] args) {
		try {
			InitialUtil.init(ResourceFactory.class, UpDownIndustryHelper.class);
		} catch (InitialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		List<String> list = UpDownIndustryHelper.getUpstreamIndustry("I63XX000B");
		List<String> list = UpDownIndustryHelper.getUpstreamIndustry("A01XX000A");
		for (String s : list) {
			System.out.println(s);
		}
	}
}
