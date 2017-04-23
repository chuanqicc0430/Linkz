package cn.net.cvtt.lian.common.util;

/**
 * 
 * <b>描述: </b>简单的包含三个参数的泛型模板类，不能用于序列化,三元组
 * <p>
 * <b>功能: </b>三元组
 * <p>
 * <b>用法: </b>三元组的正常使用方法
 * <p>
 *
 * @author 
 *
 * @param <T1>
 * @param <T2>
 * @param <T3>
 */
public class Combo3<T1, T2, T3>
{
	private T1 v1;
	private T2 v2;
	private T3 v3;
	
	/**
	 * @return the v1
	 */
	public T1 getV1()
	{
		return v1;
	}

	/**
	 * @param v1 the v1 to set
	 */
	public void setV1(T1 v1)
	{
		this.v1 = v1;
	}

	/**
	 * @return the v2
	 */
	public T2 getV2()
	{
		return v2;
	}

	/*
	 * @see java.lang.Object#hashCode()
	 */
	/**
	 * {在这里补充功能说明}
	 * @return
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
		result = prime * result + ((v3 == null) ? 0 : v3.hashCode());
		return result;
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/**
	 * {在这里补充功能说明}
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Combo3<?, ?, ?> other = (Combo3<?, ?, ?>) obj;
		if (v1 == null) {
			if (other.v1 != null)
				return false;
		} else if (!v1.equals(other.v1))
			return false;
		if (v2 == null) {
			if (other.v2 != null)
				return false;
		} else if (!v2.equals(other.v2))
			return false;
		if (v3 == null) {
			if (other.v3 != null)
				return false;
		} else if (!v3.equals(other.v3))
			return false;
		return true;
	}

	/**
	 * @return the v3
	 */
	public T3 getV3()
	{
		return v3;
	}

	/**
	 * @param v3 the v3 to set
	 */
	public void setV3(T3 v3)
	{
		this.v3 = v3;
	}

	/**
	 * @param v2 the v2 to set
	 */
	public void setV2(T2 v2)
	{
		this.v2 = v2;
	}

	public Combo3(T1 v1, T2 v2, T3 v3) 
	{
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}
}
