package cn.net.cvtt.textfilter.smartTextFilter.normalizer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.configuration.ConfigurationException;
import cn.net.cvtt.lian.common.util.Guid;
import cn.net.cvtt.lian.common.util.StringUtils;
import cn.net.cvtt.textfilter.LoadTextFilterProperties;

public class ContentNormalizer {

	private final static Logger logger = LoggerFactory.getLogger(ContentNormalizer.class);
	private static HashMap<Integer, int[]> confusableCharMapping = new HashMap<Integer, int[]>();
	private static boolean normalizeConfusableCharacter = false;

	private Reader content;

	private char[] cChar;

	private char[] cCache;
	private int lCache;
	private int iCache;

	private char[] cTemp;

	private MessageDigest hasha;
	private byte[] hbuff;
	private int hbptr;

	private Guid digest;

	public Guid getDigest() {
		return digest;
	}

	public static void initialize() {

		try {
			normalizeConfusableCharacter = (LoadTextFilterProperties.getInstance().getPropValue("NormalizeConfusableCharacter").compareToIgnoreCase("enable") == 0);
		} catch (ConfigurationException e2) {
			logger.error("load eroor ", e2);
		}

		HashMap<Integer, int[]> mapping = new HashMap<Integer, int[]>();

		BufferedReader reader = null;
		InputStreamReader inputReader = null;
		try {
			inputReader = new InputStreamReader(ContentNormalizer.class.getClassLoader().getResourceAsStream(("ConfusableCharacter.bin")));
			reader = new BufferedReader(inputReader);
			String line;
			while ((line = reader.readLine()) != null) {
				if (StringUtils.isNullOrEmpty(line))
					continue;

				int sharp = line.indexOf('#');
				if (sharp == 0)
					continue;

				if (sharp > 0)
					line = line.substring(0, sharp);

				String[] ss = removeEmptyEntries(line, ";");
				if (ss.length < 2)
					continue;
				int code = 0;
				try {
					code = Integer.parseInt(ss[0].trim(), 16);
				} catch (Exception e) {
					continue;
				}
				ss = removeEmptyEntries(ss[1], " ");
				int[] to = new int[ss.length];
				for (int i = 0; i < ss.length; i++)
					// try{
					to[i] = Integer.parseInt(ss[i].trim(), 16);
				// }catch (Exception e) {
				// to[i] = 0;
				// }
				mapping.put(Integer.valueOf(code), to);
			}
		} catch (FileNotFoundException e1) {
			logger.error("file not found ", e1);
		} catch (IOException e) {
			logger.error("io error ", e);
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("close error ", e);
				}
			if (inputReader != null)
				try {
					inputReader.close();
				} catch (IOException e) {
					logger.error("close error ", e);
				}

		}
		confusableCharMapping = mapping;
	}

	private static String[] removeEmptyEntries(String str, String flag) {
		List<String> words = new ArrayList<String>();
		for (String word : str.split(flag)) {
			if (!"".equals(word))
				words.add(word);
		}
		return words.toArray(new String[1]);
	}

	public ContentNormalizer(Reader content) {
		cChar = new char[2];
		// lChar = 0;

		cCache = prepareCharForNormalize();
		lCache = 0;
		iCache = 0;

		cTemp = prepareCharForNormalize();

		try {
			hasha = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error("get MD5 instance error ", e);
		}
		// sugry = new char[2];
		hbuff = new byte[64]; // most efficient for MD5
		hbptr = 0;

		/*
		 * try { hChar = GCHandle.Alloc(_cChar, GCHandleType.Pinned); hTemp = GCHandle.Alloc(_cTemp, GCHandleType.Pinned); hCache = GCHandle.Alloc(_cCache, GCHandleType.Pinned); } finally { Dispose(false); }
		 */

		this.content = content;
	}

	/*
	 * ~ContentNormalizer() { Dispose(false); }
	 */

	/*
	 * protected virtual void Dispose(bool disposing) { if (_hChar.IsAllocated) _hChar.Free(); if (_hTemp.IsAllocated) _hTemp.Free(); if (_hCache.IsAllocated) _hCache.Free();
	 * 
	 * if (disposing) { _hasha.Clear(); _content.Close(); } }
	 */

	/*
	 * public void Dispose() { GC.SuppressFinalize(this); Dispose(true); }
	 */

	// char.MinValue: stop
	public char read() throws IOException {
		char c = readCache();
		if (c != Character.MIN_VALUE) {
			caculateHash(c);
			return c;
		}

		// try {
		for (int i = content.read(); i != -1; i = content.read()) {
			if (fillCache((char) i)) {
				c = readCache();
				if (c != Character.MIN_VALUE) {
					caculateHash(c);
					return c;
				}
			}
		}
		// } catch (IOException e) {
		// logger.error("io error ",e);
		// }
		caculateHash(Character.MIN_VALUE);
		return Character.MIN_VALUE;
	}

	public Guid computeDigest() throws IOException {
		while (read() != Character.MIN_VALUE)
			;
		return getDigest();
	}

	private void caculateHash(char c) {
		if (c == Character.MIN_VALUE) {
			// try {
			// hasha.update(hbuff);

			// hasha.digest(hbuff, 0, hbptr);
			hasha.update(hbuff, 0, hbptr);

			// } catch (DigestException e) {
			// e.printStackTrace();
			// logger.error("digest error ",e);
			// }
			hbptr = 0;
			digest = Guid.fromByteArray(hasha.digest());
			return;
		}

		if (!Character.isLetter(c) && !(Character.isHighSurrogate(c) || Character.isLowSurrogate(c)) && Character.getType(c) != Character.PRIVATE_USE)
			return;

		if (hbptr >= hbuff.length) {
			// try {
			hasha.update(hbuff, 0, hbuff.length);
			// } catch (DigestException e) {
			// logger.error("digest error ",e);
			// }
			hbptr = 0;
		}

		if (hbptr < hbuff.length) {
			hbuff[hbptr++] = (byte) c;
			hbuff[hbptr++] = (byte) (c >> 8);
		}

		if (hbptr >= hbuff.length) {
			// _hasha.TransformBlock(_hbuff, 0, _hbuff.Length, null, 0);
			// try {
			// hasha.digest(hbuff, 0, hbuff.length);
			hasha.update(hbuff, 0, hbuff.length);
			// } catch (DigestException e) {
			// logger.error("digest error ",e);
			// }
			hbptr = 0;
		}
	}

	public static char[] prepareCharForNormalize() {
		return new char[64];
	}

	public static int normalize(int u, char[] retr) {
		if (u < 0 || u > 0x10FFFF)
			return 0;

		if (u < 0x10000)
			return normalize(new char[] { (char) u }, retr);
		return normalize(Character.toChars(u), retr);

	}

	private static int normalize(char[] inpt, char[] retr) {
		char[] temp = prepareCharForNormalize();

		int count = 0;
		String countStr = NormalizerUtil.normalizeString(inpt);
		// String countStr = Normalizer.normalize(new String(inpt), Form.NFKD);
		temp = countStr.toCharArray();
		count = countStr.length();
		if (count > 0) {
			count = mapConfusableString(temp, count, retr);
			if (count > 0) {
				String tcStr = NormalizerUtil.normalizeString(retr);
				// String tcStr = Normalizer.normalize(new String(retr), Form.NFKD);//Form.NFKD
				temp = tcStr.toCharArray();
				count = tcStr.length();
				if (count > 0) {
					// count = LCMapString(LocaleInvariant, MapStringFlag, temp, count, retr, retr.length);
					retr = new NormalizerUtil().translate(temp, "fan2Jian");
					count = retr.length;
					// count = 1;
				}
			}
		}
		// }
		// finally
		// {
		// if (inptHandle.IsAllocated)
		// inptHandle.Free();
		// if (retrHandle.IsAllocated)
		// retrHandle.Free();
		// if (tempHandle.IsAllocated)
		// tempHandle.Free();
		// }

		int readr = 0;
		int write = 0;
		while (readr < count) {
			char c = retr[readr++];

			if (Character.isLowSurrogate(c))
				continue;

			if (Character.isHighSurrogate(c)) {
				if (readr >= count)
					break;

				char n = retr[readr];
				if (!Character.isLowSurrogate(n))
					continue;

				++readr;

				if (ignoreUnicodeCategory(Character.getType(new String(retr, readr - 2, 2).charAt(0))))
					continue;

				retr[write++] = c;
				retr[write++] = n;
			} else {
				if (ignoreUnicodeCategory(Character.getType(c)))
					continue;
				retr[write++] = c;
			}
		}
		return write;
	}

	private boolean fillCache(char c) {
		lCache = 0;
		iCache = 0;

		if (Character.isLowSurrogate(c)) {
			cChar[1] = c;
			// lChar = 2;

			if (!Character.isHighSurrogate(cChar[0]))
				return false;

			if (ignoreUnicodeCategory(Character.getType(new String(cChar).charAt(0))))
				return false;
		} else {
			cChar[0] = c;
			// lChar = 1;

			if (Character.isHighSurrogate(c) || ignoreUnicodeCategory(Character.getType(cChar[0])))
				return false;
		}

		// String tempStr = Normalizer.normalize(new String(cChar), Form.NFKD);
		String tempStr = NormalizerUtil.normalizeString(cChar);
		cTemp = tempStr.toCharArray();
		lCache = tempStr.length();
		if (lCache < 1)
			return false;

		lCache = mapConfusableString(cTemp, lCache, cCache);
		if (lCache < 1)
			return false;

		// lCache = NormalizeString(NormalizeFormKD, cCache, lCache, cTemp, cTemp.length);
		// String norStr = Normalizer.normalize(new String(cCache), Form.NFKD);
		String norStr = NormalizerUtil.normalizeString(cCache);
		cTemp = norStr.toCharArray();
		lCache = norStr.length();
		if (lCache < 1)
			return false;

		// lCache = LCMapString(LocaleInvariant, MapStringFlag, cTemp, lCache, cCache, cCache.length);
		cCache = new NormalizerUtil().translate(cTemp, "fan2Jian");
		lCache = cCache.length;
		if (lCache < 1)
			return false;

		return true;
	}

	private char readCache() {
		while (iCache < lCache) {
			char c = cCache[iCache++];

			if (c == Character.MIN_VALUE || c == Character.MAX_VALUE)
				continue;

			if (Character.isLowSurrogate(c))
				return c;

			if (Character.isHighSurrogate(c)) {
				if (iCache >= lCache)
					return Character.MIN_VALUE;

				if (!Character.isLowSurrogate(cCache[iCache]))
					continue;

				if (ignoreUnicodeCategory(Character.getType(new String(cCache, iCache - 1, 2).charAt(0)))) {
					++iCache;
					continue;
				}

				return c;
			}

			if (ignoreUnicodeCategory(Character.getType(c)))
				continue;

			return c;
		}

		return Character.MIN_VALUE;
	}

	private static int mapConfusableString(char[] srcStr, int srcCount, char[] destStr) {
		int c = 0;
		for (int i = 0; i < srcCount;) {
			char s = srcStr[i++];
			if (s == Character.MIN_VALUE)
				continue;

			char n = Character.MIN_VALUE;

			int code = 0;
			if (Character.isHighSurrogate(s)) {
				if (i >= srcCount)
					return c;

				n = srcStr[i];
				if (!Character.isLowSurrogate(n))
					continue;

				++i;
				// code = char.ConvertToUtf32(s, n);
				// new String(new String(String.valueOf(u).getBytes("UTF-8"),"UTF-8").getBytes("UTF-16"),"UTF-16")
				code = Character.toCodePoint(s, n);
			} else {
				if (Character.isLowSurrogate(s))
					continue;
				code = s;
			}

			int[] map = confusableCharMapping.get(code);
			if (normalizeConfusableCharacter && map != null) {
				for (int j = 0; j < map.length; ++j) {
					if (map[j] == 0)
						continue;

					if (map[j] < 0x10000) {
						destStr[c++] = (char) map[j];
					} else {
						String m = new String(Character.toChars(map[j]));
						destStr[c++] = m.charAt(0);
						destStr[c++] = m.charAt(1);
					}
				}
			} else {
				if (code < 0x10000) {
					destStr[c++] = s;
				} else {
					destStr[c++] = s;
					destStr[c++] = n;
				}
			}
		}
		return c;
	}

	private static boolean ignoreUnicodeCategory(int cat) {
		if (cat == Character.NON_SPACING_MARK || cat == Character.COMBINING_SPACING_MARK || cat == Character.ENCLOSING_MARK || cat == Character.FORMAT)
			return true;
		return false;
	}

}
