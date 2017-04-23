package cn.net.cvtt.lian.common.serialization.protobuf.generator;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoBuilder;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoBuilderFactory;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoManager;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;
import cn.net.cvtt.lian.common.serialization.protobuf.WireFormat;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.ArrayFieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.FieldInformation;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.FieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.util.ClassUtils;
import cn.net.cvtt.lian.common.serialization.protobuf.util.FileUtil;
import cn.net.cvtt.lian.common.serialization.protobuf.util.ProtoGenericsUtils;
import cn.net.cvtt.lian.common.serialization.protobuf.util.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * <b>描述: </b>这个是{@link ProtoBuilder}和{@link ProtoBuilderFactory}
 * 子类的代码生成器,该生成器可以生成某一个类序列化的辅助类以及辅助类的工厂类，是序列化组件中较为重要的部分
 * <p>
 * <b>功能: </b>代码生成器，用于生成序列化辅助类{@link ProtoBuilder}的子类以及辅助类的工厂类
 * {@link ProtoBuilderFactory}的实现类，这些类是具体的将某一个类进行序列化或反序列化的类
 * <p>
 * <b>用法: </b>该类由序列化组件调用
 * <p>
 * 
 * @author 
 * 
 */
public class ProtoBuilderCodeGenerator {

	private static final Logger logger = LoggerFactory.getLogger(ProtoBuilderCodeGenerator.class);

	/** 已经过自动生成过proto对象的class列表，如果某一个类路径存在于这个列表中，代表此proto的class已经被生成过了，无需再次生成 */
	public static final Map<ClassLoader, Set<Class<?>>> generatedClassCache = Collections.synchronizedMap(new HashMap<ClassLoader, Set<Class<?>>>());

	/** 需要自动生成ProtoCode的class */
	private Class<?> clazz = null;

	/**
	 * 这是一个用于占位标识的Set，功能与generatedClassSet很类似，区别是它只代表当前代码生成器对象都在处理哪些类，防止在递归时重复处理
	 */
	private Set<Class<?>> codeSet = null;

	/** ProtoCode生成器自己维护的已生成源码的列表 */
	private List<String> sourceList = null;

