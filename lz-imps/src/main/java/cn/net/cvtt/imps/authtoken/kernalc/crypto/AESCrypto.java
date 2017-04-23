package cn.net.cvtt.imps.authtoken.kernalc.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 将原 AESCrypto改名成AESCrypto，并且移到包com.feinno.imps.crypt中，目的是推动代码复用 修改人：陈春松
 * 
 * Rijndael algorithm implement of encrypt methods
 * 
 * @author honghao@feinno.com
 */
public class AESCrypto implements Crypto {

	private byte[] iV = { 85, 60, 12, 116, 99, (byte) 189, (byte) 173, 19, (byte) 138, (byte) 183, (byte) 232, (byte) 248, 82, (byte) 232, (byte) 200, (byte) 242 };

	private byte[] key = { (byte) 175, (byte) 206, (byte) 222, 40, 61, (byte) 149, (byte) 157, (byte) 166, (byte) 164, (byte) 179, 15, 16, (byte) 219, (byte) 221, 96, 10, (byte) 242, (byte) 207, (byte) 137, (byte) 104, 98, (byte) 138, (byte) 246, 89, (byte) 252, (byte) 153, 50, 13, (byte) 173, 75, (byte) 183, 18 };

	public AESCrypto() {
		
	}
	public AESCrypto(byte[] key) {
		if (key == null) {
			throw new NullPointerException("key must not be null!");
		}
		this.key = key;
	}

	public AESCrypto(byte[] key, byte[] iv) {
		if (key == null) {
			throw new NullPointerException("key must not be null!");
		}
		if (iv == null) {
			throw new NullPointerException("iv must not be null!");
		}
		this.key = key;
		this.iV = iv;
	}

	public byte[] decrypt(byte[] encryptedBytes) throws Exception {
		byte[] b = null;
		b = compute(Cipher.DECRYPT_MODE, encryptedBytes);
		return b;
	}

	public byte[] encrypt(byte[] plainBytes) throws Exception {
		byte[] b = null;
		b = compute(Cipher.ENCRYPT_MODE, plainBytes);
		return b;
	}

	private byte[] compute(int mode, byte[] rawBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = null;
		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(iV);
		cipher.init(mode, keySpec, ivSpec);
		byte[] b = null;
		b = cipher.doFinal(rawBytes);
		return b;
	}

	public void setKey(byte[] value) {
		if (value != null) {
			key = value;
		}
	}
	
	public byte[] getKey() {
		return this.key;
	}
}