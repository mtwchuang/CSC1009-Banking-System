package View.BankAccount;

import ModelView.MV_Global;
import ModelView.BankAccount.MV_BankAccount;
import View.V_ViewManager;

public class V_Withdraw {
    public void run(String... params) throws Exception{
        MV_BankAccount bankAccMV = new MV_BankAccount();
        String currency = "";
        double withdrawAmt = Double.parseDouble(params[0]);
        int[] returnData;

        //Custom withdrawal amount
        if(withdrawAmt < 0){
            String userInput;
            while(true){
                System.out.println(
                    "User: " + MV_Global.sessionUserAcc.getUserLastName() + 
                    " " + MV_Global.sessionUserAcc.getUserFirstName());
                System.out.println(
                    "Bank Account: " + MV_Global.sessionBankAcc.getBankAccID());
                System.out.println("");

                System.out.print("Enter withdrawal amount: ");
                userInput = MV_Global.input.nextLine();
                try{
                    withdrawAmt = Double.parseDouble(userInput);
                    break;
                }
                catch(Exception e){
                    System.out.println("Invalid amount...");
                    MV_Global.waitError();
                }
                V_ViewManager.clearPage();
            }
        }
        V_ViewManager.clearPage();

        System.out.println(
            "User: " + MV_Global.sessionUserAcc.getUserLastName() + 
            " " + MV_Global.sessionUserAcc.getUserFirstName());
        System.out.println(
            "Bank Account: " + MV_Global.sessionBankAcc.getBankAccID());
        System.out.println("");

        //Acquire locality currency symbol
        for(String datum: MV_Global.getCurrencySymbols()){
            if(MV_Global.getAtmID().split("-")[1].equals(datum.split("-")[0])){
                currency = datum.split("-")[1];
                break;
            }
        }

        System.out.println("Withdrawing " + currency + " " + withdrawAmt);

        //If withdrawal is overseas and user disagrees surcharge
        if(bankAccMV.isBankAccOverseas()){
            boolean confirmSurcharge = confirmSurcharge(withdrawAmt);
            if(!confirmSurcharge){
                System.out.println("Cancelling withdrawal...");
                MV_Global.waitError();
                return;
            }
        }

        //Proceed to withdrawal process
        System.out.println("Processing withdrawal...");
        returnData = bankAccMV.VWithdraw_withdraw(withdrawAmt);

        //  0: Ok
        //  1: Bank acc insufficient funds
        //  2: Bank acc minimum funds trigger
        //  3: Bank acc transaction limit trigger
        //  4: ATM invalid denomination input
        //  5: ATM insufficient denomination
        //  6: Transaction error
        switch(returnData[0]){
            case 0:
                System.out.println("Withdrawal successful\n\n" + "Dispensing " + currency + " " + String.format("%.2f", withdrawAmt) + ":");
                for(int i = 0; i < MV_Global.availableNotes.length; i++)
                    System.out.println("- x" + returnData[i + 1] + "\t" + currency + " " + MV_Global.availableNotes[i][0]);
                MV_Global.wait(5000);
                return;
            case 1:
                System.out.println("Bank account does not have sufficient balance...");
                MV_Global.waitError();
                return;
            case 2:
                System.out.println("Bank account minimum balance reached...");
                MV_Global.waitError();
                return;
            case 3:
                System.out.println("Bank account transaction limit exceeded...");
                MV_Global.waitError();
                return;
            case 4:
                System.out.println("Invalid denomiation value...");
                MV_Global.waitError();
                return;
            case 5:
                System.out.println("ATM does not have sufficient amount...");
                MV_Global.waitError();
                return;
            default:
                System.out.println("A transaction error has occured...");
                MV_Global.waitError();
                return;
        }
    }

    //Overseas transaction surcharge confirmation
	private boolean confirmSurcharge(double transactionAmt){
        MV_BankAccount bankAccMV = new MV_BankAccount();
		double surchargePercentage = MV_Global.getOverseasTransactionCharge();
        double surchargeAmt = (Math.round((transactionAmt * MV_Global.getOverseasTransactionCharge()) * 100))/100;
        String confirmation = "", currency = "";

        //Acquire locality currency symbol
        currency = bankAccMV.getCurrencySymbol(MV_Global.getAtmID().split("-")[1]);

        //Loop till valid input acquired
		while(true){
			System.out.print(
				"\n**NOTICE**\n" +
				"You are attempting to perform a foreign transaction which will incur a surcharge of " + String.format("%.2f", (surchargePercentage * 100)) + "%" +
                "of your transaction amount, which equates to " + currency + " " + String.format("%.2f", surchargeAmt) + ".\n" +
				"Proceed? [Y/N]: ");

            //Acquire acknowledgement input
			confirmation = MV_Global.input.nextLine().trim().toLowerCase();

            //Agrees with surcharge
            if(confirmation.equals("y")) return true;
            //Disagrees with surcharge
            else if(confirmation.equals("n")) return false;
		}
	}
}