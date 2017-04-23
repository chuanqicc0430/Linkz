package cn.net.cvtt.lian.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolUtil {
	private static ThreadPoolUtil instance = null;
	private static Object syncLock = new Object();
	static ExecutorService robot;
	static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolUtil.class);

	public static ThreadPoolUtil getInstance() {
		if (instance == null) {
			synchronized (syncLock) {
				if (instance == null) {
					instance = new ThreadPoolUtil();
				}
			}
		}

		return instance;
	}

	private ThreadPoolUtil() {

		robot = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
				LOGGER.error("ThreadPoolExecutor rejects you");
				task.run();
			}
		});

	}

	public void pop(Runnable task) {
		try {
			robot.execute(task);
		} catch (Exception ex) {
			LOGGER.error("ThreadPoolExecutor pop task into queue meets error");
		}
	}
}
