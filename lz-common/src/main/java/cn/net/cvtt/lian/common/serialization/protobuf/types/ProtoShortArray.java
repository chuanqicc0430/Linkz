package cn.net.cvtt.lian.common.serialization.protobuf.types;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * short[]类型数据序列化
 * 
 * @auther 
 */
public class ProtoShortArray extends ProtoEntity {
	
	@ProtoMember(1)
	private short[] value;

	/**
	 * @return the value
	 */
	public short[] getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(short[] value) {
		this.value = value;
	}
	
}
