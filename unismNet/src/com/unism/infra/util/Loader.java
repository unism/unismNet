package com.unism.infra.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;

/**
 * @Title: Loader.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午11:31:56
 * @version CMS V1.0 
 */
public class Loader {
	/**
	 * 
	 */
	private static Logger logger = Logger.getLogger(Loader.class);
	/**
	 * 
	 */
	static final String TSTR = "Caught Exception while in Loader.getResource. This may be innocuous.";
	/**
	 * 
	 */
	private static boolean java1 = true;

	/**
	 * 
	 */
	private static boolean ignoreTCL = false;

	static {
		String prop = OptionConverter.getSystemProperty("java.version", null);

		if (prop != null) {
			int i = prop.indexOf('.');
			if ((i != -1) && (prop.charAt(i + 1) != '1')) {
				java1 = false;
			}
		}
		String ignoreTCLProp = OptionConverter.getSystemProperty(
				"log4j.ignoreTCL", null);
		if (ignoreTCLProp != null)
			ignoreTCL = OptionConverter.toBoolean(ignoreTCLProp, true);
	}

	/**
	 * @param resource
	 * @return
	 */
	public static URL getResource(String resource) {
		ClassLoader classLoader = null;
		URL url = null;
		try {
			if (!java1) {
				classLoader = getTCL();
				if (classLoader != null) {
					logger.debug("Trying to find [" + resource
							+ "] using context classloader " + classLoader
							+ ".");
					url = classLoader.getResource(resource);
					if (url != null) {
						return url;
					}

				}

			}

			classLoader = Loader.class.getClassLoader();
			if (classLoader != null) {
				LogLog.debug("Trying to find [" + resource + "] using "
						+ classLoader + " class loader.");
				url = classLoader.getResource(resource);
				if (url != null)
					return url;
			}
		} catch (Throwable t) {
			LogLog
					.warn(
							"Caught Exception while in Loader.getResource. This may be innocuous.",
							t);
		}

		LogLog.debug("Trying to find [" + resource
				+ "] using ClassLoader.getSystemResource().");
		return ClassLoader.getSystemResource(resource);
	}

	/**
	 * @return
	 */
	public static boolean isJava1() {
		return java1;
	}

	/**
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static ClassLoader getTCL() throws IllegalAccessException,
			InvocationTargetException {
		Method method = null;
		try {
			method = Thread.class.getMethod("getContextClassLoader", null);
		} catch (NoSuchMethodException e) {
			return null;
		}

		return (ClassLoader) method.invoke(Thread.currentThread(), null);
	}

	/**
	 * @param clazz
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class loadClass(String clazz) throws ClassNotFoundException {
		if ((java1) || (ignoreTCL))
			return Class.forName(clazz);
		try {
			return getTCL().loadClass(clazz);
		} catch (Throwable e) {
		}
		return Class.forName(clazz);
	}
}