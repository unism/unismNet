package com.unism.infra.util;

import com.unism.infra.common.WCMException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CMS字符处理工具类
 * @Title: CMyString.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-12 下午10:38:40
 * @version CMS V1.0
 */
public class CMyString {
	// 默认字符编码集
	public static String ENCODING_DEFAULT = "UTF-8";

	// 获取默认字符编码集
	public static String GET_ENCODING_DEFAULT = "UTF-8";

	// 文件写字符编码
	public static String FILE_WRITING_ENCODING = "GBK";

	// 拼音集文件
	private static String PY_RESOURCE_FILE = "winpy2000.txt";

	//
	private static Hashtable m_hCharName = null;
	//
	private static final String CDATA_END = "]]>";
	//
	private static final String CDATA_END_REPLACER = "(TRSWCM_CDATA_END_HOLDER_TRSWCM)";

	/**
	 * 判断指定字符串是否为空
	 * 
	 * @param _string
	 *            指定的字符串
	 * @return 若字符串为空对象（_string==null）或空串（长度为0），则返回true；否则，返回false.
	 */
	public static boolean isEmpty(String _string) {
		return (_string == null) || (_string.trim().length() == 0);
	}

	/**
	 * 判断指定字符串是否为空
	 * 
	 * @deprecated 由函数 isEmpty 替换
	 * @param _string
	 *            - 指定的字符串
	 * @return - 若字符串为空对象（_string==null）或空串（长度为0），则返回true；否则，返回false.
	 * @see isEmpty( String _string )
	 */
	public static boolean isEmptyStr(String _string) {
		return (_string == null) || (_string.trim().length() == 0);
	}

	/**
	 * 字符串显示处理函数：若为空对象，则返回指定的字符串
	 * 
	 * @param p_sValue
	 * @return
	 * @see showNull( String _sValue, String _sReplaceIfNull )
	 */
	public static String showObjNull(Object p_sValue) {
		return showObjNull(p_sValue, "");
	}

	/**
	 * 字符串显示处理函数：若为空对象，则返回指定的字符串
	 * 
	 * @param _sValue
	 *            - 指定的字符串
	 * @param _sReplaceIfNull
	 *            - 当_sValue==null时的替换显示字符串；可选参数，缺省值为空字符串（""）
	 * @return 处理后的字符串
	 */
	public static String showObjNull(Object _sValue, String _sReplaceIfNull) {
		if (_sValue == null)
			return _sReplaceIfNull;
		return _sValue.toString();
	}

	/**
	 * 字符串显示处理函数：若为空对象，则返回指定的字符串
	 * 
	 * @param p_sValue
	 * @return
	 * @see showNull( String _sValue, String _sReplaceIfNull )
	 */
	public static String showNull(String p_sValue) {
		return showNull(p_sValue, "");
	}

	/**
	 * 字符串显示处理函数：若为空对象，则返回指定的字符串
	 * 
	 * @param _sValue
	 *            - 指定的字符串
	 * @param _sReplaceIfNull
	 *            - 当_sValue==null时的替换显示字符串；可选参数，缺省值为空字符串（""）
	 * @return 处理后的字符串
	 */
	public static String showNull(String _sValue, String _sReplaceIfNull) {
		return _sValue == null ? _sReplaceIfNull : _sValue;
	}

	/**
	 * 
	 * @param p_sValue
	 * @return
	 */
	public static String showEmpty(String p_sValue) {
		return showEmpty(p_sValue, "");
	}

	/**
	 * 
	 * @param _sValue
	 * @param _sReplaceIfEmpty
	 * @return
	 */
	public static String showEmpty(String _sValue, String _sReplaceIfEmpty) {
		return isEmpty(_sValue) ? _sReplaceIfEmpty : _sValue;
	}

	/**
	 * 扩展字符串长度；若长度不足，则是用指定的字符串填充
	 * 
	 * @param _string
	 *            - 要扩展的字符串
	 * @param _length
	 *            - 扩展后的字符串长度
	 * @param _chrFill
	 *            - 扩展时，用于填充的字符
	 * @param _bFillOnLeft
	 *            - 扩展时，是否为左填充（扩展）；否则，为右填充
	 * @return 长度扩展后的字符串
	 */
	public static String expandStr(String _string, int _length, char _chrFill,
			boolean _bFillOnLeft) {
		int nLen = _string.length();
		if (_length <= nLen) {
			return _string;
		}

		String sRet = _string;
		for (int i = 0; i < _length - nLen; i++) {
			sRet = sRet + _chrFill;
		}
		return sRet;
	}

	/**
	 * 设置字符串最后一位为指定的字符
	 * 
	 * @param _string
	 *            - 指定的字符串
	 * 
	 * @param _chrEnd
	 *            - 指定字符，若字符串最后一位不是该字符，则在字符串尾部追加该字符
	 * @return 处理后的字符串 如果isEmpty(_string)返回true,则原样返回
	 * @see isEmpty(String)
	 */
	public static String setStrEndWith(String _string, char _chrEnd) {
		return setStrEndWith0(_string, _chrEnd);
	}

	/**
	 * 
	 * @param _str
	 * @param _charEnd
	 * @return
	 */
	private static String setStrEndWith0(String _str, char _charEnd) {
		if ((isEmpty(_str)) || (_str.endsWith(String.valueOf(_charEnd)))) {
			return _str;
		}

		return _str + _charEnd;
	}

	/**
	 * 构造指定长度的空格字符串
	 * 
	 * @param _length
	 *            - 指定长度
	 * @return 指定长度的空格字符串
	 */
	public static String makeBlanks(int _length) {
		if (_length < 1)
			return "";
		StringBuffer buffer = new StringBuffer(_length);
		for (int i = 0; i < _length; i++) {
			buffer.append(' ');
		}
		return buffer.toString();
	}

