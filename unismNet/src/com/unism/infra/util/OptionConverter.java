package com.unism.infra.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.LoggerRepository;

/**
 * @Title: OptionConverter.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午11:27:46
 * @version CMS V1.0 
 */
public class OptionConverter {
	/**
	 * 
	 */
	private static Logger LogLog = Logger.getLogger(OptionConverter.class);

	/**
	 * 
	 */
	static String DELIM_START = "${";

	/**
	 * 
	 */
	static char DELIM_STOP = '}';

	/**
	 * 
	 */
	static int DELIM_START_LEN = 2;

	/**
	 * 
	 */
	static int DELIM_STOP_LEN = 1;

	/**
	 * @param l
	 * @param r
	 * @return
	 */
	public static String[] concatanateArrays(String[] l, String[] r) {
		int len = l.length + r.length;
		String[] a = new String[len];

		System.arraycopy(l, 0, a, 0, l.length);
		System.arraycopy(r, 0, a, l.length, r.length);

		return a;
	}

	/**
	 * @param s
	 * @return
	 */
	public static String convertSpecialChars(String s) {
		int len = s.length();
		StringBuffer sbuf = new StringBuffer(len);

		int i = 0;
		while (i < len) {
			char c = s.charAt(i++);
			if (c == '\\') {
				c = s.charAt(i++);
				if (c == 'n')
					c = '\n';
				else if (c == 'r')
					c = '\r';
				else if (c == 't')
					c = '\t';
				else if (c == 'f')
					c = '\f';
				else if (c == '\b')
					c = '\b';
				else if (c == '"')
					c = '"';
				else if (c == '\'')
					c = '\'';
				else if (c == '\\')
					c = '\\';
			}
			sbuf.append(c);
		}
		return sbuf.toString();
	}

	/**
	 * @param key
	 * @param def
	 * @return
	 */
	public static String getSystemProperty(String key, String def) {
		try {
			return System.getProperty(key, def);
		} catch (Throwable e) {
			LogLog.debug("Was not allowed to read system property \"" + key
					+ "\".");
		}
		return def;
	}

	/**
	 * @param props
	 * @param key
	 * @param superClass
	 * @param defaultValue
	 * @return
	 */
	public static Object instantiateByKey(Properties props, String key,
			Class superClass, Object defaultValue) {
		String className = findAndSubst(key, props);
		if (className == null) {
			LogLog.error("Could not find value for key " + key);
			return defaultValue;
		}

		return instantiateByClassName(className.trim(), superClass,
				defaultValue);
	}

	/**
	 * @param value
	 * @param dEfault
	 * @return
	 */
	public static boolean toBoolean(String value, boolean dEfault) {
		if (value == null)
			return dEfault;
		String trimmedVal = value.trim();
		if ("true".equalsIgnoreCase(trimmedVal))
			return true;
		if ("false".equalsIgnoreCase(trimmedVal))
			return false;
		return dEfault;
	}

	/**
	 * @param value
	 * @param dEfault
	 * @return
	 */
	public static int toInt(String value, int dEfault) {
		if (value != null) {
			String s = value.trim();
			try {
				return Integer.valueOf(s).intValue();
			} catch (NumberFormatException e) {
				LogLog.error("[" + s + "] is not in proper int form.");
				e.printStackTrace();
			}
		}
		return dEfault;
	}

