package org.schillingschool.networking.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.schillingschool.networking.Network;

public class ServerInThread implements Runnable {

	private Socket clientSock; //the socket we listen to
	private Server myServer; //the server we belong to
	private Thread t; //the thread this runs on
	private boolean run = true; //whether on not we should still be running
	
	/**
	 * Create a Thread for listening to a client
	 * @param clientSock the socket (and client) we're dedicated to
	 * @param myServer the server we're associated with
	 */
	public ServerInThread(Socket clientSock, Server myServer) {
		this.clientSock = clientSock;
		this.myServer = myServer;
	}
	
	/**
	 * listen for activity and pass it up the chain
	 */
	@Override
	public void run() {
		while (run) {
			String inString;
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
				
				inString = in.readLine();
				if(inString.equalsIgnoreCase(Network.DISCONNECT_COMMAND)) {
					myServer.closeSocket(clientSock);
					end();
				}
				message(inString);
			} catch (IOException e) {
			}
		}
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
	 * end this thread and close its associated client socket
	 */
	public synchronized void end() {
		try {
			clientSock.close();
		} catch (IOException e) {
		}
		run = false;
	}
	
	/**
	 * send a message up to the server
	 * @param message the message to send
	 */
	private void message(String message) {
		myServer.message(message, this);
	}
	
	/**
	 * grab the socket we talk to
	 * @return the associated socket
	 */
	public Socket getClientSocket() {
		return clientSock;
	}

}
