package org.schillingschool.networking.utils;

import java.util.logging.Logger;

import org.schillingschool.networking.Network;

public class Utils {
	private static Logger logger;
	
	public static Logger getLogger() {
		if (logger == null) logger = Logger.getLogger(Network.TITLE);
		return logger;
	}
}
