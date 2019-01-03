package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.ViewManager;
import model.BankAccount;

@SuppressWarnings("serial")
public class HomeView extends JPanel implements ActionListener {
	
	private ViewManager manager;	// manages interactions between the views, model, and database
	private JButton logOutButton = new JButton("Log Out");
	private BankAccount current = null;
	
	
	/**
	 * Constructs an instance (or objects) of the HomeView class.
	 * 
	 * @param manager
	 */
	
	public HomeView(ViewManager manager) {
		super();
		
		this.manager = manager;
		initialize();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the HomeView components.
	 */
	
	private void initialize() {
		
	}
	
	private void makeView() {
		this.removeAll();
		// TODO
					//
					// this is a placeholder for this view and should be removed once you start
					// building the HomeView.
					
		this.add(new javax.swing.JLabel("Welcome " + current.getUser().getFirstName() + " " + current.getUser().getLastName() + ".", javax.swing.SwingConstants.CENTER));
					
		logOutButton.addActionListener(this);
		this.add(logOutButton, javax.swing.SwingConstants.CENTER);
					
					// TODO
					//
					// this is where you should build the HomeView (i.e., all the components that
					// allow the user to interact with the ATM - deposit, withdraw, transfer, etc.).
					//
					// feel free to use my layout in LoginView as an example for laying out and
					// positioning your components.
	}
	
	/*
	 * HomeView is not designed to be serialized, and attempts to serialize will throw an IOException.
	 * 
	 * @param oos
	 * @throws IOException
	 */
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		throw new IOException("ERROR: The HomeView class is not serializable.");
	}
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////
	
	/*
	 * Responds to button clicks and other actions performed in the HomeView.
	 * 
	 * @param e
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == logOutButton) {
			
			manager.logOut();
		}
		
		// TODO
		//
		// this is where you'll setup your action listener, which is responsible for
		// responding to actions the user might take in this view (an action can be a
		// user clicking a button, typing in a textfield, etc.).
		//
		// feel free to use my action listener in LoginView.java as an example.
	}
	
	public void setCurrentAccount(BankAccount current) {
		System.out.println(current.toString());
		this.current = current;
		makeView();
	}
	
	
}

