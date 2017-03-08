package org.schillingschool.networking.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.schillingschool.networking.Network;
import org.schillingschool.networking.handlers.ClientHandler;
import org.schillingschool.networking.utils.Utils;

/**
 * A client that sets itself up, then functions as an I/O for messages
 * @author geekman9097
 * @author DMWCincy
 */
public class Client implements Runnable {

	private final static String FAILURE_MESSAGE = "Client failed while attempting to start up."; //a default failure message
	private final static int TIMEOUT = 10000; //the number of millis in each timeout try
	private final static int NAP_TIME = 50; //how many mills to wait between data checks
	
	private ClientHandler myHandler; //the handler we interface with
	private String userStr; //the string set by the handler
	private boolean started = false; //if we're finished starting
	private boolean dataAvailable = false; //if the handler has sent us data recently
	private ClientInThread in;
	private ClientOutThread out;
	
	/**
	 * Create a client with a specified handler
	 * @param myHandler the handler we interface with
	 */
	public Client(ClientHandler myHandler) {
		this.myHandler = myHandler;
	}

	/**
	 * run the client thread
	 */
	@Override
	public void run() {
		try {
			startUp();
		} catch (Exception e) {
			serverward(FAILURE_MESSAGE + e);
		}
	}
	
	/**
	 * Start up the client, grabbing information on the way.
	 * @throws IOException
	 * @throws UnknownHostException
	 * @throws InterruptedException
	 */
	private void startUp() throws IOException, UnknownHostException, InterruptedException {
		InetAddress hostAddr;
		String username = "";
		String hostPort = null;

		boolean connection = true;
		//Initiate handshake with server to get an open port to bind to
		byte[] Ping = new byte[256];

		//Get server address from client
		getInfo("Enter Host Adress");
		hostAddr = InetAddress.getByName(userStr);
		Utils.getLogger().info("Host received, attempting connection");

		//Try to connect 3 times before giving up
		for(int i = 0; i < 3; i++){
			userward("Try" + (i + 1));

			connection = true; //If a connection has been established, exit loop

			DatagramSocket dataSock = new DatagramSocket(); //Create socket on first available port
			DatagramPacket dataPack = new DatagramPacket(Ping, Ping.length, hostAddr, Network.DEFAULT_PORT); //Create a packet to the host address (specified earlier), and port (pre-specified as the port to accept requests)

			dataSock.send(dataPack); //Send packet to server requesting connection port

			dataPack = new DatagramPacket(Ping, Ping.length); //reuse the packet used to send the request to wait for reply
			dataSock.setSoTimeout(TIMEOUT); //specify how long until we consider it a timeout

			try { //wait 10 seconds for reply from server, if no reply, try again
				dataSock.receive(dataPack);
				hostPort = new String(dataPack.getData(), 0, dataPack.getLength());//Convert byte array into a port number
			}catch (Exception e){
				dataSock.close();
				userward("Try " + (i + 1) + " failed");
				connection = false;
			}
			if(connection == true){ //If port number returned, exit the loop and establish connection
				break;
			}
		}
		if(hostPort == null){ //assurance that we don't try to bind to a null port
			userward("Server not responding, terminating...");
			System.exit(0);
		}
		userward("Host port: " + hostPort);

		Socket clientSock = new Socket(hostAddr, Integer.parseInt(hostPort)); //Establish connection with server


		getInfo("Please enter username:"); //Get user name (will be implemented later)
		username = userStr;
		userward("Connected. You can now send messages, type '/Server disconnect' to disconnect");
		//Start communication Thread

		out = new ClientOutThread("Client Out", clientSock, username);
		out.start(); //start out thread
		in = new ClientInThread("Client in", clientSock, this);
		in.start(); //start in thread
		
		started = true;
	}
	
	/**
	 * send a message to the handler
	 * @param message the message to send
	 */
	public void userward(String message) {
		myHandler.userward(message);
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
	
	/**
	 * send the user a message, then wait for a response.
	 * @param prompt what to ask the user with
	 * @throws InterruptedException 
	 */
	private void getInfo(String prompt) throws InterruptedException {
		userward(prompt);
		waitForData();
	}
	
	/**
	 * End the client program by shutting down all the listeners
	 * 
	 */
	public synchronized void end() {
		out.end();
//		in.end();
	}
	
	/**
	 * receive a message from the handler
	 * @param message the message to send
	 */
	public void serverward(String message) {
		if(started) out.serverward(message);
		userStr = message;
		dataAvailable = true;
		Utils.getLogger().info("message received, jefe");
	}
}
