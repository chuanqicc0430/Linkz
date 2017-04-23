package cn.net.cvtt.lian.common.serialization.protobuf.generator.field;

import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoConfig;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoFieldType;

/**
 * 
 * <b>描述: </b>用于序列化组件，这是一个Guid类型的解释器，在自动生成源码的过程中发现类某一个字段为Guid类型时，会自动调用此解释器，
 * 用于在创建源码时解释此字段如何序列化( write方法)、如何反序列化(parse方法)以及如何计算序列化长度
 * <p>
 * <b>功能: </b>Guid类型的解释器,解释此字段如何序列化( write方法)、如何反序列化(parse方法)以及如何计算序列化长度
 * <p>
 * <b>用法: </b>该类由序列化组件在遍历类中的字段时遇到Guid类型时调用,外部无需调用
 * <p>
 * 
 * @author 
 * 
 */
public class GuidFieldInterpreter extends AbstractFieldInterpreter {

	private static GuidFieldInterpreter INSTANCE;

	/**
	 * 表示序列化代码的格式，期待一个类似<br>
	 * <code>if(data.getGuid() != null){ output.writeTag(number, 2);cn.net.cvtt.lian.common.serialization.protobuf.ProtoGuid.serialize(data.getGuid(), output, true);}</code>
	 * <br>
	 * 的输出
	 */
	private static final String WRITE_CODE_TEMPLATE = "if( ${data}.${getterName}() != null){ ${output}.writeTag(${number}, 2);${package_core}.ProtoGuid.serialize(${data}.${getterName}(), ${output}, true);}";

	/**
	 * 表示获取序列化长度的代码格式，期待一个类似<br>
	 * <code>if(data.getGuid() != null) size += ( 19 + ${package_core}.CodedOutputStream.computeTagSize(${number}));</code>
	 * <br>
	 * 的输出,因为这个Guid是一个固定长度，因此这样输出了
	 */
	private static final String SIZE_CODE_TEMPLATE = "if( ${data}.${getterName}() != null) size += ( 19 + ${package_core}.CodedOutputStream.computeTagSize(${number}));";

	/**
	 * 表示反序列化代码的格式，期待一个类似于<br>
	 * <code>data.setGuid(ProtoGuid.deserialize(input););</code><br>
	 * 的输出
	 */
	private static final String PARSE_CODE_TEMPLATE = "${data}.${setterName}(${package_core}.ProtoGuid.deserialize(${input}));";

	/**
	 * 用于数组或集合类时序列化代码的格式，期待一个类似<br>
	 * <code>output.writeTag(number, 2);cn.net.cvtt.lian.common.serialization.protobuf.ProtoGuid.serialize(data.getGuid(), output, true);</code>
	 * <br>
	 * 的输出
	 */
	private static final String WRITE_CODE_ARRAY_TEMPLATE = "if( value != null){ ${output}.writeTag(${number}, 2);${package_core}.ProtoGuid.serialize(value, ${output}, true);}";

	/**
	 * 用于数组或集合类时取序列化长度的代码格式，期待一个类似<br>
	 * <code>size += ( 19 + ${package_core}.protobuf.CodedOutputStream.computeTagSize(${number}));;</code>
	 * <br>
	 * 的输出
	 */
	private static final String SIZE_CODE_ARRAY_TEMPLATE = "size += ( 19 + ${package_core}.CodedOutputStream.computeTagSize(${number}));";

	/**
	 * 用于数组或集合类时反序列化代码的格式，期待一个类似于<br>
	 * <code>fieldValue = ProtoGuid.deserialize(input);</code><br>
	 * 的输出
	 */
	private static final String PARSE_CODE_ARRAY_TEMPLATE = "fieldValue = ${package_core}.ProtoGuid.deserialize(${input});";

	/**
	 * 构造方法必须要求持有字段枚举类型的引用
	 * 
	 * @param protoFieldType
	 */
	public GuidFieldInterpreter(ProtoFieldType protoFieldType) {
		super(protoFieldType);
	}

	/**
	 * 单例模式，获得当前对象
	 * 
	 * @return
	 */
	public static synchronized GuidFieldInterpreter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new GuidFieldInterpreter(ProtoFieldType.GUID);
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
		String writeCode = getWriteCodeTemplate();
		writeCode = writeCode.replaceAll("\\$\\{output\\}", VARIABLE_NAME_OUTPUTSTREAM);
		writeCode = writeCode.replaceAll("\\$\\{streamType\\}", protoFieldType.streamType);
		writeCode = writeCode.replaceAll("\\$\\{number\\}", String.valueOf(fieldInformation.getCurrentNumber()));
		writeCode = writeCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		writeCode = writeCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		writeCode = writeCode.replaceAll("\\$\\{package_core\\}", ProtoConfig.PACKAGE_CORE);
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
		sizeCode = sizeCode.replaceAll("\\$\\{streamType\\}", protoFieldType.streamType);
		sizeCode = sizeCode.replaceAll("\\$\\{number\\}", String.valueOf(fieldInformation.getCurrentNumber()));
		sizeCode = sizeCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		sizeCode = sizeCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		sizeCode = sizeCode.replaceAll("\\$\\{package_core\\}", ProtoConfig.PACKAGE_CORE);
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
		parseCode = parseCode.replaceAll("\\$\\{package_core\\}", ProtoConfig.PACKAGE_CORE);
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
		writeCode = writeCode.replaceAll("\\$\\{package_core\\}", ProtoConfig.PACKAGE_CORE);
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
		sizeCode = sizeCode.replaceAll("\\$\\{package_core\\}", ProtoConfig.PACKAGE_CORE);
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
		parseCode = parseCode.replaceAll("\\$\\{package_core\\}", ProtoConfig.PACKAGE_CORE);
		parseCode = parseCode.replaceAll("\\$\\{setterName\\}", getSetterName(fieldInformation));
		parseCode = parseCode.replaceAll("\\$\\{input\\}", VARIABLE_NAME_INPUTSTREAM);
		parseCode = parseCode.replaceAll("\\$\\{streamType\\}", protoFieldType.streamType);
		parseCode = parseCode.replaceAll("\\$\\{package_core\\}", ProtoConfig.PACKAGE_CORE);
		return parseCode;
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

	/**
	 * 为数组或集合类使用的序列化代码的模板，如果子类中对于这个模板有修改，则重写此方法既可
	 * 
	 * @return
	 */
	protected String getWriteCodeForArrayTemplate() {
		return WRITE_CODE_ARRAY_TEMPLATE;
	}

	/**
	 * 为数组或集合类使用的序列化长度计算代码的模板，如果子类中对于这个模板有修改，则重写此方法既可
	 * 
	 * @return
	 */
	protected String getSizeCodeForArrayTemplate() {
		return SIZE_CODE_ARRAY_TEMPLATE;
	}

	/**
	 * 为数组或集合类使用的反序列化代码的模板，如果子类中对于这个模板有修改，则重写此方法既可
	 * 
	 * @return
	 */
	protected String getParseCodeForArrayTemplate() {
		return PARSE_CODE_ARRAY_TEMPLATE;
	}

}
