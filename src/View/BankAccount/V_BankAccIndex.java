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
		String inputAction;
		int specialCase;
		String[] bankAccActionOpt = bankAccMV.VBankAccIndex_getBankAccOpt();

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
			for(int i = 1; i < bankAccActionOpt.length; i++){
				String[] subData = bankAccActionOpt[i].split(("-"));
				System.out.println("[" + subData[0] + "]\t" + subData[1]);
			}

			//Print other actions
			System.out.println("\n[" + bankAccActionOpt.length + "]\tCancel and logout");
			System.out.println("[" + (bankAccActionOpt.length + 1) + "]\tSwitch bank account");

			//Get user bank acc selection input
			System.out.print("\nSelect action [Opt]: ");
			inputAction = MV_Global.input.nextLine();

			//Action switch case
			System.out.print("\nLoading... "); 
			switch(inputAction){
				//Withdraw SG
				case "a055":
					
					break;
				//Withdraw JP
				case "a056":

					break;
				//Withdraw US
				case "a057":

					break;
				//4 - Withdraw Other Amounts
				case "4":

					break;
				//5 - Deposit Balance
				case "5":

					break;
				//6 - View Transactions
				case "6":

					break;
				//7 - Transfer Balance
				case "7":

					break;
				//8 - View Balance
				case "8":

					break;
				//9 - Change Settings
				case "9":

				break;
				//Invalid action input
				default:
					try{
						specialCase = Integer.parseInt(inputAction);
						MV_Global.waitSuccess();

						//Cancel				
						if(specialCase == bankAccActionOpt.length) return true;
						//Switch bank account
						else if(specialCase == (bankAccActionOpt.length + 1)) return false;
					}
					catch(Exception e){}

					System.out.println("Invalid selection.");
					MV_Global.waitError();
					break;
			}
			
			V_ViewManager.clearPage();
		}
	}
}