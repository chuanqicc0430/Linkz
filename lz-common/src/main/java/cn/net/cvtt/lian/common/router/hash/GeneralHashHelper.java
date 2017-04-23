package cn.net.cvtt.lian.common.router.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @ClassName: GeneralHashHelper
 *
 * @Description: 直接使用JAVA的.hashCode()
 *
 * @author 
 *
 *
 */
public class GeneralHashHelper implements HashHelper {

	private static MessageDigest messageDigest = null;

	public GeneralHashHelper() {
		super();
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ignore) {
		}
	}

	@Override
	public int hash(String keyValue) throws Exception {
		synchronized (messageDigest) {
			try {
				byte[] utf8Bytes = keyValue.getBytes("UTF-8");
				messageDigest.update(utf8Bytes);
				byte[] resultByteArray = messageDigest.digest();
				String md5Result = byteToStr(resultByteArray);
				int hashResult = md5Result.hashCode();
				return hashResult;
			} catch (Exception e) {
				throw new Exception("hash error", e);
			}
		}
	}

	private String byteToStr(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(b);
		}
		return sb.toString();
	}

}
