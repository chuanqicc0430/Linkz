package cn.net.cvtt.imps.authtoken.kernalc;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import cn.net.cvtt.imps.authtoken.ValidateResultEnum;
import cn.net.cvtt.imps.authtoken.kernalc.crypto.AESCrypto;
import cn.net.cvtt.imps.authtoken.kernalc.crypto.CryptoKey;
import cn.net.cvtt.imps.authtoken.kernalc.sigininfo.SignInInfo;
import cn.net.cvtt.imps.authtoken.util.AuthConfigHelper;
import cn.net.cvtt.lian.common.util.DateUtil;
import cn.net.cvtt.lian.common.util.IPAddressV4;

/**
 * AuthCredentialHelper Credential解析辅助类
 * 
 * @author cqzong
 *
 */
public class AuthKernalcHelper {
	private static CryptoKey key;
	private static Object syncLock = new Object();
	private static AuthKernalcHelper instance = null;

	public static AuthKernalcHelper getInstance() {
		if (instance == null) {
			synchronized (syncLock) {
				if (instance == null) {
					instance = new AuthKernalcHelper();
				}
			}
		}
		return instance;
	}

	private AuthKernalcHelper() {
		try {
			loadCryptoKey();
		} catch (Exception e) {
			
		}
	}

	/**
	 * loadCryptoKey
	 * 
	 * @throws Exception
	 */
	private void loadCryptoKey() throws Exception {
		String cryptoKey = AuthConfigHelper.getConfig().getcKey();
		int timeout = AuthConfigHelper.getConfig().getcTimeout();
		CryptoKey c = new CryptoKey();
		c.setCTimeOut(timeout);
		c.setKey(cryptoKey.getBytes("UTF-8"));
		Date expireDate = DateUtil.addDatefield(DateUtil.getUTCNow(), Calendar.SECOND, timeout);
		c.setExpireTime(expireDate);
		AESCrypto to = new AESCrypto();
		System.arraycopy(to.getKey(), 0, c.getKey(), 0, c.getKey().length);
		c.setCrypto(to);
		key = c;
	}

	public ValidateResultEnum credentialValidate(String c) {
		try {
			SignInInfo info = decryptSignInInfo(c);
			if (info == null) {
				return ValidateResultEnum.USER_NOT_FOUND;
			}
			if (info.isExpired()) {
				return ValidateResultEnum.CREDENTIAL_EXPIRED;
			}
		} catch (Exception t) {
			return ValidateResultEnum.PROCESS_ERROR;
		}
		return ValidateResultEnum.OK;
	}

	/**
	 * 根据凭证字符串解出用户信息
	 * 
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public synchronized SignInInfo decryptSignInInfo(String c) throws Exception {
		Kernalc cd = Kernalc.fromBase64String(c);
		return decryptSignInInfo(cd);
	}

	/**
	 * 根据凭证解出用户信息
	 * 
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public synchronized SignInInfo decryptSignInInfo(Kernalc c) throws Exception {
		return decryptSignInInfo(c, false);
	}

	// 兼容.net Utc时间问题
	public synchronized SignInInfo decryptSignInInfo(Kernalc c, boolean isJavaUtc) throws Exception {
		return c.decryptSignInInfo(key.getCrypto(), isJavaUtc);
	}

	/**
	 * 根据SignInInfo生成对应的凭证
	 * 
	 * @param info
	 * @return
	 */
	public synchronized Kernalc encryptInfo(SignInInfo info) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(info.getCreateTime());
		cal.add(Calendar.SECOND, key.getCTimeOut());
		info.setExpireTime(cal.getTime());
		Kernalc c = Kernalc.encryptFromSignInInfo(key.getCrypto(), info);
		return c;
	}

	/**
	 * 根据SignInInfo生成对应的凭证
	 * 
	 * @param info
	 *            所加密的byte数组
	 * @return
	 */
	public synchronized Kernalc encryptInfo(byte[] info) throws Exception {
		Kernalc c = Kernalc.encryptInfo(key.getCrypto(), info, key.getCTimeOut());
		return c;
	}

	public static void main(String[] args) throws Exception {
//		AuthKernalcHelper.intialize();
//		for (int i = 0; i < 10; i++) {
//			SignInInfo ssiInfo = new SignInInfo();
//			ssiInfo.setCreateTime(DateUtil.getUTCNow());
//
//			Calendar c = Calendar.getInstance();
//			c.setTime(DateUtil.getUTCNow());
//			c.add(13, 604800);
//			c.getTime();
//			ssiInfo.setExpireTime(c.getTime());
//			Random ran = new Random();
//			ssiInfo.setNonce(ran.nextInt(10000000));
//			ssiInfo.setUserId(11650);
//			ssiInfo.setMobileNo(15555555555l);
//			ssiInfo.setUserIp(IPAddressV4.convertIpToLong("127.0.0.1"));
//			ssiInfo.setUserStatusFlags(0);
//
//			Kernalc cre = AuthKernalcHelper.getInstance().encryptInfo(ssiInfo);
//			System.out.println(cre.toBase64String());
		
			
			String cre = "UQIAAGZWQcdbKMd1LcTtwALqI6rsy4ehDS9MW16ECXk2Uw1C10f5Dj7TbY4wkOVgBSTae7hlfGAM/GMPs/UZQ9QrbIDAnHJBS4u+AYNyxXgnprAV";
			SignInInfo info = AuthKernalcHelper.getInstance().decryptSignInInfo(cre);
			System.out.println(info.isExpired());
			System.out.println(info.toString());
//			
			
			
//			byte[] b1 = new byte[]{13, 10, 69, 75, 97, 67, 70, 97, 84, 111, 114, 116, 87, 72, 48, 74, 98, 108, 73, 99, 80, 48, 51, 104, 112, 115, 77, 79, 71, 116, 83, 116, 115, 48, 108, 43, 111, 68, 13, 10};
//			System.out.println(new String(b1));
//			byte[] b2 = new byte[]{92, 114, 92, 110, 69, 75, 97, 67, 70, 97, 84, 111, 114, 116, 87, 72, 48, 74, 98, 108, 73, 99, 80, 48, 51, 104, 112, 115, 77, 79, 71, 116, 83, 116, 115, 48, 108, 43, 111, 68, 92, 114, 92, 110};
//			System.out.println(new String(b2));
		}
//	}
}
