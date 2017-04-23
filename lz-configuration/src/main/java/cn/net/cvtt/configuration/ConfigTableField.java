package cn.net.cvtt.configuration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标注一个配置表的字段名
 * 
 * @author 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigTableField
{
	String value();
	boolean trim() default true;
	boolean required() default true;
	boolean isKeyField() default false;
}
