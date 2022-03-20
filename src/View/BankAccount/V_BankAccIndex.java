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
		int inputActionInt = 0;
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
			for(int i = 0; i < bankAccActions.length; i++){
				String[] subData = bankAccActions[i].split(("-"));
				System.out.println("[" + i + "]\t" + subData[1]);
			}

			//Print other actions
			System.out.println("[" + bankAccActions.length + "]\tSwitch bank account");
			System.out.println("\n[" + (bankAccActions.length + 1) + "]\tCancel and logout");

			//Get user bank acc selection input
			System.out.print("\nSelect action [Opt]: ");
			inputAction = MV_Global.input.nextLine();

			System.out.print("\nLoading... ");
			try{
				//Convert input action's action code
				inputActionInt = Integer.parseInt(inputAction);
				inputAction = bankAccActions[inputActionInt].split("-")[0];
				
				//Execute action
				actionStatus = executeBankAccActions(inputAction);
				MV_Global.waitSuccess();
			}
			//Invalid input
			catch(NumberFormatException nfe){
				System.out.println("Invalid input.");
				MV_Global.waitError();
			}
			catch(IndexOutOfBoundsException ioobe){
				if(inputActionInt == bankAccActions.length) return false;
				else if(inputActionInt == bankAccActions.length + 1) return true;
				else System.out.println("Invalid input.");
				MV_Global.waitError();
			}
			V_ViewManager.clearPage();
		}
	}

	//Process actions
	private short executeBankAccActions(String actionCode) throws Exception{
		//	Status codes:
		//	-1: User-triggered cancellation
		short statusCode = 0;
		switch(actionCode){
			case "a0551": //Withdraw SG - S$10
				V_ViewManager.redirect("withdraw", "10");
				break;
			case "a0552": //Withdraw SG - S$20
				V_ViewManager.redirect("withdraw", "20");
				break;
			case "a0553": //Withdraw SG - S$50
				V_ViewManager.redirect("withdraw", "50");
				break;
			case "a0554": //Withdraw SG - S$100
				V_ViewManager.redirect("withdraw", "100");
				break;

			case "a0561": //Withdraw JP - 1000 yen
				break;

			case "b131": //Withdraw Other Amounts
				break;
			case "b132": //Deposit Balance
				break;
			case "b133": //View Transactions
				break;
			case "b134": //Transfer Balance
				break;

			default: //Invalid action input
				break; 
		}
		return statusCode;
	}
}