package cn.net.cvtt.lian.common.serialization.bytecode.type;

public class InvalidByteCodeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -768115021248704731L;

	public InvalidByteCodeException() {
		super();
	}

	public InvalidByteCodeException(String msg) {
		super(msg);
	}

	public InvalidByteCodeException(Exception e) {
		super(e);
	}
}
