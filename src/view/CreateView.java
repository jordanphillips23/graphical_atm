package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ViewManager;

@SuppressWarnings("serial")
public class CreateView extends JPanel implements ActionListener {
	
	private HashMap<String, JLabel> labels = new HashMap<String, JLabel>();
	private HashMap<String, JTextField> textFields = new HashMap<String, JTextField>();
	private ArrayList<JComboBox<String>> DOB = new ArrayList<JComboBox<String>>();
	
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
		
		createTextAndLabel("First Name", 50, 25, 100, 35, 240);
		createTextAndLabel("Last Name", 50, 70, 100, 35, 240);
		createDOBInput(50, 105, 100, 35, 240);
		createPhoneNumberInput("Phone Number", 50, 150, 100, 35, 240);
		createTextAndLabel("Street Address", 50, 195, 100, 35, 240);
		createTextAndLabel("City", 50, 240, 100, 35, 240);
		// State
		createTextAndLabel("Postal Code", 50, 330, 100, 35, 240);
		// PIN
		
		
		// TODO
		//
		// this is where you should build the CreateView (i.e., all the components that
		// allow the user to enter his or her information and create a new account).
		//
		// feel free to use my layout in LoginView as an example for laying out and
		// positioning your components.
	}
	
	private void createTextAndLabel(String name, int x, int y, int widthT, int height, int widthL) {
		JLabel label = new JLabel();
		label.setBounds(x, y, widthT, height);
		label.setText(name);
		this.add(label);
		labels.put("name" , label);
		
		JTextField textField = new JTextField(30);
		textField.setBounds(x + widthT + 10, y, widthL, height);
		this.add(textField);
		textFields.put("name" , textField);
	}
	
	private void createDOBInput(int x, int y, int widthT, int height, int widthL) {
		JLabel label = new JLabel();
		label.setBounds(x, y, widthT, height);
		label.setText("Date of Birth");
		this.add(label);
		labels.put("Date of Birth", label);
		
		String[] months = IntStream.rangeClosed(1, 12).mapToObj(String::valueOf).toArray(String[]::new);
		JComboBox<String> monthBox = new JComboBox<String>(months); 
		monthBox.setBounds(x + widthT + 10, y, 3 * widthL / 10, height);
		this.add(monthBox);
		DOB.add(monthBox);
		
		String[] days = IntStream.rangeClosed(1, 31).mapToObj(String::valueOf).toArray(String[]::new);
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
		textField = textFieldLimit(3, textField);
		textField.setBounds(x + widthT + 10, y, widthL / 3, height);
		this.add(textField);
		textFields.put(name + "1", textField);
		
		JTextField textField2 = new JTextField(30);
		textField2 = textFieldLimit(3, textField2);
		textField2.setBounds(x + 10 + widthT + widthL / 3, y, widthL / 3, height);
		this.add(textField2);
		textFields.put(name + "2", textField);
		
		JTextField textField3 = new JTextField(30);
		textField3 = textFieldLimit(4, textField3);
		textField3.setBounds(x + 10 + widthT + 2 * widthL / 3, y, widthL / 3, height);
		this.add(textField3);
		
		textFields.put(name + "3", textField);
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
	
	private JTextField textFieldLimit(int n, JTextField txt) {
		txt.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
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