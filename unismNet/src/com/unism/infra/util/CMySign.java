package com.unism.infra.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Title: CMySign.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:32:08
 * @version CMS V1.0 
 */
/**
 * @Title: CMySign.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:32:15
 * @version CMS V1.0 
 */
public class CMySign {
	/**
	 * 
	 */
	private Signature m_signat;
	/**
	 * 
	 */
	private Signature m_verify;
	/**
	 * 
	 */
	private static CMySign defaultSignat = null;

	/**
	 * 
	 */
	private static CMySign defaultVerify = null;

	/**
	 * 
	 */
	private static final char[] encode_array = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '-', '_' };

	/**
	 * 
	 */
	private static final byte[] decode_array = new byte[256];

	static {
		for (int i = 0; i < 255; i++) {
			decode_array[i] = -1;
		}
		for (int i = 0; i < encode_array.length; i++) {
			decode_array[encode_array[i]] = (byte) i;
		}
	}

	/**
	 * @param inbuf
	 * @return
	 */
	public byte[] decodeRadix64(byte[] inbuf) {
		int size = inbuf.length / 4 * 3;
		if (size == 0) {
			return inbuf;
		}
		if (inbuf[(inbuf.length - 1)] == 46) {
			size--;
			if (inbuf[(inbuf.length - 2)] == 46)
				size--;
		}
		byte[] outbuf = new byte[size];

		int inpos = 0;
		int outpos = 0;
		size = inbuf.length;
		while (size > 0) {
			byte a = decode_array[(inbuf[(inpos++)] & 0xFF)];
			byte b = decode_array[(inbuf[(inpos++)] & 0xFF)];

			outbuf[(outpos++)] = (byte) (a << 2 & 0xFC | b >>> 4 & 0x3);

			if (inbuf[inpos] == 46)
				return outbuf;
			a = b;
			b = decode_array[(inbuf[(inpos++)] & 0xFF)];

			outbuf[(outpos++)] = (byte) (a << 4 & 0xF0 | b >>> 2 & 0xF);

			if (inbuf[inpos] == 46)
				return outbuf;
			a = b;
			b = decode_array[(inbuf[(inpos++)] & 0xFF)];

			outbuf[(outpos++)] = (byte) (a << 6 & 0xC0 | b & 0x3F);
			size -= 4;
		}
		return outbuf;
	}

	/**
	 * @param inbuf
	 * @return
	 */
	public String encodeRadix64(byte[] inbuf) {
		if (inbuf.length == 0) {
			return "";
		}
		byte[] outbuf = new byte[(inbuf.length + 2) / 3 * 4];
		int inpos = 0;
		int outpos = 0;
		int size = inbuf.length;
		while (size > 0) {
			if (size == 1) {
				byte a = inbuf[(inpos++)];
				byte b = 0;
				byte c = 0;
				outbuf[(outpos++)] = (byte) encode_array[(a >>> 2 & 0x3F)];
				outbuf[(outpos++)] = (byte) encode_array[((a << 4 & 0x30) + (b >>> 4 & 0xF))];
				outbuf[(outpos++)] = 46;
				outbuf[(outpos++)] = 46;
			} else if (size == 2) {
				byte a = inbuf[(inpos++)];
				byte b = inbuf[(inpos++)];
				byte c = 0;
				outbuf[(outpos++)] = (byte) encode_array[(a >>> 2 & 0x3F)];
				outbuf[(outpos++)] = (byte) encode_array[((a << 4 & 0x30) + (b >>> 4 & 0xF))];
				outbuf[(outpos++)] = (byte) encode_array[((b << 2 & 0x3C) + (c >>> 6 & 0x3))];
				outbuf[(outpos++)] = 46;
			} else {
				byte a = inbuf[(inpos++)];
				byte b = inbuf[(inpos++)];
				byte c = inbuf[(inpos++)];
				outbuf[(outpos++)] = (byte) encode_array[(a >>> 2 & 0x3F)];
				outbuf[(outpos++)] = (byte) encode_array[((a << 4 & 0x30) + (b >>> 4 & 0xF))];
				outbuf[(outpos++)] = (byte) encode_array[((b << 2 & 0x3C) + (c >>> 6 & 0x3))];
				outbuf[(outpos++)] = (byte) encode_array[(c & 0x3F)];
			}
			size -= 3;
		}
		return new String(outbuf);
	}

	/**
	 * @param paramString
	 * @return
	 * @throws Exception
	 */
	public static CMySign getSign(String paramString) throws Exception {
		FileInputStream localFileInputStream = null;
		ByteArrayOutputStream localByteArrayOutputStream = null;

		if (defaultSignat == null)
			try {
				CMySign localCMySign = new CMySign();

				localFileInputStream = new FileInputStream(paramString);
				if (localFileInputStream == null) {
					throw new Exception("miss file [" + paramString + "]");
				}

				localByteArrayOutputStream = new ByteArrayOutputStream(256);

				int i = 0;
				byte[] arrayOfByte = new byte[512];
				while ((i = localFileInputStream.read(arrayOfByte)) != -1) {
					localByteArrayOutputStream.write(arrayOfByte, 0, i);
				}
				localFileInputStream.close();

				arrayOfByte = localByteArrayOutputStream.toByteArray();

				PKCS8EncodedKeySpec localPKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(
						arrayOfByte);
				KeyFactory localKeyFactory = KeyFactory.getInstance("DSA");
				PrivateKey localPrivateKey = localKeyFactory
						.generatePrivate(localPKCS8EncodedKeySpec);

				Signature localSignature = Signature.getInstance("DSA");
				localSignature.initSign(localPrivateKey);

				localCMySign.m_signat = localSignature;

				defaultSignat = localCMySign;
			} finally {
				if (localFileInputStream != null)
					try {
						localFileInputStream.close();
					} catch (Exception localException1) {
					}
				if (localByteArrayOutputStream != null)
					try {
						localByteArrayOutputStream.close();
					} catch (Exception localException2) {
					}
			}
		return defaultSignat;
	}

	/**
	 * @param paramString
	 * @return
	 * @throws Exception
	 */
	public static CMySign getVerify(String paramString) throws Exception {
		FileInputStream localFileInputStream = null;
		ByteArrayOutputStream localByteArrayOutputStream = null;

		if (defaultVerify == null)
			try {
				CMySign localCMySign = new CMySign();

				localFileInputStream = new FileInputStream(paramString);
				if (localFileInputStream == null) {
					throw new Exception("miss file [" + paramString + "]");
				}

				localByteArrayOutputStream = new ByteArrayOutputStream(256);
				int i = 0;
				byte[] arrayOfByte = new byte[512];

				while ((i = localFileInputStream.read(arrayOfByte)) != -1) {
					localByteArrayOutputStream.write(arrayOfByte, 0, i);
				}
				localFileInputStream.close();

				arrayOfByte = localByteArrayOutputStream.toByteArray();

				X509EncodedKeySpec localX509EncodedKeySpec = new X509EncodedKeySpec(
						arrayOfByte);
				KeyFactory localKeyFactory = KeyFactory.getInstance("DSA");
				PublicKey localPublicKey = localKeyFactory
						.generatePublic(localX509EncodedKeySpec);
				Signature localSignature = Signature.getInstance("DSA");
				localSignature.initVerify(localPublicKey);

				localCMySign.m_verify = localSignature;

				defaultVerify = localCMySign;
			} finally {
				if (localFileInputStream != null)
					try {
						localFileInputStream.close();
					} catch (Exception localException1) {
					}
				if (localByteArrayOutputStream != null)
					try {
						localByteArrayOutputStream.close();
					} catch (Exception localException2) {
					}
			}
		return defaultVerify;
	}

	/**
	 * @param buff
	 * @return
	 * @throws Exception
	 */
	public byte[] sign(byte[] buff) throws Exception {
		Signature s = this.m_signat;

		synchronized (s) {
			s.update(buff);
			return s.sign();
		}
	}

	/**
	 * @param data
	 * @param iLength
	 * @return
	 * @throws Exception
	 */
	public boolean verify(byte[] data, int iLength) throws Exception {
		int iOffset = data.length - iLength;
		byte[] sign = new byte[iLength];
		System.arraycopy(data, iOffset, sign, 0, iLength);
		Signature v = this.m_verify;
		synchronized (v) {
			v.update(data, 0, iOffset - 4);
			return v.verify(sign);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String m_sDataWithSign = "";
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			CMySign su = getSign("/myprivate.key");

			bos = new ByteArrayOutputStream(512);
			dos = new DataOutputStream(bos);

			dos.writeInt(123);
			dos.writeInt(345);
			dos.writeInt(231);
			dos.writeInt(456);
			dos.writeUTF("test");
			dos.writeUTF("中文");
			dos.writeUTF("频道名称");
			dos.flush();

			byte[] buff = su.sign(bos.toByteArray());
			dos.writeInt(buff.length);
			dos.write(buff);
			dos.flush();

			m_sDataWithSign = su.encodeRadix64(bos.toByteArray());

			System.out.println("m_sDataWithSign:" + m_sDataWithSign);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (dos != null)
				try {
					dos.close();
				} catch (Exception localException1) {
				}
			if (bos != null)
				try {
					bos.close();
				} catch (Exception localException2) {
				}
		}
		try {
			byte[] buff = m_sDataWithSign.getBytes();
			CMySign verify = getVerify("/mypublic.key");
			buff = verify.decodeRadix64(buff);
			DataInputStream is = new DataInputStream(new ByteArrayInputStream(
					buff));
			System.out.println(is.readInt());
			System.out.println(is.readInt());
			System.out.println(is.readInt());
			System.out.println(is.readInt());
			System.out.println(is.readUTF());
			System.out.println(is.readUTF());
			System.out.println(is.readUTF());
			if (!verify.verify(buff, is.readInt())) {
				throw new Exception("sign error");
			}
			System.out.println("Verify true");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}