	/**
	 * 字符串替换函数：用于将指定字符串中指定的字符串替换为新的字符串。
	 * 
	 * @param _strSrc
	 *            - 源字符串
	 * @param _strOld
	 *            - 被替换的旧字符串
	 * @param _strNew
	 *            - 用来替换旧字符串的新字符串
	 * @return 替换处理后的字符串
	 */
	public static String replaceStr(String _strSrc, String _strOld,
			String _strNew) {
		if ((_strSrc == null) || (_strNew == null) || (_strOld == null)) {
			return _strSrc;
		}

		char[] srcBuff = _strSrc.toCharArray();
		int nSrcLen = srcBuff.length;
		if (nSrcLen == 0) {
			return "";
		}

		char[] oldStrBuff = _strOld.toCharArray();
		int nOldStrLen = oldStrBuff.length;
		if ((nOldStrLen == 0) || (nOldStrLen > nSrcLen)) {
			return _strSrc;
		}
		StringBuffer retBuff = new StringBuffer(nSrcLen
				* (1 + _strNew.length() / nOldStrLen));

		boolean bIsFound = false;

		int i = 0;
		int j;
		while (i < nSrcLen) {
			bIsFound = false;
			if (srcBuff[i] == oldStrBuff[0]) {
				for (j = 1; j < nOldStrLen; j++) {
					if (i + j >= nSrcLen)
						break;
					if (srcBuff[(i + j)] != oldStrBuff[j])
						break;
				}
				bIsFound = (j == nOldStrLen);
			}

			if (bIsFound) {
				retBuff.append(_strNew);
				i += nOldStrLen;
			} else {
				int nSkipTo;
				if (i + nOldStrLen >= nSrcLen)
					nSkipTo = nSrcLen - 1;
				else {
					nSkipTo = i;
				}
				for (; i <= nSkipTo; i++) {
					retBuff.append(srcBuff[i]);
				}
			}
		}
		srcBuff = (char[]) null;
		oldStrBuff = (char[]) null;
		return retBuff.toString();
	}

	/**
	 * 字符串替换函数：用于将指定字符串中指定的字符串替换为新的字符串。
	 * 
	 * @param _strSrc
	 *            - 源字符串
	 * @param _strOld
	 *            - 被替换的旧字符串
	 * @param _strNew
	 *            - 用来替换旧字符串的新字符串
	 * @return 替换处理后的字符串
	 */
	public static String replaceStr(StringBuffer _strSrc, String _strOld,
			String _strNew) {
		if (_strSrc == null) {
			return null;
		}

		int nSrcLen = _strSrc.length();
		if (nSrcLen == 0) {
			return "";
		}

		char[] oldStrBuff = _strOld.toCharArray();
		int nOldStrLen = oldStrBuff.length;
		if ((nOldStrLen == 0) || (nOldStrLen > nSrcLen)) {
			return _strSrc.toString();
		}
		StringBuffer retBuff = new StringBuffer(nSrcLen
				* (1 + _strNew.length() / nOldStrLen));

		boolean bIsFound = false;

		int i = 0;
		int j;
		while (i < nSrcLen) {
			bIsFound = false;

			if (_strSrc.charAt(i) == oldStrBuff[0]) {
				for (j = 1; j < nOldStrLen; j++) {
					if (i + j >= nSrcLen)
						break;
					if (_strSrc.charAt(i + j) != oldStrBuff[j])
						break;
				}
				bIsFound = (j == nOldStrLen);
			}

			if (bIsFound) {
				retBuff.append(_strNew);
				i += nOldStrLen;
			} else {
				int nSkipTo;
				if (i + nOldStrLen >= nSrcLen)
					nSkipTo = nSrcLen - 1;
				else {
					nSkipTo = i;
				}
				for (; i <= nSkipTo; i++) {
					retBuff.append(_strSrc.charAt(i));
				}
			}
		}
		oldStrBuff = (char[]) null;
		return retBuff.toString();
	}

	/**
	 * 字符串编码转换函数，用于将指定编码的字符串转换为标准（Unicode）字符串
	 * 
	 * @param _strSrc
	 * @return
	 * @see getStr( String _strSrc, String _encoding )
	 */
	public static String getStr(String _strSrc) {
		return getStr(_strSrc, ENCODING_DEFAULT);
	}

	/**
	 * 字符转换函数，处理中文问题
	 * 
	 * @param _strSrc
	 *            - 源字符串
	 * @param _bPostMethod
	 *            -
	 *            提交数据的方式（Get方式采用GET_ENCODING_DEFAULT字符集，Post方式采用ENCODING_DEFAULT字符集
	 *            ）
	 * @return
	 */
	public static String getStr(String _strSrc, boolean _bPostMethod) {
		return getStr(_strSrc, _bPostMethod ? ENCODING_DEFAULT
				: GET_ENCODING_DEFAULT);
	}

	/**
	 * 字符串编码转换函数，用于将指定编码的字符串转换为标准（Unicode）字符串
	 * <p>
	 * Purpose: 转换字符串内码，用于解决中文显示问题
	 * <p>
	 * Usage： 在页面切换时，获取并显示中文字符串参数时可用。
	 * 
	 * @param _strSrc
	 *            - 需要转换的字符串
	 * 
	 * @param _encoding
	 *            - 指定字符串（_strSrc）的编码方式；可选参数，缺省值为ENCODING_DEFAULT
	 * @return
	 */
	public static String getStr(String _strSrc, String _encoding) {
		if ((_encoding == null) || (_encoding.length() == 0))
			return _strSrc;
		try {
			byte[] byteStr = new byte[_strSrc.length()];
			char[] charStr = _strSrc.toCharArray();
			for (int i = byteStr.length - 1; i >= 0; i--) {
				byteStr[i] = (byte) charStr[i];
			}

			return new String(byteStr, _encoding);
		} catch (Exception ex) {
		}

		return _strSrc;
	}

	/**
	 * 将指定的字符串转化为ISO-8859-1编码的字符串
	 * 
	 * @param _strSrc
	 *            - 指定的源字符串
	 * @return 转化后的字符串
	 */
	public static String toISO_8859(String _strSrc) {
		if (_strSrc == null)
			return null;
		try {
			return new String(_strSrc.getBytes(), "ISO-8859-1");
		} catch (Exception ex) {
		}
		return _strSrc;
	}

	/**
	 * 将指定的字符串转化为ISO-8859-1编码的字符串
	 * 
	 * @deprecated 含义模糊，已经使用toISO_8859替换
	 * @param _strSrc
	 *            - 指定的源字符串
	 * @return 转化后的字符串
	 */
	public static String toUnicode(String _strSrc) {
		return toISO_8859(_strSrc);
	}

	/**
	 * 提取字符串UTF8编码的字节流
	 * <p>
	 * 说明：等价于 _string.getBytes("UTF8")
	 * 
	 * @param _string
	 *            - 源字符串
	 * @return UTF8编码的字节数组
	 */
	public static byte[] getUTF8Bytes(String _string) {
		char[] c = _string.toCharArray();
		int len = c.length;

		int count = 0;
		for (int i = 0; i < len; i++) {
			int ch = c[i];
			if (ch <= 127)
				count++;
			else if (ch <= 2047)
				count += 2;
			else {
				count += 3;
			}

		}

		byte[] b = new byte[count];
		int off = 0;
		for (int i = 0; i < len; i++) {
			int ch = c[i];
			if (ch <= 127) {
				b[(off++)] = (byte) ch;
			} else if (ch <= 2047) {
				b[(off++)] = (byte) (ch >> 6 | 0xC0);
				b[(off++)] = (byte) (ch & 0x3F | 0x80);
			} else {
				b[(off++)] = (byte) (ch >> 12 | 0xE0);
				b[(off++)] = (byte) (ch >> 6 & 0x3F | 0x80);
				b[(off++)] = (byte) (ch & 0x3F | 0x80);
			}
		}
		return b;
	}

