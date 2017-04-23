package cn.net.cvtt.lian.common.serialization.protobuf.generator.field;

import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoConfig;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoBuilderCodeGenerator;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoFieldType;
import cn.net.cvtt.lian.common.serialization.protobuf.util.ProtoGenericsUtils;

/**
 * <b>描述: </b>用于序列化组件，这个MessageFieldInterpreter是{@link ProtoEntity}
 * 类型的解释器，当一个类中的字段为{@link ProtoEntity}
 * 类型时，此解释器被启用，用于解释这个类型的字段如何序列化、如何反序列化以及如何获取序列化的长度
 * <p>
 * <b>功能: </b>用于解释{@link ProtoEntity}类型的字段如何序列化、如何反序列化以及如何获取序列化的长度
 * <p>
 * <b>用法: </b>该类由序列化组件在遍历类中的字段时遇到{@link ProtoEntity}类型时调用,外部无需调用
 * <p>
 * 
 * @author 
 * 
 */
public class MessageFieldInterpreter extends AbstractFieldInterpreter {

	private static FieldInterpreter INSTANCE = null;

	/**
	 * 表示序列化代码的格式，期待一个类似<br>
	 * <code>if(data.getPersonTest() != null) output.writeMessage(1, new cn.net.cvtt.lian.common.serialization.protobuf.extension.PersonProtoBuilder(data.getPersonTest()));</code>
	 * <br>
	 * 的输出,基类提供的公用模板已经无法阻挡它的变化了，因此在这里覆盖了基类的模板,多了一个${proto_builder}变量
	 */
	private static final String WRITE_CODE_TEMPLATE = "if( ${builder_name} != null) ${output}.write${streamType}(${number}, ${builder_name});";

	/**
	 * 表示获取序列化长度的代码格式，期待一个类似<br>
	 * <code>if(data.getPersonTest() != null) size += cn.net.cvtt.lian.common.serialization.protobuf.CodedOutputStream.computeMessageSize(1, new cn.net.cvtt.lian.common.serialization.protobuf.extension.PersonProtoBuilder(data.getPersonTest()));</code>
	 * <br>
	 * 的输出,基类提供的公用模板已经无法阻挡它的变化了，因此在这里覆盖了基类的模板,多了一个${proto_builder}变量
	 */
	private static final String SIZE_CODE_TEMPLATE = "if( ${builder_name} != null) size += ${package_core}.CodedOutputStream.compute${streamType}Size(${number},${builder_name});";

	/**
	 * 表示反序列化代码的格式，期待一个类似于<br>
	 * <code>com.test.serialization.Person bean = new com.test.serialization.Person();cn.net.cvtt.lian.common.serialization.protobuf.extension.PersonProtoBuilder builder = new cn.net.cvtt.lian.common.serialization.protobuf.extension.PersonProtoBuilder(bean);input.readMessage(builder);data.setPersonTest(bean);</code>
	 * <br>
	 * 的输出,基类提供的公用模板已经无法阻挡它的变化了，因此在这里覆盖了基类的模板,这里多了一个${message_class}变量和${
	 * proto_builder}变量， 因此需要覆盖相应的getParseCode方法，用取到的基本字符串，将这个变量替换上去
	 */
	private static final String PARSE_CODE_TEMPLATE = "${message_class} bean = new ${message_class}();${proto_builder} builder = new ${proto_builder}(bean);${input}.read${streamType}(builder);${data}.${setterName}(bean);";

	/**
	 * 数组中表示序列化代码的格式，期待一个类似<br>
	 * <code> output.writeMessage(1, new cn.net.cvtt.lian.common.serialization.protobuf.extension.PersonProtoBuilder(value);</code>
	 * <br>
	 * 的输出,基类提供的公用模板已经无法阻挡它的变化了，因此在这里覆盖了基类的模板,多了一个${proto_builder}变量
	 */
	private static final String WRITE_CODE_ARRAY_TEMPLATE = "${output}.write${streamType}(${number}, new ${proto_builder}(value));";

	/**
	 * 数组中表示获取序列化长度的代码格式，期待一个类似<br>
	 * <code> size += cn.net.cvtt.lian.common.serialization.protobuf.CodedOutputStream.computeMessageSize(1, new cn.net.cvtt.lian.common.serialization.protobuf.extension.PersonProtoBuilder(value));</code>
	 * <br>
	 * 的输出,基类提供的公用模板已经无法阻挡它的变化了，因此在这里覆盖了基类的模板,多了一个${proto_builder}变量
	 */
	private static final String SIZE_CODE_ARRAY_TEMPLATE = "size += ${package_core}.CodedOutputStream.compute${streamType}Size(${number},new ${proto_builder}(value));";

