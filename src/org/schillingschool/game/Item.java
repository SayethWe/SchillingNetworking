package org.schillingschool.game;

class Item {

	private String name, description;
	private int mass;
	
	Item(String name, String description, int mass) {
		this.name = name;
		this.description = description;
		this.mass = mass;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getMass() {
		return mass;
	}
	
	public String getName() {
		return name;
	}
}
