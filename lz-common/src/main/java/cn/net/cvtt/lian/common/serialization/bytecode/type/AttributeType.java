package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.util.HashMap;
import java.util.Map;

/**
 * 符合JavaSE 7 规范中的标准Attribute格式
 * 
 * @author 
 * 
 */
public enum AttributeType {

	/** 属性类型 */
	CONSTANTVALUE("ConstantValue") {
		public DefaultAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new DefaultAttribute(classFile, nameIndex, name);
		}
	},
	CODE("Code") {
		public CodeAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new CodeAttribute(classFile, nameIndex, name);
		}
	},
	STACKMAPTABLE("StackMapTable") {
		public DefaultAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new DefaultAttribute(classFile, nameIndex, name);
		}
	},
	EXCEPTIONS("Exceptions") {
		public DefaultAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new DefaultAttribute(classFile, nameIndex, name);
		}
	},
	INNERCLASSES("InnerClasses") {
		public InnerClassesAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new InnerClassesAttribute(classFile, nameIndex, name);
		}
	},
	ENCLOSINGMETHOD("EnclosingMethod") {
		public DefaultAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new DefaultAttribute(classFile, nameIndex, name);
		}
	},
	SYNTHETIC("Synthetic") {
		public DefaultAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new DefaultAttribute(classFile, nameIndex, name);
		}
	},
	SIGNATURE("Signature") {
		public DefaultAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new DefaultAttribute(classFile, nameIndex, name);
		}
	},
	SOURCEFILE("SourceFile") {
		public DefaultAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new DefaultAttribute(classFile, nameIndex, name);
		}
	},
	SOURCEDEBUGEXTENSION("SourceDebugExtension") {
		public DefaultAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new DefaultAttribute(classFile, nameIndex, name);
		}
	},
	LINENUMBERTABLE("LineNumberTable") {
		public LineNumberTableAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new LineNumberTableAttribute(classFile, nameIndex, name);
		}
	},
	LOCALVARIABLETABLE("LocalVariableTable") {
		public LocalVariableTableAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new LocalVariableTableAttribute(classFile, nameIndex, name);
		}
	},
	LOCALVARIABLETYPETABLE("LocalVariableTypeTable") {
		public LocalVariableTypeTableAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new LocalVariableTypeTableAttribute(classFile, nameIndex, name);
		}
	},
	DEPRECATED("Deprecated") {
		public DefaultAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new DefaultAttribute(classFile, nameIndex, name);
		}
	},
	RUNTIMEVISIBLEANNOTATIONS("RuntimeVisibleAnnotations") {
		public RuntimeVisibleAnnotationsAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new RuntimeVisibleAnnotationsAttribute(classFile, nameIndex, name);
		}
	},
	RUNTIMEINVISIBLEANNOTATIONS("RuntimeInvisibleAnnotations") {
		public DefaultAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new DefaultAttribute(classFile, nameIndex, name);
		}
	},
	RUNTIMEVISIBLEPARAMETERANNOTATIONS("RuntimeVisibleParameterAnnotations") {
		public DefaultAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new DefaultAttribute(classFile, nameIndex, name);
		}
	},
	RUNTIMEINVISIBLEPARAMETERANNOTATIONS("RuntimeInvisibleParameterAnnotations") {
		public DefaultAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new DefaultAttribute(classFile, nameIndex, name);
		}
	},
	ANNOTATIONDEFAULT("AnnotationDefault") {
		public DefaultAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new DefaultAttribute(classFile, nameIndex, name);
		}
	},
	BOOTSTRAPMETHODS("BootstrapMethods") {
		public DefaultAttribute getAttribute(ClassFile classFile, int nameIndex) {
			return new DefaultAttribute(classFile, nameIndex, name);
		}
	};

	protected String name;

	private AttributeType(String name) {
		this.name = name;
	}

	public Attribute getAttribute(ClassFile classFile, int nameIndex) {
		return new DefaultAttribute(classFile, nameIndex, name);
	}

	// 预加载所有类型，可以使得检索更方便
	private static Map<String, AttributeType> typeMap = new HashMap<String, AttributeType>();

	static {
		// 预加载所有类型，可以使得检索更快
		for (AttributeType attributesType : AttributeType.values()) {
			typeMap.put(attributesType.name, attributesType);
		}
	}

	public static AttributeType get(String name) {
		return typeMap.get(name);
	}

	public String getName() {
		return name;
	}
}
