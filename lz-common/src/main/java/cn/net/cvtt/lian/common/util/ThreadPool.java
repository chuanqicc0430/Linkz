package cn.net.cvtt.lian.common.util;

import java.util.concurrent.*;
import java.util.*;

/**
 * 通用线程池 <br>
 * 如果需要使用线程池，必须显示调用ThreadPool.init()初始化； <br>
 * <br>
 * 本向线程池分为两部分实现： <li>系统静态线程池<br>
 * 参数已经被调为最优，可直接用，用于大量短时间任务的常见场景；<br>
 * 本静态线程池由进程共享，默认开设20个线程，可通过init(int corePoolSize)设定默认线程数； <br>
 * 使用示例：<br>
 * <code>
 * <pre>
package test.cn.net.cvtt.lian.common.util;
import cn.net.cvtt.lian.common.util.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.*;


 public void testSubmit1() {
		CountDownLatch cdl = new CountDownLatch(3);
		
		ThreadPool.submit(new Counter(cdl));
		ThreadPool.submit(new Counter(cdl));

		try {
			cdl.await();
			Assert.assertEquals(10, Counter.count.intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	static class Counter implements Runnable {
		public static AtomicInteger count = new AtomicInteger(0);
		public static int resetTimes=0;
		CountDownLatch cdl;
		public Counter(CountDownLatch cdl) {
			this.cdl = cdl;
		}
		public void run() {
			try {
				count.addAndGet(1);
				cdl.countDown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
 * </pre>
 * </code>
 * 
 * <li>自定义线程池(1) <br>
 * 用于普通有自定义需求的场景，需要创建新的线程池对象； <br>
 * 可配置的三个参数 <b>corePoolSize,maximumPoolSize，queueSize</b> <br>
 * 线程池将根据 corePoolSize设置和工作队列状态自动调整池大小：<br>
 * 工作线程超过corePoolSize并且工作队列已满则生成新线程。但总线程数不超过maximumPoolSize<br>
 * <br>
 * <br>
 * 使用示例：<br>
 * 
 * 
 * 
 * <code>
 * <pre>
package test.cn.net.cvtt.lian.common.util;
import cn.net.cvtt.lian.common.util.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.*;

	public void testEnqueue2(){
		ThreadPool tp=new ThreadPool(5,30,50);
		CountDownLatch cdl = new CountDownLatch(10);
		Counter.count.set(0);
		
		ArrayList<Runnable> al=new ArrayList<Runnable>();
		al.add(new Counter(cdl));
		al.add(new Counter(cdl));
		al.add(new Counter(cdl));
		al.add(new Counter(cdl));
		al.add(new Counter(cdl));
		al.add(new Counter(cdl));
		al.add(new Counter(cdl));
		al.add(new Counter(cdl));
		al.add(new Counter(cdl));
		al.add(new Counter(cdl));
		
		tp.enqueue(al);
		try {
			cdl.await();
			Assert.assertEquals(10, Counter.count.intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		tp.shutdown();
	}
	
	
		static class Counter implements Runnable {
		public static AtomicInteger count = new AtomicInteger(0);
		public static int resetTimes=0;
		CountDownLatch cdl;
		public Counter(CountDownLatch cdl) {
			this.cdl = cdl;
		}
		public void run() {
			try {
				count.addAndGet(1);
				cdl.countDown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
 * </pre>
 * </code>
 * 
 * 
 * 
 * 
 * 
 * @auther 
 */
@Deprecated
public class ThreadPool {
	private static int STATIC_POOL_SIZE = 20;
	private static ExecutorService executor;
	private static boolean init = false;

	private static int SCHEDULED_POOL_SIZE = 5;
	private static ScheduledExecutorService scheduledExecutors;
	private static boolean init_scheduled = false;

