package org.schillingschool.networking.handlers;

import java.util.Set;

import org.schillingschool.networking.server.Server;
import org.schillingschool.networking.userInterface.ServerInterface;

/**
 * a handler for the server side. Handles messages between the gui and server.
 * May also handle the game in the future
 * Only object with knowledge of both (or all three, maybe)
 * @author geekman9097
 * @version 9/3/17
 */
public class ServerHandler {

private Server myServer;
private ServerInterface myGui;

//	private Server myServer;
//	private ServerInterface myGui;
	
	/**
	 * run us off a new server side.
	 */
	public ServerHandler() {
		myGui = new ServerInterface(this);
		myServer = new Server(this);
		myServer.start();
	}
	
	/**
	 * send a message to the gui
	 * @param message the message to send
	 */
	public void guiward(String message) {
		myGui.message(message);
	}
	
	/**
	 * send a message to the gui to update the user list
	 * @param handles the set to eventually be shown
	 */
	public void updateUsers(Set<String> handles) {
		myGui.updateUsers(handles);
	}
	
	/**
	 * end the server associated with this and close the gui
	 */
	public void end() {
		myServer.end();
		myGui.close();
	}
}
