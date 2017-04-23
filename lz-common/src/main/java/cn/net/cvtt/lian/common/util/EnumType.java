package cn.net.cvtt.lian.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import org.slf4j.LoggerFactory;

import cn.net.cvtt.lian.common.serialization.protobuf.TypeEnum;

/**
 * 
 * <b>描述: </b>用来表示一个类具有枚举特征的类<br>
 * 1、实例数量是固定的。<br>
 * 2、同样的键值将得到同样的实例<br>
 * 3、可以用"=="来判断实例是否相等。<br>
 * 4、枚举类只能定义私有构造函数。 <br>
 * 5、枚举类必须定义成final类。 <br>
 * 6、枚举类的直接父类必须时EnumType。<br>
 * 7、必须提供一个静态方法defaul来指定一个有效的默认值。 <br>
 * 8、必须实现一个静态的valueOf方法，来将key值转成枚举实例。
 * <p>
 * <b>功能: </b>用来表示一个类具有枚举特征的类
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * public final class EnumTypeInt extends EnumType&lt;Integer, EnumTypeInt&gt; {
 * 
 * 	public static final EnumTypeInt TestO = new EnumTypeInt(1);
 * 	public static final EnumTypeInt TestT = new EnumTypeInt(2);
 * 	public static final EnumTypeInt TestTh = new EnumTypeInt(3);
 * 
 * 	private EnumTypeInt(Integer value) {
 * 		super(value);
 * 	}
 * 
 * 	public static EnumTypeInt defaultValue() {
 * 		return EnumType.valueOf(EnumTypeInt.class, 1);
 * 	}
 * 
 * 	public static EnumTypeInt valueOf(Integer key) {
 * 		return EnumType.valueOf(EnumTypeInt.class, key);
 * 	}
 * }
 * </pre>
 * <p>
 * 
 * @author 
 * 
 * @param <K>
 * @param <T>
 */
@SuppressWarnings("deprecation")
public abstract class EnumType<K, T extends EnumType> {

	/**
	 * 用来表示正常的异常。
	 */
	private static final Error OK = new Error("OK");
	/**
	 * 返回默认值的方法名。
	 */
	private static final String DEFAULT_METHOD = "defaultValue";
	/**
	 * 解析key值的方法名。
	 */
	private static final String VALUEOF_METHOD = "valueOf";

	/**
	 * 以枚举类型为组，保存所有的枚举实例。
	 */
	private static final HashMap<Class<? extends EnumType>, HashMap<Object, EnumType>> INSTANCES = new HashMap<Class<? extends EnumType>, HashMap<Object, EnumType>>(
			20);

	/**
	 * 保存每个枚举类型的返回默认值的方法。
	 */
	private static final HashMap<Class<? extends EnumType>, Method> DEFAULT_METHOD_MAP = new HashMap<Class<? extends EnumType>, Method>(
			20);

	/**
	 * 保存验证通过的枚举类。
	 */
	private static final HashMap<Class<? extends EnumType>, Error> LOADED_ENUMTYPES = new HashMap<Class<? extends EnumType>, Error>(
			20);

	/**
	 * 枚举类型的键值，可以唯一标识一个枚举值。
	 */
	private K key;

	/**
	 * 
	 * @param value
	 *            必须不为null。
	 * @throws InstantiationError
	 *             当键值已经被使用时抛出。
	 * @throws ClassFormatError
	 *             当枚举类实现违反了规则时抛出。
	 */
	protected EnumType(K value) {
		if (value == null) {
			throw new NullPointerException("The key of the enumeration '" + this.getClass().getName()
					+ "' can not be null!");
		}
		loadEnum(this.getClass());
		this.key = value;
		this.register();
	}

