package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ViewManager;
import model.BankAccount;

public class TransferView extends InnerView {
	
	private ViewManager manager;
	private BankAccount account;
	private JTextField destination;
	private JTextField amount;
	
	public TransferView(ViewManager manager) {
		super(manager);
		this.manager = manager;
	}

	
	
	
	public void setCurrentAccount(BankAccount account) {
		this.account = account;
		updateView();
	}
	
	public void updateView() {
		this.removeAll();
		this.add(new javax.swing.JLabel("hi"));
		createBackButton();
		createGetDestination();
		createGetAmount();
		createTransferButton();
	}
	
	public void createGetDestination() {
		destination = new JTextField();
		destination.setBounds(100, 100 , 300, 50);
		this.add(destination);
		
		JLabel text = new JLabel("Destination");
		text.setBounds(100, 50, 300, 50);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(text);
	}
	
	public void createGetAmount() {
		destination = new JTextField();
		destination.setBounds(100, 200 , 300, 50);
		this.add(destination);
		
		JLabel text = new JLabel("Amount");
		text.setBounds(100, 150, 300, 50);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(text);
	}
	
	private void createTransferButton() {
		JButton button = new JButton("Transfer");
		button.setBounds(100,300,300,50);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(account.getBalance());
				
			}
			
		});
		this.add(button);
	}

}
