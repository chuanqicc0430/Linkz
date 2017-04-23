package cn.net.cvtt.lian.common.serialization.protobuf.types;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * string类型数据序列化
 * 
 * @auther 
 */
public class ProtoString extends ProtoEntity
{
	@ProtoMember(1)
	private String value;

	public ProtoString()
	{
	}
	
	public ProtoString(String string)
	{
		this.value = string;
	}

	/**
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

}
