package cn.net.cvtt.configuration.spi;

import cn.net.cvtt.configuration.ConfigParams;
import cn.net.cvtt.configuration.ConfigTable;
import cn.net.cvtt.configuration.ConfigTableItem;
import cn.net.cvtt.configuration.ConfigTableKey;
import cn.net.cvtt.configuration.ConfigType;
import cn.net.cvtt.configuration.ConfigUpdateAction;
import cn.net.cvtt.configuration.ConfigurationException;
import cn.net.cvtt.configuration.ConfigurationFailedException;

/**
 * 配置的实际基础类
 * 
 * @author 
 */
public interface Configurator {
	/**
	 * 
	 * @see ConfigTableItem
	 * @see ConfigTableKey
	 * @param <K>
	 * @param <V>
	 * @param keyType
	 *            配置表主键类型, 可以是基础类型, 或者组合类型 , 当为组合类型时必须从ConfigTableKey中派生
	 * @param valueType
	 *            配置表值类型, 必须从ConfigTableItem类派生
	 * @param path
	 *            表名
	 * @param updateCallback
	 *            更新的callback
	 * @return
	 * @throws ConfigurationFailedException
	 *             , ConfigurationNotFoundException
	 */
	<K, V extends ConfigTableItem> ConfigTable<K, V> loadConfigTable(Class<K> keyType, Class<V> valueType, String path, ConfigUpdateAction<ConfigTable<K, V>> updateCallback) throws ConfigurationException;

	/**
	 * 
	 * {在这里补充功能说明}
	 * 
	 * @param path
	 * @param params
	 * @param onUpdate
	 * @return
	 * @throws ConfigurationException
	 */
	String loadConfigText(String path, ConfigParams params, ConfigUpdateAction<String> updateCallback) throws ConfigurationException;

	/**
	 * 
	 * {在这里补充功能说明}
	 * 
	 * @param type
	 * @param path
	 */
	void subscribeConfig(ConfigType type, String path, ConfigParams params) throws Exception;
}
