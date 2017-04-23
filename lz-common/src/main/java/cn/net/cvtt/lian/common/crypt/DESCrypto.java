package cn.net.cvtt.lian.common.crypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 将原 SSIDESCrypto改名成DESCrypto，并且移到包com.feinno.imps.crypt中，目的是推动代码复用
 * 
 * @author 
 *
 */
public class DESCrypto implements Crypto {

	private byte[] iV = { (byte) 227, (byte) 105, 5, 40, (byte) 162, (byte) 158, (byte) 143, (byte) 156 };

	private byte[] key = null;

	public DESCrypto(byte[] key) {

		if (key == null) {
			throw new NullPointerException("key must not be null!");
		}
		this.key = key;

	}

	public byte[] decrypt(byte[] encryptedBytes) {
		return compute(Cipher.DECRYPT_MODE, encryptedBytes);
	}

	public byte[] encrypt(byte[] plainBytes) {
		return compute(Cipher.ENCRYPT_MODE, plainBytes);
	}

	private byte[] compute(int mode, byte[] rawBytes) {

		try {
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			SecretKeySpec keySpec = new SecretKeySpec(key, "DES");
			IvParameterSpec ivSpec = new IvParameterSpec(iV);
			cipher.init(mode, keySpec, ivSpec);
			return cipher.doFinal(rawBytes);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public void setKey(byte[] value) {
		if (value != null) {
			this.key = value;
		}
	}

}
