package cn.net.cvtt.imps.exception;

import cn.net.cvtt.imps.lianzi.enums.ProtocolErrorRsp;

public class LzCustomImpsException extends Exception {

	private static final long serialVersionUID = -3553863869931397360L;

	private int returnCode;

	public LzCustomImpsException(String message) {
		super(message);
		this.returnCode = ProtocolErrorRsp.SERVICE_ERROR.getCode();
	}

	public LzCustomImpsException(String message, Exception ex) {
		this(message, ex, ProtocolErrorRsp.SERVICE_ERROR.getCode());
		if (ex instanceof LzCustomImpsException) {
			this.returnCode = ((LzCustomImpsException) ex).getReturnCode();
		}
	}

	public LzCustomImpsException(String message, int returnCode) {
		this(message, null, returnCode);
	}

	public LzCustomImpsException(String message, Exception ex, int returnCode) {
		super(message, ex);
		this.returnCode = returnCode;
	}

	public Exception getEx() {
		return this;
	}

	public String getMessage() {
		return super.getMessage();
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

}
