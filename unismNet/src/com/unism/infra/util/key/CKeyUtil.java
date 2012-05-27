package com.unism.infra.util.key;

import java.io.PrintStream;

public class CKeyUtil {
	private static int nPart = 15000;

	public static int getKeyNum(String _sNum) {
		String sTemp = new String(_sNum);
		while ((sTemp.charAt(0) == '0') && (sTemp.length() > 0)) {
			if (sTemp.length() == 1) {
				return 0;
			}
			sTemp = sTemp.substring(1, sTemp.length());
		}
		return Integer.parseInt(sTemp);
	}

	public static String DecodeKey(String _sCode) {
		String sRegistorCode = _sCode;
		String[] pRegistorCode = sRegistorCode.split("-");
		String strKey = "";
		String strTemp = "";
		for (int i = 0; i < 6; i++) {
			int nLen = pRegistorCode[i].length();
			int nKeyNum = Integer.parseInt(pRegistorCode[i].substring(nLen - 3,
					nLen), 36);
			strTemp = String.valueOf(nKeyNum - nPart);
			if (strTemp.length() < 4) {
				nLen = strTemp.length();
				for (int j = 0; j < 4 - nLen; j++)
					strTemp = "0" + strTemp;
			}
			strKey = strKey + strTemp;
		}

		return strKey.toUpperCase();
	}

	public static void main(String[] args) {
		System.out.println(DecodeKey("92BRY-CHEDJ-QHCNB-NVBTA-3TD70-HQBVT"));
	}

}