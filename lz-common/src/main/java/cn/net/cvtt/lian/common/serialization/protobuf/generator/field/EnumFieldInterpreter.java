package cn.net.cvtt.lian.common.serialization.protobuf.generator.field;

import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoConfig;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoFieldType;
import cn.net.cvtt.lian.common.serialization.protobuf.util.ClassUtils;
import cn.net.cvtt.lian.common.serialization.protobuf.util.ProtoGenericsUtils;

/**
 * <b>描述: </b>用于序列化组件，这是一个枚举类型的解释器，在自动生成源码的过程中发现类某一个字段为枚举类型时，会自动调用此解释器，
 * 用于在创建源码时解释此字段如何序列化( write方法)、如何反序列化(parse方法)以及如何计算序列化长度
 * <p>
 * <b>功能: </b>枚举类型的解释器,用于在创建源码时解释此字段如何序列化( write方法)、如何反序列化(parse方法)以及如何计算序列化长度
 * <p>
 * <b>用法: </b>该类由序列化组件在遍历类中的字段时遇到枚举类型时调用,外部无需调用
 * <p>
 * 
 * @author 
 * 
 */
public class EnumFieldInterpreter extends AbstractFieldInterpreter {

	private static FieldInterpreter INSTANCE = null;

	/**
	 * 表示序列化代码的格式，期待一个类似<br>
	 * <code>if(data.getEnum_obj() != null) output.writeEnum(1, data.getEnum_obj().intValue());</code>
	 * <br>
	 * 的输出
	 */
	private static final String WRITE_CODE_TEMPLATE = "if( ${data}.${getterName}() != null) ${output}.write${streamType}(${number}, ${data}.${getterName}().intValue());";

	/**
	 * 表示获取序列化长度的代码格式，期待一个类似<br>
	 * <code>if(data.getEnum_obj() != null) size += cn.net.cvtt.lian.common.serialization.protobuf.CodedOutputStream.computeEnumSize(1, data.getEnum_obj().intValue());</code>
	 * <br>
	 * 的输出
	 */
	private static final String SIZE_CODE_TEMPLATE = "if( ${data}.${getterName}() != null) size += ${package_core}.CodedOutputStream.compute${streamType}Size(${number},${data}.${getterName}().intValue());";

	/**
	 * 表示反序列化代码的格式，期待一个类似于<br>
	 * <code>data.setEnum_obj((com.test.serialization.PersonEnum)cn.net.cvtt.lian.common.util.EnumParser.parseInt(com.test.serialization.PersonEnum.class,input.readEnum()));</code>
	 * <br>
	 * 的输出
	 */
	private static final String PARSE_CODE_TEMPLATE = "${data}.${setterName}((${enum_class})cn.net.cvtt.lian.common.util.EnumParser.parseInt(${enum_class}.class,${input}.read${streamType}()));";

	/**
	 * 表示序列化代码的格式，期待一个类似<br>
	 * <code>output.writeEnum(1, value.intValue());</code> <br>
	 * 的输出
	 */
	private static final String WRITE_CODE_ARRAY_TEMPLATE = "${output}.write${streamType}(${number}, value.intValue());";

	/**
	 * 表示获取序列化长度的代码格式，期待一个类似<br>
	 * <code>size += cn.net.cvtt.lian.common.serialization.protobuf.CodedOutputStream.computeEnumSize(1, value.intValue());</code>
	 * <br>
	 * 的输出
	 */
	private static final String SIZE_CODE_ARRAY_TEMPLATE = "size += ${package_core}.CodedOutputStream.compute${streamType}Size(${number},value.intValue());";

	/**
	 * 表示反序列化代码的格式，期待一个类似于<br>
	 * <code>fieldValue = (com.test.serialization.PersonEnum)cn.net.cvtt.lian.common.util.EnumParser.parseInt(com.test.serialization.PersonEnum.class,input.readEnum());</code>
	 * <br>
	 * 的输出
	 */
	private static final String PARSE_CODE_ARRAY_TEMPLATE = "fieldValue = (${enum_class})cn.net.cvtt.lian.common.util.EnumParser.parseInt(${enum_class}.class,${input}.read${streamType}());";

