package cn.net.cvtt.lian.common.serialization.protobuf.generator.field;

import cn.net.cvtt.lian.common.serialization.protobuf.WireFormat;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoConfig;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoFieldType;
import cn.net.cvtt.lian.common.serialization.protobuf.util.ProtoGenericsUtils;

/**
 * <b>描述: </b>用于序列化组件，这个MapFieldInterpreter是Map类型的解释器，可以将Map类型进行protobuf格式的序列化，
 * 基本思路是一个Map中存储了许多个Map.entry类型的对象，每个对象又一个K和一个V，逐个对K和V按照顺序进行序列化
 * <p>
 * <b>功能: </b>对Map类型的字段提供protobuf格式的序列化的功能
 * <p>
 * <b>用法: </b>该类由序列化组件在遍历类中的字段时遇到Map类型时调用,外部无需调用
 * <p>
 * 
 * @author 
 * 
 */
public class MapFieldInterpreter extends AbstractFieldInterpreter {

	private static MapFieldInterpreter INSTANCE = null;

	/**
	 * 表示序列化代码的格式，期待一个类似<br>
	 * <code>
	 * if (data.getString_Map() != null){
	 * 		for (final Map.Entry<Integer, String> entry : data.getString_Map()) {
	 * 			java.lang.Integer value = entry.getKey();
	 * 			output.writeInt32(11, value);
	 * 			new cn.net.cvtt.lian.common.serialization.protobuf.util.Action(){
	 * 				public void run(){
	 * 		 			java.lang.String value = entry.getValue();
	 * 					output.writeString(11, value);
	 * 				}
	 * 			}
	 * 		}
	 * 	}
	 * </code><br>
	 * 的输出，其中名字为Action的内部类是为了解决变量名同为value时的问题
	 */
	private static final String WRITE_CODE_TEMPLATE = "if( ${data}.${getterName}() != null) { for (final java.util.Map.Entry<${java_type1}, ${java_type2}> entry :   ${data}.${getterName}().entrySet()) { ${output}.writeTag(${number}, ${package_core}.WireFormat.WIRETYPE_LENGTH_DELIMITED);int size = 0; size+=new ${package_core}.util.Action<Integer>(){ public Integer run(){ int size = 0 ;${java_type1} value = entry.getKey();  ${key_size} return size;} }.run(); size+=new ${package_core}.util.Action<Integer>(){ public Integer run(){ int size = 0 ;${java_type2} value = entry.getValue();  ${value_size} return size;} }.run(); ${output}.writeRawVarint32(size); ${java_type1} value = entry.getKey();  ${write_array1} new cn.net.cvtt.lian.common.serialization.protobuf.util.Action<Object>(){ public Object run(){ ${java_type2} value = entry.getValue(); try { ${write_array2} }catch(IOException e){e.printStackTrace();}return null;} }.run();}}";

	/**
	 * 表示获取序列化长度的代码格式，期待一个类似<br>
	 * <code>
	 * if (data.getString_Map() != null){
	 * 		for (final Map.Entry<Integer, String> entry : data.getString_Map()) {
	 * 			java.lang.Integer value = entry.getKey();
	 * 			size += cn.net.cvtt.lian.common.serialization.protobuf.CodedOutputStream.computeStringSize(11, value);
	 * 			new cn.net.cvtt.lian.common.serialization.protobuf.util.Action(){
	 * 				public void run(){
	 * 		 			java.lang.Integer value = entry.getValue();
	 * 					size += cn.net.cvtt.lian.common.serialization.protobuf.CodedOutputStream.computeStringSize(11, value);
	 * 				}
	 * 			}
	 * 		}
	 * 	}
	 * 	</code> <br>
	 * 的输出
	 */
	private static final String SIZE_CODE_TEMPLATE = "if( ${data}.${getterName}() != null) {  for (final java.util.Map.Entry<${java_type1}, ${java_type2}> entry :  ${data}.${getterName}().entrySet()) {int sizeTemp = 0; sizeTemp+=new ${package_core}.util.Action<Integer>(){ public Integer run(){ int size = 0 ;${java_type1} value = entry.getKey();  ${key_size} return size;} }.run(); sizeTemp+=new ${package_core}.util.Action<Integer>(){ public Integer run(){ int size = 0 ;${java_type2} value = entry.getValue();  ${value_size} return size;} }.run(); size += cn.net.cvtt.lian.common.serialization.protobuf.CodedOutputStream.computeTagSize(${number}); size+=${package_core}.CodedOutputStream.computeRawVarint32Size(sizeTemp); size += sizeTemp; } }";

