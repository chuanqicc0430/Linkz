package cn.net.cvtt.resource.redis;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.resource.route.resource.Resource;
import cn.net.cvtt.resource.route.resource.ResourceType;
import cn.net.cvtt.resource.route.resource.ResourceUri;

/**
 * {在这里补充类的功能说明}
 * 
 * @author
 */
public class RedisResource implements Resource {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisResource.class);

	private RedisClient proxy;
	private String name;
	private int index;
	private String params;

	public RedisResource(ResourceUri resource) throws Exception {
		Properties props = new Properties();
		try {
			props.load(new StringReader(resource.getConfig()));
		} catch (IOException e) {
			LOGGER.error("Resource {}.{} load failed!", resource.getName(), resource.getIndex());
			LOGGER.error("properties:" + resource.getConfig(), e);
			throw e;
		}
		RedisCluster redisCluster = new RedisCluster();
		try {
			redisCluster.configFromPropety(props);
		} catch (NullPointerException t) {
			LOGGER.error("Resource {}.{} load failed!", resource.getName(), resource.getIndex());
			LOGGER.error("properties:" + resource.getConfig(), t);
			throw t;
		}
		this.name = resource.getName();
		this.index = resource.getIndex();
		this.params = resource.getConfig();

		proxy = new RedisClient();

		proxy.initNode(redisCluster);
	}

	/*
	 * @see com.feinno.appengine.resource.Resource#type()
	 */
	/**
	 * {在这里补充功能说明}
	 * 
	 * @return
	 */
	@Override
	public ResourceType type() {
		// 默认return ResourceType.DATABASE;类型
		return ResourceType.DATABASE;
	}

	/*
	 * @see com.feinno.appengine.resource.Resource#name()
	 */
	/**
	 * {在这里补充功能说明}
	 * 
	 * @return
	 */
	@Override
	public String name() {
		return name;
	}

	/*
	 * @see com.feinno.appengine.resource.Resource#index()
	 */
	/**
	 * {在这里补充功能说明}
	 * 
	 * @return
	 */
	@Override
	public int index() {
		return index;
	}

	@Override
	public String paras() {
		// TODO Auto-generated method stub
		return params;
	}

	public RedisClient getProxy() {
		return proxy;
	}

}
