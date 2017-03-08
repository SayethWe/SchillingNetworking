package org.schillingschool.networking.userInterface;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.schillingschool.networking.handlers.ClientHandler;
import org.schillingschool.networking.utils.Utils;

/**
 * The interface for our client
 * receives user text and displays other text
 * @author geekman9097
 *
 */
public class ClientInterface extends JFrame implements ActionListener{

	private static final String NEW_LINE = "\n";
	private static final String TITLE = "SCN Client";
	final private static int TEXT_HISTORY = 10;
	final private static int DEFAULT_WIDTH = 20;
	final private static String PROMPT_TEXT = "Type a message Here";
	
	
	private ClientHandler myHandler;
	private JTextArea displayText;
	private JTextField typeBox;
	GridBagConstraints constraints;
	
	/**
	 * Create a new Client Interface
	 * @param myHandler the handler we talk with.
	 */
	public ClientInterface(ClientHandler myHandler) {
		this.myHandler = myHandler; //set the handler
		setLayout(new GridBagLayout()); //use the gridBag layout
		constraints = new GridBagConstraints(); //init our constraints
		createLayout(); //set up our layout
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit the program on window close
		setResizable(true); //allow it to be resized
		setTitle(TITLE);
		setSize(getPreferredSize()); //set the default size
		setVisible(true);
	}
	
	/**
	 * set our layout up
	 */
	private void createLayout() {
		//allow component resizing
		constraints.weighty = 0.5;
		constraints.weightx = 0.5;
		constraints.fill = GridBagConstraints.BOTH;
		
		//set it to black
		getContentPane().setBackground(Color.BLACK);
		
		displayText = new JTextArea(TEXT_HISTORY, DEFAULT_WIDTH);
		JScrollPane scrollPane = new JScrollPane(displayText);
		Utils.darkenField(displayText); //darken the field
		displayText.setLineWrap(true);
		displayText.setWrapStyleWord(true);
		displayText.setEditable(false);
		displayText.setText("");
		add(scrollPane, constraints);

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
	 * display a message to the user
	 * @param message
	 */
	public void message(String message){
		displayText.append(message + NEW_LINE);
		displayText.setCaretPosition(displayText.getDocument().getLength());//jump the cursor to the end of the display to show it
	}

	/**
	 * when something gets activated
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String message = typeBox.getText();
		typeBox.setText("");
		myHandler.serverward(message);
	}
}
