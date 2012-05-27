package com.unism.infra.util;

import com.unism.infra.util.key.MacAddressHelper;

import java.io.PrintStream;
import java.util.Random;

/**
 * @Title: CMyEncrypt.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:13:42
 * @version CMS V1.0 
 */
/**
 * @Title: CMyEncrypt.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:13:54
 * @version CMS V1.0 
 */
/**
 * @Title: CMyEncrypt.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:13:56
 * @version CMS V1.0 
 */
/**
 * @Title: CMyEncrypt.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:13:58
 * @version CMS V1.0 
 */
/**
 * @Title: CMyEncrypt.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:14:00
 * @version CMS V1.0 
 */
public class CMyEncrypt {
	static final int S11 = 7;
	static final int S12 = 12;
	static final int S13 = 17;
	static final int S14 = 22;
	static final int S21 = 5;
	static final int S22 = 9;
	static final int S23 = 14;
	static final int S24 = 20;
	static final int S31 = 4;
	static final int S32 = 11;
	static final int S33 = 16;
	static final int S34 = 23;
	static final int S41 = 6;
	static final int S42 = 10;
	static final int S43 = 15;
	static final int S44 = 21;
	
	/**
	 * 
	 */
	static final byte[] PADDING = { -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0 };

	/**
	 * 
	 */
	private long[] state = new long[4];

	/**
	 * 
	 */
	private long[] count = new long[2];

	/**
	 * 
	 */
	private byte[] buffer = new byte[64];
	/**
	 * 
	 */
	public String digestHexStr;
	/**
	 * 
	 */
	private byte[] digest = new byte[16];

	/**
	 * 
	 */
	static double ulE = 84716293.0D;

	/**
	 * 
	 */
	static double ulD = 16587853.0D;

	/**
	 * 
	 */
	static double ulN = 59987833.0D;

	/**
	 * 
	 */
	static double dDiff = 13645307.0D;

	/**
	 * @param inbuf
	 * @return
	 */
	public String getMD5OfStr(String inbuf) {
		md5Update(inbuf.getBytes(), inbuf.length());
		md5Final();
		this.digestHexStr = "";
		for (int i = 0; i < 16; i++) {
			this.digestHexStr += byteHEX(this.digest[i]);
		}
		return this.digestHexStr;
	}

	/**
	 * 
	 */
	public CMyEncrypt() {
		md5Init();
	}

