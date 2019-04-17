package com.jtech.helper.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JtLogger {

	private Logger logger;

	public JtLogger(final Class<?> clz) {
		logger = LogManager.getLogger(clz);
	}

	public void info(String msg) {
		logger.info(msg);
	}

	public void info(String msg, Throwable t) {
		logger.info(msg, t);
	}

	public void debug(String msg) {
		logger.debug(msg);
	}

	public void debug(String msg, Throwable t) {
		logger.debug(msg, t);
	}

	public void warn(String msg) {
		logger.warn(msg);
	}

	public void warn(String msg, Throwable t) {
		logger.warn(msg, t);
	}

	public void error(String msg) {
		logger.error(msg);
	}

	public void error(String msg, Throwable t) {
		logger.error(msg, t);
	}
}