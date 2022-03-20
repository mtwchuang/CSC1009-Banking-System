package View.AdminActions;

import ModelView.MV_Global;

public class V_ChangeBal {
    public void run(boolean add){
        double targetAmt;

        while(true){
            System.out.print("Enter amount: ");
            try{
			    targetAmt = Double.parseDouble(MV_Global.input.nextLine());

                
            }
            catch(Exception e){
                System.out.print("Qué pendejo");
            }
        }
    }
}