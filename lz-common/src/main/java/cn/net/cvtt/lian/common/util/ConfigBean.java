package cn.net.cvtt.lian.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <b>描述: </b>这是一个可以将Properties或XML文件转为Java对象的工具类，转换后的Java对象会是一个
 * {@link ConfigBean}
 * 类型的Java实体类，此类推荐继承使用，因为继承后，如果类中有和Properties名字相同的字段，那么会自动反射将Properties内容写入该字段，
 * 包括Map，详情参见示例 甚至可以实现级联的功能<br>
 * <p>
 * <b>功能: </b>将Properties或XML文件转为Java对象的工具类，在继承时可以实现Properties与
 * {@link ConfigBean} 类中同名字段的赋值以及级联 子类同名字段的反射
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * 假设有一个Properties文件如下
 * Properties props = new Properties();
 * props.setProperty("libraryName", "Feinno Library");
 * props.setProperty("books{0}.name", "Feinno1");
 * props.setProperty("books{0}.ISBN", "1XXXXXX");
 * props.setProperty("books{0}.author", "SanMao");
 * props.setProperty("books{1}.name", "Feinno2");
 * props.setProperty("books{1}.ISBN", "2XXXXXX");
 * props.setProperty("books{1}.author", "WangMazi");
 * props.setProperty("librarianInfo.name", "ZhangSanfeng");
 * props.setProperty("librarianInfo.sex", "male");
 * props.setProperty("librarianInfo.age", "108");
 * props.setProperty("user{ZhangSan}.age", "19");
 * props.setProperty("user{ZhaoSi}.age", "18");
 * 
 * 有两个Java类如下
 * public class Library extends ConfigBean{
 *   	private String libraryName;
 *    private Map&lt;Integer,BookInfo&gt; books = new HashMap&lt;Integer,BookInfo&gt;();
 *    Getter and Setter ...
 *   }
 *   
 *   public class BookInfo extends ConfigBean{
 *   	private String name;
 *    private String ISBN;
 *    Getter and Setter ...
 *   }
 *   
 * 将Properties信息读入Library中使用如下方法
 * Library library = ConfigBean.valueOf(props, Library.class);
 * Assert.assertEquals(library.getLibraryName(), "Feinno Library");
 * Assert.assertEquals(library.getBooks().size(), 2);
 * Assert.assertEquals(library.getBooks().get(0).getName(), "Feinno1");
 * Assert.assertEquals(library.getBooks().get(0).getISBN(), "1XXXXXX");
 * Assert.assertEquals(library.getBooks().get(1).getName(), "Feinno2");
 * Assert.assertEquals(library.getBooks().get(1).getISBN(), "2XXXXXX");
 *   
 * 针对Properties上有而Java实体类中没有的字段，可以采用{@link ConfigBean#getFieldValue(String)}来取值
 * Assert.assertEquals(library.getBooks().get(0).getFieldValue("author"), "SanMao");
 * Assert.assertEquals(library.getBooks().get(1).getFieldValue("author"), "WangMazi");
 * 
 * 如果Properties上有一个子节点，而Java实体类中没有，那么采用如下方式
 * Assert.assertEquals(library.getChild("librarianInfo").getFieldValue("name"), "ZhangSanfeng");
 * Assert.assertEquals(library.getChild("librarianInfo").getFieldValue("sex"), "male");
 * Assert.assertEquals(library.getChild("librarianInfo").getFieldValue("age"), "108");
 *   
 * 演示多层次取值
 * Assert.assertEquals(library.getChild("user").getChild("ZhangSan").getFieldValue("age"), "19");
 * Assert.assertEquals(library.getChild("user").getChild("ZhaoSi").getFieldValue("age"), "18");
 *   
 * XML格式文件的取值方法相同，上面的Properties翻译成XML为如下结果:
 *  &lt;xml&gt;
 * 	&lt;libraryName&gt;Feinno Library&lt;/libraryName&gt;
 * 	&lt;librarianInfo&gt;
 * 		&lt;name&gt;ZhangSanfeng&lt;/name&gt;
 * 		&lt;age&gt;male&lt;/age&gt;
 * 		&lt;sex&gt;108&lt;/sex&gt;
 * 	&lt;/librarianInfo&gt;
 * 	&lt;books isMultiple="true" key="0"&gt;
 * 		&lt;name&gt;Feinno1&lt;/name&gt;
 * 		&lt;ISBN&gt;1XXXXXX&lt;/ISBN&gt;
 * 		&lt;author&gt;SanMao&lt;/author&gt;
 * 	&lt;/books&gt;
 * 	&lt;books isMultiple="true" key="1"&gt;
 * 		&lt;name&gt;Feinno2&lt;/name&gt;
 * 		&lt;ISBN&gt;2XXXXXX&lt;/ISBN&gt;
 * 		&lt;author&gt;WangMazi&lt;/author&gt;
 * 	&lt;/books&gt;
 * 	&lt;user isMultiple="true" key="ZhangSan" age="19"/&gt;
 * 	&lt;user isMultiple="true" key="ZhaoSi" age="18"/&gt;
 * &lt;/xml&gt;
 * 
 * </pre>
 * 
 * 与{@link ConfigBean}相关的还有{@link ConfigPath}注解，该注解用于定义当前{@link ConfigBean}
 * 的起始路径，所谓其实路径，就是例如<code>librarianInfo.name</code>，如果{@link ConfigBean}的子类中不加入
 * {@link ConfigPath}注解，则默认的起始根路径是从root开始，如果加入了{@link ConfigPath}注解，且注解
 * {@link ConfigPath#value()}为librarianInfo，那么代表从librarianInfo开始，因此
 * {@link ConfigBean}只会解析librarianInfo后面的name，用法详见{@link ConfigPath}
 * <p>
 * 
 * @author 
 * @see ConfigPath
 */
public class ConfigBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigBean.class);

	private Properties property;
	private Map<String, ConfigBean> childMap = new HashMap<String, ConfigBean>();
	private Map<String, String> fieldMap = new HashMap<String, String>();

	/**
	 * 默认构造方法
	 */
	public ConfigBean() {
	}

	/**
	 * 获得资源文件中的某一个KEY对应的VALUE
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return property.getProperty(key);
	}

	/**
	 * 获得资源文件中的某一个KEY对应的VALUE,如果VALUE不存在，则使用defaultValue
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getProperty(String key, String defaultValue) {
		return property.getProperty(key, defaultValue);
	}

	/**
	 * 返回ConfigBean所代表的资源文件
	 * 
	 * @return
	 */
	public Properties getProperties() {
		return property;
	}

	/**
	 * 通过KEY获得本节点下的某一个子ConfigBean节点
	 * 
	 * @param key
	 * @return
	 */
	public ConfigBean getChild(String key) {
		return childMap.get(key);
	}

	/**
	 * 通过KEY获得本节点下的某一个值
	 * 
	 * @param key
	 * @return
	 */
	public String getFieldValue(String key) {
		return fieldMap.get(key);
	}

	/**
	 * 返回本节点下存储的键迭代器
	 * 
	 * @return
	 */
	public Iterable<String> fieldKeySet() {
		return fieldMap.keySet();
	}

	/**
	 * 返回本节点下存储的值迭代器
	 * 
	 * @return
	 */
	public Iterable<String> fieldValues() {
		return fieldMap.values();
	}

	/**
	 * 返回本节点下子节点的键迭代器
	 * 
	 * @return
	 */
	public Iterable<String> childKeySet() {
		return childMap.keySet();
	}

	/**
	 * 返回本节点下子节点的值迭代器
	 * 
	 * @return
	 */
	public Iterable<ConfigBean> childValues() {
		return childMap.values();
	}

	/**
	 * 将Properties中的内容转成当前内容
	 * @param props
	 */
	public void valueOf(Properties props) {
		ConfigBeanHelper configHelper = new ConfigBeanHelper();
		this.setProperty(props);
		configHelper.build(props, this);
	}

	/**
	 * 将资源文件Properties的值转换成某一个ConfigBean或其子类来存储
	 * 
	 * @param clazz
	 * @param props
	 * @return
	 */
	public static <T extends ConfigBean> T valueOf(Properties props, Class<T> clazz) {
		ConfigBeanHelper configHelper = new ConfigBeanHelper();
		try {
			T t = clazz.newInstance();
			t.setProperty(props);
			return configHelper.build(props, t);
		} catch (InstantiationException e) {
			LOGGER.error("ConfigBean valueOf Error,ConfigBean class is {}:", clazz, e);
		} catch (IllegalAccessException e) {
			LOGGER.error("ConfigBean valueOf Error,ConfigBean class is {}:", clazz, e);
		}
		return null;
	}

	public String toString() {
		return String.format("field:%s child:%s", fieldMap, childMap);
	}

	void setChild(String key, ConfigBean value) {
		childMap.put(key, value);
	}

	void setField(String key, String value) {
		fieldMap.put(key, value);
	}

	void setProperty(Properties property) {
		this.property = property;
	}
}

