package cn.net.cvtt.lian.common.serialization.protobuf.generator.field;

import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoConfig;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoFieldType;
import cn.net.cvtt.lian.common.serialization.protobuf.util.ProtoGenericsUtils;

/**
 * <b>描述:
 * </b>用于序列化组件，这是一个java.sql.Date类型的解释器，在自动生成源码的过程中发现类某一个字段为java.sql.Date类型时
 * ，会自动调用此解释器， 用于在创建源码时解释此字段如何序列化( write方法)、如何反序列化(parse方法)以及如何计算序列化长度
 * <p>
 * <b>功能: </b>java.sql.Date类型的解释器,解释java.sql.Date类型字段如何序列化(
 * write方法)、如何反序列化(parse方法)以及如何计算序列化长度
 * <p>
 * <b>用法: </b>该类由序列化组件在遍历类中的字段时遇到java.sql.Date类型时调用,外部无需调用
 * <p>
 * 
 * @author 
 * 
 */
public class SqlDateFieldInterpreter extends AbstractFieldInterpreter {

	private static FieldInterpreter INSTANCE = null;

	/**
	 * 表示序列化代码的格式，期待一个类似<br>
	 * <code>
	 * if(data.getData_obj() != null){
	 * 		java.sql.Date date = data.getData_obj();
	 * 		if(protoMember.timezone().equalsIgnoreCase("UTC")){
	 * 			date = cn.net.cvtt.lian.common.util.DateUtil.getGMTDate(date);
	 * 		}
	 * 		Long millisecond = (date.getTime() + TimeZone.getDefault().getOffset()) * 10000;
	 * 		if (millisecond >= 2534022719990000000L)
	 * 			millisecond = 2534023007999999999L;
	 * 		else if (millisecond < -621355968000000000L)
	 * 			millisecond = -621355968000000000L;
	 * 		output.writeFixed64(fieldNumber, millisecond);
	 * }
	 * </code> <br>
	 * 的输出
	 */
	private static final String WRITE_CODE_TEMPLATE = "if( ${data}.${getterName}() != null){ java.sql.Date date = ${data}.${getterName}(); ${getGMTDate_code} Long millisecond = (date.getTime() + java.util.TimeZone.getDefault().getOffset(date.getTime())) * 10000;if (millisecond >= 2534022719990000000L)millisecond = 2534023007999999999L;else if (millisecond < -621355968000000000L)millisecond = -621355968000000000L;${output}.write${streamType}(${number}, millisecond);}";

	/**
	 * 表示获取序列化长度的代码格式，期待一个类似<br>
	 * <code>
	 * * if(data.getData_obj() != null){
	 * 		java.sql.Date date = data.getData_obj();
	 * 		if(protoMember.timezone().equalsIgnoreCase("UTC")){
	 * 			date = cn.net.cvtt.lian.common.util.DateUtil.getGMTDate(date);
	 * 		}
	 * 		Long millisecond = (date.getTime() + TimeZone.getDefault().getOffset()) * 10000;
	 * 		if (millisecond >= 2534022719990000000L)
	 * 			millisecond = 2534023007999999999L;
	 * 		else if (millisecond < -621355968000000000L)
	 * 			millisecond = -621355968000000000L;
	 * 		size += cn.net.cvtt.lian.common.serialization.protobuf.CodedOutputStream.computeFixed64Size(fieldNumber, millisecond);
	 * }
	 * <br>
	 * 的输出
	 */
	private static final String SIZE_CODE_TEMPLATE = "if( ${data}.${getterName}() != null){ java.sql.Date date = ${data}.${getterName}(); ${getGMTDate_code} Long millisecond = (date.getTime() + java.util.TimeZone.getDefault().getOffset(date.getTime())) * 10000;if (millisecond >= 2534022719990000000L)millisecond = 2534023007999999999L;else if (millisecond < -621355968000000000L)millisecond = -621355968000000000L;size += ${package_core}.CodedOutputStream.compute${streamType}Size(${number},millisecond);}";

