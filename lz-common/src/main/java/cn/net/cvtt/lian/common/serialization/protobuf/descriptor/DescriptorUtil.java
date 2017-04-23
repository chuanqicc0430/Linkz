package cn.net.cvtt.lian.common.serialization.protobuf.descriptor;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoType;
import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoFieldType;
import cn.net.cvtt.lian.common.serialization.protobuf.util.ProtoGenericsUtils;
import cn.net.cvtt.lian.common.serialization.protobuf.util.StringTemplateLoader;
import cn.net.cvtt.lian.common.util.EnumInteger;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DescriptorUtil {

	/**
	 * 
	 * 获取ProtoEntity自描述对象
	 * 
	 * @return
	 */
	public static <T extends ProtoEntity> FileDescriptorSet getDescriptor(Class<T> clazz) {
		FileDescriptorSet desc = new FileDescriptorSet();
		List<FileDescriptorProto> fileDescriptorProtos = new ArrayList<FileDescriptorProto>();
		fileDescriptorProtos.add(DescriptionBuilder.newBuilder().buildFileDesc(clazz));
		desc.setFile(fileDescriptorProtos);
		return desc;
	}

	private static String generateCode(Object model, String templateContent)
			throws IOException, TemplateException {
		Configuration cfg = new Configuration();
		cfg.setTemplateLoader(new StringTemplateLoader(templateContent));
		cfg.setDefaultEncoding("UTF-8");
		Template template = cfg.getTemplate("");
		StringWriter writer = new StringWriter();
		template.process(model, writer);
		return writer.toString();
	}

	/**
	 * 
	 * 通过ProtoEntity的自描述对象生成ProtoEntity
	 * 
	 * @return key:class name value:class content
	 * @throws IOException
	 * @throws TemplateException
	 */

	public static Map<String, String> generateEntityCode(
			FileDescriptorSet descriptor) throws IOException, TemplateException {
		Map<String, String> datas = null;
		if (descriptor == null || descriptor.getFile() == null
				|| descriptor.getFile().size() == 0)
			return datas;
		datas = new HashMap<String, String>();

		FileDescriptorProto f = descriptor.getFile().get(0);
		Map model = new HashMap();

		model.put("package_name", f.getPackage_name());
		for (EnumDescriptorProto e : f.getEnum_type()) {
			model.put("item", e);
			datas.put(e.getName(), generateCode(model, PROTO_ENUM_TEMPLATE));
		}

		List<FieldParam> fields = new ArrayList<FieldParam>();

		for (DescriptorProto d : f.getMessage_type()) {
			model.put("name", d.getName());
			for (FieldDescriptorProto field : d.getField()) {
				FieldParam desc = new FieldParam();
				desc.setField(field);
				if (desc.isRepeated()) {
					model.put("has_list", true);
				}
				if (desc.getPrototype() != null) {
					model.put("has_type", true);
				}
				fields.add(desc);
			}
			model.put("item", fields);
			datas.put(d.getName(), generateCode(model, PROTO_ENTITY_TEMPLATE));
			fields.clear();
		}

		return datas;
	}

	public static class FieldParam {

		private FieldDescriptorProto field;

		private String typename;

		private String fieldname;

		private String prototype;

		private boolean required = false;

		private boolean repeated = false;

		private boolean bool = false;

		private void setLabel() {
			switch (field.getLabel()) {
			case LABEL_REQUIRED:
				required = true;
				repeated = false;
				break;
			case LABEL_REPEATED:
				required = false;
				repeated = true;
				break;
			case LABEL_OPTIONAL:
			default:
				required = false;
				repeated = false;
				break;
			}
		}

		private void setTypeName() {
			String str;
			switch (field.getType()) {
			case TYPE_DOUBLE:
				str = "double";
				break;
			case TYPE_FLOAT:
				str = "float";
				break;
			case TYPE_INT64:
				str = "long";
				break;
			case TYPE_SINT64:
				prototype = "ProtoType.SINT64";
				str = "long";
				break;
			case TYPE_UINT64:
				prototype = "ProtoType.UINT64";
				str = "long";
				break;
			case TYPE_FIXED64:
				prototype = "ProtoType.FIXED64";
				str = "long";
				break;
			case TYPE_SFIXED64:
				prototype = "ProtoType.SFIXED64";
				str = "long";
				break;
			case TYPE_INT32:
				str = "int";
				break;
			case TYPE_SINT32:
				prototype = "ProtoType.SINT32";
				str = "int";
				break;
			case TYPE_FIXED32:
				prototype = "ProtoType.FIXED32";
				str = "int";
				break;
			case TYPE_SFIXED32:
				prototype = "ProtoType.SFIXED32";
				str = "int";
				break;
			case TYPE_UINT32:
				prototype = "ProtoType.UINT32";
				str = "int";
				break;
			case TYPE_BOOL:
				str = "boolean";
				bool = true;
				break;
			case TYPE_STRING:
				str = "String";
				break;
			case TYPE_BYTES:
				str = "ByteString";
				break;

			case TYPE_GROUP:
			case TYPE_MESSAGE:
			case TYPE_ENUM:
			default:
				str = field.getType_name().substring(1);
				break;
			}
			if (repeated) {
				str = "List<" + str + ">";
			}

			typename = str;
		}

		private void setFieldName() {
			fieldname = field.getName();
			fieldname = String.valueOf(fieldname.charAt(0)).toUpperCase()
					+ field.getName().substring(1);
		}

		public void setField(FieldDescriptorProto field) {

			this.field = field;
			setLabel();
			setTypeName();
			setFieldName();
		}

		public FieldParam() {

		}

		public boolean isRepeated() {
			return repeated;
		}

		public FieldDescriptorProto getField() {
			return field;
		}

		public boolean getRequired() {
			return required;
		}

		public String getTypename() {
			return typename;
		}

		public String getFieldname() {
			return fieldname;
		}

		public String getPrototype() {
			return prototype;
		}

		public boolean isBool() {
			return bool;
		}
	}

	/**
	 * 
	 * 通过ProtoEntity的自描述对象生成proto文件
	 * 
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static String generateProtoFile(FileDescriptorSet descriptor) throws IOException, TemplateException {
		if (descriptor == null || descriptor.getFile() == null || descriptor.getFile().size() == 0)
			return "";
		return generateCode(descriptor.getFile().get(0), PROTO_FILE_TEMPLATE);
	}

	/**
	 * 
	 * <b>描述: </b> 用于生成ProtoBuffer描述对象{@link FileDescriptorProto}的建造者类
	 * <p>
	 * <b>功能: </b>
	 * <p>
	 * <b>用法: </b>
	 * <p>
	 * 
	 * @author 
	 * 
	 */
	private static class DescriptionBuilder {

		/** 此Set变量中存储了当前对象已经生成了描述对象的Class，存储这些Class的原因是避免同一个类型的重复生成 */
		private Set<Class<?>> generatedClassSet = null;

		/** 该对象定义了一个*.proto文件 */
		private FileDescriptorProto fileDescriptorProto = null;

		/** 该集合对象定义了一个*.proto文件中全部的message */
		private List<DescriptorProto> descriptorProtoList = null;

		/** 该集合对象定义了一个*.proto文件中全部的枚举类型 */
		private List<EnumDescriptorProto> enumDescriptorProtoList = null;

		/** 该index用于map类型在生成message描述时，通过每次该 index的自增能够指定不同的名字 */
		private int currentMapIndex = 0;

		private DescriptionBuilder() {
		}

		public static DescriptionBuilder newBuilder() {
			return new DescriptionBuilder();
		}

		/**
		 * 构建一个ProtoBuffer的文件描述对象，可以说是当前类的入口方法
		 * 
		 * @param clazz
		 * @return
		 */
		public <T extends ProtoEntity> FileDescriptorProto buildFileDesc(Class<T> clazz) {
			// 初始化实例变量
			currentMapIndex = 0;
			fileDescriptorProto = new FileDescriptorProto();
			descriptorProtoList = new ArrayList<DescriptorProto>();
			enumDescriptorProtoList = new ArrayList<EnumDescriptorProto>();
			generatedClassSet = new HashSet<Class<?>>();
			// 开始构建这个类对应的message描述
			buildMessageDesc(clazz);
			// 开始设置这个*.proto文件
			fileDescriptorProto.setName(clazz.getSimpleName() + ".proto");
			fileDescriptorProto.setPackage_name(clazz.getPackage().getName());
			fileDescriptorProto.setMessage_type(descriptorProtoList);
			fileDescriptorProto.setEnum_type(enumDescriptorProtoList);
			return fileDescriptorProto;
		}

		/**
		 * 构建一个DescriptorProto对象，该对象用于描述一个ProtoBuffer格式的message
		 * 
		 * @param clazz
		 * @return
		 */
		private <T extends ProtoEntity> void buildMessageDesc(Class<T> clazz) {
			// 如果该对象已经生成过了，则不再重复生成，防止在递归期间出现同一个文件无休止的生成
			if (generatedClassSet.contains(clazz)) {
				return;
			} else {
				generatedClassSet.add(clazz);
			}
			DescriptorProto descriptorProto = new DescriptorProto();
			descriptorProto.setName(clazz.getSimpleName());
			// 寻找到当前类及其父类的全部字段
			List<Field> fields = new ArrayList<Field>();
			for (Class<?> clazzTemp = clazz; !clazzTemp.equals(Object.class); clazzTemp = clazzTemp.getSuperclass()) {
				for (Field field : clazzTemp.getDeclaredFields()) {
					fields.add(field);
				}
			}
			// 开始逐个字段的解析工作
			List<FieldDescriptorProto> fieldDescList = new ArrayList<FieldDescriptorProto>();
			for (Field field : fields) {
				// 通过注解找到相关字段的详细信息
				ProtoMember protoMember = field.getAnnotation(ProtoMember.class);
				if (protoMember != null) {
					// 如果存在此注解，则开始操作，构建每一个字段
					fieldDescList.add(buildFieldDesc(field, protoMember));
				}
			}
			// 将描述字段放入当前的message中
			descriptorProto.setField(fieldDescList);
			descriptorProtoList.add(descriptorProto);
		}

		/**
		 * 构建一个符合ProtoBuffer的枚举message描述对象
		 * 
		 * @param enumClazz
		 * @return
		 */
		private void buildEnumMessageDesc(Class<EnumInteger> enumClazz) {
			// 如果该对象已经生成过了，则不再重复生成
			if (generatedClassSet.contains(enumClazz)) {
				return;
			} else {
				generatedClassSet.add(enumClazz);
			}
			// 循环将枚举中的全部内容装填到EnumDescriptorProto中
			EnumDescriptorProto enumDesc = new EnumDescriptorProto();
			List<EnumValueDescriptorProto> enumValueDescList = new ArrayList<EnumValueDescriptorProto>();
			for (EnumInteger enumObj : enumClazz.getEnumConstants()) {
				EnumValueDescriptorProto enumValueDesc = new EnumValueDescriptorProto();
				enumValueDesc.setName(enumObj.toString());
				enumValueDesc.setNumber(enumObj.intValue());
				enumValueDescList.add(enumValueDesc);
			}
			enumDesc.setName(enumClazz.getSimpleName());
			enumDesc.setValue(enumValueDescList);
			enumDescriptorProtoList.add(enumDesc);
		}

		/**
		 * 构建一个符合ProtoBuffer的字段描述对象
		 * 
		 * @param field
		 * @param protoMember
		 * @return
		 */
		private FieldDescriptorProto buildFieldDesc(Field field, ProtoMember protoMember) {
			// 获取字段信息
			boolean isRequired = protoMember != null ? protoMember.required() : false;
			FieldLabel fieldLabel = isRequired ? FieldLabel.LABEL_REQUIRED : FieldLabel.LABEL_OPTIONAL;
			// 设置字段信息
			FieldDescriptorProto fieldDesc = new FieldDescriptorProto();
			fieldDesc.setName(field.getName());
			fieldDesc.setNumber(protoMember.value());
			fieldDesc.setLabel(fieldLabel);
			// 针对字段类型的不同具有不同特征，因此在这里进入特例化处理方法
			processFieldType(fieldDesc, field, field.getType(), protoMember);
			return fieldDesc;
		}

		/**
		 * 处理字段类型，因为字段类型千变万化，例如int32、String、message等等，所以需要一个单独方法处理它
		 * 
		 * @param fieldDesc
		 * @param field
		 * @param fieldClazz
		 * @param protoMember
		 */
		private void processFieldType(FieldDescriptorProto fieldDesc, Field field, Class<?> fieldClazz,
				ProtoMember protoMember) {
			// 获得字段类型，根据不同的类型，设置不同的Type，例如int32、message、enum等
			FieldType fieldType = protoMember != null ? convertProtoType(protoMember.type()) : null;
			ProtoFieldType protoFieldType = ProtoFieldType.valueOf(fieldClazz);
			switch (protoFieldType) {
			case INT:
			case INTEGER_OBJECT:
				fieldDesc.setType(fieldType != null ? fieldType : FieldType.TYPE_INT32);
				break;
			case LONG:
			case LONG_OBJECT:
				fieldDesc.setType(fieldType != null ? fieldType : FieldType.TYPE_INT64);
				break;
			case FLOAT:
			case FLOAT_OBJECT:
				fieldDesc.setType(fieldType != null ? fieldType : FieldType.TYPE_FLOAT);
				break;
			case DOUBLE:
			case DOUBLE_OBJECT:
				fieldDesc.setType(fieldType != null ? fieldType : FieldType.TYPE_DOUBLE);
				break;
			case BOOLEAN:
			case BOOLEAN_OBJECT:
				fieldDesc.setType(fieldType != null ? fieldType : FieldType.TYPE_BOOL);
				break;
			case SHORT:
			case SHORT_OBJECT:
				fieldDesc.setType(fieldType != null ? fieldType : FieldType.TYPE_INT32);
				break;
			case BYTE:
			case BYTE_OBJECT:
				fieldDesc.setType(fieldType != null ? fieldType : FieldType.TYPE_INT32);
				break;
			case CHAR:
			case CHARACTER_OBJECT:
				fieldDesc.setType(fieldType != null ? fieldType : FieldType.TYPE_INT32);
				break;
			case STRING:
				fieldDesc.setType(FieldType.TYPE_STRING);
				break;
			case ENUM:
				fieldDesc.setType(FieldType.TYPE_ENUM);
				fieldDesc.setType_name("." + fieldClazz.getSimpleName());
				// 如果是枚举类型,则需要构建这个枚举类型的message描述
				buildEnumMessageDesc((Class<EnumInteger>) fieldClazz);
				break;
			case MESSAGE:
				fieldDesc.setType(FieldType.TYPE_MESSAGE);
				fieldDesc.setType_name("." + fieldClazz.getSimpleName());
				// 因为其中嵌套的是一个message类型,此时就需要递归了创建该类型的message描述了
				buildMessageDesc((Class<ProtoEntity>) fieldClazz);
				break;
			case LIST:
			case SET:
				// 此时需要确定该集合所泛的具体类型，再对此类型进行FieldDesc的重新组装
				Class<?> fieldClazzTemp1 = ProtoGenericsUtils.getGenericsClass(field, 0);
				processFieldType(fieldDesc, field, fieldClazzTemp1, protoMember);
				fieldDesc.setLabel(FieldLabel.LABEL_REPEATED);
				break;
			case ARRAY:
				// 此时需要确定该集合所泛的具体类型，再对此类型进行FieldDesc的重新组装
				Class<?> fieldClazzTemp2 = ProtoGenericsUtils.getGenericsClass(field, 0);
				processFieldType(fieldDesc, field, fieldClazzTemp2, protoMember);
				fieldDesc.setLabel(FieldLabel.LABEL_REPEATED);
				break;
			case MAP:
				String mapMessageName = "MapEntity" + currentMapIndex++;
				// 针对一个Map类型，那么他一定是一个集合类型，且集合中的元素又是一个键值类型的message
				fieldDesc.setLabel(FieldLabel.LABEL_REPEATED);
				fieldDesc.setType_name("." + mapMessageName);
				fieldDesc.setType(FieldType.TYPE_MESSAGE);
				// 取得Map准确的key和value类型
				Class<?> classKey = ProtoGenericsUtils.getGenericsClass(field, 0);
				Class<?> classValue = ProtoGenericsUtils.getGenericsClass(field, 1);
				// 为Map的key和value创建对应的字段
				FieldDescriptorProto mapKeyFieldDesc = new FieldDescriptorProto();
				mapKeyFieldDesc.setName("key");
				mapKeyFieldDesc.setNumber(1);
				mapKeyFieldDesc.setLabel(FieldLabel.LABEL_REQUIRED);
				processFieldType(mapKeyFieldDesc, field, classKey, null);
				FieldDescriptorProto mapValueFieldDesc = new FieldDescriptorProto();
				mapValueFieldDesc.setName("value");
				mapValueFieldDesc.setNumber(2);
				mapValueFieldDesc.setLabel(FieldLabel.LABEL_REQUIRED);
				processFieldType(mapValueFieldDesc, field, classValue, null);
				// 创建对应的DescriptorProto来装填上一步创建的字段
				DescriptorProto mapDescProto = new DescriptorProto();
				mapDescProto.setName(mapMessageName);
				List<FieldDescriptorProto> mapFieldDescList = new ArrayList<FieldDescriptorProto>();
				mapFieldDescList.add(mapKeyFieldDesc);
				mapFieldDescList.add(mapValueFieldDesc);
				mapDescProto.setField(mapFieldDescList);
				// 将创建好的这个message加入到全局的message列表中
				descriptorProtoList.add(mapDescProto);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 用于转换我们自定义的{@link ProtoType}和PB提供的FieldType之间的类型
	 * 
	 * @param protoType
	 * @return
	 */
	private static FieldType convertProtoType(ProtoType protoType) {
		switch (protoType) {
		case DOUBLE:
			return FieldType.TYPE_DOUBLE;
		case FLOAT:
			return FieldType.TYPE_FLOAT;
		case INT32:
			return FieldType.TYPE_INT32;
		case INT64:
			return FieldType.TYPE_INT64;
		case UINT32:
			return FieldType.TYPE_UINT32;
		case UINT64:
			return FieldType.TYPE_UINT64;
		case SINT32:
			return FieldType.TYPE_SINT32;
		case SINT64:
			return FieldType.TYPE_SINT64;
		case FIXED32:
			return FieldType.TYPE_FIXED32;
		case FIXED64:
			return FieldType.TYPE_FIXED64;
		case SFIXED32:
			return FieldType.TYPE_SFIXED32;
		case SFIXED64:
			return FieldType.TYPE_SFIXED64;
		case BOOL:
			return FieldType.TYPE_BOOL;
		case STRING:
			return FieldType.TYPE_STRING;
		case BYTES:
			return FieldType.TYPE_BYTES;
		default:
			break;
		}

		return null;
	}


	/** */
	private static final String PROTO_ENUM_TEMPLATE = new String(new byte[] {
			112, 97, 99, 107, 97, 103, 101, 32, 36, 123, 112, 97, 99, 107, 97,
			103, 101, 95, 110, 97, 109, 101, 125, 59, 10, 10, 105, 109, 112,
			111, 114, 116, 32, 99, 111, 109, 46, 102, 101, 105, 110, 110, 111,
			46, 117, 116, 105, 108, 46, 69, 110, 117, 109, 73, 110, 116, 101,
			103, 101, 114, 59, 10, 10, 112, 117, 98, 108, 105, 99, 32, 101,
			110, 117, 109, 32, 36, 123, 105, 116, 101, 109, 46, 110, 97, 109,
			101, 125, 32, 105, 109, 112, 108, 101, 109, 101, 110, 116, 115, 32,
			69, 110, 117, 109, 73, 110, 116, 101, 103, 101, 114, 32, 123, 10,
			60, 35, 108, 105, 115, 116, 32, 105, 116, 101, 109, 46, 118, 97,
			108, 117, 101, 32, 97, 115, 32, 115, 117, 98, 105, 116, 101, 109,
			62, 10, 32, 32, 32, 32, 36, 123, 115, 117, 98, 105, 116, 101, 109,
			46, 110, 97, 109, 101, 125, 40, 36, 123, 115, 117, 98, 105, 116,
			101, 109, 46, 110, 117, 109, 98, 101, 114, 125, 41, 44, 10, 60, 47,
			35, 108, 105, 115, 116, 62, 10, 32, 32, 32, 32, 59, 10, 10, 32, 32,
			32, 32, 112, 114, 105, 118, 97, 116, 101, 32, 105, 110, 116, 32,
			118, 97, 108, 117, 101, 32, 61, 32, 48, 59, 10, 32, 32, 10, 32, 32,
			32, 32, 36, 123, 105, 116, 101, 109, 46, 110, 97, 109, 101, 125,
			40, 105, 110, 116, 32, 118, 97, 108, 117, 101, 41, 32, 123, 10, 32,
			32, 32, 32, 32, 32, 32, 32, 116, 104, 105, 115, 46, 118, 97, 108,
			117, 101, 32, 61, 32, 118, 97, 108, 117, 101, 59, 10, 32, 32, 32,
			32, 125, 10, 32, 32, 10, 32, 32, 32, 32, 64, 79, 118, 101, 114,
			114, 105, 100, 101, 10, 32, 32, 32, 32, 112, 117, 98, 108, 105, 99,
			32, 105, 110, 116, 32, 105, 110, 116, 86, 97, 108, 117, 101, 40,
			41, 32, 123, 10, 32, 32, 32, 32, 32, 32, 114, 101, 116, 117, 114,
			110, 32, 118, 97, 108, 117, 101, 59, 10, 32, 32, 32, 32, 125, 10,
			125, 10 });

	/** */
	private static final String PROTO_ENTITY_TEMPLATE = new String(new byte[] {
			112, 97, 99, 107, 97, 103, 101, 32, 36, 123, 112, 97, 99, 107, 97,
			103, 101, 95, 110, 97, 109, 101, 125, 59, 10, 10, 60, 35, 105, 102,
			32, 104, 97, 115, 95, 108, 105, 115, 116, 63, 63, 32, 38, 38, 32,
			104, 97, 115, 95, 108, 105, 115, 116, 62, 10, 105, 109, 112, 111,
			114, 116, 32, 106, 97, 118, 97, 46, 117, 116, 105, 108, 46, 76,
			105, 115, 116, 59, 10, 60, 47, 35, 105, 102, 62, 10, 60, 35, 105,
			102, 32, 104, 97, 115, 95, 109, 97, 112, 63, 63, 32, 38, 38, 32,
			104, 97, 115, 95, 109, 97, 112, 62, 10, 105, 109, 112, 111, 114,
			116, 32, 106, 97, 118, 97, 46, 117, 116, 105, 108, 46, 77, 97, 112,
			59, 10, 60, 47, 35, 105, 102, 62, 10, 105, 109, 112, 111, 114, 116,
			32, 99, 111, 109, 46, 102, 101, 105, 110, 110, 111, 46, 115, 101,
			114, 105, 97, 108, 105, 122, 97, 116, 105, 111, 110, 46, 112, 114,
			111, 116, 111, 98, 117, 102, 46, 80, 114, 111, 116, 111, 69, 110,
			116, 105, 116, 121, 59, 10, 105, 109, 112, 111, 114, 116, 32, 99,
			111, 109, 46, 102, 101, 105, 110, 110, 111, 46, 115, 101, 114, 105,
			97, 108, 105, 122, 97, 116, 105, 111, 110, 46, 112, 114, 111, 116,
			111, 98, 117, 102, 46, 80, 114, 111, 116, 111, 77, 101, 109, 98,
			101, 114, 59, 10, 60, 35, 105, 102, 32, 104, 97, 115, 95, 116, 121,
			112, 101, 63, 63, 32, 38, 38, 32, 104, 97, 115, 95, 116, 121, 112,
			101, 62, 10, 105, 109, 112, 111, 114, 116, 32, 99, 111, 109, 46,
			102, 101, 105, 110, 110, 111, 46, 115, 101, 114, 105, 97, 108, 105,
			122, 97, 116, 105, 111, 110, 46, 112, 114, 111, 116, 111, 98, 117,
			102, 46, 80, 114, 111, 116, 111, 84, 121, 112, 101, 59, 10, 60, 47,
			35, 105, 102, 62, 10, 10, 112, 117, 98, 108, 105, 99, 32, 99, 108,
			97, 115, 115, 32, 36, 123, 110, 97, 109, 101, 125, 32, 101, 120,
			116, 101, 110, 100, 115, 32, 80, 114, 111, 116, 111, 69, 110, 116,
			105, 116, 121, 32, 123, 10, 10, 60, 35, 108, 105, 115, 116, 32,
			105, 116, 101, 109, 32, 97, 115, 32, 115, 117, 98, 105, 116, 101,
			109, 62, 10, 60, 35, 105, 102, 32, 115, 117, 98, 105, 116, 101,
			109, 46, 114, 101, 113, 117, 105, 114, 101, 100, 62, 10, 32, 32,
			32, 32, 64, 80, 114, 111, 116, 111, 77, 101, 109, 98, 101, 114, 40,
			118, 97, 108, 117, 101, 32, 61, 32, 36, 123, 115, 117, 98, 105,
			116, 101, 109, 46, 102, 105, 101, 108, 100, 46, 110, 117, 109, 98,
			101, 114, 125, 44, 60, 35, 105, 102, 32, 115, 117, 98, 105, 116,
			101, 109, 46, 112, 114, 111, 116, 111, 116, 121, 112, 101, 63, 63,
			62, 32, 116, 121, 112, 101, 32, 61, 32, 36, 123, 115, 117, 98, 105,
			116, 101, 109, 46, 112, 114, 111, 116, 111, 116, 121, 112, 101,
			125, 44, 60, 47, 35, 105, 102, 62, 32, 114, 101, 113, 117, 105,
			114, 101, 100, 32, 61, 32, 116, 114, 117, 101, 41, 10, 60, 35, 101,
			108, 115, 101, 105, 102, 32, 115, 117, 98, 105, 116, 101, 109, 46,
			112, 114, 111, 116, 111, 116, 121, 112, 101, 63, 63, 62, 10, 32,
			32, 32, 32, 64, 80, 114, 111, 116, 111, 77, 101, 109, 98, 101, 114,
			40, 118, 97, 108, 117, 101, 32, 61, 32, 36, 123, 115, 117, 98, 105,
			116, 101, 109, 46, 102, 105, 101, 108, 100, 46, 110, 117, 109, 98,
			101, 114, 125, 44, 32, 116, 121, 112, 101, 32, 61, 32, 36, 123,
			115, 117, 98, 105, 116, 101, 109, 46, 112, 114, 111, 116, 111, 116,
			121, 112, 101, 125, 41, 10, 60, 35, 101, 108, 115, 101, 62, 10, 32,
			32, 32, 32, 64, 80, 114, 111, 116, 111, 77, 101, 109, 98, 101, 114,
			40, 36, 123, 115, 117, 98, 105, 116, 101, 109, 46, 102, 105, 101,
			108, 100, 46, 110, 117, 109, 98, 101, 114, 125, 41, 10, 60, 47, 35,
			105, 102, 62, 10, 32, 32, 32, 32, 112, 114, 105, 118, 97, 116, 101,
			32, 36, 123, 115, 117, 98, 105, 116, 101, 109, 46, 116, 121, 112,
			101, 110, 97, 109, 101, 125, 32, 36, 123, 115, 117, 98, 105, 116,
			101, 109, 46, 102, 105, 101, 108, 100, 46, 110, 97, 109, 101, 125,
			59, 10, 32, 32, 32, 32, 10, 60, 47, 35, 108, 105, 115, 116, 62, 10,
			10, 60, 35, 108, 105, 115, 116, 32, 105, 116, 101, 109, 32, 97,
			115, 32, 115, 117, 98, 105, 116, 101, 109, 62, 10, 60, 35, 105,
			102, 32, 115, 117, 98, 105, 116, 101, 109, 46, 98, 111, 111, 108,
			62, 10, 32, 32, 32, 32, 112, 117, 98, 108, 105, 99, 32, 36, 123,
			115, 117, 98, 105, 116, 101, 109, 46, 116, 121, 112, 101, 110, 97,
			109, 101, 125, 32, 105, 115, 36, 123, 115, 117, 98, 105, 116, 101,
			109, 46, 102, 105, 101, 108, 100, 110, 97, 109, 101, 125, 40, 41,
			32, 123, 10, 60, 35, 101, 108, 115, 101, 62, 10, 9, 112, 117, 98,
			108, 105, 99, 32, 36, 123, 115, 117, 98, 105, 116, 101, 109, 46,
			116, 121, 112, 101, 110, 97, 109, 101, 125, 32, 103, 101, 116, 36,
			123, 115, 117, 98, 105, 116, 101, 109, 46, 102, 105, 101, 108, 100,
			110, 97, 109, 101, 125, 40, 41, 32, 123, 10, 60, 47, 35, 105, 102,
			62, 10, 32, 32, 32, 32, 32, 32, 32, 32, 114, 101, 116, 117, 114,
			110, 32, 36, 123, 115, 117, 98, 105, 116, 101, 109, 46, 102, 105,
			101, 108, 100, 46, 110, 97, 109, 101, 125, 59, 10, 32, 32, 32, 32,
			125, 10, 10, 32, 32, 32, 32, 112, 117, 98, 108, 105, 99, 32, 118,
			111, 105, 100, 32, 115, 101, 116, 36, 123, 115, 117, 98, 105, 116,
			101, 109, 46, 102, 105, 101, 108, 100, 110, 97, 109, 101, 125, 40,
			36, 123, 115, 117, 98, 105, 116, 101, 109, 46, 116, 121, 112, 101,
			110, 97, 109, 101, 125, 32, 118, 97, 108, 117, 101, 41, 32, 123,
			10, 32, 32, 32, 32, 32, 32, 32, 32, 116, 104, 105, 115, 46, 36,
			123, 115, 117, 98, 105, 116, 101, 109, 46, 102, 105, 101, 108, 100,
			46, 110, 97, 109, 101, 125, 32, 61, 32, 118, 97, 108, 117, 101, 59,
			10, 32, 32, 32, 32, 125, 10, 32, 32, 32, 32, 10, 60, 47, 35, 108,
			105, 115, 116, 62, 10, 125, 10, 10 });

	private static final String PROTO_FILE_TEMPLATE = new String(new byte[] {
			60, 35, 105, 102, 32, 112, 97, 99, 107, 97, 103, 101, 95, 110, 97,
			109, 101, 63, 63, 32, 38, 38, 32, 112, 97, 99, 107, 97, 103, 101,
			95, 110, 97, 109, 101, 32, 33, 61, 32, 34, 34, 62, 10, 112, 97, 99,
			107, 97, 103, 101, 32, 36, 123, 112, 97, 99, 107, 97, 103, 101, 95,
			110, 97, 109, 101, 125, 59, 10, 60, 47, 35, 105, 102, 62, 10, 60,
			35, 105, 102, 32, 111, 112, 116, 105, 111, 110, 115, 63, 63, 32,
			38, 38, 32, 111, 112, 116, 105, 111, 110, 115, 46, 106, 97, 118,
			97, 95, 112, 97, 99, 107, 97, 103, 101, 63, 63, 32, 38, 38, 32,
			111, 112, 116, 105, 111, 110, 115, 46, 106, 97, 118, 97, 95, 112,
			97, 99, 107, 97, 103, 101, 32, 33, 61, 32, 34, 34, 62, 10, 111,
			112, 116, 105, 111, 110, 32, 106, 97, 118, 97, 95, 112, 97, 99,
			107, 97, 103, 101, 32, 61, 32, 36, 123, 111, 112, 116, 105, 111,
			110, 115, 46, 106, 97, 118, 97, 95, 112, 97, 99, 107, 97, 103, 101,
			125, 59, 10, 60, 47, 35, 105, 102, 62, 10, 10, 60, 35, 105, 102,
			32, 101, 110, 117, 109, 95, 116, 121, 112, 101, 63, 63, 62, 10, 60,
			35, 108, 105, 115, 116, 32, 101, 110, 117, 109, 95, 116, 121, 112,
			101, 32, 97, 115, 32, 105, 116, 101, 109, 62, 10, 101, 110, 117,
			109, 32, 36, 123, 105, 116, 101, 109, 46, 110, 97, 109, 101, 125,
			32, 123, 10, 32, 32, 60, 35, 108, 105, 115, 116, 32, 105, 116, 101,
			109, 46, 118, 97, 108, 117, 101, 32, 97, 115, 32, 115, 117, 98,
			105, 116, 101, 109, 62, 10, 32, 32, 36, 123, 115, 117, 98, 105,
			116, 101, 109, 46, 110, 97, 109, 101, 125, 32, 61, 32, 36, 123,
			115, 117, 98, 105, 116, 101, 109, 46, 110, 117, 109, 98, 101, 114,
			125, 59, 10, 32, 32, 60, 47, 35, 108, 105, 115, 116, 62, 10, 125,
			10, 60, 47, 35, 108, 105, 115, 116, 62, 10, 60, 47, 35, 105, 102,
			62, 10, 10, 60, 35, 105, 102, 32, 109, 101, 115, 115, 97, 103, 101,
			95, 116, 121, 112, 101, 63, 63, 62, 10, 60, 35, 108, 105, 115, 116,
			32, 109, 101, 115, 115, 97, 103, 101, 95, 116, 121, 112, 101, 32,
			97, 115, 32, 105, 116, 101, 109, 62, 10, 109, 101, 115, 115, 97,
			103, 101, 32, 36, 123, 105, 116, 101, 109, 46, 110, 97, 109, 101,
			125, 32, 123, 10, 32, 32, 60, 35, 108, 105, 115, 116, 32, 105, 116,
			101, 109, 46, 102, 105, 101, 108, 100, 32, 97, 115, 32, 115, 117,
			98, 105, 116, 101, 109, 62, 10, 32, 32, 36, 123, 115, 117, 98, 105,
			116, 101, 109, 46, 108, 97, 98, 101, 108, 95, 110, 97, 109, 101,
			125, 32, 36, 123, 115, 117, 98, 105, 116, 101, 109, 46, 112, 114,
			111, 116, 111, 95, 116, 121, 112, 101, 95, 110, 97, 109, 101, 125,
			32, 32, 36, 123, 115, 117, 98, 105, 116, 101, 109, 46, 110, 97,
			109, 101, 125, 32, 61, 32, 36, 123, 115, 117, 98, 105, 116, 101,
			109, 46, 110, 117, 109, 98, 101, 114, 125, 59, 10, 32, 32, 60, 47,
			35, 108, 105, 115, 116, 62, 10, 125, 10, 60, 47, 35, 108, 105, 115,
			116, 62, 10, 60, 47, 35, 105, 102, 62 });

}
