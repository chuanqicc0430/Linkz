package cn.net.cvtt.lian.common.serialization.protobuf.types;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;
/**
 * boolean[]类型数据序列化
 * 
 * @auther 
 */
public class ProtoBooleanArray extends ProtoEntity {

	@ProtoMember(1)
	private boolean[] value;
	
	public ProtoBooleanArray()
	{
	}
	
	public ProtoBooleanArray(boolean[] value)
	{
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public boolean[] getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(boolean[] value) {
		this.value = value;
	}
	
}
