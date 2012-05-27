package com.unism.infra.util;

import java.io.Serializable;
import java.util.Vector;

/**
 * CMS系统输出错误信息的工具类
 * @Title: CMyErrors.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午12:34:43
 * @version CMS V1.0
 */
public class CMyErrors {
	/**
	 * 集合容器初始化大小
	 */
	private static final int INIT_SIZE = 5;
	/**
	 * 
	 */
	private Vector vErrors = null;

	/**
	 * 默认构造函数
	 */
	public CMyErrors() {
		this.vErrors = new Vector(5);
	}

	/**
	 * 添加错误信息
	 * 
	 * @param _sError
	 * @return
	 */
	public CMyErrors add(String _sError) {
		if (_sError != null)
			this.vErrors.add(_sError);
		return this;
	}

	/**
	 * 添加错误信息列表到集合中存储
	 * 
	 * @param _ex
	 * @return
	 */
	public CMyErrors add(Exception _ex) {
		if (_ex != null)
			this.vErrors.add(_ex);
		return this;
	}

	/**
	 * 添加指定的出错信息
	 * 
	 * @param _sInfo
	 * @param _ex
	 * @return
	 */
	public CMyErrors add(String _sInfo, Exception _ex) {
		add(_sInfo);
		add(_ex);
		return this;
	}

	/**
	 * 添加出错集合列表信息
	 * 
	 * @param _errors
	 * @return
	 */
	public CMyErrors add(CMyErrors _errors) {
		if ((_errors == null) || (_errors.isEmpty())) {
			return this;
		}
		this.vErrors.addAll(_errors.vErrors);
		return this;
	}

	/**
	 * 出错信息列表大小
	 * 
	 * @return
	 */
	public int size() {
		return this.vErrors.size();
	}

	/**
	 * 判断出错列表是否为空
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return this.vErrors.isEmpty();
	}

	/**
	 * 获得错误信息列表集合
	 * 
	 * @return
	 */
	public Vector getErrors() {
		return this.vErrors;
	}

	/**
	 * 获取错误信息集合元素索引
	 * 
	 * @param _index
	 * @return
	 */
	public Object getAt(int _index) {
		try {
			return this.vErrors.get(_index);
		} catch (Exception ex) {
		}
		return null;
	}

	/**
	 * 清除出错信息
	 * 
	 * @return
	 */
	public CMyErrors clear() {
		this.vErrors.clear();
		return this;
	}

	/**
	 * 返回String类型
	 */
	public String toString() {
		return toString(true);
	}

	/**
	 *  输出错误信息
	 * @param _bIncludingNo
	 * @return
	 */
	public String toString(boolean _bIncludingNo) {
		if (this.vErrors.size() == 0) {
			return "";
		}
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < this.vErrors.size(); i++) {
			Object objError = this.vErrors.get(i);
			if (objError == null) {
				continue;
			}
			if (_bIncludingNo)
				buff.append("(" + i + ")");
			if ((objError instanceof String)) {
				buff.append((String) objError).append("\n");
			} else {
				if (!(objError instanceof Throwable))
					continue;
				buff.append(
						CMyException.getStackTraceText((Throwable) objError))
						.append("\n");
			}
		}
		return buff.toString();
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CMyErrors errors = new CMyErrors();
		CMyException ex = new CMyException(1, "My Exception");

		errors.add("This is an error message!");
		errors.add(new CMyException(41, "CMyException error", ex));
		System.out.println(errors.toString());
	}

	/**
	 * 
	 * @return
	 */
	public static int getInitSize() {
		return INIT_SIZE;
	}
}