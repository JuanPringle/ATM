import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GUI implements ActionListener {
	// Instance variables in order for my GUI to work
	private JLabel numbers;
	private JLabel labelA;
	private JLabel labelB;
	private JFrame frame;
	private JPanel panel;
	private String str="";
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JButton button6;
	private JButton button7;
	private JButton button8;
	private JButton button9;
	private JButton button0;
	private JButton decimal;
	private JButton delete;
	private JButton C;
	private JButton B;
	private JButton A;
	private static ATM atm;
	private int count;
	private String customer;
	private String pin;
	private int account;
	private double transaction;
	private double balance;
	// Will make the setup for the GUI
	public GUI(){
		//instantized the variables
		frame = new JFrame();
		panel = new JPanel();
		button1 = new JButton("1");
		button2 = new JButton("2");
		button3 = new JButton("3");
		button4 = new JButton("4");
		button5 = new JButton("5");
		button6 = new JButton("6");
		button7 = new JButton("7");
		button8 = new JButton("8");
		button9 = new JButton("9");
		button0 = new JButton("0");
		delete = new JButton("Delete");
		decimal = new JButton(".");
		A = new JButton("A");
		B = new JButton("B");
		C = new JButton("C");
		labelA = new JLabel("A=Log In");
		labelB = new JLabel("B=Register C=Exit");
		numbers= new JLabel("American Bank");
		//Create the components for the GUI
		panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
		panel.setLayout(new GridLayout(7,3));
		panel.add(labelA);
		panel.add(numbers);
		panel.add(labelB);
		panel.add(button7);
		panel.add(button8);
		panel.add(button9);
		panel.add(button4);
		panel.add(button5);
		panel.add(button6);
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(decimal);
		panel.add(button0);
		panel.add(delete);
		panel.add(A);
		panel.add(B);
		panel.add(C);
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("ATM");
		frame.pack();
		frame.setVisible(true);
		delete.setBackground(Color.red);
		A.setBackground(Color.yellow);
		B.setBackground(Color.yellow);
		C.setBackground(Color.yellow);
		//Creates the functionality
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		button7.addActionListener(this);
		button8.addActionListener(this);
		button9.addActionListener(this);
		button0.addActionListener(this);
		delete.addActionListener(this);
		decimal.addActionListener(this);
		A.addActionListener(this);
		B.addActionListener(this);
		C.addActionListener(this);
	}
	// Creates the GUI
	@Override
	// Makes functionality to the number pad and text for ATM
	public void actionPerformed(ActionEvent e) {
		/* For lines 122-260
		 * case -2: Turns off the simulator and turns on when Press A
		 * case -1: Asks you to go back to the start
		 * case 0: Asks to log in or asks to register both buttons used
		 * case 1: Checks if customer was already created else ask for a pin number
		 * case 2: Puts in the information in the customer database and the two accounts in the account database
		 * case 3: Allow the user to login his/her customer number
		 * case 4: Allow the user to login his/her PIN
		 * case 5: Allows the user to ask for checking or saving both buttons used
		 * case 6: Allows the user to ask for making a deposit or withdraw money
		 * case 7: The user makes the transaction for withdraw
		 * case 8: The user makes the transaction for deposit
		 */
			if(e.getSource()==A) {
				switch(count) {
				case -2:
					labelA.setText("A=Log In");
					labelB.setText("B=Register C=Exit");
					str="";
					count=0;
					break;
				case 0:
					labelA.setText("Enter Customer Number");
					labelB.setText("A=OK C=Exit");
					count=3;
					break;
				case 1:
					customer=str;
					str="";
					System.out.println("-"+customer+"-");
					if(atm.registerCustomer(customer).equals("-1")) {
						labelA.setText("Invalid Customer");
						labelB.setText("B=Back to Start C=Exit");
						count=-1;
					}
					else {
					if(atm.registerCustomer(customer).equals(" ")) {
						labelA.setText("Customer already created");
						labelB.setText("B=Back to Start C=Exit");
						count=-1;
					}
					else {
						count++;
						labelA.setText("Enter PIN");
						labelB.setText("A=OK C=Exit");
					}
					}
					break;
				case 2:
					atm.registerPIN(customer,str);
					labelA.setText("Account Registered");
					labelB.setText("B= Back to Start C=Exit");
					count=-1;
					customer="";
					str="";
					break;
				case 3:
					if(atm.logInCustomerNumber(str)) {
						labelA.setText("Enter PIN number");
						labelB.setText("A=OK C=Exit");
						str="";
						count++;
					}
					else {
						labelA.setText("Invalid Customer Number");
						labelB.setText("B= Back to Start C=Exit");
						str="";
						count=-1;
					}
					break;
				case 4:
					if(atm.logInPinNumber(str)) {
						labelA.setText("A=Savings Account");
						labelB.setText("B=Checking Account C=Exit");
						count++;
						pin=str;
						str="";
					}
					else {
						labelA.setText("Invalid PIN number");
						labelB.setText("B= Back to Start");
						count=-1;
						str="";
					}
					break;
				case 5:
					account=atm.getSavingsAccount(pin);
					labelA.setText("A=Deposit");
					labelB.setText("B=Withdraw C=Exit");
					count++;
					break;
				case 6:
					balance=atm.getAccountBalance(account);
					labelA.setText("Enter Amount:");
					labelB.setText("A=OK Balance: "+balance+" C=Exit");
					count=8;
					break;
				case 7:
					transaction=Double.parseDouble(str);
					if(atm.withdraw(account,transaction)) {
						balance=atm.getAccountBalance(account);
						labelA.setText("Balance: "+balance);
						labelB.setText("A=Savings B=Checking C=Exit");
						count=5;
						str="";
					}
					else {
						labelA.setText("Insuffient Funds");
						labelB.setText("A=Savings B=Checking C=Exit");
						count=5;
						str="";
					}
					break;
				case 8:
					transaction=Double.parseDouble(str);
					atm.deposit(account,transaction);
					balance=atm.getAccountBalance(account);
					labelA.setText("Balance: "+balance+" C=Exit");
					labelB.setText("A=Savings B=Checking C=Exit");
					count=5;
					str="";
					break;
				}
			}
			if(e.getSource()==B) {
				switch(count) {
				case -1:
					labelA.setText("A=Log In");
					labelB.setText("B=Register C=Exit");
					str="";
					count++;
					break;
				case 0:
					count++;
					labelA.setText("Register Customer");
					labelB.setText("A=OK C=Exit");
					break;
				case 5:
					account=atm.getCheckingAccount(pin);
					labelA.setText("A=Deposit");
					labelB.setText("B=Withdraw C=Exit");
					count++;
					break;
				case 6:
					balance=atm.getAccountBalance(account);
					labelA.setText("Enter Amount:");
					labelB.setText("A=OK: "+balance+" C=Exit");
					count++;
					break;
					
				}	
			}
			// Allow the user to Exit out of the program
			if(e.getSource()==C) {
				count=-2;
				numbers.setText("A=Turn On");
				labelA.setText("Goodbye");
				labelB.setText("A=Turn On");
			}
			if(e.getSource()==decimal) {
				if(str.equals("")||str.equals(" ")) {
					str="0.";
				}
				if (!str.contains(".")) {
					str=str+".";
				}
			}
			// check if there are 2 decimal places already, if so, do nothing, else proceed
			if(str.length()> 0 && str.indexOf('.') > 0 && (str.length() - 2) == (str.indexOf('.') + 1)) {
				// does nothing
			}
			else{
				//Lines 282-326
				//Allow the buttons to append to string that shows on display
				if(e.getSource()==button1) {
					if(count!=0) {
					str=str+"1";
					}
				}
				if(e.getSource()==button2) {
					if(count!=0) {
						str=str+"2";
					}
				}
				if(e.getSource()==button3) {
					if(count!=0) {
						str=str+"3";
					}
				}
				if(e.getSource()==button4) {
					if(count!=0) {
						str=str+"4";
					}
				}
				if(e.getSource()==button5) {
					if(count!=0) {
						str=str+"5";
					}
				}
				if(e.getSource()==button6) {
					if(count!=0) {
						str=str+"6";
					}
				}
				if(e.getSource()==button7) {
					if(count!=0) {
						str=str+"7";
					}
				}
				if(e.getSource()==button8) {
					if(count!=0) {
						str=str+"8";
					}
				}
				if(e.getSource()==button9) {
					if(count!=0) {
						str=str+"9";
					}
				}
				//Exception the 0 button can't be press when the string is empty
				if(e.getSource()==button0) {
					if(count!=0) {
						if(str.equals("")) {
							
						}
						else{
							str=str+"0";
						}
					}
				}

			}
			//Allow user to remove the last number from input
		if(e.getSource()==delete) {
			if(str.equals("")||str.equals(" ")) {
				str=" ";
			}
			else {
			str=str.substring(0,str.length()-1);
			}
		}
		if(count!=0) {
		numbers.setText(str);
		}
	}
	//Allows the GUI to run
	public static void main(String[] args) throws FileNotFoundException {
		//Makes ATM objects
		atm = new ATM();
		//File of the customers
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
		//Backup of customer objects
		atm.registerPIN("123","124012");
		atm.registerPIN("168249","17492");
		atm.registerPIN("1481","1212");
		atm.registerPIN("1624919","187502");
		//Creates GUI
		new GUI();
	}
}