	/**
	 * 
	 */
	private void md5Init() {
		this.count[0] = 0L;
		this.count[1] = 0L;

		this.state[0] = 1732584193L;
		this.state[1] = 4023233417L;
		this.state[2] = 2562383102L;
		this.state[3] = 271733878L;
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	private long F(long x, long y, long z) {
		return x & y | (x ^ 0xFFFFFFFF) & z;
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	private long G(long x, long y, long z) {
		return x & z | y & (z ^ 0xFFFFFFFF);
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	private long H(long x, long y, long z) {
		return x ^ y ^ z;
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	private long I(long x, long y, long z) {
		return y ^ (x | z ^ 0xFFFFFFFF);
	}

	/**
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param x
	 * @param s
	 * @param ac
	 * @return
	 */
	private long FF(long a, long b, long c, long d, long x, long s, long ac) {
		a += F(b, c, d) + x + ac;
		a = (int) a << (int) s | (int) a >>> (int) (32L - s);
		a += b;
		return a;
	}

	/**
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param x
	 * @param s
	 * @param ac
	 * @return
	 */
	private long GG(long a, long b, long c, long d, long x, long s, long ac) {
		a += G(b, c, d) + x + ac;
		a = (int) a << (int) s | (int) a >>> (int) (32L - s);
		a += b;
		return a;
	}

	/**
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param x
	 * @param s
	 * @param ac
	 * @return
	 */
	private long HH(long a, long b, long c, long d, long x, long s, long ac) {
		a += H(b, c, d) + x + ac;
		a = (int) a << (int) s | (int) a >>> (int) (32L - s);
		a += b;
		return a;
	}

	/**
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param x
	 * @param s
	 * @param ac
	 * @return
	 */
	private long II(long a, long b, long c, long d, long x, long s, long ac) {
		a += I(b, c, d) + x + ac;
		a = (int) a << (int) s | (int) a >>> (int) (32L - s);
		a += b;
		return a;
	}

	/**
	 * @param inbuf
	 * @param inputLen
	 */
	private void md5Update(byte[] inbuf, int inputLen) {
		byte[] block = new byte[64];
		int index = (int) (this.count[0] >>> 3) & 0x3F;

		if ((this.count[0] += (inputLen << 3)) < inputLen << 3)
			this.count[1] += 1L;
		this.count[1] += (inputLen >>> 29);

		int partLen = 64 - index;
		int i;
		if (inputLen >= partLen) {
			md5Memcpy(this.buffer, inbuf, index, 0, partLen);
			md5Transform(this.buffer);

			for (i = partLen; i + 63 < inputLen; i += 64) {
				md5Memcpy(block, inbuf, 0, i, 64);
				md5Transform(block);
			}
			index = 0;
		} else {
			i = 0;
		}
		md5Memcpy(this.buffer, inbuf, index, i, inputLen - i);
	}

	/**
	 * 
	 */
	private void md5Final() {
		byte[] bits = new byte[8];

		Encode(bits, this.count, 8);

		int index = (int) (this.count[0] >>> 3) & 0x3F;
		int padLen = index < 56 ? 56 - index : 120 - index;
		md5Update(PADDING, padLen);

		md5Update(bits, 8);

		Encode(this.digest, this.state, 16);
	}

	/**
	 * @param output
	 * @param input
	 * @param outpos
	 * @param inpos
	 * @param len
	 */
	private void md5Memcpy(byte[] output, byte[] input, int outpos, int inpos,
			int len) {
		for (int i = 0; i < len; i++)
			output[(outpos + i)] = input[(inpos + i)];
	}

	/**
	 * @param block
	 */
	private void md5Transform(byte[] block) {
		long a = this.state[0];
		long b = this.state[1];
		long c = this.state[2];
		long d = this.state[3];
		long[] x = new long[16];

		Decode(x, block, 64);

		a = FF(a, b, c, d, x[0], 7L, 3614090360L);
		d = FF(d, a, b, c, x[1], 12L, 3905402710L);
		c = FF(c, d, a, b, x[2], 17L, 606105819L);
		b = FF(b, c, d, a, x[3], 22L, 3250441966L);
		a = FF(a, b, c, d, x[4], 7L, 4118548399L);
		d = FF(d, a, b, c, x[5], 12L, 1200080426L);
		c = FF(c, d, a, b, x[6], 17L, 2821735955L);
		b = FF(b, c, d, a, x[7], 22L, 4249261313L);
		a = FF(a, b, c, d, x[8], 7L, 1770035416L);
		d = FF(d, a, b, c, x[9], 12L, 2336552879L);
		c = FF(c, d, a, b, x[10], 17L, 4294925233L);
		b = FF(b, c, d, a, x[11], 22L, 2304563134L);
		a = FF(a, b, c, d, x[12], 7L, 1804603682L);
		d = FF(d, a, b, c, x[13], 12L, 4254626195L);
		c = FF(c, d, a, b, x[14], 17L, 2792965006L);
		b = FF(b, c, d, a, x[15], 22L, 1236535329L);

		a = GG(a, b, c, d, x[1], 5L, 4129170786L);
		d = GG(d, a, b, c, x[6], 9L, 3225465664L);
		c = GG(c, d, a, b, x[11], 14L, 643717713L);
		b = GG(b, c, d, a, x[0], 20L, 3921069994L);
		a = GG(a, b, c, d, x[5], 5L, 3593408605L);
		d = GG(d, a, b, c, x[10], 9L, 38016083L);
		c = GG(c, d, a, b, x[15], 14L, 3634488961L);
		b = GG(b, c, d, a, x[4], 20L, 3889429448L);
		a = GG(a, b, c, d, x[9], 5L, 568446438L);
		d = GG(d, a, b, c, x[14], 9L, 3275163606L);
		c = GG(c, d, a, b, x[3], 14L, 4107603335L);
		b = GG(b, c, d, a, x[8], 20L, 1163531501L);
		a = GG(a, b, c, d, x[13], 5L, 2850285829L);
		d = GG(d, a, b, c, x[2], 9L, 4243563512L);
		c = GG(c, d, a, b, x[7], 14L, 1735328473L);
		b = GG(b, c, d, a, x[12], 20L, 2368359562L);

		a = HH(a, b, c, d, x[5], 4L, 4294588738L);
		d = HH(d, a, b, c, x[8], 11L, 2272392833L);
		c = HH(c, d, a, b, x[11], 16L, 1839030562L);
		b = HH(b, c, d, a, x[14], 23L, 4259657740L);
		a = HH(a, b, c, d, x[1], 4L, 2763975236L);
		d = HH(d, a, b, c, x[4], 11L, 1272893353L);
		c = HH(c, d, a, b, x[7], 16L, 4139469664L);
		b = HH(b, c, d, a, x[10], 23L, 3200236656L);
		a = HH(a, b, c, d, x[13], 4L, 681279174L);
		d = HH(d, a, b, c, x[0], 11L, 3936430074L);
		c = HH(c, d, a, b, x[3], 16L, 3572445317L);
		b = HH(b, c, d, a, x[6], 23L, 76029189L);
		a = HH(a, b, c, d, x[9], 4L, 3654602809L);
		d = HH(d, a, b, c, x[12], 11L, 3873151461L);
		c = HH(c, d, a, b, x[15], 16L, 530742520L);
		b = HH(b, c, d, a, x[2], 23L, 3299628645L);

		a = II(a, b, c, d, x[0], 6L, 4096336452L);
		d = II(d, a, b, c, x[7], 10L, 1126891415L);
		c = II(c, d, a, b, x[14], 15L, 2878612391L);
		b = II(b, c, d, a, x[5], 21L, 4237533241L);
		a = II(a, b, c, d, x[12], 6L, 1700485571L);
		d = II(d, a, b, c, x[3], 10L, 2399980690L);
		c = II(c, d, a, b, x[10], 15L, 4293915773L);
		b = II(b, c, d, a, x[1], 21L, 2240044497L);
		a = II(a, b, c, d, x[8], 6L, 1873313359L);
		d = II(d, a, b, c, x[15], 10L, 4264355552L);
		c = II(c, d, a, b, x[6], 15L, 2734768916L);
		b = II(b, c, d, a, x[13], 21L, 1309151649L);
		a = II(a, b, c, d, x[4], 6L, 4149444226L);
		d = II(d, a, b, c, x[11], 10L, 3174756917L);
		c = II(c, d, a, b, x[2], 15L, 718787259L);
		b = II(b, c, d, a, x[9], 21L, 3951481745L);

		this.state[0] += a;
		this.state[1] += b;
		this.state[2] += c;
		this.state[3] += d;
	}

	/**
	 * @param output
	 * @param input
	 * @param len
	 */
	private void Encode(byte[] output, long[] input, int len) {
		int i = 0;
		for (int j = 0; j < len; j += 4) {
			output[j] = (byte) (int) (input[i] & 0xFF);
			output[(j + 1)] = (byte) (int) (input[i] >>> 8 & 0xFF);
			output[(j + 2)] = (byte) (int) (input[i] >>> 16 & 0xFF);
			output[(j + 3)] = (byte) (int) (input[i] >>> 24 & 0xFF);

			i++;
		}
	}

	/**
	 * @param output
	 * @param input
	 * @param len
	 */
	private void Decode(long[] output, byte[] input, int len) {
		int i = 0;
		for (int j = 0; j < len; j += 4) {
			output[i] = (b2iu(input[j]) | b2iu(input[(j + 1)]) << 8
					| b2iu(input[(j + 2)]) << 16 | b2iu(input[(j + 3)]) << 24);

			i++;
		}
	}

	/**
	 * @param b
	 * @return
	 */
	public static long b2iu(byte b) {
		return b < 0 ? b & 0xFF : b;
	}

	/**
	 * @param ib
	 * @return
	 */
	public static String byteHEX(byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4 & 0xF)];
		ob[1] = Digit[(ib & 0xF)];
		String s = new String(ob);
		return s;
	}

	public static String setAnotherChar(String _sSecret) {
		String sReSecret = "";

		for (int i = 0; i < _sSecret.length(); i++) {
			char c = _sSecret.charAt(i);
			int iAsc = c;

			if ((iAsc >= 97) && (iAsc <= 109))
				iAsc += 13;
			else if ((iAsc >= 110) && (iAsc <= 122))
				iAsc -= 13;
			else if ((iAsc >= 65) && (iAsc <= 77))
				iAsc += 13;
			else if ((iAsc >= 78) && (iAsc <= 90))
				iAsc -= 13;
			sReSecret = sReSecret + (char) iAsc;
		}

		return sReSecret;
	}

	/**
	 * @param _sSrc
	 * @return
	 */
	public static String RASEncrypt(String _sSrc) {
		Random aRand = new Random(System.currentTimeMillis());
		String strEnc = "";
		String strSrc = _sSrc;
		String strTemp = "";
		if (_sSrc.length() <= 0)
			return "";
		int nIndex = 0;
		for (int nCount = strSrc.length(); nIndex < nCount; nIndex++) {
			strTemp = strSrc.substring(nIndex, nIndex + 1);
			int nChar = Integer.parseInt(strTemp, 36);
			strEnc = strEnc + (double) (Mult(nChar, ulE, ulN) + dDiff);
			strEnc = strEnc + "-";
		}

		strTemp = String.valueOf(aRand.nextLong());

		strEnc = strEnc + strTemp.substring(1, 8);
		return strEnc.toUpperCase();
	}

	/**
	 * @param _sSrc
	 * @return
	 */
	public static String RASDecrypt(String _sSrc) {
		String strDec = "";
		String strSrc = new String(_sSrc);
		String strTemp = "";
		int nIndex = 0;
		int nFound = 0;
		for (int nCount = strSrc.length(); nIndex < nCount; nIndex = nFound + 1) {
			nFound = strSrc.indexOf("-", nIndex);
			if (nFound == -1) {
				break;
			}
			double dblTemp = Double.parseDouble(strSrc
					.substring(nIndex, nFound))
					- dDiff;
			strTemp = Integer.toString((int) Mult(dblTemp, ulD, ulN), 36);
			strDec = strDec + strTemp;
		}

		strDec = strDec.toUpperCase();

		return strDec;
	}

	/**
	 * @param x
	 * @param p
	 * @param m
	 * @return
	 */
	private static double Mult(double x, double p, double m) {
		double y = 1.0D;
		try {
			while (p > 0.0D) {
				while (p / 2.0D == (int) (p / 2.0D)) {
					x = x * x % m;
					p /= 2.0D;
				}
				y = x * y % m;
				p -= 1.0D;
			}
			return y;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0.0D;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CMyEncrypt m = new CMyEncrypt();

		long nMac = MacAddressHelper.getMyMacAsLong();
		System.out.println("Mac=" + nMac);

		String ras = "TRSWCM" + String.valueOf(nMac);
		ras = ras.replace('0', 'x');
		ras = ras.replace('1', 'y');
		System.out.println("RAS Encrypt String=" + ras);
		ras = RASEncrypt(ras);
		System.out.println("RAS Encrypt String=" + ras);

		String sDec = RASDecrypt(ras);
		sDec = sDec.replace('X', '0');
		sDec = sDec.replace('Y', '1');
		System.out.println("RASDecrypt String=" + sDec);

		ras = "0123456789";
		ras = RASEncrypt(ras);
		System.out.println("RAS Encrypt String=" + ras);
		sDec = RASDecrypt(ras);
		System.out.println("RASDecrypt String=" + sDec);

		ras = "0123456789";
		ras = RASEncrypt(ras);
		System.out.println("RAS Encrypt String=" + ras);
		sDec = RASDecrypt(ras);
		System.out.println("RASDecrypt String=" + sDec);

		String strSrc = "00-10-A4-7A-7C-0A";
		String s = setAnotherChar(strSrc);
		String s1 = setAnotherChar(s);
		System.out.println("Encrypt String=" + s);
		System.out.println("Decrypt String=" + s1);

		System.out.println(m.getMD5OfStr(strSrc));

		System.out.println(m.getMD5OfStr("00-10-A4-7A-7C-0A"));
		System.out.println(m.getMD5OfStr(m.getMD5OfStr("00-10-A4-7A-7C-0A")));
		System.out.println(m.getMD5OfStr("1234567"));
	}
}