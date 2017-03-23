package org.schillingschool.utils;

import java.awt.Color;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
//import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComponent;

import org.schillingschool.networking.Network;

/**
 * the utilities class. Contains many useful things.
 * Holds the logger for this program
 * Can set Gui Objects to black
 * Holds some constants used throughout the program
 * @author geekman9097
 * @version 9/3/17
 */
public class Utils {
	private static Logger logger; //our logger for the program
//	private static FileHandler writer; //a file handler to write to files
	public final static String NEWLINE = "\n"; // a string to use as a newline character
	public final static String FANCY_LINE = "==--=^=--=="; //an arbitrary, fancy, line.
	public static int LOG_LIMIT = 1024 * 1024; //one megabyte
	public static int LOG_COUNT = 1; //how many logs to create
	
	/**
	 * get the logger this program uses
	 * @return the logger
	 */
	public static Logger getLogger() {
		if (logger == null) createLogger();
		return logger;
	}
	
	private static void createLogger() {
//		try {
//			writer = new FileHandler(Network.LOG_FILE, LOG_LIMIT, LOG_COUNT, false);
			logger = Logger.getLogger(Network.TITLE);
//			logger.addHandler(writer);
//		} catch (SecurityException e) {
//		} catch (IOException e) {
//		}		
	}
	
	/**
	 * set a button to be black with white text
	 * @param button the button to color
	 */
	public static void darkenButton(JButton button) {
		button.setBackground(Color.BLACK); //black color
		button.setOpaque(true); //color every pixel
		button.setBorderPainted(false); //no border
		button.setForeground(Color.WHITE); //white text
	}
	
	/**
	 * Darken a non-button component. If the component is a button, use darkenButton instead
	 * @param component the component to color
	 */
	public static void darkenField(JComponent component) {
		component.setBackground(Color.BLACK); //black color
		component.setOpaque(true); //color every pixel
		component.setForeground(Color.WHITE); //white text
	}
	
	/**
	 * sort a set of strings alphabetically
	 * @param in the set to sort
	 */
	public static ArrayList<String> sort(Set<String> inSet) {
		ArrayList<String> list = new ArrayList<>(inSet);
		Collections.sort(list);
		return list;
	}
}
