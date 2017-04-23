package cn.net.cvtt.resource.route.locator;

import cn.net.cvtt.resource.route.context.ContextUri;
import cn.net.cvtt.resource.route.resource.Resource;
import cn.net.cvtt.resource.route.resource.ResourceGroup;
import cn.net.cvtt.lian.common.util.ObjectHelper;

/**
 * 
 * @author
 */
public class HashLocator implements ResourceLocator {
	private ResourceGroup<? extends Resource> group;

	/**
	 * @param group
	 * @param params
	 */
	@Override
	public void setParams(ResourceGroup<? extends Resource> group, String params) throws Exception {
		this.group = group;
	}

	@Override
	public Resource locate(int id) {
		return group.getResource(id);
	}

	@Override
	public Resource locate() {
		return group.getResource(0);
	}

	private static int getHashIndexByUserid(long id) {
		return (int) (id % 100 / 10);
	}

	@Override
	public Resource locate(ContextUri uri) {
		//int i=this.getHashIndexByUserid(uri.getRouteHash())/group.resources().size();
		 
		return group.getResource(getHashIndexByUserid(uri.getRouteHash())%group.resources().size()+1);   //全局的从0开始，分区的索引从1开始
	}
	
	
	@Override
	public Resource locate(String redisKey) {
		return group.getResource(getHashIndexByUserid(Math.abs(ObjectHelper.compatibleGetHashCode(redisKey)))%group.resources().size()+1);
	}
}
