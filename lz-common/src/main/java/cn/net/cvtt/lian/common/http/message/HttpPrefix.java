package cn.net.cvtt.lian.common.http.message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author 
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface HttpPrefix {
	public static final String VALUE = "value";
 
	String path();

	String[] protocal();

	HttpMethod[] method() default HttpMethod.GET;
}
