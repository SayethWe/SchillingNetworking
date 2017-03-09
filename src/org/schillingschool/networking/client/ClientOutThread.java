package org.schillingschool.networking.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.schillingschool.networking.Network;
import org.schillingschool.networking.utils.Utils;

/**
 * The Out Thread for the client. Interfaces with the server to send messages
 * @author geekman9097
 *
 */
public class ClientOutThread implements Runnable {
	private Thread t;
	private Socket clientSock;
	private boolean run = true; //if we're still supposed to be running
	private boolean dataAvailable = false; //if the handler has sent us data recently
	private String userStr;
	private final static int NAP_TIME = 50; //how many mills to wait between data checks
	
	/**
	 * Set up an out thread for the client
	 * @param name The Thread's name
	 * @param clientSock The Socket to Connect to
	 * @param username The User's name
	 */
	public ClientOutThread(Socket clientSock, String username) {
		this.clientSock = clientSock;
	}
	
	/**
	 * Tell the thread it's time to stop working
	 */
	public synchronized void end() {
		run = false;
	}
	
	/**
	 * start this Thread
	 */
	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	/**
	 * grab and send messages from the client to the server
	 */
	@Override
	public void run() {
		try {
			PrintWriter out = new PrintWriter(clientSock.getOutputStream()); //create a writer to the server
			while (run) { //loop through this as long as we're not told to stop
				waitForData(); //await some data to send
				out.println(userStr); //send our little message to the server
				out.flush(); //flush the output stream
				if(userStr.equalsIgnoreCase(Network.DISCONNECT_COMMAND)) {
					break;
				}
			}
			out.close(); //clean up the print writer
			clientSock.close(); //clean up our socket
		} catch (IOException | InterruptedException e) {
			Utils.getLogger().severe("IO Exception: " + e);
		}
	}

	/**
	 * Send a message to the server
	 * @param message The message to send
	 */
	public void serverward(String message) {
		userStr = message;
		dataAvailable = true;
	}
	
	/**
	 * wait for there to be a message about data arriving
	 * @throws InterruptedException 
	 */
	private synchronized void waitForData() throws InterruptedException {
		while(!dataAvailable) { //as long as there's no data...
			Thread.sleep(NAP_TIME); //take a little nap
		}
		Utils.getLogger().info("Data is ready");
		dataAvailable = false;
	}

}
