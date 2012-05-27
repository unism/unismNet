package com.unism.infra.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.util.BitSet;
import sun.security.action.GetPropertyAction;

/**
 * @Title: CMyURLEncoder.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:44:00
 * @version CMS V1.0 
 */
public class CMyURLEncoder {
	/**
	 * 
	 */
	static BitSet dontNeedEncoding;
	/**
	 * 
	 */
	static final int caseDiff = 32;
	/**
	 * 
	 */
	static String dfltEncName = null;

	static {
		dontNeedEncoding = new BitSet(256);

		for (int i = 97; i <= 122; i++) {
			dontNeedEncoding.set(i);
		}
		for (int i = 65; i <= 90; i++) {
			dontNeedEncoding.set(i);
		}
		for (int i = 48; i <= 57; i++) {
			dontNeedEncoding.set(i);
		}
		dontNeedEncoding.set(32);

		dontNeedEncoding.set(45);
		dontNeedEncoding.set(95);
		dontNeedEncoding.set(46);
		dontNeedEncoding.set(42);

		dfltEncName = (String) AccessController
				.doPrivileged(new GetPropertyAction("file.encoding"));
	}

	/**
	 * @param s
	 * @return
	 */
	public static String encode(String s) {
		String str = null;
		try {
			str = encode(s, dfltEncName);
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
		}
		return str;
	}

	/**
	 * @param s
	 * @param enc
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String s, String enc)
			throws UnsupportedEncodingException {
		boolean needToChange = false;
		boolean wroteUnencodedChar = false;
		int maxBytesPerChar = 10;
		StringBuffer out = new StringBuffer(s.length());
		ByteArrayOutputStream buf = new ByteArrayOutputStream(maxBytesPerChar);

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(buf,
				enc));

		for (int i = 0; i < s.length(); i++) {
			int c = s.charAt(i);

			if (dontNeedEncoding.get(c)) {
				if (c == 32) {
					c = 43;
					needToChange = true;
				}

				out.append((char) c);
				wroteUnencodedChar = true;
			} else {
				try {
					if (wroteUnencodedChar) {
						writer = new BufferedWriter(new OutputStreamWriter(buf,
								enc));
						wroteUnencodedChar = false;
					}
					writer.write(c);

					if ((c >= 55296) && (c <= 56319)) {
						if (i + 1 < s.length()) {
							int d = s.charAt(i + 1);

							if ((d >= 56320) && (d <= 57343)) {
								writer.write(d);
								i++;
							}
						}
					}
					writer.flush();
				} catch (IOException e) {
					buf.reset();
					continue;
				}
				byte[] ba = buf.toByteArray();
				for (int j = 0; j < ba.length; j++) {
					out.append('%');
					char ch = Character.forDigit(ba[j] >> 4 & 0xF, 16);

					if (Character.isLetter(ch)) {
						ch = (char) (ch - ' ');
					}
					out.append(ch);
					ch = Character.forDigit(ba[j] & 0xF, 16);
					if (Character.isLetter(ch)) {
						ch = (char) (ch - ' ');
					}
					out.append(ch);
				}
				buf.reset();
				needToChange = true;
			}
		}

		return needToChange ? out.toString() : s;
	}
}