package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.ViewManager;
import model.BankAccount;

public class InnerView extends JPanel{
	private ViewManager manager;
	private BankAccount account;
	private JLabel error;
	private JLabel balance;
	private JButton back;
	public InnerView(ViewManager manager) {
		 
		this.manager = manager;
		this.setLayout(null);
	}
	
	public void createBackButton() {
		back = new JButton("Back");
		back.setBounds(10, 430, 200, 40);
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateManagerAccount();
				manager.updateHome();
				manager.switchTo(ATM.HOME_VIEW);
			}
		});
		
		this.add(back);
		
	}
	
	public JButton getBackButton() {
		return back;
	}
	
	public void createTitle(String message) {
		JLabel title = new JLabel(message);
		title.setBounds(100, 100, 300, 50);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(title);
		
	}
	
	public void createErrorField() {
		error = new JLabel();
		error.setBounds(0,375, this.getWidth(), 35);
		error.setForeground(new Color(255,0,0));
		error.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(error);
	}
	
	public void balanceField() {
		balance = new JLabel();
		balance.setBounds(0, 50, this.getWidth(), 35);
		balance.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(balance);
	}
	
	public void updateBalance() {
		DecimalFormat b = new DecimalFormat("###,##0.00");
		this.balance.setText("Current Balance: $" + b.format(account.getBalance()));
	}
	
	public void setAccount() {
		this.account = manager.getAccount();
	}
	
	public void updateManagerAccount() {
		manager.setAccount(account);
	}
	
	
	public JLabel getError() {
		return error;
	}
	
	
}
