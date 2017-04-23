
package cn.net.cvtt.resource.redis;

/** 
*
* @ClassName: ActionResult 
*
* @Description: 用于在Proxy中final声明后赋值result
* 
* @param <T> 
*
*/ 
public class ActionResult<T> {
	
	private T value = null;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
