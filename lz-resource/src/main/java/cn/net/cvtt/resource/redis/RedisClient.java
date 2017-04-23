package cn.net.cvtt.resource.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.lian.common.router.ConsistentHashRouter;
import cn.net.cvtt.lian.common.router.Router;
import cn.net.cvtt.lian.common.router.hash.GeneralHashHelper;
import cn.net.cvtt.lian.common.serialization.Serializer;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.util.Action;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Transaction;
import redis.clients.util.SafeEncoder;

/**
 * 描述: Redis IO代理类
 * 
 * <pre>
 * //使用示例
 * RedisProxy redisProxy = RedisProxyFactory.getInstance().getRedisProxy(RedisKey.REDIS_GROUP_NAME);
 * ......
 * //no-pipline
 * String key = String.format(RedisKey.KEY, userId);
 * String currentCredential = redisProxy.hgetStr(key, RedisKey.FIELD);
 * 
 * //pipline
 * Jedis jedis = redisProxy.getJedis();
 * Pipeline pipline = jedis.pipelined();
 * try {
 * 	pipline.hset(key,RedisKey.FIELD,value);
 * 	pipline.expire(key,60);
 * 	pipline.sync();
 * } fianlly{
 * 	jedis.close();
 * }
 * </pre>
 * 
 * @author cqzong
 * 
 * @param <T>
 */
public class RedisClient {

