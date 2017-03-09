package org.schillingschool.networking.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComponent;

import org.schillingschool.networking.Network;

/**
 * the utilities class. Contains many useful things.
 * Holds the logger for this program
 * Can set Gui Objects to black
 * @author geekman9097
 *
 */
public class Utils {
	private static Logger logger; //our logger for the program
	
	/**
	 * get the logger this program uses
	 * @return the logger
	 */
	public static Logger getLogger() {
		if (logger == null) logger = Logger.getLogger(Network.TITLE);
		return logger;
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