	/**
	 * 加载枚举类包含两个部分 1、验证枚举类是否吻合规则 A、枚举类只能定义私有构造函数。 B、枚举类必须定义成final类。
	 * C、枚举类的直接父类必须时EnumType。 2、初始化枚举类的静态常量。
	 */
	protected static void loadEnum(Class<? extends EnumType> enumClazz) {
		Error exception = LOADED_ENUMTYPES.get(enumClazz);
		if (exception != null) {
			if (exception == OK) {
				return;
			} else {
				throw exception;
			}
		}
		synchronized (enumClazz) {
			exception = LOADED_ENUMTYPES.get(enumClazz);
			if (exception != null) {
				if (exception == OK) {
					return;
				} else {
					throw exception;
				}
			} else {
				try {
					Class<?> keyClazz = getKeyClass(enumClazz);
					// 先检查key的类型
					if (!validatorKeyType(keyClazz)) {
						throw new ClassFormatError("As a key type,class '" + keyClazz.getName()
								+ "' must be Integer、Long、Short、Char、String or Byte class!");
					}
					// ================================================================
					// 开始检查该枚举类
					// 检查枚举类是不是final
					if (!Modifier.isFinal(enumClazz.getModifiers())) {
						throw new ClassFormatError("As a enumeration type,class '" + enumClazz.getName()
								+ "' must be declared as final class!");
					}
					// 检查枚举类的直接父类是不是EnumTyp
					if (enumClazz.getSuperclass() != EnumType.class) {
						throw new ClassFormatError("As a enumeration type,class '" + enumClazz.getName()
								+ "' must extend the class<" + EnumType.class.getName() + "> directly!");
					}
					// 检查构造函数是不是private
					Constructor[] constructors = enumClazz.getDeclaredConstructors();
					for (Constructor constructor : constructors) {
						if (!Modifier.isPrivate(constructor.getModifiers())) {
							throw new ClassFormatError("As a enumeration type,class '" + enumClazz.getName()
									+ "' can only have private constructors!");
						}
					}
					// 检查是否实现valueOf方法。
					Method valueofMethod = null;
					try {

						valueofMethod = enumClazz.getDeclaredMethod(VALUEOF_METHOD, keyClazz);
					} catch (Exception e) {
						throw new ClassFormatError("As a enumeration type,class '" + enumClazz.getName()
								+ "' must implement a public static method 'public static " + enumClazz.getSimpleName()
								+ " " + VALUEOF_METHOD + "(" + keyClazz.getSimpleName()
								+ " key)' which return the default value!");
					}
					// 检查valueOf方法是不是static、public
					if ((!Modifier.isPublic(valueofMethod.getModifiers()))
							|| (!Modifier.isStatic(valueofMethod.getModifiers()))) {
						throw new ClassFormatError("As a enumeration type,the method '" + valueofMethod.toString()
								+ "' must be decalred as public and static method!");
					}
					// 检查valueOf的返回类型是否与枚举类型一致
					if (valueofMethod.getReturnType() != enumClazz) {
						throw new ClassFormatError("As a enumeration type,the method '" + valueofMethod.toString()
								+ "' must return type '" + enumClazz.getSimpleName() + "'!");
					}

					// 检查是否实现返回默认值的方法defaultValue。
					Method defMethod = null;
					try {
						defMethod = enumClazz.getDeclaredMethod(DEFAULT_METHOD);
					} catch (Exception e) {
						throw new ClassFormatError("As a enumeration type,class '" + enumClazz.getName()
								+ "' must implement a public static method 'public static " + enumClazz.getSimpleName()
								+ " " + DEFAULT_METHOD + "()' which return the default value!");
					}
					// 检查defaultValue方法是不是static、public
					if ((!Modifier.isPublic(defMethod.getModifiers()))
							|| (!Modifier.isStatic(defMethod.getModifiers()))) {
						throw new ClassFormatError("As a enumeration type,the method '" + defMethod.toString()
								+ "' must be decalred as public and static method!");
					}
					// 检查defaultValue的返回类型是否与枚举类型一致
					if (defMethod.getReturnType() != enumClazz) {
						throw new ClassFormatError("As a enumeration type,the method '" + defMethod.toString()
								+ "' must return type '" + enumClazz.getName() + "'!");
					}
					DEFAULT_METHOD_MAP.put(enumClazz, defMethod);

					// ================================================================
					// 开始加载该枚举

					// 创建属于该枚举类的map，
					INSTANCES.put(enumClazz, new HashMap<Object, EnumType>(20));
					// 开始初始化静态常量。
					Field[] fields = enumClazz.getDeclaredFields();
					for (Field field : fields) {
						if (Modifier.isStatic(field.getModifiers())) {
							// 静态变量。访问该静态变量，触发该静态变量的初始化。
							if (Modifier.isPublic(field.getModifiers())) {
								// public 变量
								try {
									field.get(null);
								} catch (IllegalArgumentException e) {
									// 不可能出现
								} catch (IllegalAccessException e) {
									// 不可能出现
								}
							} else {
								// 非public变量。
								try {
									field.setAccessible(true);
									field.get(null);
								} catch (Throwable ex) {
									// 出现异常吞掉，只是简单的不初始化该静态变量
								}
							}
						}
					}
					LOADED_ENUMTYPES.put(enumClazz, OK);
				} catch (Error ex) {
					// 清除痕迹。
					DEFAULT_METHOD_MAP.remove(enumClazz);
					INSTANCES.remove(enumClazz);
					// 设置初始化错误。
					LOADED_ENUMTYPES.put(enumClazz, ex);
					throw ex;
				}
			}
		}

	}

