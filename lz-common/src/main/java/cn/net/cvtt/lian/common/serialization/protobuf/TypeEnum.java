package cn.net.cvtt.lian.common.serialization.protobuf;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.net.cvtt.lian.common.util.DateUtil;
import cn.net.cvtt.lian.common.util.EnumType;
import cn.net.cvtt.lian.common.util.Guid;

/**
 * 
 * <b>描述: </b>可序列化的Java类型的枚举类型
 * <p>
 * <b>功能: </b>用于定义何种类型可以序列化
 * <p>
 * <b>用法: </b>此类到1.3.1后不再提供维护
 * <p>
 * 
 * @author 
 * @deprecated
 */
public enum TypeEnum {
	
	//对象类型
	DEFAULTTYPE("Object", 0,Object.class),
	STRINGTYPEB("String", 1,String.class),
	SHORTTYPE("Short", 2,Short.class),
	INTEGERTYPE("Integer", 3,Integer.class),
	LONGTYPE("Long", 4,Long.class),
	FLOATTYPE("Float", 5,Float.class),
	DOUBLETYPOE("Double", 6,Double.class),
	BYTETYPE("Byte", 7,Byte.class),
	CHARTYPE("Character", 8,Character.class),
	BOOLEANTYPE("Boolean", 9,Boolean.class),
	ENUMTYPE("Enum", 10,Enum.class),
	BASEPROTOBUFTYPE("ProtoEntity", 11,ProtoEntity.class),
	LISTTYPE("List", 12,null),
	MAPTYPE("Map", 13,null),
	DATETYPE("Date", 14,null),
	STRINGARRAYTYPE("String[]",15,String[].class),
	SHORTARRAYTYPE("Short[]",16,Short[].class),
	INTEGERARRAYTYPE("Integer[]",17,Integer[].class),
	LONGARRAYATYPE("Long[]",18,Long[].class),
	FLOAARRAYTTYPE("Float[]", 19,Float[].class),
	DOUBLEARRAYTYPOE("Double[]", 20,Double[].class),
	BYTEARRAYTYPE("Byte[]", 21,Byte[].class),
	CHARARRAYTYPE("Character[]", 22,Character[].class),
	BOOLEANARRAYTYPE("Boolean[]", 23,Boolean[].class),
	
	//基本类型
	SHORT("short",24,short.class),
	INT("int",25,int.class),
	LONG("long",26,long.class),
	FLOAT("float",27,float.class),
	DOUBLE("double",28,double.class),
	BTYE("byte",29,byte.class),
	CHAR("char",30,char.class),
	BOOLEAN("boolean",31,boolean.class),
	SHORTARRAY("short[]",32,short[].class),
	INTARRAY("int[]",33,int[].class),
	LONGARRAY("long[]",34,long[].class),
	FLOATARRAY("float[]",35,float[].class),
	DOUBLEARRAY("double[]",36,double[].class),
	BYTEARRAY("byte[]", 37,byte[].class),
	CHARARRAY("char[]",38,char[].class),
	BOOLEANARRAY("boolean[]",39,boolean[].class),
	FLAGSTYPE("Flags",40,Integer.class),
	SQLDATE("sqlDate",41,java.sql.Date.class),
	GUIDTYPE("Guid",42,Guid.class),
	
	//类似与枚举类的普通java类型
	ENUM_BYTE("Enum<Byte>",60,Byte.class),
	ENUM_CHARACTER("Enum<Character>",61,Character.class),
	ENUM_SHORT("Enum<Short>",62,Short.class),
	ENUM_INTEGER("Enum<Integer>",63,Integer.class),
	ENUM_LONG("Enum<Long>",64,Long.class),
	ENUM_STRING("Enum<String>",65,String.class),
	ENUM_TYPE("EnumType",66,EnumType.class);
	
	private String name;
	private Integer code;
	private Class cla;
	private static Map<String, TypeEnum> typeMap = new HashMap<String, TypeEnum>();
	private static Map<Class<? extends EnumType>, String> enumClassNameMap = new HashMap<Class<? extends EnumType>, String>();
	static {
		for (TypeEnum ct : TypeEnum.values()) {
			typeMap.put(ct.getName(), ct);
		}
	}

