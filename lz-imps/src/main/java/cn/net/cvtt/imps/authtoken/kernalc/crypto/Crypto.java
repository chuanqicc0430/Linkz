package cn.net.cvtt.imps.authtoken.kernalc.crypto;

/**
 * 将原 ISSICrypto改名成Crypto，并且移到包com.feinno.imps.crypt中，目的是推动代码复用
 * 修改人：陈春松
 * 
 * ISSICrypto
 * @author honghao@feinno.com
 *
 */
public interface Crypto {
	/**
	 * Decrypt method
	 * @param encryptedBytes
	 * @return the byte array decrypted
	 * @throws Exception 
	 */
	byte[] decrypt(byte[] encryptedBytes) throws Exception;

	/**
	 * Encrypt method
	 * @param plainBytes
	 * @return the byte array encrypted
	 * @throws Exception 
	 */
	byte[] encrypt(byte[] plainBytes) throws Exception;

	/**
	 * set the key of the encrypt/decrypt method
	 * @param value
	 */
	void setKey(byte[] value);
}
