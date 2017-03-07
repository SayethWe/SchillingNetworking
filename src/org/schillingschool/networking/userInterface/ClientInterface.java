package org.schillingschool.networking.userInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.schillingschool.networking.handlers.ClientHandler;

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
	
	private void createLayout() {
		constraints.weighty = 0.5;
		constraints.weightx = 0.5;
		
		displayText = new JTextArea(TEXT_HISTORY, DEFAULT_WIDTH);
		JScrollPane scrollPane = new JScrollPane(displayText);
		displayText.setLineWrap(true);
		displayText.setWrapStyleWord(true);
		displayText.setEditable(false);
		displayText.setText("");
		constraints.fill = GridBagConstraints.BOTH;
		add(scrollPane, constraints);

		typeBox = new JTextField(PROMPT_TEXT);
		typeBox.setEditable(true);
		typeBox.addActionListener(this);
		constraints.gridy = 1;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(typeBox, constraints);
	}

	public void message(String message){
		displayText.append(message + NEW_LINE);
		displayText.setCaretPosition(displayText.getDocument().getLength());//jump the cursor to the end of the display to show it
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String message = typeBox.getText();
		typeBox.setText("");
		myHandler.send(message);
	}
}
