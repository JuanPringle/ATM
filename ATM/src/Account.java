import java.text.DecimalFormat;

public class Account {
	int accountNumber;
	double balance;
	//Create Account object
	public Account(int accountNumber, double balance) {
		this.accountNumber=accountNumber;
		this.balance=balance;

	}
	//getter and setter methods
	public double getBalance() {
		DecimalFormat df= new DecimalFormat("0.00");
		String rounder;
		rounder=df.format(balance);
		balance=Double.parseDouble(rounder);
		return balance;
	}
	void updateBalance(double bal) {
		balance=bal;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
}
