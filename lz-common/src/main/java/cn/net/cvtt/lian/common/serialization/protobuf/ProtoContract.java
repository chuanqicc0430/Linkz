package cn.net.cvtt.lian.common.serialization.protobuf;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * <b>描述: </b>标注一个类是可以通过protobuf批注方式进行序列化的
 * <p>
 * <b>功能: </b>通过该标注，可以使一个类使用protobuf批注方式进行序列化
 * <p>
 * <b>用法: </b>将该批注放入类定义前
 * <p>
 * 
 * @deprecated 已过期,且失效,勿使用
 * @author 
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProtoContract {
}
