package cn.net.cvtt.resource.route.resource;

import java.util.HashMap;
import java.util.Map;

import cn.net.cvtt.resource.route.context.ContextUri;
import cn.net.cvtt.resource.route.locator.ResourceLocator;

/**
 * {在这里补充类的功能说明}
 * 
 * @author 
 */
public class ResourceProxy
{
	private Map<String, ResourceLocator> locators;
	
	public ResourceProxy()
	{
		locators = new HashMap<String, ResourceLocator>();
	}
	
	public void addLocator(String uriProtocol, ResourceLocator locator)
	{
		locators.put(uriProtocol, locator);
	}
	
	protected Resource locateResource(ContextUri uri)
	{
		String protocol = uri.getProtocol();
		ResourceLocator locator = locators.get(protocol);
		return locator.locate(uri);
	}
	
	protected Resource locateResource(Integer a)
	{
		ResourceLocator locator = locators.get("id");
		return locator.locate(a);
	}
	
	protected Resource locateResource(String key)
	{
		ResourceLocator locator = locators.get("id");
		return locator.locate(key);
	}
}
