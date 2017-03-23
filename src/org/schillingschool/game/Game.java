package org.schillingschool.game;

import org.schillingschool.utils.Utils;

public class Game {
	
	private final static String TITLE = "The Schilling Spaceship";
	private final static String HELP_TEXT = "Type \"HELP\" for a list of commands";
	private final static String WELCOME_MESSAGE = "Welcome to " + TITLE + Utils.NEWLINE
												+ TITLE + "is a new game, designed just for giggles" + Utils.NEWLINE
												+ "The aim is to get from the bridge of yhour spaceship to the Airlock and escape" + Utils.NEWLINE
												+ HELP_TEXT;
	
	private Room currentRoom;
	
	public Game() {
		createMap();
	}

	private void createMap() {
		Room airlock, bridge, viewscreen; 
		Item key;
		
		airlock = new Room("in the Ship's Airlock.");
		bridge = new Room("in the Bridge of the ship.");
		viewscreen = new Room("Looking out the main veiwscreen. It's completely dark.");
		
		key = new Item("Key", "a rusty, old key", 5);
		
		airlock.addExit("fore", bridge);
		airlock.addItem(key);
		
		bridge.addExit("aft", airlock);
		bridge.addExit("fore", viewscreen);
		
		viewscreen.addExit("back", bridge);
		
		currentRoom = bridge;
	}
	
	public void play() {
		boolean run = false; //a boolean to check that we should still be running
		printWelcome(); //print the text to welcome the player
		while(run) {
			
		}
	}
	
	private void printWelcome() {
		printMessage(Utils.FANCY_LINE);
		printMessage(WELCOME_MESSAGE);
		printMessage(Utils.FANCY_LINE);
		printMessage(currentRoom.getLongDescription());
	}
	
	/**
	 * Print a message line. implemented for ease of transfer between gui and console
	 */
	private static void printMessage(String message) {
		System.out.println(message);
	}
}
