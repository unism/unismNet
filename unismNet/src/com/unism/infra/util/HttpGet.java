package com.unism.infra.util;

import java.io.File;
import java.io.PrintStream;

public class HttpGet {
	/**
	 * 
	 */
	public static final int TRY_INTERVAL = 200;

	/**
	 * @param _sFileName
	 * @return
	 */
	public static boolean isStarted(String _sFileName) {
		String sContent = "";
		try {
			sContent = CMyFile.readFile(_sFileName);
		} catch (Exception localException) {
		}
		return sContent.indexOf("应用服务器启动成功") >= 0;
	} 

	/**
	 * @param _sUrl
	 * @return
	 */
	public static String get(String _sUrl) {
		return get(_sUrl, 0, true);
	}

	/**
	 * @param _sUrl
	 * @param _nTryCount
	 * @return
	 */
	public static String get(String _sUrl, int _nTryCount) {
		return get(_sUrl, _nTryCount, true);
	}

	/**
	 * @param _sUrl
	 * @param _nTryCount
	 * @param _bRetContent
	 * @return
	 */
	public static String get(String _sUrl, int _nTryCount, boolean _bRetContent) {
		File file = null;

		if (_nTryCount < 0)
			_nTryCount = 0;
		while (_nTryCount >= 0) {
			try {
				file = File.createTempFile("WCMRunJsp", "log");
				String sFileName = file.getAbsolutePath();

				if ((CMy3WLib.getFile(_sUrl, sFileName))
						&& (isStarted(sFileName))) {
					String str1;
					if (_bRetContent) {
						str1 = CMyFile.readFile(sFileName);
					}
				}
			} catch (Exception localException) {
			} finally {
				if (file != null)
					try {
						file.delete();
					} catch (Exception localException1) {
					}
			}

			if (_nTryCount < 0)
				continue;
			try {
				Thread.sleep(200L);
			} catch (Exception localException2) {
			}
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out
					.println("缺少参数！(正确示例：java HttpGet http://www.sina.com.cn)");
			return;
		}

		String sUrl = args[0];
		int nTryCount = 0;
		boolean bRetContent = true;

		if (args.length > 1) {
			try {
				nTryCount = Integer.parseInt(args[1]);
			} catch (Exception ex) {
				nTryCount = 0;
			}

		}

		if (args.length > 2) {
			try {
				bRetContent = Integer.parseInt(args[2]) != 0;
			} catch (Exception ex) {
				bRetContent = true;
			}
		}

		String sContent = get(sUrl, nTryCount, bRetContent);

		System.out.print("\n=========== Get " + sUrl + "============ \n\n");
		if (sContent == null)
			System.out.println("Failed!");
		else
			System.out.println(sContent);
	}
}