	/**
	 * 显示初始化方法，使用线程池之前必须先调用本方法或本方法的重构方法
	 */
	public synchronized static void init() {
		if(!init)
		{
			//CPU*10
			STATIC_POOL_SIZE = Runtime.getRuntime().availableProcessors()*10;
			executor = Executors.newFixedThreadPool(STATIC_POOL_SIZE);
			init = true;
		}
	}

	/**
	 * 显示初始化方法，使用线程池之前必须先调用本方法或本方法的重构方法
	 * @param corePoolSize
	 *            静态线程池守候线程数量
	 */
	public synchronized static void init(int corePoolSize) {
		if(!init)
		{
			executor = Executors.newFixedThreadPool(corePoolSize);
			init = true;
		}
	}
	
	/**
	 * 显示初始化定时任务线程池的方法，使用定时任务线程池之前必须先调用本方法
	 * 分开初始化的目的是有些worker可能不需要定时任务功能，在这种情况下，能节省线程资源。
	 */
	public synchronized static void initScheduler() {
		if(!init_scheduled)
		{
			scheduledExecutors = Executors.newScheduledThreadPool(SCHEDULED_POOL_SIZE); 
			init_scheduled = true;
		}
	}

	/**
	 * 启动一次静态线程池的顺序关闭，执行以前提交的任务，但不接受新任务。
	 * 该方法也会关闭定时任务线程池。
	 */
	public synchronized static void shutdownStaticPool(){
		if(executor != null) {
			if(!executor.isShutdown()) {
				executor.shutdown();
			}
		}
		
		if(scheduledExecutors != null ){
			if(!scheduledExecutors.isShutdown()) {
				scheduledExecutors.shutdown();
			}
		}
	}
	
	/**
	 * 如果静态线程池关闭后所有任务都已完成，则返回 true。
	 * @return
	 */
	public static boolean isStaticPoolShutDown() 
	{
		return executor.isShutdown();
	}
	
	/**
	 * 加入一个任务到Q中
	 * 
	 * @param r
	 *            一个任务
	 */
	public static Future<?> submit(Runnable r) {
		if(!init)
			init();
		return executor.submit(r);
	}
	
	/**
	 * 提交一个延迟执行的任务
	 * 如果提交出错，该任务将不会执行。
	 * @param seconds 需要等待的秒数。
	 * @param r 一个任务
	 * @return
	 */
	public static ScheduledFuture lazySubmit(Runnable r,int seconds) {
		return scheduledExecutors.schedule(r, seconds, TimeUnit.SECONDS);
	}

	/**
	 * 提交一个延迟执行的任务
	 * 如果提交出错，该任务将不会执行。
	 * @param r 一个任务
	 * @param delay 延迟的时间
	 * @param unit  延迟的时间单位。
	 * @return
	 */
	public static ScheduledFuture lazySubmit(Runnable r,int delay,TimeUnit unit) {
		return scheduledExecutors.schedule(r, delay, unit);
	}

	/**
	 * 提交一个周期任务;
	 * 周期计算是从上一次开始执行到下一次开始执行的时间
	 * @param r
	 * @param delay
	 * @param period
	 * @param unit
	 * @return
	 */
	public static ScheduledFuture scheduleAtFixedRate(Runnable r,int delay,int period,TimeUnit unit) {
		return scheduledExecutors.scheduleAtFixedRate(r, delay, period,unit);
	}
	
	/**
	 * 提交一个周期任务
	 * 周期计算是从上一次结束执行到下一次开始执行的时间
	 * @param r
	 * @param delay
	 * @param period
	 * @param unit
	 * @return
	 */
	public static ScheduledFuture scheduleWithFixedDelay(Runnable r,int delay,int period,TimeUnit unit) {
		return scheduledExecutors.scheduleWithFixedDelay(r, delay, period,unit);
	}

