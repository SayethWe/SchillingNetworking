package org.schillingschool.networking.utils;

public class Utils {
	private static final String INDENT = "    "; //four space to indent
	private static final String NEWLINE = "\n"; //a new line
	
	public static void log(Object sender, String message) { //log a message. Must hand it "this" and a message
		System.out.println(sender.toString() + NEWLINE + INDENT + "says: " + message);
	}
	
	public static void log(String message) {//a faster way to print line
		System.out.println(message);
	}
	
	public static void error(int severity, String message) {
		String errorText;
		switch (severity){
		case 0:
			errorText = "[ERROR]";
			break;
		case 1:
			errorText = "[SEVERE]";
			break;
		default:
			errorText = "[ALERT]";
			break;
		}
		System.out.println(errorText + " " + message);
	}
	
	public static void error(Object sender, int severity, String message){
		System.out.print(sender.toString() + " Says: ");
		error(severity, message);
	}
}
