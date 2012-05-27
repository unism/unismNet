package com.unism.infra.util;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public final class URLCoder {
	/**
	 * 
	 */
	private static boolean[] dontNeed;
	/**
	 * 
	 */
	private static String encoding;
	/**
	 * 
	 */
	private static char[] hexDigit;

	static {
		init();
	}

	/**
	 * @param s
	 * @return
	 */
	public static final String decode(String s) {
		if (s != null) {
			byte[] buff = new byte[s.length()];

			int iLen = decode(s, buff);
			try {
				return new String(buff, 0, iLen, encoding);
			} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
			}
		}

		return null;
	}

	/**
	 * @param str
	 * @param out
	 * @return
	 */
	private static final int decode(String str, byte[] out) {
		int iPos = 0;
		int iLen = out.length;
		int iDst = 0;

		for (iPos = 0; iPos < iLen; iPos++) {
			char c = str.charAt(iPos);
			switch (c) {
			case '+':
				out[(iDst++)] = 32;
				break;
			case '%':
				try {
					out[(iDst++)] = hex2byte(str.charAt(iPos + 1), str
							.charAt(iPos + 2));
					iPos += 2;
				} catch (NumberFormatException e) {
				}
				break;
			default:
				out[(iDst++)] = (byte) c;
			}

		}

		return iDst;
	}

	/**
	 * @param str
	 * @param enc
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static final String decode(String str, String enc)
			throws UnsupportedEncodingException {
		byte[] buff = new byte[str.length()];

		int iLen = decode(str, buff);

		return new String(buff, 0, iLen, enc);
	}

	/**
	 * @param buff
	 * @return
	 */
	private static final String encode(byte[] buff) {
		int iResultLen = 0;
		char[] out = new char[3 * buff.length + 1];
		for (int i = 0; i < buff.length; i++) {
			int c = buff[i];
			c &= 255;
			if (dontNeed[c] != false) {
				if (c == 32) {
					c = 43;
				}
				out[(iResultLen++)] = (char) c;
			} else {
				out[(iResultLen++)] = '%';
				out[(iResultLen++)] = hexDigit[(c >> 4 & 0xF)];
				out[(iResultLen++)] = hexDigit[(c & 0xF)];
			}
		}
		return new String(out, 0, iResultLen);
	}

	/**
	 * @param s
	 * @return
	 */
	public static final String encode(String s) {
		if (s != null) {
			try {
				return encode(s.getBytes(encoding));
			} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
			}
		}

		return null;
	}

	/**
	 * @param str
	 * @param enc
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static final String encode(String str, String enc)
			throws UnsupportedEncodingException {
		return encode(str.getBytes(enc));
	}

	/**
	 * @param h
	 * @param l
	 * @return
	 */
	private static final byte hex2byte(char h, char l) {
		int a = h - '0';

		if (a >= 10) {
			a -= 7;
		}

		int b = l - '0';

		if (b >= 10) {
			b -= 7;
		}

		return (byte) (a << 4 | b);
	}

	/**
	 * 
	 */
	private static final void init() {
		hexDigit = new char[16];
		hexDigit[0] = '0';
		hexDigit[1] = '1';
		hexDigit[2] = '2';
		hexDigit[3] = '3';
		hexDigit[4] = '4';
		hexDigit[5] = '5';
		hexDigit[6] = '6';
		hexDigit[7] = '7';
		hexDigit[8] = '8';
		hexDigit[9] = '9';
		hexDigit[10] = 'A';
		hexDigit[11] = 'B';
		hexDigit[12] = 'C';
		hexDigit[13] = 'D';
		hexDigit[14] = 'E';
		hexDigit[15] = 'F';

		encoding = null;
		String s = null;

		s = System.getProperty("trs.url.encoding");
		if (testEncoding(s)) {
			encoding = s;
		}
		s = System.getProperty("file.encoding");
		if (testEncoding(s)) {
			encoding = s;
		}
		s = "GBK";
		if (testEncoding(s)) {
			encoding = s;
		}
		if (encoding == null) {
			encoding = "ASCII";
		}

		dontNeed = new boolean[256];

		for (int i = 0; i < 256; i++)
			dontNeed[i] = false;
		for (int i = 97; i <= 122; i++)
			dontNeed[i] = true;
		for (int i = 65; i <= 90; i++)
			dontNeed[i] = true;
		for (int i = 48; i <= 57; i++)
			dontNeed[i] = true;
		dontNeed[32] = true;
		dontNeed[45] = true;
		dontNeed[95] = true;
		dontNeed[46] = true;
		dontNeed[42] = true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String s = args[i];

			if (s.equals("-e")) {
				i++;
				encoding = args[i];
			} else if (s.equals("-d")) {
				i++;
				System.out.println(decode(args[i]));
			} else {
				System.out.println(encode(s));
			}
		}
	}

	private static final boolean testEncoding(String enc) {
		if (enc != null) {
			try {
				"test".getBytes(enc);
				return true;
			} catch (Exception localException) {
			}
		}

		return false;
	}
}