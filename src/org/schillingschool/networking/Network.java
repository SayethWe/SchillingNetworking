package org.schillingschool.networking;

import org.schillingschool.game.Game;
import org.schillingschool.networking.handlers.ClientHandler;
import org.schillingschool.networking.handlers.ServerHandler;
import org.schillingschool.utils.Date;
import org.schillingschool.utils.Utils;

/**
 * The network class. Main Class to start, and the basis for the whole project.
 * Stores Project-wide constants
 * Contains the main() method to start the program
 * @author geekman9097
 * @version 9/3/17
 */
public class Network { //the first class to get started
	public final static String VERSION_NUMBER = "0.0.3"; //the version number. Release.Major.Minor
	public final static Date VERSION_DATE = new Date(21,03,17); //the date this version was released. DD/MM/YYYY
	public final static String TITLE = "Schilling Communications Network"; //the title of our project
	public final static int DEFAULT_PORT = 3333; //the default port for receiving connections
	public final static String DISCONNECT_COMMAND = "/Server Disconnect";
	public final static int HANDSHAKE_LENGTH = 256; //how long our ping and pong byte arrays should be
	public final static String LOG_FILE = "../logs/Network.log";
	
	/**
	 * Start our program
	 * @param args
	 */
	public static void main(String[] args) {
		//Let's get MOOOOVING
		new Game().play();
		Utils.getLogger().fine("Starting");
//		new MainMenu();
		new ServerHandler();
		new ClientHandler();
		new ClientHandler();
	}
}
