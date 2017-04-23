package cn.net.cvtt.lian.common.util;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <b>描述: </b>是一个可以通过Properties中的key、value给bean的变量赋值的工具类，与{@link ConfigBean}
 * 非常类似，但区别是该类的优势是不要求Java类继承自{@link ConfigBean}
 * ，但劣势是Java类中没有的字段而Properties中有的，在解析后，将被Java类丢弃。<br>
 * 下面为具体的注意事项:<br>
 * key可以包含"."、"{}"字符，"."代表bean中的变量可以级联(即变量类型为对象<br>
 * )；"{}"代表bean中的变量为Map类型，{}之间的内容为Map的key值。 bean中的变量如果是对象，则不能为null，必须初始化。
 * <p>
 * <b>功能: </b>与{@link ConfigBean}类似，提供一个从Properties到Java实体类型的解析转换工具类
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
 * 
 * 有两个Java类如下
 * public class Library {
 *   	private String libraryName;
 *    private Map&lt;Integer,BookInfo&gt; books = new HashMap&lt;Integer,BookInfo&gt;();
 *    Getter and Setter ...
 *   }
 *   
 *   public class BookInfo {
 *   	private String name;
 *    private String ISBN;
 *    Getter and Setter ...
 *   }
 *   
 * 将Properties信息读入Library中使用如下方法
 * Library library = new Library();
 * PropertiesUtil.fillBean(props, library);
 * Assert.assertEquals(library.getLibraryName(), "Feinno Library");
 * Assert.assertEquals(library.getBooks().size(), 2);
 * Assert.assertEquals(library.getBooks().get(0).getName(), "Feinno1");
 * Assert.assertEquals(library.getBooks().get(0).getISBN(), "1XXXXXX");
 * Assert.assertEquals(library.getBooks().get(1).getName(), "Feinno2");
 * Assert.assertEquals(library.getBooks().get(1).getISBN(), "2XXXXXX");
 *   
 * 针对Properties上有而Java实体类中没有的字段，books{0}.author与books{1}.author将被丢弃，无法在Library对象中去到
 * 
 * </pre>
 * <p>
 * 
 * @author 
 * 
 */
