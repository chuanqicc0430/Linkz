package cn.net.cvtt.lian.common.util;

/**
 * 
 * <b>描述: </b>依托泛型实现的用于输出任意类型的参数
 * <p>
 * <b>功能: </b>用于输出类型的参数
 * <p>
 * <b>用法: </b>正常类型使用
 * <p>
 * 
 * @author 
 * 
 * @param <E>
 */
public class Outter<E>
{
	private E value;
	
	public Outter()
	{
	}
	
	public Outter(E value)
	{
		this.value = value;
	}
	
	public E value()
	{
		return value;
	}

	public void setValue(E value)
	{
		this.value = value;
	}
}
