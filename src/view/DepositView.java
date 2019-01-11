package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ViewManager;
import model.BankAccount;


public class DepositView extends InnerView{
	
	private ViewManager manager;
	private BankAccount account;
	private JTextField input;
	private JLabel title;
	
	public DepositView(ViewManager manager) {
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
		createDepositButton();
		createInput();
		createTitle("How much would you like to Deposit?");
	}
	
	private void createDepositButton() {
		JButton button = new JButton("Deposit");
		button.setBounds(100,300,300,50);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				double amount = Double.parseDouble(input.getText());
				
				if (input.getText().matches("\\d{0,12}[.]?\\d{0,2}")) {
					account.deposit(amount);
					System.out.println(account.getBalance());
				}
				else {
					System.out.println("Invalid");
				}
			
				
				
			}
			
		});
		this.add(button);
	}
	
	private void createInput() {
		input = new JTextField();
		input.setBounds(100, 200, 300, 50);
		this.add(input);
	}
	
	

}
