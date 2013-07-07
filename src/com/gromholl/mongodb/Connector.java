package com.gromholl.mongodb;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gromholl.mongodb.entity.Call;
import com.gromholl.mongodb.entity.Customer;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class Connector {
	
	private String address;
	private String dbname;
	private int port;
	
	private Mongo m;
	private DB db;
	private DBCollection callColl;
	private DBCollection customerColl;
	private DBCollection tariffColl;
	
	private List<Call> calls;
	private List<Customer> customers;
	private HashMap<String, Integer> tariffs;
	
	private static final String callCollName = "call";
	private static final String customerCollName = "customer";
	private static final String tariffCollName = "tariff";
	
	private static final String fromStr = "from";
	private static final String toStr = "to";
	private static final String durationStr = "duration";
	private static final String priceStr = "price";
	private static final String numberStr = "number";
	private static final String nameStr = "name";
	private static final String addressStr = "address";
	private static final String cashStr = "cash";	
	private static final String typeStr = "type";	
	
	private static final String internalTariffName = "internal";
	private static final String inputTariffName = "input";
	private static final String outputTariffName = "output";

	
	public Connector(String address, int port, String dbname) {
		this.address = address;
		this.dbname = dbname;
		this.port = port;
		
		calls = new ArrayList<Call>();
		customers = new ArrayList<Customer>();
		
		tariffs = new HashMap<String, Integer>();
		tariffs.put(internalTariffName, 0);
		tariffs.put(inputTariffName, 0);
		tariffs.put(outputTariffName, 0);
	}
	
	public void connect() throws UnknownHostException, MongoException {
		m = new Mongo(address, port);
		db = m.getDB(dbname);
		callColl = db.getCollection(callCollName);
		customerColl = db.getCollection(customerCollName);
		tariffColl = db.getCollection(tariffCollName);
	}
	
	public void save(Call call) throws Exception {	
        if(call.getFrom().equals(call.getTo()))
        	throw new Exception("From number and to number equals.");
        
        Integer price;        
        String ourFromNumber = null;
        String ourToNumber = null;
        
        if( hasThisNumber(call.getFrom()) ) {
        	ourFromNumber = call.getFrom();
            if( !isActive(call.getFrom()) )
            	throw new Exception("Number is block");
            if( hasThisNumber(call.getTo()) ) {
            	ourToNumber = call.getTo();
            	price = tariffs.get(internalTariffName);
            } else {
            	price = tariffs.get(outputTariffName);
            }
        } else {
        	if( hasThisNumber(call.getTo()) ) {
        		ourToNumber = call.getTo();
        		price = tariffs.get(inputTariffName);
        	} else {
        		throw new Exception("Not our numbers");
        	}
        }        
        
        Integer duration =  call.getDuration()/60;
        if(call.getDuration()%60 != 0)
        	duration++;
        
        BasicDBObject doc = new BasicDBObject();
        doc.put(fromStr,     call.getFrom());
        doc.put(toStr,       call.getTo());
        doc.put(durationStr, call.getDuration());
        doc.put(priceStr,  duration*price);

        callColl.insert(doc);
        call.setPrice(duration*price);
        calls.add(call);
        
        if(ourFromNumber != null) {
        	fillUp(ourFromNumber, - call.getPrice());
        }
        if(ourToNumber != null) {
        	fillUp(ourToNumber,  - call.getPrice());
        }
	}
	public void save(Customer customer) throws Exception {
		if( hasThisNumber(customer.getNumber()) )
			throw new Exception("This number already exists."); 
        BasicDBObject doc = new BasicDBObject();

        doc.put(numberStr, customer.getNumber());
        doc.put(nameStr,   customer.getName());
        doc.put(addressStr, customer.getAddress());
        doc.put(cashStr,   customer.getCash());

        customerColl.insert(doc);
        customers.add(customer);
	}
	
	public List<Call> getCalls() {
		return calls;
	}
	public List<Customer> getCustomers() {
		return customers;
	}
	
	public void load() throws Exception {
		findAllCalls();
		findAllCustomers();
		loadTariff();
	}
	
	public void findAllCalls() {
		calls.clear();
        DBCursor cur = callColl.find();
        while(cur.hasNext()) {
        	calls.add(Call.fromDBObject(cur.next()));
        }
	}
	public void findAllCustomers() {
		customers.clear();
        DBCursor cur = customerColl.find();
        while(cur.hasNext()) {
        	customers.add(Customer.fromDBObject(cur.next()));
        }
	}
	private void loadTariff() {
        DBCursor cur = tariffColl.find();
       
        tariffs.clear();
        while(cur.hasNext()) {
        	DBObject tariff = cur.next();
        	tariffs.put( (String)tariff.get(typeStr),
        				 (Integer)tariff.get(priceStr));
        }
	}
	
	public void changeTariffs(Integer internal, Integer input, Integer output) {
		tariffs.clear();
		tariffs.put(internalTariffName, internal);
		tariffs.put(inputTariffName, input);
		tariffs.put(outputTariffName, output);
		
		BasicDBObject doc;
		
		doc = new BasicDBObject();
		doc.put(typeStr, internalTariffName);
		doc.put(priceStr, tariffs.get(internalTariffName));
		tariffColl.update(new BasicDBObject().append(typeStr, internalTariffName), doc);
		
		doc = new BasicDBObject();
		doc.put(typeStr, inputTariffName);
		doc.put(priceStr, tariffs.get(inputTariffName));
		tariffColl.update(new BasicDBObject().append(typeStr, inputTariffName), doc);
		
		doc = new BasicDBObject();
		doc.put(typeStr, outputTariffName);
		doc.put(priceStr, tariffs.get(outputTariffName));
		tariffColl.update(new BasicDBObject().append(typeStr, outputTariffName), doc);		
	}
	public Integer getInternalTariff() {
		return tariffs.get(internalTariffName);
	}
	public Integer getInputTariff() {
		return tariffs.get(inputTariffName);
	}
	public Integer getOutputTariff() {
		return tariffs.get(outputTariffName);
	}
	
	public void findCalls(String from, String to,
						  Integer minDuration, Integer maxDuration, 
						  Integer minPrice, Integer maxPrice) {
		
        BasicDBObject query = new BasicDBObject();
        DBCursor cur;
        
        if(from != null)        
        	query.put(fromStr, from);
        if(to != null)        
        	query.put(toStr, to);
        if(minDuration != null && maxDuration != null) {
        	query.put(durationStr, new BasicDBObject("$gte", minDuration)
        									 .append("$lte", maxDuration));
        }        
        if(minPrice != null && maxPrice != null)   {      
        	query.put(priceStr, new BasicDBObject("$gte", minPrice)
			 								 .append("$lte", maxPrice));
        }
        
		calls.clear();
        cur = callColl.find(query);
        while(cur.hasNext()) {
        	calls.add(Call.fromDBObject(cur.next()));
        }
	}	
	public void findCustomers(String number, String name, String address, 
			  Integer minCash, Integer maxCash) {

        BasicDBObject query = new BasicDBObject();
        DBCursor cur;
        
        if(number != null)        
        	query.put(numberStr, number);
        if(name != null)        
        	query.put(nameStr, name);
        if(address != null)        
        	query.put(addressStr, address);
        if(minCash != null && maxCash != null) {
        	query.put(cashStr, new BasicDBObject("$gte", minCash)
        									 .append("$lte", maxCash));
        }
        
		customers.clear();
        cur = customerColl.find(query);
        while(cur.hasNext()) {
        	customers.add(Customer.fromDBObject(cur.next()));
        }
	}
	
	public void fillUp(String number, Integer money) {
		BasicDBObject doc = new BasicDBObject().append("$inc", 
				new BasicDBObject().append(cashStr, money));
		customerColl.update(new BasicDBObject().append(numberStr, number), doc);
	}
		
	public boolean hasThisNumber(String number) {
        DBCursor cur = customerColl.find(new BasicDBObject().append(numberStr, number));        
        if(cur.hasNext())
        	return true;
        else
        	return false;
	}
	
	public boolean isActive(String number) {
		DBCursor cur = customerColl.find(new BasicDBObject().append(numberStr, number));     
        if( (Integer) cur.next().get(cashStr) > 0 )
        	return true;
        else
        	return false;		
	}
	
	
}
