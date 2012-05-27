package com.unism.infra.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: PinyinHelper.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午11:27:26
 * @version CMS V1.0 
 */
public final class PinyinHelper {
	/**
	 * @param paramString
	 * @return
	 */
	public static String convert(String paramString) {
		StringBuffer localStringBuffer = new StringBuffer(128);

		char[] arrayOfChar = paramString.toCharArray();
		int i = 0;
		for (int j = arrayOfChar.length; i < j; i++) {
			localStringBuffer.append(PinyinResource.get(arrayOfChar[i]));
		}

		return localStringBuffer.toString();
	}

	/**
	 * @param paramString1
	 * @param paramString2
	 * @return
	 */
	public static String convert(String paramString1, String paramString2) {
		StringBuffer localStringBuffer = new StringBuffer(128);
		char[] arrayOfChar = paramString1.toCharArray();
		localStringBuffer.append(PinyinResource.get(arrayOfChar[0]));

		int i = 1;
		for (int j = arrayOfChar.length; i < j; i++) {
			localStringBuffer.append(PinyinResource.get(arrayOfChar[i],
					paramString2));
		}

		return localStringBuffer.toString();
	}

	/**
	 * @param paramString
	 * @param paramBoolean
	 * @return
	 */
	public static String getFirstLetter(String paramString, boolean paramBoolean) {
		StringBuffer localStringBuffer = new StringBuffer(128);
		char[] arrayOfChar = paramString.toCharArray();
		localStringBuffer.append(PinyinResource.get(arrayOfChar[0]).charAt(0));

		int i = 1;
		for (int j = arrayOfChar.length; i < j; i++) {
			localStringBuffer.append(PinyinResource.get(arrayOfChar[i]).charAt(
					0));
		}

		if (paramBoolean) {
			return localStringBuffer.toString().toLowerCase();
		}

		return localStringBuffer.toString();
	}

	/**
	 * @param paramArrayOfString
	 */
	public static void main(String[] paramArrayOfString) {
		if (paramArrayOfString.length == 1) {
			System.out.println(convert(paramArrayOfString[0]));
		} else {
			System.out.println(convert("我是中国人,I love my motherland!"));
			System.out.println(convert("我是中国人,I love my motherland!", "_"));
		}
	}

	/**
	 * @Title: PinyinHelper.java
	 * @Package com.trs.infra.util
	 * @author dfreng
	 * @date 2011-7-13 上午11:27:08
	 * @version CMS V1.0 
	 */
	/**
	 * @Title: PinyinHelper.java
	 * @Package com.trs.infra.util
	 * @author dfreng
	 * @date 2011-7-13 上午11:27:20
	 * @version CMS V1.0 
	 */
	private static class PinyinResource {
		private static final String RESOURCE = "/unicode_to_hanyu_pinyin.txt";
		private static final Map CODEMAP = new HashMap(20904);

		/**
		 * @param paramChar
		 * @return
		 */
		static String get(char paramChar) {
			if ((paramChar < '一') || (paramChar > 40869)) {
				return String.valueOf(paramChar);
			}

			String str = Integer.toHexString(paramChar).toUpperCase();
			return (String) CODEMAP.get(str);
		}

		/**
		 * @param paramChar
		 * @param paramString
		 * @return
		 */
		static String get(char paramChar, String paramString) {
			if ((paramChar < '一') || (paramChar > 40869)) {
				return String.valueOf(paramChar);
			}

			String str = Integer.toHexString(paramChar).toUpperCase();
			return paramString + (String) CODEMAP.get(str);
		}

		static {
			InputStream localInputStream = null;
			InputStreamReader localInputStreamReader = null;
			BufferedReader localBufferedReader = null;
			try {
				Class localClass = PinyinHelper.class;
				localInputStream = localClass
						.getResourceAsStream("/unicode_to_hanyu_pinyin.txt");
				if (localInputStream == null) {
					localInputStream = localClass
							.getResourceAsStream("/unicode_to_hanyu_pinyin.txt"
									.substring(1));
				}
				localInputStreamReader = new InputStreamReader(localInputStream);
				localBufferedReader = new BufferedReader(localInputStreamReader);
				String str = localBufferedReader.readLine();
				String[] arrayOfString = null;
				while (str != null) {
					arrayOfString = str.trim().split(" ");
					CODEMAP.put(arrayOfString[0].toUpperCase(),
							arrayOfString[1]);
					str = localBufferedReader.readLine();
				}
			} catch (Exception localException1) {
				System.err.println("init error");
				localException1.printStackTrace();
			} finally {
				if (localInputStream != null)
					try {
						localInputStream.close();
					} catch (Exception localException2) {
					}
				if (localInputStreamReader != null)
					try {
						localInputStreamReader.close();
					} catch (Exception localException3) {
					}
				if (localBufferedReader != null)
					try {
						localBufferedReader.close();
					} catch (Exception localException4) {
					}
			}
		}
	}
}