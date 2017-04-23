package cn.net.cvtt.lian.common.serialization.protobuf.generator.field;

import cn.net.cvtt.lian.common.serialization.protobuf.WireFormat;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoFieldType;
import cn.net.cvtt.lian.common.serialization.protobuf.util.ClassUtils;
import cn.net.cvtt.lian.common.serialization.protobuf.util.ProtoGenericsUtils;

/**
 * 
 * <b>描述:
 * </b>用于序列化组件，这个ArrayFieldInterpreter是数组类型的解释器，数组因为可以包含任何类型，所以本类作为一种适配器的角色出现，
 * 他可以适配任何类型为数组类型，只要该种类型的解释器实现了ForArray的方法。
 * <p>
 * <b>功能: </b>ArrayFieldInterpreter是数组类型的解释器
 * <p>
 * <b>用法: </b>该类由序列化组件在遍历类中的字段时遇到数组类型时调用,外部无需调用
 * <p>
 * 
 * @author 
 * 
 */
public class ArrayFieldInterpreter extends AbstractFieldInterpreter {

	private static ArrayFieldInterpreter INSTANCE = null;

	/**
	 * 表示序列化代码的格式，期待一个类似<br>
	 * <code>
	 * if (data.getString_Array() != null){
	 * 		for (java.lang.String value : data.getString_Array()) {
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
	 * if (data.getString_Array() != null){
	 * 		for (java.lang.String value : data.getString_Array()) {
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
	 * List<?> listTemp = arrayTypetMap.get(1);
	 * 			if (listTemp == null){
	 * 				listTemp = new java.util.ArrayList<?>();
	 * 				arrayTypetMap.put(1,listTemp);
	 * 			}
	 * 			String fieldValue = null;
	 * 			fieldValue = input.readString();
	 * 			if(fieldValue != null){
	 * 				listTemp.add(fieldValue);
	 * 			}
	 * </code><br>
	 * 的输出,这样的输出是为了能够将没一个数组首先以List的方式存储到MAP中，在所有数组中的内容都反序列化完毕后，再将这个List转换为数组
	 */
	private static final String PARSE_CODE_TEMPLATE = "@SuppressWarnings(\"unchecked\") java.util.List<${java_type}> listTemp = (java.util.List<${java_type}>)arrayTypetMap.get(${number}); if (listTemp == null){ listTemp = new java.util.LinkedList<${java_type}>();  arrayTypetMap.put(${number},listTemp); } ${java_type} fieldValue = null; ${parse_array} if(fieldValue != null){listTemp.add(fieldValue);}";

	/**
	 * 在所有数组中的内容都反序列化完毕后，再将这个List转换为数组<br>
	 * <code>
	 * if (arrayTypetMap.get(513) != null) {
	 * 		java.lang.Boolean[] array = new java.lang.Boolean[arrayTypetMap.get(513).size()];
	 * 		cn.net.cvtt.lian.common.serialization.protobuf.util.ArrayUtil.listToArray(arrayTypetMap.get(513),array);
	 * 		data.setBoolean_Object_Array(array);
	 * 	}
	 * </code>
	 */
	private static final String CONVER_ARRAY_CODE_TEMPLATE = "if(arrayTypetMap.get(${number}) != null && arrayTypetMap.get(${number}).size() > 0){ ${java_type}[] array = new ${java_type}[arrayTypetMap.get(${number}).size()];cn.net.cvtt.lian.common.serialization.protobuf.util.ArrayUtil.listToArray(arrayTypetMap.get(${number}),array); ${data}.${setterName}(array);}";

