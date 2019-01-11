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
			hv.setCurrentAccount(account);
			
			passAccountDeposit(account);
			passAccountWithdraw(account);
			passAccountTransfer(account);
			
			LoginView lv = ((LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX]);
			lv.updateErrorMessage("");
		}
	}
	
	
	public void passAccountDeposit(BankAccount current) {
		DepositView dv = ((DepositView) views.getComponents()[ATM.DEPOSIT_VIEW_INDEX]);
		dv.setCurrentAccount(current);
	}
	
	public void passAccountWithdraw(BankAccount current) {
		WithdrawView wv = ((WithdrawView) views.getComponents()[ATM.WITHDRAW_VIEW_INDEX]);
		wv.setCurrentAccount(current);
	}
	public void passAccountTransfer(BankAccount current) {
		TransferView tv = ((TransferView) views.getComponents()[ATM.TRANSFER_VIEW_INDEX]);
		tv.setCurrentAccount(current);
	}
	
	public void updateHome() {
		HomeView hv = ((HomeView) views.getComponents()[ATM.HOME_VIEW_INDEX]);
		hv.makeView();
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
	
	public void logOut() {
		this.switchTo(ATM.LOGIN_VIEW);
		LoginView lv = ((LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX]);
		lv.logOut();
		this.account = null;
		this.destination = null;
		
	}
	
	public Database getDB() {
		return db;
	}
}
