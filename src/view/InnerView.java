package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.ViewManager;
import model.BankAccount;

public class InnerView extends JPanel{
	private ViewManager manager;
	private BankAccount account;
	public InnerView(ViewManager manager) {
		
		this.manager = manager;
		this.setLayout(null);
	}
	
	public void createBackButton() {
		JButton button = new JButton("Back");
		button.setBounds(10, 430, 200, 40);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				manager.updateHome();
				manager.switchTo(ATM.HOME_VIEW);
			}
		});
		
		this.add(button);
		
	}
	
	public void createTitle(String message) {
		JLabel title = new JLabel(message);
		title.setBounds(100, 100, 300, 50);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(title);
		
	}
	
	
}
