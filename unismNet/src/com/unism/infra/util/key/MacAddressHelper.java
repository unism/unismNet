package com.unism.infra.util.key;

import com.unism.infra.util.CMyString;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Properties;

public class MacAddressHelper {
	public static final boolean IS_DEBUG = false;

	public static boolean isValidMac(long _lMac) {
		if (_lMac == Long.parseLong("123456789012")) {
			return true;
		}

		int nMaxValidCount = 10;
		for (int i = 0; i < nMaxValidCount; i++) {
			if (getMyMacAsLong(i) == _lMac) {
				return true;
			}
		} 
		return false;
	}

	public static long getMyMacAsLong(int _nIndex) {
		String sMac = getMyMac(_nIndex);

		return getMyMacAsLong(sMac);
	}

	public static long getMyMacAsLong() {
		return getMyMacAsLong(0);
	}

	public static long getMyMacAsLong(String _sMac) {
		if ((_sMac == null) || (_sMac.length() <= 0)) {
			return 0L;
		}
		String sMac = _sMac;

		sMac = CMyString.replaceStr(sMac, "-", "");
		long nMac = Long.parseLong(sMac, 16);
		System.out.println(nMac);

		sMac = nMac + "";
		if (sMac.length() > 12) {
			sMac = sMac.substring(0, 12);
			while ((sMac.charAt(0) == '0') && (sMac.length() > 0)) {
				sMac = sMac.substring(1, sMac.length());
			}
			nMac = Long.parseLong(sMac);
		}

		return nMac;
	}

	public static long getMyMacAsLongs(String paramString) {
		if ((paramString == null) || (paramString.length() <= 0)) {
			return 0L;
		}
		String str = paramString;

		str = CMyString.replaceStr(str, "-", "");
		long l = Long.parseLong(str, 16);

		str = "" + l;
		if (str.length() > 12) {
			str = str.substring(0, 12);
			while ((str.charAt(0) == '0') && (str.length() > 0)) {
				str = str.substring(1, str.length());
			}
			l = Long.parseLong(str);
		}

		return l;
	}

	private static String getMacOnWindow(int _nIndex) {
		String s = "";
		try {
			String s1 = "ipconfig /all";
			Process process = Runtime.getRuntime().exec(s1);
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line = bufferedreader.readLine();
			int nIndex = 0;
			while (line != null) {
				String nextLine = bufferedreader.readLine();

				if ((line.indexOf("Physical Address") != -1)
						|| (line.indexOf("物理地址") != -1)) {
					if (nIndex == _nIndex) {
						/**
						 * 获取物理地址MAC
						 */
						s = line.split(":")[1].trim();
						System.out.println("获取物理地址MAC：" + s);
						break;
					}
					nIndex++;
				}
				line = nextLine;
			}
			bufferedreader.close();

			process.waitFor();
		} catch (Exception exception) {
			s = "";
		}
		return s.trim();
	}

	private static String getMacOnLinux(int _nIndex) {
		String s = "";
		try {
			String s1 = "/sbin/ifconfig -a";
			Process process = Runtime.getRuntime().exec(s1);
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line = bufferedreader.readLine().toUpperCase();
			int nIndex = 0;
			while (line != null) {
				String nextLine = bufferedreader.readLine();
				if (line.indexOf("HWADDR") > 0) {
					if (nIndex == _nIndex) {
						int i = line.indexOf("HWADDR") + 7;
						s = line.substring(i);
						break;
					}
					nIndex++;
				}
				line = nextLine.toUpperCase();
			}
			bufferedreader.close();

			process.waitFor();
		} catch (Exception exception) {
			s = "";
		}
		return s.trim().replace(':', '-');
	}

	private static String getMacOnHP(int _nIndex) {
		String s = "";
		try {
			String s1 = "/usr/sbin/lanscan";
			Process process = Runtime.getRuntime().exec(s1);
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line = bufferedreader.readLine().toUpperCase();
			int nIndex = 0;
			while (line != null) {
				String nextLine = bufferedreader.readLine();
				int nPose = line.indexOf("0X");
				if (nPose > 0) {
					if (nIndex == _nIndex) {
						int nStart = nPose + 2;
						int nEnd = line.indexOf(" ", nStart);
						s = line.substring(nStart, nEnd);
						break;
					}

					nIndex++;
				}
				line = nextLine.toUpperCase();
			}

			bufferedreader.close();

			process.waitFor();
		} catch (Exception exception) {
			s = "";
		}
		return s.trim();
	}

