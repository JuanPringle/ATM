
public class Customer {
	private int checkingAccountNumber;
	private int savingsAccountNumber;
	private String customerNum;
	private String pin;
	//Creates customer object
	public Customer(String customerNum, String pin, int checkingAccountNumber, int savingsAccountNumber) {
		this.customerNum=customerNum;
		this.pin=pin;
		this.checkingAccountNumber=checkingAccountNumber;
		this.savingsAccountNumber=savingsAccountNumber;
	}
	//getter methods
	public int getChecking() {
		return checkingAccountNumber;
	}
	public int getSavings() {
		return savingsAccountNumber;
	}
	public String getPin() {
		return pin;
	}
	public String getCustomerNum() {
		return customerNum;
	}
}
