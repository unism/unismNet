package com.unism.infra.util;

import java.io.PrintStream;
import java.math.BigInteger;

/**
 * @Title: CMyBitsValue.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:02:15
 * @version CMS V1.0 
 */
public class CMyBitsValue implements Cloneable {
	/**
	 * 
	 */
	protected long value = 0L;

	/**
	 * 
	 */
	public CMyBitsValue() {
		this.value = 0L;
	}

	/**
	 * @param _lValue
	 */
	public CMyBitsValue(long _lValue) {
		setValue(_lValue);
	}

	/**
	 * @param _sValue
	 */
	public CMyBitsValue(String _sValue) {
		setValue(_sValue);
	}

	/**
	 * @param _myBitsValue
	 */
	public CMyBitsValue(CMyBitsValue _myBitsValue) {
		copy(_myBitsValue);
	}

	/**
	 * @param _myBitsValue
	 */
	public void copy(CMyBitsValue _myBitsValue) {
		setValue(_myBitsValue.getValue());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		return new CMyBitsValue(this);
	}

	/**
	 * @param _value
	 * @param _index
	 * @return
	 */
	public static boolean getBitOfInt(int _value, int _index) {
		if ((_index < 0) || (_index > 31)) {
			return false;
		}

		return BigInteger.valueOf(_value).testBit(_index);
	}

	/**
	 * @param _value
	 * @param _index
	 * @return
	 */
	public static boolean getBit(long _value, int _index) {
		if ((_index < 0) || (_index > 63)) {
			return false;
		}

		return BigInteger.valueOf(_value).testBit(_index);
	}

	/**
	 * @param _value
	 * @param _index
	 * @param _bValue
	 * @return
	 */
	public static long setBit(long _value, int _index, boolean _bValue) {
		if ((_index < 0) || (_index > 63)) {
			return _value;
		}

		BigInteger bigInt = BigInteger.valueOf(_value);
		if (_bValue)
			bigInt = bigInt.setBit(_index);
		else {
			bigInt = bigInt.clearBit(_index);
		}
		return bigInt.longValue();
	}

	/**
	 * @param _value
	 * @param _index
	 * @param _bValue
	 * @return
	 */
	protected static int setBitOfInt(int _value, int _index, boolean _bValue) {
		if ((_index < 0) || (_index > 31)) {
			return _value;
		}

		return (int) setBit(_value, _index, _bValue);
	}

	/**
	 * @return
	 */
	public long getValue() {
		return this.value;
	}

	/**
	 * @return
	 */
	public int getValueAsInt() {
		return (int) this.value;
	}

	/**
	 * @param _nValue
	 */
	public void setValue(long _nValue) {
		this.value = _nValue;
	}

	/**
	 * @param _sValue
	 */
	public void setValue(String _sValue) {
		if (_sValue == null) {
			return;
		}

		BigInteger bigInt = BigInteger.valueOf(0L);
		int nLen = _sValue.length();
		int nBitPos = nLen - 1;
		for (int i = 0; i < nLen; i++) {
			if (_sValue.charAt(i) == '1') {
				bigInt = bigInt.setBit(nBitPos);
			}
			nBitPos--;
		}
		this.value = bigInt.longValue();
	}

	/**
	 * @param _index
	 * @return
	 */
	public boolean getBit(int _index) {
		return getBit(this.value, _index);
	}

	/**
	 * @param _index
	 * @param _bValue
	 * @return
	 */
	public CMyBitsValue setBit(int _index, boolean _bValue) {
		this.value = setBit(this.value, _index, _bValue);
		return this;
	}

	/**
	 * @param _myBitsValue
	 */
	public void and(CMyBitsValue _myBitsValue) {
		if (_myBitsValue == null) {
			return;
		}

		and(_myBitsValue.getValue());
	}

	/**
	 * @param _value
	 */
	public void and(long _value) {
		BigInteger bigInt = BigInteger.valueOf(this.value);

		bigInt = bigInt.and(BigInteger.valueOf(_value));
		this.value = bigInt.longValue();
	}