	/**
	 * 单例模式，获得当前对象
	 * 
	 * @return
	 */
	public synchronized static FieldInterpreter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new EnumFieldInterpreter(ProtoFieldType.ENUM);
		}
		return INSTANCE;
	}

	private EnumFieldInterpreter(ProtoFieldType protoFieldType) {
		super(protoFieldType);
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
		String writeCode = getWriteCodeTemplate();
		writeCode = writeCode.replaceAll("\\$\\{output\\}", VARIABLE_NAME_OUTPUTSTREAM);
		writeCode = writeCode.replaceAll("\\$\\{streamType\\}", protoFieldType.streamType);
		writeCode = writeCode.replaceAll("\\$\\{number\\}", String.valueOf(fieldInformation.getCurrentNumber()));
		writeCode = writeCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		writeCode = writeCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
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
		String sizeCode = getSizeCodeTemplate();
		sizeCode = sizeCode.replaceAll("\\$\\{package_core\\}", ProtoConfig.PACKAGE_CORE);
		sizeCode = sizeCode.replaceAll("\\$\\{streamType\\}", protoFieldType.streamType);
		sizeCode = sizeCode.replaceAll("\\$\\{number\\}", String.valueOf(fieldInformation.getCurrentNumber()));
		sizeCode = sizeCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		sizeCode = sizeCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
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
		String parseCode = getParseCodeTemplate();
		parseCode = parseCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		parseCode = parseCode.replaceAll("\\$\\{setterName\\}", getSetterName(fieldInformation));
		parseCode = parseCode.replaceAll("\\$\\{input\\}", VARIABLE_NAME_INPUTSTREAM);
		parseCode = parseCode.replaceAll("\\$\\{streamType\\}", protoFieldType.streamType);
		parseCode = parseCode.replaceAll("\\$\\{enum_class\\}",
				ClassUtils.processClassName(fieldInformation.getField().getType().getName()));
		return parseCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.cvtt.lian.common.serialization.protobuf.generator.field.AbstractFieldInterpreter
	 * #getWriteCodeForArray(cn.net.cvtt.lian.common.serialization.protobuf.generator.field.
	 * FieldInformation)
	 */
	@Override
	public String getWriteCodeForArray(FieldInformation fieldInformation) {
		String writeCode = getWriteCodeForArrayTemplate();
		writeCode = writeCode.replaceAll("\\$\\{output\\}", VARIABLE_NAME_OUTPUTSTREAM);
		writeCode = writeCode.replaceAll("\\$\\{streamType\\}", protoFieldType.streamType);
		writeCode = writeCode.replaceAll("\\$\\{number\\}", String.valueOf(fieldInformation.getCurrentNumber()));
		writeCode = writeCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		writeCode = writeCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		return writeCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.cvtt.lian.common.serialization.protobuf.generator.field.AbstractFieldInterpreter
	 * #getSizeCodeForArray(cn.net.cvtt.lian.common.serialization.protobuf.generator.field.
	 * FieldInformation)
	 */
	@Override
	public String getSizeCodeForArray(FieldInformation fieldInformation) {
		String sizeCode = getSizeCodeForArrayTemplate();
		sizeCode = sizeCode.replaceAll("\\$\\{package_core\\}", ProtoConfig.PACKAGE_CORE);
		sizeCode = sizeCode.replaceAll("\\$\\{streamType\\}", protoFieldType.streamType);
		sizeCode = sizeCode.replaceAll("\\$\\{number\\}", String.valueOf(fieldInformation.getCurrentNumber()));
		sizeCode = sizeCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		sizeCode = sizeCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		return sizeCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.cvtt.lian.common.serialization.protobuf.generator.field.AbstractFieldInterpreter
	 * #getParseCodeForArray(cn.net.cvtt.lian.common.serialization.protobuf.generator.field.
	 * FieldInformation)
	 */
	@Override
	public String getParseCodeForArray(FieldInformation fieldInformation) {
		String parseCode = getParseCodeForArrayTemplate();
		parseCode = parseCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		parseCode = parseCode.replaceAll("\\$\\{setterName\\}", getSetterName(fieldInformation));
		parseCode = parseCode.replaceAll("\\$\\{input\\}", VARIABLE_NAME_INPUTSTREAM);
		parseCode = parseCode.replaceAll("\\$\\{streamType\\}", protoFieldType.streamType);
		parseCode = parseCode.replaceAll("\\$\\{enum_class\\}",
				ProtoGenericsUtils.getClassFullName(fieldInformation.getCurrentType()));
		return parseCode;
	}

	protected String getWriteCodeTemplate() {
		return WRITE_CODE_TEMPLATE;
	}

	protected String getSizeCodeTemplate() {
		return SIZE_CODE_TEMPLATE;
	}

	protected String getParseCodeTemplate() {
		return PARSE_CODE_TEMPLATE;
	}

	protected String getWriteCodeForArrayTemplate() {
		return WRITE_CODE_ARRAY_TEMPLATE;
	}

	protected String getSizeCodeForArrayTemplate() {
		return SIZE_CODE_ARRAY_TEMPLATE;
	}

	protected String getParseCodeForArrayTemplate() {
		return PARSE_CODE_ARRAY_TEMPLATE;
	}
}
