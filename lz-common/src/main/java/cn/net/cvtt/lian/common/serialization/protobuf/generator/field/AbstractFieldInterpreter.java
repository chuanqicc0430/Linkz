package cn.net.cvtt.lian.common.serialization.protobuf.generator.field;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoType;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoFieldType;

/**
 * 
 * <b>描述:
 * </b>当使用Protobuf格式进行序列化或反序列化时，会先生成对应类的序列化辅助类代码，在生成辅助代码时，会逐个遍历待处理类的待序列化字段，
 * 根据字段类型及 注释{@link ProtoMember}信息 ，生成每个字段的序列化处理代码，再将全部字段的序列化处理代码组合起来，创建出序列化辅助类
 * ，该类就是提供每种字段类型应该如何进行序列化处理的代码的抽象类父类,它提供了在处理每一种字段类型时的基础方法与公共实现.
 * <p>
 * <b>功能: </b>用于序列化组件在生成辅助代码时使用，由它来决定每一种字段类型应该如何序列化、如何反序列化以及如何获取序列化的长度
 * <p>
 * <b>用法: </b>该类由序列化组件在遍历类中的字段时调用
 * <p>
 * 
 * @author 
 * 
 */
public abstract class AbstractFieldInterpreter implements FieldInterpreter {

	/** 变量名称的命名,待序列化的数据对象命名 */
	public static final String VARIABLE_NAME_DATA = "data";

	/** 变量名称的命名,输入流的命名 */
	public static final String VARIABLE_NAME_INPUTSTREAM = "input";

	/** 变量名称的命名,输出流的命名 */
	public static final String VARIABLE_NAME_OUTPUTSTREAM = "output";

	/** 持有的字段类型的枚举引用 */
	protected ProtoFieldType protoFieldType;

	/**
	 * 表示反序列化代码的格式，期待一个类似于<br>
	 * <code>if(data.getGuid() == null) return false;</code><br>
	 * 的输出
	 */
	private static final String REQUIRED_CODE_TEMPLATE = "if( ${data}.${getterName}() == null) return false;";

	/**
	 * 构造方法要求将正确的枚举对象类型传入
	 * 
	 * @param protoFieldType
	 */
	public AbstractFieldInterpreter(ProtoFieldType protoFieldType) {
		this.protoFieldType = protoFieldType;
	}

	/**
	 * 获取作用域为当前实例的代码
	 * 
	 * @param fieldInformation
	 * @return
	 */
	@Override
	public String getGlobalCode(FieldInformation fieldInformation) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.cvtt.lian.common.serialization.protobuf.generator.field.FieldInterpreter#
	 * getRequiredCode
	 * (cn.net.cvtt.lian.common.serialization.protobuf.generator.field.FieldInformation)
	 */
	@Override
	public String getRequiredCode(FieldInformation fieldInformation) {
		String requiredCode = REQUIRED_CODE_TEMPLATE;
		requiredCode = requiredCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		requiredCode = requiredCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		return requiredCode;
	}

	/**
	 * 这是为了针对数组或集合了而准备的，通过集合类适配器，将该方法适配成正常的WriteCode方法<br>
	 * 这个方法子类可以不用实现，如果不事先该方法，代表此类型不支持数组操作
	 * 
	 * @param fieldInformation
	 * @return
	 */
	public String getWriteCodeForArray(FieldInformation fieldInformation) {
		throw new UnsupportedOperationException(this.getClass() + " unsupported WriteCodeForArray");
	}

	/**
	 * 这是为了针对数组或集合了而准备的，通过集合类适配器，将该方法适配成正常的SizeCode方法<br>
	 * 这个方法子类可以不用实现，如果不事先该方法，代表此类型不支持数组操作
	 * 
	 * @param fieldInformation
	 * @return
	 */
	public String getSizeCodeForArray(FieldInformation fieldInformation) {
		throw new UnsupportedOperationException(this.getClass() + " unsupported SizeCodeForArray");
	}

	/**
	 * 这是为了针对数组或集合了而准备的，通过集合类适配器，将该方法适配成正常的ParseCode方法<br>
	 * 这个方法子类可以不用实现，如果不事先该方法，代表此类型不支持数组操作
	 * 
	 * @param fieldInformation
	 * @return
	 */
	public String getParseCodeForArray(FieldInformation fieldInformation) {
		throw new UnsupportedOperationException(this.getClass() + " unsupported ParseCodeForArray");
	}

	/**
	 * 这是一个获取当前类型对象的Tag值，这个Tag值是Google protobuf协议中重要的一部分，用于反序列化时case时使用
	 * 
	 * @param fieldInformation
	 * @return
	 */
	public int getTagType(FieldInformation fieldInformation) {
		return protoFieldType.tagType;
	}

