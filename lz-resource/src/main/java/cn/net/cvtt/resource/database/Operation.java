/*
 * FAE, Feinno App Engine
 *  
 * Create by wanglihui 2011-1-10
 * 
 * Copyright (c) 2011 北京新媒传信科技有限公司
 */
package cn.net.cvtt.resource.database;

import java.sql.SQLException;
import java.util.List;

/**
 * <b>描述：</b>数据库操作接口
 * <p>
 * <b>功能：</b>数据库操作类集成并实现这个接口
 * <p>
 * <b>用法：</b> 
 * <pre><code>
 * public class {@link Database} implements Operation
 * {
 * }
 * </code></pre>
 * @author wanglihui
 */
public interface Operation
{
	/**
	 * 执行不返回结果集的存储过程
	 * @param spName 存储过程名
	 * @param params 参数名,如果参数为空，需要传递String类型的空数组，如定义：String[] params = {};然后传递params。
	 * @param values 参数值
	 * @return (1)SQL 数据操作语言 (DML) 语句的行数 (2)对于无返回内容的SQL语句，返回 0
	 * @throws SQLException
	 */
	int spExecuteNonQuery(String spName, String[] params, Object... values) throws SQLException;
	
	/**
	 * 执行一个存储过程, 返回一个DataReader对象, 这个DataReader对象在操作后必须执行DataReader对象的close()操作, 释放资源
	 * @param spName 存储过程名
	 * @param params 参数名,如果参数为空，需要传递String类型的空数组，如定义：String[] params = {};然后传递params。
	 * @param values 参数值
	 * @return 包含该查询生成的数据的DataReader对象
	 * @throws SQLException
	 */
//	@SuppressWarnings("deprecation")
//	DataReader spExecuteReader(String spName, String[] params, Object... values) throws SQLException;
	
	/**
	 * 
	 * 执行一个存储过程, 返回一个DataTable, 结果缓存在DataTable中
	 * @param spName 存储过程名
	 * @param params 参数名,如果参数为空，需要传递String类型的空数组，如定义：String[] params = {};然后传递params。
	 * @param values 参数值
	 * @return 包含该查询生成的数据的DataTable对象,参考：{@link DataTable}
	 * @throws SQLException
	 */
	DataTable spExecuteTable(String spName, String[] params, Object... values) throws SQLException;
	
	/**
	 * 
	 * 执行一个存储过程, 返回一个List<DataTable>, 结果缓存在List<DataTable>中
	 * @param spName 存储过程名
	 * @param params 参数名,如果参数为空，需要传递String类型的空数组，如定义：String[] params = {};然后传递params。
	 * @param values 参数值
	 * @return 包含该查询生成的数据的List<DataTable>对象，参考：{@link DataTable}
	 * @throws SQLException
	 */
	List<DataTable> spExecuteTables(String spName, String[] params, Object... values) throws SQLException;
	
	/**
	 * 执行一个不返回结果的SQL语句
	 * @param sql 可以包含?参数的sql语句
	 * @param values ?对应的参数值
	 * @return (1)SQL 数据操作语言 (DML) 语句的行数 (2)对于无返回内容的SQL语句，返回 0
	 * @throws SQLException
	 */
	int executeNonQuery(String sql, Object... values) throws SQLException; 
	
    /**
     * 对一个带有自增长字段的表，执行一条insert语句，并返回自增长的值。
     * @param insertSql 可以包含?参数的insert语句
     * @param values ?对应的参数值
     * @return 返回自增长字段的值。如果该表不带自增长字段，则返回-1。
     * @throws SQLException
     */
    long executeInsertWithAutoColumn(String insertSql, Object... values) throws SQLException; 

    /**
	 * 
	 * 执行一个SQL语句, 返回一个DataReader对象, 这个DataReader对象在操作后必须执行DataReader对象的close()操作, 释放资源
	 * @param sql 可以包含?参数的sql语句
	 * @param values ?对应的参数值
	 * @return 包含该查询生成的数据的DataReader对象
	 * @throws SQLException
	 */	
//	@SuppressWarnings("deprecation")
//	DataReader executeReader(String sql, Object... values) throws SQLException;
	
	/**
	 * 执行一个SQL语句, 返回一个DataTable, 结果缓存在Table中
	 * @param sql 可以包含?参数的sql语句
	 * @param values ?对应的参数值
	 * @return 包含该查询生成的数据的DataTable对象，参考：{@link DataTable}
	 * @throws SQLException
	 */
	DataTable executeTable(String sql, Object... values) throws SQLException;
	
	/**
	 * 执行一个SQL语句, 返回一个List<DataTable>, 结果缓存在Table中
	 * @param sql 可以包含?参数的sql语句
	 * @param values ?对应的参数值
	 * @return 包含该查询生成的数据的List<DataTable>对象，参考：{@link DataTable}
	 * @throws SQLException
	 */
	List<DataTable> executeTables(String sql, Object... values) throws SQLException;
}