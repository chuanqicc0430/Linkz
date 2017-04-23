package cn.net.cvtt.resource.route.locator;

import cn.net.cvtt.resource.route.context.ContextUri;
import cn.net.cvtt.resource.route.resource.Resource;
import cn.net.cvtt.resource.route.resource.ResourceGroup;

public class GlobalLocator implements ResourceLocator {

	Resource resource;

	@Override
	public Resource locate(int id) {
		return resource;
	}
	@Override
	public Resource locate(String key) {
		return resource;
	}

	@Override
	public Resource locate() {
		return resource;
	}

	@Override
	public void setParams(ResourceGroup<? extends Resource> group, String params) throws Exception {
		resource = group.getResource(0);
	}

	@Override
	public Resource locate(ContextUri uri) {
		return resource;
	}

}
