package controller;

import java.awt.CardLayout;
import java.awt.Container;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import data.Database;
import model.BankAccount;
import view.ATM;
import view.DepositView;
import view.HomeView;
import view.InformationView;
import view.LoginView;
import view.TransferView;
import view.WithdrawView;

public class ViewManager {
	
	private Container views;				// the collection of all views in the application
	private Database db;					// a reference to the database
	private BankAccount account;			// the user's bank account
	private BankAccount destination;		// an account to which the user can transfer funds
	
	/**
	 * Constructs an instance (or object) of the ViewManager class.
	 * 
	 * @param layout
	 * @param container
	 */
	
	public ViewManager(Container views) {
		this.views = views;
		this.db = new Database();
	}
	
	///////////////////// INSTANCE METHODS ////////////////////////////////////////////
	
	/**
	 * Routes a login request from the LoginView to the Database.
	 * 
	 * @param accountNumber
	 * @param pin
	 */
	
	public void login(String accountNumber, char[] pin) {
		account = db.getAccount(Long.valueOf(accountNumber), Integer.valueOf(new String(pin)));
		
		if (account == null) {
			LoginView lv = ((LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX]);
			lv.updateErrorMessage("Invalid account number and/or PIN.");
		} else {
			switchTo(ATM.HOME_VIEW);
			HomeView hv = ((HomeView) views.getComponents()[ATM.HOME_VIEW_INDEX]);
			hv.setCurrentAccount();
			
			updateAccounts();
		
			
			LoginView lv = ((LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX]);
			lv.updateErrorMessage("");
		}
	}
	
	
	public void updateAccounts() {
		DepositView dv = ((DepositView) views.getComponents()[ATM.DEPOSIT_VIEW_INDEX]);
		dv.setCurrentAccount();
		
		WithdrawView wv = ((WithdrawView) views.getComponents()[ATM.WITHDRAW_VIEW_INDEX]);
		wv.setCurrentAccount();
		
		TransferView tv = ((TransferView) views.getComponents()[ATM.TRANSFER_VIEW_INDEX]);
		tv.setCurrentAccount();
		
		InformationView iv = ((InformationView) views.getComponents()[ATM.INFORMATION_VIEW_INDEX]);
		iv.setCurrentAccount();
		
	}

	public void passAccountDeposit(BankAccount current) {
		
	}
	
	public void passAccountWithdraw(BankAccount current) {
		
	}
	public void passAccountTransfer(BankAccount current) {
		
	}
	public void passAccountInfo(BankAccount current) {
		
	}
	
	public void setAccount(BankAccount current) {
		this.account = current;
	}
	
	public BankAccount getAccount() {
		return account;
	}
	
	public void updateHome() {
		HomeView hv = ((HomeView) views.getComponents()[ATM.HOME_VIEW_INDEX]);
		hv.makeView();
	}
	
	public void updateInnerViews() {
		DepositView dv = ((DepositView) views.getComponents()[ATM.DEPOSIT_VIEW_INDEX]);
		dv.updateBalance();
		WithdrawView wv = ((WithdrawView) views.getComponents()[ATM.WITHDRAW_VIEW_INDEX]);
		wv.updateBalance();
		TransferView tv = ((TransferView) views.getComponents()[ATM.TRANSFER_VIEW_INDEX]);
		tv.updateBalance();
	}
	/**
	 * Switches the active (or visible) view upon request.
	 * 
	 * @param view
	 */
	
	public void switchTo(String view) {
		((CardLayout) views.getLayout()).show(views, view);
	}
	
	/**
	 * Routes a shutdown request to the database before exiting the application. This
	 * allows the database to clean up any open resources it used.
	 */
	
	public void shutdown() {
		try {			
			int choice = JOptionPane.showConfirmDialog(
				views,
				"Are you sure?",
				"Shutdown ATM",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);
			
			if (choice == 0) {
				db.shutdown();
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeAccount() {
		try {
			int choice = JOptionPane.showConfirmDialog(
					views,
					"Are you sure you want to permanently close your account?",
					"Close ATM Account",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE
				);
				
				if (choice == 0) {
					db.closeAccount(account);
					account.setStatus('N');
					logOut(account);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		
	}
	
	public void logOut(BankAccount current) {
		this.account = current;
		this.switchTo(ATM.LOGIN_VIEW);
		LoginView lv = ((LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX]);
		lv.logOut();
		db.updateAccount(this.account);
		this.account = null;
		this.destination = null;
		
	}
	
	public Container getViews() {
		return views;
	}
	
	public Database getDB() {
		return db;
	}
}
