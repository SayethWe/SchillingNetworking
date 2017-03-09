package org.schillingschool.networking;

import org.schillingschool.networking.userInterface.MainMenu;
import org.schillingschool.networking.utils.Utils;

/**
 * The network class. Main Class to start, and the basis for the whole project.
 * Stores Project-wide constants
 * Contains the main() method to start the program
 * @author geekman9097
 *
 */
public class Network { //the first class to get started
	public final static String VERSION_NUMBER = "0.0.2"; //the version number. Release.Major.Minor
	public final static String VERSION_DATE = "08/03/2017"; //the date this version was released. DD/MM/YYYY
	public final static String TITLE = "Schilling Communications Network"; //the title of our project
	public final static int DEFAULT_PORT = 3333; //the default port for receiving connections
	public final static String DISCONNECT_COMMAND = "/Server Disconnect";
	public final static int HANDSHAKE_LENGTH = 256; //how long our ping and pong byte arrays should be
	
	/**
	 * Start our program
	 * @param args
	 */
	public static void main(String[] args) {
		//Let's get MOOOOVING
		Utils.getLogger().fine("Starting");
		new MainMenu();
	}
}
