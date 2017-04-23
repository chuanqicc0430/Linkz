package cn.net.cvtt.lian.common.util.obsoleted;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import cn.net.cvtt.lian.common.util.Action;

/**
 * 
 * <b>描述: </b>一个线程安全的Queue，此队列可以实现缓存功能，用于缓存一定时间内的一定数量的数据，例如缓存30秒内的数据或缓存100条数据，
 * 当队列中的信息满足两个条件之一 ，这一批数据会由监控线程将其移出队列，在移除队列的同时，会为每一条数据激活一次事件，事件处理方法由构造该队列实例时指定。<br>
 * 使用时请注意，因为该类依托于{@link ConcurrentLinkedQueue}，而其在
 * {@link ConcurrentLinkedQueue#size()}方法中存在线性的数量统计，因此不适合装填大数量，请避免
 * <p>
 * <b>功能: </b>一个具有缓存且线程安全的Queue，当缓存进行清理时，会回调数据处理方法
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * 以下代码段的意思是创建该队列的一个实例，队列中只能存储100条记录，且最大存储时间为100毫秒，超出该范围的对象会被移出，在移出时调用指定的输出callback
 * LazyQueue&lt;Integer&gt; queue = new LazyQueue&lt;Integer&gt;(&quot;TestLazyQueue&quot;, 100, 3, new Action&lt;List&lt;Integer&gt;&gt;() {
 * 	&#064;Override
 * 	public void run(List&lt;Integer&gt; a) {
 * 		System.out.println(a);
 * 	}
 * });
 * 向队列中增加数据，可以观察到超出既定要求的数据被移除，打印在控制台的信息
 * queue.enQueue(1);
 * queue.enQueue(2);
 * queue.enQueue(3);
 * queue.enQueue(4);
 * queue.enQueue(5);
 * 
 * </pre>
 * <p>
 * 
 * @author 
 * 
 * @param <T>
 */
public class LazyQueue<T> {
	/**
	 * 队列名称
	 */
	private String name;
	/**
	 * 队列容量
	 */
	private int capacity;
	/**
	 * 队列
	 */
	private Queue<T> queue;
	/**
	 * 当前线程
	 */
	private ThreadProc thread;

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getName() {
		return name;
	}

	/**
	 * 参数构造器
	 * 
	 * @param name
	 *            队列名,用于监控
	 * @param lazyMs
	 *            最大延迟毫秒数
	 * @param batchCount
	 *            最大出列长度
	 * @param dequeueAction
	 *            出列回调
	 */
	public LazyQueue(String name, int lazyMs, int batchCount, Action<List<T>> dequeueAction) {
		this.name = name;
		this.capacity = 65536;
		this.queue = new ConcurrentLinkedQueue<T>();
		// this.thread = new Thread(new
		// ThreadProc(queue,batchCount,lazyMs,dequeueAction));
		this.thread = new ThreadProc(queue, batchCount, lazyMs, dequeueAction);
		this.thread.start();
	}

	/**
	 * 进队列方法
	 * 
	 * @param a
	 *            要加入的队列元素
	 */
	public void enQueue(T a) {
		if (queue.size() > capacity) {
			return;
		}
		queue.add(a);
	}
	
	public void deQueue (T a) {
		if (queue.contains(a)) {
			queue.remove(a);
		}
	}

	/**
	 * 无条件清空队列
	 */
	public void flush() {
		queue.clear();
	}

	/**
	 * 返回此队列中的元素数量
	 */
	public int size() {
		return this.queue.size();
	}

	/**
	 * 返回在此队列元素上以恰当顺序进行迭代的迭代器
	 */
	public Iterator<T> iterator() {
		return this.queue.iterator();
	}

	/**
	 * 设置队列线程运行状态 true-运行 false-停止
	 */
	public boolean setRunStatus(boolean runStatus) {
		return this.thread.setRunFlag(runStatus);
	}
}

/**
 * 监控队列的线程类
 */
class ThreadProc<T> extends Thread {
	/**
	 * 队列
	 */
	private Queue<T> queue;
	/**
	 * 最大出列长度
	 */
	private int batchCount;
	/**
	 * 最大延迟毫秒数
	 */
	private int lazyMs;
	/**
	 * 队列元素上次出队列时间
	 */
	private long lastMs;
	/**
	 * 出列回调
	 */
	private Action<List<T>> dequeueAction;
	/**
	 * 线程运行标志 ： true-运行 false-停止
	 */
	private boolean runFlag;

	/**
	 * 参数构造器
	 * 
	 * @param queue
	 *            队列
	 * @param batchCount
	 *            最大出列长度
	 * @param lazyMs
	 *            最大延迟毫秒数
	 * @param dequeueAction
	 *            出列回调
	 */
	public ThreadProc(Queue<T> queue, int batchCount, int lazyMs, Action<List<T>> dequeueAction) {
		this.queue = queue;
		this.batchCount = batchCount;
		this.lazyMs = lazyMs;
		this.dequeueAction = dequeueAction;
		this.lastMs = System.currentTimeMillis();
		this.runFlag = true;
	}

	/**
	 * 重写线程的run方法
	 */
	public void run() {
		while (runFlag) {
			long iniMs = System.currentTimeMillis();
			int dequeueCount = 0;
			// 当队列元素个数大于指定出列长度时,队列元素进行出列且出列个数为指定的出列长度
			if (queue.size() >= batchCount) {
				dequeueCount = batchCount;
			} else {
				long diffMs = iniMs - lastMs;
				// 当队列元素上次出队列时间,超出指定的毫秒数时,队列元素进行出列且出列个数为队列元素个数
				if ((diffMs > lazyMs || diffMs < -lastMs) && queue.size() > 0) {
					dequeueCount = queue.size();
				}
			}
			if (dequeueCount > 0) {
				// 队列元素出列,并返回出列的元素集合
				List<T> items = dequeueItems(dequeueCount);
				// 对出列元素进行回调处理
				dequeueAction(items);
				// 重新记录队列元素出列时间
				lastMs = System.currentTimeMillis();
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					flushCache();
					e.printStackTrace();
					return;
				}
			}
		}
	}

	/**
	 * 队列元素出列
	 * 
	 * @param count
	 *            出列元素个数
	 * @return 返回所出列元素的集合
	 */
	private List<T> dequeueItems(int count) {
		// 当出列元素个数大于实际队列元素个数时,出列个数以队列实际元素为准
		if (count > queue.size()) {
			count = queue.size();
		}
		List<T> items = new ArrayList<T>(count);
		for (int i = 0; i < count; i++) {
			T item = queue.poll();
			items.add(item);
		}
		return items;
	}

	/**
	 * 对出列元素进行回调处理
	 * 
	 * @param items
	 *            出列元素集合
	 */
	private void dequeueAction(List<T> items) {
		// 队列空时,直接返回
		if (items.size() == 0)
			return;
		try {
			dequeueAction.run(items);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 刷新缓存
	 */
	public void flushCache() {
		while (queue.size() > 0) {
			int queueCount = queue.size();
			int dequeueCount = queueCount > batchCount ? batchCount : queueCount;
			// 元素出列
			List<T> items = dequeueItems(dequeueCount);
			// 对出列元素进行回调处理
			dequeueAction(items);
		}
	}

	/**
	 * 设置线程运行标志 true-运行 false-停止
	 */
	public boolean setRunFlag(boolean runFlag) {
		this.runFlag = runFlag;
		return this.runFlag;
	}
}
