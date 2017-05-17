package cn.net.cvtt.configuration.resource;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.configuration.ConfigParams;
import cn.net.cvtt.configuration.ConfigurationException;
import cn.net.cvtt.lian.common.serialization.protobuf.util.TwoTuple;
import cn.net.cvtt.resource.database.DataRow;
import cn.net.cvtt.resource.database.DataTable;
import cn.net.cvtt.resource.database.DatabaseProxy;
import cn.net.cvtt.resource.route.ResourceFactory;

/**
 * 
 * <b>描述: </b>数据库中配置表处理的帮助类，用来辅助完成获取及解析配置表的信息
 * <p>
 * <b>功能: </b>用来辅助完成获取及解析配置表的信息
 * <p>
 * <b>用法: </b>
 * <p>
 * 
 * @author
 * 
 */
public class ResdbConfigHelper {

	/** 获取配置表中文本记录 */
	public static final String SQL_SELECT_CONFIG_TEXT = "SELECT ConfigKey, ConfigParams,ConfigText,Version FROM RES_ConfigTexts WHERE ConfigKey = ?";

	/** 获取配置表 */
	public static final String SQL_SELECT_CONFIG_TABLE = "SELECT TableName, DatabaseName,Version FROM RES_ConfigTables WHERE TableName = ?";

	private static final Logger LOGGER = LoggerFactory.getLogger(ResdbConfigHelper.class);

	/**
	 * 该方法用于LoadConfigTable时使用，它可以获取指定的表所位于的数据库名称，并取得该表当前的版本号， 放入传入的tableBuffer参数中<br>
	 * 
	 * @param database
	 * @param path
	 * @param tableBuffer
	 * @return
	 * @throws SQLException
	 */
	public static String getDatabaseName(String path, ResConfigTableBuffer tableBuffer) throws SQLException {
		try {
			DatabaseProxy proxy = ResourceFactory.getDatabaseProxy("RESOURCEDB");
			// 调用存储过程，获得某一表所位于的数据库名称以及版本号
			DataTable configTable = proxy.executeTable(SQL_SELECT_CONFIG_TABLE, path);

			tableBuffer.setTableName(path);
			tableBuffer.setVersion(configTable.getRow(0).getTimestamp("Version"));
			String dbName = configTable.getRow(0).getString("DatabaseName");
			return dbName;
		} catch (Exception e) {
			LOGGER.error("GetDatabaseName failed", new RuntimeException("Not found " + path + " by ConfigTables"), e);
			return null;
		}

	}

	/**
	 * 该方法从指定数据库中获取某一张表的全部数据，将数据放入传入的tableBuffer中返回给调用者
	 * 
	 * @param database
	 * @param path
	 * @param params
	 * @param tableBuffer
	 * @return
	 * @throws SQLException
	 */
	public static ResConfigTableBuffer getConfigTable(DatabaseProxy database, String path, ResConfigTableBuffer tableBuffer) throws SQLException {
		String sql = String.format("SELECT * FROM %s ", path);
		DataTable table = database.executeTable(sql);

		// DataTable table = database.spExecuteTable("USP_LoadConfigTable", new String[] { "@TableName" }, path);
		List<String> cols = new ArrayList<String>(table.getColumnCount());
		for (int i = 0; i < table.getColumnCount(); i++) {
			cols.add(table.getColumn(i + 1).getColumnName());
		}
		tableBuffer.setColumns(cols);
		List<ResConfigTableRow> rows = new ArrayList<ResConfigTableRow>(table.getRowCount());
		for (int i = 0; i < table.getRowCount(); i++) {
			ResConfigTableRow row = new ResConfigTableRow();
			DataRow dr = table.getRow(i);
			List<String> vals = new ArrayList<String>(table.getColumnCount());
			for (int j = 0; j < table.getColumnCount(); j++) {
				if (dr.getObject(j + 1) == null) {
					vals.add("");
				} else {
					if (dr.getObject(j + 1).toString().indexOf("ClobImpl") >= 0) {
						Clob clob = ((Clob) dr.getObject(j + 1));
						String s = clob.getSubString(1, (int) clob.length());
						vals.add(s);
					} else {
						vals.add(dr.getObject(j + 1).toString());
					}
				}
			}
			row.setValues(vals);
			rows.add(row);
		}
		tableBuffer.setRows(rows);
		return tableBuffer;
	}

