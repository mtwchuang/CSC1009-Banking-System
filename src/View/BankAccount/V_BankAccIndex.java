package View.BankAccount;

import ModelView.MV_Global;
import ModelView.BankAccount.MV_BankAccount;
import View.V_ViewManager;

public class V_BankAccIndex 
{
	//Action selections
	public boolean run() throws Exception{
		//Variable declaration
		MV_BankAccount bankAccMV = new MV_BankAccount();
		short actionStatus;
		String inputAction;
		String[] bankAccActions = bankAccMV.VBankAccIndex_getBankAccActions();

		//Lock page prevent invalid input escapes; remain on page till nagivated out
		while(true){
			System.out.println(
				"User: " + MV_Global.sessionUserAcc.getUserLastName() + 
				" " + MV_Global.sessionUserAcc.getUserFirstName());
			System.out.println(
				"Bank Account: " + MV_Global.sessionBankAcc.getBankAccID());
			System.out.println("");
			
			//Print available actions
			System.out.println("Opt\tAction");
			System.out.println("---------------------------------------------------------------------");
			for(int i = 1; i < bankAccActions.length; i++){
				String[] subData = bankAccActions[i].split(("-"));
				System.out.println("[" + i + "]\t" + subData[1]);
			}

			//Print other actions
			System.out.println("\n[" + bankAccActions.length + "]\tCancel and logout");
			System.out.println("[" + (bankAccActions.length + 1) + "]\tSwitch bank account");

			//Get user bank acc selection input
			System.out.print("\nSelect action [Opt]: ");
			inputAction = MV_Global.input.nextLine();

			//Execute action
			System.out.print("\nLoading... "); 
			actionStatus = bankAccMV.VBankAccIndex_executeBankAccActions(inputAction);
			
			V_ViewManager.clearPage();
		}
	}
}