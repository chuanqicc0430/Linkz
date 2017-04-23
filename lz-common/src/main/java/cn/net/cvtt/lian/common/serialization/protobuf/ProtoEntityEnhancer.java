package cn.net.cvtt.lian.common.serialization.protobuf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.net.cvtt.lian.common.serialization.bytecode.instruction.OpcodeEnum;
import cn.net.cvtt.lian.common.serialization.bytecode.type.Attribute;
import cn.net.cvtt.lian.common.serialization.bytecode.type.AttributeType;
import cn.net.cvtt.lian.common.serialization.bytecode.type.CPInfo;
import cn.net.cvtt.lian.common.serialization.bytecode.type.ClassFile;
import cn.net.cvtt.lian.common.serialization.bytecode.type.CodeAttribute;
import cn.net.cvtt.lian.common.serialization.bytecode.type.ConstantClassInfo;
import cn.net.cvtt.lian.common.serialization.bytecode.type.ConstantIntegerInfo;
import cn.net.cvtt.lian.common.serialization.bytecode.type.ConstantMethodrefInfo;
import cn.net.cvtt.lian.common.serialization.bytecode.type.ConstantNameAndTypeInfo;
import cn.net.cvtt.lian.common.serialization.bytecode.type.ConstantUTF8Info;
import cn.net.cvtt.lian.common.serialization.bytecode.type.FieldInfo;
import cn.net.cvtt.lian.common.serialization.bytecode.type.Global;
import cn.net.cvtt.lian.common.serialization.bytecode.type.InnerClassesAttribute;
import cn.net.cvtt.lian.common.serialization.bytecode.type.InvalidByteCodeException;
import cn.net.cvtt.lian.common.serialization.bytecode.type.LineNumberTableAttribute;
import cn.net.cvtt.lian.common.serialization.bytecode.type.LineNumberTableAttribute.LineNumberTable;
import cn.net.cvtt.lian.common.serialization.bytecode.type.LocalVariableTableAttribute;
import cn.net.cvtt.lian.common.serialization.bytecode.type.LocalVariableTableAttribute.LocalVariableTable;
import cn.net.cvtt.lian.common.serialization.bytecode.type.LocalVariableTypeTableAttribute;
import cn.net.cvtt.lian.common.serialization.bytecode.type.LocalVariableTypeTableAttribute.LocalVariableTypeTable;
import cn.net.cvtt.lian.common.serialization.bytecode.type.MethodInfo;

/**
 * 
 * <b>描述: </b>通过这个类来实现ProtoEntity的AOP功能，为它的set方法加入putSerializationFieldTag方法
 * <p>
 * <b>功能: </b>通过这个类来实现ProtoEntity的AOP功能，为它的set方法加入putSerializationFieldTag方法
 * <p>
 * <b>用法: </b>
 * <p>
 * 
 * @author 
 * 
 */
public class ProtoEntityEnhancer {

	private Class<?> clazz;
	private ClassFile classFile;
	private Map<IndexType, Integer> cpIndexMap;
	private Map<String, Integer> fieldTagValueMap = new HashMap<String, Integer>();
	private static java.lang.reflect.Method defineClass1;

	/** 这里保存了经过ProtoEntityAop修改的加强版序列化的ProtoEntity */
	private static final Map<Class<?>, Class<?>> ENHANCE_MY_PROTOENTITY_MAP = new HashMap<Class<?>, Class<?>>();

