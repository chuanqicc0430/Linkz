package cn.net.cvtt.lian.common.serialization.protobuf.types;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * char[]类型数据序列化
 * 
 * @auther 
 */
public class ProtoCharArray extends ProtoEntity {

	@ProtoMember(1)
	private char[] value;

	/**
	 * @return the value
	 */
	public char[] getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(char[] value) {
		this.value = value;
	}
	
}
