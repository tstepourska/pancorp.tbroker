package com.pancorp.tbroker.util;

import org.apache.logging.log4j.Logger;

import com.ibts.controller.ApiConnection.ILogger;

public class __TLogger implements ILogger{
	private Logger lg;
	
	public __TLogger(Logger log) {
		lg = log;
	}
	
	@Override public void log(final String str) {
		lg.info(str);
	}

}
