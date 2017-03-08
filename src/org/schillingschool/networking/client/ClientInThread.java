package org.schillingschool.networking.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.schillingschool.networking.Network;

/**
 * The In Thread for the Client. Interfaces with the Server to receive messages.
 * @author geekman9097
 *
 */
public class ClientInThread implements Runnable {

	private final static String DISCONNECT_MESSAGE = "Server Disconnected, Terminating Processes...";
	
	private Thread t;
	private String name;
	private Socket clientSock;
	private Client client;
	private boolean run = true; //if we're still supposed to be running
	
	/**
	 * 
	 * @param name The thread name
	 * @param clientSock the socket on which the server resides
	 * @param client the client we pass information on to
	 */
	public ClientInThread(String name, Socket clientSock, Client client) {
		this.name = name;
		this.clientSock = clientSock;
		this.client = client;
	}

	/**
	 * grab and send messages from server to client
	 */
	@Override
	public void run() {
		BufferedReader in;
		String inStr;
		try {
			in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
			while (run) {
				inStr = in.readLine();
				userward(inStr);
				if(inStr.equalsIgnoreCase(Network.DISCONNECT_COMMAND)) {
					userward(DISCONNECT_MESSAGE);
					client.end();
				}
			}
			in.close();
		} catch (IOException e) {
		}
	}
	
	/**
	 * send a string to the client
	 * @param message the message to send
	 */
	private void userward(String message) {
		client.userward(message);
	}
	
	/**
	 * Tell the thread it's time to stop working
	 */
	public synchronized void end() {
		run = false;
	}
	
	/**
	 * start this thread
	 */
	public void start() {
		if (t == null) {
			t = new Thread(this, name);
			t.start();
		}
	}

}
