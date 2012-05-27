package com.unism.infra.common;

public class WCMDatainvalidException extends WCMException {
	public WCMDatainvalidException(int _errNo) {
		super(_errNo);
	}

	public WCMDatainvalidException(int _errNo, String _sMsg) {
		super(_errNo, _sMsg);
	}

	public WCMDatainvalidException(String _sMsg) {
		super(_sMsg);
	}

	public WCMDatainvalidException(int _errNo, String _sMsg,
			Throwable _rootCause) {
		super(_errNo, _sMsg, _rootCause);
	}

	public WCMDatainvalidException(String _sMsg, Throwable _rootCause) {
		super(_sMsg, _rootCause);
	}
}