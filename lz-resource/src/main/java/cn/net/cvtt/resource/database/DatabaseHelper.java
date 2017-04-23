/*
 * FAE, Feinno App Engine
 *  
 * Create by gaolei 2011-1-24
 * 
 * Copyright (c) 2011 北京新媒传信科技有限公司
 */
package cn.net.cvtt.resource.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.rowset.CachedRowSetImpl;

/**
 * 数据库帮助类+
 * 
 * @author
 */
public class DatabaseHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

	public static void attemptClose(ResultSet o) {
		try {
			if (o != null)
				o.close();
		} catch (Exception e) {
			LOGGER.error("attemt close {} failed {}", o, e);
		}
	}

	public static void attemptClose(Statement o) {
		try {
			if (o != null)
				o.close();
		} catch (Exception e) {
			LOGGER.error("attemt close {} failed {}", o, e);
		}
	}

	public static void attemptClose(Connection o) {
		try {
			if (o != null)
				o.close();
		} catch (Exception e) {
			LOGGER.error("attemt close {} failed {}", o, e);
		}
	}

	public static void attemptCommit(Connection o) {
		try {
			if (o != null)
				o.commit();
		} catch (SQLException e) {
			LOGGER.error("attemt commit {} failed {}", o, e);
		}
	}

	public static void attemptRollback(Connection o) {
		try {
			if (o != null)
				o.rollback();
		} catch (SQLException e) {
			LOGGER.error("attemt rollback {} failed {}", o, e);
		}
	}

	public static String getCallSql(String spName, int paramsCount) {
		StringBuffer str = new StringBuffer();
		str.append("{call ").append(spName).append("(");

		for (int i = 0; i < paramsCount; i++) {
			str.append("?");
			if (i < paramsCount - 1)
				str.append(",");
		}
		str.append(")}");
		return str.toString();
	}

	public static void fillStatememt(CallableStatement stmt, String[] params, Object[] values) throws SQLException {
		if (values != null)
			for (int i = 0; i < values.length; i++) {
				if (params == null) {
					stmt.setObject(i + 1, values[i]);
				} else {
					stmt.setObject(params[i], values[i]);
				}
			}
	}

	public static String getSqlTrace(String sql, String[] params, Object[] values) {
		return sql;
	}

	public static List<CachedRowSet> getCachedRowSet(Statement stmt) throws SQLException {
		if (stmt == null)
			return null;
		ResultSet rs = stmt.getResultSet();
		List<CachedRowSet> list = new ArrayList<CachedRowSet>();
		while (rs != null && rs.getMetaData().getColumnCount() != 0) {
			CachedRowSet crs = new CachedRowSetImpl();
			crs.populate(rs);
			list.add(crs);
			stmt.getMoreResults();
			rs = stmt.getResultSet();
		}
		return list;
	}
}
