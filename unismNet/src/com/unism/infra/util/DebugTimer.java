package com.unism.infra.util;

public class DebugTimer {
	/**
	 * 
	 */
	private long startTime;
	/**
	 * 
	 */
	private long endTime;

	/**
	 * 当前系统开始时间
	 */
	public void start() {
		this.startTime = System.currentTimeMillis();
	}

	/**
	 * 
	 */
	public void stop() {
		this.endTime = System.currentTimeMillis();
	}

	/**
	 * @return
	 */
	public long getTime() {
		return this.endTime - this.startTime;
	}
}