package com.unism.infra.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常的工具类
 * @Title: CMyException.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午12:34:12
 * @version CMS V1.0
 */
public class CMyException extends Exception {
	/**
	 * 错误编号
	 */
	protected int errNo = 0;

	/**
	 * 系统异常信息
	 */
	protected Throwable rootCause = null;

	/**
	 * 构造函数
	 * 
	 * @param _errNo
	 */
	public CMyException(int _errNo) {
		this.errNo = _errNo;
	}

	/**
	 * 构造函数
	 * 
	 * @param _errNo
	 * @param _sMsg
	 */
	public CMyException(int _errNo, String _sMsg) {
		super(_sMsg);
		this.errNo = _errNo;
	}

	/**
	 * 构造函数
	 * 
	 * @param _sMsg
	 */
	public CMyException(String _sMsg) {
		super(_sMsg);
	}

	/**
	 * 构造函数
	 * 
	 * @param _errNo
	 * @param _sMsg
	 * @param _rootCause
	 */
	public CMyException(int _errNo, String _sMsg, Throwable _rootCause) {
		super(_sMsg);
		this.errNo = _errNo;
		this.rootCause = _rootCause;
	}

	/**
	 * 构造函数
	 * 
	 * @param _sMsg
	 * @param _rootCause
	 */
	public CMyException(String _sMsg, Throwable _rootCause) {
		super(_sMsg);
		this.rootCause = _rootCause;
	}

	/**
	 * 获取错误编号
	 * 
	 * @return
	 */
	public int getErrNo() {
		return this.errNo;
	}

	/**
	 * 获取异常对象
	 * 
	 * @return
	 */
	public Throwable getRootCause() {
		return this.rootCause;
	}

	/**
	 * 获取出错编码信息
	 * 
	 * @return
	 */
	public String getErrNoMsg() {
		return ExceptionNumber.getErrNoMsg(this.errNo);
	}

	/**
	 * 仅仅取该异常自身的消息
	 * 
	 * @return
	 */
	public String getMyMessage() {
		return super.getMessage();
	}

	/**
	 * 将异常信息输出为字符串
	 */
	public String toString() {
		String sMessage = "[ERR-" + this.errNo + "] " + getMyMessage();
		return sMessage;
	}

	/**
	 * 取错误消息，若rootCause非空，则同时输出rootCause的错误消息
	 */
	public String getMessage() {
		String sMessage = toString();
		if (this.rootCause != null) {
			sMessage = sMessage + "\r\n<-- " + this.rootCause.toString();
		}

		return sMessage;
	}

	/**
	 * 取错误消息，若rootCause非空，则同时输出rootCause的错误消息
	 */
	public String getLocalizedMessage() {
		return getMessage();
	}

	/**
	 * 向指定设备打印错误信息
	 */
	public void printStackTrace(PrintStream _ps) {
		if (this.rootCause == null) {
			super.printStackTrace(_ps);
		} else {
			Throwable root = this.rootCause;
			synchronized (_ps) {
				_ps.println(toString());
				Throwable temp = null;
				while ((root instanceof CMyException)) {
					_ps.println("<-- " + root.toString());
					temp = root;
					root = ((CMyException) root).getRootCause();
					if (root == null) {
						temp.printStackTrace(_ps);
						break;
					}
				}
				if (root != null) {
					_ps.print("<-- ");
					root.printStackTrace(_ps);
				}
			}
		}
	}

	/**
	 * 向指定设备打印错误信息
	 */
	public void printStackTrace(PrintWriter _pw) {
		if (this.rootCause == null) {
			super.printStackTrace(_pw);
		} else {
			Throwable root = this.rootCause;
			synchronized (_pw) {
				_pw.println(toString());
				Throwable preRoot = null;
				while ((root instanceof CMyException)) {
					_pw.print("<-- ");
					preRoot = root;
					root = ((CMyException) root).getRootCause();
					if (root == null) {
						preRoot.printStackTrace(_pw);
						break;
					}
					_pw.println(preRoot.toString());
				}
				if (root != null) {
					_pw.print("<-- ");
					root.printStackTrace(_pw);
				}
			}
		}
	}

	/**
	 * 取异常堆栈信息
	 * 
	 * @return
	 */
	public String getStackTraceText() {
		return getStackTraceText(this);
	}

	/**
	 * 取异常堆栈信息
	 */
	public static String getStackTraceText(Throwable _ex) {
		StringWriter strWriter = null;
		PrintWriter prtWriter = null;
		String str;
		try {
			strWriter = new StringWriter();
			prtWriter = new PrintWriter(strWriter);
			_ex.printStackTrace(prtWriter);
			prtWriter.flush();
			str = strWriter.toString();
			return str;
		} catch (Exception ex2) {

			return _ex.getMessage();
		} finally {
			if (strWriter != null)
				try {
					strWriter.close();
				} catch (Exception localException1) {
				}
			if (prtWriter != null)
				try {
					prtWriter.close();
				} catch (Exception localException2) {
				}
		}
	}

	/**
	 * 主方法
	 * 
	 * @param args
	 */
	public static final void main(String[] args) {
		CMyException fire0 = new CMyException(1, "my exception 0");

		CMyException fire = new CMyException(1, "my exception 1", fire0);
		CMyException fire2 = new CMyException(10, "my exception 2", fire);
		fire2.printStackTrace(System.out);

		System.out.println("-------------------");
		System.out.println(fire2.getMessage());

		System.out.println("-------------------");
		System.out.println(fire2.getStackTraceText());
		try {
			int a = 0;
			int b = 1 / a;
			System.out.println(b);
		} catch (Exception ex) {
			System.out.println(getStackTraceText(ex));
		}
	}
}