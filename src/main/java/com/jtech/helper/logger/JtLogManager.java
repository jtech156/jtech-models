package com.jtech.helper.logger;

public class JtLogManager {

	public static JtLogger getLogger(final Class<?> clz) {
		return new JtLogger(clz);
	}
}