package cn.net.cvtt.lian.common.serialization.protobuf.types;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;
/**
 * int类型数据序列化
 * 
 * @author 高磊 gaolei@feinno.com
 */

public class ProtoInteger extends ProtoEntity
{
	@ProtoMember(1)
	private int value;
	
	public ProtoInteger()
	{
	}
	
	public ProtoInteger(int value)
	{
		this.value = value; 
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}
}
