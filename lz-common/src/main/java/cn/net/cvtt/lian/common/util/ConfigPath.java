package cn.net.cvtt.lian.common.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * <b>描述: </b>这是一个用于描述ConfigBean或其子类针对Properties资源文件的起始路径的注解，该注解用于定义当前
 * {@link ConfigBean} 的起始路径，所谓其实路径，就是例如<code>librarianInfo.name</code>，如果
 * {@link ConfigBean}的子类中不加入 {@link ConfigPath}注解，则默认的起始根路径是从root开始，如果加入了
 * {@link ConfigPath}注解，且注解 {@link ConfigPath#value()}
 * 为librarianInfo，那么代表从librarianInfo开始，因此 {@link ConfigBean}
 * 只会解析librarianInfo后面的name
 * <p>
 * <b>功能: </b>用于描述ConfigBean或其子类针对Properties资源文件的起始路径的注解
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * 假设有一个Properties文件如下
 * Properties props = new Properties();
 * props.setProperty("librarianInfo.name", "ZhangSanfeng");
 * props.setProperty("librarianInfo.sex", "male");
 * props.setProperty("librarianInfo.age", "108");
 * 
 * &#064;ConfigPath("librarianInfo")
 * public class LibrarianInfo extends ConfigBean{
 *   private String name;
 *   private String sex;
 *   private int age;
 *   Getter and Setter ...
 *  }
 * 
 * LibrarianInfo librarianInfo = ConfigBean.valueOf(props, LibrarianInfo.class);
 * Assert.assertEquals(library.getName(), "ZhangSanfeng");
 * Assert.assertEquals(library.getSex(), "male");
 * Assert.assertEquals(library.getAge(), 108);
 * </pre>
 * 
 * 加入&#064;ConfigPath("librarianInfo")注解后，使当前root路径为librarianInfo，
 * 因此只解析librarianInfo之后的内容，所以实现了此效果，关于<code>ConfigBean</code>信息请参见
 * {@link ConfigBean}
 * <p>
 * 
 * @author 
 * @see ConfigBean
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConfigPath {
	String value() default "";
}