	/**
	 * 从指定的数据库中获取文本类型的配置记录
	 * 
	 * @param database
	 * @param path
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws ConfigurationException
	 */
	public static ResConfigTextBuffer getConfigText(final String path, final ConfigParams params) throws SQLException, ConfigurationException {
		DatabaseProxy database = ResourceFactory.getDatabaseProxy("RESOURCEDB");
		DataTable dataTable = null;
		dataTable = database.executeTable(SQL_SELECT_CONFIG_TEXT, new Object[] { path });

		// 如果从数据库中取出多条记录，则对每条记录根据匹配度进行排序，选择匹配度最高的记录
		if (dataTable != null && dataTable.getRowCount() > 0) {
			return configTextFilter(dataTable, path, params);
		} else {
			LOGGER.error("Invoke getDBConfigText function. No record. key:{},params:{}", path, params);
			throw new ConfigurationException(String.format("invoke getDBConfigText function. No record. key:%s,params:%s", path, params));
		}
	}

	/**
	 * 文本配置类型的过滤，经过过滤后，仅取出最符合条件的一条文本记录
	 * 
	 * @param dataTable
	 * @param path
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws ConfigurationException
	 */
	private static ResConfigTextBuffer configTextFilter(DataTable dataTable, final String path, final ConfigParams params) throws SQLException, ConfigurationException {

		// LOGGER.info("Found {} result", dataTable.getRowCount());

		// 进行数据整理，将每一条符合条件的记录组成一个二元组，将多个二元组放入List中，用于下阶段的排序
		List<TwoTuple<ConfigParams, ResConfigTextBuffer>> resultList = sortConfigText(dataTable, params);

		// 取出匹配度最高的记录
		TwoTuple<ConfigParams, ResConfigTextBuffer> result = resultList.get(0);

		// 如果匹配度最高的记录，它的匹配度高于了1个，那么直接使用此条记录作为最终结果
		if (params != null && params.matchRegex(result.getFirst()) > 0) {
			// 经过排序后，确实有命中的，那么返回该条结果
			return result.getSecond();
		} else {
			// 如果匹配度最高的记录的命中率都是0，说明没有一个记录被命中上，那么遍历全部记录，寻找默认选项，默认项是params字段为空的那条记录
			for (TwoTuple<ConfigParams, ResConfigTextBuffer> twoTupleTemp : resultList) {
				if (twoTupleTemp.getFirst().keySet().size() == 0) {
					return twoTupleTemp.getSecond();
				}
			}
			// 如果连默认选项都没找到，那么最终返回错误
			LOGGER.error("No found default ConfigText. key:{},params:{}", path, params);
			throw new ConfigurationException(String.format("No found default ConfigText. key:%s,params:%s", path, params));
		}

	}

	/**
	 * 根据指定的params条件，对文本进行排序，命中率最高的在前面
	 * 
	 * @param dataTable
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws ConfigurationException
	 */
	private static List<TwoTuple<ConfigParams, ResConfigTextBuffer>> sortConfigText(DataTable dataTable, final ConfigParams params) throws SQLException, ConfigurationException {
		List<TwoTuple<ConfigParams, ResConfigTextBuffer>> resultList = new ArrayList<TwoTuple<ConfigParams, ResConfigTextBuffer>>();
		// 遍历数据，将数据整理成二元组格式的数据，放入List中，用于下一步的排序
		for (DataRow dataRow : dataTable.getRows()) {
			String configParams = (String) dataRow.getObject("ConfigParams");
			ResConfigTextBuffer textBuffer = new ResConfigTextBuffer();
			textBuffer.setText((String) dataRow.getObject("ConfigText"));
			textBuffer.setVersion(dataRow.getDateTime("Version"));
			ConfigParams paramsTemp = new ConfigParams(configParams);
			resultList.add(new TwoTuple<ConfigParams, ResConfigTextBuffer>(paramsTemp, textBuffer));
		}
		// 对上一步整理好的数据进行排序，排序依据是二元组第一元ConfigParams中的参数匹配度
		Collections.sort(resultList, new Comparator<TwoTuple<ConfigParams, ResConfigTextBuffer>>() {
			@Override
			public int compare(TwoTuple<ConfigParams, ResConfigTextBuffer> o1, TwoTuple<ConfigParams, ResConfigTextBuffer> o2) {
				return params.matchRegex(o2.getFirst()) - params.matchRegex(o1.getFirst());
			}
		});
		return resultList;
	}

}
