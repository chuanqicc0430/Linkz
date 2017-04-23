package cn.net.cvtt.http.container.core.configuration;

import java.io.IOException;

import javax.naming.ConfigurationException;

public class NettyFilterConfig {

	private static NettyFilterConfig instance;

	/*public static NettyFilterConfig getInstance() throws CUException {
		try {
			if (instance == null) {
				instance = new NettyFilterConfig();
			}
		} catch (Exception ex) {
			throw new CUException("Initial Setting failed", ex);
		}

		return instance;
	}

	*//**
	 * @param propertiesName
	 * @throws IOException
	 * @throws ConfigurationException
	 *//*
	private NettyFilterConfig() throws IOException, ConfigurationException {
		super("nettyfilter.properties");
		this.putString(BLACK_HOST, "10.10.188.98");
	}

	public boolean isBlackHost(String host) {
		String blackHosts = this.getString(BLACK_HOST);
		String[] bhArrays = blackHosts.split(";");
		for (String bh : bhArrays) {
			if (bh.equals(host)) {
				return true;
			}
		}
		return false;
	}*/

	private static final String BLACK_HOST = "blackHost";

}
