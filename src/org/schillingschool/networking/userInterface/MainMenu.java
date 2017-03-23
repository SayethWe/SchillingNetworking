package org.schillingschool.networking.userInterface;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import needed libraries and classes
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.schillingschool.networking.Network;
import org.schillingschool.networking.handlers.ClientHandler;
import org.schillingschool.networking.handlers.ServerHandler;
import org.schillingschool.utils.Utils;

/**
 * The Main Menu Interface
 * @author geekman9097
 * @version 9/3/17
 */
public class MainMenu extends JFrame implements ActionListener {
	//basic constants for use
	private final String START_CLIENT = "Start a Client";
	private final String START_SERVER = "Host a Server";

	private static JButton startClient; //A button. this one starts the client
	private static JButton startServer; //A button. this one starts the server
	
	private static GridBagConstraints constraints; //the grid constraints variable
	
	/**
	 * Create a new main menu
	 */
	public MainMenu() { //the constructor
		Utils.getLogger().fine("Main Menu Opened");
		setLayout(new GridBagLayout()); //use the gridBag layout
		constraints = new GridBagConstraints(); //init our constraints
		createLayout(); //set up our layout
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit the program on window close
		setResizable(true); //allow it to be resized
		setTitle(Network.TITLE);
		setSize(getPreferredSize()); //set the default size
		setVisible(true);
	}
	
	/**
	 * Create the layout
	 */
	private void createLayout() {
		//color the background black
		getContentPane().setBackground(Color.BLACK);
		
		//allow components to resize
		constraints.weightx = 0.5;
		constraints.weighty = 0.5; 
		constraints.fill = GridBagConstraints.NONE;
		
		//add padding
		constraints.ipadx = 5;
		constraints.ipady = 5;
		
		//load the Title Label
		JLabel title = new JLabel(Network.TITLE);
		constraints.gridy = 0; //NO spaces down
		title.setForeground(Color.WHITE); //set the text to white
		constraints.gridwidth = 2; //two boxes wide
		title.setHorizontalAlignment(SwingConstants.CENTER); //center the text
		title.setVerticalAlignment(SwingConstants.CENTER); 
		add(title, constraints);
		
		//load the client button
		startClient = new JButton(START_CLIENT);
		constraints.gridy = 1; //One space down
		constraints.gridwidth = 1; //one space wide
		constraints.anchor = GridBagConstraints.SOUTH;
		Utils.darkenButton(startClient);
		startClient.addActionListener(this); //give it something that listens to it. Like I wish my Ex had done for me. 
		add(startClient, constraints); //add in the created button
		
		//load the server button
		startServer = new JButton(START_SERVER);
		constraints.gridy = 2; //Three slots down
		constraints.anchor = GridBagConstraints.NORTH;
		Utils.darkenButton(startServer);
		startServer.addActionListener(this); //something to listen to it
		add(startServer, constraints);
		
		//add the version label
		JLabel versionNumber = new JLabel(Network.VERSION_NUMBER);
		versionNumber.setForeground(Color.WHITE);
		constraints.gridy = 3;
		versionNumber.setHorizontalAlignment(SwingConstants.CENTER); //center the text
		versionNumber.setVerticalAlignment(SwingConstants.CENTER);
		constraints.anchor = GridBagConstraints.SOUTHEAST;
		add(versionNumber, constraints);
	}
	
	/**
	 * run the client
	 */
	private void runClient() {
		new ClientHandler();
//		this.dispose();
	}
	
	/**
	 * run the server
	 */
	private void runServer() {
		new ServerHandler();
//		this.dispose();
	}

	/**
	 * when something happens
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startClient) runClient();
		if (e.getSource() == startServer) runServer();
	}
}
