package cn.net.cvtt.imps.quota;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.imps.user.flags.UserStatusFlags;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.resource.redis.RedisClient;
import cn.net.cvtt.resource.redis.RedisProxy;
import cn.net.cvtt.resource.route.ResourceFactory;

/**
 * 简单的基于redis的配额控制实现
 * 
 * @author zongchuanqi
 *
 */
public class RedisQuotaHandler {
	private static RedisProxy redisProxy;
	private static RedisClient redisClient;

	private static Map<QuotaType, QuotaPolicy> policyMap;
	static final Logger LOGGER = LoggerFactory.getLogger(RedisQuotaHandler.class);

	@Initializer
	public static void initialize() throws Exception {
		InitialUtil.init(ResourceFactory.class);

		redisProxy = ResourceFactory.getRedisProxy("QUOTA");
		redisClient = redisProxy.getRedisClient(0);
		policyMap = new HashMap<QuotaType, QuotaPolicy>();
		policyMap.put(QuotaType.SENDSMS_ONEDAY_LIMIT, new QuotaPolicy(QuotaType.SENDSMS_ONEDAY_LIMIT, 24 * 60 * 60, 10, 10, 10));
		policyMap.put(QuotaType.SENDSMS_ONEMINUTE_FREQUENCY, new QuotaPolicy(QuotaType.SENDSMS_ONEMINUTE_FREQUENCY, 60, 3, 3, 3));
		policyMap.put(QuotaType.NOTICE_SENDTOUNREAD_LIMIT, new QuotaPolicy(QuotaType.NOTICE_SENDTOUNREAD_LIMIT, -1, 3, 3, 3));
		policyMap.put(QuotaType.NOTICE_SENDTOUNREAD_CYCLE, new QuotaPolicy(QuotaType.NOTICE_SENDTOUNREAD_CYCLE, 60 * 5, 1, 1, 1));
	}

	/**
	 * 消费额度，若还有剩余额度则返回true，若额度不足返回false
	 * 
	 * @param type
	 * @param flag
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static boolean consume(QuotaType type, UserStatusFlags flag, Object args) throws Exception {
		return consume(type, flag.intValue(), args);
	}

	public static boolean consume(QuotaType type, int userStatusFlag, Object args) throws Exception {
		QuotaPolicy policy = policyMap.get(type);
		String redisKey = String.format(type.getKeyFormatter(), args);

		long currentValue = redisClient.incr(redisKey);
		int limit = policy.getLimit(userStatusFlag);

		if (currentValue == 1) {
			if (policy.getDuration() != -1) {
				redisClient.expire(redisKey, policy.getDuration());
			}
		}

		if (currentValue <= limit) {
			return true;
		} else {
			if (policy.getDuration() != -1) {
				long restTime = redisClient.ttl(redisKey);
				int expireSec = policy.getDuration();
				if (restTime == -1 || restTime > expireSec) {
					redisClient.set(redisKey, 1);
					redisClient.expire(redisKey, expireSec);
					return true;
				}
			}
			return false;
		}
	}

	public static int getRest(QuotaType type, int userStatusFlag, Object... args) throws Exception {
		QuotaPolicy policy = policyMap.get(type);
		if (policy == null) {
			throw new Exception("UNKNOWN_QUOTA_TYPE");
		}

		String redisKey = String.format(type.getKeyFormatter(), args);

		Integer used = redisClient.getInteger(redisKey);
		int rest = policy.getLimit(userStatusFlag) - (used != null ? used : 0);

		return rest >= 0 ? rest : 0;
	}

	public static long getRestTime(QuotaType type, Object... args) throws Exception {
		String redisKey = String.format(type.getKeyFormatter(), args);
		return redisClient.ttl(redisKey);
	}

	public static void removeAllUserQuota(int userId) throws Exception {
		for (QuotaType type : QuotaType.values()) {
			if (type.getIsBaseOnUser()) {
				String redisKey = String.format(type.getKeyFormatter(), userId);
				redisClient.del(redisKey);
			}
		}
	}
}
