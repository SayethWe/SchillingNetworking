package org.schillingschool.game;

import java.util.HashMap;
import java.util.Set;

import org.schillingschool.utils.Utils;

class Room {

	private HashMap<String, Item> items = new HashMap<>();
	private HashMap<String, Room> neighbors = new HashMap<>();
	private String description;
	
	Room(String description) {
		this.description = description;
	}
	
	public void addExit(String direction, Room neighbor) {
		neighbors.put(direction, neighbor);
	}
	
	/**
	 * Get a negihboring room
	 * @param direction the direction to check
	 * @return the room at that direction. Null if there is nothing.
	 */
	public Room getExit(String direction) {
		return neighbors.getOrDefault(direction, null);
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getLongDescription() {
		return "You are currently " + description + Utils.NEWLINE + getExitString();
	}
	
	private String getExitString() {
		String returnString = "Visible exits: ";
		Set<String> exits = neighbors.keySet();
		for(String exit : exits) {
			returnString += exit + ", ";
		}
		return returnString;
	}
	
	public void addItem(Item item) {
		items.put(item.getName(), item);
	}
	
	public void removeItem(String itemName) {
		items.remove(itemName);
	}
	
	public String getItemString() {
		String returnString = "Visible items: ";
		Set<String> items = this.items.keySet();
		if(items.size() > 0) {
			for (String item : items){
				returnString += item + ", ";
			}
			return returnString;
		} else {
			return "There are no visible items";
		}
	}
}
