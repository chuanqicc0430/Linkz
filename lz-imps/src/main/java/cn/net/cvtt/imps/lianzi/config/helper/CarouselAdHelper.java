package cn.net.cvtt.imps.lianzi.config.helper;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationManager;
import cn.net.cvtt.lian.common.initialization.InitialException;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.resource.route.ResourceFactory;

/**  
 * @Title: HomePageADHelper.java
 * @Package cn.net.cvtt.imps.lianzi.config.helper
 * @Description: 要找首页轮播图广告帮助类
 * @author hekaibo@cvtt.com  
 * @date 2016年12月16日 上午9:31:18
 */
public class CarouselAdHelper {
	private static final Logger LOGGER = Logger.getLogger(CarouselAdHelper.class);
	/**
	 * 从配置db中获取友盟的appkey和appMasterSecret
	 * @throws Exception
	 */
	private static String url = null;
	private static Set<Long> orgSet = new HashSet<Long>();
	@Initializer
	public static void initialize() throws Exception {
		try {
			ConfigurationManager.subscribeConfigUpdate(ConfigType.TEXT, "CarouselAdConf", null);
			ConfigurationManager.loadProperties("CarouselAdConf",null, new ConfigUpdateAction<Properties>() {
				public void run(Properties p) throws Exception {
					url = p.getProperty("url", "");
					String orgIdsStr = p.getProperty("orgIds", "");
					if (orgIdsStr != null) {
						String[] orgIds = orgIdsStr.split(",");
						if (orgIds != null && orgIds.length > 0) {
							for (String orgIdStr : orgIds) {
								orgSet.add(NumberUtils.toLong(orgIdStr));
							}
						}
					}
				}
			});
		} catch (Exception e) {
			LOGGER.error("getResource by ResourceDB err", e);
			throw new Exception("getResource by ResourceDB err", e);
		}
	}
	
	public static String getUrl() {
		return url;
	}
	
	public static Set<Long> getOrgIdSet() {
		return orgSet;
	}
	public static void main(String[] args) {
		try {
			InitialUtil.init(ResourceFactory.class,CarouselAdHelper.class);
		} catch (InitialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(getOrgIdSet());
	}
}
  