package cn.net.cvtt.lian.common.serialization.protobuf.generator.field;

import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoFieldType;
import cn.net.cvtt.lian.common.serialization.protobuf.util.ProtoGenericsUtils;

/**
 * 
 * <b>描述: </b>用于序列化组件，这是一个EnumType对象类型的字段解释器的适配器<br>
 * 通过它可以泛化出Byte、Character、Short、Integer、Long、String种类型的EnumType<br>
 * <b>请注意，本类型不支持数组或List、Map等其他集合类型</b>
 * <p>
 * <b>功能: </b>一个EnumType对象类型的字段解释器的适配器,它可以泛化出Byte、Character、Short、Integer、Long、
 * String种类型的EnumType
 * <p>
 * <b>用法: </b>该类由序列化组件在遍历类中的字段时遇到EnumType对象类型时调用,外部无需调用
 * <p>
 * 
 * @author 
 * 
 */
public class EnumTypeFieldInterpreter extends AbstractFieldInterpreter {

	private static FieldInterpreter INSTANCE = null;

	/**
	 * 表示序列化代码的格式，期待一个类似<br>
	 * <code>
	 * if(data.getEnumType() != null){ 
	 * 		java.lang.Character value = data.getEnumType().key();
	 * 		output.writeInt32(fieldNumber, value);
	 * }</code> <br>
	 * 的输出
	 */
	private static final String WRITE_CODE_TEMPLATE = "if( ${data}.${getterName}() != null){ ${java_type} value = ${data}.${getterName}().key(); ${write_array} }";

	/**
	 * 表示获取序列化长度的代码格式，期待一个类似<br>
	 * <code>if(data.getEnumType() != null){ java.lang.Character value = data.getEnumType().key(); size += cn.net.cvtt.lian.common.serialization.protobuf.CodedOutputStream.computeInt32Size(1,value);}</code>
	 * <br>
	 * 的输出
	 */
	private static final String SIZE_CODE_TEMPLATE = "if( ${data}.${getterName}() != null){${java_type} value = ${data}.${getterName}().key(); ${size_array} }";

	/**
	 * 表示反序列化代码的格式，期待一个类似于<br>
	 * <code>
	 * java.lang.Character fieldValue = null;
	 * fieldValue = java.lang.Character.valueOf((char)input.readInt32());
	 * if(fieldValue != null){
	 * 		EnumType.valueOf((Class<? extends cn.net.cvtt.lian.common.util.EnumType<${java_type}, ?>>cn.net.cvtt.lian.common.util.EnumType.getClass(),fieldValue));
	 * }
	 * data.getEnumType();</code> <br>
	 * 的输出
	 */
	private static final String PARSE_CODE_TEMPLATE = " ${java_type} fieldValue = null; ${parse_array} if(fieldValue!=null){ ${data}.${setterName}(cn.net.cvtt.lian.common.util.EnumType.valueOf(${java_real_type}.class,fieldValue));}";

	/**
	 * 构造方法必须要求持有字段枚举类型的引用
	 * 
	 * @param protoFieldType
	 */
	private EnumTypeFieldInterpreter(ProtoFieldType protoFieldType) {
		super(protoFieldType);
	}

