package cn.net.cvtt.imps.authtoken.kernalc.sigininfo;

import java.util.Calendar;
import java.util.GregorianCalendar;

import cn.net.cvtt.lian.common.crypt.Base641;

/**
 * SignInInfoV4Serializer凭证序列化类
 * 
 * @author cqzong
 *
 */
public class SignInInfoSerializer {
	private static long OFFSETTICKS = 0;

	static {
		Calendar cal = GregorianCalendar.getInstance();
		// 2、取得时间偏移量：
		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
		// 3、取得夏令时差：
		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
		OFFSETTICKS = (zoneOffset + dstOffset) * 10000L;
	}

	private SignInInfoSerializer() {

	}

	/**
	 * Serialize the SignInInfo object to byte array
	 * 
	 * @param info
	 * @return byte array
	 * @throws Exception
	 */
	public static byte[] serialize(SignInInfo info) throws Exception {
		long ticks = 0;
		ticks = Base641.getJavaMinDateDotNetTicks();

		int size = 48;

		byte[] buffer = new byte[size];

		int index = 0;
		Base641.putIntDotNet(buffer, info.getNonce(), index);// Nonce 4
		index += 4;
		Base641.putLongDotNet(buffer, info.getCreateTime().getTime() * 10000 + ticks + OFFSETTICKS, index); // CreateTime +8小时 与.net互通兼容
		// 12
		index += 8;
		Base641.putLongDotNet(buffer, info.getExpireTime().getTime() * 10000 + ticks + OFFSETTICKS, index);// ExpireTime
		// 20
		index += 8;
		Base641.putIntDotNet(buffer, info.getUserStatusFlags(), index); // UserStatusV2
		// 31
		index += 4;
		Base641.putLongDotNet(buffer, info.getUserIp(), index);// UserIp
		// 39
		index += 8;
		Base641.putLongDotNet(buffer, info.getUserId(), index); // UserId
		// 43
		index += 8;
		Base641.putLongDotNet(buffer, info.getMobileNo(), index); // UserId
		return buffer;
	}

	/**
	 * DeSerialize the SignInInfo from the byte array
	 * 
	 * @param bytes
	 * @return SignInInfo object
	 * @throws Exception
	 */
	public static SignInInfo deSerialize(byte[] bytes) throws Exception {
		return deSerialize(bytes, 0);
	}

	public static SignInInfo deSerialize(byte[] buffer, int offset, boolean isJavaUtc) throws Exception {
		SignInInfo info = new SignInInfo();
		info.setNonce(Base641.getIntDotNet(buffer, offset));
		offset += 4;
		if (!isJavaUtc)
			info.setCreateTime(Base641.dotNetTicks2JavaDate((Base641.getLongDotNet(buffer, offset) - OFFSETTICKS))); // -8小时与.net互通
		else
			info.setCreateTime(Base641.dotNetTicks2JavaDate(Base641.getLongDotNet(buffer, offset)));

		offset += 8;
		if (!isJavaUtc)
			info.setExpireTime(Base641.dotNetTicks2JavaDate(Base641.getLongDotNet(buffer, offset) - OFFSETTICKS)); // -8小时与.net互通
		else
			info.setExpireTime(Base641.dotNetTicks2JavaDate(Base641.getLongDotNet(buffer, offset)));

		offset += 8;
		info.setUserStatusFlags(Base641.getIntDotNet(buffer, offset));
		offset += 4;
		info.setUserIp(Base641.getLongDotNet(buffer, offset));
		offset += 8;
		info.setUserId(Base641.getLongDotNet(buffer, offset));
		offset += 8;
		info.setMobileNo(Base641.getLongDotNet(buffer, offset));

		return info;
	}

	/**
	 * DeSerialize the SignInInfo from the byte array using specified offset
	 * 
	 * @param bytes
	 * @param offset
	 * @return SignInInfo object
	 * @throws Exception
	 */
	public static SignInInfo deSerialize(byte[] buffer, int offset) throws Exception {
		return deSerialize(buffer, offset, false);
	}
}
