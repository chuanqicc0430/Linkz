package cn.net.cvtt.configuration;

/**
 * 配置异常公共类, 必须显示进行catch
 * 
 * @author 
 */
public class ConfigurationException extends Exception {
	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	static final long serialVersionUID = 1L;

	public ConfigurationException() {
	}

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(String message, Exception ex) {
		super(message, ex);
	}
}
