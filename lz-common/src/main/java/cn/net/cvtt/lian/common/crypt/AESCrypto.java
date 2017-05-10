package cn.net.cvtt.lian.common.crypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * Rijndael algorithm implement of encrypt methods
 * 
 * @author 
 */
public class AESCrypto implements Crypto {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AESCrypto.class);

	private byte[] iV = { 85, 60, 12, 116, 99, (byte) 189, (byte) 173, 19,
			(byte) 138, (byte) 183, (byte) 232, (byte) 248, 82, (byte) 232,
			(byte) 200, (byte) 242 };

	private byte[] key = { (byte) 175, (byte) 206, (byte) 222, 40, 61,
			(byte) 149, (byte) 157, (byte) 166, (byte) 164, (byte) 179, 15, 16,
			(byte) 219, (byte) 221, 96, 10, (byte) 242, (byte) 207, (byte) 137,
			(byte) 104, 98, (byte) 138, (byte) 246, 89, (byte) 252, (byte) 153,
			50, 13, (byte) 173, 75, (byte) 183, 18 };

	public AESCrypto(byte[] key) {
		if (key == null) {
			throw new NullPointerException("key must not be null!");
		}
		this.key = key;
	}
	public AESCrypto(byte[] key,byte[] iv) {
		if (key == null) {
			throw new NullPointerException("key must not be null!");
		}
		if(iv == null) {
			throw new NullPointerException("iv must not be null!");
		}
		this.key = key;
		this.iV = iv;
	}

	public byte[] decrypt(byte[] encryptedBytes) {
		byte[] b=null;
		try {
			b = compute(Cipher.DECRYPT_MODE, encryptedBytes);
		} catch (InvalidKeyException e) {
			LOGGER.error("SSIAESCrypto decrypt error: {}", e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("SSIAESCrypto decrypt error: {}", e);
		} catch (NoSuchPaddingException e) {
			LOGGER.error("SSIAESCrypto decrypt error: {}", e);
		} catch (InvalidAlgorithmParameterException e) {
			LOGGER.error("SSIAESCrypto decrypt error: {}", e);
		} catch (IllegalBlockSizeException e) {
			LOGGER.error("SSIAESCrypto decrypt error: {}", e);
		} catch (BadPaddingException e) {
			LOGGER.error("SSIAESCrypto decrypt error: {}", e);
		}
		return b;
	}

	public byte[] encrypt(byte[] plainBytes) {
		byte[] b=null;
		try {
			b = compute(Cipher.ENCRYPT_MODE, plainBytes);
		} catch (InvalidKeyException e) {
			LOGGER.error("SSIAESCrypto encrypt error: {}", e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("SSIAESCrypto encrypt error: {}", e);
		} catch (NoSuchPaddingException e) {
			LOGGER.error("SSIAESCrypto encrypt error: {}", e);
		} catch (InvalidAlgorithmParameterException e) {
			LOGGER.error("SSIAESCrypto encrypt error: {}", e);
		} catch (IllegalBlockSizeException e) {
			LOGGER.error("SSIAESCrypto encrypt error: {}", e);
		} catch (BadPaddingException e) {
			LOGGER.error("SSIAESCrypto encrypt error: {}", e);
		}
		return b;
	}

	private byte[] compute(int mode, byte[] rawBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
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
}