package org.schillingschool.networking.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.schillingschool.networking.Network;
import org.schillingschool.utils.Utils;

/**
 * A thread to listen to a specific port
 * @author geekman9097
 * @author DMWCincy
 * @version 9/3/17
 */
class ServerListener implements Runnable {
	

	private final static String USERNAME_REQUEST = "Please enter your username";
	private final static String USERNAME_TAKEN_ERROR = "That username is already taken";
	private static final String USERNAME_SUCCESS = "Thank you. Your username for this session is: ";

	private Socket clientSock; //the socket we listen to
	private Server myServer; //the server we belong to
	private Thread t; //the thread this runs on
	private String username;
	private boolean run = true; //whether on not we should still be running
	
	/**
	 * Create a Thread for listening to a client
	 * @param clientSock the socket (and client) we're dedicated to
	 * @param myServer the server we're associated with
	 */
	ServerListener(Socket clientSock, Server myServer) {
		this.clientSock = clientSock;
		this.myServer = myServer;
	}
	
	/**
	 * listen for activity and pass it up the chain
	 */
	@Override
	public void run() {
		BufferedReader in;
		String inString;
		Utils.getLogger().fine(clientSock.toString());
		try {
			username = createUsername();
			Utils.getLogger().info("Username Created");
		} catch (IOException e1) {
			username = null;
			Utils.getLogger().severe("Username Creation Failed. Reverting to null.");
			e1.printStackTrace();
		}
		try {
			in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
			while (run) {
				message("Port-ato.");
				Utils.getLogger().info(t.getName() + " New DataLine Came In");
				inString = in.readLine();
				if(inString.equalsIgnoreCase(Network.DISCONNECT_COMMAND)) {
					end();
				}
				message(inString);
			}
		} catch (IOException e) {

		}
	}
	
	private String createUsername() throws IOException {
		boolean nameValid = false;
		String username = "";
		BufferedReader nameReader = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
		while (!nameValid) {
			myServer.directMessage(this,USERNAME_REQUEST); //ask them to enter a username
			username = nameReader.readLine(); //get a username from client data
			if (myServer.nameAvailable(username)) { //if the username isn't already taken
				myServer.directMessage(this, USERNAME_SUCCESS + username);
				nameValid = true;
			} else {
				myServer.directMessage(this, USERNAME_TAKEN_ERROR);
			}
		}
		return username;
	}
	
	/**
	 * start up this thread
	 */
	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}
	
	/**
	 * get the username associated with this client
	 * @return the username we use
	 */
	String getUsername() {
		return username;
	}
	
	/**
	 * end this thread and close its associated client socket
	 */
	public synchronized void end() {
//		try {
//			clientSock.close();
//		} catch (IOException e) {
//		}
		run = false;
	}
	
	/**
	 * send a message up to the server
	 * @param message the message to send
	 */
	private void message(String message) {
		myServer.message(username + ": " + message);
	}
	
	/**
	 * grab the socket we talk to
	 * @return the associated socket
	 */
	public Socket getClientSocket() {
		return clientSock;
	}

}
