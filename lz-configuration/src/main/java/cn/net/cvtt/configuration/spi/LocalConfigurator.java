package cn.net.cvtt.configuration.spi;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.configuration.ConfigParams;
import cn.net.cvtt.configuration.ConfigTable;
import cn.net.cvtt.configuration.ConfigTableItem;
import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationException;
import cn.net.cvtt.lian.common.util.LogHelper;

public class LocalConfigurator implements Configurator {

	@Override
	public <K, V extends ConfigTableItem> ConfigTable<K, V> loadConfigTable(Class<K> keyType, Class<V> valueType, String path, ConfigUpdateAction<ConfigTable<K, V>> updateCallback) throws ConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadConfigText(String path, ConfigParams params, ConfigUpdateAction<String> updateCallback) throws ConfigurationException {

		String txtconfig = null;
		try {
			if (path.indexOf(".properties") == -1) {
				path += ".properties";
			}
			InputStream in = LocalConfigurator.class.getClassLoader().getResourceAsStream(path);
			txtconfig = inputStream2String(in);
			updateCallback.run(txtconfig);
//			LogHelper.info(LOGGER, "Load LocalConfiguration Success", path, params);
		} catch (Exception e) {
//			LogHelper.error(LOGGER, "init localconfiguration err", e, path);
			throw new ConfigurationException("load localconfiguration failed:" + path, e);
		}
		return txtconfig;
	}

	private String inputStream2String(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	@Override
	public void subscribeConfig(ConfigType type, String path, ConfigParams params) throws Exception {
		// TODO Auto-generated method stub

	}

	private static Logger LOGGER = LoggerFactory.getLogger(LocalConfigurator.class);
}
