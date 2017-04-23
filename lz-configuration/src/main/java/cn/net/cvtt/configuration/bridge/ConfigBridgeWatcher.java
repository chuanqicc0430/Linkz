package cn.net.cvtt.configuration.bridge;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.configuration.ConfigurationException;
import cn.net.cvtt.configuration.LoadZkConfigProperties;
import cn.net.cvtt.configuration.bridge.ConfigBridgeDataLoader.DataMonitor;
import cn.net.cvtt.lian.common.util.Combo2;

/**
 * zookeeper连接监控中心
 * 
 * @author zongchuanqi
 *
 */
public class ConfigBridgeWatcher {
	private static final Logger logger = LoggerFactory.getLogger(ConfigBridgeWatcher.class);
	private static final int SESSION_TIMEOUT = 10 * 2000;
	private static final int REFRESH_THRESHOLD = 2 * 1000;

	private static ConfigBridgeWatcher instance = null;
	private static Object syncLock = new Object();

	// private ConfigBridgeDataLoader dataLoader;
	private Set<ConfigBridgeDataLoader> dataLoaderList = new HashSet<ConfigBridgeDataLoader>();;
	private ZooKeeper zk;
	// private AtomicBoolean inited = new AtomicBoolean(false);

	private ConfigBridgeListener listener;

	public static ConfigBridgeWatcher getInstance() throws Exception {
		if (instance == null) {
			synchronized (syncLock) {
				if (instance == null) {
					instance = new ConfigBridgeWatcher();
				}
			}
		}
		return instance;
	}

	private ConfigBridgeWatcher() throws ConfigurationException, IOException {
		internalInit();
	}

	// public ZooKeeper getZk() {
	// return zk;
	// }

	// public void init() throws IOException,ConfigurationException{
	// if (inited.compareAndSet(false, true)) {
	// internalInit();
	// logger.warn("Init zookeeper and zkDataLoader.");
	// } else {
	// logger.warn("already inited.");
	// }
	// }

	private void internalInit() throws ConfigurationException, IOException {
		String zkHost = LoadZkConfigProperties.getInstance().getPropValue("host");
		zk = new ZooKeeper(zkHost, SESSION_TIMEOUT, new SessionWatcher());
	}

	public void internalInitDataLoader(String configPath) throws KeeperException, InterruptedException {
		initZkNode(configPath);
		ConfigBridgeDataLoader dataLoader = new ConfigBridgeDataLoader(REFRESH_THRESHOLD, zk);
		dataLoaderList.add(dataLoader);

		dataLoader.load(configPath, new DataMonitor() {
			@Override
			public void update(Combo2<String, byte[]> datas) {
				fireNodeChanged(datas.getV1());
			}
		});

	}

	private void initZkNode(String configPath) throws KeeperException, InterruptedException {
		Stat s = zk.exists(configPath, false);
		if (s == null) {
			zk.create(configPath, "1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
	}

	public void register(ConfigBridgeListener listener) {
		this.listener = listener;
	}

	private void fireNodeChanged(String path) {
		listener.onNodeChanged(path);
	}

	private class SessionWatcher implements Watcher {
		private void reConn() {
			for (ConfigBridgeDataLoader dataLoader : dataLoaderList) {
				dataLoader.shutdown();
			}

			boolean inited = false;
			while (!inited) { // never give up until re-initialized?
				try {
					internalInit();
					for (ConfigBridgeDataLoader dataLoader : dataLoaderList) {
						try {
							dataLoader.restart();
						} catch (Exception t) {
							logger.error("restart dataloader error", t);
						}
					}
					inited = true;
				} catch (Exception e) {
					logger.warn("re-init failed", e);
					try {
						Thread.sleep(2 * 1000); // TODO
					} catch (InterruptedException ie) {
						logger.warn("damn, I am sleeping", ie);
					}
				}
			}
		}

		// single thread (zooKeeper eventThread)
		@Override
		public void process(WatchedEvent event) {
			if (event.getType() == Event.EventType.None) {
				switch (event.getState()) {
				case SyncConnected:
					logger.info("zk Connected");
					break;
				case Disconnected:
					logger.warn("zk disconnected, passively waiting SyncConnected");
					break;
				case Expired:
				case AuthFailed:
					// It's all over, reconnect
					logger.warn("zk session timeout, re-establishing connection");
					reConn();
					break;
				default:
					logger.warn("what? we got an unexpected event. [{}]", event.getState());
				}
			}
		}
	}
}