	/**
	 * @param _myBitsValue
	 */
	public void or(CMyBitsValue _myBitsValue) {
		if (_myBitsValue == null) {
			return;
		}

		or(_myBitsValue.getValue());
	}

	/**
	 * @param _value
	 */
	public void or(long _value) {
		BigInteger bigInt = BigInteger.valueOf(this.value);

		bigInt = bigInt.or(BigInteger.valueOf(_value));
		this.value = bigInt.longValue();
	}

	/**
	 * @return
	 */
	public int getRealLength() {
		return BigInteger.valueOf(this.value).bitLength();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return toString(getRealLength());
	}

	/**
	 * @param _nLength
	 * @return
	 */
	public String toString(int _nLength) {
		String sValue = Long.toBinaryString(this.value);

		if (_nLength > getRealLength()) {
			sValue = CMyString.expandStr(sValue, _nLength, '0', false);
		}
		return sValue;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CMyBitsValue myBitsValue = new CMyBitsValue(0L);
		System.out.println(myBitsValue.toString(10));

		myBitsValue.setBit(31, true);
		System.out.println(myBitsValue.toString());

		myBitsValue.setBit(1, true);
		myBitsValue.setBit(2, false);
		myBitsValue.setBit(3, true);
		myBitsValue.setBit(10, true);
		myBitsValue.setBit(9, false);
		myBitsValue.setBit(8, true);
		myBitsValue.setBit(7, true);
		System.out.println(myBitsValue.toString());
		System.out.println("10=" + myBitsValue.getBit(10));
		System.out.println("1=" + myBitsValue.getBit(1));
		System.out.println("2=" + myBitsValue.getBit(2));

		int nLen = 64;
		myBitsValue.setValue(0L);
		int i = 0;
		for (i = 0; i < nLen; i++) {
			myBitsValue.setBit(i, true);
		}

		for (i = nLen - 1; i >= 0; i--) {
			System.out.print((i % 10 == 0) && (i > 0) ? " " : String
					.valueOf(i % 10));
		}
		System.out.print("\n");
		System.out.println(myBitsValue.toString(nLen));

		for (i = nLen - 1; i >= 0; i--) {
			System.out.print(myBitsValue.getBit(i) ? "1" : "0");
		}
		System.out.println("\n");

		System.out.println(CMyString.expandStr(Long
				.toBinaryString(9223372036854775807L), 64, '0', true)
				+ " : MAX=" + 9223372036854775807L);
		System.out.println(Long.toBinaryString(-9223372036854775808L)
				+ " : MIN=" + -9223372036854775808L);

		System.out.println("\n========= Test for BigInteger ==========");
		BigInteger bigInt = BigInteger.valueOf(0L);
		bigInt = bigInt.setBit(31).setBit(63);
		System.out.println(bigInt.toString(2));

		long lValue = bigInt.longValue();
		System.out.println(Long.toBinaryString(lValue));

		bigInt = BigInteger.valueOf(lValue);
		System.out.println("\n");
		for (i = 63; i >= 0; i--) {
			System.out.print(bigInt.testBit(i) ? "1" : "0");
		}

		int nValue = 0;
		nValue = setBitOfInt(nValue, 31, true);
		System.out.println("\n int = " + nValue);
		for (i = 0; i < 32; i++) {
			nValue = setBitOfInt(nValue, i, i % 2 == 1);
		}
		nValue = setBitOfInt(nValue, 31, false);
		for (i = 31; i >= 0; i--) {
			System.out.print(getBitOfInt(nValue, i) ? "1" : "0");
		}
		System.out.println("\n");
		System.out.println(Long.toBinaryString(nValue));

		String sValue = "10101";
		myBitsValue.setValue(sValue);
		System.out.println("src = " + sValue);
		System.out.println("dst = " + myBitsValue.toString(10));

		myBitsValue.or(2L);
		System.out.println("or  = " + myBitsValue.toString());

		myBitsValue.and(5L);
		System.out.println("and = " + myBitsValue.toString());
	}
}