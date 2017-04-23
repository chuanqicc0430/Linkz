package cn.net.cvtt.lian.common.serialization.bytecode.type;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.net.cvtt.lian.common.serialization.bytecode.util.DataInputStreamDecoration;
import cn.net.cvtt.lian.common.serialization.bytecode.util.OperationType;

/**
 * 根据虚拟机中的class规范定义，ClassFile是一个用于描述整个Class文件结构的类,每个类及对象的命名均为规范中的标准命名方式<br>
 * Class文件格式(Java SE 7)<br>
 * ClassFile { <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; u4 magic; <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; u2 minor_version; <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; u2 major_version; <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; u2 constant_pool_count; <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; cp_info constant_pool[constant_pool_count-1]; <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; u2 access_flags; <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; u2 this_class; <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; u2 super_class; <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; u2 interfaces_count; <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; u2 interfaces[interfaces_count];<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; u2 fields_count; <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; field_info fields[fields_count]; <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; u2 methods_count; <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; method_info methods[methods_count]; <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; u2 attributes_count; <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; attribute_info attributes[attributes_count]; <br>
 * }
 * 
 * @author 
 * 
 */
public class ClassFile implements ClassFileElement, Cloneable {

	/**
	 * 魔数,固定为0XCAFEBABE<br>
	 * (u4 magic)
	 */
	private int magic = 0XCAFEBABE;

	/**
	 * Class的副版本号<br>
	 * (u2 minor_version)
	 */
	private int minor_version;

	/**
	 * Class的主版本号<br>
	 * (u2 major_version)
	 */
	private int major_version;

	/**
	 * 常量池中常量的数量，用于确定constant_pool的length<br>
	 * (u2 constant_pool_count)
	 */
	private int constant_pool_count;

	/**
	 * 很重要的常量池，Class中的类、方法、字段等等的描述都在这个池中<br>
	 * (cp_info constant_pool[constant_pool_count-1])
	 */
	private CPInfo[] constant_pool;

	/**
	 * 当前类的访问修饰符,例如public、final、super、interface、abstract、enum等<br>
	 * (u2 access_flags)
	 */
	private int access_flags;

	/**
	 * 这是一个指向常量池的索引，且此常量的类型为{@link ConstantType#CONSTANT_CLASS},它是用于确定当前类的描述<br>
	 * (u2 this_class)
	 */
	private int this_class;

	/**
	 * 与this_class很相似，这个是指向常量池中用于描述父类型的索引，不过当此int值为0时，代表java.lang.Object,我们自己的类，
	 * 这个值绝对不能为0<br>
	 * (u2 super_class)
	 */
	private int super_class;

	/**
	 * 一个类可以实现很多接口，那么这个变量就是为了记录当前类有多少个接口<br>
	 * (u2 interfaces_count)
	 */
	private int interfaces_count;

	/**
	 * 这里是每一个接口的详细描述,这个集合中每个值都是int，它是代表了指向常量池constant_pool中的索引，且保证常量池中被索引到的常量是一个
	 * {@link ConstantType#CONSTANT_CLASS}类型<br>
	 * (u2 interfaces[interfaces_count])
	 */
	private int[] interfaces;

	/**
	 * 每一个class文件会有很多个字段，那么这个变量就是为了记录当前类中有多少个字段(类字段)<br>
	 * (u2 fields_count)
	 */
	private int fields_count;

	/**
	 * 每一个字段的具体内容<br>
	 * (field_info fields[fields_count])
	 */
	private FieldInfo[] fields;

	/**
	 * 每一个class文件会有很多个方法，那么这个变量就是为了记录当前类中有多少个方法(包含默认的构造方法)<br>
	 * (u2 methods_count)
	 */
	private int methods_count;

	/**
	 * 每一个方法的具体内容<br>
	 * (method_info methods[methods_count])
	 */
	private MethodInfo[] methods;

	/**
	 * 每一个class会有许多个属性，例如InnerClasses等，那么记录在此处，每一个字段或方法也会有属性，那些属性会记录在相应的方法或字段上，
	 * 而这里记录的是整个ClassFile范围的<br>
	 * (u2 attributes_count)
	 */
	private int attributes_count;

