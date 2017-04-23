package cn.net.cvtt.lian.common.util.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import cn.net.cvtt.lian.common.util.NumberUtils;

/**
 * 
 * <b>描述: </b>一个无锁的用于短期事务管理的高效的容器类
 * <p>
 * <b>功能: </b>无锁的用于短期事务管理的高效的容器类
 * <p>
 * <b>用法: </b>参考 {@link com.feinno.rpc.channel.tcp.RpcTcpTransactionManager}
 * 
 * <pre>
 *     SessionPoolCripped txs = new SessionPoolCripped&lt;RpcTcpClientTransaction&gt;(sessionPoolCapacity);
 *     txs.add(tx);
 *     int idx = txs.seq2idx(seq);
 *     RpcTcpClientTransaction tx = txs.valueAtIdx(idx);
 *     txs.removeByIdx(idx, tx)
 * </pre>
 * <p>
 * 
 * @author 
 * @see com.feinno.rpc.channel.tcp.RpcTcpTransactionManager
 * @param <T>
 */
public final class SessionPoolLockFree<T>
{
	private AtomicInteger head;
	private AtomicInteger count;

	private int capacity;
	private int capacityMask;

	private AtomicReference[] sessions;

	public SessionPoolLockFree(int capacity)
	{
		head = new AtomicInteger(0);
		count = new AtomicInteger(0);
		//
		// 为了保证效率，不将_pools分配满，仅保留少于一半的对象作为空槽使用
		// TODO assert capacity <　1024 * 1024;
		
		this.capacity = capacity;
		this.capacityMask = NumberUtils.NextPower2(capacity) * 2;
		sessions = new AtomicReference[capacityMask];
		for (int i = 0; i < capacityMask; i++) {
			sessions[i] = new AtomicReference<T>(null);
		}
		
		capacityMask = capacityMask - 1;
	}

	/**
	 * TODO int overflow
	 * @param value
	 * @return the *sequence number* of this session, NOT the index
	 */
	public int add(T value)
	{
		//
		// 先增加对象计数，避免容器满
		// TODO IICAssert.IsNotNull(value);
		if (count.incrementAndGet() >= capacity) {
			count.decrementAndGet();
			return -1;
		}

		//
		// 在head后寻找第一个空槽，然后插入对象
		int index, seq;
		do {
			seq = head.incrementAndGet();
			index = seq & capacityMask;
		} while (!sessions[index].compareAndSet(null, value));
				
//		return index; 
		return seq;
	}
	
	public int seq2idx(int seq) {
		return seq & capacityMask;
	}
	
	public int getCount()
	{
		return count.get();
	}

	public int getCapacity()
	{
		return capacity;
	}

	public T valueAtIdx(int index)
	{
		AtomicReference<T> r = (AtomicReference<T>)sessions[index];
		return r.get();
	}

/*	private T remove(int index)
	{
		
		AtomicReference<T> r = sessions[index];

		T v = r.get();
		if (v == null) {
			return null;
		}
		
		if (r.compareAndSet(v, null)) {
			count.decrementAndGet();
			return v;
		} else {
			return null;
		}
	}*/
	
	public boolean removeByIdx(int index, T v)
	{
		AtomicReference<T> r = sessions[index];
		if (r.compareAndSet(v, null)) {
			count.decrementAndGet();
			return true;
		} else {
			return false;
		}
	}	

	public Collection<T> getItems()
	{
		ArrayList<T> ret = new ArrayList<T>();
		for (int i = 0; i < capacity; i++) {
			AtomicReference<T> r = (AtomicReference<T>)sessions[i];
			T v = r.get();
			if (v != null)
				ret.add(v);
		}
		return ret;
	}
}
