//Elian Tiudic 
//Extra Credit #2
import java.util.Scanner;
import java.io.*;
public class bank_accounts {

	public static void main(String[] args) throws IOException {
		
		//creating input file and scanner for operations
		File input=new File("input.txt");
		Scanner in=new Scanner(input);
		
		//creating the output object
		PrintWriter outputFile=new PrintWriter("output.txt");
		
		//declaring variables
		final int MAX_NUM=100;
		int numAccts;
		boolean notDone=true;
		char selection;
		
		int[] acctNumber; //create reference variable
		acctNumber = new int[MAX_NUM]; //create the array
		
		double[] balance; //create reference variable
		balance = new double[MAX_NUM]; //create the array
		
		//reading and printing the original accounts
		numAccts=readAccts(acctNumber,balance,MAX_NUM);
		printAccts(outputFile,acctNumber,balance,numAccts);
		outputFile.println();
		outputFile.println();
		
		//loop for operations
		do {
			
			//print the menu
			menu();
			//user makes the selection
			selection=in.next().charAt(0);
			
			switch(selection) {
			
				//if the user enters an invalid value
				default:
					outputFile.println("Invalid entry! Try again");
					outputFile.println();
					outputFile.println();
					break;
				
				//the quit operation
				case'q':
				case'Q':
					outputFile.println("Program is terminating!");
					outputFile.println();
					outputFile.println();
					notDone=false;
					break;
					
				//the withdraw operation
				case'w':
				case'W':
					withdrawal(in,outputFile,acctNumber,balance,numAccts);
					outputFile.println();
					outputFile.println();
					break;
					
				//the deposit operation
				case'd':
				case'D':
					deposit(in,outputFile,acctNumber,balance,numAccts);
					outputFile.println();
					outputFile.println();
					break;
					
				//the balance operation
				case'b':
				case'B':
					balance(in,outputFile,acctNumber,balance,numAccts);
					outputFile.println();
					outputFile.println();
					break;
					
				//the new account operation
				case'n':
				case'N':
					numAccts=newAcct(in,outputFile,acctNumber,
							balance,numAccts);
					outputFile.println();
					outputFile.println();
					break;
					
				//the delete account operation
				case'x':
				case'X':
					numAccts=deleteAcct(in,outputFile,acctNumber,
							balance,numAccts);
					outputFile.println();
					outputFile.println();
					break;
			}
			
		}while(notDone);//program loops while notDone is true
		
		//printing the final database of accounts
		outputFile.println("Final database of accounts:");
		outputFile.println();
		printAccts(outputFile,acctNumber,balance,numAccts);
		
		//closing the input and output files
		in.close();
		outputFile.close();
	}

	/* method menu()
	 * 
	 * Input: none
	 * 
	 * Process:the method prints a menu for the user
	 * 
	 * Output:instruction for the user are printed to
	 * the console
	 */
	public static void menu() {
		System.out.println("Choose an option from below:");
		System.out.println("W - Withdrawal");
		System.out.println("D - Deposit");
		System.out.println("N - New account");
		System.out.println("B - Balance");
		System.out.println("X - Delete account");
		System.out.println("Q - Quit");
	}
	
	/* method readAccts()
	 * 
	 * Input: acctNum-reference to the array that holds the account number
	 * balance-reference to the array that holds the balance
	 * max_num-the max size of the arrays
	 * 
	 * Process: method reads input from a file and fills the
	 * arrays with corresponding values
	 * 
	 * Output:the method returns the number of accounts
	 */
	public static int readAccts(int[] acctNum,
			double[] balance,int max_num) throws IOException {
		
		File inputOriginal=new File("inputoriginal.txt");
		Scanner scan=new Scanner(inputOriginal);
		
		int numAccts=0;
		
		while(scan.hasNext()) {
			System.out.println("Enter account number: ");
			acctNum[numAccts]=scan.nextInt();
			System.out.println("Enter balance: ");
			balance[numAccts]=scan.nextDouble();
			numAccts++;
		}
		scan.close();
		return numAccts;
	}
	
	/* method printAccts()
	 * 
	 * Input: acctNum-reference to the array that holds the account number
	 * balance-reference to the array that holds the balance
	 * numAccts-the number of accounts to be printed
	 * 
	 * Process:method goes through the arrays and prints
	 * their values
	 * 
	 * Output:the method prints the values of the arrays in a 
	 * neat table
	 */
	public static void printAccts(PrintWriter output, int[] acctNum,
			double[] balance, int numAccts) {
		
		output.println("Account          Balance");
		output.println();
		
		//printing each existing account
		for (int i=0;i<numAccts;i++)
			output.printf("%5d %10s $%-1.2f \n",acctNum[i]," ",balance[i]);
	}
	