	/**
	 * 单例模式，获得当前对象
	 * 
	 * @return
	 */
	public synchronized static FieldInterpreter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ArrayFieldInterpreter(ProtoFieldType.ARRAY);
		}
		return INSTANCE;
	}

	private ArrayFieldInterpreter(ProtoFieldType protoFieldType) {
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
		// 如果是Byte类型的，则使用优化的过的Byte类型解释器
		FieldInterpreter fieldInterpreter = fieldType == java.lang.Byte.class ? WrapsFieldInterpreter
				.getOptimizationByteFieldInterpreter() : ProtoFieldType.valueOf(fieldType).getFieldInterpreter();
		String writeCode = getWriteCodeTemplate();
		writeCode = writeCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		writeCode = writeCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		writeCode = writeCode.replaceAll("\\$\\{java_type\\}",
				ProtoGenericsUtils.getGenericsClassFullName(fieldInformation.getField(), 0));
		writeCode = writeCode.replaceAll("\\$\\{write_array\\}", fieldInterpreter.getWriteCodeForArray(fieldInformation
				.setCurrentType(ProtoGenericsUtils.getGenericType(fieldInformation.getField(), 0))));
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
		// 如果是Byte类型的，则使用优化的过的Byte类型解释器
		FieldInterpreter fieldInterpreter = fieldType == java.lang.Byte.class ? WrapsFieldInterpreter
				.getOptimizationByteFieldInterpreter() : ProtoFieldType.valueOf(fieldType).getFieldInterpreter();
		String sizeCode = getSizeCodeTemplate();
		sizeCode = sizeCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		sizeCode = sizeCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		sizeCode = sizeCode.replaceAll("\\$\\{java_type\\}",
				ProtoGenericsUtils.getGenericsClassFullName(fieldInformation.getField(), 0));
		sizeCode = sizeCode.replaceAll("\\$\\{size_array\\}", fieldInterpreter.getSizeCodeForArray(fieldInformation
				.setCurrentType(ProtoGenericsUtils.getGenericType(fieldInformation.getField(), 0))));
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
		// 如果是Byte类型的，则使用优化的过的Byte类型解释器
		FieldInterpreter fieldInterpreter = fieldType == java.lang.Byte.class ? WrapsFieldInterpreter
				.getOptimizationByteFieldInterpreter() : ProtoFieldType.valueOf(fieldType).getFieldInterpreter();
		String ParseCode = getParseCodeTemplate();
		ParseCode = ParseCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		ParseCode = ParseCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		ParseCode = ParseCode.replaceAll("\\$\\{setterName\\}", getSetterName(fieldInformation));
		ParseCode = ParseCode.replaceAll("\\$\\{number\\}", String.valueOf(fieldInformation.getCurrentNumber()));
		ParseCode = ParseCode.replaceAll("\\$\\{java_type\\}",
				ProtoGenericsUtils.getGenericsClassFullName(fieldInformation.getField(), 0));
		ParseCode = ParseCode.replaceAll("\\$\\{parse_array\\}", fieldInterpreter.getParseCodeForArray(fieldInformation
				.setCurrentType(ProtoGenericsUtils.getGenericType(fieldInformation.getField(), 0))));
		return ParseCode;
	}

	/**
	 * 获得数组类型的转换代码，因为数组的长度是定长，在存储时为了节省效率以及代码简洁，我们通常使用List进行存储，后再转换成数组
	 * 
	 * @param fieldInformation
	 * @return
	 */
	public String getConverArrayCode(FieldInformation fieldInformation) {
		Class<?> fieldType = ProtoGenericsUtils.getGenericsClass(fieldInformation.getField(), 0);
		String converArrayCode = getConverArrayCodeTemplate();
		converArrayCode = converArrayCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		converArrayCode = converArrayCode.replaceAll("\\$\\{setterName\\}", getSetterName(fieldInformation));
		converArrayCode = converArrayCode.replaceAll("\\$\\{java_type\\}",
				ClassUtils.processClassName(fieldType.getName()));
		converArrayCode = converArrayCode.replaceAll("\\$\\{number\\}",
				String.valueOf(fieldInformation.getCurrentNumber()));
		return converArrayCode;
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
	 * 获得数组转换的模板
	 * 
	 * @return
	 */
	protected String getConverArrayCodeTemplate() {
		return CONVER_ARRAY_CODE_TEMPLATE;
	}

	/**
	 * 此时必须覆盖这个方法,因为当前的tag类型，以其泛型所表示的类型相同
	 * 
	 * @return
	 */
	public int getTagType(FieldInformation fieldInformation) {
		Class<?> fieldType = ProtoGenericsUtils.getClass(fieldInformation.getCurrentType());

		if ((fieldType == byte.class || fieldType == java.lang.Byte.class)) {
			// byte数组类型太特殊了。。。byte数组类型可以一次性写入流中，而在流中对象表示是WireFormat.WIRETYPE_LENGTH_DELIMITED,Google规定
			return WireFormat.WIRETYPE_LENGTH_DELIMITED;
		} else {
			return ProtoFieldType.valueOf(fieldType).getFieldInterpreter().getTagType(fieldInformation);
		}
	}
}
