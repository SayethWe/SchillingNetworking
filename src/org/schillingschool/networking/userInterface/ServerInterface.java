package org.schillingschool.networking.userInterface;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.schillingschool.networking.handlers.ServerHandler;
import org.schillingschool.networking.utils.Utils;

/**
 * the interface for the server to use. Shows debug messages and will allow command entry
 * @author geekman9097
 *
 */
public class ServerInterface extends JFrame implements ActionListener {
	private static final String NEW_LINE = "\n";
	private static final String TITLE = "SCN Client";
	final private static int TEXT_HISTORY = 11;
	final private static int DEFAULT_WIDTH = 20;
	final private static String PROMPT_TEXT = "Commands";
	private static final int USER_WIDTH = 10;
	
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
	public void createLayout() {
		//allow component resizing
		constraints.weighty = 0.5;
		constraints.weightx = 0.5;
		constraints.fill = GridBagConstraints.BOTH;
				
		//I see a white pane and I want to paint it black
		getContentPane().setBackground(Color.BLACK);
		
		//set up our out display
		displayText = new JTextArea(TEXT_HISTORY, DEFAULT_WIDTH);
		JScrollPane scrollPane = new JScrollPane(displayText);
		Utils.darkenField(displayText); //darken the field
		displayText.setLineWrap(true);
		displayText.setWrapStyleWord(true);
		displayText.setEditable(false);
		displayText.setText("");
		add(scrollPane, constraints);
		
		//a display for the userlist
		userList = new JTextArea(TEXT_HISTORY, USER_WIDTH);
		JScrollPane userPane = new JScrollPane(userList);
		Utils.darkenField(userList); //darken the field
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
		constraints.gridy = 1;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(typeBox, constraints);
	}
	
	/**
	 * display a message on the out field
	 * @param message THe message to display
	 */
	public void message(String message){
		displayText.append(message + NEW_LINE); //add text
		displayText.setCaretPosition(displayText.getDocument().getLength());//jump the cursor to the end of the display to show it
	}
	
	/**
	 * send message towards the server
	 * @param message the message to send
	 */
	public void command(String message) {
	}
	
	/**
	 * update the list of users
	 */
	public void updateUsers(Set<String> handles) {
		userList.setText(""); //clear the userList
		ArrayList<String> names = Utils.sort(handles);
		names.forEach((name) -> userList.append(name + NEW_LINE));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String message = typeBox.getText();
		typeBox.setText("");
		command(message);
	}
}
