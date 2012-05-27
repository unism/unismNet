package com.unism.infra.util;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.apache.log4j.Logger;

public class CMyKeyGenerator {
	/**
	 * 
	 */
	private static Logger s_logger = Logger.getLogger(CMyKeyGenerator.class);

	/**
	 * @param _sPrivateKeyFile
	 * @param _sPublicKeyFile
	 * @return
	 */
	public static boolean generatorKey(String _sPrivateKeyFile,
			String _sPublicKeyFile) {
		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");
			keygen.initialize(512);
			KeyPair keys = keygen.generateKeyPair();
			PublicKey pubkey = keys.getPublic();
			PrivateKey prikey = keys.getPrivate();

			FileOutputStream out = null;

			out = new FileOutputStream(_sPrivateKeyFile);
			out.write(prikey.getEncoded());
			out.close();

			out = new FileOutputStream(_sPublicKeyFile);
			out.write(pubkey.getEncoded());
			out.close();
		} catch (Exception ex) {
			s_logger.error("产生Key[" + _sPrivateKeyFile + "][" + _sPublicKeyFile
					+ "]失败!", ex);
		}

		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}
}