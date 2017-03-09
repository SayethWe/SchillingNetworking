package org.schillingschool.networking.handlers;

import org.schillingschool.networking.client.Client;
import org.schillingschool.networking.userInterface.ClientInterface;

/**
 * The handler for the client side. Handles messages between gui and Client
 * Only object with knowledge of both the client branch and the gui branch
 * @author geekman9097
 *
 */
public class ClientHandler{

	private Client myClient;
	private ClientInterface myGui;
	
	/**
	 * run us off a new client side.
	 */
	public ClientHandler() {
		myGui = new ClientInterface(this);
		myClient = new Client(this);
		Thread execute = new Thread(myClient);
		execute.start();
	}
	
	/**
	 * a message to the gui
	 * @param message the message to send
	 */
	public void userward(String message) {
		myGui.message(message);
	}

	/**
	 * a message to the client
	 * @param message the message to send
	 */
	public void serverward(String message) {
		myClient.serverward(message);
	}

}