	/**
	 * 属性的详细信息<br>
	 * (attribute_info attributes[attributes_count])
	 */
	private Attribute[] attributes;

	/**
	 * 存储了当前class的副本
	 */
	private byte[] buffer;

	public void read(DataInputStreamDecoration input) throws InvalidByteCodeException, IOException {
		ClassFileAnalyzer analyzer = new ClassFileAnalyzer();
		analyzer.read(this, input);
	}

	/**
	 * 将一个流转为classFile对象
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static ClassFile valueOf(InputStream input) throws InvalidByteCodeException, IOException {
		ClassFileAnalyzer analyzer = new ClassFileAnalyzer();
		ClassFile classFile = new ClassFile();
		analyzer.read(classFile, input);
		return classFile;
	}

	/**
	 * 将一个流转为classFile对象
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static ClassFile valueOf(byte[] buffer) throws InvalidByteCodeException, IOException {
		ClassFileAnalyzer analyzer = new ClassFileAnalyzer();
		ClassFile classFile = new ClassFile();
		analyzer.read(classFile, buffer);
		return classFile;
	}

	/**
	 * 重新设置class的全部byte内容，会导致当前对象的变更
	 * 
	 * @param buffer
	 * @return
	 * @throws InvalidByteCodeException
	 * @throws IOException
	 */
	public ClassFile flush() throws InvalidByteCodeException, IOException {
		ClassFileAnalyzer analyzer = new ClassFileAnalyzer();
		return analyzer.read(this, buffer);
	}

	@Override
	public final ClassFile clone() {
		ClassFileAnalyzer analyzer = new ClassFileAnalyzer();
		try {
			return analyzer.read(new ClassFile(), buffer);
		} catch (Exception e) {
			return null;
		}
	}

	/** 偏移量修正值的列表,修正在某一个索引值处因为class元素或字节码的变更导致原始元素的offset不准确，需要修正的值 */
	private Correction headCorrection = null;

	/**
	 * 设置某一个索引位置的偏移量
	 * 
	 * @return
	 */
	public void putCorrectionOffset(int index, int offset) {
		Correction correctionTemp = this.getCorrection(index);
		if (correctionTemp != null) {
			if (correctionTemp.getIndex() == index) {
				correctionTemp.setOffset(offset + correctionTemp.getOffset());
			} else {
				Correction newCorrection = new Correction();
				newCorrection.setIndex(index);
				newCorrection.setOffset(offset);
				newCorrection.setPrevious(correctionTemp);
				correctionTemp.setNext(correctionTemp.getNext());
			}
		} else {
			headCorrection = new Correction();
			headCorrection.setIndex(index);
			headCorrection.setOffset(offset);
		}
	}

	/**
	 * 获得修正值偏移量
	 * 
	 * @return
	 */
	public int getCorrectionOffset(int index) {
		if (headCorrection == null) {
			return index;
		}
		Correction correctionTemp = this.getCorrection(index);
		return correctionTemp != null && correctionTemp.getIndex() != index ? correctionTemp.getCorrection() + index
				: index;
	}

	/** 获得修正值偏移量的对象 */
	private Correction getCorrection(int index) {
		if (headCorrection == null) {
			return null;
		}
		Correction correctionTemp = headCorrection;
		while (correctionTemp.getIndex() <= index) {
			if (correctionTemp.getNext() == null) {
				return correctionTemp;
			} else if (correctionTemp.getNext().getIndex() >= index) {
				return correctionTemp;
			}
			correctionTemp = correctionTemp.getNext();
		}
		return null;
	}

	public int getMagic() {
		return magic;
	}

	void setMagic(int magic) {
		this.magic = magic;
	}

	public int getMinor_version() {
		return minor_version;
	}

	void setMinor_version(int minor_version) {
		this.minor_version = minor_version;
	}

	public int getMajor_version() {
		return major_version;
	}

	void setMajor_version(int major_version) {
		this.major_version = major_version;
	}

	public int getConstant_pool_count() {
		return constant_pool_count;
	}

	void setConstant_pool_count(int constant_pool_count) {
		this.constant_pool_count = constant_pool_count;
	}

