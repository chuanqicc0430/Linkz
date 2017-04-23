package cn.net.cvtt.lian.common.router;

import java.util.List;

import cn.net.cvtt.lian.common.util.Action;
import cn.net.cvtt.lian.common.util.Combo3;

public interface Router<T extends ReRouteAble> {

	public void initNode(T node, int weight) throws Exception;

	public void addNode(T node, int weight) throws Exception;

	public void removeNode(T node, int weight) throws Exception;

	public T get(String key) throws Exception;

	public List<Combo3<String, T, T>> reHashAsync(boolean isMoveData);

	public void reHashSync(boolean isMoveData, String[] keyPatterns, Action<Combo3<Integer, Integer, String>> progressCallback, Action<List<Combo3<String, T, T>>> finallyCallback);

}
