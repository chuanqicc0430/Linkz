package cn.net.cvtt.resource.database.pool;

/**
 * <b>描述：</b>数据库连接池的类型<br/>
 * <p>
 * <b>功能：</b> C3p0: c3p0连接池 <br/>
 * TomcatPool: tomcat连接池 <br/>
 * 
 * @author 
 * 
 */
public enum DBConnectionPoolType
{
	C3p0,
	TomcatPool,
	BoneCP,
	Druid
}