	private TypeEnum(String name, Integer code, Class cla) {
		this.name = name;
		this.code = code;
		this.cla = cla;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Class getCla() {
		return cla;
	}

	public void setCla(Class cla) {
		this.cla = cla;
	}

	public static TypeEnum getCode(String name) {
		return typeMap.get(name);
	}

	public static TypeEnum getCode(Class<?> clazz) {
		TypeEnum ct = typeMap.get(clazz.getSimpleName());
		if (ct == null) {
			if (List.class.isAssignableFrom(clazz)) {
				ct = TypeEnum.LISTTYPE;
			} else if (Map.class.isAssignableFrom(clazz)) {
				ct = TypeEnum.MAPTYPE;
			} else if (Date.class.isAssignableFrom(clazz)) {
				ct = TypeEnum.DATETYPE;
			} else if (ProtoEntity.class.isAssignableFrom(clazz)) {
				ct = TypeEnum.BASEPROTOBUFTYPE;
			} else if (Enum.class.isAssignableFrom(clazz)) {
				ct = TypeEnum.ENUMTYPE;
			} else if (EnumType.class.isAssignableFrom(clazz)) {
				ct = typeMap.get(getSimpleNameOfEnum((Class<? extends EnumType>) clazz));
			} else if (clazz.isArray()) {
				Class cla = clazz.getComponentType();
				ct = TypeEnum.getCode(cla);
			}
		}
		return ct;

	}

	public static TypeEnum getCode(Object obj) {
		TypeEnum ct = typeMap.get(obj.getClass().getSimpleName());
		if (ct == null) {
			if (obj instanceof List) {
				ct = TypeEnum.LISTTYPE;
			} else if (obj instanceof Map) {
				ct = TypeEnum.MAPTYPE;
			} else if (obj instanceof Date) {
				ct = TypeEnum.DATETYPE;
			} else if (obj instanceof ProtoEntity) {
				ct = TypeEnum.BASEPROTOBUFTYPE;
			} else if (obj instanceof Enum) {
				ct = TypeEnum.ENUMTYPE;
			} else if (obj instanceof EnumType) {
				ct = typeMap.get(getSimpleNameOfEnum((Class<? extends EnumType>) obj.getClass()));
			} else if (obj.getClass().isArray()) {
				Class cla = obj.getClass().getComponentType();
				try {
					ct = TypeEnum.getCode(cla.newInstance());
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return ct;
	}

	/**
	 * 该方法只支持EnumClass作为枚举类型的直接父类
	 * 
	 * @param enumObj
	 * @return
	 */
	private static String getSimpleNameOfEnum(Class<? extends EnumType> enumClazz) {
		String typeName = enumClassNameMap.get(enumClazz);
		if (typeName == null) {
			synchronized (enumClassNameMap) {
				typeName = enumClassNameMap.get(enumClazz);
				if (typeName == null) {
					Type enumClassType = enumClazz.getGenericSuperclass();
					Type enumClassValueType = ((ParameterizedType) enumClassType).getActualTypeArguments()[0];
					StringBuilder simpleValueName = new StringBuilder("Enum<").append(
							((Class<?>) enumClassValueType).getSimpleName()).append(">");
					typeName = simpleValueName.toString();
					enumClassNameMap.put(enumClazz, typeName);
				}
			}
		}
		return typeName;

	}

	public static Class getPrimitiveClass(Class<?> cla) {
		TypeEnum ct = typeMap.get(cla.getSimpleName());
		if (ct == null) {
			if (cla.isArray()) {
				cla = cla.getComponentType();
			}
			try {
				ct = getCode(cla.newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		switch (ct.getCode()) {
		case 32:
			return TypeEnum.SHORTTYPE.getCla();
		case 33:
			return TypeEnum.INTEGERTYPE.getCla();
		case 34:
			return TypeEnum.LONGTYPE.getCla();
		case 35:
			return TypeEnum.FLOATTYPE.getCla();
		case 36:
			return TypeEnum.DOUBLETYPOE.getCla();
		case 38:
			return TypeEnum.CHARTYPE.getCla();
		case 39:
			return TypeEnum.BOOLEANTYPE.getCla();
		case 41:
			return TypeEnum.BASEPROTOBUFTYPE.getCla();
		default:
			return cla;
		}
	}

	public static Class arrayToClassType(Class<?> cla) {
		TypeEnum ct = typeMap.get(cla.getSimpleName());
		switch (ct.getCode()) {
		case 15:
			return TypeEnum.STRINGTYPEB.getCla();
		case 16:
			return TypeEnum.SHORTTYPE.getCla();
		case 17:
			return TypeEnum.INTEGERTYPE.getCla();
		case 18:
			return TypeEnum.LONGTYPE.getCla();
		case 19:
			return TypeEnum.FLOATTYPE.getCla();
		case 20:
			return TypeEnum.DOUBLETYPOE.getCla();
		case 22:
			return TypeEnum.CHARTYPE.getCla();
		case 23:
			return TypeEnum.BOOLEANTYPE.getCla();
		case 32:
			return TypeEnum.SHORT.getCla();
		case 33:
			return TypeEnum.INT.getCla();
		case 34:
			return TypeEnum.LONG.getCla();
		case 35:
			return TypeEnum.FLOAT.getCla();
		case 36:
			return TypeEnum.DOUBLE.getCla();
		case 38:
			return TypeEnum.CHAR.getCla();
		case 39:
			return TypeEnum.BOOLEAN.getCla();
		case 41:
			return TypeEnum.BASEPROTOBUFTYPE.getCla();
		default:
			return cla;
		}
	}

	public static Object getDate(Object obj) {
		if (obj instanceof java.sql.Date) {
			try {
				return DateUtil.getUtilDate(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
}
