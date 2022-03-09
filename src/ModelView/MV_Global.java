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
	public static String dbSelectionOpt = getDynamicDbPath() + "\\data\\SelectionOpt.txt";
	private static String getDynamicDbPath(){
		return new File("").getAbsolutePath();
	}
	
	//Program-global delay function
	public static void wait(int seconds) throws Exception{
		TimeUnit.SECONDS.sleep((long) seconds);
	}

	//Clear session data
	public static void clearSession(){
		sessionUserAcc = null;
		sessionBankAcc = null;
	}
}