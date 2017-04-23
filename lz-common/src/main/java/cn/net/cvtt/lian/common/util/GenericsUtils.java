package cn.net.cvtt.lian.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <b>描述: </b>泛型工具类
 * <p>
 * <b>功能: </b>用于查找一个类的字段或其父类的泛型信息，获得这些泛型的准确类型
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * public class GenericsUtilsDemo extends ArrayList&lt;String&gt; {
 * 	private java.util.Map&lt;Integer, Date&gt; map = new HashMap&lt;Integer, java.util.Date&gt;();
 * 
 * 	public static void main(String args[]) {
 * 		GenericsUtilsDemo的父类泛型类型
 * 		System.out.println(GenericsUtils.getSuperClassGenricType(GenericsUtilsDemo.class, 0));
 * 		GenericsUtilsDemo的第一个字段的第一、二个泛型类型
 * 		System.out.println(GenericsUtils.getFieldGenericType(GenericsUtilsDemo.class.getDeclaredFields()[0], 0));
 * 		System.out.println(GenericsUtils.getFieldGenericType(GenericsUtilsDemo.class.getDeclaredFields()[0], 1));
 * 	}
 * }
 * </pre>
 * <p>
 * 
 * @author 
 * 
 */
public class GenericsUtils
{
	/**
	 * 通过反射,获得指定类的父类的泛型参数的实际类型. 如BuyerServiceBean extends DaoSupport<Buyer>
	 * 
	 * @param clazz
	 *            clazz 需要反射的类,该类必须继承范型父类
	 * @param index
	 *            泛型参数所在索引,从0开始.
	 * @return 范型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	public static Class<?> getInterfaceGenricType(Class<?> clazz,Class<?> interfaceClazz, int index)
	{
		Type[] genTypes = clazz.getGenericInterfaces();// 得到所有泛型interface
		for(Type genType : genTypes) {
			// 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
			if (!(genType instanceof ParameterizedType)) {
				continue;
			}
			if( ((ParameterizedType)genType).getRawType() != interfaceClazz) {
				continue;
			}
			// 返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class, 如BuyerServiceBean extends
			// DaoSupport<Buyer,Contact>就返回Buyer和Contact类型
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
			if (index >= params.length || index < 0) {
				throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
			}
			if (!(params[index] instanceof Class)) {
				return Object.class;
			}
			return (Class<?>) params[index];
		}
		return Object.class;
	}
	/**
	 * 通过反射,获得指定类的父类的泛型参数的实际类型. 如BuyerServiceBean extends DaoSupport<Buyer>
	 * 
	 * @param clazz
	 *            clazz 需要反射的类,该类必须继承范型父类
	 * @param index
	 *            泛型参数所在索引,从0开始.
	 * @return 范型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	public static Class<?> getSuperClassGenricType(Class<?> clazz, int index)
	{
		Type genType = clazz.getGenericSuperclass();// 得到泛型父类
		// 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		// 返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class, 如BuyerServiceBean extends
		// DaoSupport<Buyer,Contact>就返回Buyer和Contact类型
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class<?>) params[index];
	}
	/**
	 * 
	 * {在这里补充功能说明}
	 * @param clazz
	 * @param index
	 * @return
	 */
	public static Class<?>[] getSuperClassGenricTypes(Class<?> clazz)
	{
		Type genType = clazz.getGenericSuperclass();// 得到泛型父类
		// 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
		if (!(genType instanceof ParameterizedType)) {
			return null;
		}
		// 返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class, 如BuyerServiceBean extends
		// DaoSupport<Buyer,Contact>就返回Buyer和Contact类型
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		Class<?>[] results = new Class<?>[params.length];
		for (int i = 0; i < params.length; i++) {
			results[i] = (Class<?>)params[i];
		}
		return results;
	}

