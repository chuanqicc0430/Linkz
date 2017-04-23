package cn.net.cvtt.lian.common.initialization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * <b>描述: </b>标记一个静态方法为初始化器, 当一个类中有静态方法被标注为@Initialzer时,
 * 可以使用InitialUtil去按照依赖顺序加载各个带有初始化器的类
 * <p>
 * <b>功能: </b>当一个类中有静态方法被标注为@Initialzer时, 可以使用InitialUtil去按照依赖顺序加载各个带有初始化器的类
 * <p>
 * <b>用法: </b>该注释是方法注释，因此位于方法前
 * <p>
 * 
 * @author 
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Initializer {
}
