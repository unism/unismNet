package com.unism.infra.util;

import java.math.BigDecimal;

/**
 * @Title: CMyDecimalBuddy.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:07:11
 * @version CMS V1.0 
 */
public final class CMyDecimalBuddy {
	private static final int DEFAULT_PRECESION = 6;

	/**
	 * @param augend
	 * @param addend
	 * @return
	 */
	public static double add(double augend, double addend) {
		BigDecimal digit1 = new BigDecimal(Double.toString(augend));
		BigDecimal digit2 = new BigDecimal(Double.toString(addend));
		return digit1.add(digit2).doubleValue();
	}

	/**
	 * @param minuend
	 * @param subtrahend
	 * @return
	 */
	public static double sub(double minuend, double subtrahend) {
		BigDecimal digit1 = new BigDecimal(Double.toString(minuend));
		BigDecimal digit2 = new BigDecimal(Double.toString(subtrahend));
		return digit1.subtract(digit2).doubleValue();
	}

	/**
	 * @param feciend
	 * @param multiplier
	 * @return
	 */
	public static double mul(double feciend, double multiplier) {
		BigDecimal digit1 = new BigDecimal(Double.toString(feciend));
		BigDecimal digit2 = new BigDecimal(Double.toString(multiplier));
		return digit1.multiply(digit2).doubleValue();
	}

	/**
	 * @param devidend
	 * @param divisor
	 * @return
	 */
	public static double div(double devidend, double divisor) {
		return div(devidend, divisor, 6);
	}

	/**
	 * @param diviend
	 * @param divisor
	 * @param precision
	 * @return
	 */
	public static double div(double diviend, double divisor, int precision) {
		if (precision < 0) {
			throw new IllegalArgumentException(
					"bad precision for division! must be a integer over zero!");
		}
		if (divisor == 0.0D) {
			throw new RuntimeException("被除数不能为零!");
		}
		BigDecimal digit1 = new BigDecimal(Double.toString(diviend));
		BigDecimal digit2 = new BigDecimal(Double.toString(divisor));
		return digit1.divide(digit2, precision, 4).doubleValue();
	}

	/**
	 * @param digit
	 * @param precision
	 * @return
	 */
	public static double round(double digit, int precision) {
		if (precision < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal diviend = new BigDecimal(Double.toString(digit));
		BigDecimal divisor = new BigDecimal("1");
		return diviend.divide(divisor, precision, 4).doubleValue();
	}
}