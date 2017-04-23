package cn.net.cvtt.lian.common.serialization.protobuf.types;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * long[]类型数据序列化
 * 
 * @auther 
 */
public class ProtoLongArray extends ProtoEntity{

	@ProtoMember(1)
	private long[] value;

	/**
	 * @return the value
	 */
	public long[] getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(long[] value) {
		this.value = value;
	}
	
}
