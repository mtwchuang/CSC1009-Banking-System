package View.Index;

import ModelView.MV_Global;
import ModelView.BankAccount.MV_BankAccount;

public class V_UserAccIndex 
{
	public void run() throws Exception{
		//Authorization
		if(MV_Global.sessionUser == null) throw new Exception("No user logged in");

		System.out.println(
			"Welcome " + MV_Global.sessionUser.getUserLastName() +
			" " + MV_Global.sessionUser.getUserFirstName() + "\n");
		
		System.out.println("Bank Accounts:");
		String[] bankAccs = new MV_BankAccount().VUserAccIndex_getUserBankAccs();
		for(int i = 0; i < bankAccs.length; i++){
			System.out.println("[" + i + "] " + bankAccs[i]);
		}

		System.out.println("\nSelect a bank account:");

		System.exit(0);
	}
}