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

		//Get user bank accounts from ModelView layer
		String[] bankAccs = bankAccMV.VUserAccIndex_getUserBankAccs();

		//Lock page prevent invalid input escapes; remain on page till nagivated out
		while(true){
			System.out.println(
				"Welcome " + MV_Global.sessionUserAcc.getUserLastName() +
				" " + MV_Global.sessionUserAcc.getUserFirstName() + "\n");
			
			//Print available actions
			System.out.println("Opt\tBank Account\t\tDescription");
			System.out.println("---------------------------------------------------------------------");
			for(int i = 0; i < bankAccs.length; i++){
				String[] subData = bankAccs[i].split(("\\|"));
				System.out.println("[" + i + "]\t" + subData[0] + "\t\t" + subData[1]);
			}

			//Print other actions
			System.out.println("\n[" + bankAccs.length + "]\tCancel and logout");

			//Get user bank acc selection input
			System.out.print("\nSelect a bank account [Opt]: ");
			inputBankAccSelection = MV_Global.input.nextLine();

			System.out.print("\nLoading... "); 
			//User input validation
			try{
				inputBankAccSelectionInt = Integer.parseInt(inputBankAccSelection);
				//Out of selection values' range
				if(inputBankAccSelectionInt < 0 || inputBankAccSelectionInt > bankAccs.length)
					throw new Exception("Hijo de puta");
				//User cancel action
				else if(inputBankAccSelectionInt == bankAccs.length){
					System.out.println("Logging out.");
					MV_Global.waitSuccess();
					return;
				}
				else{
					//Call ModelView layer to Load selected bank account into session
					bankAccMV.loadBankAccIntoSession(bankAccs[inputBankAccSelectionInt].split("\\|")[0]);
					MV_Global.waitSuccess();

					//Navigate to bankaccindex
					boolean logout = V_ViewManager.redirect("bankaccindex");

					//If user chooses to logout in bankaccindex or further
					//Return to highest level call to free memory
					if(logout) return;
				}
			}
			//Invalid input
			catch(Exception e){
				System.out.println("Invalid selection.");
				MV_Global.waitError();
			}

			V_ViewManager.clearPage();
		}
	}
}