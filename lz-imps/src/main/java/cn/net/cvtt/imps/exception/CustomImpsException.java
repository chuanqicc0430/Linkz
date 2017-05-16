package cn.net.cvtt.imps.exception;

public class CustomImpsException extends Exception {

	private static final long serialVersionUID = -3553863869931397360L;

	private int returnCode;

	public CustomImpsException(String message) {
		super(message);
		this.returnCode = 500;
	}

	public CustomImpsException(String message, Exception ex) {
		this(message, ex, 500);
		if (ex instanceof CustomImpsException) {
			this.returnCode = ((CustomImpsException) ex).getReturnCode();
		}
	}

	public CustomImpsException(String message, int returnCode) {
		this(message, null, returnCode);
	}

	public CustomImpsException(String message, Exception ex, int returnCode) {
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
