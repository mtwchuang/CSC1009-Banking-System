package ModelView.BankAccount;

import java.util.ArrayList;
import java.util.List;

import DataAccess.BankAccount.DA_BankAccount;
import Model.BankAccount.M_IBankAccount;
import ModelView.MV_Global;

public class MV_BankAccount {
    public String[] VUserAccIndex_getUserBankAccs() throws Exception{
        DA_BankAccount bankAccDA = new DA_BankAccount();
        String[] userBankAccs = MV_Global.sessionUser.getUserBankAccounts();

        //Acquire all bank accounts owned by user
        List<String> returnValue = new ArrayList<String>();
        
        M_IBankAccount currentAcc;
        for(String userBankAcc: userBankAccs){
            currentAcc = bankAccDA.dBankAccounts_GetByID(userBankAcc);
            returnValue.add(currentAcc.getBankAccID() + "\t" + currentAcc.getBankAccDescription());
        }
        
        return returnValue.toArray(new String[returnValue.size()]);
    }
}