package cn.net.cvtt.resource.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.rowset.CachedRowSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.rowset.CachedRowSetImpl;

import cn.net.cvtt.resource.database.pool.ConnectionPoolAdapter;
import cn.net.cvtt.resource.database.pool.ConnectionPoolAdapterFactory;
import cn.net.cvtt.resource.database.pool.DBConnectionPoolType;

/**
 * <b>描述: </b>一个数据库接口封装, 保证每个操作都是连接池安全的<br>
 * <p>
 * <b>功能：</b>执行访问数据库的实现类<br>
 * <p>
 * <b>用法: </b>构造一个Database对象，就可以访问某个数据库的存储过程、执行Sql语句。<br>
 * 例如：<br>
 * <pre><code>
 * Properties p = ...;  
 * 参考：{@link  DatabaseManager} {@link DBConnectionPoolType} <br/>
 * Database db = DatabaseManager.getDatabase("updb.1",p,DBConnectionPoolType.TomcatPool);<br>
 * String[] params = {"UserId","Name"};<br>
 * db.spExecuteNonQuery("test",params,30008909,"abc");<br>
 * </code></pre>
 * <p>
 * 
 * @author 
 */
public class Database implements Operation
{
	private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);
	
	String dbName="";
	ConnectionPoolAdapter poolAdapter;
	
	Database()
	{
		
	}
	
	/**
	 * 
	 * @param dbName
	 * @param configs
	 * @param type
	 * @throws Exception
	 */
	Database(String dbName, Properties configs,DBConnectionPoolType type ) throws Exception
	{
		this.dbName = dbName;
		poolAdapter = ConnectionPoolAdapterFactory.getInstance().getConnectionPoolAdapter(configs,type);
	}

	/**
	 * 默认C3p0连接池
	 * @param dbName
	 * @param configs
	 * @throws Exception
	 */
	Database(String dbName, Properties configs) throws Exception
	{
		this(dbName, configs,DBConnectionPoolType.C3p0);
	}
	
	
	/*
	 * @see com.feinno.database.Operation#spExecuteNonQuery(java.lang.String,
	 * java.lang.String[], java.lang.Object[])
	 */
	@Override
	public int spExecuteNonQuery(String spName, String[] params, Object... values) throws SQLException
	{
		params = params == null ? new String[]{} : params;
		values = values == null ? new String[]{} : values;
		
		Connection conn = null;
		CallableStatement stmt = null;
		String sql = "";
		String[] paramsTemp = new String[params.length]; // 临时存放参数名称，防止先mysql后sqlserver访问时修改了参数名称出错
//		Stopwatch watch = null;
		String obKey = "";
		try {
			obKey = dbName + ":" + spName;
//			SmartCounter counter = DatabasePerfmon.getCounter(obKey);
//			watch = counter.begin();
			
			if(params!=null)
				if (params.length != values.length) {
					throw new IllegalArgumentException("params.length != values.length");
				}
			conn = getConnection();
			
			String dbType = conn.getMetaData().getDatabaseProductName();
			if (dbType.equalsIgnoreCase("MySQL")) {
				convertAtToT(paramsTemp, params);
			} 
			// TODO 生成的Sql可以缓冲, 但是性能影响很小
			sql = DatabaseHelper.getCallSql(spName, values == null ? 0 : values.length);
			stmt = conn.prepareCall(sql);
			if (dbType.equalsIgnoreCase("MySQL")) {
				DatabaseHelper.fillStatememt(stmt, paramsTemp, values);
			} else {
				DatabaseHelper.fillStatememt(stmt, params, values);
			}

			return stmt.executeUpdate();
		} catch (SQLException e) {
//			watch.fail(e);
			LOGGER.error(obKey + " error: {}", e);
			throw e;
		} finally {
			DatabaseHelper.attemptClose(stmt);
			closeConnection(conn);
//			filter(obKey,watch);
		}
	}

	/*
	 * @see com.feinno.database.Operation#spExecuteReader(java.lang.String,
	 * java.lang.String[], java.lang.Object[])
	 */
	/*@SuppressWarnings("deprecation")
	@Override
	public DataReader spExecuteReader(String spName, String[] params, Object... values) throws SQLException
	{
		params = params == null ? new String[]{} : params;
		values = values == null ? new String[]{} : values;
		
		Connection conn = null;
		CallableStatement stmt = null;
		String sql = "";
		String[] paramsTemp = new String[params.length];// 临时存放参数名称，防止先mysql后sqlserver访问时修改了参数名称出错
		Stopwatch watch = null;
		boolean isSuccess = false;
		String obKey = "";
		try {
			obKey = dbName + ":" + spName;
			SmartCounter counter = DatabasePerfmon.getCounter(obKey);
			watch = counter.begin();
			
			if (params != null) {
				if (params.length != values.length) {
					throw new IllegalArgumentException("params.length != values.length");
				}
			}
			conn = getConnection();
			String dbType = conn.getMetaData().getDatabaseProductName();
			if (dbType.equalsIgnoreCase("MySQL")) {
				convertAtToT(paramsTemp, params);
			}
			sql = DatabaseHelper.getCallSql(spName, values == null ? 0 : values.length);
			stmt = conn.prepareCall(sql);
			// stmt.setObject(1, "CFG_Carrier");
			if (dbType.equalsIgnoreCase("MySQL")) {
				DatabaseHelper.fillStatememt(stmt, paramsTemp, values);
			} else {
				DatabaseHelper.fillStatememt(stmt, params, values);
			}

			stmt.executeQuery();
			List<CachedRowSet> list = DatabaseHelper.getCachedRowSet(stmt);
			DatabaseHelper.attemptClose(stmt);
			DataReader reader = new DataReader(list, conn);
			reader.setSql(sql);
			isSuccess = true;
			return reader;
		} catch (SQLException e) {
			watch.fail(e);
			LOGGER.error(obKey + " error: {}", e);
		
			throw e;
		} finally{
			LOGGER.info("SQL Statement:{}",sql);
			if(!isSuccess)
			{
				DatabaseHelper.attemptClose(stmt);
				closeConnection(conn);
			}
			filter(obKey,watch);
		}
	}*/

	/*
	 * @see com.feinno.database.Operation#spExecuteTable(java.lang.String,
	 * java.lang.String[], java.lang.Object[])
	 */
	@Override
	public DataTable spExecuteTable(String spName, String[] params, Object... values) throws SQLException
	{
		params = params == null ? new String[]{} : params;
		values = values == null ? new String[]{} : values;
		
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		String sql = "";
		String[] paramsTemp = new String[params.length];// 临时存放参数名称，防止先mysql后sqlserver访问时修改了参数名称出错
//		Stopwatch watch = null;
		String obKey = "";
		try {
			obKey = dbName + ":" + spName;
//			SmartCounter counter = DatabasePerfmon.getCounter(obKey);
//			watch = counter.begin();
			
			if (params != null) {
				if (params.length != values.length) {
					throw new IllegalArgumentException("params.length != values.length");
				}
			}
			conn = getConnection();
			String dbType = conn.getMetaData().getDatabaseProductName();
			if (dbType.equalsIgnoreCase("MySQL")) {
				convertAtToT(paramsTemp, params);
			}
			sql = DatabaseHelper.getCallSql(spName, values == null ? 0 : values.length);
			stmt = conn.prepareCall(sql);
			if (dbType.equalsIgnoreCase("MySQL")) {
				DatabaseHelper.fillStatememt(stmt, paramsTemp, values);
			} else {
				DatabaseHelper.fillStatememt(stmt, params, values);
			}
			rs = stmt.executeQuery();
			return new DataTable(rs);
		} catch (SQLException e) {
//			watch.fail(e);
			LOGGER.error(obKey + " error: {}", e);
			//throw new RuntimeException("spExecuteTable:" + spName + ":failed @" + this.dbName, e);
			throw e;
		} finally {
			DatabaseHelper.attemptClose(rs);
			DatabaseHelper.attemptClose(stmt);
			closeConnection(conn);
//			filter(obKey,watch);
		}
	}
	
	/*
	 * @see com.feinno.database.Operation#executeNonQuery(java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public int executeNonQuery(String sql, Object... values) throws SQLException
	{
		values = values == null ? new String[]{} : values;
		
		Connection conn = null;
		PreparedStatement stmt = null;
//		Stopwatch watch = null;
		String obKey = "";
		try {
			String formatSql = sql;
			//非参数化的sql
			if(values == null || values.length == 0)		
				formatSql = SqlNormalizer.format(sql);
			
			obKey = dbName + ":" + formatSql;			
//			SmartCounter counter = DatabasePerfmon.getCounter(obKey);
//			watch = counter.begin();
			
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			if(values!=null)
				for (int i = 0; i < values.length; i++) {
					stmt.setObject(i + 1, values[i]);
				}
			
			return stmt.executeUpdate();
		} catch (SQLException e) {
//			watch.fail(e);
			LOGGER.error(obKey + " error: {}", e);
			throw e;
		} finally {
			DatabaseHelper.attemptClose(stmt);
			closeConnection(conn);
//			filter(obKey,watch);
		}
	}

	/*
	 * @see com.feinno.database.Operation#executeReader(java.lang.String,
	 * java.lang.Object[])
	 */
	/*@SuppressWarnings("deprecation")
	@Override
	public DataReader executeReader(String sql, Object... values) throws SQLException
	{
		values = values == null ? new String[]{} : values;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		Stopwatch watch = null;
		boolean isSuccess = false;
		String obKey = "";
		try {
			String formatSql = sql;
			//非参数化的sql
			if(values == null || values.length == 0)		
				formatSql = SqlNormalizer.format(sql);
			
			obKey = dbName + ":" + formatSql;
			SmartCounter counter = DatabasePerfmon.getCounter(obKey);
			watch = counter.begin();
			
			
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			
			if(values!=null)
				for (int i = 0; i < values.length; i++) {
					stmt.setObject(i + 1, values[i]);
				}

			stmt.executeQuery();
			List<CachedRowSet> list = DatabaseHelper.getCachedRowSet(stmt);
			DatabaseHelper.attemptClose(stmt);
	
			DataReader reader = new DataReader(list, conn);
			reader.setSql(sql);
			isSuccess = true;
			return reader;
		} catch (SQLException e) {
			watch.fail(e);
			LOGGER.error(obKey + " error: {}", e);
			throw e;
		}
		finally
		{
			if(!isSuccess)
			{
				DatabaseHelper.attemptClose(stmt);
				closeConnection(conn);
			}
			filter(obKey,watch);
		}
	}*/

	/*
	 * @see com.feinno.database.Operation#executeTable(java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public DataTable executeTable(String sql, Object... values) throws SQLException
	{
		values = values == null ? new String[]{} : values;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
//		Stopwatch watch = null;
		String obKey = "";
		try {
			String formatSql = sql;
			//非参数化的sql
			if(values == null || values.length == 0)		
				formatSql = SqlNormalizer.format(sql);
			
			obKey = dbName + ":" + formatSql;
//			SmartCounter counter = DatabasePerfmon.getCounter(obKey);
//			watch = counter.begin();
			
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			
			if(values!=null)
				for (int i = 0; i < values.length; i++) {
					stmt.setObject(i + 1, values[i]);
				}

			rs = stmt.executeQuery();

			return new DataTable(rs);
		} catch (SQLException e) {
//			watch.fail(e);
			LOGGER.error(obKey + " error: {}", e);
			throw e;
		} finally {
			DatabaseHelper.attemptClose(rs);
			DatabaseHelper.attemptClose(stmt);
			closeConnection(conn);
//			filter(obKey,watch);
		}
	}

	/**
	 * 自定义方法，取得表主键。详细参考DatabaseMetaData类的getPrimaryKeys方法。
	 * 
	 * @param catalog
	 *            类别名称，它必须与存储在数据库中的类别名称匹配；该参数为 "" 表示获取没有类别的那些描述；为 null
	 *            则表示该类别名称不应该用于缩小搜索范围
	 * @param schema
	 *            模式名称，它必须与存储在数据库中的模式名称匹配；该参数为 "" 表示获取没有模式的那些描述；为 null
	 *            则表示该模式名称不应该用于缩小搜索范围
	 * @param table
	 *            表名称，它必须与存储在数据库中的表名称匹配
	 * @return 包含主键信息的DataReader对象
	 * @throws SQLException
	 */
	/*@SuppressWarnings("deprecation")
	public DataReader getPrimaryKeys(String catalog, String schema, String table) throws SQLException
	{
		Connection conn = null;
		ResultSet rs = null;
		conn = getConnection();
		rs = conn.getMetaData().getPrimaryKeys(catalog, schema, table);
		//return new DataReader(rs, null, conn);
		
		CachedRowSet crs = new CachedRowSetImpl();
		crs.populate(rs);
		DatabaseHelper.attemptClose(rs);
		List<CachedRowSet> list = new ArrayList<CachedRowSet>(); 
		list.add(crs);
		
		return new DataReader(list,conn);
	}*/

	/**
	 * 获取可在给定类别中使用的表的描述。详细参考DatabaseMetaData类的getTables方法。
	 * 
	 * @param catalog
	 *            类别名称；它必须与存储在数据库中的类别名称匹配；该参数为 "" 表示获取没有类别的那些描述；为 null
	 *            则表示该类别名称不应该用于缩小搜索范围
	 * @param schemaPattern
	 *            模式名称的模式；它必须与存储在数据库中的模式名称匹配；该参数为 "" 表示获取没有模式的那些描述；为 null
	 *            则表示该模式名称不应该用于缩小搜索范围
	 * @param tableNamePattern
	 *            表名称模式；它必须与存储在数据库中的表名称匹配
	 * @param types
	 *            要包括的表类型所组成的列表，必须取自从 getTableTypes() 返回的表类型列表；null 表示返回所有类型
	 * @return 包含表属性信息的DataReader对象
	 * @throws SQLException
	 */
	/*@SuppressWarnings("deprecation")
	public DataReader getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException
	{
		Connection conn = null;
		ResultSet rs = null;
		conn = getConnection();
		rs = conn.getMetaData().getTables(catalog, schemaPattern, tableNamePattern, types);
		//return new DataReader(rs, null, conn);
		
		CachedRowSet crs = new CachedRowSetImpl();
		crs.populate(rs);
		DatabaseHelper.attemptClose(rs);
		List<CachedRowSet> list = new ArrayList<CachedRowSet>(); 
		list.add(crs);
		
		return new DataReader(list,conn);
	}*/

	/**
	 * 获取可在指定类别中使用的表列的描述。详细参考DatabaseMetaData类的getColumns方法。
	 * 
	 * @param catalog
	 *            类别名称；它必须与存储在数据库中的类别名称匹配；该参数为 "" 表示获取没有类别的那些描述；为 null
	 *            则表示该类别名称不应该用于缩小搜索范围
	 * @param schemaPattern
	 *            模式名称的模式；它必须与存储在数据库中的模式名称匹配；该参数为 "" 表示获取没有模式的那些描述；为 null
	 *            则表示该模式名称不应该用于缩小搜索范围
	 * @param tableNamePattern
	 *            表名称模式；它必须与存储在数据库中的表名称匹配
	 * @param columnNamePattern
	 *            列名称模式；它必须与存储在数据库中的列名称匹配
	 * @return 包含列属性信息的DataReader对象
	 * @throws SQLException
	 */
	/*@SuppressWarnings("deprecation")
	public DataReader getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException
	{
		Connection conn = null;
		ResultSet rs = null;
		conn = getConnection();
		rs = conn.getMetaData().getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
		//return new DataReader(rs, null, conn);
		
		CachedRowSet crs = new CachedRowSetImpl();
		crs.populate(rs);
		DatabaseHelper.attemptClose(rs);
		List<CachedRowSet> list = new ArrayList<CachedRowSet>(); 
		list.add(crs);
		
		return new DataReader(list,conn);
	}*/

	/**
	 * 为了实现事务管理，返回Transaction对象，通过Transaction对象实现业务逻辑。
	 * 
	 * @return Transaction对象
	 * @throws SQLException
	 */
	public Transaction beginTransaction() throws SQLException
	{
		Connection conn = getConnection();
		return new Transaction(conn,dbName);
	}

	/**
	 * 如果数据库类型是MySQL，且参数中有@符号，自动转化@为T_。以前C-sharp中做了自动处理，此处为了兼容
	 * 
	 * @param params
	 */
	private void convertAtToT(String[] paramsTemp, String[] params)
	{
		paramsTemp = paramsTemp == null ? new String[]{} : paramsTemp;
		params = params == null ? new String[]{} : params;
		
		for (int i = 0; i < params.length; i++) {
			paramsTemp[i] = params[i];
			if (paramsTemp[i].contains("@"))
				paramsTemp[i] = paramsTemp[i].replaceFirst("@", "v_");
		}
	}

    /* (non-Javadoc)
     * @see com.feinno.database.Operation#executeAutoIncrementInsert(java.lang.String, java.lang.Object[])
     */
    @Override
    public long executeInsertWithAutoColumn(String insertSql, Object... values) throws SQLException {
    	values = values == null ? new String[]{} : values;
		
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
//        Stopwatch watch = null;
        String obKey = "";
		try {
			String formatSql = insertSql;
			//非参数化的sql
			if(values == null || values.length == 0)		
				formatSql = SqlNormalizer.format(insertSql);
			
			obKey = dbName + ":" + formatSql;
//			SmartCounter counter = DatabasePerfmon.getCounter(obKey);
//			watch = counter.begin();
			
            conn = getConnection();
            stmt = conn.prepareStatement(insertSql,PreparedStatement.RETURN_GENERATED_KEYS);
            
            if(values != null)
            	for (int i = 0; i < values.length; i++) {
            		stmt.setObject(i + 1, values[i]);
            	}

            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            
            if(rs.next()) {
                return rs.getLong(1);
            } else {
                return -1;
            }
            
        } catch (SQLException e){
//        	watch.fail(e);
        	LOGGER.error(obKey + " error: {}", e);
            throw e;
        } finally {
            DatabaseHelper.attemptClose(rs);
            DatabaseHelper.attemptClose(stmt);
            closeConnection(conn);
//            filter(obKey,watch);
        }
    }
    
    /**
     * 
     * @param sql 完整的sql
     * @return
     * @throws SQLException
     */
    public CachedRowSet excuteRowSet(String sql,Object... values) throws SQLException
	{
    	values = values == null ? new String[]{} : values;
    	
    	Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
//		Stopwatch watch = null;
		String obKey = "";
		try {
			String formatSql = sql;
				//非参数化的sql
			if(values == null || values.length == 0)		
				formatSql = SqlNormalizer.format(sql);
				
			obKey = dbName + ":" + formatSql;
//			SmartCounter counter = DatabasePerfmon.getCounter(obKey);
//			watch = counter.begin();
			
			conn = getConnection();
			stmt = conn.prepareStatement(sql);

			if(values != null)
				for (int i = 0; i < values.length; i++) {
					stmt.setObject(i + 1, values[i]);
				}

			rs = stmt.executeQuery();
			if(rs == null || rs.getMetaData().getColumnCount() == 0)
				return null;
			

			CachedRowSet crs = new CachedRowSetImpl();
			crs.populate(rs);	
			
			return crs;
		} catch (SQLException e) {
//			watch.fail(e);
			LOGGER.error(obKey + " error: {}", e);
			throw e;
		} finally {
			DatabaseHelper.attemptClose(rs);
			DatabaseHelper.attemptClose(stmt);
			closeConnection(conn);
//			filter(obKey,watch);
		}
    	
	}
    
    public CachedRowSet spExecuteRowSet(String spName, String[] params, Object... values) throws SQLException
	{
    	params = params == null ? new String[]{} : params;
		values = values == null ? new String[]{} : values;
		
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		String sql = "";
		String[] paramsTemp = new String[params.length];// 临时存放参数名称，防止先mysql后sqlserver访问时修改了参数名称出错
//		Stopwatch watch = null;
		String obKey = "";
		try {
			obKey = dbName + ":" + spName;
//			SmartCounter counter = DatabasePerfmon.getCounter(obKey);
//			watch = counter.begin();
			
			if (params != null) {
				if (params.length != values.length) {
					throw new IllegalArgumentException("params.length != values.length");
				}
			}
			conn = getConnection();
			String dbType = conn.getMetaData().getDatabaseProductName();
			if (dbType.equalsIgnoreCase("MySQL")) {
				convertAtToT(paramsTemp, params);
			}
			sql = DatabaseHelper.getCallSql(spName, values == null ? 0 : values.length);
			stmt = conn.prepareCall(sql);
			if (dbType.equalsIgnoreCase("MySQL")) {
				DatabaseHelper.fillStatememt(stmt, paramsTemp, values);
			} else {
				DatabaseHelper.fillStatememt(stmt, params, values);
			}
			rs = stmt.executeQuery();
			
			if(rs == null || rs.getMetaData().getColumnCount() == 0)
				return null;
			
			CachedRowSet crs = new CachedRowSetImpl();
			crs.populate(rs);
			
			return crs;
			
		} catch (SQLException e) {
//			watch.fail(e);
			LOGGER.error(obKey + " error: {}", e);
			throw e;
		} finally {
			DatabaseHelper.attemptClose(rs);
			DatabaseHelper.attemptClose(stmt);
			closeConnection(conn);
//			filter(obKey,watch);
		}
	}
	/**
	 * 
	 * @return Connection
	 * @throws SQLException
	 * Database每次获取最新的 Connection,用完close
	 * Transaction 用自身的Connection,用完不close
	 */
	public Connection getConnection() throws SQLException
	{
		return poolAdapter.getConnection();
	}
	
	void closeConnection(Connection conn)
	{
		DatabaseHelper.attemptClose(conn);
	}

	@Override
	public List<DataTable> spExecuteTables(String spName, String[] params,
			Object... values) throws SQLException {
		
		params = params == null ? new String[]{} : params;
		values = values == null ? new String[]{} : values;
		
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		String sql = "";
		String[] paramsTemp = new String[params.length];// 临时存放参数名称，防止先mysql后sqlserver访问时修改了参数名称出错
//		Stopwatch watch = null;
//		String obKey = "";
		try {
//			obKey = dbName + ":" + spName;
//			SmartCounter counter = DatabasePerfmon.getCounter(obKey);
//			watch = counter.begin();
			
			if (params != null) {
				if (params.length != values.length) {
					throw new IllegalArgumentException("params.length != values.length");
				}
			}
			conn = getConnection();
			String dbType = conn.getMetaData().getDatabaseProductName();
			if (dbType.equalsIgnoreCase("MySQL")) {
				convertAtToT(paramsTemp, params);
			}
			sql = DatabaseHelper.getCallSql(spName, values.length);
			stmt = conn.prepareCall(sql);
			if (dbType.equalsIgnoreCase("MySQL")) {
				DatabaseHelper.fillStatememt(stmt, paramsTemp, values);
			} else {
				DatabaseHelper.fillStatememt(stmt, params, values);
			}
			rs = stmt.executeQuery();
			
			List<DataTable> ds = new ArrayList<DataTable>();
			ds.add(new DataTable(rs));
			
			while(stmt.getMoreResults()){
				rs = stmt.getResultSet();
				ds.add(new DataTable(rs));
			}
			return ds;
		} catch (SQLException e) {
//			watch.fail(e);
//			LOGGER.error(obKey + " error: {}", e);
			throw e;
		} finally {
			DatabaseHelper.attemptClose(rs);
			DatabaseHelper.attemptClose(stmt);
			DatabaseHelper.attemptClose(conn);
//			filter(obKey,watch);
		}
	}

	@Override
	public List<DataTable> executeTables(String sql, Object... values) throws SQLException 
	{
		values = values == null ? new String[]{} : values;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
//		Stopwatch watch = null;
		String obKey = "";
		try {
			String formatSql = sql;
			//非参数化的sql
			if(values == null || values.length == 0)		
				formatSql = SqlNormalizer.format(sql);
			obKey = dbName + ":" + formatSql;
			
//			SmartCounter counter = DatabasePerfmon.getCounter(obKey);
//			watch = counter.begin();
			
			conn = getConnection();
			stmt = conn.prepareStatement(sql);

			for (int i = 0; i < values.length; i++) {
				stmt.setObject(i + 1, values[i]);
			}

			rs = stmt.executeQuery();
			List<DataTable> ds = new ArrayList<DataTable>();
			ds.add(new DataTable(rs));
			
			while(stmt.getMoreResults()){
				rs = stmt.getResultSet();
				ds.add(new DataTable(rs));
			}
			
			return ds;
		} catch (SQLException e) {
//			watch.fail(e);
			LOGGER.error(obKey + " error: {}", e);
			throw e;
		} finally {
			DatabaseHelper.attemptClose(rs);
			DatabaseHelper.attemptClose(stmt);
			DatabaseHelper.attemptClose(conn);
//			filter(obKey,watch);
		}
	}
	
	/*private void filter(String obKey,Stopwatch watch)
	{
		if(watch!=null)
		{
			if(watch.getMillseconds() > SLOW_SQL)
				LOGGER.error("{} slow sql ,real cost millseconds is:{} ms",obKey,watch.getMillseconds());
			watch.end();
		}
	}*/
	
	/**
	 * 
	 * 关闭数据库连接池
	 */
	public void close() {
		poolAdapter.close();
	}

}
