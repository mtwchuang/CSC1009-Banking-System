package ModelView.BankAccount;

import java.util.ArrayList;
import java.util.List;

import DataAccess.BankAccount.DA_BankAccount;
import Model.BankAccount.M_IBankAccount;
import ModelView.MV_Global;

public class MV_BankAccount {
    public String[] VUserAccIndex_getUserBankAccs() throws Exception{
        //Authentication; A user must be logged on to access this function
		if(MV_Global.sessionUserAcc == null) throw new Exception("No user logged in");

		//Variable declaration
        DA_BankAccount bankAccDA = new DA_BankAccount();
        List<String> returnValue = new ArrayList<String>();
        M_IBankAccount currentAcc;
        String[] userBankAccs;

        //Acquire all bank accounts owned by user
        userBankAccs = MV_Global.sessionUserAcc.getUserBankAccounts();
        
        //Get all bank accounts' ID and description
        for(String userBankAcc: userBankAccs){
            currentAcc = bankAccDA.dBankAccounts_GetByID(userBankAcc);
            returnValue.add(currentAcc.getBankAccID() + "\t" + currentAcc.getBankAccDescription());
        }
        return returnValue.toArray(new String[returnValue.size()]);
    }

    public void loadBankAccIntoSession(String bankAccID) throws Exception{
        //Authentication; A user must be logged on to access this function
		if(MV_Global.sessionUserAcc == null) throw new Exception("No user logged in");

		//Variable declaration
        M_IBankAccount targetAcc;
        
        //Acquire bank account from DataAccess layer
        targetAcc = new DA_BankAccount().dBankAccounts_GetByID(bankAccID);

        //Code safety validation; ensure DataAccess layer returned something
		if(targetAcc == null) throw new Exception("No data returned from DataAccess layer");

        //Load bank account into session
        MV_Global.sessionBankAcc = targetAcc;
    }
}