	/**
	 * 通过反射,获得指定类的父类的第一个泛型参数的实际类型. 如BuyerServiceBean extends DaoSupport<Buyer>
	 * 
	 * @param clazz
	 *            clazz 需要反射的类,该类必须继承泛型父类
	 * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	public static Class<?> getSuperClassGenricType(Class<?> clazz)
	{
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得方法返回值泛型参数的实际类型. 如: public Map<String, Buyer> getNames(){}
	 * 
	 * @param Method
	 *            method 方法
	 * @param int index 泛型参数所在索引,从0开始.
	 * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	public static Class<?> getMethodGenericReturnType(Method method, int index)
	{
		Type returnType = method.getGenericReturnType();
		if (returnType instanceof ParameterizedType) {
			ParameterizedType type = (ParameterizedType) returnType;
			Type[] typeArguments = type.getActualTypeArguments();
			if (index >= typeArguments.length || index < 0) {
				throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
			}
			return (Class<?>) typeArguments[index];
		}
		return Object.class;
	}

	/**
	 * 通过反射,获得方法返回值第一个泛型参数的实际类型. 如: public Map<String, Buyer> getNames(){}
	 * 
	 * @param Method
	 *            method 方法
	 * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	public static Class<?> getMethodGenericReturnType(Method method)
	{
		return getMethodGenericReturnType(method, 0);
	}

	/**
	 * 通过反射,获得方法输入参数第index个输入参数的所有泛型参数的实际类型. 如: public void add(Map<String,
	 * Buyer> maps, List<String> names){}
	 * 
	 * @param Method
	 *            method 方法
	 * @param int index 第几个输入参数
	 * @return 输入参数的泛型参数的实际类型集合, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回空集合
	 */
	public static List<Class<?>> getMethodGenericParameterTypes(Method method, int index)
	{
		List<Class<?>> results = new ArrayList<Class<?>>();
		Type[] genericParameterTypes = method.getGenericParameterTypes();
		if (index >= genericParameterTypes.length || index < 0) {
			throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
		}
		Type genericParameterType = genericParameterTypes[index];
		if (genericParameterType instanceof ParameterizedType) {
			ParameterizedType aType = (ParameterizedType) genericParameterType;
			Type[] parameterArgTypes = aType.getActualTypeArguments();
			for (Type parameterArgType : parameterArgTypes) {
				Class<?> parameterArgClass = (Class<?>) parameterArgType;
				results.add(parameterArgClass);
			}
			return results;
		}
		return results;
	}

	/**
	 * 通过反射,获得方法输入参数第一个输入参数的所有泛型参数的实际类型. 如: public void add(Map<String, Buyer>
	 * maps, List<String> names){}
	 * 
	 * @param Method
	 *            method 方法
	 * @return 输入参数的泛型参数的实际类型集合, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回空集合
	 */
	public static List<Class<?>> getMethodGenericParameterTypes(Method method)
	{
		return getMethodGenericParameterTypes(method, 0);
	}

	/**
	 * 通过反射,获得Field泛型参数的实际类型. 如: public Map<String, Buyer> names;
	 * 
	 * @param Field
	 *            field 字段
	 * @param int index 泛型参数所在索引,从0开始.
	 * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	public static Class<?> getFieldGenericType(Field field, int index)
	{
		Type genericFieldType = field.getGenericType();

		if (genericFieldType instanceof ParameterizedType) {
			ParameterizedType aType = (ParameterizedType) genericFieldType;
			Type[] fieldArgTypes = aType.getActualTypeArguments();
			if (index >= fieldArgTypes.length || index < 0) {
				throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
			}
			return (Class<?>) fieldArgTypes[index];
		}
		return Object.class;
	}

	/**
	 * 通过反射,获得Field泛型参数的实际类型. 如: public Map<String, Buyer> names;
	 * 
	 * @param Field
	 *            field 字段
	 * @param int index 泛型参数所在索引,从0开始.
	 * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */

	public static Class<?> getFieldGenericType(Field field)
	{
		return getFieldGenericType(field, 0);
	}

	public static Class<?> getFieldEntityType(Field field)
	{
		Type genericFieldType = field.getGenericType();
		return (Class<?>) genericFieldType;
	}
}
