package View.Index;

import ModelView.MV_Global;
import ModelView.BankAccount.MV_BankAccount;
import View.V_ViewManager;

public class V_UserAccIndex 
{
	public void run() throws Exception{
		//Authorization
		if(MV_Global.sessionUser == null) throw new Exception("No user logged in");

		//Variable declarations
		String inputBankAccSelection;
		int inputBankAccSelectionInt;
		boolean pageLock = true;

		//Get user bank accounts from ModelView layer
		String[] bankAccs = new MV_BankAccount().VUserAccIndex_getUserBankAccs();

		while(pageLock){
			System.out.println(
				"Welcome " + MV_Global.sessionUser.getUserLastName() +
				" " + MV_Global.sessionUser.getUserFirstName() + "\n");
			
			//Print every bank account
			System.out.println("Bank Accounts:");
			for(int i = 0; i < bankAccs.length; i++){
				System.out.println("[" + i + "] " + bankAccs[i]);
			}
	
			//Get user bank acc selection input
			System.out.print("\nSelect a bank account: ");
			inputBankAccSelection = MV_Global.input.nextLine();

			//User input validation
			try{
				inputBankAccSelectionInt = Integer.parseInt(inputBankAccSelection);
				if(inputBankAccSelectionInt < 0 || inputBankAccSelectionInt >= bankAccs.length)
					throw new Exception("Pendejo");
				else
					pageLock = false;
			}
			//Invalid input
			catch(Exception e){
				System.out.print("\nInvalid selection");
				for(int i = 0; i < 3; i++){
					MV_Global.wait(1);
					System.out.print(".");
				}
				V_ViewManager.clearPage();
			}
		}

		System.exit(0);
	}
}