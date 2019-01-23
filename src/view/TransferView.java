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

	
	
	
	public void setCurrentAccount() {
		this.account = manager.getAccount();
		updateView();
	}
	 
	public void updateView() {
		this.removeAll();
		this.add(new javax.swing.JLabel("hi"));
		createBackButton();
		createGetDestination();
		createGetAmount();
		createTransferButton();
		createErrorField();
		balanceField();
		setAccount();
		updateBalance();
	}
	
	public void createGetDestination() {
		destination = new JTextField();
		destination.setBounds(100, 150 , 300, 50);
		this.add(destination);
		
		JLabel text = new JLabel("Destination");
		text.setBounds(100, 100, 300, 50);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(text);
	}
	
	public void createGetAmount() {
		amount = new JTextField();
		amount.setBounds(100, 250 , 300, 50);
		this.add(amount);
		
		JLabel text = new JLabel("Amount");
		text.setBounds(100, 200, 300, 50);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(text);
	}
	
	private void createTransferButton() {
		JButton button = new JButton("Transfer");
		button.setBounds(100,350,300,50);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (destination.getText().matches("\\d{9}")) {
					BankAccount dest = manager.getDB().getAccount(Long.parseLong(destination.getText()));
					System.out.println(dest);
					if (dest != null) {
						System.out.println(amount.getText().matches("\\d{0,12}[.]?\\d{0,2}"));
						if (amount.getText().matches("\\d{0,12}[.]?\\d{0,2}")) {
							double transferAmount = Double.parseDouble(amount.getText());
							int value = account.transfer(dest, transferAmount);
							if (value == ATM.INSUFFICIENT_FUNDS) {
								getError().setText("Insufficient Funds");
							}
							else {
								updateBalance();
								getError().setText("");
								manager.getDB().updateAccount(dest);
							}
						}
						else {
							getError().setText("Invalid Account");
						}
					}
					else {
						getError().setText("Invalid Account Number");
					}
				}
				else {
					getError().setText("Account Number Must be exactly 9 digits.");
				}
				
				
				
			}
			
		});
		this.add(button);
	}

}
