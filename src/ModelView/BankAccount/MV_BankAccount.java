package ModelView.BankAccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DataAccess.DA_Settings;
import DataAccess.BankAccount.DA_BankAccount;
import DataAccess.Transaction.DA_Transaction;
import Model.BankAccount.M_CorporateBankAcc;
import Model.BankAccount.M_IBankAccount;
import Model.BankAccount.M_ICorporateBankAcc;
import Model.BankAccount.M_IJointBankAcc;
import Model.BankAccount.M_JointBankAcc;
import Model.Transaction.M_BalanceChange;
import Model.Transaction.M_IBalanceChange;
import ModelView.MV_Global;

public class MV_BankAccount{
    //Logic functions for V_UserAccIndex
    //  Dynamic acquisition of user bank accs
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
            returnValue.add(currentAcc.getBankAccID() + "|" + currentAcc.getBankAccDescription());
        }
        return returnValue.toArray(new String[returnValue.size()]);
    }
    
    //Logic functions for V_BankAccIndex
    //  Dynamic acquisition of bank acc actions
    public String[] VBankAccIndex_getBankAccActions() throws Exception{
		//Variable declaration
        DA_Settings settings = new DA_Settings();
        List<String> actions = new ArrayList<String>();
        boolean owner = false;

        //Get withdraw options
        switch(MV_Global.getAtmID().split("-")[1]){
            case "02": //Japan
                actions.addAll(Arrays.asList(settings.dbSettings_GetByKey("BankAccWithdrawOptJP")));
                break;
            case "03": //US
                actions.addAll(Arrays.asList(settings.dbSettings_GetByKey("BankAccWithdrawOptUS")));
                break;
            default: //SG
                actions.addAll(Arrays.asList(settings.dbSettings_GetByKey("BankAccWithdrawOptSG")));
                break;
        }

        //Get user action options
        actions.addAll(Arrays.asList(settings.dbSettings_GetByKey("BankAccActions")));

        //Get bank account owner action options
        switch(MV_Global.sessionBankAcc.getBankAccType()){
            case 1: //Joint account
                M_IJointBankAcc jacc = new M_JointBankAcc(); //Yet to implement data access layer function
                if(jacc.getBankAccHolderID().equals(MV_Global.sessionUserAcc.getUserID()) ||
                jacc.getBankAccSubHolderID().equals(MV_Global.sessionUserAcc.getUserID()))
                    owner = true;
                break;
            case 2: //Corporate account
                M_ICorporateBankAcc cacc = new M_CorporateBankAcc(); //Yet to implement data access layer function
                if(cacc.getBankAccHolderID().equals(MV_Global.sessionUserAcc.getUserID())) owner = true;
                else{
                    for(String subHolder: cacc.getBankAccSubHolderIDs()){
                        if(subHolder.equals(MV_Global.sessionUserAcc.getUserID())){
                            owner = true;
                            break;
                        }
                    }
                }
                break;
            default: //Normal account
            if(MV_Global.sessionBankAcc.getBankAccHolderID().equals(MV_Global.sessionUserAcc.getUserID()))
                owner = true;
            break;
        }
        if(owner) actions.addAll(Arrays.asList(settings.dbSettings_GetByKey("BankAccOwnerActions")));

        //Admin actions
        if(MV_Global.sessionUserAcc.getUserType() > 3) 
            if(owner) actions.addAll(Arrays.asList(settings.dbSettings_GetByKey("BankAccAdminActions")));

        String[] temp = new String[actions.size()];
        for(int i = 0; i < actions.size(); i++)
            temp[i] = actions.get(i);
    
        return temp;
    }
    
    //Withdraw action
    public int[] VWithdraw_withdraw(double withdrawAmt) throws Exception{
        //Return data:
        //  [0]: Status code
		//      0: Ok
		//      1: Bank acc insufficient funds
		//      2: Bank acc minimum funds trigger
        //      3: ATM invalid denomination input
        //      4: ATM insufficient denomination
        //      5: Transaction error
        //  [1 ... n]: Notes denomination count

        //Variable initialization
        DA_Transaction transactionDA = new DA_Transaction();
        DA_BankAccount bankAccountDA = new DA_BankAccount();
        M_IBalanceChange withdrawalTransactionFinal;
        M_BalanceChange withdrawalTransaction;
        String transactionDesc, currency;
        int[] withdrawnNotes, returnVal;
        double bankAccFutureBal, bankAccBal, bankAccMinBal;
        short daStatusCode;

        //Check number of notes to withdraw
        withdrawnNotes = withdrawDenominationCal(withdrawAmt);
        
        //Prepare return value
        returnVal = new int[withdrawnNotes.length + 1];
        for(int i = 1; i < returnVal.length; i++)
            returnVal[i] = withdrawnNotes[i - 1];

        //If withdraw amount cannot be met with available denominations
        if(withdrawnNotes[0] == -1) return new int[]{3};
        //If withdraw amount cannot be met with availble ATM notes
        else if(withdrawnNotes[0] == -2) return new int[]{4};

        //Acquire locality currency symbol
        switch(MV_Global.getAtmID().split("-")[1]){
            case "02":
                currency = "JPY";
                break;
            case "03":
                currency = "USD";
                break;
            default:
                currency = "SGD";
        }

        //Set trasaction desciption
        transactionDesc = 
            "Withdrawal of " + currency + " " + String.format("%.2f", withdrawAmt) +
            " at ATM " + MV_Global.getAtmID();

        //Acquire bank acc balance and bank acc min balance
        bankAccBal = MV_Global.sessionBankAcc.getBankAccBalance();
        bankAccMinBal = MV_Global.sessionBankAcc.getBankAccMinBalance();

        //Withdrawal transaction record
        withdrawalTransaction = new M_BalanceChange(true);
        withdrawalTransaction.setTransactionType((short) 0);
        withdrawalTransaction.setTransactionDescription(transactionDesc);
        withdrawalTransaction.setTransactionOverseas(isBankAccOverseas());
        withdrawalTransaction.setTransactionSrcBankAccID(MV_Global.sessionBankAcc.getBankAccID());
        withdrawalTransaction.setTransactionBankAccInitialAmount(bankAccBal);
        withdrawalTransaction.executeOnAtm();
        withdrawalTransaction.setExecutedOnPurchase(false);

        //Overseas withdrawal
        if(isBankAccOverseas()){
            //Country codes, take me home~
            String atmLocality = MV_Global.getAtmID().split("-")[1];
            String bankLocality = getBankAccCountryCode();

            //Convert withdrawAmt to base currency, SGD
            double baseCurrencyWithdrawAmt = convertToBaseCurrency(withdrawAmt, atmLocality);
            baseCurrencyWithdrawAmt = (Math.round(baseCurrencyWithdrawAmt * 100))/100;

            //Convert withdrawAmt from base currency, SGD, to bank acc currency
            double bankCurrencyWithdrawAmt = convertFromBaseCurrency(baseCurrencyWithdrawAmt, bankLocality);
            bankCurrencyWithdrawAmt = (Math.round(bankCurrencyWithdrawAmt * 100))/100;

            //Forecast bank balance after withdrawal
            //  Split into multi steps due to chances of data loss
            double surchargeAmt = (bankCurrencyWithdrawAmt * MV_Global.getOverseasTransactionCharge()) * 100;
            surchargeAmt = Math.round(surchargeAmt);
            surchargeAmt /= 100;
            double balanceAftWithdraw = (bankAccBal - bankCurrencyWithdrawAmt) * 100;
            balanceAftWithdraw = Math.round(balanceAftWithdraw);
            balanceAftWithdraw /= 100;
            bankAccFutureBal = (balanceAftWithdraw - surchargeAmt) * 100;
            bankAccFutureBal = Math.round(bankAccFutureBal);
            bankAccFutureBal /= 100;

            //If bank acc does not have sufficient balance
            if(bankAccFutureBal < 0) return new int[]{1};
            //If bank acc minimum balance is triggered by withdraw amount
            else if(bankAccFutureBal < bankAccMinBal) return new int[]{2};

            //Withdrawal transaction record
            withdrawalTransaction.setTransactionAmount(bankCurrencyWithdrawAmt);
            withdrawalTransaction.setTransactionBankAccFinalAmount(balanceAftWithdraw);
            withdrawalTransactionFinal = (M_IBalanceChange) withdrawalTransaction;

            //Surcharge transaction record
            M_BalanceChange surchargeTransaction = new M_BalanceChange(true);
            surchargeTransaction.setTransactionType((short) 2);
            surchargeTransaction.setTransactionAmount(surchargeAmt);
            surchargeTransaction.setTransactionDescription(transactionDesc + "[Surcharge]");
            surchargeTransaction.setTransactionOverseas(true);
            surchargeTransaction.setTransactionSrcBankAccID(MV_Global.sessionBankAcc.getBankAccID());
            surchargeTransaction.setTransactionBankAccInitialAmount(balanceAftWithdraw);
            surchargeTransaction.setTransactionBankAccFinalAmount(bankAccFutureBal);
            surchargeTransaction.executeOnAtm();
            surchargeTransaction.setExecutedOnPurchase(false);
            M_IBalanceChange surchargeTransactionFinal = (M_IBalanceChange) surchargeTransaction;
            
            //Bank account updated record
            M_IBankAccount currentBankAcc = MV_Global.sessionBankAcc;
            currentBankAcc.setBankAccBalance(bankAccFutureBal);

            //Put new transactions to Data Access layer
            try{
                //Create withdrawal transaction
                daStatusCode = transactionDA.dbBalanceChange_Create(withdrawalTransactionFinal);
                if(daStatusCode != 0) return new int[]{5};

                //Create surcharge transaction
                daStatusCode = transactionDA.dbBalanceChange_Create(surchargeTransactionFinal);
                if(daStatusCode != 0) return new int[]{5};

                //Update bank acc record
                daStatusCode = bankAccountDA.dBankAccounts_Update(currentBankAcc);
                if(daStatusCode != 0) return new int[]{5};
            }
            catch(Exception e){
                return new int[]{5};
            }
        }
        //Local withdrawal
        else{
            //Forecast bank balance after withdrawal
            bankAccFutureBal = bankAccBal - withdrawAmt;

            //If bank acc does not have sufficient balance
            if(bankAccFutureBal < 0) return new int[]{1};
            //If bank acc minimum balance is triggered by withdraw amount
            else if(bankAccFutureBal < bankAccMinBal) return new int[]{2};

            //Withdrawal transaction record
            withdrawalTransaction.setTransactionAmount(withdrawAmt);
            withdrawalTransaction.setTransactionBankAccFinalAmount((Math.round((bankAccFutureBal) * 100))/100);
            withdrawalTransactionFinal = (M_IBalanceChange) withdrawalTransaction;

            //Bank account updated record
            M_IBankAccount currentBankAcc = MV_Global.sessionBankAcc;
            currentBankAcc.setBankAccBalance(bankAccFutureBal);

            //Put new transactions to Data Access layer
            try{
                //Create withdrawal transaction
                daStatusCode = transactionDA.dbBalanceChange_Create(withdrawalTransactionFinal);
                if(daStatusCode != 0) return new int[]{5};

                //Update bank acc record
                daStatusCode = bankAccountDA.dBankAccounts_Update(currentBankAcc);
                if(daStatusCode != 0) return new int[]{5};
            }
            catch(Exception e){
                return new int[]{5};
            }
        }

        //Update session bank data
        MV_Global.sessionBankAcc.setBankAccBalance(bankAccFutureBal);

        //Update ATM note count
        for(int i = 0; i < returnVal.length - 1; i++)
            MV_Global.availableNotes[i][1] -= returnVal[i + 1];

        returnVal[0] = 0;
        return returnVal;
    }

    //[ADMIN] Change balance
    public short VChangeBal_changeBal(double inputAmt) throws Exception{
        //Authorization
        if(MV_Global.sessionUserAcc.getUserType() <= 3) return -1;

        DA_BankAccount bankAccountDA = new DA_BankAccount();

        M_IBankAccount currentBankAcc = MV_Global.sessionBankAcc;
        double newBal = currentBankAcc.getBankAccBalance() + inputAmt;
        currentBankAcc.setBankAccBalance(newBal);

        //Backdoor increament of bank account balance
        try{
            //Update bank acc record
            short daStatusCode = bankAccountDA.dBankAccounts_Update(currentBankAcc);
            if(daStatusCode != 0) return daStatusCode;
        }
        catch(Exception e){
            return 1;
        }

        //Update session bank data
        MV_Global.sessionBankAcc.setBankAccBalance(newBal);

        return 0;
    }

    //Load bankAccID into session
    public void loadBankAccIntoSession(String bankAccID) throws Exception{
        //Authentication; A user must be logged on to access this function
		if(MV_Global.sessionUserAcc == null) throw new Exception("No session user found.");

		//Variable declaration
        M_IBankAccount targetAcc;
        
        //Acquire bank account from DataAccess layer
        targetAcc = new DA_BankAccount().dBankAccounts_GetByID(bankAccID);

        //Code safety validation; ensure DataAccess layer returned something
		if(targetAcc == null) throw new Exception("No data returned from DataAccess layer.");

        //Load bank account into session
        MV_Global.sessionBankAcc = targetAcc;
    }

    //Acquire bank acc country code
    public String getBankAccCountryCode(){
        return getBankAccCountryCode("%SESSION%");
    }
    public String getBankAccCountryCode(String bankAccID){
        if(bankAccID.equals("%SESSION%")) 
            return MV_Global.sessionBankAcc.getBankAccID().split("-")[1].substring(0,2);
        return bankAccID.split("-")[1].substring(0,2);
    }

    //Check if bank acc and ATM are in different countries
    public boolean isBankAccOverseas(){
        return isBankAccOverseas("%SESSION%");
    }
    public boolean isBankAccOverseas(String bankAccID){
        if(bankAccID.equals("%SESSION%"))
            return !getBankAccCountryCode().equals(MV_Global.getAtmID().split("-")[1]);
        return !getBankAccCountryCode(bankAccID).equals(MV_Global.getAtmID().split("-")[1]);
    }

    //Convert to base currency; X to SGD
    public double convertToBaseCurrency(double targetAmt, String countryCode) throws Exception{
        String[] conversionRates = new DA_Settings().dbSettings_GetByKey("CurrencyRate");

        for(String conversionRate: conversionRates){
            if(conversionRate.split("-")[0].equals(countryCode)){
                return targetAmt / (Double.parseDouble(conversionRate.split("-")[1]));
            }
        }
        return 0;
    }
    //Convert from base currency; SGD to X
    public double convertFromBaseCurrency(double targetAmt, String countryCode) throws Exception{
        String[] conversionRates = new DA_Settings().dbSettings_GetByKey("CurrencyRate");

        for(String conversionRate: conversionRates){
            if(conversionRate.split("-")[0].equals(countryCode)){
                return targetAmt * (Double.parseDouble(conversionRate.split("-")[1]));
            }
        }
        return 0;
    }

    private int[] withdrawDenominationCal(double amount){
        //noteCount1 remainingAmt1
        //  Checks if ATM has enough notes to meet withdraw amount
        //noteCount2 remainingAmt2
        //  Checks if withdraw amount can be met with available denominations

        int[][] availableDenominations = MV_Global.availableNotes;
        int[] notesWithdrawn = new int[availableDenominations.length];
        int noteCount1 = 0, noteCount2 = 0;
        double remainingAmt1 = amount, remainingAmt2 = amount, currentDenomination;
        Arrays.fill(notesWithdrawn, 0);

        for(int i = availableDenominations.length - 1; i > -1; i--){
            currentDenomination = availableDenominations[i][0];

            noteCount1 = (int)(remainingAmt1 / currentDenomination);
            noteCount2 = (int)(remainingAmt2 / currentDenomination);

            if(noteCount1 > 0 && noteCount1 <= availableDenominations[i][1]){
                remainingAmt1 -= noteCount1 * currentDenomination;
                notesWithdrawn[i] = noteCount1;
            }
            else if(noteCount1 > 0 && noteCount1 > availableDenominations[i][1]){
                remainingAmt1 -= availableDenominations[i][1] * currentDenomination;
                notesWithdrawn[i] = availableDenominations[i][1];
            }

            if(noteCount2 > 0) remainingAmt2 -= noteCount2 * currentDenomination;
        }
    
        //If withdraw amount cannot be met with available denominations
        if(remainingAmt2 != 0) Arrays.fill(notesWithdrawn, -1);
        //If withdraw amount cannot be met with availble ATM notes
        else if(remainingAmt1 != 0) Arrays.fill(notesWithdrawn, -2);
        
        return notesWithdrawn;
    }
}