	/**
	 * 加入一组任务到Q中
	 * 
	 * @param ar
	 *            按照顺序提交的一组任务
	 */
	public static void submit(ArrayList<Runnable> al) {
		if(!init)
			init();
		for (int i = 0; i < al.size(); i++) {
			executor.submit(al.get(i));
		}
	}
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	private final int corePoolSize;
	private final int maxnumPoolSize;
	private ThreadPoolExecutor newExecutor;
	private static final int DEFAULT_NEW_POOL_SIZE = 5;
	private static final int DEFAULT_NEW_MAX_POOL_SIZE = 50;
	private static final int DEFAULT_NEW_KEEPALIVE_TIME = 3000;
	private static final int DEFAULT_NEW_QUEUE_SIZE=1000;

	/**
	 * 实例化自定义线程池 <br>
	 * corePoolSize默认设置为5个 <br>
	 * maxnumPoolSize默认设置为50个<br>
	 * keepAliveTime默认设置为3秒<br>
	 * queueSize任务queue设置为100个
	 */
	public ThreadPool() {
		this.corePoolSize = DEFAULT_NEW_POOL_SIZE;
		this.maxnumPoolSize = DEFAULT_NEW_MAX_POOL_SIZE;
		newExecutor = new ThreadPoolExecutor(this.corePoolSize,
				this.maxnumPoolSize, DEFAULT_NEW_KEEPALIVE_TIME,
				TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(DEFAULT_NEW_QUEUE_SIZE),
				new ThreadPoolExecutor.DiscardOldestPolicy());
	}

	/**
	 * 实例化自定义线程池
	 * 
	 * @param corePoolSize
	 *            线程池大小
	 * @param maxnumPoolSize
	 *            线程池最大容量
	 * @param queueSize
	 *            任务queue大小
	 */
	public ThreadPool(int corePoolSize, int maxnumPoolSize,int queueSize) {
		this.corePoolSize = corePoolSize;
		this.maxnumPoolSize = maxnumPoolSize;
		newExecutor = new ThreadPoolExecutor(this.corePoolSize,
				this.maxnumPoolSize, DEFAULT_NEW_KEEPALIVE_TIME,
				TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(queueSize),
				new ThreadPoolExecutor.DiscardOldestPolicy());
	}
	
	/**
	 * 获取线程池的实例
	 * @return 线程池的实例
	 */
	public ThreadPoolExecutor getThreadPoolExecutor(){
		return this.newExecutor;
	}

	/**
	 * 添加任务
	 * 
	 * @param r
	 */
	public void enqueue(Runnable r) {
		newExecutor.execute(r);
	}

	/**
	 * 批量添加任务
	 * 
	 * @param ar
	 */
	public void enqueue(ArrayList<Runnable> al) {
		for (int i = 0; i < al.size(); i++) {
			newExecutor.execute(al.get(i));
		}
	}

	/**
	 * 获取线程池中任务的个数
	 * 
	 * @return 未执行的任务的个数
	 */
	public int getQueueCount() {
		return newExecutor.getQueue().size();
	}

	/**
	 * 获取线程池中空闲线程的个数，只能获取近似值
	 * 
	 * @return 空闲线程的个数
	 */
	public int getIdleThreadCount() {
		int getIdleThreadCount = this.corePoolSize
				- newExecutor.getActiveCount();
		return getIdleThreadCount > 0 ? getIdleThreadCount : 0;
	}

