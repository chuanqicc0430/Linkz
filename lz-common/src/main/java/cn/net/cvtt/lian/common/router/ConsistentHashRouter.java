package cn.net.cvtt.lian.common.router;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.lian.common.router.hash.HashHelper;
import cn.net.cvtt.lian.common.util.Action;
import cn.net.cvtt.lian.common.util.Combo3;

/**
 *
 * @ClassName: ConsistentHashRouter
 *
 * @Description: 一致性哈希路由
 *
 * @author 
 *
 */
public class ConsistentHashRouter<T extends ReRouteAble> implements Router<T> {

	private static Logger LOGGER = LoggerFactory.getLogger(ConsistentHashRouter.class);

	private final HashHelper hashHelper;
	private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();

	public ConsistentHashRouter(HashHelper hashHelper) {
		this.hashHelper = hashHelper;
	}

	public void initNode(T node, int weight) throws Exception {
		for (int i = 0; i < weight; i++) {
			circle.put(hashHelper.hash(node.toString() + i), node);
		}
	}

	public void addNode(T node, int weight) throws Exception {
		Set<T> effectedNodes = new HashSet<T>();
		for (int i = 0; i < weight; i++) {
			effectedNodes.add(get(node.toString() + i));
		}
		initNode(node, weight);
		for (T effectedNode : effectedNodes) {
			try {
				Set<String> dataKeySet = effectedNode.getAllDataKey(null);
				for (String dataKey : dataKeySet) {
					try {
						if (!effectedNode.toString().equals(get(dataKey).toString())) {
							effectedNode.moveData(dataKey, node);
						}
					} catch (Exception e) {
						LOGGER.error(String.format("moveData error, srcNode = %s, destNode = %s, dataKey = %s", effectedNode, node, dataKey), e);
					}
				}
			} catch (Exception e) {
				LOGGER.error(String.format("rehash effectedNode(%s) error", effectedNode), e);
			}
		}
	}

	public void removeNode(T node, int weight) throws Exception {
		for (int i = 0; i < weight; i++) {
			circle.remove(hashHelper.hash(node.toString() + i));
		}
		Set<String> dataKeySet = node.getAllDataKey(null);
		for (String dataKey : dataKeySet) {
			T destNode = get(dataKey);
			try {
				node.moveData(dataKey, destNode);
			} catch (Exception e) {
				LOGGER.error(String.format("moveData error, srcNode = %s, destNode = %s, dataKey = %s", node, destNode, dataKey), e);
			}
		}
	}

	public T get(String key) throws Exception {
		if (circle.isEmpty()) {
			return null;
		}
		int hash = hashHelper.hash(key);
		if (!circle.containsKey(hash)) {
			SortedMap<Integer, T> tailMap = circle.tailMap(hash);
			hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
		}
		return circle.get(hash);
	}

	public List<Combo3<String, T, T>> reHashAsync(boolean isMoveData) {
		List<Combo3<String, T, T>> result = new ArrayList<Combo3<String, T, T>>();

		Set<T> nodes = new HashSet<T>(circle.values());

		for (T node : nodes) {
			try {
				Set<String> keySet = node.getAllDataKey(null);
				for (String dataKey : keySet) {
					try {
						T expectedNode = get(dataKey);
						if (!expectedNode.toString().equals(node.toString())) {
							result.add(new Combo3<String, T, T>(dataKey, node, expectedNode));
							if (isMoveData) {
								node.moveData(dataKey, expectedNode);
							}
							LOGGER.warn(String.format("fullCheck failed, error dataKey = %s, expectedNode = %s, infactNode = %s, now it has been rehash to destNode", dataKey,
									expectedNode, node));
						}
					} catch (Exception e) {
						LOGGER.error("rehash dataKey error, dataKey = " + dataKey, e);
					}
				}
			} catch (Exception e) {
				LOGGER.error("rehash node error, node = " + node.toString(), e);
			}
		}

		return result;
	}

	@Override
	public void reHashSync(final boolean isMoveData, final String[] keyPatterns, final Action<Combo3<Integer, Integer, String>> progressCallback, final Action<List<Combo3<String, T, T>>> finallyCallback) {
		Thread hashThread = new Thread(new Runnable() {
			@Override
			public void run() {
				List<Combo3<String, T, T>> result = new ArrayList<Combo3<String, T, T>>();

				Set<T> nodes = new HashSet<T>(circle.values());

				String progressFormatter = "节点进度: %s / %s, 数据进度 : %s / %s, 是否仅查询 : %s\n当前节点： %s\n当前数据Key： %s\n";

				AtomicInteger nodeCounter = new AtomicInteger(0);
				for (T node : nodes) {
					try {
						Set<String> keySet = node.getAllDataKey(keyPatterns);
						AtomicInteger keyCounter = new AtomicInteger(0);
						for (String dataKey : keySet) {
							try {
								// 计算进度
								String progress = String.format(progressFormatter, nodeCounter.get(), nodes.size(), keyCounter.get(), keySet.size(), !isMoveData, node.toString(),
										dataKey);
								progressCallback.run(new Combo3<Integer, Integer, String>(nodeCounter.get() * 100 / nodes.size(), keyCounter.get() * 100 / keySet.size(), progress));
								T expectedNode = get(dataKey);
								if (!expectedNode.toString().equals(node.toString())) {
									result.add(new Combo3<String, T, T>(dataKey, expectedNode, node));
									if (isMoveData) {
										node.moveData(dataKey, expectedNode);
									}
									LOGGER.warn(String.format("fullCheck warnning, error dataKey = %s, expectedNode = %s, infactNode = %s"
											+ (isMoveData ? ", now it has been rehash to destNode" : ""), dataKey, expectedNode, node));
								}
							} catch (Exception e) {
								LOGGER.error("rehash dataKey error, dataKey = " + dataKey, e);
							} finally {
								keyCounter.incrementAndGet();
							}
						}
					} catch (Exception e) {
						LOGGER.error("rehash node error, node = " + node.toString(), e);
					} finally {
						nodeCounter.incrementAndGet();
					}
				}

				finallyCallback.run(result);
			}
		});

		hashThread.start();
	}
}