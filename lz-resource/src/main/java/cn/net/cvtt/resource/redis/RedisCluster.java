package cn.net.cvtt.resource.redis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import cn.net.cvtt.lian.common.router.ReRouteAble;
import cn.net.cvtt.lian.common.util.StringUtils;
import redis.clients.jedis.BinaryJedis;
import redis.clients.jedis.Client;
import redis.clients.jedis.Connection;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.util.SafeEncoder;

public class RedisCluster implements ReRouteAble {

	private int id;

	private String host;

	private int port;

	private int timeout;

	private int database;

	private int weight;

	private JedisPool jedisPool;

	public void configFromPropety(Properties properties) throws NullPointerException {
		String host = properties.getProperty("host");
		if (StringUtils.isNullOrEmpty(host)) {
			throw new NullPointerException("host is null");
		}
		this.setHost(host);

		String port = properties.getProperty("port");
		if (StringUtils.isNullOrEmpty(port)) {
			throw new NullPointerException("port is null");
		}
		this.setPort(Integer.parseInt(port));

		String weight = properties.getProperty("weight");
		if (!StringUtils.isNullOrEmpty(weight)) {
			this.setWeight(Integer.parseInt(weight));
		}

		String timeout = properties.getProperty("timeout");
		if (!StringUtils.isNullOrEmpty(timeout)) {
			this.setTimeout(Integer.parseInt(timeout));
		} else {
			this.setTimeout(Protocol.DEFAULT_TIMEOUT);
		}

		String database = properties.getProperty("database");
		if (!StringUtils.isNullOrEmpty(database)) {
			this.setDatabase(Integer.parseInt(database));
		} else {
			this.setDatabase(Protocol.DEFAULT_DATABASE);
		}

		// this.setJedisPool(new JedisPool(host, Integer.parseInt(port)));
		this.setJedisPool(new JedisPool(new GenericObjectPoolConfig(), this.host, this.port, this.timeout, null, this.database, null));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	@Override
	public String toString() {
		return String.format("id=%s,host=%s,port=%s,weight=%s", id, host, port, weight);
	}

	/**
	 * 用于执行直接的Redis命令，仅用于运维平台，生产环境开发请勿调用
	 */
	public Object runRawCommand(String rawCommand) throws Exception {
		Jedis jedis = null;
		try {
			if (StringUtils.isNullOrEmpty(rawCommand)) {
				return "bad command";
			}
			rawCommand = rawCommand.trim();
			String[] splitResult = rawCommand.split(" ");
			if (splitResult == null || splitResult.length < 2) {
				return "bad command";
			}
			String commandStr = splitResult[0];
			List<byte[]> parasList = new ArrayList<byte[]>();
			if (splitResult.length > 1) {
				for (int i = 1; i < splitResult.length; i++) {
					parasList.add(SafeEncoder.encode(splitResult[i]));
				}
			}

			Command command = null;
			try {
				command = Command.valueOf(commandStr.toUpperCase(Locale.ENGLISH));
			} catch (Exception e) {
				return "No Such Command";
			}

			byte[][] paras = parasList.toArray(new byte[0][0]);

			// 反射获取当前jedis实例的client域
			jedis = this.jedisPool.getResource();
			Field clientField = BinaryJedis.class.getDeclaredField("client");
			clientField.setAccessible(true);
			Client client = (Client) clientField.get(jedis);

			// 反射获取命令对应的方法
			Method sendCommandMethod = Connection.class.getDeclaredMethod("sendCommand", Command.class, byte[][].class);
			sendCommandMethod.setAccessible(true);
			sendCommandMethod.invoke(client, command, paras);
			return client.getOne();
		} catch (JedisDataException e) {
			return e.getMessage();
		} catch (Exception e) {
			throw new Exception("runRawCommand Error", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public Set<String> getAllDataKey(String[] keyPatterns) throws Exception {
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			if (keyPatterns != null && keyPatterns.length > 0) {
				Set<String> result = new HashSet<String>();
				for (String pattern : keyPatterns) {
					result.addAll(jedis.keys(pattern));
				}
				return result;
			} else {
				return jedis.keys("*");
			}
		} catch (Exception e) {
			throw new Exception("getAllDataKey Error", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public void deleteData(String dataKey) throws Exception {
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			if (dataKey != null) {
				jedis.del(dataKey);
			}
		} catch (Exception e) {
			throw new Exception("deleteDataKey Error", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public void moveData(String dataKey, ReRouteAble destNode) throws Exception {
		Jedis srcJedis = null;
		Jedis destJedis = null;
		RedisCluster destRedis = null;
		try {
			if (destNode instanceof RedisCluster) {
				System.out.println(String.format("begin migrate data, dataKey=%s, srcRedis=%s, destRedis=%s", dataKey, this.toString(), destNode.toString()));

				boolean renamed = false;
				destRedis = (RedisCluster) destNode;
				destJedis = destRedis.getJedisPool().getResource();

				// 如果目标节点已含有该数据key，则先删除目标节点的数据，覆盖性迁移
				if (destJedis.exists(dataKey)) {
					destJedis.rename(dataKey, dataKey + "_bak");
					renamed = true;
				}

				srcJedis = this.jedisPool.getResource();
				String result = srcJedis.migrate(destRedis.getHost(), destRedis.getPort(), dataKey, 0, 3000);

				if (!"OK".equals(result)) {
					if (renamed) {
						destJedis.rename(dataKey + "_bak", dataKey);
					}
					throw new Exception(String.format("begin migrate data, dataKey=%s, srcRedis=%s, destRedis=%s, message=%s", dataKey, this.toString(), destNode.toString(), result));
				} else if (renamed) {
					destJedis.del(dataKey + "_bak");
				}
			} else {
				throw new Exception(String.format("destNode is not a RedisCluster, infact = %s", destNode));
			}
		} catch (Exception e) {
			throw new Exception(String.format("move data failed, dataKey=%s, srcRedis=%s, destRedis=%s", dataKey, this.toString(), destNode.toString()), e);
		} finally {
			if (srcJedis != null) {
				srcJedis.close();
			}
			if (destJedis != null) {
				destJedis.close();
			}
		}
	}

}
