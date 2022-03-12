package ModelView;

import java.io.File;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import Model.BankAccount.M_IBankAccount;
import Model.UserAccount.M_IUserAccount;

public class MV_Global {
	//Program-global scanner
	public static Scanner input = new Scanner(System.in);

	//Stack to keep track of user page navigation
    public static Stack<String> pageDir = new Stack<String>();

	//Session storage
	public static M_IUserAccount sessionUserAcc;
	public static M_IBankAccount sessionBankAcc;

	//Database path config
	public static String dbUserAccounts = getDynamicDbPath() + "\\data\\UserAccount\\UserAccounts.txt";
	public static String dbUserAccountLogins = getDynamicDbPath() + "\\data\\UserAccount\\UserAccountLogins";
	public static String dbBankAccounts = getDynamicDbPath() + "\\data\\BankAccount\\BankAccounts.txt";
	public static String dbBalanceChanges = getDynamicDbPath() + "\\data\\Transaction\\BalanceChanges.txt";
	public static String dbBalanceTranfers = getDynamicDbPath() + "\\data\\Transaction\\BalanceTransfers.txt";
	public static String dbSettings = getDynamicDbPath() + "\\data\\Settings.txt";
	private static String getDynamicDbPath(){
		return new File("").getAbsolutePath();
	}
	
	//ATM config
	//[Country]-[ATM_ID]
	public static final String atmID = "01-6969";

	//Program-global delay functions; for user experience and readability
	public static void wait(int milliseconds) throws Exception{
		TimeUnit.MILLISECONDS.sleep((long) milliseconds);
	}
	public static void waitSuccess() throws Exception{wait(1000);}
	public static void waitError() throws Exception{wait(1000);}

	//Clear session data
	public static void clearSession(){
		sessionUserAcc = null;
		sessionBankAcc = null;
	}
}