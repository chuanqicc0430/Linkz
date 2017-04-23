package cn.net.cvtt.resource.database;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.resource.route.context.ApplicationCtx;
import cn.net.cvtt.resource.route.context.ContextUri;
import cn.net.cvtt.resource.route.resource.ResourceProxy;

/**
 * 一个DatabaseProxy后面可能包含一组Database 一组Database通过ApplicationCtx.getUri()进行定位
 * 
 * @author
 */
public class DatabaseProxy extends ResourceProxy {
	// TODO 记录错误日志
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseProxy.class);

	public DatabaseProxy() {
		super();
	}

	/**
	 * 执行不返回结果集的存储过程
	 * 
	 * @param ctx
	 *            ApplicationCtx对象
	 * @param spName
	 *            存储过程名
	 * @param params
	 *            参数名
	 * @param values
	 *            参数值
	 * @return (1)SQL 数据操作语言 (DML) 语句的行数 (2)对于无返回内容的SQL语句，返回 0
	 * @throws SQLException
	 */
	public int spExecuteNonQuery(ApplicationCtx ctx, String spName, String[] params, Object... values) throws SQLException {
		ContextUri uri = ctx.getContextUri();
		DatabaseResource dbResource = (DatabaseResource) locateResource(uri);
		LOGGER.info("process spExecuteNonQuery for {} {}", uri, dbResource);
		return dbResource.getDb().spExecuteNonQuery(spName, params, values);
	}

	/**
	 * 
	 * 执行一个存储过程, 返回一个DataTable, 结果缓存在DataTable中
	 * 
	 * @param ctx
	 *            ApplicationCtx对象
	 * @param spName
	 *            存储过程名
	 * @param params
	 *            参数名
	 * @param values
	 *            参数值
	 * @return 包含该查询生成的数据的DataTable对象
	 * @throws SQLException
	 */
	public DataTable spExecuteTable(ApplicationCtx ctx, String spName, String[] params, Object... values) throws SQLException {
		ContextUri uri = ctx.getContextUri();
		DatabaseResource dbResource = (DatabaseResource) locateResource(uri);
		return dbResource.getDb().spExecuteTable(spName, params, values);
	}

	/**
	 * 
	 * 执行一个存储过程, 返回一个List<DataTable>
	 * 
	 * @param ctx
	 *            ApplicationCtx对象
	 * @param spName
	 *            存储过程名
	 * @param params
	 *            参数名
	 * @param values
	 *            参数值
	 * @return 包含该查询生成的数据的List<DataTable>对象
	 * @throws SQLException
	 */
	public List<DataTable> spExecuteTables(ApplicationCtx ctx, String spName, String[] params, Object... values) throws SQLException {
		ContextUri uri = ctx.getContextUri();
		DatabaseResource dbResource = (DatabaseResource) locateResource(uri);
		return dbResource.getDb().spExecuteTables(spName, params, values);
	}

	/**
	 * 
	 * 执行一个存储过程, 返回一个List<DataTable>
	 * 
	 * @param ctx
	 *            ApplicationCtx对象
	 * @param spName
	 *            存储过程名
	 * @param params
	 *            参数名
	 * @param values
	 *            参数值
	 * @return 包含该查询生成的数据的List<DataTable>对象
	 * @throws SQLException
	 */
	public List<DataTable> spExecuteTables(Integer id, String spName, String[] params, Object... values) throws SQLException {
		DatabaseResource dbResource = (DatabaseResource) locateResource(id);
		return dbResource.getDb().spExecuteTables(spName, params, values);
	}

	/**
	 * 
	 * 执行一个存储过程, 返回一个List<DataTable>
	 * 
	 * @param ctx
	 *            ApplicationCtx对象
	 * @param spName
	 *            存储过程名
	 * @param params
	 *            参数名
	 * @param values
	 *            参数值
	 * @return 包含该查询生成的数据的List<DataTable>对象
	 * @throws SQLException
	 */
	public List<DataTable> spExecuteTables(String spName, String[] params, Object... values) throws SQLException {
		return spExecuteTables(0, spName, params, values);
	}

	/**
	 * 执行一个不返回结果的SQL语句
	 * 
	 * @param ctx
	 *            ApplicationCtx对象
	 * @param sql
	 *            可以包含?参数的sql语句
	 * @param values
	 *            ?对应的参数值
	 * @return (1)SQL 数据操作语言 (DML) 语句的行数 (2)对于无返回内容的SQL语句，返回 0
	 * @throws SQLException
	 */
	public int executeNonQuery(ApplicationCtx ctx, String sql, Object... values) throws SQLException {
		ContextUri uri = ctx.getContextUri();
		DatabaseResource dbResource = (DatabaseResource) locateResource(uri);
		return dbResource.getDb().executeNonQuery(sql, values);
	}

	/**
	 * 对一个带有自增长字段的表，执行一条insert语句，并返回自增长的值。
	 * 
	 * @param insertSql
	 *            可以包含?参数的insert语句
	 * @param values
	 *            ?对应的参数值
	 * @return 返回自增长字段的值。如果该表不带自增长字段，则返回-1。
	 * @throws SQLException
	 */
	public long executeInsertWithAutoColumn(String insertSql, Object... values) throws SQLException {
		return executeInsertWithAutoColumn(0, insertSql, values);
	}

	/**
	 * 对一个带有自增长字段的表，执行一条insert语句，并返回自增长的值。
	 * 
	 * @param insertSql
	 *            可以包含?参数的insert语句
	 * @param values
	 *            ?对应的参数值
	 * @return 返回自增长字段的值。如果该表不带自增长字段，则返回-1。
	 * @throws SQLException
	 */
	public long executeInsertWithAutoColumn(Integer id, String insertSql, Object... values) throws SQLException {
		DatabaseResource dbResource = (DatabaseResource) locateResource(id);
		return dbResource.getDb().executeInsertWithAutoColumn(insertSql, values);
	}

	/**
	 * 对一个带有自增长字段的表，执行一条insert语句，并返回自增长的值。
	 * 
	 * @param insertSql
	 *            可以包含?参数的insert语句
	 * @param values
	 *            ?对应的参数值
	 * @return 返回自增长字段的值。如果该表不带自增长字段，则返回-1。
	 * @throws SQLException
	 */
	public long executeInsertWithAutoColumn(ApplicationCtx ctx, String insertSql, Object... values) throws SQLException {
		ContextUri uri = ctx.getContextUri();
		DatabaseResource dbResource = (DatabaseResource) locateResource(uri);
		return dbResource.getDb().executeInsertWithAutoColumn(insertSql, values);
	}

	/**
	 * 执行一个SQL语句, 返回一个DataTable, 结果缓存在Table中
	 * 
	 * @param ctx
	 *            ApplicationCtx对象
	 * @param sql
	 *            可以包含?参数的sql语句
	 * @param values
	 *            ?对应的参数值
	 * @return 包含该查询生成的数据的DataTable对象
	 * @throws SQLException
	 */
	public DataTable executeTable(ApplicationCtx ctx, String sql, Object... values) throws SQLException {
		ContextUri uri = ctx.getContextUri();
		DatabaseResource dbResource = (DatabaseResource) locateResource(uri);
		return dbResource.getDb().executeTable(sql, values);
	}

	/**
	 * 执行一个SQL语句, 返回一个List<DataTable>
	 * 
	 * @param ctx
	 *            ApplicationCtx对象
	 * @param sql
	 *            可以包含?参数的sql语句
	 * @param values
	 *            ?对应的参数值
	 * @return 包含该查询生成的数据的List<DataTable>对象
	 * @throws SQLException
	 */
	public List<DataTable> executeTables(ApplicationCtx ctx, String sql, Object... values) throws SQLException {
		ContextUri uri = ctx.getContextUri();
		DatabaseResource dbResource = (DatabaseResource) locateResource(uri);
		return dbResource.getDb().executeTables(sql, values);
	}

	/**
	 * 执行一个存储过程, 返回一个DataTable, 结果缓存在DataTable中
	 * 
	 * @param id
	 *            物理poolID
	 * @param spName
	 *            存储过程名
	 * @param params
	 *            参数名
	 * @param values
	 *            参数值
	 * @return 包含该查询生成的数据的DataTable对象
	 * @throws SQLException
	 */
	public DataTable spExecuteTable(String spName, String[] params, Object... values) throws SQLException {
		return spExecuteTable(0, spName, params, values);
	}

	/**
	 * 执行一个存储过程, 返回一个DataTable, 结果缓存在DataTable中
	 * 
	 * @param id
	 *            物理poolID
	 * @param spName
	 *            存储过程名
	 * @param params
	 *            参数名
	 * @param values
	 *            参数值
	 * @return 包含该查询生成的数据的DataTable对象
	 * @throws SQLException
	 */
	public DataTable spExecuteTable(Integer id, String spName, String[] params, Object... values) throws SQLException {
		DatabaseResource dbResource = (DatabaseResource) locateResource(id);
		return dbResource.getDb().spExecuteTable(spName, params, values);
	}

	/**
	 * 执行不返回结果集的存储过程
	 * 
	 * @param id
	 *            物理poolID
	 * @param spName
	 *            存储过程名
	 * @param params
	 *            参数名
	 * @param values
	 *            参数值
	 * @return (1)SQL 数据操作语言 (DML) 语句的行数 (2)对于无返回内容的SQL语句，返回 0
	 * @throws SQLException
	 */
	public int spExecuteNonQuery(String spName, String[] params, Object... values) throws SQLException {
		return spExecuteNonQuery(0, spName, params, values);
	}

	/**
	 * 执行不返回结果集的存储过程
	 * 
	 * @param id
	 *            物理poolID
	 * @param spName
	 *            存储过程名
	 * @param params
	 *            参数名
	 * @param values
	 *            参数值
	 * @return (1)SQL 数据操作语言 (DML) 语句的行数 (2)对于无返回内容的SQL语句，返回 0
	 * @throws SQLException
	 */
	public int spExecuteNonQuery(Integer id, String spName, String[] params, Object... values) throws SQLException {
		DatabaseResource dbResource = (DatabaseResource) locateResource(id);
		LOGGER.info("process spExecuteNonQuery for {} {}", id, dbResource);
		return dbResource.getDb().spExecuteNonQuery(spName, params, values);
	}

	/**
	 * 执行一个SQL语句, 返回一个DataTable, 结果缓存在Table中
	 * 
	 * @param sql
	 *            可以包含?参数的sql语句
	 * @param values
	 *            ?对应的参数值
	 * @return 包含该查询生成的数据的DataTable对象
	 * @throws SQLException
	 */
	public DataTable executeTable(String sql, Object... values) throws SQLException {
		return executeTable(0, sql, values);
	}

	/**
	 * 执行一个SQL语句, 返回一个DataTable, 结果缓存在Table中
	 * 
	 * @param id
	 *            物理poolID
	 * @param sql
	 *            可以包含?参数的sql语句
	 * @param values
	 *            ?对应的参数值
	 * @return 包含该查询生成的数据的DataTable对象
	 * @throws SQLException
	 */
	public DataTable executeTable(Integer id, String sql, Object... values) throws SQLException {
		DatabaseResource dbResource = (DatabaseResource) locateResource(id);
		return dbResource.getDb().executeTable(sql, values);
	}

	/**
	 * 执行一个不返回结果的SQL语句
	 * 
	 * @param sql
	 *            可以包含?参数的sql语句
	 * @param values
	 *            ?对应的参数值
	 * @return (1)SQL 数据操作语言 (DML) 语句的行数 (2)对于无返回内容的SQL语句，返回 0
	 * @throws SQLException
	 */
	public int executeNonQuery(String sql, Object... values) throws SQLException {
		return executeNonQuery(0, sql, values);
	}

	/**
	 * 执行一个不返回结果的SQL语句
	 * 
	 * @param id
	 *            物理poolID
	 * @param sql
	 *            可以包含?参数的sql语句
	 * @param values
	 *            ?对应的参数值
	 * @return (1)SQL 数据操作语言 (DML) 语句的行数 (2)对于无返回内容的SQL语句，返回 0
	 * @throws SQLException
	 */
	public int executeNonQuery(Integer id, String sql, Object... values) throws SQLException {
		DatabaseResource dbResource = (DatabaseResource) locateResource(id);
		return dbResource.getDb().executeNonQuery(sql, values);
	}

	/**
	 * 返回定位的db对象
	 * 
	 * @param ctx
	 *            ApplicationCtx对象
	 * @return 资源定位的db对象
	 */
	public Database getLocateDatabase(ApplicationCtx ctx) {
		ContextUri uri = ctx.getContextUri();
		DatabaseResource dbResource = (DatabaseResource) locateResource(uri);
		return (Database) dbResource.getDb();
	}

	/**
	 * 返回定位的db对象
	 * 
	 * @param id
	 *            物理poolID
	 * @return 资源定位的db对象
	 */
	public Database getLocateDatabase(Integer id) {
		DatabaseResource dbResource = (DatabaseResource) locateResource(id);
		return (Database) dbResource.getDb();
	}

}
