package cn.net.cvtt.resource.redis;

import cn.net.cvtt.resource.route.context.ApplicationCtx;
import cn.net.cvtt.resource.route.context.ContextUri;
import cn.net.cvtt.resource.route.resource.ResourceProxy;

/**
 * 描述: Redis IO代理类
 * 
 * @author cqzong
 * 
 * @param <T>
 */
public class RedisProxy extends ResourceProxy {

	public RedisProxy() {
		super();
	}

	public RedisClient getRedisClient(ApplicationCtx ctx) {
		ContextUri uri = ctx.getContextUri();
		RedisResource dbResource = (RedisResource) locateResource(uri);
		return dbResource.getProxy();
	}

	public RedisClient getRedisClient(int id) {
		RedisResource dbResource = (RedisResource) locateResource(id);
		return dbResource.getProxy();
	}
	public RedisClient getRedisClient(String key) {
		RedisResource dbResource = (RedisResource) locateResource(key);
		return dbResource.getProxy();
	}
}