	/**
	 * 如果cdl对象计数器达到0或者达到超时时间delay就执行r方法
	 * 
	 * @see java.util.concurrent.CountDownLatch;

	 * @param r	任务句柄
	 * @param cdl	计数器	
	 * @param delay	超时时间，时间单位为毫秒
	 * 
	 * 使用示例：
	 * 
	 * 
	 * <pre>
	 <code>
	 public void testDoWhileFired1(){
		ThreadPool tp=new ThreadPool();
		Counter.count.set(0);
		final CountDownLatch cdl = new CountDownLatch(5);
		final CountDownLatch cdl2 = new CountDownLatch(1);
		tp.enqueue(new Counter(cdl));
		tp.enqueue(new Counter(cdl));
		tp.enqueue(new Counter(cdl));
		tp.enqueue(new Counter(cdl));
		tp.enqueue(new Counter(cdl));
		Runnable run =new Runnable(){
			public void run(){
				resetCount1();
				cdl2.countDown();
			}
		};
		
		tp.doWhileFired(run, cdl, (long)5000);
		
		try {
			cdl2.await();
			Assert.assertEquals(1, Counter.resetTimes);
			Assert.assertEquals(0, Counter.count.intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		tp.shutdown();
	}
	
		static class Counter implements Runnable {
		public static AtomicInteger count = new AtomicInteger(0);
		public static int resetTimes=0;
		CountDownLatch cdl;
		public Counter(CountDownLatch cdl) {
			this.cdl = cdl;
		}
		public void run() {
			try {
				count.addAndGet(1);
				cdl.countDown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	</code></pre>
	 */
	public void doWhileFired(final Runnable r, final CountDownLatch cdl, final long delay){
		new Thread() {
			public void run(){
				try{
					cdl.await(delay,TimeUnit.MILLISECONDS);
					r.run();
				}
				catch(InterruptedException e){
					e.printStackTrace( );
				}
			}
		}.start();
		
	}
	

	/**
	 * 如果cb对象达到公共屏障点，就执行r方法
	 * @see java.util.concurrent.CyclicBarrier;
	 * @param r	任务句柄
	 * @param cb	循环路障变量
	 * @param delay 超时时间，时间单位为毫秒
	 * @param doCount 执行次数限制，如果无限制，设置为-1
	 * 
	 * 使用示例：
	 * 
	 * 
	 * <pre>
	 <code>
	 public void testDoWhileFired2(){
		ThreadPool tp=new ThreadPool();
		Counter.count.set(0);
		final CyclicBarrier cb1 = new CyclicBarrier(3);
		final CountDownLatch cdl2 = new CountDownLatch(3);
		tp.enqueue(new Counter2(cb1));
		tp.enqueue(new Counter2(cb1));
		tp.enqueue(new Counter2(cb1));
		tp.enqueue(new Counter2(cb1));
		tp.enqueue(new Counter2(cb1));
		tp.enqueue(new Counter2(cb1));
		Runnable run =new Runnable(){
			public void run(){
				resetCount2();
				cdl2.countDown();
			}
		};
		
		tp.doWhileFired(run, cb1, (long)5000,3);
		
		try {
			cdl2.await();
			Assert.assertEquals(1, Counter.resetTimes);
			Assert.assertEquals(0, Counter.count.intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		tp.shutdown();
	}
	
	
		static class Counter implements Runnable {
		public static AtomicInteger count = new AtomicInteger(0);
		public static int resetTimes=0;
		CountDownLatch cdl;
		public Counter(CountDownLatch cdl) {
			this.cdl = cdl;
		}
		public void run() {
			try {
				count.addAndGet(1);
				cdl.countDown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	</code></pre>
	 * 
	 */
	public void doWhileFired(final Runnable r,final CyclicBarrier cb,final long delay,final int doCount) {
		new Thread() {
			public void run(){
				int count=0;
				try{
					for(;;){
						cb.await(delay,TimeUnit.MILLISECONDS);
						r.run();
						count++;
						if(count==doCount && doCount!=-1){
							break;
						}
					}
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				catch(TimeoutException e){
					e.printStackTrace();
				}
				catch(BrokenBarrierException e){
					e.printStackTrace();
				}
				
			}
		}.start();
	}

	/**
	 * 按过去执行已提交任务的顺序发起一个有序的关闭，但是不接受新任务。 需要注意的是线程池必须使用shutdown来显式关闭，否则主线程就无法退出。
	 * shutdown不会阻塞主线程。
	 */
	public void shutdown() {
		newExecutor.shutdown();
	}
}