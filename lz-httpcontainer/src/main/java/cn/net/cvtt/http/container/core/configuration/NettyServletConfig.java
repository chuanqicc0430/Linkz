package cn.net.cvtt.http.container.core.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * netty基础配置
 * 
 * @author 
 *
 */

public class NettyServletConfig {
	// 必须填写的
	private String ip;
	private int port;

	// 有默认值的
	private int readIdle = 90000;// 读操作空闲
	private int writeIdle = 60000;// 写操作空闲
	private int workerOutTime = 120000;
	private int servletThreadSize = 30;// 自定义EventExecutorGroup的线程池数量
	private int maxChunkContentSize = 65536;

	private Map<String, Object> params = new HashMap<String, Object>();

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the readIdle
	 */
	public int getReadIdle() {
		return readIdle;
	}

	/**
	 * @param readIdle
	 *            the readIdle to set
	 */
	public void setReadIdle(int readIdle) {
		this.readIdle = readIdle;
	}

	/**
	 * @return the writeIdle
	 */
	public int getWriteIdle() {
		return writeIdle;
	}

	/**
	 * @param writeIdle
	 *            the writeIdle to set
	 */
	public void setWriteIdle(int writeIdle) {
		this.writeIdle = writeIdle;
	}

	/**
	 * @return the workerOutTime
	 */
	public int getWorkerOutTime() {
		return workerOutTime;
	}

	/**
	 * @param workerOutTime
	 *            the workerOutTime to set
	 */
	public void setWorkerOutTime(int workerOutTime) {
		this.workerOutTime = workerOutTime;
	}

	/**
	 * @return the servletThreadSize
	 */
	public int getServletThreadSize() {
		return servletThreadSize;
	}

	/**
	 * @param servletThreadSize
	 *            the servletThreadSize to set
	 */
	public void setServletThreadSize(int servletThreadSize) {
		this.servletThreadSize = servletThreadSize;
	}

	/**
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * @return the maxChunkContentSize
	 */
	public int getMaxChunkContentSize() {
		return maxChunkContentSize;
	}

	/**
	 * @param maxChunkContentSize
	 *            the maxChunkContentSize to set
	 */
	public void setMaxChunkContentSize(int maxChunkContentSize) {
		this.maxChunkContentSize = maxChunkContentSize;
	}

	@Override
	public String toString() {
		return "NettyServletConfiguration [ip=" + ip + ", port=" + port + ", readIdle=" + readIdle + ", writeIdle=" + writeIdle + ", workerOutTime=" + workerOutTime + ", servletThreadSize=" + servletThreadSize + ", maxChunkContentSize=" + maxChunkContentSize + ", params=" + params + "]";
	}

}
