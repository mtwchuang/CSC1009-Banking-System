package View.BankAccount;

import ModelView.MV_Global;
import ModelView.BankAccount.MV_BankAccount;

public class V_Withdraw {
    public void run(String... params) throws Exception{
        MV_BankAccount bankAccMV = new MV_BankAccount();
        double withdrawAmt = Double.parseDouble(params[0]);
        short statusCode;

        while(true){
            System.out.println(
                "User: " + MV_Global.sessionUserAcc.getUserLastName() + 
                " " + MV_Global.sessionUserAcc.getUserFirstName());
            System.out.println(
                "Bank Account: " + MV_Global.sessionBankAcc.getBankAccID());
            System.out.println("");
        
            System.out.println("Procesing transaction...");
        
            if(bankAccMV.isBankAccOverseas() && !confirmSurcharge()) return;

            statusCode = bankAccMV.VWithdraw_withdraw(withdrawAmt);
        
        }
    }

    //Overseas withdrawal surcharge confirmation
	private boolean confirmSurcharge(){
		double surchargeAmt = MV_Global.getOverseasTransactionCharge();
		surchargeAmt = Math.round(surchargeAmt * 100);

		while(true){
			System.out.print(
				"\n**NOTICE**\n" +
				"You are attempting to perform a foriegn transaction which will incur a surcharge" +
				"of " + surchargeAmt + "% of your transaction amount.\n" +
				"Proceed? [Y/N]: ");
				
            
		}

		return false;
	}
}