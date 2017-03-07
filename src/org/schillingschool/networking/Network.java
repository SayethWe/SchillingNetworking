package org.schillingschool.networking;

import org.schillingschool.networking.userInterface.MainMenu;
import org.schillingschool.networking.utils.Utils;

public class Network { //the first class to get started
	public final static String VERSION_NUMBER = "0.0.01";
	public final static String VERSION_DATE = "07/03/2017";
	public final static String TITLE = "Schilling Communications Network";
	
	public static void main(String[] args) {
		//let's get MOOOOVING
		Utils.getLogger().fine("Starting");
		new MainMenu();
	}
}
