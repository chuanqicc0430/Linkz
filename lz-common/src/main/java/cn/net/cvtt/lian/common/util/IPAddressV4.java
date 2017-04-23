package cn.net.cvtt.lian.common.util;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ipv4地址封装类
 * 回滚，这个类严禁序列化
 */
public class IPAddressV4 implements Comparable<Object> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(IPAddressV4.class);

	private long ipV4 = 0;
	private String ipStr = "";
	private Inet4Address ipJava;

	protected IPAddressV4(Inet4Address addr) {
		ipStr = addr.toString().substring(1);
		ipV4 = convertIpToLong(ipStr);
		ipJava = addr;
	}

	protected IPAddressV4(long addr) {
		ipV4 = addr;
		ipStr = convertLongToIp(addr);
		try {
			ipJava = (Inet4Address) Inet4Address.getByName(ipStr);
		} catch (UnknownHostException e) {
			LOGGER.error("bad ip address" + addr, e);
			throw new IllegalArgumentException("bad ip address" + addr, e);
		}
	}

	public IPAddressV4() {
	}

	public static IPAddressV4 parse(String addr) {
		IPAddressV4 address = null;
		try {
			address = new IPAddressV4((Inet4Address) Inet4Address
					.getByName(addr));
		} catch (UnknownHostException e) {
			LOGGER.error("unknown host: " + addr, e);
			throw new IllegalArgumentException("unknown host: " + addr, e);
		}
		return address;
	}

	public static IPAddressV4 parse(Inet4Address addr) {
		return new IPAddressV4(addr);
	}

	public static IPAddressV4 parse(long addr) {
		return new IPAddressV4(addr);
	}

	public IPAddressV4 getServerIP() {
		short values[] = {  (short)((ipV4 >> 24) & 0xff), (short)((ipV4 >> 16) & 0xff),
				(short)((ipV4 >> 8) & 0xff),(short)(ipV4 & 0xff) };
		
		long newValue = 0;
		
		for (int i = values.length-1; i > -1; i--) {
			newValue = newValue * 256 + values[i];

		}
		return IPAddressV4.parse(newValue);
		
	}
	
	
	

	public static long convertIpToLong(String ip) {
		long ipValue = 0;
		String[] ips = ip.split("\\.");
		for (int i = ips.length-1; i > -1; i--) {
			ipValue = ipValue * 256 + Short.parseShort(ips[i]);

		}
		return ipValue;

	}

	public static String convertLongToIp(long addr) {
		return String.format("%s.%s.%s.%s", addr & 0xff, (addr >> 8) & 0xff,
				(addr >> 16) & 0xff,(addr >> 24) & 0xff);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof IPAddressV4))
			return false;
		IPAddressV4 target = (IPAddressV4) obj;
		return ipV4 == target.ipV4;
	}

	public int hashCode() {
		return Long.valueOf(ipV4).hashCode();
	}

	public String toString() {
		return ipStr;
	}

	public int compareTo(Object obj) {
		if (obj instanceof IPAddressV4) {
			IPAddressV4 ip = (IPAddressV4) obj;
			return Long.valueOf(ipV4).compareTo(Long.valueOf(ip.ipV4));
		}
		return 0;
	}

	public long getIpV4() {
		return ipV4;
	}

	public Inet4Address getJavaIp() {
		return ipJava;
	}
	
	public static void main(String[] args) {
		System.out.println(IPAddressV4.parse("127.0.0.1").getIpV4());
	}
}
