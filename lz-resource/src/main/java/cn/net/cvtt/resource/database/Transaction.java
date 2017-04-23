package cn.net.cvtt.resource.database;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <b>描述：</b>事务调用类
 * <p>
 * <b>功能：</b>启动一个事务
 * <p>
 * <b>用法:</b>
 * 
 * <pre>
 * <code>
 * Transaction trans = Database.beginTransaction();
 * try{
 * 	trans.spExcuteNoQuery();
 * 	trans.spExcuteNoQuery();
 * 	trans.commit();
 * }catch(Exception ex)
 * {
 * 	trans.rollback();
 * }
 * finally
 * {
 * 	trans.close();
 * }
 * </code>
 * </pre>
 * 
 * <b>备注:</b>mysql 表的engine=InnoDB,否则事务不起作用
 * 
 * @author
 */
public class Transaction extends Database {
	Connection conn = null;

	Transaction(Connection conn, String dbName) throws SQLException {
		if (conn == null) {
			throw new InvalidParameterException("参数不能为空");
		}
		this.dbName = dbName;
		this.conn = conn;
		this.conn.setAutoCommit(false);
	}

	/**
	 * 释放数据库连接，用户在不使用Transaction对象后必须显示执行此方法
	 */
	public void close() {
		DatabaseHelper.attemptClose(conn);
	}

	/**
	 * 事务提交
	 */
	public void commit() {
		DatabaseHelper.attemptCommit(conn);
	}

	/**
	 * 事务回滚
	 */
	public void rollback() {
		DatabaseHelper.attemptRollback(conn);
	}

	/**
	 * 
	 * @return Connection
	 * @throws SQLException
	 *             Database每次获取最新的 Connection,用完close Transaction 用自身的Connection,用完不close
	 */
	@Override
	public Connection getConnection() throws SQLException {
		this.conn.setAutoCommit(false);
		return this.conn;
	}

	@Override
	void closeConnection(Connection conn) {
		// don't close
	}

}
