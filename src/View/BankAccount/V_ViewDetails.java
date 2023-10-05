package View.BankAccount;

import ModelView.BankAccount.MV_BankAccount;
import ModelView.Global.MV_Global;
import View.Global.V_ViewManager;

public class V_ViewDetails {
    public boolean run() throws Exception{
		MV_BankAccount bankAccMV = new MV_BankAccount();
        String bankAccLocality = bankAccMV.getBankAccCountryCode(MV_Global.sessionBankAcc.getBankAccID());
        String currency = bankAccMV.getCurrencySymbol(bankAccLocality);
        String accountStatus = "", accountType = "", userInput;
        int userInputInt = 0;
        boolean executeAction = false;

        //Hardcoded list of actions due to fixed limited choices
        String[] actionList = new String[]{"Change Settings", "Return", "Logout"};

        //Account status translation
        switch(MV_Global.sessionBankAcc.getBankAccStatus()){
            case 0: 
                accountStatus = "Normal";
                break;
            case 1:
                accountStatus = "Locked";
                break;
        }
        //Account type translation
        switch(MV_Global.sessionBankAcc.getBankAccStatus()){
            case 0: 
                accountType = "Standard Account";
                break;
            case 1:
                accountType = "Joint Account";
                break;
            case 2:
                accountType = "Corporate Account";
                break;
        }

        while(true){
            //Page name
		    System.out.println(">> Bank Account >> Account Details\n");
            
            //Page display
            System.out.println(
                "User: " + MV_Global.sessionUserAcc.getUserLastName() + 
                " " + MV_Global.sessionUserAcc.getUserFirstName());
            System.out.println(
                "Bank Account: " + MV_Global.sessionBankAcc.getBankAccID());
            System.out.println("");
    
            System.out.println(
                "Account Balance: " + currency + " " + String.format("%.2f", MV_Global.sessionBankAcc.getBankAccBalance()));
    
            System.out.println("\nDetails:");
            System.out.println("Account Type\t\t\t" + accountType);
            System.out.println("Account Status\t\t\t" + accountStatus);
            System.out.println("Account Description\t\t" + MV_Global.sessionBankAcc.getBankAccDescription());
    
            System.out.println("Transaction Limit\t\t" + currency + " " +  String.format("%.2f", MV_Global.sessionBankAcc.getBankAccTransactionLimit()));
            System.out.println("Minimum Balance\t\t\t" + currency + " " +  String.format("%.2f", MV_Global.sessionBankAcc.getBankAccMinBalance()));
    
            System.out.println("\nOpt\tAction");
            System.out.println("---------------------------------------------------------------------");
            for(int i = 0; i < actionList.length; i++)
                System.out.println("[" + i + "]\t" + actionList[i]);
            
            System.out.print("\nSelect action [Opt]: ");
            try{
                userInput = MV_Global.input.nextLine();
                userInputInt = Integer.parseInt(userInput);

                if(userInputInt < 0 || userInputInt >= actionList.length)
                    throw new Exception("Error Detected");
                
                executeAction = true;
            }
            catch(Exception e){
                System.out.println("Invalid input...");
                MV_Global.waitError();
            }

            if(executeAction){
                switch(userInputInt){
                    case 0: //Change Settings
                        changeBankAccSettings();
                        break;
                    case 1: //Return
                        System.out.println("Returing...");
                        MV_Global.waitSuccess();
                        return false;
                    default: //Logout
                        return true;
                }
            }

            V_ViewManager.clearPage();
        }
    }