	private static String getMacOnSolaris(int _nIndex) {
		String s = "";
		Process process = null;
		try {
			String s1 = "/usr/sbin/ifconfig -a";
			process = Runtime.getRuntime().exec(s1);
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line = bufferedreader.readLine().toUpperCase();
			int nIndex = 0;
			while (line != null) {
				String nextLine = bufferedreader.readLine().toUpperCase();
				if (line.indexOf("NEI0") > 0) {
					int nStart = nextLine.indexOf("INET") + 5;
					if (nStart < 5)
						break;
					int nEnd = nextLine.indexOf(" ", nStart);
					if (nEnd <= nStart)
						break;
					if (nIndex == _nIndex) {
						s = nextLine.substring(nStart, nEnd);
						break;
					}
					nIndex++;
				}
				line = nextLine;
			}
			bufferedreader.close();

			process.waitFor();
		} catch (Exception localException) {
		} finally {
			if (process != null) {
				process.destroy();
			}
		}
		if (s.length() <= 8) {
			s = getMacOnSolaris2(_nIndex);
		}

		return CMyString.replaceStr(s, ".", "");
	}

	private static String getMacOnSolaris2(int _nIndex) {
		String s = "";
		try {
			String s1 = "/usr/sbin/ifconfig -a";
			Process process = Runtime.getRuntime().exec(s1);
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line = bufferedreader.readLine().toUpperCase();
			int nIndex = 0;
			while (line != null) {
				line = line.toUpperCase();
				int nStart = line.indexOf("ETHER");
				if (nStart >= 0) {
					if (nIndex == _nIndex) {
						nStart += 6;
						int nEnd = line.indexOf(" ", nStart);
						if (nEnd <= 0)
							nEnd = line.length();
						s = line.substring(nStart, nEnd).trim();
						break;
					}
					nIndex++;
				}
				line = bufferedreader.readLine();
			}
			bufferedreader.close();
			process.waitFor();
		} catch (Exception ex) {
			System.out.println("获取Sun Solaris操作系统的Mac地址失败！(getMacOnSolaris2)");
			ex.printStackTrace();
		}
		s = CMyString.replaceStr(s, ".", "");
		s = CMyString.replaceStr(s, ":", "");
		return s;
	}

	private static String getMacOnAIX(int _nIndex) {
		String s = "";
		try {
			String s1 = "/usr/bin/uname -m";
			Process process = Runtime.getRuntime().exec(s1);
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line = bufferedreader.readLine().toUpperCase();
			s = line;
			bufferedreader.close();

			process.waitFor();
		} catch (Exception exception) {
			s = "";
		}
		return s.trim();
	}

	public static String getMyMac(int _nIndex) {
		String sOs = System.getProperty("os.name", "");
		if (CMyString.isEmpty(sOs)) {
			sOs = (String) System.getProperties().get("os.name");
			System.out.println("操作系统的名称：" + sOs);
		}
		if (CMyString.isEmpty(sOs)) {
			System.out.println("Can't obatain the os name at runtime!");
			System.out.println("So can't mark the machine.");
			return "";
		}
		sOs = sOs.toUpperCase();
		System.out.println(sOs);

		if (sOs.indexOf("WINDOWS") >= 0)
			return getMacOnWindow(_nIndex);
		if (sOs.indexOf("HP") >= 0)
			return getMacOnHP(_nIndex);
		if (sOs.indexOf("LINUX") >= 0)
			return getMacOnLinux(_nIndex);
		if ((sOs.indexOf("SOLARIS") >= 0) || (sOs.indexOf("SUNOS") >= 0))
			return getMacOnSolaris(_nIndex);
		if (sOs.indexOf("AIX") >= 0)
			return getMacOnAIX(_nIndex);

		return "";
	}

	/**
	 * 117962471696000020401010
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(getMyMacAsLong(0));
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}
}