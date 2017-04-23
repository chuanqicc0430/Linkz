package cn.net.cvtt.lian.common.serialization.json;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.net.cvtt.lian.common.serialization.Serializer;

/**
 * 
 * <b>描述: </b>用于标识一个类想要通过Json的方式进行序列化，被此标识的类在调用{@link Serializer#encode(Object)}
 * 或{@link Serializer#decode(Class, byte[])}时，会自动通过{@link JsonCodecFactory}获得一个
 * {@link JsonCodec}的对象，由{@link JsonCodec}来帮助进行JSON格式的序列化或反序列化
 * <p>
 * <b>功能: </b>用于标识一个类想要通过Json的方式进行序列化
 * <p>
 * <b>用法: </b>如果一个类想要通过Json的方式进行序列化，那么将此注解标于类声明前
 * <p>
 * 
 * @author 
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonContract {

}
