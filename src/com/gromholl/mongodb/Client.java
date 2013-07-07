package com.gromholl.mongodb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

import com.gromholl.mongodb.entity.Call;
import com.gromholl.mongodb.entity.Customer;
import com.gromholl.mongodb.models.CallTableModel;
import com.gromholl.mongodb.models.CustomerTableModel;
import com.mongodb.MongoException;

public class Client extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private Connector connector;
	
	private JTabbedPane tabs;
		
	private JFormattedTextField fromAddField;
	private JFormattedTextField toAddField;
	private JSpinner durationAddSpinner;
	private JButton addCallButton;
	private JSpinner internalSpinner;
	private JSpinner inputSpinner;
	private JSpinner outputSpinner;
	private JButton changeButton;
	private JButton resetButton;
	private JCheckBox fromFindCheckBox;
	private JFormattedTextField fromFindField;
	private JCheckBox toFindCheckBox;
	private JFormattedTextField toFindField;
	private JCheckBox durationFindCheckBox;
	private JSpinner minDurationFindSpinner;
	private JSpinner maxDurationFindSpinner;
	private JCheckBox priceFindCheckBox;
	private JSpinner minPriceFindSpinner;
	private JSpinner maxPriceFindSpinner;
	private JButton findCallButton;
	private JButton findAllCallButton;
	private CallTableModel callTableModel;
	private JTable callTable;
	
	private JFormattedTextField numberAddField;
	private JTextField nameAddField;
	private JTextField addressAddField;
	private JSpinner cashAddSpinner;
	private JButton addCustomerButton;
	private JFormattedTextField fillUpNumber;
	private JSpinner fillUpSpinner;	
	private JButton fillUpButton;
	private JCheckBox numberFindCheckBox;
	private JFormattedTextField numberFindField;
	private JCheckBox nameFindCheckBox;
	private JTextField nameFindField;
	private JCheckBox addressFindCheckBox;
	private JTextField addressFindField;
	private JCheckBox cashFindCheckBox;
	private JSpinner minCashFindSpinner;
	private JSpinner maxCashFindSpinner;
	private JButton findCustomerButton;
	private JButton findAllCustomerButton;
	private CustomerTableModel customerTableModel;
	private JTable customerTable;
	
	private MaskFormatter numberFormat;
	private final String numberMask = "8-###-###-##-##";
	private final int fieldSize = 10;
	
	private final String fromStr = "From:";
	private final String toStr = "To:";
	private final String durationStr = "Duration:";
	private final String priceStr = "Price:";
	private final String numberStr = "Number:";
	private final String nameStr = "Name:";
	private final String addressStr = "Address:";
	private final String cashStr = "Cash:";
	private final String internalStr = "Internal:";
	private final String inputStr = "Input:";
	private final String outputStr = "Output:";
	private final String minStr = "min:";
	private final String maxStr = "max:";
	private final String addStr = "Add";
	private final String findStr = "Find";
	private final String findAllStr = "Find All";
	private final String changeStr = "Change";
	private final String resetStr = "Reset";
	private final String fillUpStr = "Fill Up";
	private final String callTabtr = "Calls";
	private final String customerTabStr = "Customers";
	
		
	public Client() {
    	try {
		//	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) { /* Do nothing */ }	
    	try {
			numberFormat = new MaskFormatter(numberMask);
		} catch (ParseException e) {
			e.printStackTrace(); 
		}
    	
    	connector = new Connector("localhost", 27017, "cellStation");    	
    	
		tabs = new JTabbedPane();
		
		fromAddField = new JFormattedTextField(numberFormat);
		toAddField = new JFormattedTextField(numberFormat);
		durationAddSpinner = new JSpinner();
		addCallButton = new JButton(addStr);
		internalSpinner = new JSpinner();
		inputSpinner = new JSpinner();
		outputSpinner = new JSpinner();
		changeButton = new JButton(changeStr);
		resetButton = new JButton(resetStr);		
		fromFindCheckBox = new JCheckBox(fromStr);
		fromFindField = new JFormattedTextField(numberFormat);
		toFindCheckBox = new JCheckBox(toStr);
		toFindField = new JFormattedTextField(numberFormat);
		durationFindCheckBox = new JCheckBox(durationStr);
		minDurationFindSpinner = new JSpinner();
		maxDurationFindSpinner = new JSpinner();
		priceFindCheckBox = new JCheckBox(priceStr);
		minPriceFindSpinner = new JSpinner();
		maxPriceFindSpinner = new JSpinner();
		findCallButton = new JButton(findStr);
		findAllCallButton = new JButton(findAllStr);		
		callTableModel = new CallTableModel(connector.getCalls());
		callTable = new JTable(callTableModel);
		
		numberAddField = new JFormattedTextField(numberFormat);
		nameAddField = new JTextField();
		addressAddField = new JTextField();
		cashAddSpinner = new JSpinner();
		addCustomerButton = new JButton(addStr);
		fillUpNumber = new JFormattedTextField(numberFormat);
		fillUpSpinner = new JSpinner();	
		fillUpButton = new JButton(fillUpStr);
		numberFindCheckBox = new JCheckBox(numberStr);		
		numberFindField = new JFormattedTextField(numberFormat);	
		nameFindCheckBox = new JCheckBox(nameStr);	
		nameFindField = new JTextField();
		addressFindCheckBox = new JCheckBox(addressStr);
		addressFindField = new JTextField();
		cashFindCheckBox = new JCheckBox(cashStr);
		minCashFindSpinner = new JSpinner();
		maxCashFindSpinner = new JSpinner();
		findCustomerButton = new JButton(findStr);
		findAllCustomerButton = new JButton(findAllStr);		
		customerTableModel = new CustomerTableModel(connector.getCustomers());
		customerTable = new JTable(customerTableModel);
		
		init();
	}	    
    public void init(){
    	JLabel label;
    	GridBagConstraints c = new GridBagConstraints();
    	c.anchor = GridBagConstraints.NORTHWEST;
    	c.fill   = GridBagConstraints.HORIZONTAL;
    	c.gridheight = 1;
    	c.gridwidth = 1;
    	c.gridx = 0; 
    	c.gridy = 0; 
    	c.insets = new Insets(3, 3, 3, 3);
    	c.ipadx = 0;
    	c.ipady = 0;
    	c.weightx = 1.0;
    	c.weighty = 0.0;
    	
    	/* Calls Tab */
    	JPanel callTablePanel = new JPanel();
    	JPanel callPanel = new JPanel();
    	JPanel leftCallPanel = new JPanel();
    	JPanel addCallPanel = new JPanel();
    	JPanel tariffPanel = new JPanel();
    	JPanel findCallPanel = new JPanel();
    	GridBagLayout addCallGBL = new GridBagLayout();
    	GridBagLayout tariffCallGBL = new GridBagLayout();
    	GridBagLayout findCallGBL = new GridBagLayout();
    	
    	callPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
    	callPanel.setLayout(new BorderLayout(3, 3));
    	leftCallPanel.setLayout(new BoxLayout(leftCallPanel, BoxLayout.Y_AXIS));
    	leftCallPanel.add(addCallPanel);
    	leftCallPanel.add(tariffPanel);
    	leftCallPanel.add(findCallPanel);
    	
    	addCallPanel.setBorder(BorderFactory.createTitledBorder("Add call"));    	
    	addCallPanel.setLayout(addCallGBL); 
    	c.gridy = GridBagConstraints.RELATIVE;
    	label = new JLabel(fromStr);
    	addCallGBL.setConstraints(label, c);
    	addCallPanel.add(label);
    	label = new JLabel(toStr);
    	addCallGBL.setConstraints(label, c);
    	addCallPanel.add(label);
    	label = new JLabel(durationStr);
    	addCallGBL.setConstraints(label, c);
    	addCallPanel.add(label);    	
    	c.gridx = 1;
    	c.gridy = 0;
    	addCallGBL.setConstraints(fromAddField, c);
    	addCallPanel.add(fromAddField);
    	c.gridy = GridBagConstraints.RELATIVE;
    	addCallGBL.setConstraints(toAddField, c);
    	addCallPanel.add(toAddField);
    	c.gridy = GridBagConstraints.RELATIVE;
    	addCallGBL.setConstraints(durationAddSpinner, c);
    	addCallPanel.add(durationAddSpinner);
    	c.gridx = 0;
    	c.gridwidth = 2;
    	addCallGBL.setConstraints(addCallButton, c);
    	addCallPanel.add(addCallButton);
    	
    	tariffPanel.setBorder(BorderFactory.createTitledBorder("Tariffs"));
    	tariffPanel.setLayout(tariffCallGBL);
    	c.gridx = 0;
    	c.gridy = 0;
    	c.gridwidth = 1;
    	label = new JLabel(internalStr);
    	tariffCallGBL.setConstraints(label, c);
    	tariffPanel.add(label);
    	c.gridy = GridBagConstraints.RELATIVE;
    	label = new JLabel(inputStr);
    	tariffCallGBL.setConstraints(label, c);
    	tariffPanel.add(label);
    	label = new JLabel(outputStr);
    	tariffCallGBL.setConstraints(label, c);
    	tariffPanel.add(label);
    	c.gridwidth = 2;
    	tariffCallGBL.setConstraints(changeButton, c);
    	tariffPanel.add(changeButton);
    	c.gridy = 3;
    	c.gridx = 2;
    	tariffCallGBL.setConstraints(resetButton, c);
    	tariffPanel.add(resetButton);   	
    	c.gridwidth = 3;
    	c.gridy = 0;
    	c.gridx = 1;
    	tariffCallGBL.setConstraints(internalSpinner, c);
    	tariffPanel.add(internalSpinner);  
    	c.gridy = GridBagConstraints.RELATIVE; 	
    	tariffCallGBL.setConstraints(inputSpinner, c);
    	tariffPanel.add(inputSpinner); 
    	tariffCallGBL.setConstraints(outputSpinner, c);
    	tariffPanel.add(outputSpinner);
    	
    	
    	findCallPanel.setBorder(BorderFactory.createTitledBorder("Filter"));
    	findCallPanel.setLayout(findCallGBL);
    	c.gridx = 0;
    	c.gridy = 0;
    	c.gridwidth = 1;
    	findCallGBL.setConstraints(fromFindCheckBox, c);
    	findCallPanel.add(fromFindCheckBox);
    	c.gridy = GridBagConstraints.RELATIVE;
    	findCallGBL.setConstraints(toFindCheckBox, c);
    	findCallPanel.add(toFindCheckBox);    	
    	findCallGBL.setConstraints(durationFindCheckBox, c);
    	findCallPanel.add(durationFindCheckBox);
    	c.gridy = 4;
    	findCallGBL.setConstraints(priceFindCheckBox, c);
    	findCallPanel.add(priceFindCheckBox);
    	c.gridx = 1;
    	c.gridy = 2;
    	label = new JLabel(minStr);
    	findCallGBL.setConstraints(label, c);
    	findCallPanel.add(label);
    	c.gridy = GridBagConstraints.RELATIVE;
    	label = new JLabel(maxStr);
    	findCallGBL.setConstraints(label, c);
    	findCallPanel.add(label);
    	label = new JLabel(minStr);
    	findCallGBL.setConstraints(label, c);
    	findCallPanel.add(label);
    	c.gridy = GridBagConstraints.RELATIVE;
    	label = new JLabel(maxStr);
    	findCallGBL.setConstraints(label, c);
    	findCallPanel.add(label);
    	c.gridx = 1;
    	c.gridy = 0;
    	c.gridwidth = 3;
    	findCallGBL.setConstraints(fromFindField, c);
    	findCallPanel.add(fromFindField);
    	c.gridy = GridBagConstraints.RELATIVE;
    	findCallGBL.setConstraints(toFindField, c);
    	findCallPanel.add(toFindField);
    	c.gridx = 2;
    	c.gridwidth = 2;
    	findCallGBL.setConstraints(minDurationFindSpinner, c);
    	findCallPanel.add(minDurationFindSpinner);    
    	findCallGBL.setConstraints(maxDurationFindSpinner, c);
    	findCallPanel.add(maxDurationFindSpinner);
    	findCallGBL.setConstraints(minPriceFindSpinner, c);
    	findCallPanel.add(minPriceFindSpinner);    
    	findCallGBL.setConstraints(maxPriceFindSpinner, c);
    	findCallPanel.add(maxPriceFindSpinner);
    	c.gridx = 0;
    	c.gridy = 6;
    	findCallGBL.setConstraints(findCallButton, c);
    	findCallPanel.add(findCallButton);
    	c.gridx = 2;
    	findCallGBL.setConstraints(findAllCallButton, c);
    	findCallPanel.add(findAllCallButton);
    	
    	callPanel.add(leftCallPanel, BorderLayout.WEST);
    	callTablePanel.add(callTable);
    	callTablePanel.add(new JScrollPane(callTable));
    	callPanel.add(callTablePanel, BorderLayout.CENTER);
    	
    	/* Customer Tab */
    	JPanel customerPanel = new JPanel();
    	JPanel customerTablePanel = new JPanel();
    	JPanel leftCustomerPanel = new JPanel();
    	JPanel addCustomerPanel = new JPanel();
    	JPanel fillUpPanel = new JPanel();
    	JPanel findCustomerPanel = new JPanel();
    	GridBagLayout addCustomerGBL = new GridBagLayout();
    	GridBagLayout fillUpGBL = new GridBagLayout();
    	GridBagLayout findCustomerGBL = new GridBagLayout();
    	
    	customerPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
    	customerPanel.setLayout(new BorderLayout(3, 3));
    	leftCustomerPanel.setLayout(new BoxLayout(leftCustomerPanel, BoxLayout.Y_AXIS));
    	leftCustomerPanel.add(addCustomerPanel);
    	leftCustomerPanel.add(fillUpPanel);
    	leftCustomerPanel.add(findCustomerPanel);
    	
    	addCustomerPanel.setBorder(BorderFactory.createTitledBorder("Add customer"));    	
    	addCustomerPanel.setLayout(addCustomerGBL);
    	c.gridwidth = 1;
    	c.gridx = 0;
    	c.gridy = 0;
    	label = new JLabel(numberStr);
    	addCustomerGBL.setConstraints(label, c);
    	addCustomerPanel.add(label);
    	c.gridy = GridBagConstraints.RELATIVE;
    	label = new JLabel(nameStr);
    	addCustomerGBL.setConstraints(label, c);
    	addCustomerPanel.add(label);
    	label = new JLabel(addressStr);
    	addCustomerGBL.setConstraints(label, c);
    	addCustomerPanel.add(label);
    	label = new JLabel(cashStr);
    	addCustomerGBL.setConstraints(label, c);
    	addCustomerPanel.add(label);
    	c.gridwidth = 2;
    	addCustomerGBL.setConstraints(addCustomerButton, c);
    	addCustomerPanel.add(addCustomerButton); 
    	c.gridwidth = 1;
    	c.gridx = 1;
    	c.gridy = 0;
    	addCustomerGBL.setConstraints(numberAddField, c);
    	addCustomerPanel.add(numberAddField);
    	c.gridy = GridBagConstraints.RELATIVE;
    	addCustomerGBL.setConstraints(nameAddField, c);
    	addCustomerPanel.add(nameAddField);
    	addCustomerGBL.setConstraints(addressAddField, c);
    	addCustomerPanel.add(addressAddField);
    	addCustomerGBL.setConstraints(cashAddSpinner, c);
    	addCustomerPanel.add(cashAddSpinner);
    	
    	fillUpPanel.setBorder(BorderFactory.createTitledBorder("Fill up account"));    	
    	fillUpPanel.setLayout(fillUpGBL);
    	c.gridwidth = 1;
    	c.gridx = 0;
    	c.gridy = 0;
    	label = new JLabel(numberStr);
    	fillUpGBL.setConstraints(label, c);
    	fillUpPanel.add(label);
    	c.gridy = 1;    	
    	label = new JLabel(cashStr);
    	fillUpGBL.setConstraints(label, c);
    	fillUpPanel.add(label);    	
    	c.gridx = 1;
    	c.gridy = 0;
    	fillUpGBL.setConstraints(fillUpNumber, c);
    	fillUpPanel.add(fillUpNumber);
    	c.gridy = 1;
    	fillUpGBL.setConstraints(fillUpSpinner, c);
    	fillUpPanel.add(fillUpSpinner);
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 2;    	
    	fillUpGBL.setConstraints(fillUpButton, c);
    	fillUpPanel.add(fillUpButton);  
    	
    	findCustomerPanel.setBorder(BorderFactory.createTitledBorder("Find customer"));    	
    	findCustomerPanel.setLayout(findCustomerGBL);
    	c.gridwidth = 1;
    	c.gridx = 0;
    	c.gridy = 0;
    	findCustomerGBL.setConstraints(numberFindCheckBox, c);
    	findCustomerPanel.add(numberFindCheckBox);
    	c.gridy = GridBagConstraints.RELATIVE;
    	findCustomerGBL.setConstraints(nameFindCheckBox, c);
    	findCustomerPanel.add(nameFindCheckBox);
    	findCustomerGBL.setConstraints(addressFindCheckBox, c);
    	findCustomerPanel.add(addressFindCheckBox);
    	findCustomerGBL.setConstraints(cashFindCheckBox, c);
    	findCustomerPanel.add(cashFindCheckBox);
    	c.gridwidth = 2;
    	c.gridx = 1;
    	c.gridy = 0;
    	findCustomerGBL.setConstraints(numberFindField, c);
    	findCustomerPanel.add(numberFindField);
    	c.gridy = GridBagConstraints.RELATIVE;
    	findCustomerGBL.setConstraints(nameFindField, c);
    	findCustomerPanel.add(nameFindField);    	
    	findCustomerGBL.setConstraints(addressFindField, c);
    	findCustomerPanel.add(addressFindField);
    	c.gridwidth = 1;
       	label = new JLabel(minStr);
    	findCustomerGBL.setConstraints(label, c);
    	findCustomerPanel.add(label);
       	label = new JLabel(maxStr);
    	findCustomerGBL.setConstraints(label, c);
    	findCustomerPanel.add(label);
    	c.gridwidth = 1;
    	c.gridx = 2;
    	c.gridy = 3;
    	findCustomerGBL.setConstraints(minCashFindSpinner, c);
    	findCustomerPanel.add(minCashFindSpinner);
    	c.gridy = 4;
    	findCustomerGBL.setConstraints(maxCashFindSpinner, c);
    	findCustomerPanel.add(maxCashFindSpinner);
    	c.gridwidth = 2;
    	c.gridy = 5;
    	c.gridx = 0;
    	findCustomerGBL.setConstraints(findCustomerButton, c);
    	findCustomerPanel.add(findCustomerButton);
    	c.gridx = 2;
    	findCustomerGBL.setConstraints(findAllCustomerButton, c);
    	findCustomerPanel.add(findAllCustomerButton);
   	
    	customerPanel.add(leftCustomerPanel, BorderLayout.WEST);
    	customerTablePanel.add(customerTable);
    	customerTablePanel.add(new JScrollPane(customerTable));
    	customerPanel.add(customerTablePanel, BorderLayout.CENTER);    	
    	
    	
    	tabs.add(callPanel, callTabtr);
    	tabs.add(customerPanel, customerTabStr);
    	
    	fromAddField.setColumns(fieldSize);
    	toAddField.setColumns(fieldSize);
    	fromFindField.setColumns(fieldSize);
    	toFindField.setColumns(fieldSize);
    	numberAddField.setColumns(fieldSize);
    	fillUpNumber.setColumns(fieldSize);
    	
    	numberFindField.setColumns(fieldSize);
    	nameAddField.setColumns(fieldSize);
    	addressAddField.setColumns(fieldSize);
    	nameFindField.setColumns(fieldSize);
    	addressFindField.setColumns(fieldSize);
    	
    	durationAddSpinner.addChangeListener(new myChangeSpinnerListener(durationAddSpinner));
    	internalSpinner.addChangeListener(new myChangeSpinnerListener(internalSpinner));
    	inputSpinner.addChangeListener(new myChangeSpinnerListener(inputSpinner));
    	outputSpinner.addChangeListener(new myChangeSpinnerListener(outputSpinner));
    	minDurationFindSpinner.addChangeListener(new myChangeSpinnerListener(minDurationFindSpinner));
    	maxDurationFindSpinner.addChangeListener(new myChangeSpinnerListener(maxDurationFindSpinner));
    	minPriceFindSpinner.addChangeListener(new myChangeSpinnerListener(minPriceFindSpinner));
    	maxPriceFindSpinner.addChangeListener(new myChangeSpinnerListener(maxPriceFindSpinner));
    	cashAddSpinner.addChangeListener(new myChangeSpinnerListener(cashAddSpinner));
    	fillUpSpinner.addChangeListener(new myChangeSpinnerListener(fillUpSpinner));
    	minCashFindSpinner.addChangeListener(new myChangeSpinnerListener(minCashFindSpinner));
    	maxCashFindSpinner.addChangeListener(new myChangeSpinnerListener(maxCashFindSpinner));
    	
    	fromFindCheckBox.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				fromFindField.setEnabled(fromFindCheckBox.isSelected());
			}
		});
    	toFindCheckBox.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				toFindField.setEnabled(toFindCheckBox.isSelected());
			}
		});    	

		numberFindCheckBox.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				numberFindField.setEnabled(numberFindCheckBox.isSelected());
			}
		});
		nameFindCheckBox.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				nameFindField.setEnabled(nameFindCheckBox.isSelected());
			}
		});
		addressFindCheckBox.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				addressFindField.setEnabled(addressFindCheckBox.isSelected());
			}
		});
		
    	priceFindCheckBox.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				minPriceFindSpinner.setEnabled(priceFindCheckBox.isSelected());
				maxPriceFindSpinner.setEnabled(priceFindCheckBox.isSelected());
			}
		});
    	durationFindCheckBox.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				minDurationFindSpinner.setEnabled(durationFindCheckBox.isSelected());
				maxDurationFindSpinner.setEnabled(durationFindCheckBox.isSelected());				
			}
		});
    	cashFindCheckBox.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				minCashFindSpinner.setEnabled(cashFindCheckBox.isSelected());
				maxCashFindSpinner.setEnabled(cashFindCheckBox.isSelected());						
			}
		});    	
    	
    	addCallButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addCall();
			}
		});
    	changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeTariffs();
			}
		});
    	findCallButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				findCalls();
			}
		});
    	findAllCallButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				findAllCalls();
			}
		});
    	addCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addCustomer();
			}
		});
    	fillUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fillUp();
			}
		});
    	resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetTariff();
			}
		});
    	findCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				findCustomers();
			}
		});
    	findAllCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				findAllCustomers();
			}
		});
    	
    	customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				fillUpNumber.setValue(customerTableModel.getValueAt(customerTable.getSelectedRow(), 0));
			}
		});

    	fromFindField.setEnabled(false);
    	toFindField.setEnabled(false);
    	
		numberFindField.setEnabled(false);
		nameFindField.setEnabled(false);
		addressFindField.setEnabled(false);
    	
    	minCashFindSpinner.setEnabled(false);
    	maxCashFindSpinner.setEnabled(false);
    	minDurationFindSpinner.setEnabled(false);
    	maxDurationFindSpinner.setEnabled(false);
    	minPriceFindSpinner.setEnabled(false);
    	maxPriceFindSpinner.setEnabled(false);
    	
    	try {
			customerTable.setDefaultRenderer(Class.forName( "java.lang.String" ), new ColorTableCellRenderer());
		} catch (ClassNotFoundException e1) {
		}
    	
    	this.add(tabs);
    	this.pack();
    	this.setResizable(false);    	
    	this.setTitle("Cell Station Emulator");
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	this.setVisible(true);
    }

	public static void main(String[] args) {    	
    	SwingUtilities.invokeLater(new Runnable() {
        	public void run() {  
            	Client app = new Client();
            	try {
					app.connect();
					System.out.println("Connection to DB....OK");
					app.load();
					System.out.println("Loading data........OK");					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(app, "Data base connection error.", "Error", JOptionPane.ERROR_MESSAGE);
					app.dispose();
				}
        	}
        });
	}    
    
    public void connect() throws UnknownHostException, MongoException {
    	connector.connect();
    }
    public void load() throws Exception {
    	connector.load();
    	resetTariff();
    }
    
    private void addCall() {
    	try {
    		if(!fromAddField.isEditValid() || !toAddField.isEditValid())
    			throw new Exception("Not correct numbers format.");
    		Call call = new Call( fromAddField.getText(),
					  toAddField.getText(),
					  (Integer) durationAddSpinner.getValue());
			connector.save(call);
			callTable.repaint();
			callTable.revalidate();
			findCustomers();
    	} catch(Exception exc) {
    		JOptionPane.showMessageDialog(this, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }    
    private void changeTariffs() {
    	connector.changeTariffs((Integer) internalSpinner.getValue(),
				    			(Integer) inputSpinner.getValue(),
				    			(Integer) outputSpinner.getValue());
    }
    private void resetTariff() {    
    	internalSpinner.setValue(connector.getInternalTariff());
    	inputSpinner.setValue(connector.getInputTariff());
    	outputSpinner.setValue(connector.getOutputTariff());
    }
    private void findCalls() {    	
    	try {    	
	    	String from = null;
	    	String to = null;
	    	Integer minDuration = null;
	    	Integer maxDuration = null;
	    	Integer minPrice = null;
	    	Integer maxPrice = null;    
	    	
	    	if(fromFindCheckBox.isSelected()) {
	    		if(fromFindField.isEditValid())
	    			from = fromFindField.getText();
	    		else
	    			throw new Exception("Not correct numbers format.");
	    	}
	    	if(toFindCheckBox.isSelected()) {
	    		if(toFindField.isEditValid())
	    			to = toFindField.getText();
	    		else
	    			throw new Exception("Not correct numbers format.");
	    	}
	    	
	    	if(durationFindCheckBox.isSelected()) {
	        	minDuration = (Integer) minDurationFindSpinner.getValue();
	        	maxDuration = (Integer) maxDurationFindSpinner.getValue();    		
	    	}
	    	if(priceFindCheckBox.isSelected()) {
	    		minPrice = (Integer) minPriceFindSpinner.getValue();
	    		maxPrice = (Integer) maxPriceFindSpinner.getValue();    		
	    	}
	    	
	    	connector.findCalls(from, to, minDuration, maxDuration, minPrice, maxPrice);
			callTable.repaint();
	    	callTable.revalidate();
    	} catch(Exception exc) {
    		JOptionPane.showMessageDialog(this, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
    private void findAllCalls() {
    	connector.findAllCalls();

    	fromFindCheckBox.setSelected(false);
    	toFindCheckBox.setSelected(false);
    	durationFindCheckBox.setSelected(false);
    	priceFindCheckBox.setSelected(false);
    	
		callTable.repaint();
    	callTable.revalidate();
    }
    
    private void addCustomer() {
    	try {
    		if(!numberAddField.isEditValid()) 
    			throw new Exception("Not correct numbers format.");
    		if(nameAddField.getText().isEmpty() || addressAddField.getText().isEmpty())
    			throw new Exception("Fill all fields.");
    		
    		Customer customer = new Customer(numberAddField.getText(),
					 nameAddField.getText(),
					 addressAddField.getText(),
					 (Integer) cashAddSpinner.getValue());
			connector.save(customer);
			customerTable.repaint();
			customerTable.revalidate();
    	} catch(Exception exc) {
    		JOptionPane.showMessageDialog(this, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
    private void fillUp() {
    	if(fillUpNumber.isEditValid()) {
    		connector.fillUp(fillUpNumber.getText(), (Integer) fillUpSpinner.getValue());
    		findCustomers();
    	} else {
    		JOptionPane.showMessageDialog(this, "Not correct numbers format.", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
    private void findCustomers() {
    	try {
	    	String number = null;
	    	String name = null;
	    	String address = null;
	    	Integer minCash = null;
	    	Integer maxCash = null;
	    	
	    	if(numberFindCheckBox.isSelected()) {
	    		if(numberFindField.isEditValid())
	    			number = numberFindField.getText();
	    		else
	    			throw new Exception("Not correct numbers format.");
	    	}
	    	if(nameFindCheckBox.isSelected()) {
	    		name = nameFindField.getText();
	    	}
	    	if(addressFindCheckBox.isSelected()) {
	    		address = addressFindField.getText();
	    	}
	    	if(cashFindCheckBox.isSelected()) {
	    		minCash = (Integer) minCashFindSpinner.getValue();
	    		maxCash = (Integer) maxCashFindSpinner.getValue();    		
	    	}
	    	
	    	connector.findCustomers(number, name, address, minCash, maxCash);
	    	customerTable.repaint();
	    	customerTable.revalidate();
		} catch(Exception exc) {
			JOptionPane.showMessageDialog(this, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
    }    
    private void findAllCustomers() {
    	connector.findAllCustomers();
    	
    	numberFindCheckBox.setSelected(false);
    	nameFindCheckBox.setSelected(false);
    	addressFindCheckBox.setSelected(false);
    	cashFindCheckBox.setSelected(false);
    	
    	customerTable.repaint();
    	customerTable.revalidate();
    }
	
}

class myChangeSpinnerListener implements ChangeListener {    	
	JSpinner parent;
	
	public myChangeSpinnerListener(JSpinner parent) {
		this.parent = parent;
	}
	public void stateChanged(ChangeEvent e) {
		if((Integer)parent.getValue() < 0) 
			parent.setValue(0);		
	}    	
}

class ColorTableCellRenderer extends DefaultTableCellRenderer {
	
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent
       (JTable table, Object value, boolean isSelected,
       boolean hasFocus, int row, int column) 
    {
        Component cell = super.getTableCellRendererComponent
           (table, value, isSelected, hasFocus, row, column);
        
        if(column == 4) {
        	if( ((String) table.getValueAt(row, column)).equals("Block") )
                cell.setBackground( Color.red );
            else 
            	cell.setBackground( Color.green );
        } else {
        	cell.setBackground( Color.white );
        }
        return cell;
    }
}