	static {
		try {
			AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
				public Object run() throws Exception {
					Class<?> cl = Class.forName("java.lang.ClassLoader");
					defineClass1 = cl.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class,
							int.class, int.class });
					return null;
				}
			});
		} catch (PrivilegedActionException pae) {
			throw new RuntimeException("cannot initialize ClassLoader", pae.getException());
		}
	}

	private ProtoEntityEnhancer(Class<?> clazz) {
		this.clazz = clazz;
		initProtoMember();
	}

	/**
	 * 获得一个该类加强类的ProtoEntity<br>
	 * 此方法保证一个clazz只生成一个对应的加强类
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T extends ProtoEntity> Class<T> getEnhanceProtoEntityClass(Class<T> clazz) {
		Class<?> enhanceClass = ENHANCE_MY_PROTOENTITY_MAP.get(clazz);
		if (enhanceClass == null) {
			synchronized (ENHANCE_MY_PROTOENTITY_MAP) {
				if (ENHANCE_MY_PROTOENTITY_MAP.get(clazz) == null) {
					ProtoEntityEnhancer enhancer = new ProtoEntityEnhancer(clazz);
					try {
						ClassFile classFile = enhancer.newEnhanceClassFile();
						enhanceClass = createClass(classFile, clazz);
						ENHANCE_MY_PROTOENTITY_MAP.put(clazz, enhanceClass);
					} catch (Exception e) {
						throw new RuntimeException("getEnhanceProtoEntityClass Error.", e);
					}
				} else {
					enhanceClass = ENHANCE_MY_PROTOENTITY_MAP.get(clazz);
				}

			}
		}
		return (Class<T>) enhanceClass;
	}

	/**
	 * /** 创建一个加强的类的基类
	 * 
	 * @param clazz
	 * @return
	 * @throws IOException
	 * @throws InvalidByteCodeException
	 */
	public ClassFile newEnhanceClassFile() throws IOException, InvalidByteCodeException {
		classFile = ClassFile.valueOf(toBytes(clazz));

		// Step1 处理常量池
		List<CPInfo> newCpInfoList = processCPInfo();

		// Step2 处理Field
		FieldInfo[] newFieldInfos = new FieldInfo[0];

		// Step3 处理方法区
		List<MethodInfo> newMethodInfoList = processMethod(newCpInfoList);
		MethodInfo[] newMethodInfos = new MethodInfo[newMethodInfoList.size()];
		newMethodInfoList.toArray(newMethodInfos);

		// Step4 处理Class的Attribute属性(对内部类，要清除内部类属性)
		List<Attribute> classAttributes = processClassAttribute();
		Attribute[] newAttributes = new Attribute[classAttributes.size()];
		classAttributes.toArray(newAttributes);

		CPInfo[] newCpInfos = new CPInfo[newCpInfoList.size()];
		newCpInfoList.toArray(newCpInfos);
		classFile.writeToCPInfo(newCpInfos);
		classFile.writeToFieldInfo(newFieldInfos);
		classFile.writeToMethodInfo(newMethodInfos);
		classFile.writeToAttribute(newAttributes);
		classFile.writeToInterface(new int[] { cpIndexMap.get(IndexType.INTERFACE_ENHANCE) });

		return classFile;
	}

	/**
	 * 处理常量池中的信息<br>
	 * 1. 修改当前类名 <br>
	 * 2. 修改当前类的基类 <br>
	 * 3. 为当前类增加超类的putSerializationFieldTag的方法区
	 * 
	 * @param classFile
	 * @return
	 */
	private List<CPInfo> processCPInfo() {
		List<CPInfo> newCpInfoList = new ArrayList<CPInfo>();
		cpIndexMap = new HashMap<IndexType, Integer>();
		CPInfo[] cpInfos = classFile.getConstant_pool();
		for (CPInfo cpInfo : cpInfos) {
			newCpInfoList.add(cpInfo);
		}

		// 寻找到常量池中标识当前类及当前类的超类的描述常量
		int thisClassIndex = classFile.getThis_class();
		ConstantClassInfo thisClassInfo = (ConstantClassInfo) cpInfos[thisClassIndex];
		int superClassIndex = classFile.getSuper_class();
		ConstantClassInfo superClassInfo = (ConstantClassInfo) cpInfos[superClassIndex];
		// 获得当前类名称
		String oldName = ((ConstantUTF8Info) cpInfos[thisClassInfo.getNameIndex()]).getValue();
		String newName = oldName + System.currentTimeMillis();
		// 修改当前类的超类为原始类的名字
		newCpInfoList.set(superClassInfo.getNameIndex(), new ConstantUTF8Info(classFile, oldName));
		// 修改当前类的名字为原来是加时间戳
		newCpInfoList.set(thisClassInfo.getNameIndex(), new ConstantUTF8Info(classFile, newName));

		// 加入EnhanceProtoEntity接口
		newCpInfoList.add(new ConstantUTF8Info(classFile, "cn/net/cvtt/lian/common/serialization/protobuf/EnhanceProtoEntity"));
		int interfaceIndex = newCpInfoList.size();
		newCpInfoList.add(new ConstantClassInfo(classFile, interfaceIndex - 1));

		// 向常量池中加入父类的putSerializationFieldTag方法
		int putMethodIndex = newCpInfoList.size();
		newCpInfoList.add(new ConstantMethodrefInfo(classFile, superClassIndex, newCpInfoList.size() + 1));
		newCpInfoList.add(new ConstantNameAndTypeInfo(classFile, newCpInfoList.size() + 1, newCpInfoList.size() + 2));
		newCpInfoList.add(new ConstantUTF8Info(classFile, "putSerializationFieldTag"));
		newCpInfoList.add(new ConstantUTF8Info(classFile, "(I)V"));
		int classDescIndex = newCpInfoList.size();
		newCpInfoList.add(new ConstantUTF8Info(classFile, "L" + newName + ";"));

		cpIndexMap.put(IndexType.CLASS, thisClassIndex);
		cpIndexMap.put(IndexType.CLASS_DESC, classDescIndex);
		cpIndexMap.put(IndexType.SUPER_CLASS, superClassIndex);
		cpIndexMap.put(IndexType.INTERFACE_ENHANCE, interfaceIndex);
		cpIndexMap.put(IndexType.PUT_SERIALIZATION_FIELD_TAG, putMethodIndex);
		return newCpInfoList;
	}

	/**
	 * 处理方法区<br>
	 * 1. 保留方法区的默认构造方法<br>
	 * 2. 保留方法区所有的set方法<br>
	 * 3. 修改set方法中的指令集<br>
	 * 4. 向常量池中增加set方法指令集需要用到的常量
	 * 
	 * @param classFile
	 * @param newCpInfoList
	 * @return
	 */
	private List<MethodInfo> processMethod(List<CPInfo> newCpInfoList) {
		List<MethodInfo> newMethodInfoList = new ArrayList<MethodInfo>();
		for (MethodInfo method : classFile.getMethods()) {
			String methodName = method.getName().getValue();
			if (methodName.startsWith("set")) {

				boolean isPrivate = (method.getAccessFlags() & Global.ACC_PRIVATE) != 0;
				boolean isFinal = (method.getAccessFlags() & Global.ACC_FINAL) != 0;
				// 如果是私有或者不允许继承的,那么不处理
				if (isPrivate || isFinal) {
					continue;
				}
				// 如果当前的方法没有对应的ProtoMember字段，那么忽略
				Integer protoTag = getProtoMemberValue(methodName.substring(3, methodName.length()));
				if (protoTag == null) {
					continue;
				}

				// 向常量池增加父类该方法的调用，用于给当前类来调用这个方法(super.setXXX(XXX))
				int nameAndTypeIndex = newCpInfoList.size();
				ConstantNameAndTypeInfo nameAndTypeInfo = new ConstantNameAndTypeInfo(classFile, method.getNameIndex(),
						method.getDescriptorIndex());
				newCpInfoList.add(nameAndTypeInfo);

				int methodrefIndex = newCpInfoList.size();
				ConstantMethodrefInfo methodrefInfo = new ConstantMethodrefInfo(classFile,
						cpIndexMap.get(IndexType.SUPER_CLASS), nameAndTypeIndex);
				newCpInfoList.add(methodrefInfo);
				int protoTagIndex = newCpInfoList.size();
				ConstantIntegerInfo protoTagCP = new ConstantIntegerInfo(classFile, protoTag);
				newCpInfoList.add(protoTagCP);

				// 修改当前方法区中的操作指令为调用父类方法
				CodeAttribute codeAttribute = method.getCodeAttribute();
				int oldLength = codeAttribute.getLength();
				if (protoTagIndex > 255) {
					codeAttribute.setCodes(new byte[] { OpcodeEnum.ALOAD_0.getOpcode(), codeAttribute.getCodes()[1],
							OpcodeEnum.INVOKESPECIAL.getOpcode(), (byte) ((methodrefIndex >>> 8) & 0xFF),
							(byte) ((methodrefIndex >>> 0) & 0xFF), OpcodeEnum.ALOAD_0.getOpcode(),
							OpcodeEnum.LDC_W.getOpcode(), (byte) ((protoTagIndex >>> 8) & 0xFF),
							(byte) ((protoTagIndex >>> 0) & 0xFF), OpcodeEnum.INVOKESPECIAL.getOpcode(),
							(byte) ((cpIndexMap.get(IndexType.PUT_SERIALIZATION_FIELD_TAG) >>> 8) & 0xFF),
							(byte) ((cpIndexMap.get(IndexType.PUT_SERIALIZATION_FIELD_TAG) >>> 0) & 0xFF),
							OpcodeEnum.RETURN.getOpcode() });
				} else {
					codeAttribute.setCodes(new byte[] { OpcodeEnum.ALOAD_0.getOpcode(), codeAttribute.getCodes()[1],
							OpcodeEnum.INVOKESPECIAL.getOpcode(), (byte) ((methodrefIndex >>> 8) & 0xFF),
							(byte) ((methodrefIndex >>> 0) & 0xFF), OpcodeEnum.ALOAD_0.getOpcode(),
							OpcodeEnum.LDC.getOpcode(), (byte) ((protoTagIndex >>> 0) & 0xFF),
							OpcodeEnum.INVOKESPECIAL.getOpcode(),
							(byte) ((cpIndexMap.get(IndexType.PUT_SERIALIZATION_FIELD_TAG) >>> 8) & 0xFF),
							(byte) ((cpIndexMap.get(IndexType.PUT_SERIALIZATION_FIELD_TAG) >>> 0) & 0xFF),
							OpcodeEnum.RETURN.getOpcode() });
				}
				// 清理掉CodeAttribute中的其他属性
				clearCodeAttribute(codeAttribute);

				int newLength = codeAttribute.getLength();
				method.setCorrectOffset(newLength - oldLength);

				newMethodInfoList.add(method);
			} else if (method.getName().getValue().equals("<init>")) {
				CodeAttribute codeAttribute = method.getCodeAttribute();
				int oldLength = codeAttribute.getLength();
				byte[] oldCodes = codeAttribute.getCodes();
				// 仅取前三个操作字节，用于超类的初始化方法调用
				codeAttribute.setCodes(new byte[] { oldCodes[0], oldCodes[1], oldCodes[2], oldCodes[3],
						OpcodeEnum.RETURN.getOpcode() });
				// 清理掉CodeAttribute中的其他属性
				clearCodeAttribute(codeAttribute);
				int newLength = codeAttribute.getLength();
				method.setCorrectOffset(newLength - oldLength);

				newMethodInfoList.add(method);

			}
		}

		return newMethodInfoList;
	}

	/**
	 * 清理CodeAttribute属性
	 * 
	 * @param codeAttribute
	 */
	private void clearCodeAttribute(CodeAttribute codeAttribute) {
		// 修改第一个参数this的常量池描述为当前类的描述
		LocalVariableTableAttribute localVariableTableAttribute = (LocalVariableTableAttribute) codeAttribute
				.getAttribute(AttributeType.LOCALVARIABLETABLE);
		if (localVariableTableAttribute != null && localVariableTableAttribute.getLineVariableTables() != null
				&& localVariableTableAttribute.getLineVariableTables().length > 0) {
			for (LocalVariableTable localVariableTable : localVariableTableAttribute.getLineVariableTables()) {
				localVariableTable.setStartPc(0);
				localVariableTable.setLengthPc(codeAttribute.getCodes().length);
			}
			localVariableTableAttribute.getLineVariableTables()[0].setDescriptorIndex(cpIndexMap
					.get(IndexType.CLASS_DESC));
		}
		// 修改泛型变量签名
		LocalVariableTypeTableAttribute localVariableTypeTableAttribute = (LocalVariableTypeTableAttribute) codeAttribute
				.getAttribute(AttributeType.LOCALVARIABLETYPETABLE);
		if (localVariableTypeTableAttribute != null
				&& localVariableTypeTableAttribute.getLineVariableTypeTables() != null
				&& localVariableTypeTableAttribute.getLineVariableTypeTables().length > 0) {
			for (LocalVariableTypeTable localVariableTypeTable : localVariableTypeTableAttribute
					.getLineVariableTypeTables()) {
				localVariableTypeTable.setStartPc(0);
				localVariableTypeTable.setLengthPc(codeAttribute.getCodes().length);
			}
		}
		// 修改LineNumberTableAttribute属性，该属性用于调试器，但是不修改将使运行报错
		LineNumberTableAttribute lineNumberTableAttribute = (LineNumberTableAttribute) codeAttribute
				.getAttribute(AttributeType.LINENUMBERTABLE);
		if (lineNumberTableAttribute != null && lineNumberTableAttribute.getLineNumberTables() != null
				&& lineNumberTableAttribute.getLineNumberTables().length > 0) {
			for (LineNumberTable lineNumberTable : lineNumberTableAttribute.getLineNumberTables()) {
				lineNumberTable.setStartPc(0);
				lineNumberTable.setLineNumber(0);
			}
		}
	}

	/**
	 * 处理Class的Attribute属性，清理掉innerClass标记
	 * 
	 * @param classFile
	 * @param newCpInfoList
	 * @return
	 */
	private List<Attribute> processClassAttribute() {
		List<Attribute> classAttributes = new ArrayList<Attribute>();
		Attribute[] attributes = classFile.getAttributes();
		if (attributes != null && attributes.length > 0) {
			for (Attribute attribute : attributes) {
				if (attribute instanceof InnerClassesAttribute) {
					continue;
				}
				classAttributes.add(attribute);
			}
		}
		return classAttributes;
	}

	/**
	 * 初始化ProtoMember相关Value参数到fieldTagValueMap中
	 */
	private void initProtoMember() {
		for (Class<?> clazzTemp = clazz; !clazzTemp.equals(Object.class); clazzTemp = clazzTemp.getSuperclass()) {
			for (Field field : clazzTemp.getDeclaredFields()) {
				ProtoMember protoMember = field.getAnnotation(ProtoMember.class);
				if (protoMember != null) {
					fieldTagValueMap.put(field.getName().toUpperCase(), protoMember.value());
				}
			}
		}
	}

	/**
	 * 获得某一个字段的ProtoTagValue
	 * 
	 * @param fieldName
	 * @return
	 */
	private Integer getProtoMemberValue(String fieldName) {
		return fieldTagValueMap.get(fieldName.toUpperCase());
	}

	public static byte[] toBytes(Class<?> clazz) {
		String path = "/" + clazz.getName().replaceAll("\\.", "/") + ".class";
		try {
			InputStream input = clazz.getResourceAsStream(path);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int len = 0;
			byte[] b = new byte[1024];
			while ((len = input.read(b, 0, b.length)) != -1) {
				baos.write(b, 0, len);
			}
			byte[] buffer = baos.toByteArray();
			return buffer;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> createClass(ClassFile classFile, Class<T> clazz) throws Exception {
		defineClass1.setAccessible(true);
		Object[] args = new Object[] { null, classFile.getBuffer(), new Integer(0),
				new Integer(classFile.getBuffer().length) };
		Class<T> newClass = (Class<T>) defineClass1.invoke(Thread.currentThread().getContextClassLoader(), args);
		defineClass1.setAccessible(false);
		return newClass;
	}
}

enum IndexType {
	CLASS, CLASS_DESC, INTERFACE_ENHANCE, SUPER_CLASS, PUT_SERIALIZATION_FIELD_TAG
}