	/**
	 * 表示反序列化代码的格式，期待一个类似于<br>
	 * <code>
	 * long l = input.readFixed64();
	 * 	if (l > 2534022719990000000L)
	 * 		l = 2534022719990000000L / 10000;
	 * 	else if (l == -621355968000000000L) {
	 * 		l = -621357696000000000L;
	 * 		l = l / 10000 - TimeZone.getDefault().getOffset(l / 10000);
	 * 	} else {
	 * 		l = l / 10000 - TimeZone.getDefault().getOffset(l / 10000);
	 * 	}
	 * 	java.sql.Date date = new java.sql.Date(l);
	 * 	if(protoMember.timezone().equals("UTC")){
	 * 		date = cn.net.cvtt.lian.common.util.DateUtil.getUTCDate(date);
	 * 	}
	 * data.setDate_obj(date);</code> <br>
	 * 的输出
	 */
	private static final String PARSE_CODE_TEMPLATE = "long l = ${input}.read${streamType}();if (l > 2534022719990000000L)l = 2534022719990000000L / 10000;else if (l == -621355968000000000L) {l = -621357696000000000L;l = l / 10000 - java.util.TimeZone.getDefault().getOffset(l / 10000);} else {l = l / 10000 - java.util.TimeZone.getDefault().getOffset(l / 10000);}java.sql.Date date = new java.sql.Date(l); ${getGMTDate_code} ${data}.${setterName}(date);";

	/**
	 * 表示序列化代码的格式，期待一个类似<br>
	 * <code>
	 * java.sql.Date date = value;
	 * 	if(protoMember.timezone().equalsIgnoreCase("UTC")){
	 * 		date = cn.net.cvtt.lian.common.util.DateUtil.getGMTDate(date);
	 * 	}
	 * 	Long millisecond = (date.getTime() + TimeZone.getDefault().getOffset(date.getTime())) * 10000;
	 * 	if (millisecond >= 2534022719990000000L)
	 * 		millisecond = 2534023007999999999L;
	 * 	else if (millisecond < -621355968000000000L)
	 * 		millisecond = -621355968000000000L;
	 * 	output.writeFixed64(fieldNumber, millisecond);
	 * </code> <br>
	 * 的输出
	 */
	private static final String WRITE_CODE_ARRAY_TEMPLATE = " java.sql.Date date = value; ${getGMTDate_code} Long millisecond = (date.getTime() + java.util.TimeZone.getDefault().getOffset(date.getTime())) * 10000;if (millisecond >= 2534022719990000000L)millisecond = 2534023007999999999L;else if (millisecond < -621355968000000000L)millisecond = -621355968000000000L;${output}.write${streamType}(${number}, millisecond);";

	/**
	 * 表示获取序列化长度的代码格式，期待一个类似<br>
	 * <code>
	 * java.sql.Date date = value;
	 * 	if(protoMember.timezone().equalsIgnoreCase("UTC")){
	 * 		date = cn.net.cvtt.lian.common.util.DateUtil.getGMTDate(date);
	 * 	}
	 * 	Long millisecond = (date.getTime() + TimeZone.getDefault().getOffset(date.getTime())) * 10000;
	 * 	if (millisecond >= 2534022719990000000L)
	 * 		millisecond = 2534023007999999999L;
	 * 	else if (millisecond < -621355968000000000L)
	 * 		millisecond = -621355968000000000L;
	 * 	size += cn.net.cvtt.lian.common.serialization.protobuf.CodedOutputStream.computeFixed64Size(fieldNumber, millisecond);
	 * </code> <br>
	 * 的输出
	 */
	private static final String SIZE_CODE_ARRAY_TEMPLATE = "java.sql.Date date = value; ${getGMTDate_code} Long millisecond = (date.getTime() + java.util.TimeZone.getDefault().getOffset(date.getTime())) * 10000;if (millisecond >= 2534022719990000000L)millisecond = 2534023007999999999L;else if (millisecond < -621355968000000000L)millisecond = -621355968000000000L;size += ${package_core}.CodedOutputStream.compute${streamType}Size(${number},millisecond);";

