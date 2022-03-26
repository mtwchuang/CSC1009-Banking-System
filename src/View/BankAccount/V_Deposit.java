package View.BankAccount;

import ModelView.MV_Global;
import ModelView.BankAccount.MV_BankAccount;

public class V_Deposit {
    public void run() throws Exception{
		MV_BankAccount bankAccMV = new MV_BankAccount();
        String userInput = "", currency = bankAccMV.getCurrencySymbol(MV_Global.getAtmID().split("-")[1]);
        double inputAmount = 0, depositableAmount = 0, undepositableAmount = 0;
        int[][] availableDenominations = MV_Global.availableNotes;
        int[] depositedNotes;
        int i;
        short status;
        boolean withdraw = false;

        while(true){
            System.out.println(
				"User: " + MV_Global.sessionUserAcc.getUserLastName() + 
				" " + MV_Global.sessionUserAcc.getUserFirstName());
			System.out.println(
				"Bank Account: " + MV_Global.sessionBankAcc.getBankAccID());
			System.out.println("");

            //Deposit value input
            System.out.print("Deposit amount: ");
            userInput = MV_Global.input.nextLine();

			System.out.println("\nProcessing...\n");
            try{
                inputAmount = Double.parseDouble(userInput);
                if(inputAmount <= 0) throw new Exception();

                inputAmount = Math.round((inputAmount * 100));
                inputAmount /= 100;

                withdraw = !withdraw;
            }
            catch(Exception e){
                System.out.println("\nInvalid value...");
                MV_Global.waitError();
            }

            if(withdraw){
                //Get closest note denominations
                depositedNotes = bankAccMV.denominationCal(inputAmount, true);

                //Get closest depositable value
                for(i = 0; i < depositedNotes.length; i++){
                    System.out.println(currency + " " + availableDenominations[i][0] + "   \t" + "x " + depositedNotes[i]);
                    depositableAmount += (depositedNotes[i] * availableDenominations[i][0]);
                }
                System.out.println("Depositable amount: " + currency + " " + String.format("%.2f", depositableAmount));

                //Calculate undepositable amount
                undepositableAmount = inputAmount - depositableAmount;
                if(undepositableAmount > 0){
                    System.out.println("\nThe following amount does not have a valid note denomination and will not be deposited.");
                    System.out.println("Rejected amount: " + currency + " " + String.format("%.2f", undepositableAmount));
                }
;
                status = bankAccMV.VDeposit_deposit(depositableAmount);
                if(status == 0){
                    System.out.println("\nDeposit successful.");
                    System.out.println("Returning...");
                    MV_Global.waitSuccess();
                }
                else{
                    System.out.println("\nDeposit failed.");
                    System.out.println("Returning...");
                    MV_Global.waitError();
                }
                return;
            }
        }
    }
}