package cn.net.cvtt.lian.common.util;

import java.util.ArrayList;
import java.util.Collection;


public class LinqHelper {
	public static <E> boolean exist(Collection<E> coll,LamdaAction<E> action){
		for(E e : coll){
			if(action.run(e))
				return true;
		}
		return false;
	}
	
	public static <E, R> R exist(Collection<E> coll,LamdaAction2<E,R> action){
		for(E e : coll){
			R r = action.run(e);
			if(r != null)
				return r;
		}
		return null;
	}
	
	public static <E> Collection<E> where(Collection<E> coll,LamdaAction<E> action){
		Collection<E> res = new ArrayList<E>();
		for(E e : coll){
			if(action.run(e))
				res.add(e);
		}
		return res;
	}
	
	public static <E,R> Collection<R> select(Collection<E> coll,LamdaAction2<E,R> action){
		Collection<R> res = new ArrayList<R>();
		for(E e : coll){
			R r = action.run(e);
			if(r != null)
				res.add(r);
		}
		return res;
	}
}
