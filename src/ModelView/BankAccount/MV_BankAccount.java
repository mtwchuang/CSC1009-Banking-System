package ModelView.BankAccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DataAccess.DA_Settings;
import DataAccess.BankAccount.DA_BankAccount;
import Model.BankAccount.M_CorporateBankAcc;
import Model.BankAccount.M_IBankAccount;
import Model.BankAccount.M_ICorporateBankAcc;
import Model.BankAccount.M_IJointBankAcc;
import Model.BankAccount.M_JointBankAcc;
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
        switch(MV_Global.atmID.split("-")[1]){
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
    
    //  Withdraw action
    public short VBankAccIndex_withdraw(double withdrawAmt) throws Exception{
        //Status codes:
		//  0: Ok
		//  1: Bank acc insufficient funds
		//  2: Bank acc minimum funds trigger
        //  3: ATM invalid denomination input
        //  4: ATM insufficient denomination
        //  5: Transaction error

        if(isBankAccOverseas()){
            //Country codes, take me home
            String atmLocality = MV_Global.atmID.split("-")[1];
            String bankLocality = getBankAccCountryCode();

            //Acquire bank acc balance and bank acc min balance
            double bankAccBal = MV_Global.sessionBankAcc.getBankAccBalance();
            double bankAccMinBal = MV_Global.sessionBankAcc.getBankAccMinBalance();

            int[] withdrawnNotes = withdrawDenominationCal(withdrawAmt);
            if()

            //Convert withdrawAmt to base currency, SGD
            double baseCurrencyWithdrawAmt = convertToBaseCurrency(withdrawAmt, atmLocality);
            //Convert withdrawAmt from base currency, SGD, to bank acc currency
            double bankCurrencyWithdrawAmt = convertFromBaseCurrency(baseCurrencyWithdrawAmt, bankLocality);

        }

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
            return MV_Global.sessionBankAcc.getBankAccID().split("-")[1].substring(0,3);
        return bankAccID.split("-")[1].substring(0,3);
    }

    //Check if bank acc and ATM are in different countries
    public boolean isBankAccOverseas(){
        return isBankAccOverseas("%SESSION%");
    }
    public boolean isBankAccOverseas(String bankAccID){
        if(bankAccID.equals("%SESSION%"))
            return getBankAccCountryCode().equals(MV_Global.atmID.split("-")[1]);
        return getBankAccCountryCode(bankAccID).equals(MV_Global.atmID.split("-")[1]);
    }

    //Convert to base currency; X to SGD
    public double convertToBaseCurrency(double targetAmt, String countryCode) throws Exception{
        String[] conversionRates = new DA_Settings().dbSettings_GetByKey("CurrencyRate");

        String currencyCountry;
        double currenyRate;
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

        String currencyCountry;
        double currenyRate;
        for(String conversionRate: conversionRates){
            if(conversionRate.split("-")[0].equals(countryCode)){
                return targetAmt * (Double.parseDouble(conversionRate.split("-")[1]));
            }
        }
        return 0;
    }

    private int[] withdrawDenominationCal(double amount){
        String[] availableDenominations = MV_Global.availableNotes[0];

        
        return null;
    }
}