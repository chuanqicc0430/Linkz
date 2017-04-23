package cn.net.cvtt.lian.common.serialization.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoBuilderCodeGenerator;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoCodeGeneratorException;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoEntityCodeGenerator;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoFieldType;
import cn.net.cvtt.lian.common.serialization.protobuf.util.JavaEval;
import cn.net.cvtt.lian.common.serialization.protobuf.util.JavaEvalException;

/**
 * <b>描述: </b>序列化组件对外暴露的统一序列化接口
 * <p>
 * <b>功能：</b>将Java对象中的数据转换为符合Google
 * protobuf二进制存储协议的二进制流,或从符合该协议的二进制流中解析出Java对象中的数据,用在序列化及反序列化的场景中
 * ,相对于Java原生序列化的优势是可以使用在异构语言的环境中，且速度及效率要高于Java原生的序列化.
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 *  序列化一个String对象
 *  byte[] buffer = ProtoManager.toByteArray("Feinno");
 *  
 *  反序列化一个String对象
 *  String newString = "";
 *  newString = ProtoManager.parseFrom(newString,buffer);
 *  System.out.println(newString);
 *  
 *  序列化一个List对象
 *  List<String> stringList = new ArrayList<String>();
 *  stringList.add("Feinno");
 *  stringList.add("Good");
 *  buffer = ProtoManager.toByteArray(stringList,String.class);
 *  
 *  反序列化一个List对象
 *  List<String> resultList = new ArrayList<String>();
 *  ProtoManager.parseFrom(buffer, resultList, String.class);
 *  System.out.println(resultList.getSize());
 * </pre>
 * 
 * 序列化或反序列化一个混合对象，对象中有1个String和1个int，需要新创建一个类，且继承自{@link ProtoEntity}，请点击参考
 * {@link ProtoEntity}的javadoc
 * <p>
 * <b>支持范围: </b>ProtoManager支持的类型范围为 {@link ProtoFieldType}中标识的除 {@link Object}
 * 外的全部类型,以及这些类型的混合情况
 * 
 * @author 
 * 
 */
public final class ProtoManager {

	/**
	 * 开启此DEBUG后,会有如下变化<br>
	 * 1.生成源码，默认存储在根路径下<br>
	 * 2.在序列化出错时，生成详细的异常信息，打印源码中出错位置的代码
	 */
	private static boolean isDebug = false;

	/** 当开启Debug模式时，源文件默认保存路径 */
	public static String SOURCE_SAVE_PATH = JavaEval.getRootClassPath() + "source/";

	private ProtoManager() {

	}

	/**
	 * 将对象序列化成符合ProtoBuf格式的byte
	 * 
	 * @param args
	 * @return
	 * @throws IOException
	 */
	public static <T extends Object> byte[] toByteArray(T t) throws IOException {
		if (t instanceof ProtoEntity) {
			return ProtoBuilderHelper.getProtoBuilder((ProtoEntity) t).toByteArray();
		} else {
			// 如果当前类型不是ProtoEntity类型，那么需要验证是否在可处理范围内
			if (!checkScope(t.getClass())) {
				throw new RuntimeException("Unsupported " + t.getClass() + " serialization.");
			}
			return ProtoBuilderHelper.getNativeProtoBuilder(t, true).toByteArray();
		}
	}

	/**
	 * 将List序列化成符合ProtoBuf格式的byte
	 * 
	 * @param args
	 * @return
	 * @throws IOException
	 */
	public static <T extends Object> byte[] toByteArray(List<T> args, Class<T> genericsClazz) throws IOException {
		return ProtoBuilderHelper.getNativeProtoBuilder(args, true, genericsClazz).toByteArray();
	}

	/**
	 * 将Map序列化成符合ProtoBuf格式的byte
	 * 
	 * @param args
	 * @return
	 * @throws IOException
	 */
	public static <K extends Object, V extends Object> byte[] toByteArray(Map<K, V> args, Class<K> keyGenericsClazz,
			Class<V> valueGenericsClazz) throws IOException {
		return ProtoBuilderHelper.getNativeProtoBuilder(args, true, keyGenericsClazz, valueGenericsClazz).toByteArray();
	}

