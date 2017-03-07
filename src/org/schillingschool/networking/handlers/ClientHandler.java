package org.schillingschool.networking.handlers;

import org.schillingschool.networking.client.Client;
import org.schillingschool.networking.userInterface.ClientInterface;

public class ClientHandler implements Runnable{

	private Client myClient;
	private ClientInterface myGui;
	
	@Override
	public void run() {
		myClient = new Client(this);
		Thread execute = new Thread(myClient);
		execute.start();
		myGui = new ClientInterface(this);
	}
	
	public void message(String message) {
		myGui.message(message);
	}

	public void send(String message) {
		myClient.send(message);
	}

}