	public CPInfo[] getConstant_pool() {
		return constant_pool;
	}

	void setConstant_pool(CPInfo[] constant_pool) {
		this.constant_pool = constant_pool;
	}

	public int getAccess_flags() {
		return access_flags;
	}

	void setAccess_flags(int access_flags) {
		this.access_flags = access_flags;
	}

	public int getThis_class() {
		return this_class;
	}

	void setThis_class(int this_class) {
		this.this_class = this_class;
	}

	public int getSuper_class() {
		return super_class;
	}

	void setSuper_class(int super_class) {
		this.super_class = super_class;
	}

	public int getInterfaces_count() {
		return interfaces_count;
	}

	void setInterfaces_count(int interfaces_count) {
		this.interfaces_count = interfaces_count;
	}

	public int[] getInterfaces() {
		return interfaces;
	}

	void setInterfaces(int[] interfaces) {
		this.interfaces = interfaces;
	}

	public int getFields_count() {
		return fields_count;
	}

	void setFields_count(int fields_count) {
		this.fields_count = fields_count;
	}

	public FieldInfo[] getFields() {
		return fields;
	}

	void setFields(FieldInfo[] fields) {
		this.fields = fields;
	}

	public int getMethods_count() {
		return methods_count;
	}

	void setMethods_count(int methods_count) {
		this.methods_count = methods_count;
	}

	public MethodInfo[] getMethods() {
		return methods;
	}

	void setMethods(MethodInfo[] methods) {
		this.methods = methods;
	}

	public int getAttributes_count() {
		return attributes_count;
	}

	void setAttributes_count(int attributes_count) {
		this.attributes_count = attributes_count;
	}

	public Attribute[] getAttributes() {
		return attributes;
	}

	void setAttributes(Attribute[] attributes) {
		this.attributes = attributes;
	}

	public byte[] getBuffer() {
		return buffer;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("magic = 0XCAFEBABE").append("\r\n");
		sb.append("minor_version = ").append(minor_version).append("\r\n");
		sb.append("major_version = ").append(major_version).append("\r\n");
		sb.append("constant_pool_count = ").append(constant_pool_count).append("\r\n");
		if (constant_pool != null) {
			for (int i = 0; i < constant_pool.length; i++) {
				sb.append("constant_pool[").append(i).append("] = ").append(constant_pool[i]).append("\r\n");
			}
		}
		sb.append("access_flags = ").append(access_flags).append("  ").append("detail = ");

		if ((access_flags & Global.ACC_PUBLIC) != 0) {
			sb.append("public ");
		}
		if ((access_flags & Global.ACC_FINAL) != 0) {
			sb.append("final ");
		}
		if ((access_flags & Global.ACC_SUPER) != 0) {
			sb.append("ACC_SUPER ");
		}

		if ((access_flags & Global.ACC_INTERFACE) != 0) {
			sb.append("interface ");
		}

		if ((access_flags & Global.ACC_ABSTRACT) != 0) {
			sb.append("abstract ");
		}

		if ((access_flags & Global.ACC_SYNTHETIC) != 0) {
			sb.append("ACC_SYNTHETIC ");
		}

		if ((access_flags & Global.ACC_ANNOTATION) != 0) {
			sb.append("annotation ");
		}

		if ((access_flags & Global.ACC_ENUM) != 0) {
			sb.append("enum ");
		}

		sb.append("\r\n");
		sb.append("this_class = ").append(this_class).append("  ");
		sb.append("detail = ").append(constant_pool[this_class]).append("\r\n");
		sb.append("super_class = ").append(super_class).append("  ");
		sb.append("detail = ").append(constant_pool[super_class]).append("\r\n");
		sb.append("interfaces_count = ").append(interfaces_count).append("\r\n");
		if (interfaces != null) {
			for (int i = 0; i < interfaces.length; i++) {
				sb.append("interfaces[").append(i).append("] = ").append(interfaces[i]).append("  ");
				sb.append("detail = ").append(constant_pool[interfaces[i]]).append("\r\n");
			}
		}
		sb.append("fields_count = ").append(fields_count).append("\r\n");
		if (fields != null) {
			for (int i = 0; i < fields.length; i++) {
				sb.append("fields[").append(i).append("] = ").append(fields[i]).append("\r\n");
			}
		}
		sb.append("methods_count = ").append(methods_count).append("\r\n");
		if (methods != null) {
			for (int i = 0; i < methods.length; i++) {
				sb.append("methods[").append(i).append("] = ").append(methods[i]).append("\r\n");
			}
		}
		sb.append("attributes_count = ").append(attributes_count).append("\r\n");
		if (attributes != null) {
			for (int i = 0; i < attributes.length; i++) {
				sb.append("attributes[").append(i).append("] = ").append(attributes[i]).append("\r\n");
			}
		}
		return sb.toString();
	}

