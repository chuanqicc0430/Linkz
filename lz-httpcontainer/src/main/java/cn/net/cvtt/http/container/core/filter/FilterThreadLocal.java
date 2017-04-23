package cn.net.cvtt.http.container.core.filter;

/**
 * 参考netty的线程模型，在多线程环境下，保持变量一份，有多个线程副本
 * 
 * @author 
 *
 */
public class FilterThreadLocal {
	private static ThreadLocal<Boolean> isPass = new ThreadLocal<Boolean>() {
		public Boolean initialValue() {
			return false;
		}
	};

	@SuppressWarnings("static-access")
	public void setIsPass(Boolean _isPass) {
		this.isPass.set(_isPass);
	}

	public boolean getPass() {
		return isPass.get();
	}

	/**
	 * 可用可不用
	 */
	public void unUser() {
		isPass.remove();
	}
}
