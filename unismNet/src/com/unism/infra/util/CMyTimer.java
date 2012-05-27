package com.unism.infra.util;

/**
 * 
 * @Title: CMyTimer.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-12 下午10:35:31
 * @version CMS V1.0
 */
public class CMyTimer extends Thread {
	/**
	 * 
	 */
	private volatile int interval = 0;

	/**
	 * 
	 */
	private volatile boolean bEnabled = false;

	/**
	 * 
	 */
	private volatile boolean bStopped = false;

	/**
	 * 
	 */
	private Runnable target = null;

	/**
	 * 
	 * @param _target
	 */
	public CMyTimer(Runnable _target) {
		this.target = _target;
	}

	/**
	 * 
	 * @return
	 */
	public int getInerval() {
		return this.interval;
	}

	/**
	 * 
	 * @param _interval
	 */
	public void setInterval(int _interval) {
		this.interval = (_interval <= 0 ? 100 : _interval);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isEnabled() {
		return this.bEnabled;
	}

	/**
	 * 
	 * @param _bEnabled
	 */
	public void setEnabled(boolean _bEnabled) {
		this.bEnabled = _bEnabled;
		if (this.bEnabled)
			try {
				notify();
			} catch (Exception localException) {
			}
	}

	/**
	 * 
	 * @return
	 */
	public boolean isStopped() {
		return isStopped();
	}

	/**
	 * 
	 */
	public void toStop() {
		this.bStopped = true;
	}

	/**
	 * 
	 */
	public final void run() {
		while (!this.bStopped) {
			while (!this.bEnabled) {
				try {
					sleep(this.interval);
				} catch (Exception localException) {
				}
				if (this.bStopped) {
					return;
				}
			}
			this.target.run();
			try {
				Thread.sleep(this.interval);
			} catch (Exception localException1) {
			}
		}
	}
}