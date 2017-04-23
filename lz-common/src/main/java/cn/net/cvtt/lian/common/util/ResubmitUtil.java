package cn.net.cvtt.lian.common.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;


public class ResubmitUtil {
	private static final Logger logger = Logger.getLogger(ResubmitUtil.class);
	
	/**
	 * 获取obj对象所有属性值的hashcode之和
	 * @param obj
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static long getSubmitCode(Object obj) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
//		return getAllValues(obj);
		return getVal(obj);
	}
	/**
	 * 获取obj对象包含在fieldNameMap中的所有对象的hashcode之和
	 * @param obj
	 * @param fieldNameMap(key:fieldName;value:"",之所以用map是为了增加匹配filedName的命中率)
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static long getSubmitCode(Object obj, Map<String, String> fieldNameMap) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
//		return getAllValues(obj);
		return getVal(obj, fieldNameMap);
	}
	private static int getAllValues(Object obj) {
		int result = 0;
		StringBuffer sb = new StringBuffer();
		Method[] methods = obj.getClass().getDeclaredMethods();
		for(Method method:methods){
			Object returnType = method.getReturnType();
			String methodName = method.getName();
			try {
				if (returnType.toString().equals("void")) {
					continue;
				}
				Object value = method.invoke(obj);
				if (value == null) {
					continue;
				}
				if (isValue(returnType)) {
					result = result + value.hashCode();
					sb.append(value);
				} else if (value instanceof List<?>) {
					List<?> list = (List<?>)value;
					for (Object method2 : list) {
						if (isValue(method2.getClass())) {
							result = result + method2.hashCode();
							sb.append(method2);
						} else {
							result = result + getAllValues(method2);
							sb.append(getAllValues(method2));
						}
					}
				} else if (value instanceof Map<?, ?>) {
					Map<?, ?> map = (Map<?, ?>)value;
					for (Entry<?, ?> method2 : map.entrySet()) {
						if (isValue(method2.getKey().getClass())) {
							result = result + method2.hashCode();
							sb.append(method2);
						} else {
							result = result + getAllValues(method2);
							sb.append(getAllValues(method2));
						}
						if (isValue(method2.getValue().getClass())) {
							result = result + method2.hashCode();
							sb.append(method2);
						} else {
							result = result + getAllValues(method2);
							sb.append(getAllValues(method2));
						}
					}
				} else {
					result = result + getAllValues(value);
					sb.append(getAllValues(value));
				}
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				logger.error("ResubmitUtil err:" + methodName, e);
				continue;
			}
		}
		return result;
	}
	
	private static boolean isValue(Object obj) {
		boolean result = false;
		if (obj == Integer.class || obj == int.class) {
			result = true;
		} else if (obj == Long.class || obj == long.class) {
			result = true;
		} else if (obj == Double.class || obj == double.class) {
			result = true;
		} else if (obj == Float.class || obj == float.class) {
			result = true;
		} else if (obj == Character.class || obj == char.class) {
			result = true;
		} else if (obj == Boolean.class || obj == boolean.class) {
			result = true;
		} else if (obj == Short.class || obj == short.class) {
			result = true;
		} else if (obj == Byte.class || obj == byte.class) {
			result = true;
		} else if (obj == Byte.class || obj == byte.class) {
			result = true;
		} else if (obj == String.class) {
			result = true;
		}
		return result;
	}
	
	/**
	 * fieldNameMap不包含的属性不计算
	 * @param obj
	 * @param fieldNameMap
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static long getVal(Object obj, Map<String, String> fieldNameMap) throws IllegalArgumentException, IllegalAccessException {
		long result = 0;
		Field fields[]=obj.getClass().getDeclaredFields();//获得对象所有属性
			Field field=null;
			for (int i = 0; i < fields.length; i++) {
				field=fields[i];
				if (!fieldNameMap.containsKey(field.getName()) || field.get(obj) == null) {
					continue;
				}
				field.setAccessible(true);//修改访问权限
				Class<?> type = field.getType();
				if (isValue(type)) {
					result = result + field.get(obj).hashCode();
				} else if (type == List.class) {
					List<?> list = (List<?>)field.get(obj);
					for (Object val : list) {
						if (isValue(val.getClass())) {
							result = result + field.get(val).hashCode();
						} else {
							result = result + getVal(val);
						}
					}
				} else if (type == Map.class) {
					Map<?, ?> map = (Map<?, ?>)field.get(obj);
					for (Entry<?, ?> val : map.entrySet()) {
						if (isValue(val.getKey().getClass())) {
							result = result + field.get(val).hashCode();
						} else {
							result = result + getVal(val);
						}
						if (isValue(val.getValue().getClass())) {
							result = result + field.get(val).hashCode();
						} else {
							result = result + getVal(val);
						}
					}
				} else {
					result = result + getVal(field.get(obj));
				}
			}
		return result;
	}
	
	private static long getVal(Object obj) throws IllegalArgumentException, IllegalAccessException {
		if (obj == null) {
			return 0;
		}
		long result = 0;
		Field fields[]=obj.getClass().getDeclaredFields();//获得对象所有属性
			Field field=null;
			for (int i = 0; i < fields.length; i++) {
				field=fields[i];
				field.setAccessible(true);//修改访问权限
				Class<?> type = field.getType();
				if (field.get(obj) == null) {
					continue;
				}
				if (isValue(type)) {
					result = result + field.get(obj).hashCode(); 
				} else if (type == List.class) {
					List<?> list = (List<?>)field.get(obj);
					for (Object method2 : list) {
						if (isValue(method2.getClass())) {
							result = result + field.get(obj).hashCode(); 
						} else {
							result = result + getVal(method2); 
						}
					}
				} else if (type == Map.class) {
					Map<?, ?> map = (Map<?, ?>)field.get(obj);
					for (Entry<?, ?> method2 : map.entrySet()) {
						if (isValue(method2.getKey().getClass())) {
							result = result + field.get(obj).hashCode(); 
						} else {
							result = result + getVal(method2); 
						}
						if (isValue(method2.getValue().getClass())) {
							result = result + field.get(obj).hashCode(); 
						} else {
							result = result + getVal(method2); 
						}
					}
				} else {
					result = result + getVal(field.get(obj)); 
				}
			}
		return result;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			Test test = new Test();
			test1 test1 = new test1();
			test1.setVt("test1");
//			test.setVar1(11);
//			test.setVar2("22");
			test.setVar3(test1);
//			test.setVar5("");
			test.setVar4(Arrays.asList(test1));
			try {
				Field fields[]=test.getClass().getDeclaredFields();//获得对象所有属性
				Method[] methods = test.getClass().getDeclaredMethods();
				String fName = fields[0].getName();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			System.out.println(getAllValues(test));
			try {
				try {
					Map<String, String> map =  new HashMap<String, String>();
					map.put("var3", "");
					System.out.println(getSubmitCode(test));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	static class Test{
		private int var1;
		private String var2;
		private String var5;
		private test1 var3;
		private List<test1> var4;
		public int getVar1() {
			return var1;
		}
		public void setVar1(int var1) {
			this.var1 = var1;
		}
		public String getVar2() {
			return var2;
		}
		public void setVar2(String var2) {
			this.var2 = var2;
		}
		public test1 getVar3() {
			return var3;
		}
		public void setVar3(test1 var3) {
			this.var3 = var3;
		}
		public List<test1> getVar4() {
			return var4;
		}
		public void setVar4(List<test1> var4) {
			this.var4 = var4;
		}
		public String getVar5() {
			return var5;
		}
		public void setVar5(String var5) {
			this.var5 = var5;
		}
		
	}
	static class test1 {
		private String vt;

		public String getVt() {
			return vt;
		}

		public void setVt(String vt) {
			this.vt = vt;
		}
	}
}