	/**
	 * 表示反序列化代码的格式，期待一个类似于<br>
	 * <code>
	 * long l = input.readFixed64();
	 * 	if (l > 2534022719990000000L)
	 * 		l = 2534022719990000000L / 10000;
	 * 	else if (l == -621355968000000000L) {
	 * 		l = -621357696000000000L;
	 * 		l = l / 10000 - timeZone.getOffset(l / 10000);
	 * 	} else {
	 * 		l = l / 10000 - timeZone.getOffset(l / 10000);
	 * 	}
	 * 	java.sql.Date date = new java.sql.Date(l);
	 * 	if(protoMember.timezone().equals("UTC")){
	 * 		date = cn.net.cvtt.lian.common.util.DateUtil.getUTCDate(date);
	 * 	}
	 * fieldValue = date;
	 * </code> <br>
	 * 的输出
	 */
	private static final String PARSE_CODE_ARRAY_TEMPLATE = "long l = ${input}.read${streamType}();if (l > 2534022719990000000L)l = 2534022719990000000L / 10000;else if (l == -621355968000000000L) {l = -621357696000000000L;l = l / 10000 - java.util.TimeZone.getDefault().getOffset(l / 10000);} else {l = l / 10000 - java.util.TimeZone.getDefault().getOffset(l / 10000);}java.sql.Date date = new java.sql.Date(l); ${getGMTDate_code} fieldValue = date;";

	/**
	 * 单例模式，获得当前对象
	 * 
	 * @return
	 */
	public synchronized static FieldInterpreter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SqlDateFieldInterpreter(ProtoFieldType.DATE);
		}
		return INSTANCE;
	}

	private SqlDateFieldInterpreter(ProtoFieldType protoFieldType) {
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
		if (fieldInformation.getTimezone().equalsIgnoreCase("UTC")) {
			writeCode = writeCode.replaceAll("\\$\\{getGMTDate_code\\}",
					"date = cn.net.cvtt.lian.common.util.DateUtil.getGMTDate(date);");
		} else {
			writeCode = writeCode.replaceAll("\\$\\{getGMTDate_code\\}", "");
		}
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
		if (fieldInformation.getTimezone().equalsIgnoreCase("UTC")) {
			sizeCode = sizeCode.replaceAll("\\$\\{getGMTDate_code\\}",
					"date = cn.net.cvtt.lian.common.util.DateUtil.getGMTDate(date);");
		} else {
			sizeCode = sizeCode.replaceAll("\\$\\{getGMTDate_code\\}", "");
		}
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
		if (fieldInformation.getTimezone().equalsIgnoreCase("UTC")) {
			parseCode = parseCode.replaceAll("\\$\\{getGMTDate_code\\}",
					"date = cn.net.cvtt.lian.common.util.DateUtil.getGMTDate(date);");
		} else {
			parseCode = parseCode.replaceAll("\\$\\{getGMTDate_code\\}", "");
		}
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
		if (fieldInformation.getTimezone().equalsIgnoreCase("UTC")) {
			writeCode = writeCode.replaceAll("\\$\\{getGMTDate_code\\}",
					"date = cn.net.cvtt.lian.common.util.DateUtil.getGMTDate(date);");
		} else {
			writeCode = writeCode.replaceAll("\\$\\{getGMTDate_code\\}", "");
		}
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
		if (fieldInformation.getTimezone().equalsIgnoreCase("UTC")) {
			sizeCode = sizeCode.replaceAll("\\$\\{getGMTDate_code\\}",
					"date = cn.net.cvtt.lian.common.util.DateUtil.getGMTDate(date);");
		} else {
			sizeCode = sizeCode.replaceAll("\\$\\{getGMTDate_code\\}", "");
		}
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
		if (fieldInformation.getTimezone().equalsIgnoreCase("UTC")) {
			parseCode = parseCode.replaceAll("\\$\\{getGMTDate_code\\}",
					"date = cn.net.cvtt.lian.common.util.DateUtil.getGMTDate(date);");
		} else {
			parseCode = parseCode.replaceAll("\\$\\{getGMTDate_code\\}", "");
		}
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
