package cn.net.cvtt.resource.database;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.resource.database.pool.DBConnectionPoolType;
import cn.net.cvtt.resource.route.resource.ResourceUri;
import cn.net.cvtt.resource.route.resource.Resource;
import cn.net.cvtt.resource.route.resource.ResourceType;

/**
 * 连接池改为Druid连接池
 * 
 * @author zongchuanqi
 */
public class DatabaseResource implements Resource {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseResource.class);

	private Database db;
	private String name;
	private int index;
	private String params;
	// 默认Druid连接池
	private static DBConnectionPoolType poolType = DBConnectionPoolType.Druid;

	public DatabaseResource(ResourceUri resource) throws Exception {
		Properties props = new Properties();
		try {
			props.load(new StringReader(resource.getConfig()));
		} catch (IOException e) {
			LOGGER.error("Resource {}.{} load failed!", resource.getName(), resource.getIndex());
			LOGGER.error("properties:" + resource.getConfig(), e);
			throw e;
		}

		this.name = resource.getName();
		this.index = resource.getIndex();
		this.params = resource.getConfig();

		String dbName = name + "." + index;

		db = DatabaseManager.getDatabase(dbName, props, poolType);
	}

	/*
	 * @see cn.net.cvtt.resource.route.resource.Resource#type()
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
	 * @see cn.net.cvtt.resource.route.resource.Resource#name()
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
	 * @see cn.net.cvtt.resource.route.resource.Resource#index()
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

	/**
	 * @return the db
	 */
	public Operation getDb() {
		return db;
	}

}