	/**
	 * 将对象列化到流中
	 * 
	 * @param t
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public static <T extends Object> void writeTo(T t, OutputStream output) throws IOException {
		if (t instanceof ProtoEntity) {
			ProtoBuilderHelper.getProtoBuilder((ProtoEntity) t).writeTo(output);
		} else {
			// 如果当前类型不是ProtoEntity类型，那么需要验证是否在可处理范围内
			if (!checkScope(t.getClass())) {
				throw new RuntimeException("Unsupported " + t.getClass() + " serialization.");
			}
			ProtoBuilderHelper.getNativeProtoBuilder(t, true).writeTo(output);
		}
	}

	/**
	 * 将对象列化到流中
	 * 
	 * @param t
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public static <T extends Object> void writeTo(OutputStream output, List<T> args, Class<T> genericsClazz)
			throws IOException {
		ProtoBuilderHelper.getNativeProtoBuilder(args, true, genericsClazz).writeTo(output);
	}

	/**
	 * 将对象列化到流中
	 * 
	 * @param t
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public static <K, V> void writeTo(OutputStream output, Map<K, V> args, Class<K> keyGenericsClazz,
			Class<V> valueGenericsClazz) throws IOException {
		ProtoBuilderHelper.getNativeProtoBuilder(args, true, keyGenericsClazz, valueGenericsClazz).writeTo(output);
	}

	/**
	 * 从byte数组中解析出并保存成需要的格式
	 * 
	 * @param buffer
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> T parseFrom(T t, byte[] buffer) throws IOException {
		if (t instanceof ProtoEntity) {
			ProtoEntity protoEntity = (ProtoEntity) t;
			ProtoBuilderHelper.getProtoBuilder(protoEntity).parseFrom(buffer);
			return (T) protoEntity;
		} else {
			// 如果当前类型不是ProtoEntity类型，那么需要验证是否在可处理范围内
			if (!checkScope(t.getClass())) {
				throw new RuntimeException("Unsupported " + t.getClass() + " serialization.");
			}
			ProtoBuilder<?> builder = ProtoBuilderHelper.getNativeProtoBuilder(t, false);
			builder.parseFrom(buffer);
			return ((NativeProtoEntity<T>) builder.getData()).getData();
		}
	}

	/**
	 * 从input中解析出并保存成需要的格式
	 * 
	 * @param buffer
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> T parseFrom(T t, InputStream input) throws IOException {
		if (t instanceof ProtoEntity) {
			ProtoEntity protoEntity = (ProtoEntity) t;
			ProtoBuilderHelper.getProtoBuilder(protoEntity).parseFrom(input);
			return (T) protoEntity;
		} else {
			// 如果当前类型不是ProtoEntity类型，那么需要验证是否在可处理范围内
			if (!checkScope(t.getClass())) {
				throw new RuntimeException("Unsupported " + t.getClass() + " serialization.");
			}
			ProtoBuilder<?> builder = ProtoBuilderHelper.getNativeProtoBuilder(t, false);
			builder.parseFrom(input);
			return ((NativeProtoEntity<T>) builder.getData()).getData();
		}
	}

	/**
	 * 从byte数组中读出List
	 * 
	 * @param buffer
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> List<T> parseFrom(byte[] buffer, List<T> list, Class<T> genericsClazz)
			throws IOException {
		if (list == null) {
			list = new ArrayList<T>();
		}
		ProtoBuilder<?> builder = ProtoBuilderHelper.getNativeProtoBuilder(list, false, genericsClazz);
		builder.parseFrom(buffer);
		return ((NativeProtoEntity<List<T>>) builder.getData()).getData();
	}

	/**
	 * 从byte数组中读出Map
	 * 
	 * @param buffer
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static <K extends Object, V extends Object> Map<K, V> parseFrom(byte[] buffer, Map<K, V> map,
			Class<K> keyGenericsClazz, Class<V> valueGenericsClazz) throws IOException {
		if (map == null) {
			map = new HashMap<K, V>();
		}
		ProtoBuilder<?> builder = ProtoBuilderHelper.getNativeProtoBuilder(map, false, keyGenericsClazz,
				valueGenericsClazz);
		builder.parseFrom(buffer);
		return ((NativeProtoEntity<Map<K, V>>) builder.getData()).getData();
	}

	/**
	 * 获得一个加强版的ProtoEntity，这个ProtoEntity可以记录用户的set字段，并准确的将其序列化
	 * 
	 * @param t
	 * @return
	 */
	public static <T extends ProtoEntity> T getEnhanceProtoEntity(Class<T> t) {
		return ProtoBuilderHelper.getEnhanceProtoEntity(t);
	}

	public static <T extends ProtoEntity> ProtoBuilder<T> getProtoBuilder(T t) {
		return ProtoBuilderHelper.getProtoBuilder(t);
	}

	public static boolean isDebug() {
		return isDebug;
	}

