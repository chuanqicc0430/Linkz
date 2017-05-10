package cn.net.cvtt.lian.common.crypt;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import cn.net.cvtt.lian.common.util.StringUtils;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author 
 *
 */
public class LZBase64 {
	private static final int SIZE = 10;

	private static final ArrayBlockingQueue<BASE64Encoder> base64Encoders = new ArrayBlockingQueue<BASE64Encoder>(SIZE);

	static {
		for (int index = 0; index < SIZE; index++) {
			base64Encoders.add(new BASE64Encoder());
		}
	}

	private static long javaMinDateDotNetTicks;
	private static final int[] daysToMonth366 = { 0, 0x1f, 60, 0x5b, 0x79, 0x98, 0xb6, 0xd5, 0xf4, 0x112, 0x131, 0x14f, 0x16e };
	private static int[] daysToMonth365 = { 0, 0x1f, 0x3b, 90, 120, 0x97, 0xb5, 0xd4, 0xf3, 0x111, 0x130, 0x14e, 0x16d };

	private LZBase64() {
	}

	/**
	 * Convert 1970-1-1 in java to Microsoft(C) .Net DateTime.Ticks
	 * 
	 * @return ticks value
	 * @throws Exception
	 */
	public static long getJavaMinDateDotNetTicks() throws Exception {
		if (javaMinDateDotNetTicks == 0L) {
			javaMinDateDotNetTicks = date2DotNetTicks(1970, 1, 1) & 0x3fffffffffffffffL;
		}
		return javaMinDateDotNetTicks;
	}

	/**
	 * Convert specified year/month/day to Microsoft(C) .Net DateTime.Ticks
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return DateTime.Ticks value in Microsoft(C) .Net
	 * @throws Exception
	 */
	public static long date2DotNetTicks(int year, int month, int day) throws Exception {
		if (((year >= 1) && (year <= 0x270f)) && ((month >= 1) && (month <= 12))) {
			int[] numArray = isLeapYear(year) ? daysToMonth366 : daysToMonth365;
			if ((day >= 1) && (day <= (numArray[month] - numArray[month - 1]))) {
				int num = year - 1;
				int num2 = ((((((num * 0x16d) + (num / 4)) - (num / 100)) + (num / 400)) + numArray[month - 1]) + day) - 1;
				return (num2 * 0xc92a69c000L);
			}
		}
		throw new Exception("Paramenter Invalid!");
	}

	/**
	 * Determine the year is Leap year or not.
	 * 
	 * @param year
	 * @return The year is leap year or not
	 * @throws Exception
	 */
	public static boolean isLeapYear(int year) throws Exception {
		if ((year < 1) || (year > 0x270f)) {
			throw new Exception("year out of range");
		}
		if ((year % 4) != 0) {
			return false;
		}
		if ((year % 100) == 0) {
			return ((year % 400) == 0);
		}
		return true;
	}

	/**
	 * Convert UTC Time in Microsoft(C) .Net to java local time. Microsoft(C) .Net DateTime.Ticks 此属性的值表示自0001 年 1 月 1 日午夜 12:00:00 以来已经过的时间的以 100 毫微秒为间隔的间隔数。 SUN(C) Java Date(long date) Allocates a Date object and initializes it to represent the specified number of milliseconds since the standard base time known as "the epoch", namely January 1, 1970, 00:00:00 GMT.
	 * 
	 * @param ticks
	 *            DateTime.Ticks value in dot net
	 * @return UTC date value
	 * @throws Exception
	 */
	public static Date dotNetTicks2JavaDate(long ticks) throws Exception {
		long rawTicks = ticks - getJavaMinDateDotNetTicks();
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.setTimeInMillis((rawTicks / 10L) / 1000L);
		return calendar.getTime();
	}

	public static String getStringByte(byte[] b, int index, int len) {
		byte[] b2 = new byte[len];
		System.arraycopy(b, index, b2, 0, len);
		return new String(b2);
	}

	/**
	 * Convert byte to Integer
	 * 
	 * @param b
	 * @return Integer value
	 */
	public static int oneByte2Int(byte b) {
		return b & 0xff;
	}

	/**
	 * Convert specified byte in a byte array to Integer
	 * 
	 * @param b
	 * @param index
	 * @return Integer value
	 */
	public static int oneByte2Int(byte[] b, int index) {
		return b[index] & 0xff;
	}

	/**
	 * Convert Short Object to 2 bytes in the specified byte array
	 * 
	 * @param b
	 *            byte array to save the value
	 * @param s
	 *            Short object
	 * @param index
	 *            The index of the byte array to store the Short value
	 */
	public static void putShort(byte b[], short s, int index) {
		b[index] = (byte) (s >> 8);
		b[index + 1] = (byte) (s >> 0);
	}

	public static void putShortDotNet(byte b[], short s, int index) {
		b[index] = (byte) (s >> 0);
		b[index + 1] = (byte) (s >> 8);
	}

