package org.schillingschool.networking.userInterface;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.schillingschool.networking.Network;
import org.schillingschool.networking.handlers.ServerHandler;
import org.schillingschool.utils.Utils;

/**
 * the interface for the server to use. Shows debug messages and will allow command entry
 * @author geekman9097
 * @version 9/3/17
 */
public class ServerInterface extends JFrame implements ActionListener, WindowListener {
	private static final String TITLE = "SCN Server";
	final private static int TEXT_HISTORY = 11;
	final private static int DEFAULT_WIDTH = 20;
	final private static String PROMPT_TEXT = "Commands";
	private static final int USER_WIDTH = 5;
	private static final String USER_TITLE = "Users:";
	
	private ServerHandler myHandler;
	private GridBagConstraints constraints;
	private JTextArea displayText;
	private JTextArea userList;
	private JTextField typeBox;

	/**
	 * Create a Server gui with the designated handler
	 * @param myHandler the handler this will receive messages from and send commands to
	 */
	public ServerInterface(ServerHandler myHandler) {
		this.myHandler = myHandler;
		setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		createLayout(); //set up our layout
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit the program on window close
		setResizable(true); //allow it to be resized
		setTitle(TITLE);
		setSize(getPreferredSize()); //set the default size
		setVisible(true);
	}
	
	/**
	 * create our server layout
	 */
	private void createLayout() {
		//allow component resizing
		constraints.weighty = 0.5;
		constraints.weightx = 0.5;
		constraints.fill = GridBagConstraints.BOTH;
		
		//add padding
		constraints.ipadx = 5;
		constraints.ipady = 5;
				
		//I see a white pane and I want to paint it black
		getContentPane().setBackground(Color.BLACK);
		
		//set up our out display
		displayText = new JTextArea(TEXT_HISTORY, DEFAULT_WIDTH);
		JScrollPane scrollPane = new JScrollPane(displayText);
//		Utils.darkenField(displayText); //darken the field
		constraints.gridy = 1;
		constraints.gridheight = 3;
		displayText.setLineWrap(true);
		displayText.setWrapStyleWord(true);
		displayText.setEditable(false);
		displayText.setText("");
		add(scrollPane, constraints);
		
		// a version label
		JLabel version = new JLabel(Network.VERSION_NUMBER);
		version.setForeground(Color.WHITE);
		constraints.gridx = 1;
		constraints.gridheight = 1;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		version.setHorizontalAlignment(SwingConstants.CENTER); //center the text
		version.setVerticalAlignment(SwingConstants.CENTER); 
		add(version, constraints);
		
		//a label for the userlist
		JLabel label = new JLabel(USER_TITLE);
		label.setForeground(Color.YELLOW);
		constraints.gridy = 2;
		label.setHorizontalAlignment(SwingConstants.CENTER); //center the text
		label.setVerticalAlignment(SwingConstants.CENTER); 
		add(label, constraints);
		
		//a display for the userlist
		userList = new JTextArea(TEXT_HISTORY, USER_WIDTH);
		JScrollPane userPane = new JScrollPane(userList);
//		Utils.darkenField(userList); //darken the field
		constraints.gridy = 3;
		constraints.weightx = 0.25;
		constraints.fill = GridBagConstraints.BOTH;
		userList.setLineWrap(true);
		userList.setWrapStyleWord(true);
		userList.setEditable(false);
		userList.setText("");
		add(userPane, constraints);
		
		//the command field
		typeBox = new JTextField(PROMPT_TEXT);
		Utils.darkenField(typeBox);
		typeBox.setEditable(true);
		typeBox.addActionListener(this);
		constraints.gridy = 4;
		constraints.gridx = 0;
		constraints.weighty = 0;
		constraints.weightx = .5;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(typeBox, constraints);
		
		//draw the title across the top
		JLabel title = new JLabel(Network.TITLE);
		title.setForeground(Color.WHITE);
		constraints.gridy = 0;
		title.setHorizontalAlignment(SwingConstants.CENTER); //center the text
		title.setVerticalAlignment(SwingConstants.CENTER); 
		add(title, constraints);
	}
	
	/**
	 * display a message on the out field
	 * @param message The message to display
	 */
	public void message(String message){
		displayText.append(message + Utils.NEWLINE); //add text
		displayText.setCaretPosition(displayText.getDocument().getLength());//jump the cursor to the end of the display to show it
	}
	
	/**
	 * update the list of users
	 */
	public void updateUsers(Set<String> handles) {
		userList.setText(""); //clear the userList
		ArrayList<String> names = Utils.sort(handles);
		names.forEach((name) -> userList.append(name + Utils.NEWLINE));
	}
	
	/**
	 * close the gui safely.
	 */
	public void close() {
		this.dispose();
	}
	
	/**
	 * When an action is done, process some code.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String message = typeBox.getText();
		typeBox.setText("");
	}

	@Override public void windowOpened(WindowEvent e) {}
	@Override public void windowClosing(WindowEvent e) {}
	@Override public void windowClosed(WindowEvent e) {
		myHandler.end();
	}
	@Override public void windowIconified(WindowEvent e) {}
	@Override public void windowDeiconified(WindowEvent e) {}
	@Override public void windowActivated(WindowEvent e) {}
	@Override public void windowDeactivated(WindowEvent e) {}
}