    private void changeBankAccSettings() throws Exception{
        MV_BankAccount bankAccMV = new MV_BankAccount();
        String bankAccLocality = bankAccMV.getBankAccCountryCode(MV_Global.sessionBankAcc.getBankAccID());
        String currency = bankAccMV.getCurrencySymbol(bankAccLocality);
        String accountStatus = "", accountType = "", userInput;
        double userInputDouble = 0;
        int userInputInt = 0;
        short status = 0;

        //Attributes subjectable to change
        String newDesc = MV_Global.sessionBankAcc.getBankAccDescription();
        double newTxnLimit = MV_Global.sessionBankAcc.getBankAccTransactionLimit();
        double newMinBal = MV_Global.sessionBankAcc.getBankAccMinBalance();

        String oldDesc = newDesc;
        double oldTxnLimit = newTxnLimit, oldMinBal = newMinBal;

        System.out.println(
            "User: " + MV_Global.sessionUserAcc.getUserLastName() + 
            " " + MV_Global.sessionUserAcc.getUserFirstName());
        System.out.println(
            "Bank Account: " + MV_Global.sessionBankAcc.getBankAccID());
        System.out.println("");

        //Account status translation
        switch(MV_Global.sessionBankAcc.getBankAccStatus()){
            case 0: 
                accountStatus = "Normal";
                break;
            case 1:
                accountStatus = "Locked";
                break;
        }
        //Account type translation
        switch(MV_Global.sessionBankAcc.getBankAccStatus()){
            case 0: 
                accountType = "Standard Account";
                break;
            case 1:
                accountType = "Joint Account";
                break;
            case 2:
                accountType = "Corporate Account";
                break;
        }

        while(true){
            V_ViewManager.clearPage();
            
            //Page name
		    System.out.println(">> Bank Account >> Account Details >> Edit Details\n");

            //Page Display
            System.out.println("\nOpt\tField\t\t\t\tField Data");
            System.out.println("---------------------------------------------------------------------");
            System.out.println("[-]\tAccount Type\t\t\t" + accountType);
            System.out.println("[-]\tAccount Status\t\t\t" + accountStatus);
            System.out.println("[0]\tAccount Description" + (oldDesc.equals(newDesc) ? "": "*") + "\t\t" + newDesc);
            System.out.println("[1]\tTransaction Limit" + ((oldTxnLimit == newTxnLimit) ? "": "*") + "\t\t" + currency + " " +  String.format("%.2f", newTxnLimit));
            System.out.println("[2]\tMinimum Balance" + ((oldMinBal == newMinBal) ? "": "*") + "\t\t\t" + currency + " " +  String.format("%.2f", newMinBal));
            System.out.println("\n[3] Save Changes and Return");
            System.out.println("[4] Discard Changes and Return");

            System.out.print("\nSelect action [Opt]: ");
            try{
                userInput = MV_Global.input.nextLine();
                userInputInt = Integer.parseInt(userInput);

                if(userInputInt < 0 || userInputInt >= 5)
                    throw new Exception("Error Detected");
                
                //Change fields
                switch(userInputInt){
                    case 0: //Change Account Description
                        System.out.print("Account Description [MaxChar 100]: ");
                        userInput = MV_Global.input.nextLine();
                        //Exceed character limit
                        if(userInput.trim().length() > 100){
                            System.out.println("Exceeded character count of 100...");
                            MV_Global.waitError();
                        }
                        else if(userInput.contains("|")){
                            System.out.println("Illegal character '|' detected...");
                            MV_Global.waitError();
                        }
                        //Valid input
                        else{
                            System.out.println("\nApplying changes...");
                            MV_Global.waitSuccess();
                            newDesc = userInput;
                        }
                        break;
                    case 1: //Change Transaction Limit
                        System.out.print("Transaction Limit [0 - Unlimited]: ");
                        userInput = MV_Global.input.nextLine();
                        userInputDouble = Double.parseDouble(userInput);

                        userInputDouble = Math.round(userInputDouble * 100);
                        userInputDouble /= 100;

                        //Negative value
                        if(userInputDouble < 0){
                            System.out.println("\nValue cannot be lesser than 0...");
                            MV_Global.waitError();
                        }
                        //Valid input
                        else{
                            System.out.println("\nApplying changes...");
                            newTxnLimit = userInputDouble;
                            MV_Global.waitSuccess();
                        }
                        break;
                    case 2: //Change Minimum Balance
                        System.out.print("Minimum Balance: ");
                        userInput = MV_Global.input.nextLine();
                        userInputDouble = Double.parseDouble(userInput);

                        userInputDouble = Math.round(userInputDouble * 100);
                        userInputDouble /= 100;

                        //Negative value
                        if(userInputDouble < 0){
                            System.out.println("Value cannot be lesser than 0...");
                            MV_Global.waitError();
                        }
                        //Valid input
                        else{
                            System.out.println("\nApplying changes...");
                            newMinBal = oldMinBal;
                            MV_Global.waitSuccess();
                        }
                        break;
                    case 3: //Save Changes and Return
                        System.out.println("Saving changes...");
                        status = bankAccMV.VViewDetails_updateBankAcc(newDesc, newTxnLimit, newMinBal);
                        if(status == 0){
                            System.out.println("Saving successful.\n\nReturning...");
                            MV_Global.waitSuccess();
                            return;
                        }
                        else{
                            System.out.println("Saving failed.\n\nReturning...");
                            MV_Global.waitError();
                        }
                        break;
                    default: //Discard Changes and Return
                        System.out.println("Discarding changes...\nReturning...");
                        MV_Global.waitError();
                        return;
                }
            }
            catch(Exception e){
                System.out.println("Invalid input...");
                MV_Global.waitError();
            }
        }
    }
}