package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
		this.setLayout(null);
		
	}
	
	public void makeView() {
		this.removeAll();
		// TODO
					//
					// this is a placeholder for this view and should be removed once you start
					// building the HomeView.
		createLabel("Welcome " + current.getUser().getFirstName() + " " + current.getUser().getLastName() + ".", 100, 0, 300, 35);
		createLabel("Your Account Number is: " + current.getAccountNumber() + ".", 100, 40, 300, 35);
		
		DecimalFormat balance = new DecimalFormat("###,##0.00");
		createLabel("Your Balance is: $" + balance.format(current.getBalance()), 100, 80, 300, 35);
		
		createLinkButton("Deposit", 100, 120, 300, 35, ATM.DEPOSIT_VIEW);
		createLinkButton("Withdraw", 100, 180, 300, 35, ATM.WITHDRAW_VIEW);
		createLinkButton("Transfer", 100, 240, 300, 35, ATM.TRANSFER_VIEW);
		createLinkButton("View/Edit Information", 100, 300, 300, 35, ATM.INFORMATION_VIEW);
		createCloseAccount("Close Account", 100, 360, 300, 35);
					
		logOutButton.addActionListener(this);
		logOutButton.setBounds(10, 430, 200, 40);
		this.add(logOutButton);
					
					// TODO
					//
					// this is where you should build the HomeView (i.e., all the components that
					// allow the user to interact with the ATM - deposit, withdraw, transfer, etc.).
					//
					// feel free to use my layout in LoginView as an example for laying out and
					// positioning your components.
	}
	
	
	private void createCloseAccount(String name, int x, int y, int width, int height) {
		JButton button = new JButton(name);
		button.setBounds(x, y, width, height);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				manager.closeAccount();
			
			}});
		this.add(button);
			
		
	}

	private void createLabel(String message, int x, int y, int width, int height) {
		JLabel label = new JLabel(message);
		label.setBounds(x, y, width, height);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(label);
	}
	
	private void createLinkButton(String message, int x, int y, int width, int height, String view) {
		JButton button = new JButton(message);
		button.setBounds(x, y, width, height);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(current.getUser().getStreetAddress());
				manager.switchTo(view);
				manager.updateInnerViews();
				
			}
			
		});
		this.add(button);
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
			
			manager.logOut(current);
		}
		
		// TODO
		//	
		// this is where you'll setup your action listener, which is responsible for
		// responding to actions the user might take in this view (an action can be a
		// user clicking a button, typing in a textfield, etc.).
		//
		// feel free to use my action listener in LoginView.java as an example.
	}
	
	public void setCurrentAccount() {
		this.current = manager.getAccount();
		makeView();
	}
	
	
}

