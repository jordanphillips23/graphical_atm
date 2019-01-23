package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.ViewManager;
import data.Database;
import model.BankAccount;
import model.User;

@SuppressWarnings("serial")
public class InformationView extends InnerView {
	
	private HashMap<String, JLabel> labels = new HashMap<String, JLabel>();
	private HashMap<String, JTextField> textFields = new HashMap<String, JTextField>();
	private ArrayList<JComboBox<String>> DOB = new ArrayList<JComboBox<String>>();
	private JComboBox<String> state = new JComboBox<String>();
	private JPasswordField PIN = new JPasswordField();
	private JPasswordField oldPIN = new JPasswordField();
	private JLabel error = new JLabel("");
	private JButton edit;
	private JButton cancel;
	
	private String[] edittable = {"Phone Number1", "Phone Number2", "Phone Number3", "Street Address", "City", "Postal Code"};
	
	private ViewManager manager;	
	private BankAccount account;// manages interactions between the views, model, and database
	
	/**
	 * Constructs an instance (or object) of the CreateView class.
	 * 
	 * @param manager
	 */
	
	public InformationView(ViewManager manager) {
		super(manager);
		
		this.manager = manager;
		initialize();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the CreateView components.
	 */
	
	private void initialize() {
		this.removeAll();
		this.setLayout(null);
		
		// TODO
		//
		// this is a placeholder for this view and should be removed once you start
		// building the CreateView.
		
		createTextAndLabel("First Name", 50, 0, 100, 35, 240, 15, false);
		createTextAndLabel("Last Name", 50, 45, 100, 35, 240, 20, false);
		createDOBInput(50, 90, 100, 35, 240);
		createPhoneNumberInput("Phone Number", 50, 135, 100, 35, 240);
		createTextAndLabel("Street Address", 50, 180, 100, 35, 240, 30, false);
		createTextAndLabel("City", 50, 225, 100, 35, 240, 30, false);
		createState("State", 50, 270, 100, 35, 240);
		createTextAndLabel("Postal Code", 50, 315, 100, 35, 240, 5, true);
		createPIN("PIN", 50, 360, 100, 35, 240);
		createError(50, 440, 300, 35);
		createEdit(50, 395, 150, 35);
		createCancel(250, 395, 150, 35);
		createBackButton();
		
		
		// TODO
		//
		// this is where you should build the CreateView (i.e., all the components that
		// allow the user to enter his or her information and create a new account).
		//
		// feel free to use my layout in LoginView as an example for laying out and
		// positioning your components.
		setEditable(false);
	}

	private void createEdit(int x, int y, int width, int height) {
		edit = new JButton("edit");
		edit.setBounds(x, y, width, height);
		edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (textFields.get("Street Address").isEnabled()) {
					if(updateAccount()) {
						edit.setText("Edit");
						setEditable(false);
						PIN.setText("");
						oldPIN.setText("");
						
					}
				}
				else {
					edit.setText("Save");
					setEditable(true);
				}
				
				
			}

			
		});
		this.add(edit);
		
	}
	
	private void createCancel(int x, int y, int width, int height) {
		cancel = new JButton("Cancel");
		cancel.setBounds(x, y, width, height);
		cancel.setVisible(false);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				edit.setText("Edit");
				setEditable(false);
				updateFields();
				
			}
			
		});
		this.add(cancel);
		
	}

	private void createError(int x, int y, int width, int height) {
		error.setBounds(x, y, width, height);repaint();
		error.setForeground(new Color(255, 0, 0));
		this.add(error);
		
	}

	
	private void empty() {
		
		String[] keys = textFields.keySet().stream().toArray(n -> new String[n]);
		for (int i = 0; i < keys.length; i++) {
			textFields.get(keys[i]).setText("");
		}
		
		DOB.get(0).setSelectedIndex(0);
		DOB.get(1).setSelectedIndex(0);
		DOB.get(2).setSelectedIndex(0);
		
		state.setSelectedIndex(0);
		
		PIN.setText("");
		error.setText("");
		
		
	}
	private void updateFields() {
		User user = account.getUser();
		PIN.setText("");
		oldPIN.setText("");
		textFields.get("Date of Birth").setText(user.getFormattedDob());
		textFields.get("First Name").setText(user.getFirstName());
		textFields.get("Last Name").setText(user.getLastName());
		textFields.get("Street Address").setText(user.getStreetAddress());
		textFields.get("Postal Code").setText(user.getZip());
		textFields.get("City").setText(user.getCity());
		textFields.get("Phone Number1").setText(String.valueOf(user.getPhone()).substring(0, 3));
		textFields.get("Phone Number2").setText(String.valueOf(user.getPhone()).substring(3, 6));
		textFields.get("Phone Number3").setText(String.valueOf(user.getPhone()).substring(6, 10));
		state.setSelectedItem(user.getState());
		
		
	}
	
	private boolean updateAccount() {
		if (verifyInput()) {
			User user = account.getUser();
			
			if (new String(oldPIN.getPassword()).length() != 0) {
				
				if (Integer.parseInt(new String(oldPIN.getPassword())) == user.getPin()) {
					int pin = Integer.parseInt(new String(PIN.getPassword()));
					user.setPin(user.getPin(), pin);
				}
				else {
					error.setText("Invalid PIN. Must be Old then New");
					return false;
				}
			}
			
			
			long phone = Long.parseLong(textFields.get("Phone Number1").getText() + textFields.get("Phone Number2").getText() + textFields.get("Phone Number3").getText());
			String address = textFields.get("Street Address").getText();
			String city = textFields.get("City").getText();
			String stateName = state.getSelectedItem().toString();
			String zip = textFields.get("Postal Code").getText();
			
		
			
			user.setPhone(phone);
			user.setStreetAddress(address);
			user.setState(stateName);
			user.setZip(zip);
			
			account.setUser(user);
			
			
			return true;
		}
		
			
		
		return false;
		
		
		
		
	}

	private boolean verifyInput() {
		error.setText("");
		if ((new String(PIN.getPassword()).length() != 0 && new String(oldPIN.getPassword()).length() != 0)) {
			if ((new String(PIN.getPassword()).length() != 4 || new String(oldPIN.getPassword()).length() != 4)){
				error.setText("PIN must be 4 numbers long.");
				return false;
			}
		}
		if ((textFields.get("Phone Number1").getText() + textFields.get("Phone Number2").getText() + textFields.get("Phone Number3").getText()).length() != 10) {
			error.setText("Phone Number must be 10 numbers long.");
			return false;
		}
		if ((textFields.get("Postal Code").getText().length() != 5)) {
			error.setText("Postal Code must be 5 numbers long");
			return false;
		}
		if (textFields.get("First Name").getText().length() == 0 || textFields.get("Last Name").getText().length() == 0 || textFields.get("Street Address").getText().length() == 0 || textFields.get("City").getText().length() == 0) {
			error.setText("All fields are required");
			return false;
		}
		return true;
	}

	private void createState(String string, int x, int y, int widthT, int height, int widthL) {
		JLabel label = new JLabel();
		label.setBounds(x, y, widthT, height);
		label.setText(string);
		this.add(label);
		labels.put(string, label);
		
		String[] states = {"AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA",  "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE",  "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC",  "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"};
		state = new JComboBox<String>(states); 
		state.setBounds(x + widthT + 10, y, widthL, height);
		this.add(state);
		
	}

	private void createTextAndLabel(String name, int x, int y, int widthT, int height, int widthL, int limit, boolean number) {
		JLabel label = new JLabel();
		label.setBounds(x, y, widthT, height);
		label.setText(name);
		this.add(label);
		labels.put(name , label);
		
		JTextField textField = new JTextField(30);
		textField.setBounds(x + widthT + 10, y, widthL, height);
		textField.setEnabled(false);
		this.add(textField);
		if (limit > 0) {
			textField = textFieldLimit(limit, textField, number);
		}
		textFields.put(name , textField);
	}
	
	private void createDOBInput(int x, int y, int widthT, int height, int widthL) {
		JLabel label = new JLabel();
		label.setBounds(x, y, widthT, height);
		label.setText("Date of Birth");
		this.add(label);
		labels.put("Date of Birth", label);
		
		JTextField textField = new JTextField(30);
		textField.setBounds(x + widthT + 10, y, widthL, height);
		textField.setEnabled(false);
		this.add(textField);
		textFields.put("Date of Birth" , textField);
		
		
		
	}
	
	private void createPhoneNumberInput(String name, int x, int y, int widthT, int height, int widthL) {
		JLabel label = new JLabel();
		label.setBounds(x, y, widthT, height);
		label.setText(name);
		this.add(label);
		labels.put(name, label);
		
		JTextField textField = new JTextField(30);
		textField = textFieldLimit(3, textField, true);
		textField.setBounds(x + widthT + 10, y, widthL / 3, height);
		this.add(textField);
		textFields.put((name + "1"), textField);
		
		JTextField textField2 = new JTextField(30);
		textField2 = textFieldLimit(3, textField2, true);
		textField2.setBounds(x + 10 + widthT + widthL / 3, y, widthL / 3, height);
		this.add(textField2);
		textFields.put(name + "2", textField2);
		
		JTextField textField3 = new JTextField(30);
		textField3 = textFieldLimit(4, textField3, true);
		textField3.setBounds(x + 10 + widthT + 2 * widthL / 3, y, widthL / 3, height);
		this.add(textField3);
		
		textFields.put(name + "3", textField3);
	}
	
	private void createPIN(String name, int x, int y, int widthT, int height, int widthL) {
		JLabel label = new JLabel();
		label.setBounds(x, y, widthT, height);
		label.setText(name);
		this.add(label);
		labels.put(name , label);
		
		oldPIN = new JPasswordField(30);
		oldPIN.setBounds(x + widthT + 10, y, (widthL - 20 )/ 2, height);
		oldPIN.setToolTipText("old PIN");
		oldPIN.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		    	if  (!Character.isDigit(e.getKeyChar())) {
		    		e.consume();
		    	}
		        if (oldPIN.getPassword().length >= 4 ) // limit textfield to 3 characters
		        	e.consume(); 
		    }  
		    
		});
		this.add(oldPIN);
		
		PIN = new JPasswordField(30);
		PIN.setBounds(x + widthT + 10 + widthL / 2, y, (widthL - 20 )/ 2, height);
		PIN.setToolTipText("new PIN");
		PIN.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		    	if  (!Character.isDigit(e.getKeyChar())) {
		    		e.consume();
		    	}
		        if (PIN.getPassword().length >= 4 ) // limit textfield to 3 characters
		        	e.consume(); 
		    }  
		    
		});
		this.add(PIN);
		
	}
	/*
	 * CreateView is not designed to be serialized, and attempts to serialize will throw an IOException.
	 * 
	 * @param oos
	 * @throws IOException
	 */
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		throw new IOException("ERROR: The CreateView class is not serializable.");
	}
	
	private JTextField textFieldLimit(int n, JTextField txt, boolean numbers) {
		txt.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		    	if  (numbers && !Character.isDigit(e.getKeyChar())) {
		    		e.consume();
		    	}
		        if (txt.getText().length() >= n ) // limit textfield to 3 characters
		        	e.consume(); 
		    }  
		    
		});
		return txt;
	}
	
	public void setCurrentAccount() {
		this.account = manager.getAccount();
		initialize();
		updateFields();
	}
	
	private void setEditable (Boolean edit) {
		for (String name : edittable) {
			
			textFields.get(name).setEnabled(edit);
		}
		PIN.setEnabled(edit);
		oldPIN.setEnabled(edit);
		state.setEnabled(edit);
		cancel.setVisible(edit);
		getBackButton().setVisible(!edit);
		if(!edit) {
			error.setText("");
		}
	}
	
}