	/**
	 * 数组中表示反序列化代码的格式，期待一个类似于<br>
	 * <code>com.test.serialization.Person fieldValue = new com.test.serialization.Person();cn.net.cvtt.lian.common.serialization.protobuf.extension.PersonProtoBuilder builder = new cn.net.cvtt.lian.common.serialization.protobuf.extension.PersonProtoBuilder(fieldValue);input.readMessage(builder);</code>
	 * <br>
	 * 的输出,基类提供的公用模板已经无法阻挡它的变化了，因此在这里覆盖了基类的模板,这里多了一个${message_class}变量和${
	 * proto_builder}变量， 因此需要覆盖相应的getParseCode方法，用取到的基本字符串，将这个变量替换上去
	 */
	private static final String PARSE_CODE_ARRAY_TEMPLATE = " fieldValue = new ${message_class}();${proto_builder} builder = new ${proto_builder}(fieldValue);${input}.read${streamType}(builder);";

	private static final String GLOBAL_CODE_TEMPLATE = "private ${proto_builder} ${builder_name} = null;{if(${data}.${getterName}() != null)${builder_name} = new ${proto_builder}(${data}.${getterName}());}";

	/**
	 * 单例模式，获得当前对象
	 * 
	 * @return
	 */
	public synchronized static FieldInterpreter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MessageFieldInterpreter(ProtoFieldType.MESSAGE);
		}
		return INSTANCE;
	}

	private MessageFieldInterpreter(ProtoFieldType protoFieldType) {
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
		writeCode = writeCode.replaceAll("\\$\\{builder_name\\}", getBuilderClassName(fieldInformation));
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
		sizeCode = sizeCode.replaceAll("\\$\\{builder_name\\}", getBuilderClassName(fieldInformation));
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
		parseCode = parseCode.replaceAll("\\$\\{proto_builder\\}",
				ProtoBuilderCodeGenerator.getBuilderClassFullName(fieldInformation.getField().getType()));
		parseCode = parseCode.replaceAll("\\$\\{message_class\\}", fieldInformation.getField().getType().getName()
				.replace("$", "."));
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
		writeCode = writeCode.replaceAll("\\$\\{proto_builder\\}", ProtoBuilderCodeGenerator
				.getBuilderClassFullName(ProtoGenericsUtils.getClass(fieldInformation.getCurrentType())));
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
		sizeCode = sizeCode.replaceAll("\\$\\{proto_builder\\}", ProtoBuilderCodeGenerator
				.getBuilderClassFullName(ProtoGenericsUtils.getClass(fieldInformation.getCurrentType())));
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
		parseCode = parseCode.replaceAll("\\$\\{proto_builder\\}", ProtoBuilderCodeGenerator
				.getBuilderClassFullName(ProtoGenericsUtils.getClass(fieldInformation.getCurrentType())));
		parseCode = parseCode.replaceAll("\\$\\{message_class\\}",
				ProtoGenericsUtils.getClassFullName(fieldInformation.getCurrentType()));
		return parseCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.cvtt.lian.common.serialization.protobuf.generator.field.AbstractFieldInterpreter
	 * #getGlobalCode(cn.net.cvtt.lian.common.serialization.protobuf.generator.field.
	 * FieldInformation)
	 */
	@Override
	public String getGlobalCode(FieldInformation fieldInformation) {
		String globalVariableCode = getGlobalCodeTemplate();
		globalVariableCode = globalVariableCode.replaceAll("\\$\\{proto_builder\\}", ProtoBuilderCodeGenerator
				.getBuilderClassFullName(ProtoGenericsUtils.getClass(fieldInformation.getCurrentType())));
		globalVariableCode = globalVariableCode.replaceAll("\\$\\{builder_name\\}",
				getBuilderClassName(fieldInformation));
		globalVariableCode = globalVariableCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		globalVariableCode = globalVariableCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		return globalVariableCode;
	}

	private String getBuilderClassName(FieldInformation fieldInformation) {
		StringBuilder builderClassName = new StringBuilder(
				ProtoBuilderCodeGenerator.getBuilderClassName(ProtoGenericsUtils.getClass(fieldInformation
						.getCurrentType())));
		builderClassName.setCharAt(0, Character.toLowerCase(builderClassName.charAt(0)));
		builderClassName.append(fieldInformation.getNumber());
		return builderClassName.toString();
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

	protected String getGlobalCodeTemplate() {
		return GLOBAL_CODE_TEMPLATE;
	}
}