	/**
	 * Convert specified 2 bytes in byte array to Short value
	 * 
	 * @param b
	 *            byte array contains the Short value
	 * @param index
	 * @return Short value
	 */
	public static short getShort(byte[] b, int index) {
		return (short) (((b[index] << 8) | b[index + 1] & 0xff));
	}

	/**
	 * Convert specified 2 bytes in Microsoft(C) .Net byte array to Java Short value
	 * 
	 * @param b
	 * @param index
	 * @return Short value
	 */
	public static short getShortDotNet(byte[] b, int index) {
		return (short) (((b[index + 1] << 8) | b[index] & 0xff));
	}

	public static void putInt(byte[] bb, int x, int index) {
		bb[index + 0] = (byte) (x >> 24);
		bb[index + 1] = (byte) (x >> 16);
		bb[index + 2] = (byte) (x >> 8);
		bb[index + 3] = (byte) (x >> 0);
	}

	public static void putIntDotNet(byte[] bb, int x, int index) {
		bb[index + 0] = (byte) (x >> 0);
		bb[index + 1] = (byte) (x >> 8);
		bb[index + 2] = (byte) (x >> 16);
		bb[index + 3] = (byte) (x >> 24);
	}

	/**
	 * Convert specified 4 bytes in byte array to Integer
	 * 
	 * @param bb
	 *            byte array
	 * @param index
	 * @return Integer value
	 */
	public static int getInt(byte[] bb, int index) {
		return (int) ((((bb[index + 0] & 0xff) << 24) | ((bb[index + 1] & 0xff) << 16) | ((bb[index + 2] & 0xff) << 8) | ((bb[index + 3] & 0xff) << 0)));
	}

	/**
	 * Convert specified 4 bytes in Microsoft(C) .Net byte array to Java Integer
	 * 
	 * @param bb
	 *            Microsoft(C) .Net byte array
	 * @param index
	 * @return Integer value
	 */
	public static int getIntDotNet(byte[] bb, int index) {
		return (int) ((((bb[index + 3] & 0xff) << 24) | ((bb[index + 2] & 0xff) << 16) | ((bb[index + 1] & 0xff) << 8) | ((bb[index + 0] & 0xff) << 0)));
	}

	public static void putLong(byte[] bb, long x, int index) {
		bb[index + 0] = (byte) (x >> 56);
		bb[index + 1] = (byte) (x >> 48);
		bb[index + 2] = (byte) (x >> 40);
		bb[index + 3] = (byte) (x >> 32);
		bb[index + 4] = (byte) (x >> 24);
		bb[index + 5] = (byte) (x >> 16);
		bb[index + 6] = (byte) (x >> 8);
		bb[index + 7] = (byte) (x >> 0);
	}

	public static void putLongDotNet(byte[] bb, long x, int index) {
		bb[index + 0] = (byte) (x >> 0);
		bb[index + 1] = (byte) (x >> 8);
		bb[index + 2] = (byte) (x >> 16);
		bb[index + 3] = (byte) (x >> 24);
		bb[index + 4] = (byte) (x >> 32);
		bb[index + 5] = (byte) (x >> 40);
		bb[index + 6] = (byte) (x >> 48);
		bb[index + 7] = (byte) (x >> 56);
	}

	/**
	 * Convert specified 8 bytes in byte array to Long
	 * 
	 * @param bb
	 *            byte array
	 * @param index
	 * @return Long value
	 */
	public static long getLong(byte[] bb, int index) {
		return ((((long) bb[index + 0] & 0xff) << 56) | (((long) bb[index + 1] & 0xff) << 48) | (((long) bb[index + 2] & 0xff) << 40) | (((long) bb[index + 3] & 0xff) << 32) | (((long) bb[index + 4] & 0xff) << 24) | (((long) bb[index + 5] & 0xff) << 16) | (((long) bb[index + 6] & 0xff) << 8) | (((long) bb[index + 7] & 0xff) << 0));
	}

	/**
	 * Convert specified 8 bytes in Microsoft(C) .Net byte array to Java Long
	 * 
	 * @param bb
	 *            the Microsoft(C) .Net bytes array contains the value
	 * @param index
	 *            the start index
	 * @return Long value
	 */
	public static long getLongDotNet(byte[] bb, int index) {
		return ((((long) bb[index + 7] & 0xff) << 56) | (((long) bb[index + 6] & 0xff) << 48) | (((long) bb[index + 5] & 0xff) << 40) | (((long) bb[index + 4] & 0xff) << 32) | (((long) bb[index + 3] & 0xff) << 24) | (((long) bb[index + 2] & 0xff) << 16) | (((long) bb[index + 1] & 0xff) << 8) | (((long) bb[index + 0] & 0xff) << 0));
	}

	/**
	 * Decode the input byte array to base64 String Use a object pool to guarantee the thread safe
	 * 
	 * 
	 * 每隔76个字符加一个换行符，在结尾再加一个换行符 换行符根据操作系统不同而变化。
	 * 
	 * @param buffer
	 * @return encoded base64 string;
	 */
	public static String base64Encode(byte[] buffer) {
		return base64Encode(buffer, true, true);
	}