	/**
	 * 注册一个枚举实例.
	 * 
	 * @throws InstantiationError
	 *             当键值已经被使用时抛出。
	 */
	private void register() {
		Class<T> clazz = (Class<T>) this.getClass();
		HashMap<Object, EnumType> classEnumMap = INSTANCES.get(clazz);
		synchronized (classEnumMap) {
			if (classEnumMap.containsKey(key)) {
				throw new InstantiationError("The key(" + key + ") is already used!");
			} else {
				classEnumMap.put(key, this);
			}
		}
	}

	/**
	 * 
	 * @return 返回该枚举值的键值。
	 */
	public final K key() {
		return this.key;
	}

	/**
	 * 
	 * @return 返回给枚举值的名字，用key值的字符串表示。
	 */
	public final String name() {
		return String.valueOf(this.key);
	}

	public String toString() {
		return String.valueOf(this.key);
	}

	/**
	 * 
	 * @return 返回当前枚举类型的值集合。
	 */
	public final Collection<T> values() {
		return values((Class<T>) this.getClass());
	}

	/**
	 * 
	 * @param enumClazz
	 * @return 返回枚举类的键值类型。
	 */
	private static Class<?> getKeyClass(Class<?> enumClazz) {
		return GenericsUtils.getSuperClassGenricType(enumClazz, 0);
	}

	private static boolean validatorKeyType(Class<?> keyClazz) {
		if (TypeEnum.INTEGERTYPE.getCla().isAssignableFrom(keyClazz)
				|| TypeEnum.SHORTTYPE.getCla().isAssignableFrom(keyClazz)
				|| TypeEnum.LONGTYPE.getCla().isAssignableFrom(keyClazz)
				|| TypeEnum.BYTETYPE.getCla().isAssignableFrom(keyClazz)
				|| TypeEnum.CHARTYPE.getCla().isAssignableFrom(keyClazz)
				|| TypeEnum.STRINGTYPEB.getCla().isAssignableFrom(keyClazz)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param enumClazz
	 * @return 返回制定枚举类型的值集合
	 */
	public static <T extends EnumType> Collection<T> values(Class<T> enumClazz) {
		HashMap<Object, EnumType> classEnumMap = INSTANCES.get(enumClazz);
		if (classEnumMap == null) {
			// 没有被加载，加载该枚举类
			loadEnum(enumClazz);
			// 递归调用，返回枚举值集合
			return values(enumClazz);
		} else {
			return (Collection<T>) Collections.unmodifiableCollection(classEnumMap.values());
		}
	}

	/**
	 * 
	 * @param key
	 * @return 返回value对应的枚举对象实例，同一个value将始终返回同一个实例。如果找不到，或者key为null，则返回默认值。
	 */
	public static <K, T extends EnumType> T valueOf(Class<T> enumClazz, K key) {
		if (key == null) {
			try {
				// 如果之前没有加载，而访问的时一个静态变量定义的枚举值，则此举将会触发枚举值加载。
				Method defMethod = DEFAULT_METHOD_MAP.get(enumClazz);
				if (defMethod == null) {
					// 没有被加载,或加载失败了，加载该枚举类
					loadEnum(enumClazz);
					// 递归调用，返回该枚举实例。
					return valueOf(enumClazz, key);
				} else {
					return (T) defMethod.invoke(null);
				}
			} catch (Exception e) {
				throw new RuntimeException("Invoke the method '" + DEFAULT_METHOD_MAP.get(enumClazz).toString()
						+ "' failed!", e);
			}
		}
		HashMap<Object, EnumType> classEnumMap = INSTANCES.get(enumClazz);
		if (classEnumMap == null) {
			// 没有被加载,或加载失败了，加载该枚举类
			loadEnum(enumClazz);
			// 递归调用，返回该枚举实例。
			return valueOf(enumClazz, key);
		} else {
			T value = (T) classEnumMap.get(key);
			if (value == null) {
				LoggerFactory.getLogger(enumClazz).warn("Received a invalid key '" + String.valueOf(key) + "'!");
				try {
					return (T) DEFAULT_METHOD_MAP.get(enumClazz).invoke(null);
				} catch (Exception e) {
					throw new RuntimeException("Invoke the method '" + DEFAULT_METHOD_MAP.get(enumClazz).toString()
							+ "' failed!", e);
				}
			} else {
				return value;
			}
		}

	}

}
