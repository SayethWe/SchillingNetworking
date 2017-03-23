package org.schillingschool.networking.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.schillingschool.networking.Network;
import org.schillingschool.networking.handlers.ServerHandler;
import org.schillingschool.utils.Utils;

/**
 * The server class. Handles receiving connections and acts as a handler for incoming/outgoing messages
 * @author geekman9097
 * @author DMWCincy
 * @version 9/3/17
 */
public class Server implements Runnable {

	private final static String REQUEST_MESSAGE = "Client request detected.";
	private final static String CONNECT_MESSAGE = "Client Connected." + Utils.NEWLINE + Utils.FANCY_LINE;
	private final static int SOCKET_TIMEOUT = 5000; //the number of mills before we say a socket has timed out
	
	private Thread t;
	private final ServerHandler myHandler;
	private boolean run = true; //whether or not we should be moving
	private ArrayList<ServerListener> listeners = new ArrayList<>();//Store a user's name and associated thread
	
	/**
	 * Start an object to receive connections
	 * @param myServer the server we send connections to
	 */
	public Server(ServerHandler myHandler) {
		this.myHandler = myHandler;
	}
	
	/**
	 * Run the thread to receive connections
	 */
	@Override
	public void run() {
		while(run) { //loop until we're told not to
			InetAddress conAddr; //address of person attempting to connect
			int conPort; //port of person attempting to connect
			byte[] Pong = new byte[Network.HANDSHAKE_LENGTH]; //byte array for packages
			ServerSocket servSock;
			try {
				DatagramSocket conSock = new DatagramSocket(Network.DEFAULT_PORT); //Create a socket to wait for connection requests
				DatagramPacket conPack = new DatagramPacket(Pong, Pong.length); //Create package to wait for connection requests
			
				conSock.receive(conPack); //wait for connection requests
				
				//Package has been received
				conAddr = conPack.getAddress(); //Get address of requestee
				conPort = conPack.getPort(); //Get port of requestee
				
				guiward(REQUEST_MESSAGE +  Utils.NEWLINE + "Client addr: " + conAddr + Utils.NEWLINE + "Client port: " + conPort);
				
				servSock = new ServerSocket(0);
				
				Pong = Integer.toString(servSock.getLocalPort()).getBytes();
				// ^^^ Take the ServerSocket we just created port number, convert to string
				//			convert said string into byte array.
				
				conPack = new DatagramPacket(Pong, Pong.length, conAddr, conPort); //create package with port number
				conSock.send(conPack); //send package with port number
				conSock.close(); //close the default socket to prevent resource leaking
				
				servSock.setSoTimeout(SOCKET_TIMEOUT);
				
				// we've not crashed yet, so the client must be connected
				guiward(CONNECT_MESSAGE);
				
				ServerListener client = new ServerListener(servSock.accept(),this);
				listeners.add(client);
				client.start();
				updateUsers();
			} catch (IOException e) {
			}
		}
		
	}
	
	/**
	 * Start this Thread
	 */
	public void start() {
		if (t == null) { //if no thread exists
			t = new Thread(this); //create a thread
			t.start(); //start the thread
		}
	}
	
	/**
	 * end this and its dependents
	 */
	public synchronized void end() {
		run = false;
		listeners.forEach((inThread) -> inThread.end()); //Lambda. for each inThread, end it;);
	}
	
	/**
	 * pass a message to the handler
	 * @param message the message to send
	 */
	private void guiward(String message) {
		myHandler.guiward(message);
	}
	
	/**
	 * Send a message to all connected clients
	 * @param message the message to send
	 */
	private void clientward(String message) {
		listeners.forEach((inThread) -> {
			PrintWriter out;
			try {
				out = new PrintWriter(inThread.getClientSocket().getOutputStream());
				out.println(message);
				out.close();
			} catch (IOException e) {
			}
		});
	}
	
	/**
	 * handles a message from a client thread
	 * @param message the message to process
	 * @param messenger the thread that sent the message
	 */
	void message(String message) {
		clientward(message);
	}
	
	/**
	 * Send a message to a specific client
	 * @param message the message to send 
	 * @param recipient who to send the message to
	 * @throws IOException 
	 */
	void directMessage(ServerListener recipient, String message) throws IOException {
		PrintWriter out = new PrintWriter(recipient.getClientSocket().getOutputStream());
		out.println(message);
	}
	
	/**
	 * send a message to the handler to update the user list
	 * should be updated whenever:
	 * 	we get a new client
	 * 	A client disconnects
	 * @param handles the set to eventually be shown
	 */
	private void updateUsers() {
		Set<String> handles = new HashSet<>();
		listeners.forEach((inThread) -> handles.add(inThread.getUsername()));
		myHandler.updateUsers(handles);
	}
	
	/**
	 * check if a name is already taken
	 * @param username the name to check against
	 * @return whether or not the name is taken
	 */
	boolean nameAvailable(String username) {
		boolean available = true;
		for (ServerListener inThread : listeners) {
			if(username.equals(inThread.getUsername())) available = false;
		}
		return available;
	}
}