	public static void setDebug(boolean isDebug) {
		ProtoManager.isDebug = isDebug;
	}

	/**
	 * 验证某一类型是否属于序列化范围内
	 * 
	 * @param t
	 * @return
	 */
	public static <T extends Object> boolean checkScope(Class<?> clazz) {
		ProtoFieldType protoFieldType = ProtoFieldType.valueOf(clazz);
		if (protoFieldType != ProtoFieldType.UNKNOWN) {
			return true;
		} else {
			return false;
		}
	}
}

/**
 * 这个类是用于管理与生成ProtoBuilder和ProtoBuilderFactory的类<br>
 * 用户会调用{@link ProtoHelper# getProtoBuilder(T t)}
 * 方法从ProtoHelper中取某一个T对应的ProtoBuilder,
 * ProtoBuilderHelper会从自身的PROTO_BUILDER_FACTORY_MAP中寻找这个ProtoBuilder的Factory
 * ,如果找到了，那么通过这个Factory来创建对应的ProtoBuilder ，如果发现没有这个类对应的Factory ，会调用
 * {@link ProtoBuilderCodeGenerator}
 * 生成ProtoBuilderFactory和ProtoBuilder源码，拿到源码后，此类调用JavaEval的编译源码方法，将源码编译后
 * ，存入PROTO_BUILDER_FACTORY_MAP中，以备下次使用，并将需要的对象返回.
 * 
 * @author 
 * 
 */
class ProtoBuilderHelper {

	private static final Logger logger = LoggerFactory.getLogger(ProtoBuilderHelper.class);

	/** 这里保存了所有继承自ProtoEntity的ProtoBuilderFactory */
	private static final Map<Class<?>, ProtoBuilderFactory> PROTO_BUILDER_FACTORY_MAP = Collections
			.synchronizedMap(new HashMap<Class<?>, ProtoBuilderFactory>());

	/** 这里保存了所有的已经生成过的原始数据类型序列化的包装类 */
	private static final Map<NativeProtoEntityType, NativeProtoEntity<?>> NATIVE_TYPE_PROTO_ENTITY_MAP = Collections
			.synchronizedMap(new HashMap<NativeProtoEntityType, NativeProtoEntity<?>>());

	/** 这个是用于锁住同时只有一个线程能够进行自动构建源码与编译 */
	private static final Object SYNCHRONIZED_OBJECT = new Object();

