package com.gromholl.mongodb.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.gromholl.mongodb.entity.Customer;

public class CustomerTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
	private List<Customer> customers;
	
	public CustomerTableModel(List<Customer> customers) {
		this.customers = customers;
	}
	
	public void setList(List<Customer> customers) {
		this.customers = customers;
	}
	
	public void addTableModelListener(TableModelListener l) {
		listeners.add(l);
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		return 5;
	}

	public String getColumnName(int columnIndex) {
		switch(columnIndex) {
		case 0:
			return "Number";
		case 1:
			return "Name";
		case 2:
			return "Address";
		case 3:
			return "Cash";
		case 4:
			return "Status";
		}
		return "";
	}

	public int getRowCount() {
		return customers.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Customer customer = customers.get(rowIndex);
		switch(columnIndex) {
		case 0:
			return customer.getNumber();
		case 1:
			return customer.getName();
		case 2:
			return customer.getAddress();
		case 3:
			return customer.getCash();
		case 4:
			if(customer.getCash() > 0)
				return "Active";
			else
				return "Block";
		}
		return "";
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		
	}

}
