package cn.net.cvtt.lian.common.serialization.protobuf.types;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * byte类型数据序列化
 * 
 * @auther 
 */
public class ProtoByte extends ProtoEntity
{

	@ProtoMember(1)
	private byte value;

	public ProtoByte()
	{
	}

	public ProtoByte(byte value)
	{
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public byte getValue()
	{
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(byte value)
	{
		this.value = value;
	}
}