/**
 * 这是一个配合ConfigBean使用的工具类
 * 
 * @author Lv.Mingwei
 * 
 */
class ConfigBeanHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigBeanHelper.class);

	/**
	 * 这是一个获取路径轨迹的正则表达式，例如a.b.c{com.feinno.test}.d，那么轨迹应该为a->b->c{com.feinno.
	 * test}->d<br>
	 * 下面定义的正则分为两部分:<br>
	 * 第一部分为至少存在一个字符，且这个字符不能是"."点号或大括号 ,因为点号作为分割使用，大括号也是我们自定义的一个用于MAP的关键字,公式：
	 * <code>[^\\.\\{]+</code><br>
	 * 第二部分是最多只能存在一个大括号"{}"，大括号中的字符可以是多个，但是不能又是一个大括号，也就是大括号中不允许再次嵌套大括号,公式:
	 * <code>(\\{[^\\{\\}]+\\})?</code><br>
	 */
	private static final String NODE_LOCUS_REGEX = "[^\\.\\{]+(\\{[^\\{\\}]+\\})?";

	/**
	 * 使用上面的正则公式创建出一个正则表达式的工具类
	 */
	private static final Pattern pattern = Pattern.compile(NODE_LOCUS_REGEX);

	// 这是一个获取路径轨迹的方法，例如路径为a.b.c{com.feinno.test}.d，那么轨迹应该为a->b->c{com.feinno.test}->d的数组
	public static String[] getNodeLocus(String path) {
		Matcher matcher = pattern.matcher(path);
		ArrayList<String> matchList = new ArrayList<String>();
		while (matcher.find()) {
			matchList.add(matcher.group());
		}
		String[] result = new String[matchList.size()];
		matchList.toArray(result);
		return result;
	}

	/**
	 * 构建一个configBean
	 * 
	 * @param props
	 * @param rootConfigBean
	 * @return
	 */
	public <T extends ConfigBean> T build(Properties props, T rootConfigBean) {
		// Step1.第一步，将资源文件的键值对转换成以Node对象存储的List，Node中用数组保存了某一条信息所位于的对象位置
		List<ConfigBeanNode> nodeList = propertiesToNodeList(props);
		// Step2.第二步，将创建好的nodeList传给buildRootConfigBean由它来递归的创建具有树状目录结构的ConfigBean
		buildRootConfigBean(nodeList, rootConfigBean);
		return rootConfigBean;
	}

	/**
	 * 将资源文件的键值对转换成以Node对象存储的List，NodeList是一个有序的排列着每一个节点的集合
	 * 
	 * @param props
	 * @return
	 */
	private List<ConfigBeanNode> propertiesToNodeList(Properties props) {
		List<ConfigBeanNode> nodeList = new ArrayList<ConfigBeanNode>();
		for (Object obj : props.keySet()) {
			nodeList.add(new ConfigBeanNode(obj.toString(), props.get(obj).toString()));
		}
		// 排序是为了提升遍历时的处理速度
		Collections.sort(nodeList);
		return nodeList;
	}

	/**
	 * 构建ConfigBean的根节点，根节点就是由无数个子节点来形成的，因此通过循环每一个节点，将每一个子节点添加到根节点上
	 * 
	 * @param nodeList
	 * @param configBean
	 */
	private void buildRootConfigBean(List<ConfigBeanNode> nodeList, ConfigBean configBean) {
		// 取的configBean的起始路径
		ConfigPath configPath = configBean.getClass().getAnnotation(ConfigPath.class);
		String startPath = configPath != null ? configPath.value() : "";
		// 根据起始路径过滤掉不相符的nodeList
		nodeList = filterNodeListByPath(nodeList, startPath);
		// 对所有节点进行逐个遍历
		for (ConfigBeanNode node : nodeList) {
			// 构建每一个子节点
			buildNodeConfigBean(node, configBean);
		}
	}

	/**
	 * 过滤掉其它路径下的节点
	 * 
	 * @param nodeList
	 * @param path
	 * @return
	 */
	private List<ConfigBeanNode> filterNodeListByPath(List<ConfigBeanNode> nodeList, String path) {
		List<ConfigBeanNode> resultList = new ArrayList<ConfigBeanNode>();
		if (path.length() > 0) {// 如果有需要过滤的,则在过滤的路径后面加个"."点，目的是为了增加匹配时的准确性
			path += ".";
		}
		for (ConfigBeanNode node : nodeList) {
			if (node.getPath().startsWith(path)) {
				// 需要重新组装，把当前节点提到根节点上来
				resultList.add(new ConfigBeanNode(node.getPath().substring(path.length()), node.getValue()));
			}
		}
		return resultList;
	}

	/**
	 * 该方法负责构建某一个子节点
	 * 
	 * @param node
	 * @param configBean
	 */
	private void buildNodeConfigBean(ConfigBeanNode node, ConfigBean configBean) {
		ConfigBean configBeanTemp = configBean;
		try {
			// 从第一层开始循环节点的轨迹，一直循环到倒数第二个节点上
			for (int i = 0; i < node.getLocus().length - 1; i++) {
				// 当到达某一层节点时，需要判断该层节点是否为特殊类型，也就是为Map时，节点名称名后面会跟随大括号{},例如subLevel{com.feinno.test}
				String[] fieldNameAndKey = node.getLocus()[i].split("[\\{\\}]");// 将以大括号作为区分，将节点名称分割开
				// 获得configBeanTemp对象以fieldName命名的子对象，因为这里位于循环，所以会产生递归效果，一直递归到Node的最后一个对象
				configBeanTemp = getObject(configBeanTemp, fieldNameAndKey[0],
						fieldNameAndKey.length > 1 ? fieldNameAndKey[1] : null, null);
			}
			// 拿到Node的最后一个对象后，对这个对象进行赋值
			if (configBeanTemp != null) {
				String fieldName = node.getLocus()[node.getLocus().length - 1];
				// 需要判断该层节点是否为特殊类型，也就是为Map时，节点名称名后面会跟随大括号{},例如subLevel{com.feinno.test}
				String[] fieldNameAndKey = fieldName.split("[\\{\\}]");// 将以大括号作为区分，将节点名称分割开
				if (fieldNameAndKey != null && fieldNameAndKey.length > 1) {
					// 获得configBeanTemp对象以fieldName命名的子对象，因为这里位于循环，所以会产生递归效果，一直递归到Node的最后一个对象
					configBeanTemp = getObject(configBeanTemp, fieldNameAndKey[0], fieldNameAndKey[1], node.getValue());
					fieldName = fieldNameAndKey[1];
				}
				if (configBeanTemp != null) {
					setValue(configBeanTemp, fieldName, node.getValue());
				}
			}

		} catch (Exception e) {
			LOGGER.error("ConfigBean.ConfigHelper.buildNodeConfigBean found error,node is {}", node, e);
		}
	}

	/**
	 * 获得configBean中以fieldName命名的子对象，这个方法是类中最重要的，因此注释会多一些
	 * 
	 * @param configBean
	 * @param fieldName
	 * @param mapKey
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	private ConfigBean getObject(ConfigBean configBean, String fieldName, String mapKey, String mapValue)
			throws IllegalAccessException {
		// 根据名称从当前的对象中取出Field
		Field field = getField(configBean, fieldName);

		// 取出configBean中这个Field字段的对象
		Object object = getObjectByField(configBean, field, fieldName);

		if (object instanceof ConfigBean) {// 如果字段类型是一个ConfigBean类型
			if (mapKey == null) {// 当mapKey为空时，代表本次是一个普通的ConfigBean类型,而非MAP,所以直接返回既可
				return (ConfigBean) object;
			} else {// 如果这次想取出的是一个MAP中的某一个Key，在通过fieldName取出MAP后，还需要通过mapKey再取出Key,所以还需要再取一次
				if (mapValue != null) {
					// 如果当前是想对MAP赋值，那么直接赋值返回空既可了
					((ConfigBean) object).setField(mapKey, mapValue);
					return null;
				} else {
					return getObject((ConfigBean) object, mapKey, null, mapValue);
				}

			}
		} else if (object instanceof Map) { // 如果我们通过feildName取出了一个Map类型，这种情况一般发生在用户自己继承实现的ConfigBean上有一个MAP字段，我们此时把这个MAP字段取出
			if (mapKey == null) {// 如果取出了MAP类型，我们要求MAP类型必须指定一个KEY值，也就是大括号中的内容{xxx}
				return null;
			} else {// 现在我们假设用户给出了大括号中的内容,也就是key
				if (((Map) object).get(mapKey) != null) {
					Object mapValueObj = ((Map) object).get(mapKey);
					// 如果从这个MAP中取到了这个主键的值是一个ConfigBean的类型，则直接给用户返回
					if (ConfigBean.class.isAssignableFrom(mapValueObj.getClass())) {
						return (ConfigBean) ((Map) object).get(mapKey);
					} else {
						// 如果不是一个ConfigBean类型，并且这个map有value值，则直接赋值
						if (mapValue != null) {
							((Map) object).put(mapKey, newInstance(mapValueObj.getClass(), mapValue));
						}
						return null;
					}
				} else {// 如果这个MAP中没有值，是一个空的MAP，那么我们创造一个空值返回
					// 通过field取出MAP中value的泛型类型，如果field为空，则使用默认的ConfigBean.class,否则使用feild中取出的泛型类型
					Class fieldArgType = field != null ? GenericsUtils.getFieldGenericType(field, 1) : ConfigBean.class;
					// 取得field的准确类型，如果是ConfigBean类型，则返回，否则不返回，直接向MAP中赋值
					if (ConfigBean.class.isAssignableFrom(fieldArgType)) {
						((Map) object).put(mapKey, newObject(fieldArgType));// 创建了一个MAP想要的类型对象，将这个空对象放到取出的这个MAP中
						return (ConfigBean) ((Map) object).get(mapKey);// 把这个空对象返回出去，返回到外面状态信息
					} else {
						((Map) object).put(mapKey, newInstance(fieldArgType, mapValue));
						return null;
					}
				}

			}
		} else {// 如果取出configBean中这个Field字段的object对象没有或者为空，那么我们为了创建完整的级联轨迹，需要给创建上这些对象
			if (mapKey == null) {// 当MAP的关键字为空时，以ConfigBean的普通类型进行创建
				// 通过field取出MAP中value的泛型类型，如果field为空，则使用默认的ConfigBean.class,否则使用feild中取出的泛型类型
				Class fieldArgType = field != null ? field.getType() : ConfigBean.class;
				// 添加以fieldName名称命名的configBean对象feild到他的上一层configBean中去，并且把创建的以fieldName名称命名的configBean对象返回出来
				ConfigBean configBeanTemp = addField(configBean, field, fieldName, fieldArgType);
				return configBeanTemp;
			} else {// MAP的关键字不为空，也就是说有关键字，则类型肯定为MAP类型，而此时这个对象又为空，所以我们要自己创建一个MAP
				// 通过field取出MAP中value的泛型类型，如果field为空，则使用默认的ConfigBean.class,否则使用feild中取出的泛型类型
				Class fieldArgType = field != null ? GenericsUtils.getFieldGenericType(field, 1) : ConfigBean.class;

				if (field != null) {// 如果field不为空，则创建一个MAP，把这个MAP通过这个feild放入到他的父亲configBean的怀抱中
					Map map = new HashMap();
					field.set(configBean, map);
					if (mapValue != null) {
						map.put(mapKey, newInstance(fieldArgType, mapValue));
						return null;
					} else {
						map.put(mapKey, newObject(fieldArgType));
						return (ConfigBean) map.get(mapKey);
					}
				} else {// 如果field为空,则他是一个收养儿,需要放到configBean统一的childMap中
					// 创建一个child来模拟MAP
					ConfigBean configBeanTemp = addField(configBean, field, fieldName, fieldArgType);
					if (mapValue != null) {
						// 如果有值,则直接写入了
						setValue(configBeanTemp, mapKey, mapValue);
						return null;
					} else {
						// 向上一步创建的child中放入这个键
						ConfigBean configBeanTemp2 = addField(configBeanTemp, field, mapKey, fieldArgType);
						return configBeanTemp2;
					}
				}
			}
		}
	}

	/**
	 * 设置对象
	 * 
	 * @param configBean
	 * @param fieldName
	 * @param value
	 * @throws IllegalAccessException
	 */
	private void setValue(ConfigBean configBean, String fieldName, String value) throws IllegalAccessException {
		ConfigUnit configUnit = null;
		Field field = getField(configBean, fieldName);

		if (field != null) {// 如果有这个字段，那么找到这个字段的确切类型
			field.setAccessible(true);
			if (field.getType().getName().equals("java.lang.String")) {
				configUnit = ConfigUnit.STRING;
			} else if (field.getType().getName().equals("int")) {
				configUnit = ConfigUnit.INT;
			} else if (field.getType().getName().equals("long")) {
				configUnit = ConfigUnit.LONG;
			} else if (field.getType().getName().equals("float")) {
				configUnit = ConfigUnit.FLOAT;
			} else if (field.getType().getName().equals("double")) {
				configUnit = ConfigUnit.DOUBLE;
			} else if (field.getType().getName().equals("boolean")) {
				configUnit = ConfigUnit.BOOLEAN;
			} else if (field.getType().getName().equals("byte")) {
				configUnit = ConfigUnit.BYTE;
			} else if (field.getType().getName().equals("short")) {
				configUnit = ConfigUnit.SHORT;
			} else if (field.getType().isEnum()) {
				configUnit = ConfigUnit.ENUM;
			} else {
				configUnit = ConfigUnit.OBJECT;
			}
			// 找到确切类型后，进行向configBean对象的field字段赋予value值
			configUnit.setValue(configBean, field, value);
		} else {// 如果没有这个字段,那么就把他存在fieldMap中，那么他的类型为String
			configUnit = ConfigUnit.STRING;
			// 向configBean对象中放入Key为fieldName,v值为value
			configUnit.setValue(configBean, fieldName, value);
		}
	}

	/**
	 * 根据名称从object对象中取出Field,因为允许object中布存在这个Field，所以异常均被忽略
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	private Field getField(Object object, String fieldName) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			return field;
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
		}
		return null;
	}

	/**
	 * 取出configBean中这个Field字段的对象,如果没有去到，则通过fieldName在configBean提供的默认child上寻找，
	 * 并将结果返回
	 * 
	 * @param configBean
	 * @param field
	 * @param fieldName
	 * @return
	 */
	private Object getObjectByField(ConfigBean configBean, Field field, String fieldName) {
		Object object = null;

		try {
			if (field != null) {
				field.setAccessible(true);
				object = field.get(configBean);
			}
		} catch (IllegalArgumentException e) {
			LOGGER.error(
					"ConfigBean.ConfigHelper.getObjectByField found error,field is {} ,field Name is " + fieldName,
					field, e);
		} catch (IllegalAccessException e) {
			LOGGER.error(
					"ConfigBean.ConfigHelper.getObjectByField found error,field is {} ,field Name is " + fieldName,
					field, e);
		}
		// 如果在field上没有找到对象，那么到ConfigBean的child上碰碰运气
		if (object == null) {
			return configBean.getChild(fieldName);
		}

		return object;
	}

	/**
	 * 将一个类对象放入到ConfigBean的指定字段Field中，如果这个Field不存在，则放到ConfigBean的childMap中
	 * 
	 * @param configBean
	 * @param field
	 * @param fieldName
	 * @param clazz
	 * @return
	 */
	private <T extends ConfigBean> T addField(ConfigBean configBean, Field field, String fieldName, Class<T> clazz) {
		try {
			T configBeanTemp = newObject(clazz);
			if (field != null) {
				field.setAccessible(true);
				field.set(configBean, configBeanTemp);

			} else {
				configBean.setChild(fieldName, configBeanTemp);
			}
			return configBeanTemp;
		} catch (IllegalArgumentException e) {
			LOGGER.error("ConfigBean.ConfigHelper.addField found error,class is {},field is " + field
					+ " ,field Name is " + fieldName, clazz.toString(), e);
			return null;
		} catch (IllegalAccessException e) {
			LOGGER.error("ConfigBean.ConfigHelper.addField found error,class is {},field is " + field
					+ " ,field Name is " + fieldName, clazz.toString(), e);
			return null;
		}
	}

	/**
	 * 创建一个对象的实例，创建时允许失败
	 * 
	 * @param clazz
	 * @return
	 */
	private <T> T newObject(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (SecurityException e) {
			LOGGER.error("ConfigBean.ConfigHelper.newObject found error,class is {}", clazz.toString(), e);
		} catch (IllegalAccessException e) {
			LOGGER.error("ConfigBean.ConfigHelper.newObject found error,class is {}", clazz.toString(), e);
		} catch (InstantiationException e) {
			LOGGER.error("ConfigBean.ConfigHelper.newObject found error,class is {}", clazz.toString(), e);
		}
		return null;
	}

	/**
	 * 创建一个对象的实例，创建时允许失败
	 * 
	 * @param clazz
	 * @return
	 */
	private Object newInstance(Class<?> clazz, String value) {
		try {
			if (clazz.getName().equals("java.lang.String")) {
				return value;
			} else if (clazz.equals(Integer.class)) {
				return Integer.valueOf(value);
			} else if (clazz.equals(Long.class)) {
				return Long.valueOf(value);
			} else if (clazz.equals(Float.class)) {
				return Float.valueOf(value);
			} else if (clazz.equals(Double.class)) {
				return Double.valueOf(value);
			} else if (clazz.equals(Boolean.class)) {
				return Boolean.valueOf(value);
			} else if (clazz.equals(Byte.class)) {
				return Byte.valueOf(value);
			} else if (clazz.equals(Short.class)) {
				return Short.valueOf(value);
			} else if (clazz.isEnum()) {
				return clazz.getDeclaredMethod("getEnumVal", String.class).invoke(null, value);
			} else {
				return clazz.newInstance();
			}
		} catch (Exception e) {
			LOGGER.error("ConfigBean.ConfigHelper.newObject found error,class is {}", clazz.toString(), e);
		}
		return null;
	}

}