	/* method findAcct()
	 * 
	 * Input: acctNum-reference to the array that holds the account number
	 * numAccts-the number of accounts existent
	 * account_number-the account that is to be found
	 * 
	 * Process:method goes through the array until it finds
	 * the value it is looking for, in which case it stores the index
	 * where it was found in the account variable
	 * 
	 * Output:the method returns the index where account was found, or
	 * -1 if the account is inexistent
	 */
	public static int findAcct(int[] acctNum, int numAccts,
			int account_number) {
		
		int i, account=-1;
		
		//checking if the account exists
		for(i=0; i<numAccts; i++)
			if(acctNum[i]==account_number)
			{
				account=i;
			}
		return account;
	}
	
	/* method balance()
	 * 
	 * Input: 
	 * scan-reference to the scanner opened in main
	 * output-reference to the output file
	 * acctNum-reference to the array that holds the account number
	 * balance-reference to the array that holds the balance
	 * numAccts-the number of accounts existent
	 * 
	 * Process:user inputs the desired account number, then
	 * the method calls the findAcct() method and uses the 
	 * return value to test if the account exists. If the 
	 * account exists, its balance is printed
	 * 
	 * Output:the method prints a receipt that includes type of
	 * operation, account number, and the balance, or if the account
	 * is inexistent, an error message
	 */
	public static void balance(Scanner scan,PrintWriter output,
			int[] acctNum, double[] balance, int numAccts) {
		
		//entering the desired account
		int account_number=scan.nextInt();
		int accountIndex=findAcct(acctNum, numAccts, account_number);
		
		output.println("Operation:Balance");
		output.println("Account number: "+account_number);
		
		//testing if the account exists and printing accordingly
		if(accountIndex==-1)
			output.println("Error! Account doesn't exist");
		else {
			output.println("Current balance: $"+balance[accountIndex]);
		}
	}

	/* method deposit()
	 * 
	 * Input: 
	 * scan-reference to the scanner opened in main
	 * output-reference to the output file
	 * acctNum-reference to the array that holds the account number
	 * balance-reference to the array that holds the balance
	 * numAccts-the number of accounts existent
	 * 
	 * Process:user inputs the desired account number, then
	 * the method calls the findAcct() method and uses the 
	 * return value to test if the account exists. If the 
	 * account exists, the method tests if the amount to be
	 * deposited is valid, and subsequently it adds this value
	 * to the original balance
	 * 
	 * Output:the method prints a receipt that includes type of
	 * operation, account number, original balance, amount to be
	 * deposited, and the new balance, or if the account
	 * is inexistent, an error message
	 */
	public static void deposit(Scanner scan, PrintWriter output,
			int[] acctNum, double[] balance, int numAccts) {
	
		int account_number=scan.nextInt();
		double deposit=scan.nextDouble();
		int accountIndex=findAcct(acctNum, numAccts, account_number);
		
		//printing the receipt
		output.println("Operation:Deposit");
		output.println("Account number: "+account_number);
		output.println("Amount to deposit: $"+deposit);
	
		//testing if the user entered valid input
		if (deposit>=0) {
			
			//testing if the account exists
			if(accountIndex==-1)
				output.println("Error! Account doesn't exist.");
			else {
				output.println("Current balance: $"+balance[accountIndex]);
				balance[accountIndex]=balance[accountIndex]+deposit;
				output.printf("New balance: $%.2f \n",balance[accountIndex]);
			}
		}
		else {
			output.println("Current balance: $"+balance[accountIndex]);
			output.println("Error! Can't deposit sums smaller than 0");
		}
	}

