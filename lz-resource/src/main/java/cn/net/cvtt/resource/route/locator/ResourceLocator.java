package cn.net.cvtt.resource.route.locator;

import cn.net.cvtt.resource.route.context.ContextUri;
import cn.net.cvtt.resource.route.resource.Resource;
import cn.net.cvtt.resource.route.resource.ResourceGroup;

/**
 * {在这里补充类的功能说明}
 * 
 * @author 
 */
public interface ResourceLocator
{
	void setParams(ResourceGroup<? extends Resource> group, String params) throws Exception;
	Resource locate(ContextUri uri);
	Resource locate(int id);
	Resource locate(String  key);
	Resource locate();
}