	/**
	 * 通过此处返回序列化或反序列化时写入的流类型(用字符串标识，例如Int32或Fixed32)
	 * 
	 * @param fieldInformation
	 * @return
	 */
	protected String getStreamTypeString(FieldInformation fieldInformation) {
		ProtoType protoType = fieldInformation.getProtoMember().type();
		protoType = ProtoFieldType.processProtoType(protoFieldType, protoType);
		if (protoType == null || protoType == ProtoType.AUTOMATIC) {
			return protoFieldType.streamType;
		} else {
			return protoType.getProtoStreamString();
		}
	}

	/**
	 * 获得一个类的Getter方法名称
	 * 
	 * @param fieldInformation
	 * @return
	 */
	@Override
	public String getGetterName(FieldInformation fieldInformation) {

		StringBuilder sb = new StringBuilder();
		if (fieldInformation.getCurrentType() == boolean.class) {

			// 我们的JavaBean的get/set方法不是按照标准的命名方法来写，针对boolean不是用isBoolean()而是用getBoolean(),
			// 所以提供了isXXX和getXXX两种的自适应
			StringBuffer method1 = new StringBuffer();
			method1.append("get").append(String.valueOf(fieldInformation.getField().getName().charAt(0)).toUpperCase())
					.append(fieldInformation.getField().getName().substring(1));
			// method2是正常JavaBean规范应该书写的格式
			StringBuffer method2 = new StringBuffer();
			String fieldName = fieldInformation.getField().getName();
			if (fieldName.length() > 1 && (fieldName.substring(0, 2).startsWith("is"))) {

				int threeChar = fieldInformation.getField().getName().charAt(2);
				// 如果FieldName = isBoolean , 则 GetterName = isBoolean
				if (threeChar >= 65 && threeChar <= 90) {
					method2.append(String.valueOf(fieldInformation.getField().getName().charAt(0)).toLowerCase())
							.append(fieldInformation.getField().getName().substring(1));
				} else {
					// 如果FieldName = isboolean , 则 GetterName = isIsboolean
					method2.append("is")
							.append(String.valueOf(fieldInformation.getField().getName().charAt(0)).toUpperCase())
							.append(fieldInformation.getField().getName().substring(1));
				}

			} else {
				// 否则 isBoolean
				method2.append("is")
						.append(String.valueOf(fieldInformation.getField().getName().charAt(0)).toUpperCase())
						.append(fieldInformation.getField().getName().substring(1));
			}
			java.lang.reflect.Method[] methods = fieldInformation.getOutterClass().getMethods();
			for (java.lang.reflect.Method method : methods) {
				if (method.getName().equals(method1.toString())) {
					return method1.toString();
				} else if (method.getName().equals(method2.toString())) {
					return method2.toString();
				}
			}
			throw new RuntimeException(String.format("Not Found Getter method in [Class: %s , FieldName : %s]",
					fieldInformation.getOutterClass().toString(), fieldName));
		} else {
			sb.append("get").append(String.valueOf(fieldInformation.getField().getName().charAt(0)).toUpperCase())
					.append(fieldInformation.getField().getName().substring(1));
		}
		return sb.toString();
	}

	/**
	 * 获得一个类的Setter方法名称
	 * 
	 * @param fieldInformation
	 * @return
	 */
	@Override
	public String getSetterName(FieldInformation fieldInformation) {

		StringBuilder sb = new StringBuilder();
		if (fieldInformation.getCurrentType() == boolean.class) {

			StringBuffer method1 = new StringBuffer();
			method1.append("set").append(String.valueOf(fieldInformation.getField().getName().charAt(0)).toUpperCase())
					.append(fieldInformation.getField().getName().substring(1));

			StringBuffer method2 = new StringBuffer();
			String fieldName = fieldInformation.getField().getName();
			if (fieldName.length() > 1 && (fieldName.substring(0, 2).startsWith("is"))) {

				int threeChar = fieldInformation.getField().getName().charAt(2);
				if (threeChar >= 65 && threeChar <= 90) {
					method2.append("set").append(fieldInformation.getField().getName().substring(2));
				} else {
					method2.append("set")
							.append(String.valueOf(fieldInformation.getField().getName().charAt(0)).toUpperCase())
							.append(fieldInformation.getField().getName().substring(1));
				}

			} else {
				method2.append("set")
						.append(String.valueOf(fieldInformation.getField().getName().charAt(0)).toUpperCase())
						.append(fieldInformation.getField().getName().substring(1));
			}
			java.lang.reflect.Method[] methods = fieldInformation.getOutterClass().getMethods();
			for (java.lang.reflect.Method method : methods) {
				if (method.getName().equals(method1.toString())) {
					return method1.toString();
				} else if (method.getName().equals(method2.toString())) {
					return method2.toString();
				}
			}
			throw new RuntimeException(String.format("Not Found Setter method in [Class: %s , FieldName : %s]",
					fieldInformation.getOutterClass().toString(), fieldName));

		} else {
			sb.append("set").append(String.valueOf(fieldInformation.getField().getName().charAt(0)).toUpperCase())
					.append(fieldInformation.getField().getName().substring(1));
		}
		return sb.toString();
	}

}