	/**
	 * 表示反序列化代码的格式，期待一个类似于<br>
	 * <code>
	 * //第一步从MAP重找出此序号对应的集合
	 * java.util.List<cn.net.cvtt.lian.common.serialization.protobuf.util.TwoTuple<${java_type1},${java_type2}>> listTemp = (java.util.List<cn.net.cvtt.lian.common.serialization.protobuf.util.TwoTuple<${java_type1},${java_type2}>>)arrayTypetMap.get(${number});
	 * if (listTemp == null){//如果这个序号对应的集合不存在，则创建这个集合,并且向这个集合中加入一个空的二元组，每个二元组代表一对Key Value
	 * 		listTemp = new java.util.LinkedList<cn.net.cvtt.lian.common.serialization.protobuf.util.TwoTuple<${java_type1},${java_type2}>>();
	 * 		arrayTypetMap.put(${number},listTemp);
	 * }
	 * boolean isRun = true;
	 * whild(isRun){
	 * 		int mapTag = input.readTag();
	 * 		switch (tag) {
	 * 			case 0:
	 * 				isRun = false;
	 * 				cn.net.cvtt.lian.common.serialization.protobuf.util.TwoTuple<${java_type1},${java_type2}> entry = listTemp.get(listTemp.size()-1);
	 * 				java.util.Map<${java_type1},${java_type2}> map = ${data}.${getterName}();
	 * 				if(map == null){
	 * 					map = new java.util.HashMap<${java_type1},${java_type2}>();
	 * 					${data}.${setterName}(map);
	 * 				}
	 * 				map.put(entry.getFirst(),entry.getSecond());
	 * 				break;
	 * 			case ${key_wire_type}:
	 * 				${java_type1} fieldValue = null;
	 * 				${parse_array1}
	 * 				cn.net.cvtt.lian.common.serialization.protobuf.util.TwoTuple<${java_type1},${java_type2}> entryTemp = listTemp.get(listTemp.size()-1);
	 * 				if(entryTemp == null){
	 * 					entryTemp = new cn.net.cvtt.lian.common.serialization.protobuf.util.TwoTuple<${java_type1},${java_type2}>(null,null);
	 * 					listTemp.add(entryTemp);
	 * 				}
	 * 				entryTemp.setFirst(fieldValue);
	 * 				break;
	 * 			case ${value_wire_type}:
	 * 				${java_type2} fieldValue = null;
	 * 				${parse_array2}
	 * 				cn.net.cvtt.lian.common.serialization.protobuf.util.TwoTuple<${java_type1},${java_type2}> entryTemp = listTemp.get(listTemp.size()-1);
	 * 				if(entryTemp == null){
	 * 					entryTemp = new cn.net.cvtt.lian.common.serialization.protobuf.util.TwoTuple<${java_type1},${java_type2}>(null,null);
	 * 					listTemp.add(entryTemp);
	 * 				}
	 * 				entryTemp.setSecond(fieldValue);
	 * 				break;
	 * 			default:
	 * 				break;
	 * 		}
	 * }
	 * </code><br>
	 * 的输出,这样的输出是首先将一个MAP信息放入二元组twoTuple对象中，twoTuple维护了两个List，分别代表K List与 V
	 * List,如果发现两个List的长度相同，那么新来的序列化就是从K List开始添加，如果长度不同，代表还没有为最后一个K
	 * List添加相应的值，所以将值写入V List的末尾
	 */
	private static final String PARSE_CODE_TEMPLATE = "@SuppressWarnings(\"unchecked\") java.util.List<${package_core}.util.TwoTuple<${java_type1},${java_type2}>> listTemp = (java.util.List<${package_core}.util.TwoTuple<${java_type1},${java_type2}>>)arrayTypetMap.get(${number});if (listTemp == null){listTemp = new java.util.LinkedList<${package_core}.util.TwoTuple<${java_type1},${java_type2}>>();arrayTypetMap.put(${number},listTemp);}boolean isRun = true;int oldLimit = ${input}.readMessageStart();while(isRun){int mapTag = ${input}.readTag();switch (mapTag) {case 0:isRun = false;${package_core}.util.TwoTuple<${java_type1},${java_type2}> entry = listTemp.size()>0?listTemp.get(listTemp.size()-1):null;java.util.Map<${java_type1},${java_type2}> map = ${data}.${getterName}();if(map == null){map = new java.util.HashMap<${java_type1},${java_type2}>();${data}.${setterName}(map);}map.put(entry.getFirst(),entry.getSecond());${input}.readMessageEnd(oldLimit);break;case ${key_wire_type}:${java_type1} fieldValue = null;${parse_array1} ${package_core}.util.TwoTuple<${java_type1},${java_type2}> entryTemp =  listTemp.size()>0?listTemp.get(listTemp.size()-1):null;if(entryTemp == null){entryTemp = new ${package_core}.util.TwoTuple<${java_type1},${java_type2}>(null,null);listTemp.add(entryTemp);}entryTemp.setFirst(fieldValue);break;case ${value_wire_type}:${java_type2} fieldValue2 = new cn.net.cvtt.lian.common.serialization.protobuf.util.Action<${java_type2}>(){ public ${java_type2} run(){ ${java_type2} fieldValue = null; try {${parse_array2}} catch (IOException e) { e.printStackTrace(); } return fieldValue;} }.run(); ${package_core}.util.TwoTuple<${java_type1},${java_type2}> entryTempValue = listTemp.size()>0?listTemp.get(listTemp.size()-1):null;if(entryTempValue == null){entryTempValue = new ${package_core}.util.TwoTuple<${java_type1},${java_type2}>(null,null);listTemp.add(entryTempValue);}entryTempValue.setSecond(fieldValue2);break;default:break;}}";

