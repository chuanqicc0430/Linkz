package cn.net.cvtt.lian.common.initialization;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <b>描述: </b>用于初始化某些class的某些方法的的工具类，当某一个类中有一个方法有{@link Initializer}
 * 注解时，例如A类的foo()方法,调用{@link InitialUtil#init(Class...)}
 * 方法将A.class传入，A类中的foo()方法会被反射调用，且一个类在同一个JVM下仅会被调用一次，一般用于初始化某些数据，
 * 这个特性与类中的static静态块很相似
 * ，但区别是对于static快，我们无法知晓它准确的运行时间，也就无法保证运行顺序，当我们的某些方法依赖于外部数据时，如果将方法放入static块中
 * ，会出现外部数据没有加载完
 * ，我们的static块就被执行了，这样就会出现数据不准确的情况，如果拥有此类，我们就可以在外部数据都加载完毕的情况下，再去执行我们想要的初始化方法
 * ，且能够保证每个类的初始化方法仅会被执行一次，对于这个初始化方法的有一些要求，如下：<br>
 * 1. 方法必须有{@link Initializer}注解，且一个类中仅有一个<br>
 * 2. 方法必须是公有的(public)且静态的(static)<br>
 * <p>
 * <b>功能: </b>在外部数据加载完毕后，使用此类来初始化其他类，并且保证每个类的每个初始化方法仅被执行一次
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * 		class A {
 * 			<code>@Initializer</code>
 * 			public static void init(){
 * 				System.out.println("Feinno");
 * 			}
 * 		}
 * 
 * 		...
 * 		InitialUtil.init(A.class);
 * </pre>
 * <p>
 * 
 * @author 
 * 
 */
public class InitialUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(InitialUtil.class);
	private static Object syncRoot = new Object();
	private static Map<Class<?>, Lock> locks = new Hashtable<Class<?>, Lock>();

	public static void init(Class<?>... classes) throws InitialException {
		for (Class<?> c : classes) {
			doInit(c);
		}
	}

	private static void doInit(Class<?> clazz) throws InitialException {
		Method initMethod = null;

		//
		// 针对clazz, 先获取锁对象
		Lock lock = locks.get(clazz);
		if (lock == null) {
			synchronized (syncRoot) {
				lock = locks.get(clazz);
				if (lock == null) {
					lock = new Lock();

					initMethod = getInitMethod(clazz);
					// if initMethod is null, skip init and set class is inited
					lock.inited = (initMethod == null);
					locks.put(clazz, lock);
					LOGGER.info("Prepare to initialize clazz: {}", clazz);
				}
			}
		}

		//
		// 单个初始化
		if (lock.inited)
			return;

		synchronized (lock.syncRoot) {
			if (lock.inited)
				return;

			try {
				LOGGER.info("Initializing clazz: {}", clazz);
				initMethod.invoke(null, new Object[] {});
				lock.inited = true;
				LOGGER.info("Initialize clazz OK: {}", clazz);
			} catch (Exception e) {
				LOGGER.error("Initialize clazz failed: " + clazz.getName(), e);
				throw new InitialException(clazz, e);
			}
		}
	}

	/**
	 * {在这里补充功能说明}
	 * 
	 * @param clazz
	 * @return
	 */
	private static Method getInitMethod(Class<?> clazz) {
		Method r = null;
		for (Method m : clazz.getMethods()) {
			if (m.getAnnotation(Initializer.class) != null) {
				if (r != null) {
					throw new IllegalArgumentException("more than one @Initializer for:" + clazz.getName());
				} else if (!Modifier.isStatic(m.getModifiers())) {
					throw new IllegalArgumentException("@Initializer must be static for:" + clazz.getName() + "."
							+ m.getName());
				} else if (m.getParameterTypes().length != 0) {
					throw new IllegalArgumentException("@Initializer can not have parameter:" + clazz.getName() + "."
							+ m.getName());
				} else {
					r = m;
				}
			}
		}
		// if (r == null) {
		// throw new IllegalArgumentException("@Initializer not found for:" +
		// clazz.getName());
		// }
		return r;
	}

	private static class Lock {
		public Object syncRoot = new Object();
		public boolean inited = false;
	}
}
