package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.util.HashMap;
import java.util.Map;

/**
 * 这是一个可辨识联合体，它可以用来表示元素-值的键值对中的值,它在这里被用来描述所有注解中涉及到的元素值
 * 
 * @author 
 * 
 */
public abstract class ElementValue extends AbstractClassElement {
}

enum ElementValueType {
	BYTE('B', "byte") {
		@Override
		protected ElementValue newElementValue() {
			return new ConstElementValue(tag);
		}
	},
	CHAR('C', "char") {
		@Override
		protected ElementValue newElementValue() {
			return new ConstElementValue(tag);
		}
	},
	DOUBLE('D', "double") {
		@Override
		protected ElementValue newElementValue() {
			return new ConstElementValue(tag);
		}
	},
	FLOAT('F', "float") {
		@Override
		protected ElementValue newElementValue() {
			return new ConstElementValue(tag);
		}
	},
	INT('I', "int") {
		@Override
		protected ElementValue newElementValue() {
			return new ConstElementValue(tag);
		}
	},
	LONG('J', "long") {
		@Override
		protected ElementValue newElementValue() {
			return new ConstElementValue(tag);
		}
	},
	SHORT('S', "short") {
		@Override
		protected ElementValue newElementValue() {
			return new ConstElementValue(tag);
		}
	},
	BOOLEAN('Z', "boolean") {
		@Override
		protected ElementValue newElementValue() {
			return new ConstElementValue(tag);
		}
	},
	STRING('s', "String") {
		@Override
		protected ElementValue newElementValue() {
			return new ConstElementValue(tag);
		}
	},
	ENUM('e', "Enum") {
		@Override
		protected ElementValue newElementValue() {
			return new EnumElementValue(tag);
		}
	},
	CLASS('c', "Class") {
		@Override
		protected ElementValue newElementValue() {
			return new ClassElementValue(tag);
		}
	},
	ARRAY('[', "Array") {
		@Override
		protected ElementValue newElementValue() {
			return new ArrayElementValue(tag);
		}
	},
	ANNOTATION('@', "Annotation") {
		@Override
		protected ElementValue newElementValue() {
			return new AnnotationElementValue(tag);
		}
	};

	protected char tag;
	protected String name;

	private ElementValueType(char tag, String name) {
		this.tag = tag;
		this.name = name;
	}

	protected abstract ElementValue newElementValue();

	// 预加载所有类型，可以使得检索更方便
	private static Map<Character, ElementValueType> typeMap = new HashMap<Character, ElementValueType>();

	static {
		// 预加载所有类型，可以使得检索更快
		for (ElementValueType elementValueType : ElementValueType.values()) {
			typeMap.put(elementValueType.tag, elementValueType);
		}
	}

	public static ElementValueType get(char tag) {
		return typeMap.get(tag);
	}

	public char getTag() {
		return tag;
	}

	public String getName() {
		return name;
	}

}
