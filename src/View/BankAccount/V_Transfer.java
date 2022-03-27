package View.BankAccount;

import java.util.Arrays;

import ModelView.MV_Global;
import ModelView.BankAccount.MV_BankAccount;
import View.V_ViewManager;

public class V_Transfer {
    public void run() throws Exception{
        MV_BankAccount bankAccMV = new MV_BankAccount();
        String[] fieldValues = new String[2], fieldNames = {"Dest Bank Acc Num", "Transaction Amount"};
        boolean[] fieldValidity = {false, false};
        String userInput = "", inputDestAccNum, currency, atmLocality;
        double inputTxnAmt;
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

            System.out.println("Opt\tField\t\tValues");
            System.out.println("---------------------------------------------------------------------");
            for(i = 0; i < fieldNames.length; i++)
                System.out.println("[" + i + "]\t" + fieldNames[i] + "\t" + fieldValues[i]);
            System.out.println("\n" + "[" + fieldNames.length + "]\tDiscard and Return");
            System.out.println("[" + (fieldNames.length + 1) + "]\tExecute Transaction");

            //User action input
			System.out.println("");
            System.out.println("Input [Opt]: ");
            userInput = MV_Global.input.nextLine();
			System.out.println("");

            //User input validation and processes
            try{
                userInputInt = Integer.parseInt(userInput);
                switch(userInputInt){
                    case 0: //Edit Destination Bank Account Number
                        //Acquire user input of destination bank account number
                        System.out.println("Dest Bank Acc Num: ");
                        userInput = MV_Global.input.nextLine();
			            System.out.println("");

                        //Post user input to Model View layer for validation
                        inputDestAccNum = bankAccMV.VTransfer_checkDestBankAcc(userInput);
                        if(inputDestAccNum.equals("**INVALID**")){
                            System.out.println("Invalid bank account.");
                            fieldValidity[0] = false;
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
                        System.out.println("Transaction Amount: ");
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
                            
                        }
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