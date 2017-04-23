package cn.net.cvtt.lian.common.util.obsoleted;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <b>描述: </b>一致性hash工具类，用于解决集群中当服务器出现增减变动时对散列值的影响
 * <p>
 * <b>功能: </b>使每台服务器的hash值固定，消除新服务器增加对老服务器hash值的变化，减小服务器增减时的缓存重新分布
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * int VIRTUAL_NODE_COUNT = 10;
 * List&lt;String&gt; ipList = new ArrayList&lt;String&gt;();
 * ipList.add(&quot;192.168.0.1&quot;);
 * ipList.add(&quot;192.168.0.2&quot;);
 * ipList.add(&quot;192.168.0.3&quot;);
 * ipList.add(&quot;192.168.0.4&quot;);
 * ipList.add(&quot;192.168.0.5&quot;);
 * KetamaNodeLocator&lt;String&gt; ketamaNodeLocator1 = new KetamaNodeLocator&lt;String&gt;(ipList, VIRTUAL_NODE_COUNT);
 * String ip = ketamaNodeLocator1.getPrimary(&quot;AppServer&quot;);
 * 
 * </pre>
 * <p>
 * 
 * @author 
 * 
 * @param <T>
 */
public final class KetamaNodeLocator<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(KetamaNodeLocator.class);
	private TreeMap<Long, T> ketamaNodes;
	private int numReps = 160;

	public KetamaNodeLocator(List<T> nodes, int nodeCopies) {
		ketamaNodes = new TreeMap<Long, T>();

		numReps = nodeCopies;

		for (T node : nodes) {
			for (int i = 0; i < numReps / 4; i++) {
				// System.out.println(node.toString());
				byte[] digest = computeMd5(node.toString() + i);
				for (int h = 0; h < 4; h++) {
					long m = hash(digest, h);
					ketamaNodes.put(m, node);
				}
			}
		}
	}

	public KetamaNodeLocator(T[] nodes, int nodeCopies) {
		ketamaNodes = new TreeMap<Long, T>();

		numReps = nodeCopies;
		int length = nodes.length;
		for (int j = 0; j < length; j++) {
			for (int i = 0; i < numReps / 4; i++) {
				// System.out.println(node.toString());
				byte[] digest = computeMd5(nodes[j].toString() + i);
				for (int h = 0; h < 4; h++) {
					long m = hash(digest, h);
					ketamaNodes.put(m, nodes[j]);
				}
			}
		}
	}

	public T getPrimary(final String k) {
		byte[] digest = computeMd5(k);
		T rv = getNodeForKey(hash(digest, 0));
		return rv;
	}

	T getNodeForKey(long hash) {
		final T rv;
		Long key = hash;
		if (!ketamaNodes.containsKey(key)) {
			// SortedMap<Long, T> tailMap=ketamaNodes.tailMap(key);
			// if(tailMap.isEmpty()) {
			// key=ketamaNodes.firstKey();
			// } else {
			// key=tailMap.firstKey();
			// }
			// For JDK1.6 version
			key = ketamaNodes.ceilingKey(key);
			if (key == null) {
				key = ketamaNodes.firstKey();
			}
		}

		rv = ketamaNodes.get(key);
		return rv;
	}

	private long hash(byte[] digest, int nTime) {
		long rv = ((long) (digest[3 + nTime * 4] & 0xFF) << 24) | ((long) (digest[2 + nTime * 4] & 0xFF) << 16)
				| ((long) (digest[1 + nTime * 4] & 0xFF) << 8) | (digest[0 + nTime * 4] & 0xFF);

		return rv & 0xffffffffL; /* Truncate to 32-bits */
	}

	/**
	 * Get the md5 of the given key.
	 */
	private byte[] computeMd5(String k) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Instance MD5 algorithm error {}", e);
			throw new RuntimeException("MD5 not supported", e);
		}
		md5.reset();
		byte[] keyBytes = null;
		try {
			keyBytes = k.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(k + " get utf-8 bytes  error {}", e);
			throw new RuntimeException("Unknown string :" + k, e);
		}

		md5.update(keyBytes);
		return md5.digest();
	}
}
