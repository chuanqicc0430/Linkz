package cn.net.cvtt.lian.common.util;

import java.util.HashMap;

/**
 * 
 * <b>描述:
 * </b>该类可以在一个对象上绑定多个其它类型的对象，并为多个其它类型的对象起不同的名字，存放在Map中用以区分，这些记录会保存在整个JVM的运行期间
 * ，当需要时，可通过{@link ObjectBinder#get(Object, String)}方法将绑定的对象取出
 * <p>
 * <b>功能: </b>该类可以在一个对象上绑定命名的上下文
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * 为object绑定一个日期的对象，用以确定object的创建日期
 * Object object = new Object();
 * java.util.Date createTime = new java.util.Date();
 * ObjectBinder.set(object, "createTime", createTime );
 * 
 * 在另一处取出object的创建日期
 * (java.util.Date) ObjectBinder.get(object, "createTime");
 * </pre>
 * 
 * <p>
 * 
 * @author 
 * 
 */
public class ObjectBinder
{
	private static Object sync = new Object(); 
	private static HashMap<Object, HashMap<String, Object>> table = new HashMap<Object, HashMap<String, Object>>();
	
	public static void set(Object a, String name, Object b)
	{
		synchronized (sync) {
			HashMap<String, Object> h = table.get(a);
			if (h == null) {
				h = new HashMap<String, Object>();
				table.put(a, h);
			}
			h.put(name, b);
		}
	}
	
	public static Object get(Object a, String name)
	{
		synchronized (sync) {
			HashMap<String, Object> h = table.get(a);
			if (h != null) {
				return h.get(name);
			} else {
				return null;
			}
		}
	}
	
	public static void remove(Object a)
	{
		synchronized (sync) {
			table.remove(a);
		}
	}
}
