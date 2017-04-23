package cn.net.cvtt.lian.common.serialization.protobuf.generator.field;

import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoFieldType;
import cn.net.cvtt.lian.common.serialization.protobuf.util.ProtoGenericsUtils;

/**
 * <b>描述:
 * </b>用于序列化组件，这个SetFieldInterpreter是Set类型的解释器，Set因为泛型的关系，其中只能存储某一指定类型，
 * 因此本类作为一种适配器的角色出现，他可以适配任何类型为集合类型，只要该种类型的解释器实现了ForArray的方法既可。
 * 
 * <p>
 * <b>功能: </b>是Set类型的解释器，对任何实现ForArray方法的解释器提供集合类序列化功能的实现
 * <p>
 * <b>用法: </b>该类由序列化组件在遍历类中的字段时遇到Set类型时调用,外部无需调用
 * <p>
 * 
 * @author 
 * 
 */
public class SetFieldInterpreter extends AbstractFieldInterpreter {

	private static SetFieldInterpreter INSTANCE = null;

	/**
	 * 表示序列化代码的格式，期待一个类似<br>
	 * <code>
	 * if (data.getString_Set() != null){
	 * 		for (java.lang.String value : data.getString_Set()) {
	 * 			if (value != null){
	 * 				output.writeString(11, value);
	 * 			}
	 * 		}
	 * 	}
	 * </code><br>
	 * 的输出
	 */
	private static final String WRITE_CODE_TEMPLATE = "if( ${data}.${getterName}() != null) { for (${java_type} value : ${data}.${getterName}()) { if (value != null){ ${write_array} }}}";

	/**
	 * 表示获取序列化长度的代码格式，期待一个类似<br>
	 * <code>
	 * if (data.getString_Set() != null){
	 * 		for (java.lang.String value : data.getString_Set()) {
	 * 			if (value != null)
	 * 				size += cn.net.cvtt.lian.common.serialization.protobuf.CodedOutputStream.computeStringSize(11, value);
	 * 		}
	 * 	}
	 * 	</code> <br>
	 * 的输出
	 */
	private static final String SIZE_CODE_TEMPLATE = "if( ${data}.${getterName}() != null) { for (${java_type} value : ${data}.${getterName}()) { if (value != null){ ${size_array} }}}";

	/**
	 * 表示反序列化代码的格式，期待一个类似于<br>
	 * <code>
	 * java.util.Set<java.lang.String> set = data.getString_Array();
	 * 			if (set == null){
	 * 				set = new java.util.HashSet<java.lang.String>();
	 * 				data.setString_Set(set);
	 * 			}
	 * 			String fieldValue = null;
	 * 			fieldValue = input.readString();
	 * 			if(fieldValue != null){
	 * 	 			set.add(fieldValue);
	 * 			 }
	 * </code><br>
	 * 的输出
	 */
	private static final String PARSE_CODE_TEMPLATE = "java.util.Set<${java_type}> set = ${data}.${getterName}(); if (set == null){ set = new java.util.HashSet<${java_type}>();  ${data}.${setterName}(set); } ${java_type} fieldValue = null; ${parse_array} if(fieldValue != null) { set.add(fieldValue);} ";

	/**
	 * 单例模式，获得当前对象
	 * 
	 * @return
	 */
	public synchronized static FieldInterpreter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SetFieldInterpreter(ProtoFieldType.SET);
		}
		return INSTANCE;
	}

	private SetFieldInterpreter(ProtoFieldType protoFieldType) {
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
		// 找到想要适配的字段类型，通过字段类型来找出相应的解释器
		Class<?> fieldType = ProtoGenericsUtils.getGenericsClass(fieldInformation.getField(), 0);
		String writeCode = getWriteCodeTemplate();
		writeCode = writeCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		writeCode = writeCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		writeCode = writeCode.replaceAll("\\$\\{java_type\\}",
				ProtoGenericsUtils.getGenericsClassFullName(fieldInformation.getField(), 0));
		writeCode = writeCode.replaceAll(
				"\\$\\{write_array\\}",
				ProtoFieldType
						.valueOf(fieldType)
						.getFieldInterpreter()
						.getWriteCodeForArray(
								fieldInformation.setCurrentType(ProtoGenericsUtils.getGenericType(
										fieldInformation.getField(), 0))));

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
		// 找到想要适配的字段类型，通过字段类型来找出相应的解释器
		Class<?> fieldType = ProtoGenericsUtils.getGenericsClass(fieldInformation.getField(), 0);
		String sizeCode = getSizeCodeTemplate();
		sizeCode = sizeCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		sizeCode = sizeCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		sizeCode = sizeCode.replaceAll("\\$\\{java_type\\}",
				ProtoGenericsUtils.getGenericsClassFullName(fieldInformation.getField(), 0));
		sizeCode = sizeCode.replaceAll(
				"\\$\\{size_array\\}",
				ProtoFieldType
						.valueOf(fieldType)
						.getFieldInterpreter()
						.getSizeCodeForArray(
								fieldInformation.setCurrentType(ProtoGenericsUtils.getGenericType(
										fieldInformation.getField(), 0))));
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
		// 找到想要适配的字段类型，通过字段类型来找出相应的解释器
		Class<?> fieldType = ProtoGenericsUtils.getGenericsClass(fieldInformation.getField(), 0);
		String parseCode = getParseCodeTemplate();
		parseCode = parseCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		parseCode = parseCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		parseCode = parseCode.replaceAll("\\$\\{setterName\\}", getSetterName(fieldInformation));
		parseCode = parseCode.replaceAll("\\$\\{java_type\\}",
				ProtoGenericsUtils.getGenericsClassFullName(fieldInformation.getField(), 0));
		parseCode = parseCode.replaceAll(
				"\\$\\{parse_array\\}",
				ProtoFieldType
						.valueOf(fieldType)
						.getFieldInterpreter()
						.getParseCodeForArray(
								fieldInformation.setCurrentType(ProtoGenericsUtils.getGenericType(
										fieldInformation.getField(), 0))));
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
	 * 此时必须覆盖这个方法,因为当前的tag类型，以其泛型所表示的类型相同
	 * 
	 * @return
	 */
	public int getTagType(FieldInformation fieldInformation) {
		Class<?> fieldType = ProtoGenericsUtils.getGenericsClass(fieldInformation.getCurrentType(), 0);
		return ProtoFieldType.valueOf(fieldType).getFieldInterpreter().getTagType(fieldInformation);

	}
}