	private String groupName = "";
	private Router<RedisCluster> jedisGroup = null;
	private static int maxRetryTimes = 3;
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisClient.class);

	public RedisClient() {
		// 一致性哈希，每个实例虚拟节点数从数据库中读取
		jedisGroup = new ConsistentHashRouter<RedisCluster>(new GeneralHashHelper());
	}

	public String getGroupName() {
		return groupName;
	}

	/**
	 * 极度危险！应用层切勿调用！
	 * 
	 * @throws Exception
	 * 
	 */
	public void initNode(RedisCluster jedisPool) throws Exception {
		jedisGroup.initNode(jedisPool, jedisPool.getWeight());
	}

	public void runCommand(String key, Action<Jedis> command) throws Exception {
		runCommand(key, command, 1);
	}

	private void runCommand(String key, Action<Jedis> command, int tryTimes) throws Exception {
		JedisPool jedisPool = null;
		Jedis jedis = null;

		try {
			jedisPool = this.jedisGroup.get(key).getJedisPool();
			jedis = jedisPool.getResource();
			command.run(jedis);
		} catch (Exception e) {

			LOGGER.error(String.format("RedisProxy_%s runCommand throws UnionException:%s,tryTimes:%s", this.groupName, e.getMessage(), tryTimes), e);
			if (tryTimes < maxRetryTimes) {
				runCommand(key, command, tryTimes + 1);
			} else {
				throw new Exception(String.format("RedisProxy_%s runCommand error:%s", this.groupName, e.getMessage()), e);
			}
		} finally {
			if (jedisPool != null && jedis != null) {
				jedis.close();
			}
		}
	}

	private <T> T getInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			LOGGER.error(String.format("call %s.newInstance() failed:%s", clazz.getName(), e.getMessage()), e);
			return null;
		} catch (IllegalAccessException e) {
			LOGGER.error(String.format("call %s.newInstance() failed:%s", clazz.getName(), e.getMessage()), e);
			return null;
		}
	}

	public Long append(final String key, final String afterString) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.append(SafeEncoder.encode(key), SafeEncoder.encode(afterString)));
			}
		});

		return result.getValue();
	}

	public Long decr(final String key) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.decr(SafeEncoder.encode(key)));
			}
		});

		return result.getValue();
	}

	public Long decrBy(final String key, final long offset) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.decrBy(SafeEncoder.encode(key), offset));
			}
		});

		return result.getValue();
	}

	/**
	 * 不再兼容原Redis中可以批量del的命令，因为不同的key会路由到不同的实例
	 * 
	 * @param key
	 * @return 删除成功返回1，否则返回0
	 */
	public Long del(final String key) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.del(SafeEncoder.encode(key)));
			}
		});

		return result.getValue();
	}

	public Boolean exists(final String key) throws Exception {
		final ActionResult<Boolean> result = new ActionResult<Boolean>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.exists(SafeEncoder.encode(key)));
			}
		});

		return result.getValue();
	}

	public String rename(final String oldKey, final String newKey) throws Exception {
		final ActionResult<String> result = new ActionResult<String>();
		runCommand(oldKey, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.rename(oldKey, newKey));
			}
		});

		return result.getValue();
	}

	/**
	 * @param key
	 * @param seconds
	 * @return 1 成功设置了过期时间
	 *         <p>
	 *         0设置过期时间失败
	 */
	public Long expire(final String key, final int seconds) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.expire(SafeEncoder.encode(key), seconds));
			}
		});

		return result.getValue();
	}

	/**
	 * @param key
	 * @param seconds
	 * @return 1 成功设置了过期时间
	 *         <p>
	 *         0设置过期时间失败
	 */
	public Long expireAt(final String key, final int unixTimeStamp) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.expireAt(SafeEncoder.encode(key), unixTimeStamp));
			}
		});

		return result.getValue();
	}

	public <T extends ProtoEntity> T get(final String key, final Class<T> protoClass) throws Exception {
		final ActionResult<T> result = new ActionResult<T>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {

				byte[] buffer = jedis.get(SafeEncoder.encode(key));
				if (buffer != null) {
					try {
						result.setValue(Serializer.decode(protoClass, buffer));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		return result.getValue();
	}

	/*
	 * public <T extends ProtoEntity> T get(final String key, final Class<T> protoClass) throws UnionException { final ActionResult<T> result = new ActionResult<T>();
	 * 
	 * runCommand(key, new Action<Jedis>() {
	 * 
	 * @Override public void run(Jedis jedis) {
	 * 
	 * byte[] buffer = jedis.get(SafeEncoder.encode(key)); if (buffer != null) { T value = getInstance(protoClass); value.parseFrom(buffer); result.setValue(value); } } });
	 * 
	 * return result.getValue(); }
	 */

	/**
	 * 
	 * @Title: get
	 * 
	 * @Description: 获取非ProtoVaule的key，如果value不为String类型，请自己转换下
	 * 
	 * @param @param key @param @return
	 * 
	 * @return String
	 * 
	 * @throws
	 * 
	 */
	public String getStr(final String key) throws Exception {
		final ActionResult<String> result = new ActionResult<String>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.get(key));
			}
		});

		return result.getValue();
	}

	/**
	 * 
	 * @Title: get
	 * 
	 * @Description: 获取非int的key
	 * 
	 * @param @param key @param @return
	 * 
	 * @return Integer
	 * 
	 * @throws
	 * 
	 */
	public Integer getInteger(final String key) throws Exception {
		Integer result = null;
		String resultStr = getStr(key);
		if (resultStr != null) {
			result = Integer.parseInt(resultStr);
		}
		return result;
	}

	public byte[] get(final String key) throws Exception {
		final ActionResult<byte[]> result = new ActionResult<byte[]>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.get(SafeEncoder.encode(key)));
			}
		});

		return result.getValue();
	}

	public <T extends ProtoEntity> T getSet(final String key, final Class<T> protoClass, final ProtoEntity newValue) throws Exception {
		final ActionResult<T> result = new ActionResult<T>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				byte[] buffer = jedis.getSet(SafeEncoder.encode(key), newValue.toByteArray());
				if (buffer != null) {
					T value = getInstance(protoClass);
					value.parseFrom(buffer);
					result.setValue(value);
				}

			}
		});

		return result.getValue();
	}

	/**
	 * @param key
	 * @param field
	 * @return 删除成功返回1，key/filed不存在则返回0
	 */
	public Long hdel(final String key, final String field) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.hdel(SafeEncoder.encode(key), SafeEncoder.encode(field)));
			}
		});

		return result.getValue();
	}

	public Long hdel(final String key, final Integer field) throws Exception {
		return hdel(key, String.valueOf(field));
	}

	public Long hdel(final String key, final Long field) throws Exception {
		return hdel(key, String.valueOf(field));
	}

	public Boolean hexists(final String key, final String field) throws Exception {
		final ActionResult<Boolean> result = new ActionResult<Boolean>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.hexists(SafeEncoder.encode(key), SafeEncoder.encode(field)));
			}
		});

		return result.getValue();
	}

	public <T extends ProtoEntity> T hget(final String key, final String field, final Class<T> protoClass) throws Exception {
		final ActionResult<T> result = new ActionResult<T>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				byte[] buffer = jedis.hget(SafeEncoder.encode(key), SafeEncoder.encode(field));
				if (buffer != null) {
					T value = getInstance(protoClass);
					value.parseFrom(buffer);
					result.setValue(value);
				}
			}
		});

		return result.getValue();
	}

	public byte[] hget(final String key, final String field) throws Exception {
		final ActionResult<byte[]> result = new ActionResult<byte[]>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.hget(SafeEncoder.encode(key), SafeEncoder.encode(field)));
			}
		});

		return result.getValue();
	}

	public int hgetInteger(final String key, final String field) throws Exception {
		String resultStr = hgetStr(key, field);
		if (resultStr != null) {
			return Integer.parseInt(resultStr);
		} else {
			return 0;
		}
	}

	public long hgetLong(final String key, final String field) throws Exception {
		String resultStr = hgetStr(key, field);
		if (resultStr != null) {
			return Long.parseLong(resultStr);
		} else {
			return (long) 0;
		}
	}

	public String hgetStr(final String key, final String field) throws Exception {
		final ActionResult<String> result = new ActionResult<String>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.hget(key, field));
			}
		});

		return result.getValue();
	}

	public <T extends ProtoEntity> Map<String, T> hgetAll(final String key, final Class<T> protoClass) throws Exception {
		final ActionResult<Map<String, T>> result = new ActionResult<Map<String, T>>();

		runCommand(key, new Action<Jedis>() {
			@Override
			public void run(Jedis jedis) {
				Map<byte[], byte[]> bufferMap = jedis.hgetAll(SafeEncoder.encode(key));
				if (bufferMap != null && bufferMap.size() > 0) {
					Map<String, T> valueMap = new HashMap<String, T>();
					for (Entry<byte[], byte[]> bufferEntry : bufferMap.entrySet()) {
						T value = getInstance(protoClass);
						value.parseFrom(bufferEntry.getValue());
						valueMap.put(SafeEncoder.encode(bufferEntry.getKey()), value);
					}
					result.setValue(valueMap);
				}
			}
		});

		return result.getValue();
	}

	public Map<String, Long> hgetAllLong(final String key) throws Exception {
		final ActionResult<Map<String, Long>> result = new ActionResult<Map<String, Long>>();

		runCommand(key, new Action<Jedis>() {
			@Override
			public void run(Jedis jedis) {
				Map<String, String> strMap = jedis.hgetAll(key);
				if (strMap != null && strMap.size() > 0) {
					Map<String, Long> valueMap = new HashMap<String, Long>();
					for (Entry<String, String> strEntry : strMap.entrySet()) {
						valueMap.put(strEntry.getKey(), Long.parseLong(strEntry.getValue()));
					}
					result.setValue(valueMap);
				}
			}
		});

		return result.getValue();
	}

	public Map<String, String> hgetAll(final String key) throws Exception {
		final ActionResult<Map<String, String>> result = new ActionResult<Map<String, String>>();

		runCommand(key, new Action<Jedis>() {
			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.hgetAll(key));
			}
		});

		return result.getValue();
	}

	public Map<byte[], byte[]> hgetAllBytes(final String key) throws Exception {
		final ActionResult<Map<byte[], byte[]>> result = new ActionResult<Map<byte[], byte[]>>();
		final byte[] keyBytes = key.getBytes("UTF-8");
		runCommand(key, new Action<Jedis>() {
			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.hgetAll(keyBytes));
			}
		});

		return result.getValue();
	}

	public Long hincrBy(final String key, final String field, final long offset) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.hincrBy(SafeEncoder.encode(key), SafeEncoder.encode(field), offset));
			}
		});

		return result.getValue();
	}

	public Double hincrByFloat(final String key, final String field, final double offset) throws Exception {
		final ActionResult<Double> result = new ActionResult<Double>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.hincrByFloat(SafeEncoder.encode(key), SafeEncoder.encode(field), offset));
			}
		});

		return result.getValue();
	}

	public Set<String> hkeys(final String key) throws Exception {
		final ActionResult<Set<String>> result = new ActionResult<Set<String>>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				Set<byte[]> bytesSet = jedis.hkeys(SafeEncoder.encode(key));
				if (bytesSet != null && bytesSet.size() > 0) {
					Set<String> value = new HashSet<String>();
					for (byte[] bytes : bytesSet) {
						value.add(SafeEncoder.encode(bytes));
					}
					result.setValue(value);
				}
			}
		});

		return result.getValue();
	}

	public Long hlen(final String key) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.hlen(SafeEncoder.encode(key)));
			}
		});

		return result.getValue();
	}

	/**
	 * WARN:由于要对field和value进行序列化，因此要对Map进行遍历，所以在Map较大时性能会比较低
	 * 
	 * @param key
	 * @param hashValue
	 */
	public String hmsetProto(final String key, final Map<String, ProtoEntity> hashValue) throws Exception {
		final ActionResult<String> result = new ActionResult<String>();
		final Map<byte[], byte[]> jedisHashValue = new HashMap<byte[], byte[]>();
		for (Entry<String, ProtoEntity> entry : hashValue.entrySet()) {
			byte[] jedisKey = SafeEncoder.encode(entry.getKey());
			byte[] jedisValue = entry.getValue().toByteArray();

			jedisHashValue.put(jedisKey, jedisValue);
		}
		runCommand(key, new Action<Jedis>() {
			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.hmset(SafeEncoder.encode(key), jedisHashValue));
			}
		});

		return result.getValue();
	}

	/**
	 * 
	 * @param key
	 * @param hashValue
	 */
	public String hmset(final String key, final Map<String, String> hashValue) throws Exception {

		final ActionResult<String> result = new ActionResult<String>();

		runCommand(key, new Action<Jedis>() {
			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.hmset(key, hashValue));
			}
		});

		return result.getValue();
	}

	/**
	 * WARN:由于要对field和value进行反序列化，因此要对Map进行遍历，所以在Map较大时性能会比较低
	 * 
	 * @param key
	 * @param hashValue
	 */
	public <T extends ProtoEntity> List<T> hmget(final String key, List<String> fields, final Class<T> protoClass) throws Exception {
		final ActionResult<List<T>> result = new ActionResult<List<T>>();
		final List<byte[]> filedsBytes = new ArrayList<byte[]>();

		for (String field : fields) {
			filedsBytes.add(SafeEncoder.encode(field));
		}

		runCommand(key, new Action<Jedis>() {
			@Override
			public void run(Jedis jedis) {
				List<byte[]> bufferList = jedis.hmget(SafeEncoder.encode(key), filedsBytes.toArray(new byte[][] {}));

				if (bufferList != null && !bufferList.isEmpty()) {
					final List<T> value = new ArrayList<T>();
					for (byte[] buffer : bufferList) {
						T element = getInstance(protoClass);
						element.parseFrom(buffer);
						value.add(element);
					}
					result.setValue(value);
				}
			}
		});
		return result.getValue();
	}

	public Long hset(final String key, final String field, final ProtoEntity value) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.hset(SafeEncoder.encode(key), SafeEncoder.encode(field), value.toByteArray()));
			}
		});
		return result.getValue();
	}

	public Long hset(final String key, final String field, final String strValue) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.hset(SafeEncoder.encode(key), SafeEncoder.encode(field), SafeEncoder.encode(strValue)));
			}
		});
		return result.getValue();
	}

	public Long hset(final String key, final String field, final int intValue) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.hset(SafeEncoder.encode(key), SafeEncoder.encode(field), SafeEncoder.encode(String.valueOf(intValue))));
			}
		});
		return result.getValue();
	}

	public Long hset(final String key, final int field, final int intValue) throws Exception {
		return hset(key, String.valueOf(field), intValue);
	}

	public Long hset(final String key, final int field, final long longValue) throws Exception {
		return hset(key, String.valueOf(field), String.valueOf(longValue));
	}

	public Long hset(final String key, final String field, final long longValue) throws Exception {
		return hset(key, String.valueOf(field), String.valueOf(longValue));
	}

	public Long hset(final String key, final String field, final float floatValue) throws Exception {
		return hset(key, String.valueOf(field), String.valueOf(floatValue));
	}

	public Long hsetnx(final String key, final String field, final ProtoEntity value) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.hsetnx(SafeEncoder.encode(key), SafeEncoder.encode(field), value.toByteArray()));
			}
		});

		return result.getValue();
	}

	public <T extends ProtoEntity> List<T> hvals(final String key, final Class<T> protoClass) throws Exception {
		final ActionResult<List<T>> result = new ActionResult<List<T>>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				List<byte[]> bufferList = jedis.hvals(SafeEncoder.encode(key));

				if (bufferList != null) {
					final List<T> value = new ArrayList<T>();
					for (byte[] buffer : bufferList) {
						T element = getInstance(protoClass);
						element.parseFrom(buffer);
						value.add(element);
					}
					result.setValue(value);
				}
			}
		});

		return result.getValue();
	}

	public List<Integer> hvalsInteger(final String key) throws Exception {
		final ActionResult<List<Integer>> result = new ActionResult<List<Integer>>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				List<byte[]> bufferList = jedis.hvals(SafeEncoder.encode(key));

				if (bufferList != null) {
					final List<Integer> value = new ArrayList<Integer>();
					for (byte[] buffer : bufferList) {
						Integer element = Integer.valueOf(SafeEncoder.encode(buffer));
						value.add(element);
					}
					result.setValue(value);
				}
			}
		});

		return result.getValue();
	}

	public List<String> hvalsString(final String key) throws Exception {
		final ActionResult<List<String>> result = new ActionResult<List<String>>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				List<byte[]> bufferList = jedis.hvals(SafeEncoder.encode(key));

				if (bufferList != null) {
					final List<String> value = new ArrayList<String>();
					for (byte[] buffer : bufferList) {
						String element = SafeEncoder.encode(buffer);
						value.add(element);
					}
					result.setValue(value);
				}
			}
		});

		return result.getValue();
	}

	public Long incr(final String key) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.incr(SafeEncoder.encode(key)));
			}
		});

		return result.getValue();
	}

	public Long incrBy(final String key, final long offset) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.incrBy(SafeEncoder.encode(key), offset));
			}
		});

		return result.getValue();
	}

	/**
	 * 返回列表里的元素的索引 index 存储在 key 里面。 下标是从0开始索引的，所以 0 是表示第一个元素， 1 表示第二个元素，并以此类推。 负数索引用于指定从列表尾部开始索引的元素。在这种方法下，-1 表示最后一个元素，-2 表示倒数第二个元素，并以此往前推。
	 * <p>
	 * 当 key 位置的值不是一个列表的时候，会返回一个error。
	 * 
	 * @param key
	 * @param index
	 * @param protoClass
	 * @return 请求的对应元素，或者当 index 超过范围的时候返回 null。
	 */
	public <T extends ProtoEntity> T lindex(final String key, final int index, final Class<T> protoClass) throws Exception {
		final ActionResult<T> result = new ActionResult<T>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {

				byte[] buffer = jedis.lindex(SafeEncoder.encode(key), index);
				if (buffer != null) {
					T value = getInstance(protoClass);
					value.parseFrom(buffer);
					result.setValue(value);
				}
			}
		});

		return result.getValue();
	}

	/**
	 * 返回列表里的元素的索引 index 存储在 key 里面。 下标是从0开始索引的，所以 0 是表示第一个元素， 1 表示第二个元素，并以此类推。 负数索引用于指定从列表尾部开始索引的元素。在这种方法下，-1 表示最后一个元素，-2 表示倒数第二个元素，并以此往前推。
	 * <p>
	 * 当 key 位置的值不是一个列表的时候，会返回一个error。
	 * 
	 * @param key
	 * @param index
	 * @param protoClass
	 * @return 请求的对应元素，或者当 index 超过范围的时候返回 null。
	 */
	public Integer lindexInteger(final String key, final int index) throws Exception {
		Integer result = null;
		String resultStr = lindexString(key, index);
		if (resultStr != null) {
			result = Integer.parseInt(resultStr);
		}
		return result;
	}

	/**
	 * 返回列表里的元素的索引 index 存储在 key 里面。 下标是从0开始索引的，所以 0 是表示第一个元素， 1 表示第二个元素，并以此类推。 负数索引用于指定从列表尾部开始索引的元素。在这种方法下，-1 表示最后一个元素，-2 表示倒数第二个元素，并以此往前推。
	 * <p>
	 * 当 key 位置的值不是一个列表的时候，会返回一个error。
	 * 
	 * @param key
	 * @param index
	 * @param protoClass
	 * @return 请求的对应元素，或者当 index 超过范围的时候返回 null。
	 */
	public String lindexString(final String key, final int index) throws Exception {
		final ActionResult<String> result = new ActionResult<String>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {

				byte[] buffer = jedis.lindex(SafeEncoder.encode(key), index);
				if (buffer != null) {
					SafeEncoder.encode(buffer);
				}
			}
		});

		return result.getValue();
	}

	public Long linsert(final String key, final LIST_POSITION position, final ProtoEntity pivot, final ProtoEntity value) throws Exception {
		return linsert(key, position, pivot, value.toByteArray());
	}

	public Long linsert(final String key, final LIST_POSITION position, final ProtoEntity pivot, final int value) throws Exception {
		return linsert(key, position, pivot, SafeEncoder.encode(String.valueOf(value)));
	}

	public Long linsert(final String key, final LIST_POSITION position, final ProtoEntity pivot, final String value) throws Exception {
		return linsert(key, position, pivot, SafeEncoder.encode(value));
	}

	private Long linsert(final String key, final LIST_POSITION position, final ProtoEntity pivot, final byte[] value) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.linsert(SafeEncoder.encode(key), position, pivot.toByteArray(), value));
			}
		});

		return result.getValue();
	}

	public Long llen(final String key) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.llen(SafeEncoder.encode(key)));
			}
		});

		return result.getValue();
	}

	public <T extends ProtoEntity> T lpop(final String key, final Class<T> protoClass) throws Exception {
		final ActionResult<T> result = new ActionResult<T>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				byte[] buffer = jedis.lpop(SafeEncoder.encode(key));
				if (buffer != null) {
					T value = getInstance(protoClass);
					value.parseFrom(buffer);
					result.setValue(value);
				}
			}
		});

		return result.getValue();
	}

	private byte[] lpop(final String key) throws Exception {
		final ActionResult<byte[]> result = new ActionResult<byte[]>();
		runCommand(key, new Action<Jedis>() {
			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.lpop(SafeEncoder.encode(key)));
			}
		});

		return result.getValue();
	}

	private void rpush(final String key, final byte[] value) throws Exception {
		runCommand(key, new Action<Jedis>() {
			@Override
			public void run(Jedis jedis) {
				jedis.rpush(SafeEncoder.encode(key), value);
			}
		});

	}

	/**
	 * 将sourcekey队列中的第一元素迁移到destKey的最后一个元素
	 * 
	 * @param sourceKey
	 * @param destKey
	 * @throws Exception
	 */
	public void lpoppush(final String sourceKey, final String destKey) throws Exception {
		rpush(destKey, lpop(sourceKey));
	}

	public Integer lpopInteger(final String key) throws Exception {
		Integer result = null;
		String resultStr = lpopString(key);
		if (resultStr != null) {
			result = Integer.parseInt(resultStr);
		}
		return result;
	}

	public String lpopString(final String key) throws Exception {
		final ActionResult<String> result = new ActionResult<String>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(SafeEncoder.encode(jedis.lpop(SafeEncoder.encode(key))));
			}
		});

		return result.getValue();
	}

	public Long lrem(final String key, final int count, final long orgId) throws Exception {
		return lrem(key, count, SafeEncoder.encode(Long.toString(orgId)));
	}

	public Long lrem(final String key, final int count, final String value) throws Exception {
		return lrem(key, count, SafeEncoder.encode(value));
	}

	public Long lrem(final String key, final int count, final ProtoEntity value) throws Exception {
		return lrem(key, count, value.toByteArray());
	}

	private Long lrem(final String key, final int count, final byte[] value) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.lrem(SafeEncoder.encode(key), count, value));
			}
		});

		return result.getValue();
	}

	public Long lpush(final String key, final ProtoEntity... values) throws Exception {
		List<byte[]> arrays = new ArrayList<byte[]>();
		for (ProtoEntity value : values) {
			arrays.add(value.toByteArray());
		}
		return lpush(key, arrays.toArray(new byte[][] {}));
	}

	public Long lpush(final String key, final String... values) throws Exception {
		List<byte[]> arrays = new ArrayList<byte[]>();
		for (String value : values) {
			arrays.add(SafeEncoder.encode(value));
		}
		return lpush(key, arrays.toArray(new byte[][] {}));
	}

	public Long lpush(final String key, final int... values) throws Exception {
		List<byte[]> arrays = new ArrayList<byte[]>();
		for (int value : values) {
			arrays.add(SafeEncoder.encode(String.valueOf(value)));
		}
		return lpush(key, arrays.toArray(new byte[][] {}));
	}
	
	public Long lpush(final String key, final long... values) throws Exception {
		List<byte[]> arrays = new ArrayList<byte[]>();
		for (long value : values) {
			arrays.add(SafeEncoder.encode(String.valueOf(value)));
		}
		return lpush(key, arrays.toArray(new byte[][] {}));
	}

	private Long lpush(final String key, final byte[]... values) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.lpush(SafeEncoder.encode(key), values));
			}
		});

		return result.getValue();
	}

	public Long lpushx(final String key, final ProtoEntity value) throws Exception {
		return lpushx(key, value.toByteArray());
	}

	public Long lpushx(final String key, final String value) throws Exception {
		return lpushx(key, SafeEncoder.encode(value));
	}

	public Long lpushx(final String key, final int value) throws Exception {
		return lpushx(key, SafeEncoder.encode(String.valueOf(value)));
	}

	Long lpushx(final String key, final byte[] value) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.lpushx(SafeEncoder.encode(key), value));
			}
		});

		return result.getValue();
	}

	public <T extends ProtoEntity> List<T> lrange(final String key, final int start, final int end, final Class<T> protoClass) throws Exception {
		final ActionResult<List<T>> result = new ActionResult<List<T>>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				List<byte[]> bufferList = jedis.lrange(SafeEncoder.encode(key), start, end);

				if (bufferList != null && !bufferList.isEmpty()) {
					List<T> valueList = new ArrayList<T>();
					for (byte[] buffer : bufferList) {
						T element = getInstance(protoClass);
						element.parseFrom(buffer);
						valueList.add(element);
					}
					result.setValue(valueList);
				}
			}
		});

		return result.getValue();
	}

	public List<Integer> lrangeInteger(final String key, final int start, final int end) throws Exception {
		final ActionResult<List<Integer>> result = new ActionResult<List<Integer>>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				List<byte[]> bufferList = jedis.lrange(SafeEncoder.encode(key), start, end);

				if (bufferList != null && !bufferList.isEmpty()) {
					List<Integer> valueList = new ArrayList<Integer>();
					for (byte[] buffer : bufferList) {
						int element = Integer.parseInt(SafeEncoder.encode(buffer));
						valueList.add(element);
					}
					result.setValue(valueList);
				}
			}
		});

		return result.getValue();
	}
	
	public List<Long> lrangeLong(final String key, final int start, final int end) throws Exception {
		final ActionResult<List<Long>> result = new ActionResult<List<Long>>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				List<byte[]> bufferList = jedis.lrange(SafeEncoder.encode(key), start, end);

				if (bufferList != null && !bufferList.isEmpty()) {
					List<Long> valueList = new ArrayList<Long>();
					for (byte[] buffer : bufferList) {
						Long element = Long.parseLong(SafeEncoder.encode(buffer));
						valueList.add(element);
					}
					result.setValue(valueList);
				}
			}
		});

		return result.getValue();
	}

	public List<String> lrangeString(final String key, final int start, final int end) throws Exception {
		final ActionResult<List<String>> result = new ActionResult<List<String>>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				List<byte[]> bufferList = jedis.lrange(SafeEncoder.encode(key), start, end);

				if (bufferList != null && !bufferList.isEmpty()) {
					List<String> valueList = new ArrayList<String>();
					for (byte[] buffer : bufferList) {
						String element = SafeEncoder.encode(buffer);
						valueList.add(element);
					}
					result.setValue(valueList);
				}
			}
		});

		return result.getValue();
	}

	public String lset(final String key, final int index, final ProtoEntity value) throws Exception {
		return lset(key, index, value.toByteArray());
	}

	public String lset(final String key, final int index, final String value) throws Exception {
		return lset(key, index, SafeEncoder.encode(value));
	}

	public String lset(final String key, final int index, final int value) throws Exception {
		return lset(key, index, SafeEncoder.encode(String.valueOf(value)));
	}

	String lset(final String key, final int index, final byte[] value) throws Exception {
		final ActionResult<String> result = new ActionResult<String>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.lset(SafeEncoder.encode(key), index, value));
			}
		});

		return result.getValue();
	}

	public String ltrim(final String key, final int start, final int end) throws Exception {
		final ActionResult<String> result = new ActionResult<String>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.ltrim(SafeEncoder.encode(key), start, end));
			}
		});

		return result.getValue();
	}

	public Response<String> transactionLSettest(final String key, final long index, final byte[] value) throws Exception {
		final ActionResult<Response<String>> result = new ActionResult<Response<String>>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				Transaction transaction = jedis.multi();
				result.setValue(transaction.lset(SafeEncoder.encode(key), index, value));
				transaction.exec();
			}
		});

		return result.getValue();

	}

	public void watch(final String key) throws Exception {
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				jedis.watch(key);
			}
		});
	}

	public void unwatch(final String key) throws Exception {
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				jedis.unwatch();
			}
		});
	}

	public Long persist(final String key) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.persist(SafeEncoder.encode(key)));
			}
		});

		return result.getValue();
	}

	public <T extends ProtoEntity> T rpop(final String key, final Class<T> protoClass) throws Exception {
		final ActionResult<T> result = new ActionResult<T>();

		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				byte[] buffer = jedis.rpop(SafeEncoder.encode(key));
				if (buffer != null) {
					T value = getInstance(protoClass);
					value.parseFrom(buffer);
					result.setValue(value);
				}
			}
		});

		return result.getValue();
	}

	public Integer rpopInteger(final String key) throws Exception {
		Integer result = null;
		String resultStr = rpopString(key);
		if (resultStr != null) {
			result = Integer.parseInt(resultStr);
		}
		return result;
	}

	public String rpopString(final String key) throws Exception {
		final ActionResult<String> result = new ActionResult<String>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(SafeEncoder.encode(jedis.rpop(SafeEncoder.encode(key))));
			}
		});

		return result.getValue();
	}

	public Long rpush(final String key, final ProtoEntity... values) throws Exception {
		List<byte[]> arrays = new ArrayList<byte[]>();
		for (ProtoEntity pe : values) {
			arrays.add(pe.toByteArray());
		}

		return rpush(key, arrays.toArray(new byte[][] {}));
	}

	public Long rpush(final String key, final String... values) throws Exception {
		List<byte[]> arrays = new ArrayList<byte[]>();
		for (String value : values) {
			arrays.add(SafeEncoder.encode(value));
		}

		return rpush(key, arrays.toArray(new byte[][] {}));
	}

	public Long rpush(final String key, final int... values) throws Exception {
		List<byte[]> arrays = new ArrayList<byte[]>();
		for (int value : values) {
			arrays.add(SafeEncoder.encode(String.valueOf(value)));
		}

		return rpush(key, arrays.toArray(new byte[][] {}));
	}

	private Long rpush(final String key, final byte[]... values) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.rpush(SafeEncoder.encode(key), values));
			}
		});

		return result.getValue();
	}

	public Long rpushx(final String key, final ProtoEntity value) throws Exception {
		return rpushx(key, value.toByteArray());
	}

	public Long rpushx(final String key, final String value) throws Exception {
		return rpushx(key, SafeEncoder.encode(value));
	}

	public Long rpushx(final String key, final int value) throws Exception {
		return rpushx(key, SafeEncoder.encode(String.valueOf(value)));
	}

	Long rpushx(final String key, final byte[] value) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.rpushx(SafeEncoder.encode(key), value));
			}
		});

		return result.getValue();
	}

	public String set(final String key, final ProtoEntity value) throws Exception {
		final ActionResult<String> result = new ActionResult<String>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.set(SafeEncoder.encode(key), value.toByteArray()));
			}
		});

		return result.getValue();
	}

	public String set(final String key, final String strValue) throws Exception {
		final ActionResult<String> result = new ActionResult<String>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.set(SafeEncoder.encode(key), SafeEncoder.encode(strValue)));
			}
		});

		return result.getValue();
	}

	public Long sadd(final String key, final String value) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.sadd(key, value));
			}
		});

		return result.getValue();
	}

	public Long sadd(final String key, final Integer value) throws Exception {
		return sadd(key, String.valueOf(value));
	}

	public Long srem(final String key, final String value) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.srem(key, value));
			}
		});

		return result.getValue();
	}

	public Set<String> sdiff(final String... keys) throws Exception {
		final ActionResult<Set<String>> result = new ActionResult<Set<String>>();
		runCommand(keys[0], new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.sdiff(keys));
			}
		});

		return result.getValue();
	}

	public Set<String> smembers(final String key) throws Exception {
		final ActionResult<Set<String>> result = new ActionResult<Set<String>>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.smembers(key));
			}
		});

		return result.getValue();
	}

	public Long smove(final String sourceKey, final String destKey, final String value) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(sourceKey, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.smove(sourceKey, destKey, value));
			}
		});

		return result.getValue();
	}

	public ScanResult<Integer> sscanInteger(final String key, final String cursor) throws Exception {
		final ActionResult<ScanResult<Integer>> result = new ActionResult<ScanResult<Integer>>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				ScanResult<String> returnValue = jedis.sscan(key, String.valueOf(cursor));
				List<Integer> integerList = new ArrayList<Integer>();
				for (String s : returnValue.getResult()) {
					integerList.add(Integer.valueOf(s));
				}

				result.setValue(new ScanResult<Integer>(returnValue.getCursorAsBytes(), integerList));
			}
		});

		return result.getValue();
	}

	public ScanResult<String> sscan(final String key, final String cursor) throws Exception {
		final ActionResult<ScanResult<String>> result = new ActionResult<ScanResult<String>>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.sscan(key, String.valueOf(cursor)));
			}
		});

		return result.getValue();
	}

	public Long scard(final String key) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.scard(key));
			}
		});

		return result.getValue();
	}

	public Set<Integer> smembersInteger(final String key) throws Exception {
		Set<Integer> result = new HashSet<Integer>();
		Set<String> set = smembers(key);
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			result.add(Integer.valueOf(it.next()));
		}
		return result;
	}
	
	public Set<Long> smembersLong(final String key) throws Exception {
		Set<Long> result = new HashSet<Long>();
		Set<String> set = smembers(key);
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			result.add(Long.valueOf(it.next()));
		}
		return result;
	}

	public boolean sismember(final String key, final String member) throws Exception {
		final ActionResult<Boolean> result = new ActionResult<Boolean>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.sismember(key, member));
			}
		});

		return result.getValue();
	}

	public boolean sismemberInteger(final String key, final Integer member) throws Exception {
		return sismember(key, String.valueOf(member));
	}

	public Long srem(final String key, final Integer value) throws Exception {
		return srem(key, String.valueOf(value));
	}

	public String set(final String key, final int intValue) throws Exception {
		final ActionResult<String> result = new ActionResult<String>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.set(SafeEncoder.encode(key), SafeEncoder.encode(String.valueOf(intValue))));
			}
		});

		return result.getValue();
	}

	public String setex(final String key, final int seconds, final ProtoEntity value) throws Exception {
		final ActionResult<String> result = new ActionResult<String>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				try {
					result.setValue(jedis.setex(SafeEncoder.encode(key), seconds, Serializer.encode(value)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		return result.getValue();
	}

	/*
	 * public String setex(final String key, final int seconds, final ProtoEntity value) throws UnionException { final ActionResult<String> result = new ActionResult<String>(); runCommand(key, new Action<Jedis>() {
	 * 
	 * @Override public void run(Jedis jedis) { result.setValue(jedis.setex(SafeEncoder.encode(key), seconds, value.toByteArray())); } });
	 * 
	 * return result.getValue(); }
	 */

	public String setex(final String key, final int seconds, final byte[] bytesValue) throws Exception {
		final ActionResult<String> result = new ActionResult<String>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.setex(SafeEncoder.encode(key), seconds, bytesValue));
			}
		});

		return result.getValue();
	}

	public String setex(final String key, final int seconds, final String strValue) throws Exception {
		final ActionResult<String> result = new ActionResult<String>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.setex(SafeEncoder.encode(key), seconds, SafeEncoder.encode(strValue)));
			}
		});

		return result.getValue();
	}

	public String setex(final String key, final int seconds, final int intValue) throws Exception {
		final ActionResult<String> result = new ActionResult<String>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.setex(SafeEncoder.encode(key), seconds, SafeEncoder.encode(String.valueOf(intValue))));
			}
		});

		return result.getValue();
	}

	public Long setnx(final String key, final ProtoEntity value) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.setnx(SafeEncoder.encode(key), value.toByteArray()));
			}
		});

		return result.getValue();
	}

	public Long setnx(final String key, final int intValue) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.setnx(SafeEncoder.encode(key), SafeEncoder.encode(String.valueOf(intValue))));
			}
		});

		return result.getValue();
	}

	public Long setnx(final String key, final String strValue) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.setnx(SafeEncoder.encode(key), SafeEncoder.encode(strValue)));
			}
		});

		return result.getValue();
	}

	public Long strlen(final String key) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.strlen(SafeEncoder.encode(key)));
			}
		});

		return result.getValue();
	}

	public Long ttl(final String key) throws Exception {
		final ActionResult<Long> result = new ActionResult<Long>();
		runCommand(key, new Action<Jedis>() {

			@Override
			public void run(Jedis jedis) {
				result.setValue(jedis.ttl(SafeEncoder.encode(key)));
			}
		});

		return result.getValue();
	}

	/**
	 * 用于执行直接的Redis命令，仅用于运维平台，生产环境开发请勿调用
	 * 
	 * @throws Exception
	 */
	public RedisCluster getCurrentRedisCluster(String key) throws Exception {
		return this.jedisGroup.get(key);
	}

	/**
	 * 用于执行直接的Redis命令，仅用于运维平台，生产环境开发请勿调用
	 */
	public Router<RedisCluster> getCurrentRouter() {
		return this.jedisGroup;
	}

	// 由于启用pipeline，所以废弃redis的分发机制，以后再需要的话，使用3.0自带的cluster
	public Jedis getJedis() throws Exception {
		return this.jedisGroup.get("").getJedisPool().getResource();
	}
}
