package View.BankAccount;

import java.util.Arrays;

import ModelView.BankAccount.MV_BankAccount;
import ModelView.Global.MV_Global;
import View.Global.V_ViewManager;

public class V_Transfer {
    public void run() throws Exception{
        MV_BankAccount bankAccMV = new MV_BankAccount();
        String[] fieldValues = {"[Empty]", "[Empty]"}, fieldNames = {"Dest Bank Acc Num", "Transaction Amount"};
        boolean[] fieldValidity = {false, false};
        String userInput = "", inputDestAccNum = "", currency, atmLocality, confirmation;
        double inputTxnAmt = 0;
        int i, userInputInt;
        short status;

        //Acquire bank account currency
        atmLocality = MV_Global.getAtmID().split("-")[1];
        currency = Arrays.stream(MV_Global.getCurrencySymbols())
            .filter(x -> x.split("-")[0].equals(atmLocality))
            .findFirst()
            .get()
            .split("-")[1];

        //Lock page till process is done or cancelled
        while(true){
            
            //Display
            System.out.println(
				"User: " + MV_Global.sessionUserAcc.getUserLastName() + 
				" " + MV_Global.sessionUserAcc.getUserFirstName());
			System.out.println(
				"Bank Account: " + MV_Global.sessionBankAcc.getBankAccID());
			System.out.println("");

            System.out.println("Opt\tField\t\t\tValues");
            System.out.println("---------------------------------------------------------------------");
            for(i = 0; i < fieldNames.length; i++)
                System.out.println("[" + i + "]\t" + fieldNames[i] + "\t" + fieldValues[i]);
            System.out.println("\n" + "[" + fieldNames.length + "]\tDiscard and Return");
            System.out.println("[" + (fieldNames.length + 1) + "]\tExecute Transaction");

            //User action input
			System.out.println("");
            System.out.print("Input [Opt]: ");
            userInput = MV_Global.input.nextLine();
			System.out.println("");

            //User input validation and processes
            try{
                userInputInt = Integer.parseInt(userInput);
                switch(userInputInt){
                    case 0: //Edit Destination Bank Account Number
                        //Acquire user input of destination bank account number
                        System.out.print("Dest Bank Acc Num: ");
                        userInput = MV_Global.input.nextLine();
			            System.out.println("");

                        //Post user input to Model View layer for validation
                        inputDestAccNum = bankAccMV.VTransfer_checkDestBankAcc(userInput);
                        if(inputDestAccNum.equals("**INVALID**")){
                            System.out.println("Invalid bank account.");
                            fieldValidity[0] = false;
                            fieldValues[0] = "[Empty]";
                            MV_Global.waitError();
                        }
                        //Set field value to user input if valid
                        else{
                            fieldValidity[0] = true;
                            fieldValues[0] = inputDestAccNum;
                        }
                        break;
                    case 1: //Edit Transaction Amount
                        //Acquire user input of destination bank account number
                        System.out.print("Transaction Amount: ");
                        userInput = MV_Global.input.nextLine();
			            System.out.println("");
                        inputTxnAmt = Double.parseDouble(userInput);

                        inputTxnAmt = Math.round(inputTxnAmt * 100);
                        inputTxnAmt /= 100;

                        //First hand validation
                        if(inputTxnAmt <= 0) throw new NumberFormatException();

                        //Post user input to Model View layer for validation
                        status = bankAccMV.VTransfer_checkBankCapable(inputTxnAmt);
                        
                        switch(status){
                            case 1: //Not enough balance
                                System.out.println("Insufficient balance.");
                                fieldValidity[1] = false;
                                fieldValues[0] = "[Empty]";
                                MV_Global.waitError();
                                break;
                            case 2: //Transaction limit triggered
                                System.out.println("Transaction limit triggered.");
                                fieldValidity[1] = false;
                                MV_Global.waitError();
                                break;
                            case 3: //Minimum balance triggered
                                System.out.println("Minimum balance triggered.");
                                fieldValidity[1] = false;
                                MV_Global.waitError();
                                break;
                            default: //Valid
                                fieldValidity[1] = true;
                                fieldValues[1] = currency + " " + inputTxnAmt;
                                break;
                        }
                        break;
                    case 2: //Discard and Return
			            System.out.println("Returning...");
                        MV_Global.waitSuccess();
                        return;
                    case 3: //Execute Transaction
                        if(fieldValidity[0] && fieldValidity[1]){
                            status = bankAccMV.VTransfer_transfer(inputTxnAmt, inputDestAccNum, false);

                            //If surcharge acknowledgement is required
                            if(status == 1){
                                //Calculate surchage
                                double surchargePercentage = MV_Global.getOverseasTransactionCharge();
                                double surchargeAmt = inputTxnAmt * MV_Global.getOverseasTransactionCharge();
                                surchargeAmt = Math.round(surchargeAmt * 100);
                                surchargeAmt /= 100;

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
                                    if(confirmation.equals("y")){
                                        status = bankAccMV.VTransfer_transfer(inputTxnAmt, inputDestAccNum, true);
                                        break;
                                    }
                                    //Disagrees with surcharge
                                    else if(confirmation.equals("n")) break;
                                }
                            }

                            //Process status codes
                            switch(status){
                                case 2: //Insufficient funds for surcharge
                                    System.out.println("Bank account do not have enough balance for surcharge...");
                                    MV_Global.waitError();
                                    break;
                                case 3: //Transaction error
                                    System.out.println("Transaction error...");
                                    MV_Global.waitError();
                                    break;
                                default: //Success
                                    System.out.println("Transfer successful.");
                                    System.out.println("Returning...");
                                    MV_Global.waitSuccess();
                                    return;
                            }
                        }
                        else{
			                System.out.println("Invalid input in field(s)...");
                            MV_Global.waitError();
                        }
                        break;
                    default: throw new NumberFormatException();
                }
            }
            catch(NumberFormatException nfe){
                System.out.println("Invalid input...");
                MV_Global.waitError();
            }
            V_ViewManager.clearPage();
        }
    }
}