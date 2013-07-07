package com.gromholl.mongodb.entity;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Customer {
	
	private String number;
	private String name;
	private String address;
	private Integer cash;
	
	private static final String numberStr = "number";
	private static final String nameStr = "name";
	private static final String addressStr = "address";
	private static final String cashStr = "cash";
	
	public Customer(String number, String name, String adress, Integer cash) {
		this.number = number;
		this.name = name;
		this.address = adress;
		this.cash = cash;
	}
	
    public BasicDBObject toDBObject() {
        BasicDBObject document = new BasicDBObject();
        
        document.put(numberStr, number);
        document.put(nameStr, name);
        document.put(addressStr, address);
        document.put(cashStr, cash);
        
        return document;
    }
    
    public static Customer fromDBObject(DBObject document) {
    	Customer customer = new Customer(
    			(String) document.get(numberStr),
    			(String) document.get(nameStr),
    			(String) document.get(addressStr),
    			(Integer) document.get(cashStr));        
        return customer;
    }
	
	public String getNumber() {
		return number;
	}
	
	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}
	
	public Integer getCash() {
		return cash;
	}
	
}
