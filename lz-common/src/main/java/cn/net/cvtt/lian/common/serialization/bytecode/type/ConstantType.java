package cn.net.cvtt.lian.common.serialization.bytecode.type;

/**
 * 这个枚举类型标识着Class字节码中常量池中的全部类型(截止到JAVA SE 7)
 * 
 * @author 
 * 
 */
public enum ConstantType {

	/** 常量池 */
	CONSTANT_CLASS((byte) 7, "CONSTANT_Class_info") {
		public ConstantClassInfo newCpInfo(ClassFile classFile) {
			return new ConstantClassInfo(classFile);
		}
	},
	CONSTANT_FIELDREF((byte) 9, "CONSTANT_Fieldref_info") {
		public ConstantFieldrefInfo newCpInfo(ClassFile classFile) {
			return new ConstantFieldrefInfo(classFile);
		}
	},
	CONSTANT_METHODREF((byte) 10, "CONSTANT_Methodref_info") {
		public ConstantMethodrefInfo newCpInfo(ClassFile classFile) {
			return new ConstantMethodrefInfo(classFile);
		}
	},
	CONSTANT_INTERFACEMETHODREF((byte) 11, "CONSTANT_InterfaceMethodref_info") {
		public ConstantInterfacerefInfo newCpInfo(ClassFile classFile) {
			return new ConstantInterfacerefInfo(classFile);
		}
	},
	CONSTANT_STRING((byte) 8, "CONSTANT_String_info") {
		public ConstantStringInfo newCpInfo(ClassFile classFile) {
			return new ConstantStringInfo(classFile);
		}
	},
	CONSTANT_INTEGER((byte) 3, "CONSTANT_Integer_info") {
		public ConstantIntegerInfo newCpInfo(ClassFile classFile) {
			return new ConstantIntegerInfo(classFile);
		}
	},
	CONSTANT_FLOAT((byte) 4, "CONSTANT_Float_info") {
		public ConstantFloatInfo newCpInfo(ClassFile classFile) {
			return new ConstantFloatInfo(classFile);
		}
	},
	CONSTANT_LONG((byte) 5, "CONSTANT_Long_info") {
		public ConstantLongInfo newCpInfo(ClassFile classFile) {
			return new ConstantLongInfo(classFile);
		}
	},
	CONSTANT_DOUBLE((byte) 6, "CONSTANT_Double_info") {
		public ConstantDoubleInfo newCpInfo(ClassFile classFile) {
			return new ConstantDoubleInfo(classFile);
		}
	},
	CONSTANT_NAMEANDTYPE((byte) 12, "CONSTANT_NameAndType_info") {
		public ConstantNameAndTypeInfo newCpInfo(ClassFile classFile) {
			return new ConstantNameAndTypeInfo(classFile);
		}
	},
	CONSTANT_UTF8((byte) 1, "CONSTANT_Utf8_info") {
		public ConstantUTF8Info newCpInfo(ClassFile classFile) {
			return new ConstantUTF8Info(classFile);
		}
	},
	CONSTANT_METHODHANDLE((byte) 15, "CONSTANT_MethodHandle_info") {
		public ConstantMethodHandleInfo newCpInfo(ClassFile classFile) {
			return new ConstantMethodHandleInfo(classFile);
		}
	},
	CONSTANT_METHODTYPE((byte) 16, "CONSTANT_MethodType_info") {
		public ConstantMethodTypeInfo newCpInfo(ClassFile classFile) {
			return new ConstantMethodTypeInfo(classFile);
		}
	},
	CONSTANT_INVOKEDYNAMIC((byte) 18, "CONSTANT_InvokeDynamic_info") {
		public ConstantInvokeDynamicInfo newCpInfo(ClassFile classFile) {
			return new ConstantInvokeDynamicInfo(classFile);
		}
	};

	private byte tag;
	private String tagVerbose;

	private ConstantType(byte tag, String tagVerbose) {
		this.tag = tag;
		this.tagVerbose = tagVerbose;
	}

	public abstract CPInfo newCpInfo(ClassFile classFile);

	public static ConstantType valueOf(byte tag) {
		switch (tag) {
		case 7:
			return ConstantType.CONSTANT_CLASS;
		case 9:
			return ConstantType.CONSTANT_FIELDREF;
		case 10:
			return ConstantType.CONSTANT_METHODREF;
		case 11:
			return ConstantType.CONSTANT_INTERFACEMETHODREF;
		case 8:
			return ConstantType.CONSTANT_STRING;
		case 3:
			return ConstantType.CONSTANT_INTEGER;
		case 4:
			return ConstantType.CONSTANT_FLOAT;
		case 5:
			return ConstantType.CONSTANT_LONG;
		case 6:
			return ConstantType.CONSTANT_DOUBLE;
		case 12:
			return ConstantType.CONSTANT_NAMEANDTYPE;
		case 1:
			return ConstantType.CONSTANT_UTF8;
		case 15:
			return ConstantType.CONSTANT_METHODHANDLE;
		case 16:
			return ConstantType.CONSTANT_METHODTYPE;
		case 18:
			return ConstantType.CONSTANT_INVOKEDYNAMIC;
		default:
			return null;
		}
	}

	public int getTag() {
		return tag;
	}

	public String getTagVerbose() {
		return tagVerbose;
	}

}