	/**
	 * 获得当前元素在class中的偏移量，classFile固定为0
	 */
	@Override
	public int getOffset() {
		return 0;
	}

	/**
	 * 获取当前元素长度，ClassFile的长度是整个class的长度
	 */
	@Override
	public int getLength() {
		return buffer.length;
	}

	void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	/**
	 * 将当前元素转换为byte数组
	 * 
	 * @return
	 */
	@Override
	public byte[] toByteArray() {
		ByteArrayOutputStream byteArrayOutputStream = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			DataOutputStream outputStream = new DataOutputStream(byteArrayOutputStream);
			outputStream.writeInt(magic);
			outputStream.writeShort(minor_version);
			outputStream.writeShort(major_version);
			outputStream.writeShort(constant_pool_count);
			for (int i = 0; i < constant_pool_count; i++) {
				if (constant_pool[i] == null) {
					continue;
				}
				outputStream.write(constant_pool[i].toByteArray());
			}
			outputStream.writeShort(access_flags);
			outputStream.writeShort(this_class);
			outputStream.writeShort(super_class);
			outputStream.writeShort(interfaces_count);
			for (int i = 0; i < interfaces_count; i++) {
				outputStream.writeShort(interfaces[i]);
			}
			outputStream.writeShort(fields_count);
			for (int i = 0; i < fields_count; i++) {
				outputStream.write(fields[i].toByteArray());
			}
			outputStream.writeShort(methods_count);
			for (int i = 0; i < methods_count; i++) {
				outputStream.write(methods[i].toByteArray());
			}
			outputStream.writeShort(attributes_count);
			for (int i = 0; i < attributes_count; i++) {
				outputStream.write(attributes[i].toByteArray());
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return byteArrayOutputStream.toByteArray();
	}

	@Override
	public int writeTo(ClassFile classFile, int offset, OperationType operationType) throws InvalidByteCodeException,
			IOException {
		throw new UnsupportedOperationException();
	}

	public void writeToInterface(int[] interfaces) {
		this.interfaces = interfaces;
		this.interfaces_count = interfaces.length;
		setBuffer(toByteArray());
		try {
			this.flush();
		} catch (InvalidByteCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void writeToCPInfo(CPInfo[] constant_pool) {
		this.constant_pool = constant_pool;
		this.constant_pool_count = constant_pool.length;
		setBuffer(toByteArray());
		try {
			this.flush();
		} catch (InvalidByteCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void writeToMethodInfo(MethodInfo[] methodInfos) {
		this.methods_count = methodInfos.length;
		this.methods = methodInfos;
		setBuffer(toByteArray());
		try {
			this.flush();
		} catch (InvalidByteCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void writeToAttribute(Attribute[] attributes) {
		this.attributes_count = attributes.length;
		this.attributes = attributes;
		setBuffer(toByteArray());
		try {
			this.flush();
		} catch (InvalidByteCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void writeToFieldInfo(FieldInfo[] fieldInfos) {
		this.fields_count = fieldInfos.length;
		this.fields = fieldInfos;
		setBuffer(toByteArray());
		try {
			this.flush();
		} catch (InvalidByteCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

class ClassFileAnalyzer {

	public ClassFile read(ClassFile classFile, InputStream input) throws InvalidByteCodeException, IOException {
		// 第一步将流中的byte备份
		byte[] buffer = toBytes(input);
		return read(classFile, buffer);
	}

	public static byte[] toBytes(InputStream input) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		byte[] b = new byte[1024];
		while ((len = input.read(b, 0, b.length)) != -1) {
			baos.write(b, 0, len);
		}
		byte[] buffer = baos.toByteArray();
		return buffer;
	}

	public ClassFile read(ClassFile classFile, byte[] buffer) throws InvalidByteCodeException, IOException {
		classFile.setBuffer(buffer);
		// 创建新的流用于解析操作
		DataInputStreamDecoration dataInput = new DataInputStreamDecoration(new ByteArrayInputStream(buffer));
		readMagic(classFile, dataInput);
		readVersion(classFile, dataInput);
		readCpInfo(classFile, dataInput);
		readAccessFlags(classFile, dataInput);
		readThisClass(classFile, dataInput);
		readSuperClass(classFile, dataInput);
		readInterfaces(classFile, dataInput);
		readFields(classFile, dataInput);
		readMethods(classFile, dataInput);
		readAttributes(classFile, dataInput);
		return classFile;
	}

	/**
	 * 读取魔数，如果魔数不是咖啡宝贝，那么抛出异常，标识这不是一个Class格式的文件
	 * 
	 * @param classFile
	 * @param input
	 */
	private void readMagic(ClassFile classFile, DataInputStreamDecoration input) throws InvalidByteCodeException,
			IOException {
		int magic = input.readInt();
		if (magic != 0XCAFEBABE) {
			throw new InvalidByteCodeException("Invalid migic number.");
		}
	}

	/**
	 * 读取版本号
	 * 
	 * @param classFile
	 * @param input
	 */
	private void readVersion(ClassFile classFile, DataInputStreamDecoration input) throws InvalidByteCodeException,
			IOException {
		classFile.setMinor_version(input.readUnsignedShort());
		classFile.setMajor_version(input.readUnsignedShort());
	}

	/**
	 * 读取常量池信息
	 * 
	 * @param classFile
	 * @param input
	 */
	private void readCpInfo(ClassFile classFile, DataInputStreamDecoration input) throws InvalidByteCodeException,
			IOException {
		// Step 1.首先取出常量池的大小，使用这个大小，确定常量池数组的长度
		int constantPoolCount = input.readUnsignedShort();
		CPInfo[] constant_pool = new CPInfo[constantPoolCount];
		// Step 2.根据常量池的长度，逐个遍历每个常量，常量池是从1开始的(索引范围是1至constant_pool_count−1)
		for (int i = 1; i < constantPoolCount; i++) {
			// 找到每个常量的类型后读取常量中的内容
			ConstantType constantType = ConstantType.valueOf(input.readByte());
			constant_pool[i] = constantType.newCpInfo(classFile);
			constant_pool[i].read(input);
			if (constantType == ConstantType.CONSTANT_DOUBLE || constantType == ConstantType.CONSTANT_LONG) {
				i++;
			}
		}

		classFile.setConstant_pool_count(constantPoolCount);
		classFile.setConstant_pool(constant_pool);
	}

	/**
	 * 读取访问标志
	 * 
	 * @param classFile
	 * @param input
	 */
	private void readAccessFlags(ClassFile classFile, DataInputStreamDecoration input) throws InvalidByteCodeException,
			IOException {
		classFile.setAccess_flags(input.readUnsignedShort());
	}

	/**
	 * 读取当前类名
	 * 
	 * @param classFile
	 * @param input
	 */
	private void readThisClass(ClassFile classFile, DataInputStreamDecoration input) throws InvalidByteCodeException,
			IOException {
		classFile.setThis_class(input.readUnsignedShort());
	}

	/**
	 * 读取当前类的父类
	 * 
	 * @param classFile
	 * @param input
	 */
	private void readSuperClass(ClassFile classFile, DataInputStreamDecoration input) throws InvalidByteCodeException,
			IOException {
		classFile.setSuper_class(input.readUnsignedShort());
	}

	/**
	 * 读取当前类的接口
	 * 
	 * @param classFile
	 * @param input
	 */
	private void readInterfaces(ClassFile classFile, DataInputStreamDecoration input) throws InvalidByteCodeException,
			IOException {
		int interfacesCount = input.readUnsignedShort();
		int[] interfaces = new int[interfacesCount];
		for (int i = 0; i < interfacesCount; i++) {
			interfaces[i] = input.readUnsignedShort();
		}
		classFile.setInterfaces_count(interfacesCount);
		classFile.setInterfaces(interfaces);
	}

	/**
	 * 读取当前类的字段
	 * 
	 * @param classFile
	 * @param input
	 */
	private void readFields(ClassFile classFile, DataInputStreamDecoration input) throws InvalidByteCodeException,
			IOException {
		int fieldsCount = input.readUnsignedShort();
		FieldInfo[] fields = new FieldInfo[fieldsCount];
		for (int i = 0; i < fieldsCount; i++) {
			FieldInfo fieldInfo = new FieldInfo(classFile);
			fieldInfo.read(input);
			fields[i] = fieldInfo;
		}
		classFile.setFields_count(fieldsCount);
		classFile.setFields(fields);
	}

	/**
	 * 读取当前类的方法
	 * 
	 * @param classFile
	 * @param input
	 */
	private void readMethods(ClassFile classFile, DataInputStreamDecoration input) throws InvalidByteCodeException,
			IOException {
		int methodsCount = input.readUnsignedShort();
		MethodInfo[] methods = new MethodInfo[methodsCount];
		for (int i = 0; i < methodsCount; i++) {
			MethodInfo methodInfo = new MethodInfo(classFile);
			methodInfo.read(input);
			methods[i] = methodInfo;
		}
		classFile.setMethods_count(methodsCount);
		classFile.setMethods(methods);
	}

	/**
	 * 读取当前类的属性
	 * 
	 * @param classFile
	 * @param input
	 */
	private void readAttributes(ClassFile classFile, DataInputStreamDecoration input) throws InvalidByteCodeException,
			IOException {
		int attributesCount = input.readUnsignedShort();
		Attribute[] attributes = new Attribute[attributesCount];
		for (int i = 0; i < attributesCount; i++) {
			int nameIndex = input.readUnsignedShort();
			String attributeName = ((ConstantUTF8Info) classFile.getConstant_pool()[nameIndex]).getValue();
			AttributeType attributeType = AttributeType.get(attributeName);
			Attribute attribute = attributeType != null ? attributeType.getAttribute(classFile, nameIndex)
					: new DefaultAttribute(classFile, nameIndex, "DefaultAttribute");
			attribute.read(input);
			attributes[i] = attribute;
		}
		classFile.setAttributes_count(attributesCount);
		classFile.setAttributes(attributes);
	}
}

/**
 * 偏移修正量,在某一处索引需要修正的值
 * 
 * @author 
 * 
 */
class Correction {

	/** 这个修正量所位于的索引位置 */
	private int index;

	/** 这个索引值处因为修改而产生的偏移量 */
	private int offset;

	/** 此索引处的偏移量，加上他前面全部索引值位置的偏移量，就等于了这个最终的偏移量修正值 */
	private int correction;

	/** 用于标识此修正量的前一个修正量 */
	private Correction previous;

	/** 用于标识此修正量的后一个修正量 */
	private Correction next;

	public void setIndex(int index) {
		this.index = index;
	}

	public void setOffset(int offset) {
		this.offset = offset;
		this.correction = previous != null ? previous.correction + offset : offset;
	}

	public void setPrevious(Correction previous) {
		if (previous == null || previous.equals(this.previous)) {
			return;
		}
		this.previous = previous;
		setOffset(offset);// 当前索引的前一个索引变更，会影响到当前偏移量的变更
		this.previous.setNext(this);
	}

	public void setNext(Correction next) {
		if (next == null || next.equals(this.next)) {
			return;
		}
		this.next = next;
		if (this.next != null) {
			this.next.setPrevious(this);
		}
	}

	public int getIndex() {
		return index;
	}

	public int getOffset() {
		return offset;
	}

	public int getCorrection() {
		return correction;
	}

	public Correction getPrevious() {
		return previous;
	}

	public Correction getNext() {
		return next;
	}
}