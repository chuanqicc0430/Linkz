package cn.net.cvtt.lian.common.dumper;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import cn.net.cvtt.lian.common.util.DateUtil;

/**
 * 
 * <b>描述: </b>将Object信息Dump成可读的文本<br>
 * 如 a:User = { name:String = "Hello", age:int = 32, password:byte[16] = {
 * 12,34,56,78,8a,bc,b3,b3,b3,b4,b5,b5,b8 } b:User = { name:String = "Barry",
 * age: int = 30, password:byte[16] = { 12,34,56,78,8a,bc,b3,b3,b3,b4,b5,b5,b8 }
 * } }
 * 
 * array:byte[6] = { 34,56,7a,7b,98,89 };
 * 
 * userArray:ArrayList<User> = { {"Tom", 26}, {"Jack", 28}, }
 * <p>
 * <b>功能: </b>将Object对象Dump成可读的文本的工具类
 * <p>
 * <b>用法: </b>该类是为{@link ObjectDumper}提供支持的底层工具类，对外暴露的接口在{@link ObjectDumper}
 * 中，因此使用方式请参考{@link ObjectDumper}
 * <p>
 * 
 * @author 
 * @see ObjectDumper
 */
@Deprecated
class BruceForceDumper
{
	
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
		return "Deprecated";
//		try {
//			StringBuilder str = new StringBuilder();
//			int level = 0;
//			if (dumpInner(str, obj, objName, null, level))
//				return str.toString();
//			else
//				return "null";
//		} catch (Exception e) {
//			return "dump failed:" + e.getMessage();
//		}
	}

	/**
	 * 逐个将object内反射出来的对象取出
	 * 
	 * @param str
	 *            dump字符流
	 * @param obj
	 *            需要反射的对象
	 * @param fieldName
	 *            属性名称
	 * @param level
	 *            层级关系
	 * @return 是否成功
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private static boolean dumpInner(StringBuilder str, Object obj, String fieldName, String fieldTypeStr, int level) throws InstantiationException, IllegalAccessException
	{
		if (str.toString().length() > 8192) {
			str.append("...TOO LONG...");
			return false;
		}

		if (obj == null && (fieldTypeStr == null || "".equals(fieldTypeStr.trim()))) {
			dumpValue(str, obj, fieldName, null, level);
			return true;
		} else if (obj == null && fieldTypeStr != null) {
			dumpValue(str, obj, fieldName, fieldTypeStr, level);
			return true;
		}

		// 判断obj是否为基本类型或基本类型的封装对象
		if (obj.getClass().isPrimitive() || isFundamentalTypeClass(obj)) {
			dumpValue(str, obj, fieldName, null, level);
			return true;
		}
		Class<?> classType = obj.getClass();
		Field[] fields = classType.getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);
		if (fields.length == 0 || isFundamentalTypeClass(obj)) {
			dumpValue(str, obj, fieldName, null, level);
			return true;
		} else {
			if (obj instanceof Iterable || obj instanceof Map) {
				// 迭代类型
				dumpValue(str, obj, fieldName, null, level);
			} else {
				// 对象类型,循环获取对象里各个属性值
				apppendTabs(str, level);
				String typeName = getClassName(obj.getClass().getCanonicalName());
				str.append(fieldName + ":" + typeName + " ={\r\n");

				for (Field field : fields) {
					Object fieldObj = field.get(obj);
					if (fieldObj == null)
						typeName = getClassName(field.getType().getCanonicalName());
					if (fieldObj != null && fieldObj.getClass() != obj.getClass())
						dumpInner(str, fieldObj, field.getName(), typeName, level + 1);
				}
				apppendTabs(str, level);
				str.append("}\r\n");
			}
		}
		return true;
	}

	/**
	 * 将object内的对象反射出来的值生成格式化的字符流
	 * 
	 * @param str
	 *            dump字符流
	 * @param obj
	 *            需要反射的对象
	 * @param fieldName
	 *            属性名称
	 * @param level
	 *            层级关系
	 * @return 是否成功
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */

	@SuppressWarnings("unchecked")
	private static boolean dumpValue(StringBuilder str, Object obj, String fieldName, String fieldTypeStr, int level) throws InstantiationException, IllegalAccessException
	{
		apppendTabs(str, level);
		if (obj == null && (fieldTypeStr == null || "".equals(fieldTypeStr.trim()))) {
			str.append(fieldName + " = null\r\n");
			return true;
		} else if (obj == null && fieldTypeStr != null) {
			str.append(fieldName + ":" + fieldTypeStr + " = null\r\n");
			return true;
		}

		// List或者Set对象可以用Iterable迭代获取
		Iterable e = null;
		if (obj instanceof Iterable) {
			e = (Iterable) obj;
		}

		Map map = null;
		if (obj instanceof Map) {
			map = (Map) obj;
		}
		String typeName = getClassName(obj.getClass().getCanonicalName());

		if (map == null && e == null && obj.getClass().isArray()) {
			// 获得基本类型数组
			Object sObj = getFundamentalTypeList(obj);
			getDumpString(str, fieldName, typeName, sObj);
		} else if ((map == null && e == null) || isFundamentalTypeClass(obj)) {
			// 如果为空或者obj对象为字符串类型，直接打印
			obj = addObjectString(obj);
			getDumpString(str, fieldName, typeName, obj);

		} else if (map == null && e != null) {
			// Iterable不为空
			str.append(fieldName + ":" + typeName + " = {\r\n");
			for (Iterator it = e.iterator(); it.hasNext();) {
				Object o = (Object) it.next();
				if (!dumpInner(str, o, fieldName, null, level + 1))
					return false;
			}
			apppendTabs(str, level);
			str.append("}\r\n");
		} else {
			// Map不为空
			str.append(fieldName + ":" + typeName + " = {\r\n");
			for (Iterator it = map.entrySet().iterator(); it.hasNext();) {

				Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) it.next();
				apppendTabs(str, level + 1);
				str.append("key:" + entry.getKey() + "\r\n");
				Object o = (Object) entry.getValue();

				apppendTabs(str, level + 1);
				str.append("value:\r\n");
				if (!dumpInner(str, o, fieldName, null, level + 2))
					return false;
			}
			apppendTabs(str, level);
			str.append("}\r\n");
		}
		return true;
	}

	/**
	 * 填充缩进符
	 * 
	 * @param str
	 *            字符流
	 * @param level
	 *            缩进个数
	 */
	private static void apppendTabs(StringBuilder str, int level)
	{
		for (int i = 0; i < level; i++) {
			str.append("\t");
		}
	}

	/**
	 * 编辑类名
	 * 
	 * @param str
	 *            类全路径
	 * @return String 类名
	 */
	private static String getClassName(String str)
	{
		str = str.substring(str.lastIndexOf(".") + 1, str.length());
		return str;
	}

	private static Object addObjectString(Object obj)
	{
		if (obj instanceof String) {
			String objString = (String) obj;
			objString = "\"" + objString + "\"";
			return objString;
		}
		return obj;
	}

	/**
	 * 获得指定格式的字符流
	 * 
	 * @param str
	 *            字符流
	 * @param fieldName
	 *            属性名称
	 * @param typeName
	 *            属性类型
	 * @param obj
	 *            属性值
	 */
	private static void getDumpString(StringBuilder str, String fieldName, String typeName, Object obj)
	{
		if (obj instanceof java.util.Date) {
			str.append(fieldName + ":" + typeName + " = "
					+ DateUtil.formatDateStr((Date) obj, DateUtil.DEFAULT_DATETIME_HYPHEN_FORMAT) + "\r\n");
		} else if (obj instanceof Character) {
			str.append(fieldName + ":" + typeName + " = " + (int) ((Character) obj).charValue() + "\r\n");
		} else {
			str.append(fieldName + ":" + typeName + " = " + obj + "\r\n");
		}
	}

	/**
	 * 获得数组指定格式字符流.如:char[] = char[8]{1, 2, 3, 4, 5, 6, 7, 8}
	 * 
	 * @param obj
	 *            字符数组对象
	 * @return String 字符流
	 */
	private static String getFundamentalTypeList(Object obj)
	{
		StringBuilder fStr = new StringBuilder();
		Class<?> classType = obj.getClass();
		fStr.append(getClassName(classType.getCanonicalName()));
		fStr.insert(fStr.length() - 1, Array.getLength(obj));
		fStr.append("{");
		for (int i = 0; i < Array.getLength(obj); i++) {
			Object val = Array.get(obj, i);

			// 数组加逗号如{1,2,3,4,5,6,7}
			if (i > 0)
				fStr.append(", ");
			if (val instanceof Character)
				fStr.append((int) ((Character) val).charValue());
			else if (val instanceof String)
				fStr.append("\"" + val + "\"");
			else if (val instanceof Byte)
				fStr.append(Integer.toHexString((Byte) val));
			else
				fStr.append(val);
		}

		fStr.append("}");
		return fStr.toString();
	}

	/**
	 * 判断是否为基本类型的封装类
	 * 
	 * @param param
	 * @return boolean
	 */
	private static boolean isFundamentalTypeClass(Object param)
	{
		if (param instanceof Number || param instanceof Comparable) {
			return true;
		} else {
			return false;
		}
	}
}
