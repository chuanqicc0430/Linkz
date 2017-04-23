package cn.net.cvtt.lian.common.serialization.protobuf;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * <b>描述: </b>用于标识一个字段可以被protobuf序列化<br>
 * 一个类继承自{@link ProtoEntity}
 * 后既标识为这个类可以序列化，配合此注解，既可标识出类中的哪些字段可以被序列化，序列化组件在进行序列化时，会检测字段中是否有此注解
 * ，如果有，才会对此字段进行序列化，该注解中还额外提供特别的序列化处理方式.
 * <p>
 * <b>功能: </b>标识一个字段可以被序列化，且标识序列化时的细节处理
 * <p>
 * <b>用法: </b>将此注解置于类字段前,并设置各自的{@link ProtoMember#value()}值，请注意，
 * {@link ProtoMember#value()}值不能为空或重复，例如:
 * 
 * <pre>
 * &#064;ProtoMember(1)
 * private int index;
 * </pre>
 * <p>
 * 
 * @author 
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProtoMember {
	/**
	 * 用于标识该字段的序列化序号，该序号不能为空且不能重复，因为这个序号是写入二进制流中时该字段的唯一身份标识
	 * 
	 * @return
	 */
	int value();

	/**
	 * 用于标识该字段是否为必须字段，如果为true，那么代表此字段不能为null
	 * 
	 * @return
	 */
	boolean required() default false;

	/**
	 * 标识一个序列化字段的名字，该标识已过期，无意义
	 * 
	 * @deprecated
	 * @return
	 */
	String name() default "";

	/**
	 * 设置时间规范，不设置为默认，如果设置，仅可以设置为"UTC"字符串，例如下:
	 * 
	 * <pre>
	 * &#064;ProtoMember(value = 1, timezone = &quot;UTC&quot;)
	 * private int index;
	 * </pre>
	 * 
	 * @return
	 */
	String timezone() default "";

	/**
	 * 一个字段在序列化到流中时使用的具体格式，符合Google protobuf规范,如果不设置，则采用默认值，建议使用默认值
	 * 
	 * @return
	 */
	ProtoType type() default ProtoType.AUTOMATIC;
}
