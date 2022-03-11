package View.UserAccount;

import ModelView.MV_Global;
import ModelView.BankAccount.MV_BankAccount;
import View.V_ViewManager;

public class V_UserAccIndex 
{
	public void run() throws Exception{
		//Variable declaration
		MV_BankAccount bankAccMV = new MV_BankAccount();
		String inputBankAccSelection;
		int inputBankAccSelectionInt;
		boolean pageLock = true;

		//Get user bank accounts from ModelView layer
		String[] bankAccs = bankAccMV.VUserAccIndex_getUserBankAccs();

		//Lock page until user provides valid input
		while(pageLock){
			System.out.println(
				"Welcome " + MV_Global.sessionUserAcc.getUserLastName() +
				" " + MV_Global.sessionUserAcc.getUserFirstName() + "\n");
			
			//Print every user-accessible bank account
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
					throw new Exception("Hijo de puta");
				else{
        			//Call ModelView layer to Load selected bank account into session
					bankAccMV.loadBankAccIntoSession(bankAccs[inputBankAccSelectionInt].split("\t")[0]);
					
					//Break out of page lock
					pageLock = false;
				}
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


	}
}