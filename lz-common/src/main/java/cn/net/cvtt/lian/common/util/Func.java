package cn.net.cvtt.lian.common.util;

/**
 * 
 * <b>描述: </b>通用的泛型委托接口，或用于将指定的入参类型加工后，返回另一个类型
 * <p>
 * <b>功能: </b>通用的泛型委托接口，或用于将指定的入参类型加工后，返回另一个类型
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * class FuncDemo {
 * 	static void test(String str, Func&lt;String, Integer&gt; func) {
 * 		int length = func.exec(str);
 * 		System.out.println(&quot;length = &quot; + length);
 * 	}
 * 
 * 	public static void main(String[] args) {
 * 		FuncDemo.test(&quot;Feinno&quot;, new Func&lt;String, Integer&gt;() {
 * 			public int run(String str) {
 * 				return str != null ? str.length() : 0;
 * 			}
 * 		});
 * 	}
 * }
 * </pre>
 * <p>
 * 
 * @author 
 * @see Func2
 * @see Func3
 * @param <T>
 * @param <E>
 */
public interface Func<T, E> {
	public E exec(T obj);
}