	/**
	 * Decode the input byte array to base64 String Use a object pool to guarantee the thread safe
	 * 
	 * 在结尾不加换行符 换行符采用\r\n
	 * 
	 * @param buffer
	 * @param lineSep
	 *            a flag indicates whether insert a line separator after 76 chars
	 * @return original String object
	 */
	public static String base64Encode(byte[] buffer, boolean lineSep) {
		return base64Encode(buffer, lineSep, false);
	}

	/**
	 * Decode the input byte array to base64 String Use a object pool to guarantee the thread safe 当lineSep和endWithLineSep为true时，换行符随着操作系统而变化；在其他情况下，换行符为\r\n
	 * 
	 * @param buffer
	 * @param lineSep
	 *            a flag indicates whether insert a line separator after 76 chars, if lineSep is false, then endWithLineSep will be looked as false.
	 * @param endWithLineSep
	 *            a flag indicates whether insert a line separator at the end.
	 * @return original String object
	 */
	public static String base64Encode(byte[] buffer, boolean lineSep, boolean endWithLineSep) {
		if (buffer == null || buffer.length == 0) {
			return StringUtils.EMPTY;
		}
		if (!lineSep) {
			endWithLineSep = false;
		}

		if (lineSep == true) {
			if (endWithLineSep) {
				// 每76个字符加换行符，最后已换行符结尾。
				try {
					BASE64Encoder encoder = base64Encoders.poll(30, TimeUnit.SECONDS);
					if (encoder == null) {
						throw new RuntimeException("No available Base64Encoder!");
					}
					try {
						return encoder.encodeBuffer(buffer);
					} finally {
						base64Encoders.add(encoder);
					}
				} catch (RuntimeException ex) {
					throw ex;
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			} else {
				// 每76个字符加换行符，不以换行符结尾。
				return Base64.encodeToString(buffer, lineSep);
			}
		} else {
			// 不加换行符
			return Base64.encodeToString(buffer, false);
		}
	}

	/**
	 * Encoding input String to base64 encoded String thread safe
	 * 
	 * illegal chars at the begin and end will be trimmed.
	 * 
	 * @param str
	 * @return original byte[] object
	 * @throws IOException
	 */
	public static byte[] base64Decode(String str) throws IOException {
		return Base64.decode(str);
	}

	/**
	 * Compute hash value use SHA1 algorithm
	 * 
	 * @param buffer
	 *            the byte[] to compute
	 * @return hash value(20 bytes)
	 */
	public static byte[] computeSHA1Hash(byte[] buffer) {
		return computeSHA1Hash(buffer, 0, buffer.length);
	}

	/**
	 * Compute hash value use SHA1 algorithm using specified offset and length
	 * 
	 * @param buffer
	 *            the buffer to be computed
	 * @param offset
	 *            the start index
	 * @param length
	 *            the length of the bytes to be computed
	 * @return hash value(20 bytes)
	 */
	public static byte[] computeSHA1Hash(byte[] buffer, int offset, int length) {

		if (offset > buffer.length) {
			return null;
		}

		if ((offset + length) > buffer.length) {
			return null;
		}

		byte[] b = null;
		MessageDigest sha1 = null;
		try {
			sha1 = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (offset > 0 || length < buffer.length) {
			byte[] rawBuffer = new byte[length];
			System.arraycopy(buffer, offset, rawBuffer, 0, length);
			sha1.update(rawBuffer);
		} else {

			sha1.update(buffer);
		}
		b = sha1.digest();
		return b;
	}

	/**
	 * 整数转byte[]
	 * 
	 * @param buf
	 * @return
	 */
	public static byte[] intToBytes(int res) {
		byte[] targets = new byte[4];
		// 最低位
		targets[0] = (byte) (res & 0xff);
		// 次低位
		targets[1] = (byte) ((res >> 8) & 0xff);
		// 次高位
		targets[2] = (byte) ((res >> 16) & 0xff);
		// 最高位,无符号右移
		targets[3] = (byte) (res >>> 24);
		return targets;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static long ipAddress2Int64(String address) {
		long res;
		String[] elements = address.split("\\.");
		res = Long.parseLong((elements[0])) << 24;
		res += Long.parseLong((elements[1])) << 16;
		res += Long.parseLong((elements[2])) << 8;
		res += Long.parseLong((elements[3]));
		return res;
	}

	public static String int642IPAddress(long ip) {
		String[] elements = new String[4];
		elements[0] = String.valueOf((ip / 16777216) % 256);
		elements[1] = String.valueOf((ip / 65536) % 256);
		elements[2] = String.valueOf((ip / 256) % 256);
		elements[3] = String.valueOf((ip % 256));
		MessageFormat mf = new MessageFormat("{0}.{1}.{2}.{3}");
		return mf.format(elements);
	}
}