	/* method withdrawal()
	 * 
	 * Input: 
	 * scan-reference to the scanner opened in main
	 * output-reference to the output file
	 * acctNum-reference to the array that holds the account number
	 * balance-reference to the array that holds the balance
	 * numAccts-the number of accounts existent
	 * 
	 * Process:user inputs the desired account number, then
	 * the method calls the findAcct() method and uses the 
	 * return value to test if the account exists. If the 
	 * account exists, the method tests if the amount to be
	 * withdrawn is valid, and subsequently it subtracts this value
	 * from the original balance if there are enough funds
	 * 
	 * Output:the method prints a receipt that includes type of
	 * operation, account number, original balance, amount to be
	 * withdrawn, and the new balance, or if the account
	 * is inexistent, an error message
	 */
	public static void withdrawal(Scanner scan,PrintWriter output,
			int[] acctNum, double[] balance, int numAccts) {
		
		int account_number=scan.nextInt();
		double withdrawn=scan.nextDouble();
		
		int accountIndex=findAcct(acctNum, numAccts, account_number);
		
		//printing the receipt
		output.println("Operation:Withdrawal");
		output.println("Account number: "+account_number);
		output.println("Amount to withdraw: $"+withdrawn);
		
		//testing if the user entered valid input
		if(withdrawn>=0) {
			
			//testing if the account exists
			if(accountIndex==-1)
				output.println("Error! Account doesn't exist.");
			
			//testing if there are sufficient funds
			else if(balance[accountIndex]>=withdrawn) {
				 output.println("Current balance: $"+balance[accountIndex]);
				 balance[accountIndex]=balance[accountIndex]-withdrawn;
				 output.printf("New balance: $%.2f \n",balance[accountIndex]);
			}
			else {
				output.println("Current balance: $"+balance[accountIndex]);
				output.println("Error! Insufficient funds!");
			}
		}
		else {
			output.println("Current balance: $"+balance[accountIndex]);
			output.println("Error! Can't withdraw sums smaller than 0!");
		}
	}
	
	/* method newAcct()
	 * 
	 * Input: 
	 * scan-reference to the scanner opened in main
	 * output-reference to the output file
	 * acctNum-reference to the array that holds the account number
	 * balance-reference to the array that holds the balance
	 * numAccts-the number of accounts existent
	 * 
	 * Process:user inputs the desired account number, then
	 * the method calls the findAcct() method and uses the 
	 * return value to test if the account exists. If the 
	 * account doesn't exist, the method creates a new account
	 * with the balance of 0
	 * 
	 * Output:the method prints a receipt that includes type of
	 * operation, account number, and the original balance, or if
	 * the account already exists, an error message.
	 * The method returns the new number of accounts
	 */
	public static int newAcct(Scanner scan,PrintWriter output,
			int[] acctNum, double[] balance, int numAccts) {
		
		//entering the new account
		System.out.println("Enter account number:");
		int account_number=scan.nextInt();
		int accountIndex=findAcct(acctNum, numAccts, account_number);
		output.println("Operation: New Account");
		
		//testing if the account exists
		if(accountIndex==-1) {
			//creating the new account
			acctNum[numAccts]=account_number;
			balance[numAccts]=0;
			output.println("Account created: "+acctNum[numAccts]);
			output.println("Balance: $"+balance[numAccts]);
			numAccts++;
		}
		else {
			output.println("Account to be created: "+account_number);
			output.println("Error! Account already exists");
		}
			
		return numAccts;
	}
	
	/* method deleteAcct()
	 * 
	 * Input: 
	 * scan-reference to the scanner opened in main
	 * output-reference to the output file
	 * acctNum-reference to the array that holds the account number
	 * balance-reference to the array that holds the balance
	 * numAccts-the number of accounts existent
	 * 
	 * Process:user inputs the desired account number, then
	 * the method calls the findAcct() method and uses the 
	 * return value to test if the account exists. If the 
	 * account exists, and it has a balance of 0, the program
	 * deletes it, otherwise it prints error messages
	 * 
	 * Output:the method prints a receipt that includes type of
	 * operation, account to be deleted, and status of deletion.
	 * The method returns the new number of accounts
	 */
	//Extra credit #2
	public static int deleteAcct(Scanner scan,PrintWriter output,
			int[] acctNum, double[] balance, int numAccts) {
		
		//entering the account to be deleted
		System.out.println("Enter account to be deleted: ");
		int account_number=scan.nextInt();
		int accountIndex=findAcct(acctNum, numAccts, account_number);
		
		output.println("Operation: Delete account");
		output.println("Account to be deleted: "+account_number);
		
		//testing if the account exists
		if(accountIndex==-1)
			output.println("Error! Account doesn't exist");
		
		//testing if there is money in the account
		else if(balance[accountIndex]>0)
			output.println("Error! Balance greater than 0.");
		else {
			//deleting the account
			for(int i=accountIndex;i<numAccts;i++) {
				acctNum[i]=acctNum[i+1];
				balance[i]=balance[i+1];
			}
			output.println("Account deleted successfully!");
			
			//decreasing the total number of accounts
			numAccts--;
		}
		return numAccts;
	}
}
