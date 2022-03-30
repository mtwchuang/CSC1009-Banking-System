package View.UserAccount;

import ModelView.BankAccount.MV_BankAccount;
import ModelView.Global.MV_Global;
import ModelView.UserAccount.MV_UserAccount;
import View.Global.V_ViewManager;

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
			System.out.println("Opt\tBank Account\t\t\tDescription");
			System.out.println("---------------------------------------------------------------------");
			for(int i = 0; i < bankAccs.length; i++){
				String[] subData = bankAccs[i].split(("\\|"));
				System.out.println("[" + i + "]\t" + subData[0] + "\t\t" + subData[1]);
			}

			//Print other actions
			System.out.println("\n[" + bankAccs.length + "]\tChange password");
			System.out.println("[" + (bankAccs.length + 1) + "]\tLogout");

			//Get user bank acc selection input
			System.out.print("\nSelect a bank account [Opt]: ");
			inputBankAccSelection = MV_Global.input.nextLine();

			System.out.print("\nLoading... "); 
			//User input validation
			try{
				inputBankAccSelectionInt = Integer.parseInt(inputBankAccSelection);

				//Out of selection values' range
				if(inputBankAccSelectionInt < 0 || inputBankAccSelectionInt > (bankAccs.length + 1))
					throw new Exception("Hijo de puta");
				//
				else if(inputBankAccSelectionInt == bankAccs.length){
					this.changePassword();
					V_ViewManager.clearPage();
				}
				//User cancel action
				else if(inputBankAccSelectionInt == (bankAccs.length + 1)){
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
				System.out.println("Invalid input.");
				MV_Global.waitError();
			}

			V_ViewManager.clearPage();
		}
	}

	public void changePassword(){
		short authenticationStatus;
		MV_UserAccount userAccMV = new MV_UserAccount();
		String inputPassword, inputPassword2;

		while(true){
			V_ViewManager.clearPage();

			System.out.print("Current password: ");
			inputPassword = MV_Global.input.nextLine();

			try{
				authenticationStatus = userAccMV.VLogin_checkAcc(MV_Global.sessionUserAcc.getUserName(), inputPassword);
				//Check authenticationStatus
				if(authenticationStatus == 0){
					System.out.print("Enter new password [Max 100]: ");
					inputPassword = MV_Global.input.nextLine();

					System.out.print("Confirm new password [Max 100]: ");
					inputPassword2 = MV_Global.input.nextLine();

					if(inputPassword2.equals(inputPassword)){
						short mvStatus = userAccMV.VUserAccIndex_changePassword(inputPassword);
						switch(mvStatus){
							case 0: //OK Status
								System.out.println("Password change successful.");
								MV_Global.waitSuccess();
								return;
							case 1: //Password too long
								System.out.println("New password exceeded 100 characters");
								MV_Global.waitError();
								break;
							default:
								System.out.println("Change password failed.");
								MV_Global.waitError();
								break;
						}
					}
					else{
						System.out.println("New password does not match password confirmation.");
						MV_Global.waitError();
					}
				}
				else{
					System.out.println("\nIncorrect password: ");
					MV_Global.waitError();
				}
			}
			catch(Exception e){
				
			}
		}
	}
}