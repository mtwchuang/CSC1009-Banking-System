package View.AdminActions;

import ModelView.BankAccount.MV_BankAccount;
import ModelView.Global.MV_Global;

public class V_ChangeBalance {
    public void run(){
        //Authorization; Lvl Admin
        if(MV_Global.sessionUserAcc.getUserType() <= 3) return;

        double targetAmt;
        short statusCode;

        while(true){
            System.out.print("Enter amount to add: ");
            try{
			    targetAmt = Double.parseDouble(MV_Global.input.nextLine());
                System.out.println("\nProcessing...");
                
                statusCode = new MV_BankAccount().VChangeBal_changeBal(targetAmt);

                if(statusCode == -1){
                    System.out.println("You are not authorized.");
                    MV_Global.waitError();
                    return;
                }
                else if(statusCode > 0){
                    System.out.println("Process failed...");
                    MV_Global.waitError();
                    return;
                }
                else if(statusCode == 0){
                    System.out.println("Process complete.");
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