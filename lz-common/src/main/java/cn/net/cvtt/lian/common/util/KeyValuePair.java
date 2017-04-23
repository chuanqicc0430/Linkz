package cn.net.cvtt.lian.common.util;

import java.io.Serializable;

/**
 * 
 * <b>描述: </b>一个键值对，类似Map<K,V>中的一条记录，当前类和List组合，可实现一个Map
 * <p>
 * <b>功能: </b>键值对，类似Map<K,V>中的一条记录
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * List&lt;KeyValuePair&lt;Integer, String&gt;&gt; list = new ArrayList&lt;KeyValuePair&lt;Integer, String&gt;&gt;();
 * KeyValuePair keyValuePair = new KeyValuePair();
 * keyValuePair.setKey(1);
 * keyValuePair.setValue(&quot;Feinno&quot;);
 * list.add(keyValuePair);
 * </pre>
 * <p>
 * 
 * @author 
 * 
 * @param <K>
 * @param <V>
 */
public class KeyValuePair<K, V> implements Serializable
{
	private static final long serialVersionUID = -7308950510798096639L;
	
	private K key;
	private V value;
	
	public KeyValuePair(){}
	
	public KeyValuePair(K key, V value)
	{
		this.key = key;
		this.value = value;
	}
	
	public K getKey()
	{
		return key;
	}

	public V getValue()
	{
		return value;
	}

	public void setKey(K key)
	{
		this.key = key;
	}

	public void setValue(V value)
	{
		this.value = value;
	}
}
