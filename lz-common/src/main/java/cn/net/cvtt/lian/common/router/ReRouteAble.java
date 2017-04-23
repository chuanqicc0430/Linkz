package cn.net.cvtt.lian.common.router;

import java.util.Set;

public interface ReRouteAble {

	public Set<String> getAllDataKey(String[] keyPatterns) throws Exception;

	public void moveData(String dataKey, ReRouteAble destNode) throws Exception;
	
	public void deleteData(String dataKey) throws Exception;
	
	public String toString();
}