	/**
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Level toLevel(String value, Level defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		int hashIndex = value.indexOf('#');
		if (hashIndex == -1) {
			if ("NULL".equalsIgnoreCase(value)) {
				return null;
			}

			return Level.toLevel(value, defaultValue);
		}

		Level result = defaultValue;

		String clazz = value.substring(hashIndex + 1);
		String levelName = value.substring(0, hashIndex);

		if ("NULL".equalsIgnoreCase(levelName)) {
			return null;
		}

		LogLog.debug("toLevel:class=[" + clazz + "]" + ":pri=[" + levelName
				+ "]");
		try {
			Class customLevel = Loader.loadClass(clazz);

			Class[] paramTypes = { String.class, Level.class };
			Method toLevelMethod = customLevel.getMethod("toLevel", paramTypes);

			Object[] params = { levelName, defaultValue };
			Object o = toLevelMethod.invoke(null, params);

			result = (Level) o;
		} catch (ClassNotFoundException e) {
			LogLog.warn("custom level class [" + clazz + "] not found.");
		} catch (NoSuchMethodException e) {
			LogLog
					.warn(
							"custom level class ["
									+ clazz
									+ "]"
									+ " does not have a constructor which takes one string parameter",
							e);
		} catch (InvocationTargetException e) {
			LogLog.warn("custom level class [" + clazz + "]"
					+ " could not be instantiated", e);
		} catch (ClassCastException e) {
			LogLog.warn("class [" + clazz
					+ "] is not a subclass of org.apache.log4j.Level", e);
		} catch (IllegalAccessException e) {
			LogLog.warn("class [" + clazz
					+ "] cannot be instantiated due to access restrictions", e);
		} catch (Exception e) {
			LogLog.warn("class [" + clazz + "], level [" + levelName
					+ "] conversion failed.", e);
		}
		return result;
	}

	/**
	 * @param value
	 * @param dEfault
	 * @return
	 */
	public static long toFileSize(String value, long dEfault) {
		if (value == null) {
			return dEfault;
		}
		String s = value.trim().toUpperCase();
		long multiplier = 1L;
		int index;
		if ((index = s.indexOf("KB")) != -1) {
			multiplier = 1024L;
			s = s.substring(0, index);
		} else if ((index = s.indexOf("MB")) != -1) {
			multiplier = 1048576L;
			s = s.substring(0, index);
		} else if ((index = s.indexOf("GB")) != -1) {
			multiplier = 1073741824L;
			s = s.substring(0, index);
		}
		if (s != null) {
			try {
				return Long.valueOf(s).longValue() * multiplier;
			} catch (NumberFormatException e) {
				LogLog.error("[" + s + "] is not in proper int form.");
				LogLog.error("[" + value + "] not in expected format.", e);
			}
		}
		return dEfault;
	}

	/**
	 * @param key
	 * @param props
	 * @return
	 */
	public static String findAndSubst(String key, Properties props) {
		String value = props.getProperty(key);
		if (value == null)
			return null;
		try {
			return substVars(value, props);
		} catch (IllegalArgumentException e) {
			LogLog.error("Bad option value [" + value + "].", e);
		}
		return value;
	}

	/**
	 * @param className
	 * @param superClass
	 * @param defaultValue
	 * @return
	 */
	public static Object instantiateByClassName(String className,
			Class superClass, Object defaultValue) {
		if (className != null) {
			try {
				Class classObj = Loader.loadClass(className);
				if (!superClass.isAssignableFrom(classObj)) {
					LogLog.error("A \"" + className
							+ "\" object is not assignable to a \""
							+ superClass.getName() + "\" variable.");
					LogLog.error("The class \"" + superClass.getName()
							+ "\" was loaded by ");
					LogLog.error("[" + superClass.getClassLoader()
							+ "] whereas object of type ");
					LogLog.error("\"" + classObj.getName()
							+ "\" was loaded by [" + classObj.getClassLoader()
							+ "].");
					return defaultValue;
				}
				return classObj.newInstance();
			} catch (Exception e) {
				LogLog.error(
						"Could not instantiate class [" + className + "].", e);
			}
		}
		return defaultValue;
	}

	/**
	 * @param val
	 * @param props
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static String substVars(String val, Properties props)
			throws IllegalArgumentException {
		StringBuffer sbuf = new StringBuffer();

		int i = 0;
		while (true) {
			int j = val.indexOf(DELIM_START, i);
			if (j == -1) {
				if (i == 0) {
					return val;
				}

				sbuf.append(val.substring(i, val.length()));
				return sbuf.toString();
			}

			sbuf.append(val.substring(i, j));
			int k = val.indexOf(DELIM_STOP, j);
			if (k == -1) {
				throw new IllegalArgumentException('"' + val
						+ "\" has no closing brace. Opening brace at position "
						+ j + '.');
			}
			j += DELIM_START_LEN;
			String key = val.substring(j, k);

			String replacement = getSystemProperty(key, null);

			if ((replacement == null) && (props != null)) {
				replacement = props.getProperty(key);
			}

			if (replacement != null) {
				String recursiveReplacement = substVars(replacement, props);
				sbuf.append(recursiveReplacement);
			}
			i = k + DELIM_STOP_LEN;
		}
	}

	/**
	 * @param url
	 * @param clazz
	 * @param hierarchy
	 */
	public static void selectAndConfigure(URL url, String clazz,
			LoggerRepository hierarchy) {
		Configurator configurator = null;
		String filename = url.getFile();

		if ((clazz == null) && (filename != null)
				&& (filename.endsWith(".xml"))) {
			clazz = "org.apache.log4j.xml.DOMConfigurator";
		}

		if (clazz != null) {
			LogLog.debug("Preferred configurator class: " + clazz);
			configurator = (Configurator) instantiateByClassName(clazz,
					Configurator.class, null);
			if (configurator == null) {
				LogLog.error("Could not instantiate configurator [" + clazz
						+ "].");
				return;
			}
		} else {
			configurator = new PropertyConfigurator();
		}

		configurator.doConfigure(url, hierarchy);
	}
}