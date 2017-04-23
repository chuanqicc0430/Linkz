package cn.net.cvtt.textfilter;

import cn.net.cvtt.lian.common.util.EnumInteger;

public enum FilterModeEnum implements EnumInteger{
	 None(0),				// 无意义
     Exact(1),				// 精确匹配
     Phrase(2),				// 短语匹配 (只针对英文, 去除两端的非英文字母, 和空格后精确匹配)
     // 例: "AV", 
     //		Java				不匹配
     //		给我弄一段AV看吧	    匹配
     //		下载A V呢			不匹配

     Filtered(3),			// 全角转半角, 去除空格及特殊符号后精确匹配// \"!@#$%^&*"
     Composite(4),          // 过滤规则类似Filtered, 但无主副词概念, 主词位置填写组合词数量
     Language(5),
	 MasterSlave(6);

	 private int value;
	 
	 private FilterModeEnum(int value){
		 this.value = value;
	 }
	 
	public int intValue() {
		return value;
	}
}