enum ConfigUnit {

	STRING {
		@Override
		public void setValue(ConfigBean configBean, Field field, String value) throws IllegalAccessException {
			field.set(configBean, value);
		}

		@Override
		public void setValue(ConfigBean configBean, String key, String value) throws IllegalAccessException {
			configBean.setField(key, value);
		}
	},
	INT {
		@Override
		public void setValue(ConfigBean configBean, Field field, String value) throws IllegalAccessException {
			field.setInt(configBean, Integer.parseInt(value));
		}

		@Override
		public void setValue(ConfigBean configBean, String key, String value) throws IllegalAccessException {
			configBean.setField(key, value);
		}
	},
	LONG {
		@Override
		public void setValue(ConfigBean configBean, Field field, String value) throws IllegalAccessException {
			field.setLong(configBean, Long.valueOf(value));
		}

		@Override
		public void setValue(ConfigBean configBean, String key, String value) throws IllegalAccessException {
			configBean.setField(key, value);
		}
	},
	FLOAT {
		@Override
		public void setValue(ConfigBean configBean, Field field, String value) throws IllegalAccessException {
			field.setFloat(configBean, Float.valueOf(value));
		}

		@Override
		public void setValue(ConfigBean configBean, String key, String value) throws IllegalAccessException {
			configBean.setField(key, value);
		}
	},
	DOUBLE {
		@Override
		public void setValue(ConfigBean configBean, Field field, String value) throws IllegalAccessException {
			field.setDouble(configBean, Double.valueOf(value));
		}

		@Override
		public void setValue(ConfigBean configBean, String key, String value) throws IllegalAccessException {
			configBean.setField(key, value);
		}
	},
	BOOLEAN {
		@Override
		public void setValue(ConfigBean configBean, Field field, String value) throws IllegalAccessException {
			field.setBoolean(configBean, Boolean.valueOf(value));
		}

		@Override
		public void setValue(ConfigBean configBean, String key, String value) throws IllegalAccessException {
			configBean.setField(key, value);
		}
	},
	BYTE {
		@Override
		public void setValue(ConfigBean configBean, Field field, String value) throws IllegalAccessException {
			field.setByte(configBean, Byte.valueOf(value));
		}

		@Override
		public void setValue(ConfigBean configBean, String key, String value) throws IllegalAccessException {
			configBean.setField(key, value);
		}
	},
	SHORT {
		@Override
		public void setValue(ConfigBean configBean, Field field, String value) throws IllegalAccessException {
			field.setShort(configBean, Short.valueOf(value));
		}

		@Override
		public void setValue(ConfigBean configBean, String key, String value) throws IllegalAccessException {
			configBean.setField(key, value);
		}
	},
	ENUM {
		@Override
		public void setValue(ConfigBean configBean, Field field, String value) throws IllegalAccessException {
			// 枚举类型中的getEnumVal方法必须声明为static
			try {
				field.set(configBean, field.getType().getDeclaredMethod("getEnumVal", String.class).invoke(null, value));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		public void setValue(ConfigBean configBean, String key, String value) throws IllegalAccessException {
			configBean.setField(key, value);
		}
	},
	OBJECT {
		@Override
		public void setValue(ConfigBean configBean, Field field, String value) throws IllegalAccessException {
			field.set(configBean, Short.valueOf(value));
		}

		@Override
		public void setValue(ConfigBean configBean, String key, String value) throws IllegalAccessException {
			configBean.setField(key, value);
		}
	};

	/**
	 * 将configBean对象的field写入value数据
	 * 
	 * @param configBean
	 * @param field
	 * @param value
	 * @throws IllegalAccessException
	 */
	public abstract void setValue(ConfigBean configBean, Field field, String value) throws IllegalAccessException;

	/**
	 * 将configBean对象的fieldMap字段写入value数据
	 * 
	 * @param configBean
	 * @param key
	 * @param value
	 * @throws IllegalAccessException
	 */
	public abstract void setValue(ConfigBean configBean, String key, String value) throws IllegalAccessException;
}

/**
 * 一个信息节点，记录此节点位于整个目录的轨迹以及保存的信息
 * 
 * @author Lv.Mingwei
 * 
 */
class ConfigBeanNode implements Comparable<ConfigBeanNode> {

	// 这里按顺序存储了此节点位于根节点的轨迹
	private String[] locus;

	// 用于保存原始路径
	private String path;

	private String value;

	private String parentPath;

	/**
	 * 构造方法，创建Node对象后，会自动获得这个Node对象在整个资源文件中的节点轨迹
	 * 
	 * @param path
	 */
	public ConfigBeanNode(String path, String value) {
		this.path = path;
		this.value = value;
		locus = ConfigBeanHelper.getNodeLocus(path);
		parentPath = getPath(locus.length - 1);
	}

	@Override
	public String toString() {
		return String.format("locus:%s  \r\nvalue:%s", Arrays.toString(locus), value);
	}

	public String getPath() {
		return path;
	}

	/**
	 * 获取第index层的路径名称
	 * 
	 * @param index
	 * @return
	 */
	public String getPath(int index) {
		if (locus.length < index) {
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < index; i++) {
			stringBuilder.append(locus[i]).append(".");
		}
		return stringBuilder.toString();
	}

	public String getParentPath() {
		return parentPath;
	}

	public String getValue() {
		return value;
	}

	public String[] getLocus() {
		return locus;
	}

	/**
	 * 重写排序方法<br>
	 * 排序规则为根据轨迹的深度作为第一排序，从浅到深<br>
	 * 同一深度下，以节点的名字String作为自然排序
	 */
	@Override
	public int compareTo(ConfigBeanNode node) {

		// 如果被比较对象为空
		if (node == null) {
			return -1;
		}
		// 如果两个节点内容相同
		if (Arrays.equals(locus, node.locus)) {
			return 0;
		}

		// 如果自身的轨迹长度大于被比较对象的轨迹长度
		if (locus.length > node.locus.length) {
			return 1;
		} else if (locus.length < node.locus.length) {
			return -1;
		}

		// 如果两个对象的长度相同，那么比较其中的每一个对象，一直找到比较为不相等的为止，如果一直相等，好吧，返回相等
		if (locus.length == node.locus.length && locus.length > 0) {
			for (int i = 0; i < locus.length; i++) {
				if (locus[i].compareTo(node.locus[i]) != 0) {
					return locus[i].compareTo(node.locus[i]);
				}
			}
		}

		return 0;
	}
}