	/**
	 * 构造方法,构造某一个类型的序列化辅助类的代码生成器
	 * 
	 * @param clazz
	 */
	public ProtoBuilderCodeGenerator(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * 获得生成的ProtoCode源码列表
	 * 
	 * @return
	 */
	public List<String> getProtoCodeList() throws ProtoCodeGeneratorException {
		if (sourceList == null) {
			codeSet = new HashSet<Class<?>>();
			sourceList = new ArrayList<String>();
			processProtoCode(clazz);
		}
		return sourceList;
	}

	/**
	 * 通过一个类获得他的序列化辅助类源码，这个方法是生成序列化辅助类代码的核心部分<br>
	 * 1. 首先寻找到将要序列化的类的全部字段，为了支持继承结构，这些字段包含了父类的字段<br>
	 * 2. 遍历这些字段<br>
	 * 3. 对每一个字段判断是否有序列化注解ProtoMember,如果有此注解，那么继续，否则放弃这个字段<br>
	 * 4. 如果满足第3跳，那么继续获得该字段对应的字段解释器FieldInterpreter，通过解释器翻译出该字段的序列化、反序列化等代码 <br>
	 * 5. 处理特殊字段类型，例如ProtoEntity、List、Array、Map (因为这些类型可能存在递归的序列化代码生成操作) <br>
	 * 6. 将前几步得到的结果存入JavaBean对象，并通过freemarker将其填充入代码模板中 <br>
	 * 注：第5步可能会产生递归操作，会最终递归到以上步骤的第1步
	 * 
	 * @param clazz
	 * @return
	 */
	private void processProtoCode(Class<?> clazz) throws ProtoCodeGeneratorException {
		logger.info("Began to generate the ProtoBuilder and ProtoBuilderFactory source code of the [{}].",
				clazz.getName());
		// 这个为占位符，在递归时使用，代表此类(clazz)已经有人处理了，递归时发现这个类，不需要管他了，一个类只需要生成一次源代码既可
		codeSet.add(clazz);
		try {
			// 寻找到该类及其父类的全部字段
			List<Field> fields = new ArrayList<Field>();
			for (Class<?> clazzTemp = clazz; !clazzTemp.equals(Object.class); clazzTemp = clazzTemp.getSuperclass()) {
				for (Field field : clazzTemp.getDeclaredFields()) {
					fields.add(field);
				}
			}

			// 这里放置模板需要使用的字段，此字段通过freemarker最终写入到模板中
			ProtoTemplateParam templateParam = new ProtoTemplateParam();
			// 这个list用于存放这个序列化类对应的所有字段信息
			templateParam.setPackageName(getBuilderPackage(clazz));
			templateParam.setClassName(ClassUtils.processClassName(clazz.getName()));
			templateParam.setBuilderClassName(clazz.getSimpleName() + ProtoConfig.PROTO_BUILDER_NAME);
			templateParam.setBuilderFactoryClassName(clazz.getSimpleName() + ProtoConfig.PROTO_BUILDER_FACTORY_NAME);

			// 开始逐个字段的解析工作
			for (Field field : fields) {
				// 通过注解找到相关字段的详细信息
				ProtoMember protoMember = field.getAnnotation(ProtoMember.class);
				if (protoMember != null) {
					// 找到此类型对应的ProtoType枚举类型以及对应的字段解释器
					ProtoFieldType protoFieldType = ProtoFieldType.valueOf(field.getType());
					FieldInterpreter fieldInterpreter = protoFieldType.getFieldInterpreter();
					// 拼装这个字段的信息到FieldInformation对象中，后面的各种字段解释器，都是用这里存储的字段信息进行生成序列化与反序列化代码
					FieldInformation fieldInformation = new FieldInformation(clazz, field, protoMember);
					// 组装模板需要用到的字段内容，再将这个内容填充到模板上，最终形成一个完整的源文件
					// 这个内容就是具体的这个字段应该如何序列化、如何反序列化、如何计算长度的具体代码
					ProtoTemplateParam.FieldParam fieldParam = ProtoTemplateParam.newFieldParam();
					fieldParam.setValue(protoMember.value());
					fieldParam.setFieldName(field.getName());
					fieldParam.setTag(WireFormat.makeTag(protoMember.value(),
							fieldInterpreter.getTagType(fieldInformation)));
					fieldParam.setParseCode(fieldInterpreter.getParseCode(fieldInformation));
					fieldParam.setWriteCode(fieldInterpreter.getWriteCode(fieldInformation));
					fieldParam.setSizeCode(fieldInterpreter.getSizeCode(fieldInformation));
					// 如果此字段为必输项，则对外输出判空代码
					if (protoMember.required()) {
						fieldParam.setRequiredCode(fieldInterpreter.getRequiredCode(fieldInformation));
					}
					// 调用这个方法,处理了几种特殊的protoFieldType类型,因为这些类型有可能有嵌套的应该递归创建序列化辅助类的类型
					processSpecialField(templateParam, fieldParam, fieldInformation, protoFieldType);
					templateParam.addGlobalCode(fieldInterpreter.getGlobalCode(fieldInformation));
					templateParam.addFieldParam(fieldParam);
				}
			}
			// 开始通过模板生成对应的源码，并且将源码放入源码列表中
			sourceList.add(generatedCode(ProtoConfig.PROTO_BUILDER_TEMPLATE, templateParam));
			sourceList.add(generatedCode(ProtoConfig.PROTO_BUILDER_FACTORY_TEMPLATE, templateParam));
			// 将已经生成源码的类的名字放入已经生成过的列表中,下次遇到他就不需要再次生成了
			putGeneratedClassCache(clazz);
		} catch (Exception e) {
			throw new ProtoCodeGeneratorException(String.format("Generate %s error.", clazz.getName()), e);
		}
	}

	/**
	 * 添加自动生成的class到cache中
	 * 
	 * @param value
	 */
	private void putGeneratedClassCache(Class<?> value) {
		ClassLoader classLoader = clazz.getClassLoader();
		Set<Class<?>> classSet = generatedClassCache.get(classLoader);
		if (classSet == null) {
			classSet = new HashSet<Class<?>>();
			generatedClassCache.put(classLoader, classSet);
		}
		classSet.add(value);
	}

	/**
	 * 判断一个Class是否在Cache中
	 * 
	 * @param value
	 * @return
	 */
	public boolean containsGeneratedClass(Class<?> value) {
		ClassLoader classLoader = clazz.getClassLoader();
		Set<Class<?>> classSet = generatedClassCache.get(classLoader);
		if (classSet == null) {
			return false;
		} else {
			return classSet.contains(value);
		}
	}

	/**
	 * 处理了几种特殊的ProtoFieldType类型,因为这些类型有可能有嵌套的应该创建序列化辅助类的类型，我们需要找出这样的类型，
	 * 对他创建序列化辅助代码(ProtoBuilder以及ProtoBuilderFactory)
	 * 
	 * @param templateParam
	 * @param fieldParam
	 * @param fieldInformation
	 * @param protoFieldType
	 * @throws ProtoCodeGeneratorException
	 */
	private void processSpecialField(ProtoTemplateParam templateParam, ProtoTemplateParam.FieldParam fieldParam,
			FieldInformation fieldInformation, ProtoFieldType protoFieldType) throws ProtoCodeGeneratorException {
		switch (protoFieldType) {
		case MESSAGE:
			// 判断是否需要递归生成动态解析代码(Message本身就是需要递归创建对应动态代码的类型,下面的判断只是判断这个具体的MESSAGE类型是否已经创建过动态代码)
			if (!containsGeneratedClass(fieldInformation.getField().getType())
					&& !codeSet.contains(fieldInformation.getField().getType())) {
				// 如果需要创建动态代码的类不存在已生成列表中，并且本次也没人处理他，则需要处理他了
				processProtoCode(fieldInformation.getField().getType());
			}
			break;
		case LIST:
		case SET:
			// 取List中泛型所代表的具体类型，查看此类型是否需要递归生成源码的类型
			Class<?> classType1 = ProtoGenericsUtils.getGenericsClass(fieldInformation.getField(), 0);
			if (ProtoFieldType.valueOf(classType1) == ProtoFieldType.MESSAGE) {// 如果集合中的泛型又是一个消息类型，则需要创建这个类型的序列化辅助类
				if (!containsGeneratedClass(classType1) && !codeSet.contains(classType1)) {
					// 如果需要创建动态代码的类不存在已生成列表中，并且本次也没人处理他，则需要处理他了
					processProtoCode(classType1);
				}
			}
			break;
		case ARRAY:
			// 这次不是List了，他有可能是数组，但是方法一样
			Class<?> classType2 = ProtoGenericsUtils.getGenericsClass(fieldInformation.getField(), 0);
			if (ProtoFieldType.valueOf(classType2) == ProtoFieldType.MESSAGE) {// 如果数组中的类型又是一个消息类型，则需要创建这个类型的序列化辅助类
				if (!containsGeneratedClass(classType2) && !codeSet.contains(classType2)) {
					// 如果需要创建动态代码的类不存在已生成列表中，并且本次也没人处理他，则需要处理他了
					processProtoCode(classType2);
				}
			}
			// 数组类型需要在反序列化时将缓存在MAP<Integer,List<Object>>的List转换成数组再赋值到相应的字段上，这么做是为了提升数组的反序列化速度
			fieldParam.setParseFooter(((ArrayFieldInterpreter) protoFieldType.getFieldInterpreter())
					.getConverArrayCode(fieldInformation));
			break;
		case MAP:
			// 这次有可能是MAP了，但是方法一样，不过他要分别对Key和Value进行泛型类型检查以及判断这两种类型是否递归进行动态编译
			Class<?> classKey = ProtoGenericsUtils.getGenericsClass(fieldInformation.getField(), 0);
			if (ProtoFieldType.valueOf(classKey) == ProtoFieldType.MESSAGE) {
				if (!containsGeneratedClass(classKey) && !codeSet.contains(classKey)) {
					// 如果需要创建动态代码的类不存在已生成列表中，并且本次也没人处理他，则需要处理他了
					processProtoCode(classKey);
				}
			}
			Class<?> classValue = ProtoGenericsUtils.getGenericsClass(fieldInformation.getField(), 1);
			if (ProtoFieldType.valueOf(classValue) == ProtoFieldType.MESSAGE) {
				if (!containsGeneratedClass(classValue) && !codeSet.contains(classValue)) {
					// 如果需要创建动态代码的类不存在已生成列表中，并且本次也没人处理他，则需要处理他了
					processProtoCode(classValue);
				}
			}
			templateParam.setParseFooterExists(true);
			break;
		default:
			break;
		}
	}

	/**
	 * 通过指定的模板，填充指定MAP数据，返回填充后的内容
	 * 
	 * @param templateName
	 * @param map
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	private String generatedCode(String templateName, ProtoTemplateParam templateParam) throws IOException,
			TemplateException {
		Configuration cfg = new Configuration();
		cfg.setTemplateLoader(new StringTemplateLoader(templateName));
		cfg.setDefaultEncoding("UTF-8");
		Template template = cfg.getTemplate("");
		StringWriter writer = new StringWriter();
		template.process(templateParam, writer);
		// 这个测试代码是将生成的内容写成文件保存至某一位置
		if (ProtoManager.isDebug()) {
			File dirFile = new File(ProtoManager.SOURCE_SAVE_PATH);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			FileUtil.write(writer.toString(), ProtoManager.SOURCE_SAVE_PATH
					+ (templateName == ProtoConfig.PROTO_BUILDER_TEMPLATE ? templateParam.getBuilderClassName()
							: templateParam.getBuilderFactoryClassName()) + ".java");
		}
		return writer.toString();
	}

	/**
	 * 获得一个proto类的Builder类名称(Builder类的全路径)
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getBuilderClassFullName(Class<?> clazz) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getBuilderPackage(clazz)).append(".").append(clazz.getSimpleName())
				.append(ProtoConfig.PROTO_BUILDER_NAME);
		return stringBuilder.toString();
	}

	/**
	 * 获得一个proto类的Builder类名称(Builder类的全路径)
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getBuilderClassName(Class<?> clazz) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(clazz.getSimpleName()).append(ProtoConfig.PROTO_BUILDER_NAME);
		return stringBuilder.toString();
	}

	/**
	 * 获得一个proto类的工厂类名称(工厂类的全路径)
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getBuilderFactoryClassFullName(Class<?> clazz) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getBuilderPackage(clazz)).append(".").append(clazz.getSimpleName())
				.append(ProtoConfig.PROTO_BUILDER_FACTORY_NAME);
		return stringBuilder.toString();
	}

	/**
	 * 获得一个序列化辅助类所属的包路径
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getBuilderPackage(Class<?> clazz) {
		if (clazz.getPackage() != null) {
			return clazz.getPackage().getName();
		} else {
			return ProtoConfig.PACKAGE_CODE;
		}
	}
}
