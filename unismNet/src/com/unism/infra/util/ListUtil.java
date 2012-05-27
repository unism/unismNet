package com.unism.infra.util;

/**
 * @Title: ListUtil.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:47:50
 * @version CMS V1.0 
 */
public class ListUtil {
	/**
	 * @param _value
	 * @param _array
	 * @return
	 */
	public static int indexOf(int _value, int[] _array) {
		for (int i = 0; i < _array.length; i++) {
			if (_array[i] == _value) {
				return i;
			}
		}
		return -1;
	}
}