	/**
	 * 单例模式，获得当前对象
	 * 
	 * @return
	 */
	public synchronized static FieldInterpreter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new EnumTypeFieldInterpreter(ProtoFieldType.ENUMTYPE);
		}
		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.cvtt.lian.common.serialization.protobuf.generator.field.FieldInterpreter#
	 * getWriteCode
	 * (cn.net.cvtt.lian.common.serialization.protobuf.generator.field.FieldInformation)
	 */
	@Override
	public String getWriteCode(FieldInformation fieldInformation) {
		Class<?> genericFieldType = ProtoGenericsUtils.getSuperClassGenricClass(fieldInformation.getField(), 0);
		String writeCode = getWriteCodeTemplate();
		writeCode = writeCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		writeCode = writeCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		writeCode = writeCode.replaceAll("\\$\\{java_type\\}", genericFieldType.getName());
		writeCode = writeCode.replaceAll("\\$\\{write_array\\}", ProtoFieldType.valueOf(genericFieldType)
				.getFieldInterpreter().getWriteCodeForArray(fieldInformation.setCurrentType(genericFieldType)));
		return writeCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.cvtt.lian.common.serialization.protobuf.generator.field.FieldInterpreter#
	 * getSizeCode
	 * (cn.net.cvtt.lian.common.serialization.protobuf.generator.field.FieldInformation)
	 */
	@Override
	public String getSizeCode(FieldInformation fieldInformation) {
		Class<?> genericFieldType = ProtoGenericsUtils.getSuperClassGenricClass(fieldInformation.getField(), 0);
		String sizeCode = getSizeCodeTemplate();
		sizeCode = sizeCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		sizeCode = sizeCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		sizeCode = sizeCode.replaceAll("\\$\\{java_type\\}", genericFieldType.getName());
		sizeCode = sizeCode.replaceAll("\\$\\{size_array\\}", ProtoFieldType.valueOf(genericFieldType)
				.getFieldInterpreter().getSizeCodeForArray(fieldInformation.setCurrentType(genericFieldType)));
		return sizeCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.cvtt.lian.common.serialization.protobuf.generator.field.FieldInterpreter#
	 * getParseCode
	 * (cn.net.cvtt.lian.common.serialization.protobuf.generator.field.FieldInformation)
	 */
	@Override
	public String getParseCode(FieldInformation fieldInformation) {
		Class<?> genericFieldType = ProtoGenericsUtils.getSuperClassGenricClass(fieldInformation.getField(), 0);
		String parseCode = getParseCodeTemplate();
		parseCode = parseCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		parseCode = parseCode.replaceAll("\\$\\{setterName\\}", getSetterName(fieldInformation));
		parseCode = parseCode.replaceAll("\\$\\{java_type\\}", genericFieldType.getName());
		parseCode = parseCode.replaceAll("\\$\\{java_real_type\\}", fieldInformation.getField().getType().getName());
		parseCode = parseCode.replaceAll("\\$\\{parse_array\\}", ProtoFieldType.valueOf(genericFieldType)
				.getFieldInterpreter().getParseCodeForArray(fieldInformation.setCurrentType(genericFieldType)));
		return parseCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.cvtt.lian.common.serialization.protobuf.generator.field.AbstractFieldInterpreter
	 * #
	 * getTagType(cn.net.cvtt.lian.common.serialization.protobuf.generator.field.FieldInformation
	 * )
	 */
	@Override
	public int getTagType(FieldInformation fieldInformation) {

		Class<?> genericFieldType = ProtoGenericsUtils.getSuperClassGenricClass(fieldInformation.getCurrentType(), 0);
		return ProtoFieldType.valueOf(genericFieldType).getFieldInterpreter().getTagType(fieldInformation);
	}

	/**
	 * 获得序列化代码的模板，如果子类中对于这个模板有修改，则重写此方法既可
	 * 
	 * @return
	 */
	protected String getWriteCodeTemplate() {
		return WRITE_CODE_TEMPLATE;
	}

	/**
	 * 获得序列化长度计算代码的模板，如果子类中对于这个模板有修改，则重写此方法既可
	 * 
	 * @return
	 */
	protected String getSizeCodeTemplate() {
		return SIZE_CODE_TEMPLATE;
	}

	/**
	 * 获得反序列化代码的模板，如果子类中对于这个模板有修改，则重写此方法既可
	 * 
	 * @return
	 */
	protected String getParseCodeTemplate() {
		return PARSE_CODE_TEMPLATE;
	}

	// /**
	// * 为数组或集合类使用的序列化代码的模板，如果子类中对于这个模板有修改，则重写此方法既可
	// *
	// * @return
	// */
	// protected String getWriteCodeForArrayTemplate() {
	// return WRITE_CODE_ARRAY_TEMPLATE;
	// }
	//
	// /**
	// * 为数组或集合类使用的序列化长度计算代码的模板，如果子类中对于这个模板有修改，则重写此方法既可
	// *
	// * @return
	// */
	// protected String getSizeCodeForArrayTemplate() {
	// return SIZE_CODE_ARRAY_TEMPLATE;
	// }
	//
	// /**
	// * 为数组或集合类使用的反序列化代码的模板，如果子类中对于这个模板有修改，则重写此方法既可
	// *
	// * @return
	// */
	// protected String getParseCodeForArrayTemplate() {
	// return PARSE_CODE_ARRAY_TEMPLATE;
	// }

}