public class PropertiesUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

	/**
	 * 入口函数
	 * 
	 * @param pro
	 * @param bean
	 * @return
	 */
	public static void fillBean(Properties pro, Object bean) {
		Object obj = null;
		Enumeration enumeration = pro.keys();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			String path = "";
			String field = "";
			if (key.contains(".")) {
				path = key.substring(0, key.lastIndexOf(".")); // 取最后一个"."前的字符串
																// user{tom}
				field = key.substring(key.lastIndexOf(".") + 1); // 取最后一个"."后的字符串
																	// password
			} else {
				field = key;
				if (key.contains("{")) {
					path = key;
				}
			}
			// 找到最后一个.前的对象
			if (path.equals("")) { // password = haha
				obj = bean;
			} else {
				obj = findObject(bean, path, pro.getProperty(key));
			}

			if (obj != null) {
				// 给找到的对象赋值
				setValue(obj, field, pro.getProperty(key));
			}
		}
	}

	private static Object eval(Class clazz, String val) throws Exception {
		Constructor ctor = clazz.getConstructor(String.class);
		return ctor.newInstance(val);
	}

	/**
	 * 针对路径, 从path中找到对象地址
	 * 
	 * @param bean
	 * @param path
	 * @return
	 */
	private static Object findObject(Object bean, String path, String propVal) {
		//
		// 如user{tom}.info.name;
		String pathFirst = "";
		String pathNext = "";
		if (path.contains(".")) {
			pathFirst = path.substring(0, path.indexOf(".")); // 获取第一个"."之前的部分
																// user{tom}
			pathNext = path.substring(path.indexOf(".") + 1); // 获取第一个"."之后的部分
																// info.name
		} else {
			pathFirst = path;
		}
		Object obj = null;
		Field field = null;
		if (pathFirst.contains("{")) {
			int begin = pathFirst.indexOf("{");
			int end = pathFirst.indexOf("}");
			String pathFirstField = pathFirst.substring(0, begin);// 获取大括号前的部分
																	// user
			String pathFirstParam = pathFirst.substring(begin + 1, end);// 获取大括号中的部分
																		// tom
			try {// 空return 空
				field = bean.getClass().getDeclaredField(pathFirstField);
			} catch (NoSuchFieldException e) {
				LOGGER.error(e.getMessage());
				return null;
			}

			field.setAccessible(true);
			Object val;
			try {
				val = field.get(bean);
			} catch (IllegalArgumentException e1) {
				LOGGER.error(e1.getMessage());
				return null;
			} catch (IllegalAccessException e1) {
				LOGGER.error(e1.getMessage());
				return null;
			}

			if (val instanceof Map) {
				Map map = (Map) val;
				// 取出范型的实际类型
				Class fieldArgType = GenericsUtils.getFieldGenericType(field, 1);
				if (map != null) {
					obj = map.get(pathFirstParam);
					if (obj == null) {
						try {
							if (pathNext.equals("")) {
								obj = eval(fieldArgType, propVal);
								map.put(pathFirstParam, obj);
							} else {
								map.put(pathFirstParam, fieldArgType.newInstance());
								obj = map.get(pathFirstParam);
							}
						} catch (Exception e) {
							LOGGER.error(e.getMessage());
							return null;
						}
					}
				}
			} else { // 有"{",但是不是Map类型
				return null;
			}
		} else {
			try {
				field = bean.getClass().getDeclaredField(pathFirst);
			} catch (NoSuchFieldException e) {
				LOGGER.error(e.getMessage());
				return null;
			}
			try {
				field.setAccessible(true);
				obj = field.get(bean);
			} catch (IllegalArgumentException e) {
				LOGGER.error(e.getMessage());
				return null;
			} catch (IllegalAccessException e) {
				LOGGER.error(e.getMessage());
				return null;
			}
		}

		if (pathNext == "") {
			return obj;
		} else {
			return findObject(obj, pathNext, propVal);
		}
	}

	/**
	 * 通过反射给对象赋值
	 * 
	 * @param o
	 * @param strField
	 * @param val
	 */
	public static void setValue(Object o, String strField, String val) {
		if (val.trim().equals("")) {
			LOGGER.warn("the value of field in Properties is null");
		} else {
			Field field = null;
			try {
				field = o.getClass().getDeclaredField(strField);
			} catch (SecurityException e) {
				LOGGER.error(e.getMessage());
			} catch (NoSuchFieldException e) {
				LOGGER.error(e.getMessage());
			}
			if (field != null) {
				field.setAccessible(true);
				try {
					if (field.getType().getName().equals("int")) {
						field.setInt(o, Integer.parseInt(val));
					} else if (field.getType().getName().equals("long")) {
						field.setLong(o, Long.parseLong(val));
					} else if (field.getType().getName().equals("float")) {
						field.setFloat(o, Float.parseFloat(val));
					} else if (field.getType().getName().equals("double")) {
						field.setDouble(o, Double.parseDouble(val));
					} else if (field.getType().getName().equals("boolean")) {
						field.setBoolean(o, Boolean.parseBoolean(val));
					} else if (field.getType().getName().equals("byte")) {
						field.setByte(o, Byte.parseByte(val));
					} else if (field.getType().getName().equals("short")) {
						field.setShort(o, Short.parseShort(val));
					} else if (field.getType().getName().equals("java.lang.String")) {
						field.set(o, val);
					} else if (field.getType().isEnum()) { // 枚举类型
						// 枚举类型中的getEnumVal方法必须声明为static
						try {
							field.set(o, field.getType().getDeclaredMethod("getEnumVal", String.class)
									.invoke(null, val));
						} catch (IllegalArgumentException e) {
							LOGGER.error(e.getMessage());
						} catch (SecurityException e) {
							LOGGER.error(e.getMessage());
						} catch (InvocationTargetException e) {
							LOGGER.error(e.getMessage());
						} catch (NoSuchMethodException e) {
							LOGGER.error(e.getMessage());
						}
					}
				} catch (IllegalAccessException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
	}

	/**
	 * 用于将XML转换成资源文件
	 * 
	 * @param xmlBody
	 * @return
	 * @throws DocumentException
	 */
	public static Properties xmlToProperties(String xmlBody) {
		try {
			return XmlToPropertiesUtils.xmlToProperties(xmlBody);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 用于将XML转换成资源文件
	 * 
	 * @param file
	 * @return
	 * @throws DocumentException
	 */
	public static Properties xmlToProperties(File file) {
		try {
			return XmlToPropertiesUtils.xmlToProperties(file);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 用于将XML转换成资源文件
	 * 
	 * @param input
	 * @return
	 * @throws DocumentException
	 */
	public static Properties xmlToProperties(InputStream input) {
		try {
			return XmlToPropertiesUtils.xmlToProperties(input);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
	}

}

/**
 * 增加一个类，用于做XML->Properties的转换
 * 
 * @author Lv.Mingwei
 * 
 */
class XmlToPropertiesUtils {

	/** 如果在XML节点的Attribute中出现isMultiple属性且属性为true，那么标识此节点是一个ListItem类型 */
	private static final String MULTIPLE_KEYWORD = "isMultiple";

	private static final String KEY_VALUE = "key";

	private XmlToPropertiesUtils() {
	}

	/**
	 * 将XML文本解析成Properties对象
	 * 
	 * @param xmlBody
	 * @return
	 * @throws DocumentException
	 */
	public static Properties xmlToProperties(String xmlBody) throws DocumentException {
		return xmlToProperties(DocumentHelper.parseText(xmlBody));
	}

	/**
	 * 将XML文件解析成Properties对象
	 * 
	 * @param file
	 * @return
	 * @throws DocumentException
	 */
	public static Properties xmlToProperties(File file) throws DocumentException {
		SAXReader reader = new SAXReader();
		return xmlToProperties(reader.read(file));
	}

	/**
	 * 将XML InputStream解析成Properties对象
	 * 
	 * @param input
	 * @return
	 * @throws DocumentException
	 */
	public static Properties xmlToProperties(InputStream input) throws DocumentException {
		SAXReader reader = new SAXReader();
		return xmlToProperties(reader.read(input));
	}

	/**
	 * 将XML的Dom4j的Document对象解析成Properties对象
	 * 
	 * @param doucment
	 * @return
	 */
	public static Properties xmlToProperties(Document doucment) {
		Element element = doucment.getRootElement();
		Stack<String> pathStack = new Stack<String>();
		// 根节点是否要放入路径中，纠结啊，暂时不放了
		// pathStack.push(element.getName());
		Properties properties = new Properties();
		return analyzeElements(element, pathStack, properties);
	}

	/**
	 * 具体元素解析方法，内部使用，不对外暴露
	 * 
	 * @param element
	 *            当前待处理的元素
	 * @param pathStack
	 *            当前元素位于的路径栈
	 * @param properties
	 *            当元素解析完毕后，会被存储到此资源文件中
	 * @return
	 */
	private static Properties analyzeElements(Element element, Stack<String> pathStack, Properties properties) {

		// Step 1.遍历子节点
		int count = 0;
		for (Iterator i = element.elementIterator(); i.hasNext();) {
			Element childElement = (Element) i.next();
			// 如果一个节点有isMultiple属性或者有同名的并列节点，那么以每个同名子节点attribute中的key作为区分,key使用{key},其下及节点路径就变为a.name{key}.xxx
			// 如果没有key，则使用count++作为默认key
			if ((childElement.attribute(MULTIPLE_KEYWORD) != null
					&& childElement.attribute(MULTIPLE_KEYWORD).getText().equalsIgnoreCase("true"))
					|| element.elements(childElement.getName()).size() > 1) {
				// 如果这样的节点中有key属性，那么此key属性就作为key值了
				if (childElement.attribute(KEY_VALUE) != null
						&& childElement.attribute(KEY_VALUE).getText().length() > 0) {
					pathStack.push(childElement.getName() + "{" + childElement.attribute(KEY_VALUE).getText() + "}");
				} else {
					pathStack.push(childElement.getName() + "{" + count++ + "}");
				}

			} else {
				pathStack.push(childElement.getName());
			}

			analyzeElements(childElement, pathStack, properties);
			pathStack.pop();
		}
		// Step 2.遍历子属性
		for (Iterator i = element.attributeIterator(); i.hasNext();) {
			Attribute attribute = (Attribute) i.next();
			properties.put(getPath(pathStack, attribute.getName()), attribute.getText());
		}
		// 只有当确认子节点为叶子节点，且Text中有内容时，才装填节点中的key-value
		if (element.elements().size() == 0 && element.getText().length() > 0) {
			properties.put(getPath(pathStack, null), element.getText());
		}
		return properties;
	}

	private static String getPath(Stack<String> pathStack, String currentNodeName) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String path : pathStack) {
			stringBuilder.append(path).append(".");
		}
		if (currentNodeName != null && currentNodeName.length() > 0) {
			stringBuilder.append(currentNodeName);
			return stringBuilder.toString();
		} else {
			return stringBuilder.length() > 0 ? stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString() : "";
		}
	}
}