package cn.net.cvtt.lian.common.serialization.protobuf.descriptor;

import cn.net.cvtt.lian.common.util.EnumInteger;

/**
 * Type
 * 
 * @author
 */
public enum FieldType implements EnumInteger {
	TYPE_DOUBLE(1), 
	TYPE_FLOAT(2),
	TYPE_INT64(3), 
	TYPE_UINT64(4), 
	TYPE_INT32(5), 
	TYPE_FIXED64(6), 
	TYPE_FIXED32(7), 
	TYPE_BOOL(8), 
	TYPE_STRING(9), 
	TYPE_GROUP(10), 
	TYPE_MESSAGE(11), 
	TYPE_BYTES(12),
	TYPE_UINT32(13), 
	TYPE_ENUM(14), 
	TYPE_SFIXED32(15), 
	TYPE_SFIXED64(16), 
	TYPE_SINT32(17), 
	TYPE_SINT64(18), ;

	private int value;

	FieldType(int value) {
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}

	public String getName() {
		switch (this.value) {
		case 1:
			return "double";
		case 2:
			return "float";
		case 3:
			return "int64";
		case 4:
			return "uint64";
		case 5:
			return "int32";
		case 6:
			return "fixed64";
		case 7:
			return "fixed32";
		case 8:
			return "bool";
		case 9:
			return "string";
		case 10:
			return "group";
		case 11:
			return "message";
		case 12:
			return "bytes";
		case 13:
			return "uint32";
		case 14:
			return "enum";
		case 15:
			return "sfixed32";
		case 16:
			return "sfixed64";
		case 17:
			return "sint32";
		case 18:
			return "sint64";
		default:
			return "string";
		}
	}
}
