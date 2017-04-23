package cn.net.cvtt.lian.common.util;

/**
 * 
 * <b>描述: </b>具有两个入参一个出参的的通用的泛型委托接口，或用于将指定的入参类型加工后，返回另一个类型
 * <p>
 * <b>功能: </b>具有两个入参一个出参的通用的泛型委托接口，或用于将指定的入参类型加工后，返回另一个类型
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * class FuncDemo {
 * 	static void test(String str1, String str2, Func&lt;String, String, Integer&gt; func) {
 * 		int length = func.exec(str1, str2);
 * 		System.out.println(&quot;length = &quot; + length);
 * 	}
 * 
 * 	public static void main(String[] args) {
 * 		FuncDemo.test(&quot;Feinno&quot;, &quot;Good&quot;, new Func&lt;String, String, Integer&gt;() {
 * 			public int run(String str1, String str2) {
 * 				int str1Length = str1 != null ? str1.length() : 0;
 * 				int str2Length = str2 != null ? str2.length() : 0;
 * 				return str1Length + str2Length;
 * 			}
 * 		});
 * 	}
 * }
 * </pre>
 * <p>
 * 
 * @author 
 * @see Func
 * @see Func3
 * @param <T1>
 * @param <T2>
 * @param <E>
 */
public interface Func2<T1, T2, E>
{
	public E run(T1 v1, T2 v2);
}
