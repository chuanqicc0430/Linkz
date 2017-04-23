package cn.net.cvtt.lian.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * <b>描述: </b>一个随机选择器，在将指定的数据源打散后调用{@link RandomPicker#pickOne()}取出，实现随机选择
 * <p>
 * <b>功能: </b>随机选择器
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * List&lt;String&gt; list = new ArrayList&lt;String&gt;();
 * list.add(&quot;a&quot;);
 * list.add(&quot;b&quot;);
 * list.add(&quot;c&quot;);
 * list.add(&quot;d&quot;);
 * RandomPicker&lt;String&gt; randomPicker = new RandomPicker&lt;String&gt;(list);
 * System.out.println(randomPicker.pickOne());
 * System.out.println(randomPicker.pickOne());
 * System.out.println(randomPicker.pickOne());
 * System.out.println(randomPicker.pickOne());
 * </pre>
 * <p>
 * 
 * @author 
 * 
 * @param <T>
 */
public class RandomPicker<T>
{
	private final List<T> data = new ArrayList<T>();;
	private final int size;
	private int idx = 0;

	public RandomPicker(List<T> data)
	{		 
		this.data.addAll(data);
		this.size = data.size();
		Collections.shuffle(this.data);
	}

	public T pickOne()
	{
		if (size > 0)
		{
			return data.get(idx++ % size);
		}
		else
		{
			return null;
		}
	}

	public List<T> data()
	{
		return data;
	}
}
