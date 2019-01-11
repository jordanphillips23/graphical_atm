package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.ViewManager;
import model.BankAccount;

public class WithdrawView extends InnerView {
	
	private ViewManager manager;
	private BankAccount account;
	private JTextField input;
	
	public WithdrawView(ViewManager manager) {
		super(manager);
		this.manager = manager;
	}

	
	
	
	public void setCurrentAccount(BankAccount account) {
		this.account = account;
		updateView();
		createInput();
		createWithdrawButton();
		createTitle("How much would you like to Withdraw?");
		
	}
	
	private void createWithdrawButton() {
		JButton button = new JButton("Withdraw");
		button.setBounds(100,300,300,50);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				account.withdraw(1);
				System.out.println(account.getBalance());
				
			}
			
		});
		this.add(button);
	}
	
	public void updateView() {
		this.removeAll();
		this.add(new javax.swing.JLabel("hi"));
		createBackButton();
	}
	
	private void createInput() {
		input = new JTextField();
		input.setBounds(100, 200, 300, 50);
		this.add(input);
	}

}

