package cn.net.cvtt.lian.common.crypt;

/**
 * 
 * ISSICrypto
 * @author 
 *
 */
public interface Crypto {
	/**
	 * Decrypt method
	 * @param encryptedBytes
	 * @return the byte array decrypted
	 */
	byte[] decrypt(byte[] encryptedBytes);

	/**
	 * Encrypt method
	 * @param plainBytes
	 * @return the byte array encrypted
	 */
	byte[] encrypt(byte[] plainBytes);

	/**
	 * set the key of the encrypt/decrypt method
	 * @param value
	 */
	void setKey(byte[] value);
}