	/**
	 * 获得一个加强版的{@link ProtoEntity}
	 * ，这个ProtoEntity可以准确的知道某一个Field是否设置，且在序列化时决定是否序列化进去
	 * 
	 * @param t
	 * @return
	 */
	static <T extends ProtoEntity> T getEnhanceProtoEntity(Class<T> t) {
		try {
			return (T) ProtoEntityEnhancer.getEnhanceProtoEntityClass(t).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过待序列化的对象获得其对应的ProtoBuilder，<br>
	 * 获得ProtoBuilder的步骤是首先从ProtoManage自己维护的MAP中取得对应ProtoBuilder的Factory，
	 * 通过这个Factory来创建ProtoBuilder，如果这个Factory不存在，则需要生成这个Factory和Builder
	 * 
	 * @param t
	 * @return
	 */
	static <T extends ProtoEntity> ProtoBuilder<T> getProtoBuilder(T t) {
		String protoBuilderFactory = t.getClass().getName() + "ProtoBuilderFactory";
		ProtoBuilderFactory factory = PROTO_BUILDER_FACTORY_MAP.get(t.getClass());
		if (factory == null) {
			logger.info("Not found [{}] ProtoBuilder and ProtoBuilderFactory. Ready to automatically generated.", t
					.getClass().getName());
			// 好吧，这个待序列化对象的类型没有找到对应的BuilderFactory，那么在这里在构建一个BuilderFactory，构建的过程就是代码自动生成与编译的过程
			synchronized (SYNCHRONIZED_OBJECT) {
				long startTime = System.nanoTime();
				if (PROTO_BUILDER_FACTORY_MAP.get(t.getClass()) == null) {
					/*
					try {
						factory = (ProtoBuilderFactory) t.getClass()
								.getClassLoader()
								.loadClass(protoBuilderFactory).newInstance();
						if (factory != null) {
							PROTO_BUILDER_FACTORY_MAP
									.put(t.getClass(), factory);
							return factory.newProtoBuilder(t);
						}
					} catch (Exception ex) {
						logger.error("", ex);
					}
					*/
					// 构建一个需要的对象存入Map中，并将这个对象返回
					factory = buildFactory(t);
					PROTO_BUILDER_FACTORY_MAP.put(t.getClass(), factory);
					if (logger.isInfoEnabled()) {
						logger.info("The end of the generated  ProtoBuilder and ProtoBuilderFactory source code.", t
								.getClass().getName());
						logger.info("Create {}ProtoBuilder.class with {} ms.", t.getClass().getSimpleName(),
								java.util.concurrent.TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime,
										java.util.concurrent.TimeUnit.NANOSECONDS));
					}
				} else {
					// 如果阻塞过后已经存在了这个对象，则直接将引用赋予到factory变量上，用于后面的创建ProtoBuilder
					factory = PROTO_BUILDER_FACTORY_MAP.get(t.getClass());
					logger.info(
							"OK, Found [{}] ProtoBuilder and ProtoBuilderFactory. Do not need to automatically generated.",
							t.getClass().getName());
				}
			}
		}
		return factory.newProtoBuilder(t);
	}

	/**
	 * 用于生成原生类型的序列化辅助类，是指可以直接将List、Map、数组以及基本类型直接序列化的辅助类的生成方法
	 * 
	 * @param <T>
	 * @param t
	 * @param isInit
	 *            是否将t这个对象赋予当前ProtoBuilder中
	 * @param genericsClazz
	 * @return
	 */
	static <T> ProtoBuilder<?> getNativeProtoBuilder(T t, boolean isInit, Class<?>... genericsClazz) {
		NativeProtoEntityType nativeProtoEntityType = new NativeProtoEntityType(t.getClass(), genericsClazz);
		NativeProtoEntity<T> nativeProtoEntity = (NativeProtoEntity<T>) NATIVE_TYPE_PROTO_ENTITY_MAP
				.get(nativeProtoEntityType);
		if (nativeProtoEntity == null) {
			logger.info("Not found [{}] NativeProtoBuilder and ProtoBuilderFactory. Ready to automatically generated.",
					t.getClass().getName());
			synchronized (SYNCHRONIZED_OBJECT) {
				long startTime = System.nanoTime();
				if (NATIVE_TYPE_PROTO_ENTITY_MAP.get(nativeProtoEntityType) == null) {
					try {
						String protoEntityCode = ProtoEntityCodeGenerator.getProtoEntityBeanCode(t, genericsClazz);
						nativeProtoEntity = (NativeProtoEntity<T>) JavaEval.eval(t.getClass(), protoEntityCode);
						NATIVE_TYPE_PROTO_ENTITY_MAP.put(nativeProtoEntityType, nativeProtoEntity);
						if (logger.isInfoEnabled()) {
							String beanName = ProtoEntityCodeGenerator.getProtoEntityBeanFullName(t, genericsClazz);
							logger.info(
									"The end of the generated NativeProtoEntity NativeProtoBuilder and ProtoBuilderFactory source code.",
									t.getClass().getName());
							logger.info("Create [{}] with {} ms.", beanName, java.util.concurrent.TimeUnit.MILLISECONDS
									.convert(System.nanoTime() - startTime, java.util.concurrent.TimeUnit.NANOSECONDS));
						}
					} catch (Exception e) {
						logger.error(t.getClass().getName() + " generate NativeProtoBuilder error.", e);
					}
				} else {
					// 如果阻塞过后已经存在了这个对象，则直接将引用赋予到factory变量上，用于后面的创建ProtoBuilder
					nativeProtoEntity = (NativeProtoEntity<T>) NATIVE_TYPE_PROTO_ENTITY_MAP.get(nativeProtoEntityType);
					logger.info(
							"OK, Found [{}] NativeProtoBuilder and ProtoBuilderFactory. Do not need to automatically generated.",
							t.getClass().getName());
				}
			}
		}
		// 下面代码是通过上面生成的原始对象类型的ProtoEntity再寻找对应的序列化辅助类
		nativeProtoEntity = nativeProtoEntity.newInstance();
		if (isInit) {
			nativeProtoEntity.setData(t);
		}
		return getProtoBuilder(nativeProtoEntity);
	}

	/**
	 * 构建一个指定JavaBean文件类型的ProtoBuilderFactory类
	 * 
	 * @param t
	 * @return
	 */
	private static <T> ProtoBuilderFactory buildFactory(T t) {
		try {
			/** Step 1.首先准备待编译的源码列表，如果待处理的类中存在其他需要动态编译的类型，则一并合入本次待编译列表中 */
			List<String> sourceList = new ArrayList<String>();

			/** Step 2.第二步将这个编译列表交给源码构建类去构建源码，构建后的源码会填充到这个待编译的源码列表中 */
			Class<?> clazz = t.getClass();
			while (EnhanceProtoEntity.class.isAssignableFrom(clazz)) {
				clazz = t.getClass().getSuperclass();
			}

			protoToSource(sourceList, clazz);

			/** Step 3.第三步将源码列表交给JavaEval进行动态编译 */
			// 编译源文件
			if (sourceList.size() > 0) {
				String[] sourceArrays = new String[sourceList.size()];
				JavaEval.compile(clazz.getClassLoader(), sourceList.toArray(sourceArrays));
			}
			// 编译并且返回对应的ProtoBuilderFactory实例
			return JavaEval.newClassInstance(clazz.getClassLoader(), ProtoBuilderFactory.class,
					ProtoBuilderCodeGenerator.getBuilderFactoryClassFullName(clazz));
		} catch (JavaEvalException e) {
			logger.error(String.format("%s create failed in JavaEval,details:", t.getClass().getName()), e);
			return new NullProtoBuilderFactory<T>(e);
		} catch (ProtoCodeGeneratorException e) {
			logger.error(String.format("%s create failed in ProtoCodeGenerator,details:", t.getClass().getName()), e);
			return new NullProtoBuilderFactory<T>(e);
		}
	}

	/**
	 * 这个类负责解析JavaBean类型文件，根据这个类型文件，来生成指定的源码，并将源码保存于sourceList中
	 * 
	 * @param sourceList
	 * @param clazz
	 */
	private static void protoToSource(List<String> sourceList, Class<?> clazz) throws ProtoCodeGeneratorException {

		/**
		 * Step 1.创建代码生成器
		 */
		ProtoBuilderCodeGenerator generator = new ProtoBuilderCodeGenerator(clazz);

		/** Step 2.判断这个类是否生成过工厂类，如果生成过，代表它已经动态编译过了，此时不需要再次动态编译了 */
		if (generator.containsGeneratedClass(clazz)) {
			// 如果存在这个类了，则直接返回，不需要再将他放入编译列表
			return;
		}

		/**
		 * Step 3.使用代码生成器生成针对指定实体类型的ProtoBuilder和ProtoBuilderFactory，
		 * 这个方法会发现这个实体类型中的嵌套的子待序列化实体类型
		 * ，它会自动递归创建它们的Builder和BuilderFactory，直到将需要编译的的全部代码生成完毕才停止
		 * ，当然，如果嵌套的子实体类型已经存在它们对应的Builder和BuilderFactory时，这个生成器也不会再次创建
		 */
		List<String> list = generator.getProtoCodeList();
		if (list != null) {
			sourceList.addAll(list);
		}
	}
}

/**
 * 
 * <b>描述: </b>这个类是用于区分原始类型的ProtoEntity的标识类
 * <p>
 * <b>功能: </b>
 * <p>
 * <b>用法: </b>
 * <p>
 * 
 * @author 
 * 
 */
class NativeProtoEntityType {

