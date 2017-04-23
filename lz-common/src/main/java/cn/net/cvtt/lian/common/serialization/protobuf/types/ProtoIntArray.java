package cn.net.cvtt.lian.common.serialization.protobuf.types;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * int[]类型数据序列化
 * 
 * @auther 
 */
public class ProtoIntArray extends ProtoEntity{

	@ProtoMember(1)
	private int[] value;

	/**
	 * @return the value
	 */
	public int[] getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int[] value) {
		this.value = value;
	}
	
}
