package cn.net.cvtt.configuration;

import cn.net.cvtt.lian.common.util.EnumInteger;

/**
 * HA模式下支持的配置类型
 * 
 * @author 
 */
public enum ConfigType implements EnumInteger
{
	UNKOWN(0),
	
	/** 文本, 可以是xml或者其他文本  */
	TEXT(1),
	
	/** 表 */
	TABLE(2);

	private int value;
	
	ConfigType(int type)
	{
		value = type;
	}
	
	@Override
	public int intValue() {
		return value;
	}
}

