package cn.net.cvtt.lian.common.serialization.protobuf.types;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * double[]类型数据序列化
 * 
 * @auther 
 */
public class ProtoDoubleArray extends ProtoEntity {

	@ProtoMember(1)
	private double[] value;

	/**
	 * @return the value
	 */
	public double[] getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double[] value) {
		this.value = value;
	}
	
}
