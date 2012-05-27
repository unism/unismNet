package com.unism.infra.common;

import com.unism.infra.util.CMyException;

import org.apache.log4j.Logger;

public class WCMException extends CMyException {
	private static final long serialVersionUID = 8819172007753214059L;
	private static final Logger logger = Logger.getLogger(WCMException.class
			.getName());

	public WCMException(int _errNo) {
		super(_errNo);
	}

	public WCMException(int _errNo, String _sMsg) {
		super(_errNo, _sMsg);
	}

	public WCMException(String _sMsg) {
		super(_sMsg);
		this.errNo = 1008;
	}

	public WCMException(int _errNo, String _sMsg, Throwable _rootCause) {
		super(_errNo, _sMsg, _rootCause);
	}

	public WCMException(String _sMsg, Throwable _rootCause) {
		super(_sMsg, _rootCause);
		this.errNo = 1100;
	}

	public static void catchException(String _strDesc, Exception _exCaught,
			boolean bSeverity) throws WCMException {
		logger.error(_strDesc, _exCaught);

		if (bSeverity)
			throw new WCMException(1000, _strDesc, _exCaught);
	}
}