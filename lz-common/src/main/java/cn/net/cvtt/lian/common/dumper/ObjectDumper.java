package cn.net.cvtt.lian.common.dumper;

/**
 * 
 * <b>描述: </b>将Object信息Dump成可读的文本的工具类，提供了将一个对象Dump成可读的字符串
 * <p>
 * <b>功能: </b>将一个对象Dump成可读的字符串
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * 简单对象
 * byte[] by = { 'a', 'b', 'c' };
 * String str = ObjectDumper.dumpString(by, &quot;byte&quot;);
 * 复杂的Java对象
 * ProtoEntity protoEntity = ... //任意复杂的Java对象
 * String str = ObjectDumper.dumpString(protoEntity, &quot;ProtoEntity&quot;);
 * </pre>
 * <p>
 * 
 * @author 
 * 
 */
public class ObjectDumper
{
	public static String dumpString(Object obj)
	{
		return dumpString(obj, "");
	}
	
	/**
	 * 将Object内信息反射成指定格式的字符串
	 * 
	 * @param obj
	 *            需要反射的对象
	 * @param objName
	 *            属性名称
	 * @return String 字符流
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static String dumpString(Object obj, String objName)
	{
		if (obj == null) {
			return "<null>";
		} else {
			try {
			if (obj instanceof Dumpable) {
				Dumpable d = (Dumpable)obj;
				return d.dumpContent();
			} else {
				return BruceForceDumper.dumpString(obj, objName);
			}
			} catch (Exception e) {
				return "obj dump failed:" + e;
			}
		}
	}
}
