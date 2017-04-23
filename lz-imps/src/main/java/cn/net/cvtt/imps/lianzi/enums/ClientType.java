package cn.net.cvtt.imps.lianzi.enums;

import cn.net.cvtt.lian.common.util.EnumInteger;
import cn.net.cvtt.lian.common.util.EnumParser;

public enum ClientType  implements EnumInteger {
	IPHO(1),
	ANDR(2),
	IPAD(3),
	ANPD(4),
	PCCL(5),
	WEBC(6),
	UNKN(99);
	

	private final int value;

	ClientType(int value)
	{
		this.value = value;
	}

	@Override
	public int intValue() {
		return value;
	}
	
	public static ClientType parse(String value){
		ClientType clientType = EnumParser.valueOf(ClientType.class, value.toUpperCase());
    	if(clientType == null){
    		clientType = ClientType.UNKN;
    	}
    	return clientType;
	}
	public static ClientType parse(int value)
	{
		ClientType clientType = EnumParser.valueOf(ClientType.class, value);
    	if(clientType == null){
    		clientType = ClientType.UNKN;
    	}
    	return clientType;
	}
}
