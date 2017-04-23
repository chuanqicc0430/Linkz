package cn.net.cvtt.lian.common.util.word;

/**
 * 
 * <b>描述: </b>字体转换类型枚举类，在{@link Translation}中使用，用于在字符串转码时明确指定转换时的目标类型
 * <p>
 * <b>功能: </b>用于在字符串转码时明确指定转换时的目标类型
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * Translation.trans.translate(TranslationWordInfo.BIG5_WORD, TranslationWordEnum.GBKTOBIG5)
 * </pre>
 * <p>
 * 
 * @author 
 * 
 */
public enum TranslationWordEnum {
	GBKTOBIG5(0), BIG5TOGBK(1);
	private Integer code;

	private TranslationWordEnum(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
