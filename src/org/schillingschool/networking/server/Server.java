package org.schillingschool.networking.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.schillingschool.networking.Network;
import org.schillingschool.networking.handlers.ServerHandler;

//TODO have the server store usernames and pre-rend them
//TODO therefore, server must get username

/**
 * The server class. Handles receiving connections and acts as a handler for incoming/outgoing messages
 * @author geekman9097
 * @author DMWCincy
 */
public class Server implements Runnable {

	private final static String REQUEST_MESSAGE = "Client request detected.";
	private final static String NEWLINE = "\n";// a constant to make printing new lines easier
	private final static String CONNECT_MESSAGE = "Client Connected." + NEWLINE + "==--=^=--==";
	private final static int SOCKET_TIMEOUT = 5000; //the number of mills before we say a socket has timed out
	
	private Thread t;
	private ServerHandler myHandler;
	private boolean run = true; //whether or not we should be moving 
	private ArrayList<Socket> sockets = new ArrayList<>();
	private HashMap<String, ServerInThread> inThreads = new HashMap<>();//Store a user's name and associated thread
	
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
		int generatedName = 0;
		while(run) { //loop until we're told not to
			InetAddress conAddr; //address of person attempting to connect
			int conPort; //port of person attempting to connect
			byte[] Pong = new byte[Network.HANDSHAKE_LENGTH]; //byte array for packages
			ServerSocket servSock;
			Socket clientSock;
			ServerInThread inThread;
			try {
				DatagramSocket conSock = new DatagramSocket(Network.DEFAULT_PORT); //Create a socket to wait for connection requests
				DatagramPacket conPack = new DatagramPacket(Pong, Pong.length); //Create package to wait for connection requests
			
				conSock.receive(conPack); //wait for connection requests
				
				//Package has been received
				conAddr = conPack.getAddress(); //Get address of requestee
				conPort = conPack.getPort(); //Get port of requestee
				
				guiward(REQUEST_MESSAGE +  NEWLINE + "Client addr: " + conAddr + NEWLINE + "Client port: " + conPort);
				
				servSock = new ServerSocket(0);
				
				Pong = Integer.toString(servSock.getLocalPort()).getBytes();
				// ^^^ Take the ServerSocket we just created port number, convert to string
				//			convert said string into byte array.
				
				conPack = new DatagramPacket(Pong, Pong.length, conAddr, conPort); //create package with port number
				conSock.send(conPack); //send package with port number
				conSock.close(); //close the default socket to prevent resource leaking
				
				servSock.setSoTimeout(SOCKET_TIMEOUT);
				clientSock = servSock.accept();
				
				// we've not crashed yet, so the client must be connected
				guiward(CONNECT_MESSAGE);
				
				inThreads.put(new Integer(generatedName).toString(), inThread = new ServerInThread(clientSock, this));
				inThread.start();
				updateUsers(inThreads.keySet());
				generatedName++;
				
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
		inThreads.forEach((name, inThread) -> inThread.end()); //Lambda. for each inThread, end it;);
	}
	
	/**
	 * pass a message to the handler
	 * @param message the message to send
	 */
	public void guiward(String message) {
		myHandler.guiward(message);
	}
	
	/**
	 * Close a client socket
	 * @param socket the socket to close
	 * @throws IOException 
	 */
	public void closeSocket(Socket socket) throws IOException{
		sockets.remove(socket);
		socket.close();
	}
	
	/**
	 * Send a message to all connected clients
	 * @param message the message to send
	 */
	public void clientward(String message) {
		for (Socket thisSocket : sockets) {
			PrintWriter out;
			try {
				out = new PrintWriter(thisSocket.getOutputStream());
				out.println(message);
				out.close(); //flush the buffer, then close it to save resources
			} catch (IOException e) {
			}
		}
	}

	/**
	 * process a command
	 * @param message
	 */
	public void command(String message) {
		
	}
	
	/**
	 * handles a message from a client thread
	 * @param message the message to process
	 * @param messenger the thread that sent the message
	 */
	public void message(String message, ServerInThread messenger) {
		inThreads.forEach((name, inThread) -> { //lambda. for each...
			if (inThread.equals(messenger)) { //...ServerInThread, see if it sent this If it did-
				clientward(name + ": " + message); //-print the ...name + the message
				guiward(name + ": " + message);
			}
		});
	}
	
	/**
	 * send a message to the handler to update the user list
	 * should be updated whenever:
	 * 	we get a new client
	 * 	A client disconnects
	 * @param handles the set to eventually be shown
	 */
	public void updateUsers(Set<String> handles) {
		myHandler.updateUsers(handles);
	}
	
}
