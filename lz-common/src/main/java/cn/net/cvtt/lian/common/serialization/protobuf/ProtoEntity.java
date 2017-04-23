package cn.net.cvtt.lian.common.serialization.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.BitSet;
import java.util.List;

import cn.net.cvtt.lian.common.serialization.Serializer;
import cn.net.cvtt.lian.common.util.EnumInteger;

/**
 * <b>描述: </b>这是一个用于创建可序列化的Java类的基类<br>
 * 如果一个Java类A想要使用protobuf协议格式进行序列化，那么需要将A类继承此类或此类的派生类，并且将想要序列化的字段加上
 * {@link ProtoMember} 注解，详见用法部分
 * <p>
 * <b>功能：</b>将一个类定义成可序列化的类
 * <p>
 * <b>用法: </b>将一个类继承自此类或继承自此类的派生类，将想要序列化的字段加上{@link ProtoMember}注解，注解中
 * {@link ProtoMember#value()} 为int类型序号，序号不能重复，保证对应的字段有get/set方法，例如
 * 
 * <pre>
 * public class TestEntity extends ProtoEntity {
 * 
 * 	&#064;ProtoMember(1)
 * 	private int id;
 * 
 * 	&#064;ProtoMember(2)
 * 	private String name;
 * 
 * 	public int getId() {
 * 		return id;
 * 	}
 * 
 * 	public void setId(int id) {
 * 		this.id = id;
 * 	}
 * 
 * 	public String getName() {
 * 		return name;
 * 	}
 * 
 * 	public void setName(String name) {
 * 		this.name = name;
 * 	}
 * }
 * </pre>
 * 
 * <pre>
 * 序列化时:
 * TestEntity testEntity1 = new TestEntity(); //创建待序列化对象并赋值
 * testEntity1.setId(1);
 * testEntity1.setName(&quot;Feinno&quot;);
 * 以下两种序列化方式可二选一
 * (1). byte[] buffer = Serializer.encode(testEntity); //使用Serializer进行序列化(推荐使用)
 * (2). byte[] buffer = ProtoManager.toByteArray(testEntity); //使用ProtoManager进行序列化
 * 
 * 反序列化时：
 * TestEntity testEntity2 = new TestEntity();//创建一个空的对象
 * 以下两种反序列化方式可二选一
 * (1). TestEntity testEntity2 = Serializer.decode(TestEntity.class,buffer); //使用{@link Serializer}从字节数组中反序列化内容到对象中(推荐使用)
 * (2). ProtoManager.parseForm(testEntity2,buffer); //使用ProtoManager从字节数组中反序列化内容到之前创建的对象中
 * testEntity2.getId(); //正常取值使用
 * testEntity2.getName();
 * </pre>
 * 
 * 枚举类型稍有不同，一个枚举类型想要序列化，需要实现{@link EnumInteger}接口.
 * <p>
 * <b>注：</b>: 当一个想要序列化的类继承自此类的其他派生类时，如若派生类中有被{@link ProtoMember}
 * 标注的字段，那么该字段中的内容也会一并序列化到流中。
 * 
 * @author 
 * @see ProtoManager
 * @see ProtoMember
 * @see UnknownFieldSet
 * @see UnknownField
 */
public class ProtoEntity {

	/** 由于某种原因导致流中{@link ProtoMember}序号不在当前的JAVA对象中，则将此序号和数据存储在此区域 */
	private UnknownFieldSet unknownFieldSet = new UnknownFieldSet();

	/** 用于装载反序列化的字段名称，通过此集合中的内容，可以知道哪些字段值被反序列化到当前对象中 */
	private BitSet serializationFieldSet = new BitSet();

	/**
	 * 获得全部的未知字段，关于未知字段的描述和功能，请参见{@link UnknownFieldSet}
	 * 
	 * @return
	 */
	public UnknownFieldSet getUnknownFields() {
		return unknownFieldSet;
	}

	/**
	 * 通过调用此方法，来标识出一个字段是反序列化的
	 * 
	 * @param tag
	 */
	public void putSerializationFieldTag(int tag) {
		serializationFieldSet.set(tag);
	}

	/**
	 * 通过字段的tag值来判断一个字段是否是反序列化过来的
	 * 
	 * @param fieldName
	 * @return
	 */
	public boolean hasValue(int tag) {
		return serializationFieldSet.get(tag);
	}

	/**
	 * 反序列化
	 * 
	 * @param buffer
	 */
	public void parseFrom(byte[] buffer) {
		try {
			ProtoManager.parseFrom(this, buffer);
		} catch (IOException e) {
			throw new RuntimeException("Parse from the byte array throw an IOException ", e);
		}
	}

	/**
	 * 反序列化
	 * 
	 * @param input
	 */
	public void parseFrom(InputStream input) {
		try {
			ProtoManager.parseFrom(this, input);
		} catch (IOException e) {
			throw new RuntimeException("Parse from the data stream throw an IOException ", e);
		}
	}

	/**
	 * 序列化
	 * 
	 * @param output
	 */
	public void writeTo(OutputStream output) {
		try {
			ProtoManager.writeTo(this, output);
		} catch (IOException e) {
			throw new RuntimeException("Serializing to a outputStream throw an IOException ", e);
		}
	}

	/**
	 * 获得对象序列化的byt数组，同序列化
	 * 
	 * @return 被序列化后的byt数组
	 */
	public byte[] toByteArray() {
		try {
			return ProtoManager.toByteArray(this);
		} catch (IOException e) {
			throw new RuntimeException("Serializing to a byte array throw an IOException ", e);
		}
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		try {
			byte[] buffer = this.toByteArray();
			CodedInputStream codedInputStream = CodedInputStream.newInstance(buffer);
			List<ProtoBean> protoBeans = ProtoBufferHelper.print(codedInputStream);
			for (ProtoBean protoBean : protoBeans) {
				sb.append(protoBean.toString(""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
}
