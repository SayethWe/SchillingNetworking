package org.schillingschool.networking.client;

import java.io.IOException;
import java.net.UnknownHostException;

import org.schillingschool.networking.handlers.ClientHandler;

public class Client implements Runnable {

	private static final String FAILURE_MESSAGE = "Client failed while attempting to start up.";
	private ClientHandler myHandler;
	
	public Client(ClientHandler myHandler) {
		this.myHandler = myHandler;
	}

	@Override
	public void run() {
		try {
			startUp();
		} catch (Exception e) {
			message(FAILURE_MESSAGE + e.toString());
		}
	}
	
	private void startUp() throws IOException, UnknownHostException {
		
	}
	
	/**
	 * send a message from the client to the handler
	 * @param message the message to send
	 */
	public void send(String message) {
		myHandler.message(message);
	}
	
	/**
	 * receive a message from the handler
	 * @param message the message to send
	 */
	public void receive(String message) {

	}
}
