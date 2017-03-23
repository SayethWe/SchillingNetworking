package org.schillingschool.utils;

public class Date {

	private int year;
	private int month;
	private int day;
	
	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	@Override
	public String toString() {
		return day + "/" + month + "/" + year;
	}
}
