package cn.net.cvtt.configuration.spi;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.configuration.ConfigParams;
import cn.net.cvtt.configuration.ConfigTable;
import cn.net.cvtt.configuration.ConfigTableItem;
import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationException;
import cn.net.cvtt.configuration.ConfigurationManager;
import cn.net.cvtt.configuration.LoadZkConfigProperties;
import cn.net.cvtt.configuration.bridge.ConfigBridgeListener;
import cn.net.cvtt.configuration.bridge.ConfigBridgeWatcher;
import cn.net.cvtt.configuration.resource.ResConfigTableBuffer;
import cn.net.cvtt.configuration.resource.ResdbConfigHelper;
import cn.net.cvtt.lian.common.util.EnumParser;
import cn.net.cvtt.resource.database.DatabaseProxy;
import cn.net.cvtt.resource.route.ResourceFactory;

public class ZookeeperConfigurator implements ConfigBridgeListener, Configurator {
	private static Logger LOGGER = LoggerFactory.getLogger(ZookeeperConfigurator.class);

	private AtomicBoolean registered = new AtomicBoolean(false);
	
	@Override
	public void onNodeChanged(String configPath) {
		try {
			String[] path = configPath.split("/");
			String[] configs = path[path.length - 1].split("#");
			ConfigurationManager.updateConfig(EnumParser.valueOf(ConfigType.class, configs[1]), configs[0], configs.length == 2 ? "" : configs[2]);
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <K, V extends ConfigTableItem> ConfigTable<K, V> loadConfigTable(Class<K> keyType, Class<V> valueType, String path, ConfigUpdateAction<ConfigTable<K, V>> updateCallback) throws ConfigurationException {
		try {
			// 1.首选获取此配置表的配置信息RESOURCEDB.RES_ConfigTables : TableName DatabaseName
			ResConfigTableBuffer tableBuffer = new ResConfigTableBuffer();
			String dbName = ResdbConfigHelper.getDatabaseName(path, tableBuffer);

			// 2.根据配置信息到具体的数据库获取此配置表的内容
			if (dbName != null) {
				DatabaseProxy database = ResourceFactory.getDatabaseProxy(dbName);
				if (database == null) {
					throw new ConfigurationException("no database found by path:" + path);
				}
				tableBuffer = ResdbConfigHelper.getConfigTable(database, path, tableBuffer);
			} else {
				throw new ConfigurationException("no database found by path:" + path);
			}
			// 3.转换为Table，且执行callback
			ConfigTable<K, V> configTableTemp = tableBuffer.toTable(keyType, valueType);
			if (updateCallback != null) {
				updateCallback.run(configTableTemp);
			}
			return configTableTemp;
		} catch (Exception e) {
			throw new ConfigurationException("", e);
		}
	}

	@Override
	public String loadConfigText(String path, ConfigParams params, ConfigUpdateAction<String> updateCallback) throws ConfigurationException {
		LOGGER.info("Load config text. path:{},params:{}", path, params != null ? params.toString() : "");
		try {
			// 如果从本地取配置文件出现问题，那么转头向DB中索取
			String text = getDBConfigText(path, params);
			if (updateCallback != null) {
				updateCallback.run(text);
			}
			return text;
		} catch (Exception e) {
			LOGGER.error(String.format("Database found error. path:%s,params%s", path, params != null ? params.toString() : ""), e);
			throw new ConfigurationException("", e);
		}
	}

	@Override
	public void subscribeConfig(ConfigType type, String path, ConfigParams params) throws Exception {
		StringBuffer configPath = new StringBuffer();
		configPath.append(String.format("/%s/", LoadZkConfigProperties.getInstance().getPropValue("rootPath")));
		configPath.append(String.format("%s#%s", path, type.name()));
		if (params != null) {
			configPath.append(String.format("#%s", params.toString()));
		}
		if (registered.compareAndSet(false, true)) {
			ConfigBridgeWatcher.getInstance().register(this);
			LOGGER.warn("Init zookeeper and zkDataLoader.");
		}
		ConfigBridgeWatcher.getInstance().internalInitDataLoader(configPath.toString());
		
//		bridge.register(this);
//		bridge.init();
	}

	/**
	 * 从数据库中取出指定路径作为KEY，且符合指定的{@link ConfigParams}的记录
	 * 
	 * @param path
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	private String getDBConfigText(final String path, final ConfigParams params) throws SQLException, ConfigurationException {
		LOGGER.info("Get configuration form database. path:{},params:{}", path, params != null ? params.toString() : "");
		return ResdbConfigHelper.getConfigText(path, params).getText();
	}
}