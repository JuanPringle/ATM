import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

//Extra Credit I created a registering system for new users

public class ATM {
	// This is the database of Accounts that need to be checked on I used HashTables instead of ArrayList because time complexity of ArrayList is O(n) while HashTables is O(1)
	public static ArrayList<Account> accountDatabase= new ArrayList<Account>();
	public static ArrayList<Customer> customerDatabase= new ArrayList<Customer>();
	// True if there is a customer in customer database else false
	public static boolean logInCustomerNumber(String customer) {
		for(int i=0;i<customerDatabase.size();i++) {
			if(customer.equals(customerDatabase.get(i).getCustomerNum())) {
				return true;
			}
		}
		return false;
	}
	//True if the pin is in the database else false
	public static boolean logInPinNumber(String Pin) {
		for(int i=0;i<customerDatabase.size();i++) {
			if(Pin.equals(customerDatabase.get(i).getPin())) {
				return true;
			}
		}
		return false;
	}
	//Asks to get the checking account is in the account database if it is not in the database it will return -1
	public static int getCheckingAccount(String Pin) {
		for(int i=0;i<customerDatabase.size();i++) {
			if(Pin.equals(customerDatabase.get(i).getPin())) {
				for(int j=0;j<accountDatabase.size();j++) {
					if(accountDatabase.get(j).getAccountNumber()==customerDatabase.get(i).getChecking()) {
						return accountDatabase.get(j).getAccountNumber();
					}
				}
			}
		}
		return -1;
	}
	//Asks to get the savings account is in the account database if it is not in the database it will return -1
	public static int getSavingsAccount(String Pin) {
		for(int i=0;i<customerDatabase.size();i++) {
			if(Pin.equals(customerDatabase.get(i).getPin())) {
				for(int j=0;j<accountDatabase.size();j++) {
					if(accountDatabase.get(j).getAccountNumber()==customerDatabase.get(i).getSavings()) {
						return accountDatabase.get(j).getAccountNumber();
					}
				}
			}
		}
		return -1;
	}
	//User inputs customer if there an existing name or no name given it will give an error
	public static String registerCustomer(String customer) {
		if(customer.equals("")) {
			customer="-1";
			return customer;
		}
		for(int i=0;i<customerDatabase.size();i++) {
			if(customer.equals(customerDatabase.get(i).getCustomerNum())){
				return " ";
			}
		}
		return customer;
	}
	// User register their PIN number once entered it will add two accounts and one customer to the databases
	public static void registerPIN(String customer, String pin) {
		Random r= new Random();
		int check=r.nextInt(1000000);
		int save=r.nextInt(1000000);
		//Checks if the account duplicates then it will give them a new number
		for(int i=0;i<accountDatabase.size();i++) {
			if(check==accountDatabase.get(i).getAccountNumber()) {
				check=r.nextInt(1000000);
				i=0;
			}
			if(check==accountDatabase.get(i).getAccountNumber()) {
				save=r.nextInt(1000000);
				i=0;
			}
		}
		//Creates the new objects and append to the database
		Customer newCustomer=new Customer(customer,pin,check,save);
		SavingsAccount savings= new SavingsAccount(save,0.0);
		CheckingAccount checking= new CheckingAccount(check,0.0);
		customerDatabase.add(newCustomer);
		accountDatabase.add(savings);
		accountDatabase.add(checking);
		
	}
	//Withdraw the cash from the account
	public static boolean withdraw(int account, double amount) {
		DecimalFormat df= new DecimalFormat("0.00");
		double update=0;
		String rounder;
		for(int i=0;i<accountDatabase.size();i++) {
			if(account==accountDatabase.get(i).getAccountNumber()) {
				//Checks if the amount is less than balance and fixes the doubles so that it won't make the 2.999999999 error
				if(accountDatabase.get(i).getBalance()>=amount) {
					update=accountDatabase.get(i).getBalance()-amount;
					rounder=df.format(update);
					System.out.println(rounder);
					update=Double.parseDouble(rounder);
					accountDatabase.get(i).updateBalance(update);
					System.out.println(accountDatabase.get(i).getBalance());
					return true;
				}
			}
		}
		System.out.println("Insufficient Funds");
		return false;
	}
	//Ask for the balance using the corresponding account number
	public static double getAccountBalance(int account) {
		for(int i=0;i<accountDatabase.size();i++) {
			if(account==accountDatabase.get(i).getAccountNumber()) {
				return accountDatabase.get(i).getBalance();
			}
		}
		return 0;
	}
	//Deposit money to the account
	public static void deposit(int account, double amount) {
		DecimalFormat df= new DecimalFormat("0.00");
		double update=0;
		String rounder;
		for(int i=0;i<accountDatabase.size();i++) {
			//Fixes the double issues as seen from withdraw method where it fixed the 2.99999999 error
			if(account==accountDatabase.get(i).getAccountNumber()) {
				update=accountDatabase.get(i).getBalance()+amount;
				rounder=df.format(update);
				update=Double.parseDouble(rounder);
				accountDatabase.get(i).updateBalance(update);
				System.out.println(accountDatabase.get(i).getBalance());
				return;
			}
		}
		return;
	}
	//Text based GUI that compiles the program in order for it to work
	public static void textBasedGUI() {
		Scanner in=new Scanner(System.in);
		int input;
		String choice="d";
		String conversion;
		String user;
		String pin="";
		double trans;
		int account=-1;
		boolean on=true;
		//On while loop until you input c
		//Uses a bunch of switch statements so the program will run faster than using a bunch of if and else statements
		while(on) {
			System.out.println("American Bank");
			System.out.println("A=Register");
			System.out.println("B=Log In");
			System.out.println("C=Turn off");
			choice=in.next();
			switch(choice) {
			//User decides to register or login a= register b=log in
			case "a":
				//register
				System.out.println("Register Customer Number:");
				input=in.nextInt();
				conversion=String.valueOf(input);
				user=registerCustomer(conversion);
				//Checks if the user is already created else they can register PIN
				if(user.equals(" ")) {
					System.out.println("Error user already created");
					System.out.println();
				}
				else {
				//Ask for PIN and will append to corresponding databases
				System.out.println("Register PIN:");
				input=in.nextInt();
				conversion=String.valueOf(input);
				registerPIN(user,conversion);
				}
				break;
			case "b":
				//login ask to enter customer number
				System.out.println("Enter Customer Number:");
				input=in.nextInt();
				conversion=String.valueOf(input);
				//checks if the customer is in the database
				if(logInCustomerNumber(conversion)) {
				//login ask to enter PIN
					System.out.println("Enter PIN");
					input=in.nextInt();
					conversion=String.valueOf(input);
				//checks if the pin is in the database
					if(logInPinNumber(conversion)) {
						//Ask for which account the user wants to go in
						System.out.println("A=Checking");
						System.out.println("B=Savings");
						choice=in.next();
						pin=conversion;
						switch(choice) {
						//Ask for a checking account
						case "a":
							//Ask for deposit or withdraw
							account=getCheckingAccount(pin);
							System.out.println("A=Deposit");
							System.out.println("B=Withdraw");
							choice=in.next();
							switch(choice) {
							//Does a deposit
							case "a":
								System.out.println("Enter Amount:");
								System.out.println("Balance: "+getAccountBalance(account));
								trans=in.nextDouble();
								deposit(account,trans);
								System.out.println("New Balance: "+getAccountBalance(account));
								break;
							// Does a withdraw
							case "b":
								System.out.println("Enter Amount:");
								trans=in.nextDouble();
								withdraw(account,trans);
								System.out.println("New Balance: "+getAccountBalance(account));
								break;
								default:
									System.out.println("Invalid Input");
							}
							break;
						case "b":
							account=getCheckingAccount(pin);
							System.out.println("A=Deposit");
							System.out.println("B=Withdraw");
							choice=in.next();
							switch(choice) {
							case "a":
								System.out.println("Enter Amount");
								System.out.println("Balance: "+getAccountBalance(account));
								trans=in.nextDouble();
								deposit(account,trans);
								System.out.println("New Balance: "+getAccountBalance(account));
								break;
							case "b":
								System.out.println("Enter Amount");
								trans=in.nextDouble();
								withdraw(account,trans);
								System.out.println("New Balance: "+getAccountBalance(account));
								break;
						default:
							System.out.println("Invalid input");
							System.out.println();
						}
					}
					}
					else {
						System.out.println("Invalid PIN");
						System.out.println();
					}
				}
				else {
					System.out.println("Invalid Username");
					System.out.println();
				}
				break;
			case "c":
				on=false;
				System.out.println("Goodbye");
				break;
			default:
				System.out.println("Invalid input");
				System.out.println();
			}
		}
	}
	public static void main(String[] args) throws FileNotFoundException {
		//File of Customer Accounts
		File file= new File("C:\\Users\\John\\Desktop\\John Pingol.txt");
		Scanner scan=new Scanner(file);
		
		while(scan.hasNextLine()) {
			String info = scan.nextLine();
			String[] customer = info.split(",");
			String param1 = customer[0];
			String param2 = customer[1];
			String param3 = customer[2];
			String param4 = customer[3];
			int para3= Integer.parseInt(param3);
			int para4= Integer.parseInt(param4);
			Customer newCustomer=new Customer(param1,param2,para3,para4);
		}
		//Backup just in case customer file doesn't work
		populateAccountDatabase();
		populateCustomerDatabase();
		textBasedGUI();
	}
	// Create account database
	private static void populateAccountDatabase() {
		accountDatabase.add(new CheckingAccount(476812, 10.00));
		accountDatabase.add(new CheckingAccount(1234, 2.00));
		accountDatabase.add(new SavingsAccount(92849, 5.00));
		accountDatabase.add(new SavingsAccount(129874, 6.50));
	}
	//Create customer database
	private static void populateCustomerDatabase() {
		customerDatabase.add(new Customer("2574", "274689", 476812,92849));
		customerDatabase.add(new Customer("22480", "128940", 1234,129874));
		customerDatabase.add(new Customer("25724", "27689", 42242,95170));
		customerDatabase.add(new Customer("2280", "12894", 134,1224));
		customerDatabase.add(new Customer("257454", "227489", 76812,9249));
		customerDatabase.add(new Customer("2248", "12840", 234,12984));
	}
}