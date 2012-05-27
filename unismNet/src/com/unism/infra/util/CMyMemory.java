package com.unism.infra.util;

import java.io.PrintStream;

public class CMyMemory {
	/**
	 * @return
	 */
	public static int freeMemory() {
		Runtime currRuntime = Runtime.getRuntime();
		return (int) (currRuntime.freeMemory() / 1024L / 1024L);
	}

	/**
	 * @return
	 */
	public static int totalMemory() {
		Runtime currRuntime = Runtime.getRuntime();
		return (int) (currRuntime.totalMemory() / 1024L / 1024L);
	}

	/**
	 * @return
	 */
	public static String toMemoryInfo() {
		return freeMemory() + "M/" + totalMemory() + "M(free/total)";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(toMemoryInfo());
	}
}