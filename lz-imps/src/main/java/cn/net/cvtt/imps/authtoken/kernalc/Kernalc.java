/*
 * FAE, Feinno App Engine
 *  
 * Create by honghao 2011-8-4
 * 
 * Copyright (c) 2011 北京新媒传信科技有限公司
 */
package cn.net.cvtt.imps.authtoken.kernalc;

import java.io.IOException;

import cn.net.cvtt.lian.common.crypt.LZBase64;
import cn.net.cvtt.imps.authtoken.kernalc.crypto.Crypto;
import cn.net.cvtt.imps.authtoken.kernalc.sigininfo.SignInInfo;

/**
 * {@link Kernalc}用户凭证，可用作数据传输用户信息加密校验
 * 
 * @author honghao@feinno.com
 */
public class Kernalc {
	// 凭证有效时间，单位秒
	private int expire;
	// 加密的字节数组
	private byte[] encryptedBuffer;

	/**
	 * 凭证有效时间，单位秒
	 * 
	 * @return
	 */
	public int getExpire() {
		return expire;
	}

	/**
	 * 凭证有效时间，单位秒
	 * 
	 * @param expire
	 */
	public void setExpire(int expire) {
		this.expire = expire;
	}

	@Override
	public String toString() {
		try {
			return toBase64String();
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 从一个BASE64字符串反序列化
	 * 
	 * @param s
	 * @return
	 * @throws IOException
	 */
	public synchronized static Kernalc fromBase64String(String s) throws IOException {
		byte[] data = LZBase64.base64Decode(s);
		int encryptedSize = data.length - 4;
		Kernalc c = new Kernalc();
		c.expire = LZBase64.getIntDotNet(data, 0);
		c.encryptedBuffer = new byte[encryptedSize];
		System.arraycopy(data, 4, c.encryptedBuffer, 0, encryptedSize);
		return c;
	}

	/**
	 * 将凭证序列化为一个BASE64字符串
	 * 
	 * @return BASE64字符串
	 * @throws Exception
	 */
	public String toBase64String() throws Exception {
		return toBase64String(false, false);
	}

	/**
	 * 将凭证序列化为一个BASE64字符串
	 * 
	 * @return BASE64字符串
	 * @throws Exception
	 */
	public String toBase64String(boolean lineSep, boolean endWithLineSep) throws Exception {
		if (null == encryptedBuffer) {
			throw new Exception("Encrypted Data Unavailable");
		}
		int size = encryptedBuffer.length;
		byte[] buffer = new byte[size + 4];
		System.arraycopy(LZBase64.intToBytes(expire), 0, buffer, 0, 3);
		System.arraycopy(encryptedBuffer, 0, buffer, 4, size);
		return LZBase64.base64Encode(buffer, lineSep, endWithLineSep);
	}

	/**
	 * 生成凭证
	 * 
	 * @param crypto
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public static Kernalc encryptFromSignInInfo(Crypto crypto, SignInInfo info) throws Exception {

		byte[] infoBuffer = info.toBinary();

		byte[] shaBuffer = null;

		shaBuffer = LZBase64.computeSHA1Hash(infoBuffer);

		byte[] plainBuffer = new byte[infoBuffer.length + shaBuffer.length];

		System.arraycopy(infoBuffer, 0, plainBuffer, 0, infoBuffer.length);

		System.arraycopy(shaBuffer, 0, plainBuffer, infoBuffer.length, shaBuffer.length);

		byte[] encryptedBuffer = crypto.encrypt(plainBuffer);

		Kernalc c = new Kernalc();

		// 把expire存加密数据长度
		// c._expire = encryptedBuffer.Length;
		int mod = (encryptedBuffer.length + 5) % 3;
		long time = info.getExpireTime().getTime() - info.getCreateTime().getTime();
		int exp = (int) (time / 1000);

		c.expire = exp;
		c.expire = (exp & 0x7FFFFFF0) | (mod & 0x0F);
		//
		int paddinglen = 0;
		if (mod == 1) {
			c.encryptedBuffer = encryptedBuffer;
		}
		{
			if (mod == 0) {
				paddinglen = 1;
			} else if (mod == 2) {
				paddinglen = 2;
			}
			byte[] newBuffer = new byte[encryptedBuffer.length + paddinglen];
			System.arraycopy(encryptedBuffer, 0, newBuffer, 0, encryptedBuffer.length);
			c.encryptedBuffer = newBuffer;
		}
		return c;
	}

	public SignInInfo decryptSignInInfo(Crypto crypto, boolean isJavaUtc) throws Exception {
		int rot = expire & 0x0F;
		byte[] realBuffer;
		if (rot == 1) {
			realBuffer = encryptedBuffer;
		} else {
			realBuffer = new byte[encryptedBuffer.length - (rot == 0 ? 1 : 2)];
			System.arraycopy(encryptedBuffer, 0, realBuffer, 0, realBuffer.length);
		}

		byte[] plainBuffer = null;
		plainBuffer = crypto.decrypt(realBuffer);

		if (plainBuffer.length < 20) {
			throw new Exception("Has sum invalid!");
		}

		byte[] shaBuffer = LZBase64.computeSHA1Hash(plainBuffer, 0, plainBuffer.length - 20);
		if (shaBuffer == null) {
			throw new Exception("Has Sum Invalid");
		}
		if (shaBuffer.length != 20) {
			throw new Exception("SHA1 Hased buffer invalid");
		}
		for (int i = 0; i < 20; i++) {
			if (shaBuffer[i] != plainBuffer[plainBuffer.length - 20 + i]) {
				throw new Exception("Has Sum Invalid");
			}
		}
		SignInInfo info = SignInInfo.fromBinary(plainBuffer, isJavaUtc);
		return info;
	}

	/**
	 * 解密凭证生成用户信息
	 * 
	 * @param crypto
	 * @return
	 * @throws Exception
	 */
	public SignInInfo decryptSignInInfo(Crypto crypto) throws Exception {
		return decryptSignInInfo(crypto, false);
	}

	/**
	 * byte数组凭证加密
	 * 
	 * @param crypto
	 * @param info
	 * @param expire
	 * @return
	 * @throws Exception
	 */
	public synchronized static Kernalc encryptInfo(Crypto crypto, byte[] info, int expire) throws Exception {
		byte[] infoBuffer = info;
		byte[] encryptedBuffer = crypto.encrypt(infoBuffer);
		Kernalc c = new Kernalc();
		c.expire = expire;
		c.encryptedBuffer = encryptedBuffer;
		return c;
	}

	/**
	 * byte数组凭证解密
	 * 
	 * @param crypto
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptInfo(Crypto crypto) throws Exception {
		byte[] plainBuffer = crypto.decrypt(encryptedBuffer);
		return plainBuffer;
	}
}
