package cn.net.cvtt.lian.common.serialization.protobuf.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoType;
import cn.net.cvtt.lian.common.serialization.protobuf.WireFormat;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.ArrayFieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.DateFieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.EnumFieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.EnumTypeFieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.FieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.FlagsFieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.GuidFieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.ListFieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.MapFieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.MessageFieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.PrimitiveFieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.SetFieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.SqlDateFieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.StringFieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.UnKnownFieldInterpreter;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.field.WrapsFieldInterpreter;

/**
 * 使用枚举来表示在实体类中发现的每一种字段的类型，并为这些类型提供相应的序列化信息，<br>
 * 
 * @author 
 * 
 */

/**
 * <b>描述: </b>用于标识可protobuf序列化的Java字段类型的枚举类，并且为这些类型提供一些基本信息，例如序列化时写入类型
 * {@link WireFormat#WIRETYPE_VARINT}，如果想为protobuf增加新的可序列化类型，需要在此增加枚举内容
 * <p>
 * <b>功能: </b>用于标识可protobuf序列化的Java字段类型的枚举类，并且为这些类型提供一些基本信息，例如序列化时写入类型
 * {@link WireFormat#WIRETYPE_VARINT}
 * <p>
 * <b>用法: </b>由序列化组件直接调用
 * <p>
 * 
 * @author 
 * 
 */
public enum ProtoFieldType {