	/**
	 * 单例模式，获得当前对象
	 * 
	 * @return
	 */
	public synchronized static FieldInterpreter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MapFieldInterpreter(ProtoFieldType.ARRAY);
		}
		return INSTANCE;
	}

	private MapFieldInterpreter(ProtoFieldType protoFieldType) {
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
		Class<?> keyFieldType = ProtoGenericsUtils.getGenericsClass(fieldInformation.getField(), 0);
		Class<?> valueFieldType = ProtoGenericsUtils.getGenericsClass(fieldInformation.getField(), 1);
		FieldInterpreter keyFieldInterpreter = ProtoFieldType.valueOf(keyFieldType).getFieldInterpreter();
		FieldInterpreter valueFieldInterpreter = ProtoFieldType.valueOf(valueFieldType).getFieldInterpreter();

		String writeCode = getWriteCodeTemplate();
		writeCode = writeCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		writeCode = writeCode.replaceAll("\\$\\{output\\}", VARIABLE_NAME_OUTPUTSTREAM);
		writeCode = writeCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		writeCode = writeCode.replaceAll("\\$\\{java_type1\\}",
				ProtoGenericsUtils.getGenericsClassFullName(fieldInformation.getField(), 0));
		writeCode = writeCode.replaceAll("\\$\\{java_type2\\}",
				ProtoGenericsUtils.getGenericsClassFullName(fieldInformation.getField(), 1));
		writeCode = writeCode.replaceAll("\\$\\{package_core\\}", ProtoConfig.PACKAGE_CORE);
		writeCode = writeCode.replaceAll("\\$\\{number\\}", String.valueOf(fieldInformation.getCurrentNumber()));
		writeCode = writeCode.replaceAll(
				"\\$\\{key_size\\}",
				keyFieldInterpreter.getSizeCodeForArray(fieldInformation.setCurrentType(
						ProtoGenericsUtils.getGenericType(fieldInformation.getField(), 0)).setCurrentNumber(1)));
		writeCode = writeCode.replaceAll(
				"\\$\\{value_size\\}",
				valueFieldInterpreter.getSizeCodeForArray(fieldInformation.setCurrentType(
						ProtoGenericsUtils.getGenericType(fieldInformation.getField(), 1)).setCurrentNumber(2)));
		writeCode = writeCode.replaceAll(
				"\\$\\{write_array1\\}",
				keyFieldInterpreter.getWriteCodeForArray(fieldInformation.setCurrentType(
						ProtoGenericsUtils.getGenericType(fieldInformation.getField(), 0)).setCurrentNumber(1)));
		writeCode = writeCode.replaceAll(
				"\\$\\{write_array2\\}",
				valueFieldInterpreter.getWriteCodeForArray(fieldInformation.setCurrentType(
						ProtoGenericsUtils.getGenericType(fieldInformation.getField(), 1)).setCurrentNumber(2)));
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
		Class<?> keyFieldType = ProtoGenericsUtils.getGenericsClass(fieldInformation.getField(), 0);
		Class<?> valueFieldType = ProtoGenericsUtils.getGenericsClass(fieldInformation.getField(), 1);
		FieldInterpreter keyFieldInterpreter = ProtoFieldType.valueOf(keyFieldType).getFieldInterpreter();
		FieldInterpreter valueFieldInterpreter = ProtoFieldType.valueOf(valueFieldType).getFieldInterpreter();

		String sizeCode = getSizeCodeTemplate();
		sizeCode = sizeCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		sizeCode = sizeCode.replaceAll("\\$\\{output\\}", VARIABLE_NAME_OUTPUTSTREAM);
		sizeCode = sizeCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		sizeCode = sizeCode.replaceAll("\\$\\{java_type1\\}",
				ProtoGenericsUtils.getGenericsClassFullName(fieldInformation.getField(), 0));
		sizeCode = sizeCode.replaceAll("\\$\\{java_type2\\}",
				ProtoGenericsUtils.getGenericsClassFullName(fieldInformation.getField(), 1));
		sizeCode = sizeCode.replaceAll("\\$\\{package_core\\}", ProtoConfig.PACKAGE_CORE);
		sizeCode = sizeCode.replaceAll("\\$\\{number\\}", String.valueOf(fieldInformation.getCurrentNumber()));
		sizeCode = sizeCode.replaceAll(
				"\\$\\{key_size\\}",
				keyFieldInterpreter.getSizeCodeForArray(fieldInformation.setCurrentType(
						ProtoGenericsUtils.getGenericType(fieldInformation.getField(), 0)).setCurrentNumber(1)));
		sizeCode = sizeCode.replaceAll(
				"\\$\\{value_size\\}",
				valueFieldInterpreter.getSizeCodeForArray(fieldInformation.setCurrentType(
						ProtoGenericsUtils.getGenericType(fieldInformation.getField(), 1)).setCurrentNumber(2)));
		sizeCode = sizeCode.replaceAll(
				"\\$\\{size_array1\\}",
				keyFieldInterpreter.getSizeCodeForArray(fieldInformation.setCurrentType(
						ProtoGenericsUtils.getGenericType(fieldInformation.getField(), 0)).setCurrentNumber(1)));
		sizeCode = sizeCode.replaceAll(
				"\\$\\{size_array2\\}",
				valueFieldInterpreter.getSizeCodeForArray(fieldInformation.setCurrentType(
						ProtoGenericsUtils.getGenericType(fieldInformation.getField(), 1)).setCurrentNumber(2)));
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
		Class<?> keyFieldType = ProtoGenericsUtils.getGenericsClass(fieldInformation.getField(), 0);
		Class<?> valueFieldType = ProtoGenericsUtils.getGenericsClass(fieldInformation.getField(), 1);
		ProtoFieldType keyProtoFieldType = ProtoFieldType.valueOf(keyFieldType);
		ProtoFieldType valueProtoFieldType = ProtoFieldType.valueOf(valueFieldType);

		String parseCode = getParseCodeTemplate();
		parseCode = parseCode.replaceAll("\\$\\{data\\}", VARIABLE_NAME_DATA);
		parseCode = parseCode.replaceAll("\\$\\{input\\}", VARIABLE_NAME_INPUTSTREAM);
		parseCode = parseCode.replaceAll("\\$\\{package_core\\}", ProtoConfig.PACKAGE_CORE);
		parseCode = parseCode.replaceAll("\\$\\{getterName\\}", getGetterName(fieldInformation));
		parseCode = parseCode.replaceAll("\\$\\{setterName\\}", getSetterName(fieldInformation));
		parseCode = parseCode.replaceAll("\\$\\{number\\}", String.valueOf(fieldInformation.getCurrentNumber()));
		parseCode = parseCode.replaceAll("\\$\\{key_wire_type\\}",
				String.valueOf(WireFormat.makeTag(1, keyProtoFieldType.tagType)));
		parseCode = parseCode.replaceAll("\\$\\{value_wire_type\\}",
				String.valueOf(WireFormat.makeTag(2, valueProtoFieldType.tagType)));
		parseCode = parseCode.replaceAll("\\$\\{java_type1\\}",
				ProtoGenericsUtils.getGenericsClassFullName(fieldInformation.getField(), 0));
		parseCode = parseCode
				.replaceAll(
						"\\$\\{parse_array1\\}",
						keyProtoFieldType.getFieldInterpreter().getParseCodeForArray(
								fieldInformation.setCurrentType(
										ProtoGenericsUtils.getGenericType(fieldInformation.getField(), 0))
										.setCurrentNumber(1)));
		parseCode = parseCode.replaceAll("\\$\\{java_type2\\}",
				ProtoGenericsUtils.getGenericsClassFullName(fieldInformation.getField(), 1));
		parseCode = parseCode
				.replaceAll(
						"\\$\\{parse_array2\\}",
						valueProtoFieldType.getFieldInterpreter().getParseCodeForArray(
								fieldInformation.setCurrentType(
										ProtoGenericsUtils.getGenericType(fieldInformation.getField(), 1))
										.setCurrentNumber(2)));
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
}