	private Class<?> clazz;
	private Class<?>[] genericsClazz;

	public NativeProtoEntityType(Class<?> clazz, Class<?>... genericsClazz) {
		this.clazz = clazz;
		this.genericsClazz = genericsClazz;
	}

	@Override
	public int hashCode() {
		int value = 17 * clazz.hashCode();
		for (Class<?> clazzTemp : genericsClazz) {
			value += 17 * clazzTemp.hashCode();
		}
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NativeProtoEntityType)) {
			return false;
		}
		NativeProtoEntityType nativeProtoEntityType = (NativeProtoEntityType) obj;
		if (clazz != null && nativeProtoEntityType.clazz != null) {
			if (!clazz.equals(nativeProtoEntityType.clazz)) {
				return false;
			}
		} else if (clazz != nativeProtoEntityType.clazz) {
			return false;
		}
		if (genericsClazz != null && nativeProtoEntityType.genericsClazz != null) {
			if (genericsClazz.length == nativeProtoEntityType.genericsClazz.length) {
				for (int i = 0; i < genericsClazz.length; i++) {
					if (!genericsClazz[i].equals(nativeProtoEntityType.genericsClazz[i])) {
						return false;
					}
				}
			} else {
				// 加入了如果泛型长度不匹配返回false的逻辑
				// 增加此处是为了可读性，因为此时class已经确定了，泛型长度肯定相同
				return false;
			}
		} else if (genericsClazz != nativeProtoEntityType.genericsClazz) {
			return false;
		}
		return true;
	}
}
