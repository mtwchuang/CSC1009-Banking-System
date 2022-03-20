package View.AdminActions;

import ModelView.MV_Global;
import ModelView.BankAccount.MV_BankAccount;

public class V_ChangeBal {
    public void run(){
        double targetAmt;
        short statusCode;

        while(true){
            System.out.print("Enter amount: ");
            try{
			    targetAmt = Double.parseDouble(MV_Global.input.nextLine());
                System.out.println("Processing...");
                
                statusCode = new MV_BankAccount().VChangeBal_changeBal(targetAmt);

                if(statusCode == -1){
                    System.out.println("You are not authorized. (⌐■_■)");
                    MV_Global.waitError();
                    return;
                }
                else if(statusCode > 0){
                    System.out.println("Process failed... (¬_¬ )");
                    MV_Global.waitError();
                    return;
                }
                else if(statusCode == 0){
                    System.out.println("Process complete. (●'◡'●)");
                    System.out.println("Current balance: " + MV_Global.sessionBankAcc.getBankAccBalance());
                    MV_Global.waitSuccess();
                    return;
                }
            }
            catch(Exception e){
                System.out.println("Qué pendejo");
            }
        }
    }
}