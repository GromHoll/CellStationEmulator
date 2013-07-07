package com.gromholl.mongodb.entity;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


public class Call {
	
	private String from;
	private String to;
	private Integer duration;
	private Integer price;
	
	private static final String fromStr = "from";
	private static final String toStr = "to";
	private static final String durationStr = "duration";
	private static final String priceStr = "price";
	
	public Call(String from, String to, int duration, Integer price) {
		this(from, to, duration);
		this.price = price;
	}
	
	public Call(String from, String to, Integer duration) {
		this.from = from;
		this.to = to;
		this.duration = duration;
	}
	
    public BasicDBObject toDBObject() {
        BasicDBObject document = new BasicDBObject();
        
        document.put(fromStr, from);
        document.put(toStr, to);
        document.put(durationStr, duration);
        document.put(priceStr, price);
        
        return document;
    }
    
    public static Call fromDBObject(DBObject document) {
    	Call call = new Call(
    			(String) document.get(fromStr),
    			(String) document.get(toStr),
    			(Integer) document.get(durationStr),
    			(Integer) document.get(priceStr));        
        return call;
    }
	
	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public Integer getDuration() {
		return duration;
	}

	public Integer getPrice() {
		return price;
	}
	
	public void setPrice(Integer price) {
		this.price = new Integer(price);
	}
	
}
