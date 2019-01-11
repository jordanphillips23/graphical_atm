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
public class CreateView extends JPanel implements ActionListener {
	
	private HashMap<String, JLabel> labels = new HashMap<String, JLabel>();
	private HashMap<String, JTextField> textFields = new HashMap<String, JTextField>();
	private ArrayList<JComboBox<String>> DOB = new ArrayList<JComboBox<String>>();
	private JComboBox<String> state = new JComboBox<String>();
	private JPasswordField PIN = new JPasswordField();
	private JLabel error = new JLabel("");
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	
	/**
	 * Constructs an instance (or object) of the CreateView class.
	 * 
	 * @param manager
	 */
	
	public CreateView(ViewManager manager) {
		super();
		
		this.manager = manager;
		initialize();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the CreateView components.
	 */
	
	private void initialize() {
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
		createSubmitAndCancel(50, 405, 170, 35);
		createError(50, 440, 300, 35);
		
		
		// TODO
		//
		// this is where you should build the CreateView (i.e., all the components that
		// allow the user to enter his or her information and create a new account).
		//
		// feel free to use my layout in LoginView as an example for laying out and
		// positioning your components.
	}

	private void createError(int x, int y, int width, int height) {
		error.setBounds(x, y, width, height);repaint();
		error.setForeground(new Color(255, 0, 0));
		this.add(error);
		
	}

	private void createSubmitAndCancel(int x, int y, int width, int height) {
		JButton submit = new JButton("Submit");
		submit.setBounds(x + width + 10, y, width, height);
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (updateDatabase()) {
					empty();
					manager.switchTo(ATM.HOME_VIEW);
				}
			}
		});
		
		this.add(submit);
		
		
		JButton cancel = new JButton("Cancel");
		cancel.setBounds(x, y, width, height);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				empty();
				manager.switchTo(ATM.LOGIN_VIEW);
				
			}

		});
		
		this.add(cancel);
		
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
	
	private boolean updateDatabase() {
		if (verifyInput()) {
			int pin = Integer.parseInt(new String(PIN.getPassword()));
			int dob = Integer.parseInt(DOB.get(0).getSelectedItem().toString() + DOB.get(1).getSelectedItem().toString() + DOB.get(2).getSelectedItem().toString());
			long phone = Long.parseLong(textFields.get("Phone Number1").getText() + textFields.get("Phone Number2").getText() + textFields.get("Phone Number3").getText());
			String first = textFields.get("First Name").getText();
			String last = textFields.get("Last Name").getText();
			String address = textFields.get("Street Address").getText();
			String city = textFields.get("City").getText();
			String stateName = state.getSelectedItem().toString();
			String zip = textFields.get("Postal Code").getText();
			
			Database data = manager.getDB();
			
			User user = new User(pin, dob, phone, first, last, address, city, stateName, zip);
			
			long number = 100000001;
			while (data.getAccount(number) != null) {
				number++;
			}
			BankAccount bankAccount = new BankAccount('Y', number, 0.0, user);
			
			data.insertAccount(bankAccount);
			manager.login(String.valueOf(number), PIN.getPassword());
			return true;
		}
		return false;
		
		
		
		
	}

	private boolean verifyInput() {
		error.setText("");
		if (new String(PIN.getPassword()).length() != 4) {
			error.setText("PIN must be 4 numbers long.");
			return false;
		}
		else if ((textFields.get("Phone Number1").getText() + textFields.get("Phone Number2").getText() + textFields.get("Phone Number3").getText()).length() != 10) {
			error.setText("Phone Number must be 10 numbers long.");
			return false;
		}
		else if ((textFields.get("Postal Code").getText().length() != 5)) {
			error.setText("Postal Code must be 5 numbers long");
			return false;
		}
		else if (textFields.get("First Name").getText().length() == 0 || textFields.get("Last Name").getText().length() == 0 || textFields.get("Street Address").getText().length() == 0 || textFields.get("City").getText().length() == 0) {
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
		
		String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		JComboBox<String> monthBox = new JComboBox<String>(months); 
		monthBox.setBounds(x + widthT + 10, y, 3 * widthL / 10, height);
		this.add(monthBox);
		DOB.add(monthBox);
		
		String[] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
		JComboBox<String> dayBox = new JComboBox<String>(days); 
		dayBox.setBounds(x + widthT + 10 + 3 * widthL / 10, y, 3 * widthL / 10, height);
		this.add(dayBox);
		DOB.add(dayBox);
		
		String[] years = IntStream.rangeClosed(1900, 2018).mapToObj(String::valueOf).toArray(String[]::new);
		JComboBox<String> yearBox = new JComboBox<String>(years); 
		yearBox.setBounds(x + widthT + 10 + 6 * widthL / 10, y, 4 * widthL / 10, height);
		this.add(yearBox);
		DOB.add(yearBox);
		
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
		
		PIN = new JPasswordField(30);
		PIN.setBounds(x + widthT + 10, y, widthL, height);
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
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////
	
	/*
	 * Responds to button clicks and other actions performed in the CreateView.
	 * 
	 * @param e
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// TODO
		//
		// this is where you'll setup your action listener, which is responsible for
		// responding to actions the user might take in this view (an action can be a
		// user clicking a button, typing in a textfield, etc.).
		//
		// feel free to use my action listener in LoginView.java as an example.
	}
}