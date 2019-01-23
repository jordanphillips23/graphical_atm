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

	
	
	
	public void setCurrentAccount() {
		this.account = manager.getAccount();
		updateView();
		
		
	}
	
	
	private void createWithdrawButton() {
		JButton button = new JButton("Withdraw");
		button.setBounds(100,300,300,50);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (input.getText().matches("\\d{0,12}[.]?\\d{0,2}")) {
					getError().setText("");
					double amount = Double.parseDouble(input.getText());
					switch(account.withdraw(amount)) {
					case ATM.INVALID_AMOUNT:
						getError().setText("Input must be greater than 0");
						break;
					case ATM.INSUFFICIENT_FUNDS:
						getError().setText("Insufficient Funds");
						break;
					case ATM.SUCCESS:
						updateBalance();
						break;
					}
					System.out.println(account.getBalance());
				}
				else {
					getError().setText("Invalid Input must be in the form ###,###.##");
					System.out.println("Invalid");
				}
				
			}
			
		});
		this.add(button);
	}
	
	public void updateView() {
		this.removeAll();
		createBackButton();
		createInput();
		createWithdrawButton();
		createErrorField();
		createTitle("How much would you like to Withdraw?");
		balanceField();
		setAccount();
		updateBalance();
		
	}
	
	private void createInput() {
		input = new JTextField();
		input.setBounds(100, 200, 300, 50);
		this.add(input);
	}

}

