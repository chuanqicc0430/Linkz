package cn.net.cvtt.lian.common.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.CRC32;

/**
 * CommonHelper : 工具类
 *
 */
public class CommonHelper {
	public static int getCodeCRC32(String codeStr) throws UnsupportedEncodingException {
		CRC32 crc32 = new CRC32();
		crc32.update(codeStr.getBytes("UTF-8"));
		return (int) crc32.getValue();
	}

	public static boolean isSameMonth(Date d) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");

		if (d == null)
			return true;
		else {
			return format.format(new Date()).equalsIgnoreCase(format.format(d));
		}
	}

	public static boolean isSameDay(Date d) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		if (d == null)
			return true;
		else {
			return format.format(new Date()).equalsIgnoreCase(format.format(d));
		}
	}

	public static String byte2HexStr(byte[] buffer) {
		int len = buffer.length;
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < len && i < len; i++) {
			byte b = buffer[i];
			if ((b & 0xf0) != 0) {
				s.append(String.format("%X", b));
			} else {
				s.append(String.format("0%X", b));
			}
		}
		return s.toString();
	}

	public static byte[] longToByte(long longValue) {
		long temp = longValue;
		byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Long(temp & 0xff).byteValue();
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}

	public static long byteToLong(byte[] bytes) {
		long s = 0;
		long s0 = bytes[0] & 0xff;// 最低位
		long s1 = bytes[1] & 0xff;
		long s2 = bytes[2] & 0xff;
		long s3 = bytes[3] & 0xff;
		long s4 = bytes[4] & 0xff;// 最低位
		long s5 = bytes[5] & 0xff;
		long s6 = bytes[6] & 0xff;
		long s7 = bytes[7] & 0xff;

		// s0不变
		s1 <<= 8;
		s2 <<= 16;
		s3 <<= 24;
		s4 <<= 8 * 4;
		s5 <<= 8 * 5;
		s6 <<= 8 * 6;
		s7 <<= 8 * 7;
		s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
		return s;
	}

	public static byte[] intToByte(int intVlaue) {
		int temp = intVlaue;
		byte[] b = new byte[4];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}

	/*
	 * "0" 返回 false "1" 返回 true 非以上字符串返回 false
	 */
	public static boolean numberStr2Bool(String strNum) {
		try {
			int num = Integer.parseInt(strNum);
			if (num == 1)
				return true;
			else
				return false;
		} catch (Exception ex) {
			return false;
		}
	}

	public static int getExponent(int mask) {
		int digit = 1;
		int exponent = 0;
		while (digit < mask) {
			digit = 2 * digit;
			exponent++;
		}
		if (digit == mask)
			return exponent;
		else
			return 0; // 不是2的指数值
	}

	/**
	 * 集合拆分，拆成小List
	 * 
	 * @param resList
	 * @param count
	 * @return
	 */
	// public static <T> List<List<T>> split(List<T> resList, int count) {
	// if (resList == null || count < 1)
	// return null;
	// List<List<T>> ret = new ArrayList<List<T>>();
	// int size = resList.size();
	// if (size <= count) { // 数据量不足count指定的大小
	// ret.add(resList);
	// } else {
	// int pre = size / count;
	// int last = size % count;
	// // 前面pre个集合，每个大小都是count个元素
	// for (int i = 0; i < pre; i++) {
	// List<T> itemList = new ArrayList<T>();
	// for (int j = 0; j < count; j++) {
	// itemList.add(resList.get(i * count + j));
	// }
	// ret.add(itemList);
	// }
	// // last的进行处理
	// if (last > 0) {
	// List<T> itemList = new ArrayList<T>();
	// for (int i = 0; i < last; i++) {
	// itemList.add(resList.get(pre * count + i));
	// }
	// ret.add(itemList);
	// }
	// }
	// return ret;
	// }

	/**
	 * 集合拆分，拆成小List
	 * 
	 * @param resList
	 * @param count
	 * @return
	 */
	public static List<String[]> splitListToListArray(List<String> resList, int count) {
		if (resList == null || count < 1)
			return null;
		List<String[]> ret = new ArrayList<>();
		int size = resList.size();
		if (size <= count) { // 数据量不足count指定的大小
			String[] t = new String[size];
			ret.add(resList.toArray(t));
		} else {
			int pre = size / count;
			int last = size % count;
			// 前面pre个集合，每个大小都是count个元素
			for (int i = 0; i < pre; i++) {
				String[] t = new String[count];
				for (int j = 0; j < count; j++) {
					t[j] = resList.get(i * count + j);
				}
				ret.add(t);
			}
			// last的进行处理
			if (last > 0) {
				String[] t = new String[last];
				for (int i = 0; i < last; i++) {
					t[i] = resList.get(pre * count + i);
				}
				ret.add(t);
			}
		}
		return ret;
	}

	public static void main(String[] args) {
		List<String> strList = new ArrayList<>();
		for (int i = 0; i < 4565; i++) {
			strList.add(String.valueOf(i));
		}
		List<String[]> splitList = CommonHelper.splitListToListArray(strList, 20);
		for (String[] s : splitList) {
			System.out.println(Arrays.asList(s));
		}

	}

}
