package cn.net.cvtt.lian.common.serialization.protobuf.types;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * float[]类型数据序列化
 * 
 * @auther 
 */
public class ProtoFloatArray extends ProtoEntity {
	
	@ProtoMember(1)
	private float[] value;

	/**
	 * @return the value
	 */
	public float[] getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(float[] value) {
		this.value = value;
	}
	
}