	/**
	 * 
	 * @param b
	 * @return
	 */
	public static String getUTF8String(byte[] b) {
		return getUTF8String(b, 0, b.length);
	}

	/**
	 * 从指定的字节数组中提取UTF8编码的字符串
	 * <p>
	 * 说明：函数等价于 new String(b,"UTF8")
	 * 
	 * @param b
	 *            - 指定的字节数组（UTF8编码）
	 * @param off
	 *            - 开始提取的字节起始位置；可选参数，缺省值为0
	 * @param len
	 *            - 提取的字节数；可选择书，缺省值为全部。
	 * @return 提取后得到的字符串
	 */
	public static String getUTF8String(byte[] b, int off, int len) {
		int count = 0;
		int max = off + len;
		int i = off;
		while (i < max) {
			int c = b[(i++)] & 0xFF;
			switch (c >> 4) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				count++;
				break;
			case 12:
			case 13:
				if ((b[(i++)] & 0xC0) != 128) {
					throw new IllegalArgumentException();
				}
				count++;
				break;
			case 14:
				if (((b[(i++)] & 0xC0) != 128) || ((b[(i++)] & 0xC0) != 128)) {
					throw new IllegalArgumentException();
				}
				count++;
				break;
			case 8:
			case 9:
			case 10:
			case 11:
			default:
				throw new IllegalArgumentException();
			}
		}
		if (i != max) {
			throw new IllegalArgumentException();
		}

