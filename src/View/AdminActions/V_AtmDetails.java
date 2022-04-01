package View.AdminActions;

import ModelView.BankAccount.MV_BankAccount;
import ModelView.Global.MV_Global;
import View.Global.V_ViewManager;

public class V_AtmDetails {
    public void run() throws Exception{
        //Authorization; Lvl Admin
        if(MV_Global.sessionUserAcc.getUserType() <= 3) return;

        MV_BankAccount bankAccMV = new MV_BankAccount();
        String currency = bankAccMV.getCurrencySymbol(MV_Global.getAtmID().split("-")[1]);
        String adminInput; int adminInputInt = 0, targetDenom = 0;
        int[][] carryingNotes = MV_Global.availableNotes;
        double totalCarryAmt = 0;
        boolean toppingUp = false;

        while(true){
            System.out.println("ATM General Info:");
            System.out.println("\tATM ID\t\t" + MV_Global.getAtmID());
            System.out.println("\tATM Locality\t" + bankAccMV.getLocalityName(MV_Global.getAtmID().split("-")[1]));
            System.out.println("");
    
            System.out.println("Carrying Notes:");
            for(int i = 0; i < carryingNotes.length; i++){
                System.out.println("\t[" + i + "]\t" + currency + " " + carryingNotes[i][0] + "   \tx " + carryingNotes[i][1]);
                totalCarryAmt += carryingNotes[i][1] * carryingNotes[i][0];
            }
            System.out.println("\nTotal Carrying Amount: " + currency + " " + String.format("%.2f", totalCarryAmt) + "\n");
    
            System.out.print("Enter nothing to return. Enter [Opt] to top up notes.\nInput: ");
            adminInput = MV_Global.input.nextLine();
            System.out.println("");
    
            if(adminInput.length() == 0){
                System.out.println("Returning...");
                return;
            }
            else{
                try{
                    adminInputInt = Integer.parseInt(adminInput);
                    if(adminInputInt < 0 || adminInputInt >= carryingNotes.length)
                        throw new Exception("Error Detected");
                    targetDenom = adminInputInt;
                    toppingUp = true;
                }
                catch(Exception e){
                    System.out.println("Invalid [Opt].");
                    MV_Global.waitError();
                }
            }
    
            if(toppingUp){
                System.out.print("Topping up " + currency + " " + carryingNotes[adminInputInt][0] + ": ");
                adminInput = MV_Global.input.nextLine();
    
                try{
                    adminInputInt = Integer.parseInt(adminInput);
                    System.out.println("Updating...");
                    MV_Global.availableNotes[targetDenom][1] += adminInputInt;
                    if(MV_Global.availableNotes[targetDenom][1] < 0)
                        MV_Global.availableNotes[targetDenom][1] = 0;
                }
                catch(Exception e){
                    System.out.println("Enter a valid integer.");
                    MV_Global.waitError();
                }
                toppingUp = !toppingUp;
            }
    
            V_ViewManager.clearPage();
        }
    }
}