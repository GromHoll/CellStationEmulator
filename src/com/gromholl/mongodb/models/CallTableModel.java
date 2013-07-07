package com.gromholl.mongodb.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.gromholl.mongodb.entity.Call;

public class CallTableModel extends AbstractTableModel  {
	
	private static final long serialVersionUID = 1L;
	
	private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
	private List<Call> calls;
	
	public CallTableModel(List<Call> calls) {
		this.calls = calls;
	}
	
	public void setList(List<Call> calls) {
		this.calls = calls;
	}
	
	public void addTableModelListener(TableModelListener l) {
		listeners.add(l);
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		return 4;
	}

	public String getColumnName(int columnIndex) {
		switch(columnIndex) {
		case 0:
			return "From";
		case 1:
			return "To";
		case 2:
			return "Duration";
		case 3:
			return "Price";
		}
		return "";
	}

	public int getRowCount() {
		return calls.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Call call = calls.get(rowIndex);
		switch(columnIndex) {
		case 0:
			return call.getFrom();
		case 1:
			return call.getTo();
		case 2:
			return call.getDuration();
		case 3:
			return call.getPrice();
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
