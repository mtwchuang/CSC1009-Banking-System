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
		boolean logout;
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
			System.out.println("\n[" + bankAccActions.length + "]\tSwitch bank account");
			System.out.println("[" + (bankAccActions.length + 1) + "]\tLogout");

			//Get user bank acc selection input
			System.out.print("\nSelect action [Opt]: ");
			inputAction = MV_Global.input.nextLine();

			System.out.print("\nLoading... ");
			try{
				//Convert input action's action code
				inputActionInt = Integer.parseInt(inputAction);
				inputAction = bankAccActions[inputActionInt].split("-")[0];
				
				//Execute action
				logout = executeBankAccActions(inputAction);
				MV_Global.waitSuccess();

				//If user chooses to logout from inner pages
				if(logout) return true;
			}
			//Invalid input
			catch(NumberFormatException nfe){
				System.out.println("Invalid input.");
				MV_Global.waitError();
			}
			catch(IndexOutOfBoundsException ioobe){
				if(inputActionInt == bankAccActions.length) return false;
				else if(inputActionInt == bankAccActions.length + 1) return true;
				else{
					System.out.println("Invalid input.");
					MV_Global.waitError();
				}
			}
			V_ViewManager.clearPage();
		}
	}

	//Process actions
	private boolean executeBankAccActions(String actionCode) throws Exception{
		switch(actionCode){
			case "a0551": //Withdraw 10
				return V_ViewManager.redirect("withdraw", "10");
			case "a0552": //Withdraw 20
				return V_ViewManager.redirect("withdraw", "20");
			case "a0553": //Withdraw 50
				return V_ViewManager.redirect("withdraw", "50");
			case "a0554": //Withdraw 100
				return V_ViewManager.redirect("withdraw", "100");
			case "a0555": //Withdraw 1000
				return V_ViewManager.redirect("withdraw", "1000");
			case "a0556": //Withdraw 2000
				return V_ViewManager.redirect("withdraw", "2000");
			case "a0557": //Withdraw 5000
				return V_ViewManager.redirect("withdraw", "5000");
			case "a0558": //Withdraw 10000
				return V_ViewManager.redirect("withdraw", "10000");

			case "b131": //Withdraw Other Amounts
				return V_ViewManager.redirect("withdraw", "-1");
			case "b132": //Deposit Balance
				break;
			case "b133": //View Transactions
				break;
			case "b134": //Transfer Balance
				break;

			//Account holder settings
			case "c961": //View Balance
				return V_ViewManager.redirect("viewbankaccdetails");
			case "c962": //Change Settings
				break;

			//Admin Actions
			case "z420": //ADMIN Alter Balance
				V_ViewManager.redirect("changebal");
				break;
			case "z422": //ADMIN Transfer to Michael McDoesn'tExist [Local]
				break;
			case "z423": //ADMIN Transfer to Michael McDoesn'tExist [Overseas]
				break;
			case "z312": //ADMIN Terminate Program
				System.exit(0);

			//Invalid action input
			default:
				break; 
		}
		return false;
	}
}