	/** part 1.基本数据类型 */
	INT(int.class, WireFormat.WIRETYPE_VARINT, "Int32") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return PrimitiveFieldInterpreter.getIntFieldInterpreter();
		}
	},
	LONG(long.class, WireFormat.WIRETYPE_VARINT, "Int64") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return PrimitiveFieldInterpreter.getLongFieldInterpreter();
		}
	},
	FLOAT(float.class, WireFormat.WIRETYPE_FIXED32, "Float") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return PrimitiveFieldInterpreter.getFloatFieldInterpreter();
		}
	},
	DOUBLE(double.class, WireFormat.WIRETYPE_FIXED64, "Double") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return PrimitiveFieldInterpreter.getDoubleFieldInterpreter();
		}
	},
	BOOLEAN(boolean.class, WireFormat.WIRETYPE_VARINT, "Bool") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return PrimitiveFieldInterpreter.getBooleanFieldInterpreter();
		}

	},
	SHORT(short.class, WireFormat.WIRETYPE_VARINT, "Int32") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return PrimitiveFieldInterpreter.getShortFieldInterpreter();
		}
	},
	BYTE(byte.class, WireFormat.WIRETYPE_VARINT, "Int32") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return PrimitiveFieldInterpreter.getByteFieldInterpreter();
		}
	},
	CHAR(char.class, WireFormat.WIRETYPE_VARINT, "Int32") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return PrimitiveFieldInterpreter.getCharFieldInterpreter();
		}
	},
	/** part 2.基本数据类型的包装类型 */
	INTEGER_OBJECT(java.lang.Integer.class, WireFormat.WIRETYPE_VARINT, "Int32") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return WrapsFieldInterpreter.getIntegerFieldInterpreter();
		}
	},
	LONG_OBJECT(java.lang.Long.class, WireFormat.WIRETYPE_VARINT, "Int64") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return WrapsFieldInterpreter.getLongFieldInterpreter();
		}
	},
	FLOAT_OBJECT(java.lang.Float.class, WireFormat.WIRETYPE_FIXED32, "Float") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return WrapsFieldInterpreter.getFloatFieldInterpreter();
		}
	},
	DOUBLE_OBJECT(java.lang.Double.class, WireFormat.WIRETYPE_FIXED64, "Double") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return WrapsFieldInterpreter.getDoubleFieldInterpreter();
		}
	},
	BOOLEAN_OBJECT(java.lang.Boolean.class, WireFormat.WIRETYPE_VARINT, "Bool") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return WrapsFieldInterpreter.getBooleanFieldInterpreter();
		}

	},
	SHORT_OBJECT(java.lang.Short.class, WireFormat.WIRETYPE_VARINT, "Int32") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return WrapsFieldInterpreter.getShortFieldInterpreter();
		}
	},
	BYTE_OBJECT(java.lang.Byte.class, WireFormat.WIRETYPE_VARINT, "Int32") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return WrapsFieldInterpreter.getByteFieldInterpreter();
		}
	},
	CHARACTER_OBJECT(java.lang.Character.class, WireFormat.WIRETYPE_VARINT, "Int32") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return WrapsFieldInterpreter.getCharacterFieldInterpreter();
		}
	},
	/** part 3.对象类型 */
	STRING(java.lang.String.class, WireFormat.WIRETYPE_LENGTH_DELIMITED, "String") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return StringFieldInterpreter.getInstance();
		}
	},
	ENUM(java.lang.Enum.class, WireFormat.WIRETYPE_VARINT, "Enum") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return EnumFieldInterpreter.getInstance();
		}

		@Override
		public boolean isMyType(Class<?> clazz) {
			return javaType.equals(clazz) || javaType.equals(clazz.getSuperclass());
		}
	},
	MESSAGE(ProtoEntity.class, WireFormat.WIRETYPE_LENGTH_DELIMITED, "Message") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return MessageFieldInterpreter.getInstance();
		}

		@Override
		public boolean isMyType(Class<?> clazz) {
			return javaType.equals(clazz) || javaType.equals(clazz.getSuperclass()) || javaType.isAssignableFrom(clazz);
		}
	},
	GUID(cn.net.cvtt.lian.common.util.Guid.class, WireFormat.WIRETYPE_LENGTH_DELIMITED, "Guid") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return GuidFieldInterpreter.getInstance();
		}
	},
	FLAGS(cn.net.cvtt.lian.common.util.Flags.class, WireFormat.WIRETYPE_VARINT, "Int32") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return FlagsFieldInterpreter.getInstance();
		}
	},
	DATE(java.util.Date.class, WireFormat.WIRETYPE_FIXED64, "Fixed64") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return DateFieldInterpreter.getInstance();
		}
	},
	SQLDATE(java.sql.Date.class, WireFormat.WIRETYPE_FIXED64, "Fixed64") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return SqlDateFieldInterpreter.getInstance();
		}
	},
	ENUMTYPE(cn.net.cvtt.lian.common.util.EnumType.class, WireFormat.WIRETYPE_VARINT, "Int32") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return EnumTypeFieldInterpreter.getInstance();
		}

		@Override
		public boolean isMyType(Class<?> clazz) {
			return javaType.equals(clazz) || javaType.equals(clazz.getSuperclass());
		}
	},
	// 集合类型
	LIST(java.util.List.class, WireFormat.WIRETYPE_LENGTH_DELIMITED, "List") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return ListFieldInterpreter.getInstance();
		}

		@Override
		public boolean isMyType(Class<?> clazz) {
			return javaType.equals(clazz) || javaType.equals(clazz.getSuperclass());
		}
	},
	// 集合类型
	SET(java.util.Set.class, WireFormat.WIRETYPE_LENGTH_DELIMITED, "Set") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return SetFieldInterpreter.getInstance();
		}

		@Override
		public boolean isMyType(Class<?> clazz) {
			return javaType.equals(clazz) || javaType.equals(clazz.getSuperclass());
		}
	},
	ARRAY(java.lang.reflect.Array.class, WireFormat.WIRETYPE_LENGTH_DELIMITED, "Array") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return ArrayFieldInterpreter.getInstance();
		}

		@Override
		public boolean isMyType(Class<?> clazz) {
			return clazz.isArray();
		}
	},
	MAP(java.util.Map.class, WireFormat.WIRETYPE_LENGTH_DELIMITED, "Map") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return MapFieldInterpreter.getInstance();
		}

		@Override
		public boolean isMyType(Class<?> clazz) {
			return javaType.equals(clazz) || javaType.equals(clazz.getSuperclass());
		}
	},
	/** part 4.当无法识别某一字段时,会给出这个空的解释器 */
	UNKNOWN(UnKnownFieldInterpreter.class, WireFormat.WIRETYPE_LENGTH_DELIMITED, "NULL") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return UnKnownFieldInterpreter.getInstance();
		}
	},
	OBJECT(java.lang.Object.class, WireFormat.WIRETYPE_LENGTH_DELIMITED, "Object") {
		@Override
		public FieldInterpreter getFieldInterpreter() {
			return UnKnownFieldInterpreter.getInstance();
		}
	};
	private static final Logger logger = LoggerFactory.getLogger(ProtoFieldType.class);

	public Class<?> javaType = null;
	public int tagType = 0;
	public String streamType = null;

	// 预加载所有类型，可以使得检索更快
	private static Map<Class<?>, ProtoFieldType> typeMap = new HashMap<Class<?>, ProtoFieldType>();

	static {
		// 预加载所有类型，可以使得检索更快
		for (ProtoFieldType protoFieldType : ProtoFieldType.values()) {
			typeMap.put(protoFieldType.javaType, protoFieldType);
		}
	}

	/** 枚举的构造方法 */
	ProtoFieldType(Class<?> javaType, int tagType, String codedStreamType) {
		this.javaType = javaType;
		this.tagType = tagType;
		this.streamType = codedStreamType;
	}

	/**
	 * 获取对应字段类型的解释器，该解释器用于生成该字段类型的序列化代码和反序列化代码
	 * 
	 * @return
	 */
	public abstract FieldInterpreter getFieldInterpreter();

	/**
	 * 验证一个对象类型是否是自身类型<br>
	 * 一般的对象只要求两个class相同既可判定是同一种类型，但是某些类型我们需要判断他的父类是否为某一个类型
	 * 
	 * @param clazz
	 * @return
	 */
	public boolean isMyType(Class<?> clazz) {
		return javaType.equals(clazz);
	}

	/**
	 * 通过传入的Class来找到对应的枚举类型
	 * 
	 * @param clazz
	 * @return
	 */
	public static ProtoFieldType valueOf(Class<?> clazz) {

		// Step 1.首先从MAP中直接取
		ProtoFieldType protoFieldType = typeMap.get(clazz);

		if (protoFieldType != null) {
			return protoFieldType;
		}

		// Step 2.如果MAP中没有找到可以直接使用的类型，那么遍历这个枚举的所有内容，通过isMyType方法寻找匹配的类型
		for (ProtoFieldType type : ProtoFieldType.values()) {
			if (type.isMyType(clazz)) {
				return type;
			}
		}

		// Step 3.如果还是没有找到合适的类型，只能返回一个代表为空的枚举类型，
		// 此类型会导出一个空的字段解释器，这个解释器会忽略掉本次需要需要序列化的内容
		logger.warn("{} is not found ProtoFieldType,so use ProtoFieldType.UNKNOWN.", clazz.getName());
		return ProtoFieldType.UNKNOWN;
	}

	/**
	 * 这是我们自定义的序列化类型解释器对应于Protobuf原生的序列类型之间的映射关系，例如int类型对应的protobuf的序列方式分别为int32
	 * ,uint32等等
	 */
	private static final Map<ProtoFieldType, List<ProtoType>> fieldTypeToProtoTypeMap = new HashMap<ProtoFieldType, List<ProtoType>>();
	static {
		List<ProtoType> intListTemp = new ArrayList<ProtoType>();
		intListTemp.add(ProtoType.INT32);
		intListTemp.add(ProtoType.UINT32);
		intListTemp.add(ProtoType.SINT32);
		intListTemp.add(ProtoType.FIXED32);
		intListTemp.add(ProtoType.SFIXED32);
		fieldTypeToProtoTypeMap.put(ProtoFieldType.INT, intListTemp);
		fieldTypeToProtoTypeMap.put(ProtoFieldType.INTEGER_OBJECT, intListTemp);
		List<ProtoType> longListTemp = new ArrayList<ProtoType>();
		longListTemp.add(ProtoType.INT64);
		longListTemp.add(ProtoType.UINT64);
		longListTemp.add(ProtoType.SINT64);
		longListTemp.add(ProtoType.FIXED64);
		longListTemp.add(ProtoType.SFIXED64);
		fieldTypeToProtoTypeMap.put(ProtoFieldType.LONG, longListTemp);
		fieldTypeToProtoTypeMap.put(ProtoFieldType.LONG_OBJECT, longListTemp);
	}

	/**
	 * 此方法用于判断用户自定义的某种protobuf类型是否是有效的，如果是有效的，例如int类型对方配置的为UInt32，那么使用用户定义的，
	 * 如果用户定义的是非法的，例如int类型对方却定义为UInt64，那么就返回默认自动匹配的类型
	 * 
	 * @param protoType
	 * @return
	 */
	public static ProtoType processProtoType(ProtoFieldType protoFieldType, ProtoType protoType) {
		List<ProtoType> listTemp = fieldTypeToProtoTypeMap.get(protoFieldType);
		if (listTemp != null && listTemp.contains(protoType)) {
			return protoType;
		}
		return ProtoType.AUTOMATIC;
	}
}