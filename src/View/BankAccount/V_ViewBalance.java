package View.BankAccount;

import ModelView.MV_Global;
import ModelView.BankAccount.MV_BankAccount;

public class V_ViewBalance {
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
            System.out.println(
                "User: " + MV_Global.sessionUserAcc.getUserLastName() + 
                " " + MV_Global.sessionUserAcc.getUserFirstName());
            System.out.println(
                "Bank Account: " + MV_Global.sessionBankAcc.getBankAccID());
            System.out.println("");
    
            System.out.println(
                "Account Balance: " + currency + " " + String.format("%.2f", MV_Global.sessionBankAcc.getBankAccBalance()));
    
            System.out.println("\nDetails:");
            System.out.println("Account Status\t\t\t" + accountStatus);
            System.out.println("Account Type\t\t\t" + accountType);
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
                    throw new Exception();
                
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
                        return false;
                    default: //Logout
                        return true;
                }
            }
        }
    }

    private void changeBankAccSettings() throws Exception{
        System.out.println("Change settings");
        MV_Global.waitSuccess();
        System.exit(0);
    }
}