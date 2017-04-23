package cn.net.cvtt.imps.authtoken.kernalc.crypto;

import java.util.Date;

public class CryptoKey {
	private int cTimeOut;

	private byte[] key;

	private Crypto crypto;

	private Date expireTime;
	
	public int getCTimeOut() {
		return cTimeOut;
	}
	
	public int getcTimeOut() {
		return cTimeOut;
	}

	public void setCTimeOut(int cTimeOut) {
		this.cTimeOut = cTimeOut;
	}

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

	public Crypto getCrypto() {
		return crypto;
	}

	public void setCrypto(Crypto crypto) {
		this.crypto = crypto;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
}