		char[] cs = new char[count];
		i = 0;
		while (off < max) {
			int c = b[(off++)] & 0xFF;
			switch (c >> 4) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				cs[(i++)] = (char) c;
				break;
			case 12:
			case 13:
				cs[(i++)] = (char) ((c & 0x1F) << 6 | b[(off++)] & 0x3F);
				break;
			case 14:
				int t = (b[(off++)] & 0x3F) << 6;
				cs[(i++)] = (char) ((c & 0xF) << 12 | t | b[(off++)] & 0x3F);
				break;
			case 8:
			case 9:
			case 10:
			case 11:
			default:
				throw new IllegalArgumentException();
			}
		}
		return new String(cs, 0, count);
	}

	/**
	 * 将字节数据输出为16进制数表示的字符串
	 * 
	 * @param _bytes
	 * @return
	 * @see byteToHexString( byte[] _bytes, char _delim )
	 */
	public static String byteToHexString(byte[] _bytes) {
		return byteToHexString(_bytes, ',');
	}

	/**
	 * 将字节数据输出为16进制无符号数表示的字符串
	 * 
	 * @param _bytes
	 *            - 字节数组
	 * @param _delim
	 *            - 字节数据显示时，字节之间的分隔符；可选参数，缺省值为','
	 * @return 16进制无符号数表示的字节数据
	 */
	public static String byteToHexString(byte[] _bytes, char _delim) {
		String sRet = "";
		for (int i = 0; i < _bytes.length; i++) {
			if (i > 0) {
				sRet = sRet + _delim;
			}
			sRet = sRet + Integer.toHexString(_bytes[i]);
		}
		return sRet;
	}

	/**
	 * 将字节数据输出为指定进制数表示的字符串（注意：负数带有负号）
	 * 
	 * @param _bytes
	 *            - 字节数组
	 * @param _delim
	 *            - 字节数据显示时，字节之间的分隔符；可选参数，缺省值为','
	 * @param _radix
	 *            - 进制数（如16进制）
	 * @return 指定进制数表示的字节数据（负数带由负号）
	 */
	public static String byteToString(byte[] _bytes, char _delim, int _radix) {
		String sRet = "";
		for (int i = 0; i < _bytes.length; i++) {
			if (i > 0) {
				sRet = sRet + _delim;
			}
			sRet = sRet + Integer.toString(_bytes[i], _radix);
		}
		return sRet;
	}

	/**
	 * 用于在Html中显示文本内容
	 * 
	 * @param _sContent
	 * @return
	 * @see transDisplay( String _sContent, boolean _bChangeBlank )
	 */
	public static String transDisplay(String _sContent) {
		return transDisplay(_sContent, true);
	}

	/**
	 * 用于在Html中显示文本内容。将空格等转化为html标记,说明：处理折行时，若使用 style="WORD_WRAP:keepall"
	 * ，则不能将空格转换为 &nbsp;
	 * 
	 * @param _sContent
	 *            - 要显示的内容
	 * @param _bChangeBlank
	 *            - 是否转换空格符；可选参数，默认值为true.
	 * @return - 转化后的Html文本
	 */
	public static String transDisplay(String _sContent, boolean _bChangeBlank) {
		if (_sContent == null) {
			return "";
		}
		char[] srcBuff = _sContent.toCharArray();
		int nSrcLen = srcBuff.length;

		StringBuffer retBuff = new StringBuffer(nSrcLen * 2);

		for (int i = 0; i < nSrcLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case ' ':
				retBuff.append(_bChangeBlank ? "&nbsp;" : " ");
				break;
			case '<':
				retBuff.append("&lt;");
				break;
			case '>':
				retBuff.append("&gt;");
				break;
			case '\n':
				if (_bChangeBlank)
					retBuff.append("<br/>");
				else
					retBuff.append(cTemp);
				break;
			case '"':
				retBuff.append("&quot;");
				break;
			case '&':
				boolean bUnicode = false;
				for (int j = i + 1; (j < nSrcLen) && (!bUnicode); j++) {
					cTemp = srcBuff[j];
					if ((cTemp == '#') || (cTemp == ';')) {
						retBuff.append("&");
						bUnicode = true;
					}
				}
				if (bUnicode)
					continue;
				retBuff.append("&amp;");
				break;
			case '\t':
				retBuff.append(_bChangeBlank ? "&nbsp;&nbsp;&nbsp;&nbsp;"
						: "    ");
				break;
			default:
				retBuff.append(cTemp);
			}

		}

		if (_bChangeBlank) {
			return retBuff.toString();
		}

		return replaceParasStartEndSpaces(retBuff.toString());
	}

	/**
	 * 
	 * @param _sContent
	 * @param p_sQuoteColor
	 * @return
	 */
	public static String transDisplay_bbs(String _sContent, String p_sQuoteColor) {
		return transDisplay_bbs(_sContent, p_sQuoteColor, true);
	}

	/**
	 * 将指定文本内容，格式化为bbs风格的Html文本字符串。
	 * <p>
	 * 说明：[1]处理折行时，若使用 style="WORD_WRAP:keepall"，则不能将空格转换为 &nbsp;
	 * 
	 * <p>
	 * [2]该函数格式化时，如果遇到某一行以":"开始，则认为是引用语(quote)，
	 * 
	 * @param _sContent
	 *            - 文本内容
	 * @param p_sQuoteColor
	 *            - 引用语的显示颜色
	 * @param _bChangeBlank
	 *            - 是否转换空格符，可省略，默认值为true
	 * @return 转化后的Html文本
	 */
	public static String transDisplay_bbs(String _sContent,
			String p_sQuoteColor, boolean _bChangeBlank) {
		if (_sContent == null) {
			return "";
		}

		boolean bIsQuote = false;
		boolean bIsNewLine = true;

		char[] srcBuff = _sContent.toCharArray();
		int nSrcLen = srcBuff.length;

		StringBuffer retBuff = new StringBuffer((int) (nSrcLen * 1.8D));

		for (int i = 0; i < nSrcLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case ':':
				if (bIsNewLine) {
					bIsQuote = true;
					retBuff.append("<font color=" + p_sQuoteColor + ">:");
				} else {
					retBuff.append(":");
				}
				bIsNewLine = false;
				break;
			case ' ':
				retBuff.append(_bChangeBlank ? "&nbsp;" : " ");
				bIsNewLine = false;
				break;
			case '<':
				retBuff.append("&lt;");
				bIsNewLine = false;
				break;
			case '>':
				retBuff.append("&gt;");
				bIsNewLine = false;
				break;
			case '"':
				retBuff.append("&quot;");
				bIsNewLine = false;
				break;
			case '&':
				retBuff.append("&amp;");
				bIsNewLine = false;
				break;
			case '\t':
				retBuff.append(_bChangeBlank ? "&nbsp;&nbsp;&nbsp;&nbsp;"
						: "    ");
				bIsNewLine = false;
				break;
			case '\n':
				if (bIsQuote) {
					bIsQuote = false;
					retBuff.append("</font>");
				}
				retBuff.append("<br/>");
				bIsNewLine = true;
				break;
			default:
				retBuff.append(cTemp);
				bIsNewLine = false;
			}
		}

		if (bIsQuote) {
			retBuff.append("</font>");
		}
		return retBuff.toString();
	}

	/**
	 * javascript显示处理，用于处理javascript中的文本字符串显示
	 * 
	 * @param _sContent
	 *            - javascript文本
	 * @return 处理后的javascript文本
	 */
	public static String transJsDisplay(String _sContent) {
		if (_sContent == null) {
			return "";
		}
		char[] srcBuff = _sContent.toCharArray();
		int nSrcLen = srcBuff.length;

		StringBuffer retBuff = new StringBuffer((int) (nSrcLen * 1.5D));

		for (int i = 0; i < nSrcLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case '<':
				retBuff.append("&lt;");
				break;
			case '>':
				retBuff.append("&gt;");
				break;
			case '"':
				retBuff.append("&quot;");
				break;
			default:
				retBuff.append(cTemp);
			}
		}
		return retBuff.toString();
	}

	/**
	 * 字符串的掩码显示：用指定的掩码构造与指定字符串相同长度的字符串 用于：密码显示等需要掩码处理的场合
	 * 
	 * @param _strSrc
	 *            - 源字符串
	 * 
	 * @param p_chrMark
	 *            - 指定的掩码
	 * @return 用掩码处理后的字符串
	 */
	public static String transDisplayMark(String _strSrc, char p_chrMark) {
		if (_strSrc == null) {
			return "";
		}

		char[] buff = new char[_strSrc.length()];
		for (int i = 0; i < buff.length; i++) {
			buff[i] = p_chrMark;
		}
		return new String(buff);
	}

	/**
	 * SQL语句特殊字符过滤处理函数
	 * <p>
	 * 用于：构造SQL语句时，填充字符串参数时使用
	 * 
	 * <p>
	 * 如： String strSQL =
	 * "select * from tbName where Name='"+CMyString.filterForSQL("a'bc")+"'"
	 * 
	 * <p>
	 * 说明：需要处理的特殊字符及对应转化规则：如： ' --->''
	 * 
	 * <p>
	 * 不允许使用的特殊字符： !@#$%^&*()+|-=\\;:\",./<>?
	 * 
	 * @param _sContent
	 *            - 需要处理的字符串
	 * @return 过滤处理后的字符串
	 */
	public static String filterForSQL(String _sContent) {
		if (_sContent == null) {
			return "";
		}
		int nLen = _sContent.length();
		if (nLen == 0) {
			return "";
		}
		char[] srcBuff = _sContent.toCharArray();
		StringBuffer retBuff = new StringBuffer((int) (nLen * 1.5D));

		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case '\'':
				retBuff.append("''");
				break;
			case ';':
				boolean bSkip = false;
				for (int j = i + 1; (j < nLen) && (!bSkip); j++) {
					char cTemp2 = srcBuff[j];
					if (cTemp2 == ' ')
						continue;
					if (cTemp2 == '&')
						retBuff.append(';');
					bSkip = true;
				}
				if (bSkip)
					continue;
				retBuff.append(';');
				break;
			default:
				retBuff.append(cTemp);
			}

		}

		return retBuff.toString();
	}
	
	/**
	 *
	 * @param paramString
	 * @return
	 */
	public static String filterForSQL2(String paramString) {
	    if (isEmpty(paramString))
	      return paramString;
	    return paramString.replaceAll("(?i)([;\n\r])", "");
	}

	/**
	 * 
	 * @param _sAttrValue
	 * @return
	 */
	public static String filterForXsltValue(String _sAttrValue) {
		if (_sAttrValue == null)
			return "";
		_sAttrValue = _sAttrValue.replaceAll("\\{", "{{");
		_sAttrValue = _sAttrValue.replaceAll("\\}", "}}");
		return _sAttrValue;
	}

	/**
	 * XML文本过滤处理函数：将 & < >\ 等特殊字符做转化处理
	 * 
	 * @param _sContent
	 *            - 指定的XML文本内容
	 * @return 处理后的文本内容
	 */
	public static String filterForXML(String _sContent) {
		if (_sContent == null) {
			return "";
		}
		char[] srcBuff = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0) {
			return "";
		}
		StringBuffer retBuff = new StringBuffer((int) (nLen * 1.8D));

		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case '&':
				retBuff.append("&amp;");
				break;
			case '<':
				retBuff.append("&lt;");
				break;
			case '>':
				retBuff.append("&gt;");
				break;
			case '"':
				retBuff.append("&quot;");
				break;
			case '\'':
				retBuff.append("&apos;");
				break;
			default:
				retBuff.append(cTemp);
			}
		}

		return retBuff.toString();
	}

	/**
	 * HTML元素value值过滤处理函数：将 & < >\ 等特殊字符作转化处理
	 * 
	 * @param _sContent
	 *            - 指定的文本内容
	 * @return 处理后的文本内容
	 */
	public static String filterForHTMLValue(String _sContent) {
		if (_sContent == null) {
			return "";
		}

		char[] srcBuff = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0) {
			return "";
		}
		StringBuffer retBuff = new StringBuffer((int) (nLen * 1.8D));

		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case '&':
				if (i + 1 < nLen) {
					cTemp = srcBuff[(i + 1)];
					if (cTemp == '#')
						retBuff.append("&");
					else
						retBuff.append("&amp;");
				} else {
					retBuff.append("&amp;");
				}
				break;
			case '<':
				retBuff.append("&lt;");
				break;
			case '>':
				retBuff.append("&gt;");
				break;
			case '"':
				retBuff.append("&quot;");
				break;
			default:
				retBuff.append(cTemp);
			}
		}

		return retBuff.toString();
	}

	/**
	 * URL过滤处理函数：将 # & 等特殊字符作转化处理
	 * 
	 * @param _sContent
	 *            - 指定的URL内容
	 * @return 处理后的字符串
	 */
	public static String filterForUrl(String _sContent) {
		if (_sContent == null) {
			return "";
		}
		char[] srcBuff = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0) {
			return "";
		}
		StringBuffer retBuff = new StringBuffer((int) (nLen * 1.8D));

		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case '%':
				retBuff.append("%25");
				break;
			case '?':
				retBuff.append("%3F");
				break;
			case '#':
				retBuff.append("%23");
				break;
			case '&':
				retBuff.append("%26");
				break;
			case ' ':
				retBuff.append("%20");
				break;
			default:
				retBuff.append(cTemp);
			}
		}

		return retBuff.toString();
	}

	/**
	 * JavaScript过滤处理函数：将指定文本中的 " \ \r \n 等特殊字符做转化处理
	 * 
	 * @param _sContent
	 *            - 指定的javascript文本
	 * @return 转化处理后的字符串
	 */
	public static String filterForJs(String _sContent) {
		if (_sContent == null) {
			return "";
		}
		char[] srcBuff = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0) {
			return "";
		}
		StringBuffer retBuff = new StringBuffer((int) (nLen * 1.8D));

		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case '"':
				retBuff.append("\\\"");
				break;
			case '\'':
				retBuff.append("\\'");
				break;
			case '\\':
				retBuff.append("\\\\");
				break;
			case '\n':
				retBuff.append("\\n");
				break;
			case '\r':
				retBuff.append("\\r");
				break;
			case '\f':
				retBuff.append("\\f");
				break;
			case '\t':
				retBuff.append("\\t");
				break;
			case '/':
				retBuff.append("\\/");
				break;
			default:
				retBuff.append(cTemp);
			}
		}

		return retBuff.toString();
	}

	/**
	 * 将指定整型值转化为字符串
	 * 
	 * @param _nValue
	 * @return
	 * @see numberToStr(int _nValue, int _length, char _chrFill)
	 */
	public static String numberToStr(int _nValue) {
		return numberToStr(_nValue, 0);
	}

	/**
	 * 将指定整型值转化为字符串
	 * 
	 * @param _nValue
	 * @param _length
	 * @return
	 * @see numberToStr(int _nValue, int _length, char _chrFill)
	 */
	public static String numberToStr(int _nValue, int _length) {
		return numberToStr(_nValue, _length, '0');
	}

	/**
	 * 将指定整型值转化为字符串
	 * 
	 * @param _nValue
	 *            - 指定整数
	 * 
	 * @param _length
	 *            - 转化后字符串长度；若实际长度小于该长度，则使用_chrFill左填充; 可选参数，缺省值0，表示按照实际长度，不扩展
	 * @param _chrFill
	 *            - 当整数的实际位数小于指定长度时的填充字符；可选参数，缺省值'0'
	 * @return 转化后的字符串
	 */
	public static String numberToStr(int _nValue, int _length, char _chrFill) {
		String sValue = String.valueOf(_nValue);
		return expandStr(sValue, _length, _chrFill, true);
	}

	/**
	 * 将指定长整数转化为字符串
	 * 
	 * @param _lValue
	 * @return
	 * @see numberToStr(long _lValue, int _length, char _chrFill)
	 */
	public static String numberToStr(long _lValue) {
		return numberToStr(_lValue, 0);
	}

	/**
	 * 将指定长整数转化为字符串
	 * 
	 * @param _lValue
	 * @param _length
	 * @return
	 * @see numberToStr(long _lValue, int _length, char _chrFill)
	 */
	public static String numberToStr(long _lValue, int _length) {
		return numberToStr(_lValue, _length, '0');
	}

	/**
	 * 将指定长整数转化为字符串
	 * 
	 * @param _lValue
	 *            - 指定长整数
	 * @param _length
	 *            - 转化后字符串长度；若实际长度小于该长度，则使用_chrFill左填充; 可选参数，缺省值0，表示按照实际长度，不扩展。
	 * @param _chrFill
	 *            - 当整数的实际位数小于指定长度时的填充字符；可选参数，缺省值'0'
	 * @return 转化后的字符串
	 */
	public static String numberToStr(long _lValue, int _length, char _chrFill) {
		String sValue = String.valueOf(_lValue);
		return expandStr(sValue, _length, _chrFill, true);
	}

	/**
	 * 字符串翻转：对于给定的字符串，按相反的顺序输出
	 * 
	 * @param _strSrc
	 *            - 指定的字符串
	 * @return 翻转后的字符串
	 */
	public static String circleStr(String _strSrc) {
		if (_strSrc == null) {
			return null;
		}
		String sResult = "";
		int nLength = _strSrc.length();
		for (int i = nLength - 1; i >= 0; i--) {
			sResult = sResult + _strSrc.charAt(i);
		}
		return sResult;
	}

	/**
	 * 判断指定的字符是不是汉字，目前是通过判断其值是否大于7FH实现的
	 * 
	 * @param c
	 *            - 指定的字符
	 * @return 是否汉字
	 */
	public static final boolean isChineseChar(int c) {
		return c > 127;
	}

	/**
	 * 返回指定字符的显示宽度，在目前的实现中，认为一个英文字符的显示宽度是1，一个汉字的显示宽度是2
	 * 
	 * @param c
	 *            - 指定的字符
	 * @return 指定字符的显示宽度
	 */
	public static final int getCharViewWidth(int c) {
		return isChineseChar(c) ? 2 : 1;
	}

	/**
	 * 返回指定字符串的显示宽度
	 * 
	 * @param s
	 *            - 指定的字符串
	 * @return 指定字符串的显示宽度
	 */
	public static final int getStringViewWidth(String s) {
		if ((s == null) || (s.length() == 0)) {
			return 0;
		}

		int iWidth = 0;
		int iLength = s.length();

		for (int i = 0; i < iLength; i++) {
			iWidth += getCharViewWidth(s.charAt(i));
		}

		return iWidth;
	}

	/**
	 * 字符串截断函数：取指定字符串前部指定长度的字符串； 说明：英文和数字字符长度记1；中文字符长度记2
	 * 
	 * @param _string
	 *            - 要截断的字符串
	 * @param _maxLength
	 *            - 截断长度
	 * @return 截断后的字符串。若指定长度小于字符串实际长度，则在返回的字符串后补“...”
	 */
	public static String truncateStr(String _string, int _maxLength) {
		return truncateStr(_string, _maxLength, "..");
	}

	/**
	 * 字符串截断函数：取指定字符串前部指定长度的字符串； 说明：英文和数字字符长度记1；中文字符长度记2
	 * 
	 * @param _string
	 *            - 要截断的字符串
	 * @param _maxLength
	 *            - 截断长度
	 * @param _sExt
	 *            - 在截断后的字符串上的附加的字符串
	 * @return 截断后的字符串
	 */
	public static String truncateStr(String _string, int _maxLength,
			String _sExt) {
		if (_string == null) {
			return null;
		}

		if (_sExt == null) {
			_sExt = "..";
		}

		int nSrcLen = getStringViewWidth(_string);
		if (nSrcLen <= _maxLength) {
			return _string;
		}

		int nExtLen = getStringViewWidth(_sExt);
		if (nExtLen >= _maxLength) {
			return _string;
		}

		int iLength = _string.length();
		int iRemain = _maxLength - nExtLen;
		StringBuffer sb = new StringBuffer(_maxLength + 2);

		for (int i = 0; i < iLength; i++) {
			char aChar = _string.charAt(i);
			int iNeed = getCharViewWidth(aChar);
			if (iNeed > iRemain) {
				sb.append(_sExt);
				break;
			}
			sb.append(aChar);
			iRemain -= iNeed;
		}

		return sb.toString();
	}

	/**
	 * 过滤掉XML不接受的字符
	 * 
	 * @param _string
	 *            - 源字符串
	 * @return
	 */
	public static String filterForJDOM(String _string) {
		if (_string == null) {
			return null;
		}
		char[] srcBuff = _string.toCharArray();
		int nLen = srcBuff.length;

		StringBuffer dstBuff = new StringBuffer(nLen);

		for (int i = 0; i < nLen; i++) {
			char aChar = srcBuff[i];
			if (!isValidCharOfXML(aChar)) {
				continue;
			}
			dstBuff.append(aChar);
		}
		return dstBuff.toString();
	}

	/**
	 * 校验当前字符是否是合法的XML字符
	 * 
	 * @param _char
	 *            - 需要校验的字符
	 * @return
	 */
	public static boolean isValidCharOfXML(char _char) {
		return (_char == '\t') || (_char == '\n') || (_char == '\r')
				|| ((' ' <= _char) && (_char <= 55295))
				|| ((57344 <= _char) && (_char <= 65533))
				|| ((65536 <= _char) && (_char <= 1114111));
	}

	/**
	 * 计算字符串所占的字节数
	 * <p>
	 * 说明：英文和数字字符长度记1；中文字符长度记2
	 * 
	 * @param _string
	 *            - 要截断的字符串
	 * @return 截断后的字符串。若指定长度小于字符串实际长度，则在返回的字符串后补“...”
	 */
	public static int getBytesLength(String _string) {
		if (_string == null) {
			return 0;
		}
		char[] srcBuff = _string.toCharArray();

		int nGet = 0;
		for (int i = 0; i < srcBuff.length; i++) {
			char aChar = srcBuff[i];
			nGet += (aChar <= '' ? 1 : 2);
		}
		return nGet;
	}

	/**
	 * 字符串截断函数：取指定字符串前部指定长度的字符串；
	 * <p>
	 * 说明：英文和数字字符长度记1；中文字符长度记2。
	 * 
	 * 
	 * @deprecated 已经由函数truncateStr替代
	 * @param _string
	 *            - 要截断的字符串
	 * @param _length
	 *            - 截断长度
	 * @return 截断后的字符串。若指定长度小于字符串实际长度，则在返回的字符串后补“...”
	 */
	public static String cutStr(String _string, int _length) {
		return truncateStr(_string, _length);
	}

	/**
	 * 处理Get方式的字符串
	 * 
	 * @param s
	 *            - 待处理的字符串
	 * @return 返回经过处理的字符串
	 */
	public static String URLEncode(String s) {
		try {
			return URLCoder.encode(s, GET_ENCODING_DEFAULT);
		} catch (Exception ex) {
		}
		return s;
	}

	/**
	 * 
	 * @param _str
	 * @param _sDelim
	 * @return
	 */
	public static String[] split(String _str, String _sDelim) {
		if ((_str == null) || (_sDelim == null)) {
			return new String[0];
		}

		StringTokenizer stTemp = new StringTokenizer(_str, _sDelim);
		int nSize = stTemp.countTokens();
		if (nSize == 0) {
			return new String[0];
		}

		String[] str = new String[nSize];
		int i = 0;
		while (stTemp.hasMoreElements()) {
			str[i] = stTemp.nextToken().trim();
			i++;
		}
		return str;
	}

	/**
	 * 获取按照指定的分隔符截取到的字符个数
	 * 
	 * @param _str
	 *            - 指定的字符数
	 * 
	 * @param _sDelim
	 *            - 指定的分隔符
	 * @return 分隔的字符个数（int）
	 */
	public static int countTokens(String _str, String _sDelim) {
		StringTokenizer stTemp = new StringTokenizer(_str, _sDelim);
		return stTemp.countTokens();
	}

	/**
	 * 
	 * @param _str
	 *            - if null or empty string return an array with zero length
	 * @param _sDelim
	 *            - if null or empty string then this will set to
	 * @return
	 */
	public static int[] splitToInt(String _str, String _sDelim) {
		if (isEmpty(_str)) {
			return new int[0];
		}

		if (isEmpty(_sDelim)) {
			_sDelim = ",";
		}

		StringTokenizer stTemp = new StringTokenizer(_str, _sDelim);
		int[] arInt = new int[stTemp.countTokens()];
		int nIndex = 0;

		while (stTemp.hasMoreElements()) {
			String sValue = (String) stTemp.nextElement();
			arInt[nIndex] = Integer.parseInt(sValue.trim());
			nIndex++;
		}
		return arInt;
	}

	/**
	 * 
	 * @param paramString
	 * @throws Exception
	 */
	private static void loadFirstLetter(String paramString) throws Exception {
		if (m_hCharName == null)
			m_hCharName = new Hashtable(300);
		else {
			m_hCharName.clear();
		}
		Object localObject1 = null;
		FileInputStream localFileInputStream = null;
		BufferedReader localBufferedReader = null;
		try {
			localFileInputStream = new FileInputStream(paramString);
			localBufferedReader = new BufferedReader(new InputStreamReader(
					localFileInputStream, FILE_WRITING_ENCODING));
			String str;
			while ((str = localBufferedReader.readLine()) != null) {
				str = str.trim();

				if (str.length() < 2)
					continue;
				int i = str.charAt(1);
				if (i > 127)
					continue;
				m_hCharName.put(str.substring(0, 1).toUpperCase(), str
						.substring(1, 2));
			}
		} catch (FileNotFoundException localFileNotFoundException) {
			throw new CMyException(55,
					"要读取的拼音配置文件没有找到(CMyString.getFirstLetter)",
					localFileNotFoundException);
		} catch (IOException localIOException) {
			throw new CMyException(53, "读拼音配置文件件时错误(CMyString.getFirstLetter)",
					localIOException);
		} finally {
			if (localBufferedReader != null)
				localBufferedReader.close();
			if (localFileInputStream != null)
				localFileInputStream.close();
			if (localObject1 != null)
				try {
					// localObject1.close();
				} catch (Exception localException) {
				}
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private static Hashtable getPYResource() throws Exception {
		if (m_hCharName != null) {
			return m_hCharName;
		}

		String sResourcePath = CMyFile.mapResouceFullPath(PY_RESOURCE_FILE);
		loadFirstLetter(sResourcePath);
		return m_hCharName;
	}

	/**
	 * 
	 * @param _str
	 * @return
	 * @throws Exception
	 */
	public static String getFirstLetter(String _str) throws Exception {
		if ((_str == null) || (_str.length() < 0)) {
			return "";
		}
		char[] arChars = _str.toCharArray();
		String sFirstChar = _str.substring(0, 1);
		if (arChars[0] > '') {
			return ((String) getPYResource().get(sFirstChar.toUpperCase()))
					.toUpperCase();
		}
		return sFirstChar.toUpperCase();
	}

	/**
	 * 处理XML内容时 如果有CDATA嵌套则替换
	 * 
	 * @param _str
	 * @return
	 */
	public static final String encodeForCDATA(String _str) {
		if ((_str == null) || (_str.length() < 1)) {
			return _str;
		}

		return replaceStr(_str, "]]>", "(TRSWCM_CDATA_END_HOLDER_TRSWCM)");
	}

	/**
	 * 处理XML内容 如果有经过@see #encodeForCDATA(String)替换的CDATA嵌套则还原
	 * 
	 * @param _str
	 * @return
	 */
	public static final String decodeForCDATA(String _str) {
		if ((_str == null) || (_str.length() < 1)) {
			return _str;
		}

		return replaceStr(_str, "(TRSWCM_CDATA_END_HOLDER_TRSWCM)", "]]>");
	}

	/**
	 * 判断字符串中是否包含中文字符 如果包含,则返回 true
	 * 
	 * @param _str
	 *            - 指定的字符串
	 * @return
	 */
	public static final boolean isContainChineseChar(String _str) {
		if (_str == null) {
			return false;
		}

		return _str.getBytes().length != _str.length();
	}

	/**
	 * 将一个数组按照给定的连接符联结起来
	 * 
	 * @param _arColl
	 *            - 进行操作的数组
	 * @param _sSeparator
	 *            - 连接符
	 * @return 连接后的字符串
	 */
	public static String join(ArrayList _arColl, String _sSeparator) {
		if (_arColl == null) {
			return null;
		}

		return join(_arColl.toArray(), _sSeparator);
	}

	/**
	 * 将一个数组按照给定的连接符联结起来
	 * 
	 * @param _arColl
	 *            - 进行操作的数组
	 * @param _sSeparator
	 *            - 连接符
	 * @return 连接后的字符串
	 */
	public static String join(Object[] _arColl, String _sSeparator) {
		if ((_arColl == null) || (_arColl.length == 0) || (_sSeparator == null)) {
			return null;
		}
		if (_arColl.length == 1) {
			return _arColl[0].toString();
		}

		StringBuffer result = new StringBuffer(_arColl[0].toString());
		for (int i = 1; i < _arColl.length; i++) {
			result.append(_sSeparator);
			result.append(_arColl[i].toString());
		}

		return result.toString();
	}

	/**
	 * 
	 * @param _sValue
	 * @return
	 */
	public static boolean containsCDATAStr(String _sValue) {
		if (_sValue == null) {
			return false;
		}
		return _sValue.matches("(?ism).*<!\\[CDATA\\[.*|.*\\]\\]>.*");
	}

	/**
	 * 替换内容中的变量
	 * 
	 * @param _sContent
	 *            - 正文内容
	 * @param _variables
	 *            - 变量表
	 * @return
	 * @throws WCMException
	 */
	public static String parsePageVariables(String _sContent, Map _variables)
			throws WCMException {
		if (_sContent == null) {
			return null;
		}

		StringBuffer buffResult = null;
		try {
			char[] chrArray = _sContent.toCharArray();
			int nLength = chrArray.length;
			int nPos = 0;
			buffResult = new StringBuffer();

			while (nPos < chrArray.length) {
				char aChar = chrArray[(nPos++)];

				if ((aChar == '$') && (nPos < nLength)
						&& (chrArray[nPos] == '{')) {
					StringBuffer buffVarName = new StringBuffer(16);
					nPos++;

					int iCount = 0;
					boolean zFound = false;

					while ((iCount++ < 20) && (nPos < nLength)) {
						if ((aChar = chrArray[(nPos++)]) == '}') {
							zFound = true;
							break;
						}
						buffVarName.append(aChar);
					}

					if (zFound) {
						String sKey = buffVarName.toString().toUpperCase();
						String sVal = "";
						Object oValue = _variables.get(sKey);
						if (oValue != null) {
							sVal = oValue.toString();
						}

						if (sVal != null)
							buffResult.append(sVal);
						else
							buffResult.append("${").append(buffVarName).append(
									'}');
					} else {
						buffResult.append("${").append(buffVarName);
					}
				} else {
					buffResult.append(aChar);
				}
			}
			return buffResult.toString();
		} catch (Exception ex) {
			throw new WCMException(1100, "解析内容中的变量失败!", ex);
		}

	}

	/**
	 * 
	 * @param _sUrl
	 * @param _nMaxLen
	 * @return
	 */
	public static String transPrettyUrl(String _sUrl, int _nMaxLen) {
		return transPrettyUrl(_sUrl, _nMaxLen, null);
	}

	/**
	 * 
	 * @param _sUrl
	 * @param _nMaxLen
	 * @param _sSkimWord
	 * @return
	 */
	public static String transPrettyUrl(String _sUrl, int _nMaxLen,
			String _sSkimWord) {
		int nDemPos = 0;
		if ((_sUrl == null) || (_nMaxLen <= 0) || (_sUrl.length() <= _nMaxLen)
				|| ((nDemPos = _sUrl.lastIndexOf('/')) == -1)) {
			return _sUrl;
		}

		int nFirstPartDemPos = _sUrl.lastIndexOf("://") + 3;
		String sFirstPart = _sUrl.substring(0, nFirstPartDemPos);
		String sMidPart = _sUrl.substring(nFirstPartDemPos, nDemPos);
		if (sMidPart.length() < 3) {
			return _sUrl;
		}
		int nMidLen = _nMaxLen + sMidPart.length() - _sUrl.length();
		if (nMidLen <= 3) {
			nMidLen = 3;
		}
		sMidPart = sMidPart.substring(0, nMidLen);
		sMidPart = sMidPart + (_sSkimWord != null ? _sSkimWord : "....");

		String sLastPart = _sUrl.substring(nDemPos);
		return sFirstPart + sMidPart + sLastPart;
	}

	/**
	 * 替换段首和段尾的空格
	 * 
	 * @param _strValue
	 * @return
	 */
	public static String replaceStartEndSpaces(String _strValue) {
		Pattern pattern = Pattern.compile("(?m)^(\\s*)(.*?)(\\s*)$");
		Matcher matcher = pattern.matcher(_strValue);
		int nLineCount = 30;
		StringBuffer sbResult = new StringBuffer(nLineCount * 100
				+ _strValue.length());
		while (matcher.find()) {
			String sStartSpaces = matcher.group(1);
			for (int i = 0; i < sStartSpaces.length(); i++) {
				char c = sStartSpaces.charAt(i);
				if (c == ' ')
					sbResult.append("&nbsp;");
				else {
					sbResult.append(c);
				}
			}

			sbResult.append(matcher.group(2));

			String sEndSpaces = matcher.group(3);
			char c = '\000';
			for (int i = 0; i < sEndSpaces.length(); i++) {
				c = sEndSpaces.charAt(i);
				if (c == ' ')
					sbResult.append("&nbsp;");
				else {
					sbResult.append(c);
				}
			}
		}

		return sbResult.toString();
	}

	/**
	 * 
	 * @param _strValue
	 * @return
	 */
	public static String replaceParasStartEndSpaces(String _strValue) {
		Pattern pattern = Pattern.compile("\n\r|\n|\r");
		Matcher matcher = pattern.matcher(_strValue);
		int nLineCount = 30;
		StringBuffer sbResult = new StringBuffer(nLineCount * 100
				+ _strValue.length());
		int nLastIndex = 0;
		while (matcher.find()) {
			int nCurrIndex = matcher.start();
			String sTmpBefore = _strValue.substring(nLastIndex, nCurrIndex);
			sTmpBefore = replaceStartEndSpaces(sTmpBefore);
			sbResult.append(sTmpBefore);
			sbResult.append("<br/>");
			nLastIndex = matcher.end();
		}
		String sTmpAfter = _strValue.substring(nLastIndex);
		sTmpAfter = replaceStartEndSpaces(sTmpAfter);
		sbResult.append(sTmpAfter);
		return sbResult.toString();
	}

	/**
	 * 
	 * @param _strValue
	 * @return
	 */
	public static String innerText(String _strValue) {
		String sValue = _strValue.replaceAll("<[^>]+>", "");
		return sValue.replaceAll("&nbsp;", " ").replaceAll("&lt;", "<")
				.replaceAll("&gt;", ">").replaceAll("&quot;", "\"").replaceAll(
						"&apos;", "'").replaceAll("&amp;", "&");
	}

	/**
	 * 
	 * @param _sAttrStr
	 * @return
	 */
	public static Map split2AttrMap(String _sAttrStr) {
		Pattern pattern = Pattern
				.compile("([^\\s=]*)\\s*=(([^\\s'\"]+\\s)|(\\s*(['\"]?)(.*?)\\5))");
		Matcher matcher = pattern.matcher(_sAttrStr);
		Map mpResult = new HashMap();
		while (matcher.find()) {
			String sValue = showNull(matcher.group(6), matcher.group(3));
			mpResult.put(matcher.group(1), sValue);
		}
		return mpResult;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String sValue = " \na aa \n bbb\n中 国\n\r大工业 ac bd \n \r全部bcc ";

		sValue = replaceParasStartEndSpaces(sValue);
		System.out.println(sValue);
		String sExtraAttrs = "action=post.do target = \"_bla nk\" onclick='alert(\"abc\"); return false;'";
		System.out.println(split2AttrMap(sExtraAttrs));
	}

	/**
	 * 
	 * @param paramString
	 * @param paramArrayOfString
	 * @return
	 */
	public static String format(String paramString, String[] paramArrayOfString) {
		if (isEmpty(paramString))
			return "";
		paramString = paramString.replaceAll("(\\$)", "\\\\$1");
		Pattern localPattern = null;
		for (int i = 0; i < paramArrayOfString.length; i++) {
			String str = paramArrayOfString[i].replaceAll("(\\$)", "\\\\$1");
			localPattern = Pattern.compile("\\{" + i + "}");
			paramString = localPattern.matcher(paramString).replaceAll(str);
		}
		return paramString.replaceAll("\\\\\\$", "\\$");
	}

	/**
	 * 
	 * @param paramString
	 * @param paramArrayOfObject
	 * @return
	 */
	public static String format(String paramString, Object[] paramArrayOfObject) {
		if (isEmpty(paramString))
			return "";
		paramString = paramString.replaceAll("(\\$)", "\\\\$1");
		Pattern localPattern = null;
		for (int i = 0; i < paramArrayOfObject.length; i++) {
			if (paramArrayOfObject[i] == null)
				continue;
			String str = paramArrayOfObject[i].toString().replaceAll("(\\$)",
					"\\\\$1");
			localPattern = Pattern.compile("\\{" + i + "}");
			paramString = localPattern.matcher(paramString).replaceAll(str);
		}
		return paramString.replaceAll("\\\\\\$", "\\$");
	}

	/**
	 * 
	 * @param paramString
	 * @param paramArrayOfInt
	 * @return
	 */
	public static String format(String paramString, int[] paramArrayOfInt) {
		if (isEmpty(paramString))
			return "";
		Pattern localPattern = null;
		for (int i = 0; i < paramArrayOfInt.length; i++) {
			localPattern = Pattern.compile("\\{" + i + "}");
			paramString = localPattern.matcher(paramString).replaceAll(
					String.valueOf(paramArrayOfInt[i]));
		}

		return paramString;
	}
}