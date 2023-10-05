package ModelView.BankAccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DataAccess.BankAccount.DA_BankAccount;
import DataAccess.Global.DA_Settings;
import DataAccess.Transaction.DA_Transaction;
import Model.BankAccount.M_IBankAccount;
import Model.BankAccount.M_ICorporateBankAcc;
import Model.BankAccount.M_IJointBankAcc;
import Model.Global.M_DataAccessException;
import Model.Global.M_ModelViewException;
import Model.Transaction.M_BalanceChange;
import Model.Transaction.M_BalanceTransfer;
import Model.Transaction.M_IBalanceChange;
import Model.Transaction.M_IBalanceTransfer;
import ModelView.Global.MV_Global;

public class MV_BankAccount{
    //View Layer Access Functions
    //  V_UserAccIndexDynamic: Acquisition of user bank accs
    public String[] VUserAccIndex_getUserBankAccs() throws M_ModelViewException{
        //Authentication; A user must be logged on to access this function
        //  Throw fatal error is failed authentication
		if(MV_Global.sessionUserAcc == null) throw new M_ModelViewException("No user logged in.", true);

		//Variable declaration
        DA_BankAccount bankAccDA = new DA_BankAccount();
        List<String> returnValue = new ArrayList<String>();
        M_IBankAccount currentAcc;
        String[] userBankAccs;

        //Acquire all bank accounts owned by user
        userBankAccs = MV_Global.sessionUserAcc.getUserBankAccounts();
        
        //Get all bank accounts' ID and description
        try{
            for(String userBankAcc: userBankAccs){
                currentAcc = bankAccDA.dBankAccounts_GetByID(userBankAcc);
                returnValue.add(currentAcc.getBankAccID() + "|" + currentAcc.getBankAccDescription());
            }
        }
        catch(M_DataAccessException DAe){
            //Suppress Data Access layer exception
            throw new M_ModelViewException("Data Access layer unable to fetch data.");
        }
        return returnValue.toArray(new String[returnValue.size()]);
    }
    //  V_BankAccIndex: Dynamic acquisition of bank acc actions
    public String[] VBankAccIndex_getBankAccActions() throws M_ModelViewException{
		//Variable declaration
        DA_Settings settingsDA = new DA_Settings();
        DA_BankAccount bankAccDA = new DA_BankAccount();
        M_IJointBankAcc jacc = null;
        M_ICorporateBankAcc cacc = null;

        List<String> actions = new ArrayList<String>();
        boolean owner = false, transactOnly = false;

        try{
            //Acquire special class bank account data
            switch(MV_Global.sessionBankAcc.getBankAccType()){
                case 1:
                    jacc = bankAccDA.dBankAccounts_GetByIDJoint(MV_Global.sessionBankAcc.getBankAccID());
                case 2: //Corporate account
                    cacc = bankAccDA.dBankAccounts_GetByIDCorporate(MV_Global.sessionBankAcc.getBankAccID());
                default: //Normal account & Joint account
                break;
            }

            //Transaction only check
            switch(MV_Global.sessionBankAcc.getBankAccType()){
                case 2: //Corporate account
                    String[] transactionOnyIDs = cacc.getBankAccTransactOnlyIDs();
                    for(String ID: transactionOnyIDs){
                        if(ID.equals(MV_Global.sessionUserAcc.getUserID())){
                            transactOnly = true;
                            break;
                        }
                    }
                    break;
                default: //Normal account & Joint account
                break;
            }

            //Get withdrawal options based off country code
            if(!transactOnly){
                String withdrawOptCountry = "BankAccWithdrawOpt" + MV_Global.getAtmID().split("-")[1];
                actions.addAll(Arrays.asList(settingsDA.dbSettings_GetByKey(withdrawOptCountry)));
            }
            
            //Get user action options
            actions.addAll(Arrays.asList(settingsDA.dbSettings_GetByKey("BankAccActions")));

            //Get actions only available for local access
            //  - Deposit
            //  - Transfer
            if(!isBankAccOverseas()) actions.addAll(Arrays.asList(settingsDA.dbSettings_GetByKey("BankAccLocalOnlyActions")));

            //Get bank account owner action options
            switch(MV_Global.sessionBankAcc.getBankAccType()){
                case 1: //Joint account
                    if(jacc.getBankAccHolderID().equals(MV_Global.sessionUserAcc.getUserID()) ||
                    jacc.getBankAccSubHolderID().equals(MV_Global.sessionUserAcc.getUserID()))
                        owner = true;
                    break;
                case 2: //Corporate account
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
            if(owner) actions.addAll(Arrays.asList(settingsDA.dbSettings_GetByKey("BankAccOwnerActions")));

            //Admin actions
            if(MV_Global.sessionUserAcc.getUserType() > 3)
                if(owner) actions.addAll(Arrays.asList(settingsDA.dbSettings_GetByKey("BankAccAdminActions")));
        }
        catch(M_DataAccessException DAe){
            //Invalid configuration of Settings.txt; fatal exception
            throw new M_ModelViewException("Invalid settings file.", true);
        }

        String[] temp = new String[actions.size()];
        for(int i = 0; i < actions.size(); i++)
            temp[i] = actions.get(i);
        return temp;
    }
    //  V_Withdraw: Logic for withdraw action
    public int[] VWithdraw_withdraw(double withdrawAmt) throws M_ModelViewException{
        //Return data:
        //  [0]: Status code
		//      0: Ok
		//      1: Bank acc insufficient funds
		//      2: Bank acc minimum funds trigger
        //      3: Bank acc transaction limit trigger
        //      4: ATM invalid denomination input
        //      5: ATM insufficient denomination
        //      6: Transaction error
        //  [1 ... n]: Notes denomination count

        //Variable initialization
        DA_Transaction transactionDA = new DA_Transaction();
        DA_BankAccount bankAccountDA = new DA_BankAccount();
        M_IBalanceChange withdrawalTransactionFinal;
        M_BalanceChange withdrawalTransaction;
        String transactionDesc, currency;
        int[] withdrawnNotes, returnVal;
        double bankAccFutureBal, bankAccBal, bankAccMinBal, bankAccTxnLimit;
        short daStatusCode;

        //Check number of notes to withdraw
        withdrawnNotes = denominationCal(withdrawAmt, false);
        
        //Prepare return value
        returnVal = new int[withdrawnNotes.length + 1];
        for(int i = 1; i < returnVal.length; i++)
            returnVal[i] = withdrawnNotes[i - 1];

        //If withdraw amount cannot be met with available denominations
        if(withdrawnNotes[0] == -1) return new int[]{4};
        //If withdraw amount cannot be met with availble ATM notes
        else if(withdrawnNotes[0] == -2) return new int[]{5};

        //Acquire locality currency symbol
        currency = getCurrencySymbol(MV_Global.getAtmID().split("-")[1]);

        //Set trasaction desciption
        transactionDesc = 
            "Withdrawal of " + currency + " " + String.format("%.2f", withdrawAmt) +
            " at ATM " + MV_Global.getAtmID();

        //Acquire bank acc balance and bank acc min balance
        bankAccBal = MV_Global.sessionBankAcc.getBankAccBalance();
        bankAccMinBal = MV_Global.sessionBankAcc.getBankAccMinBalance();
        bankAccTxnLimit = MV_Global.sessionBankAcc.getBankAccTransactionLimit();

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
            baseCurrencyWithdrawAmt = Math.round(baseCurrencyWithdrawAmt * 100);
            baseCurrencyWithdrawAmt /= 100;

            //Convert withdrawAmt from base currency, SGD, to bank acc currency
            double bankCurrencyWithdrawAmt = convertFromBaseCurrency(baseCurrencyWithdrawAmt, bankLocality);
            bankCurrencyWithdrawAmt = Math.round(bankCurrencyWithdrawAmt * 100);
            bankCurrencyWithdrawAmt /= 100;

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
            //If bank acc transaction limit is triggered by withdraw amount
            else if(bankAccTxnLimit > 0 && bankCurrencyWithdrawAmt > bankAccTxnLimit) return new int[]{3};

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
                if(daStatusCode != 0) return new int[]{6};

                //Create surcharge transaction
                daStatusCode = transactionDA.dbBalanceChange_Create(surchargeTransactionFinal);
                if(daStatusCode != 0) return new int[]{6};

                //Update bank acc record
                daStatusCode = bankAccountDA.dBankAccounts_Update(currentBankAcc);
                if(daStatusCode != 0) return new int[]{6};
            }
            catch(Exception e){
                return new int[]{6};
            }
        }
        //Local withdrawal
        else{
            //Forecast bank balance after withdrawal
            bankAccFutureBal = bankAccBal - withdrawAmt;
            bankAccFutureBal = Math.round((bankAccFutureBal) * 100);
            bankAccFutureBal /= 100;

            //If bank acc does not have sufficient balance
            if(bankAccFutureBal < 0) return new int[]{1};
            //If bank acc minimum balance is triggered by withdraw amount
            else if(bankAccFutureBal < bankAccMinBal) return new int[]{2};
            //If bank acc transaction limit is triggered by withdraw amount
            else if(bankAccTxnLimit > 0 && withdrawAmt > bankAccTxnLimit) return new int[]{3};

            //Withdrawal transaction record
            withdrawalTransaction.setTransactionAmount(withdrawAmt);
            withdrawalTransaction.setTransactionBankAccFinalAmount(bankAccFutureBal);
            withdrawalTransactionFinal = (M_IBalanceChange) withdrawalTransaction;

            //Bank account updated record
            M_IBankAccount currentBankAcc = MV_Global.sessionBankAcc;
            currentBankAcc.setBankAccBalance(bankAccFutureBal);

            //Put new transactions to Data Access layer
            try{
                //Create withdrawal transaction
                daStatusCode = transactionDA.dbBalanceChange_Create(withdrawalTransactionFinal);
                if(daStatusCode != 0) return new int[]{6};

                //Update bank acc record
                daStatusCode = bankAccountDA.dBankAccounts_Update(currentBankAcc);
                if(daStatusCode != 0) return new int[]{6};
            }
            catch(Exception e){
                return new int[]{6};
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
    //  V_Deposit: Logic for deposit action
    public short VDeposit_deposit(double depositAmt){
        //Status code:
        //  0: Ok
        //  1: Data access layer withdrawal transaction error
        //  2: Data access layer bank account update error

        //Variable initialization
        DA_Transaction transactionDA = new DA_Transaction();
        DA_BankAccount bankAccountDA = new DA_BankAccount();
        M_IBalanceChange depositTransactionFinal;
        M_BalanceChange depositTransaction;
        String transactionDesc, currency;
        int[] notes;
        double bankAccFutureBal, bankAccBal = MV_Global.sessionBankAcc.getBankAccBalance();
        short daStatusCode = 0;

        //Acquire locality currency symbol
        currency = getCurrencySymbol(MV_Global.getAtmID().split("-")[1]);

        //Forecast future balance
        bankAccFutureBal = bankAccBal + depositAmt;

        //Ensure 2dp consistency
        bankAccFutureBal = Math.round(bankAccFutureBal * 100);
        bankAccFutureBal /= 100;

        //Set trasaction description
        transactionDesc = 
            "Deposit of " + currency + " " + String.format("%.2f", depositAmt) +
            " at ATM " + MV_Global.getAtmID();

        //Withdrawal transaction record
        depositTransaction = new M_BalanceChange(true);
        depositTransaction.setTransactionType((short) 1);
        depositTransaction.setTransactionDescription(transactionDesc);
        depositTransaction.setTransactionOverseas(isBankAccOverseas());
        depositTransaction.setTransactionSrcBankAccID(MV_Global.sessionBankAcc.getBankAccID());
        depositTransaction.setTransactionBankAccInitialAmount(bankAccBal);
        depositTransaction.executeOnAtm();
        depositTransaction.setExecutedOnPurchase(false);
        depositTransaction.setTransactionAmount(depositAmt);
        depositTransaction.setTransactionBankAccFinalAmount(bankAccFutureBal);
        depositTransactionFinal = (M_IBalanceChange) depositTransaction;

        //Updated bank account record
        M_IBankAccount updatedBankAccRecord = MV_Global.sessionBankAcc;
        updatedBankAccRecord.setBankAccBalance(bankAccFutureBal);
		updatedBankAccRecord.updated();

        //Put new transactions to Data Access layer
        try{
            //Create withdrawal transaction
            daStatusCode = transactionDA.dbBalanceChange_Create(depositTransactionFinal);
            if(daStatusCode != 0) return 1;

            //Update bank acc record
            daStatusCode = bankAccountDA.dBankAccounts_Update(updatedBankAccRecord);
            if(daStatusCode != 0) return 2;
        }
        catch(Exception e){
            return 3;
        }
        
        //Update session bank data
        MV_Global.sessionBankAcc.setBankAccBalance(bankAccFutureBal);

        //Update ATM note count
        notes = denominationCal(depositAmt, true);
        for(int i = 0; i < notes.length; i++)
            MV_Global.availableNotes[i][1] += notes[i];
    
        return 0;
    }
    //  V_Transfer: Logic for transfer action
    public short VTransfer_transfer(double transferAmt, String destBankAccID, boolean surchargeAcknowledgement) throws M_ModelViewException{
        //Status codes:
        //  0 - Ok
        //  1 - Requires surcharge acknowledgement
        //  2 - Insufficient funds for surcharge
        //  3 - Transaction error

        //If destination bank is overseas and no surcharge acknowledgement
        if(isBankAccOverseas(destBankAccID) && !surchargeAcknowledgement) return 1;

        //Variable initialization
        M_IBankAccount sendingBankAcc, receivingBankAcc;
        DA_BankAccount bankAccountDA = new DA_BankAccount();
        M_BalanceChange surchargeTransaction;
        M_BalanceTransfer transferSending, transferReceiving;
        M_IBalanceChange surchargeTransactionFinal = null;
        M_IBalanceTransfer transferSendingFinal, transferReceivingFinal;
        String 
            transactionDesc, 
            sendingBankCurrency, 
            receivingBankLocality;
        double 
            surchargeAmt = 0, transferAmtConverted = 0,
            sendingBankInitialBal, sendingBankFinalBal, 
            receivingBankInitialBal, receivingBankFinalBal, 
            sendingBankFinalWithSurchargeBal = 0;
        short daStatusCode;
        boolean overseasTransfer = isBankAccOverseas(destBankAccID);

        //Acquire sending party bank account data
        sendingBankAcc = MV_Global.sessionBankAcc;

        //Acquire receiving party bank account data
        try{
            receivingBankAcc = bankAccountDA.dBankAccounts_GetByID(destBankAccID);
        }
        catch(M_DataAccessException DAe){
            return 3;
        }
        receivingBankLocality = getBankAccCountryCode(destBankAccID);

        //Check currencies
        sendingBankCurrency = getCurrencySymbol(MV_Global.getAtmID().split("-")[1]);

        //Prepare transaction description
        transactionDesc = 
            "Transfer " + sendingBankCurrency + " " + String.format("%.2f", transferAmt) + " from " +
            MV_Global.sessionBankAcc.getBankAccID() + " to " + destBankAccID + " @ ATM " + MV_Global.getAtmID();

        //Calculate surcharge
        if(overseasTransfer) {
            surchargeAmt = transferAmt * MV_Global.getOverseasTransactionCharge();
            surchargeAmt = Math.round(surchargeAmt * 100);
            surchargeAmt /= 100;
        }
        
        //Process and calculate receiving bank account's record
        receivingBankInitialBal = receivingBankAcc.getBankAccBalance();
        if(overseasTransfer){
            //Convert withdrawAmt to base currency, SGD
            transferAmtConverted = convertToBaseCurrency(transferAmt, MV_Global.getAtmID().split("-")[1]);
            transferAmtConverted = Math.round(transferAmtConverted * 100);
            transferAmtConverted /= 100;

            //Convert withdrawAmt from base currency, SGD, to receiving bank acc currency
            transferAmtConverted = convertFromBaseCurrency(transferAmtConverted, receivingBankLocality);
            transferAmtConverted = Math.round(transferAmtConverted * 100);
            transferAmtConverted /= 100;

            receivingBankFinalBal = receivingBankInitialBal + transferAmtConverted;
        }
        else receivingBankFinalBal = receivingBankInitialBal + transferAmt;

        //Process and calculate sending bank account's record
        sendingBankInitialBal = MV_Global.sessionBankAcc.getBankAccBalance();
        sendingBankFinalBal = sendingBankInitialBal - transferAmt;
        if(overseasTransfer) sendingBankFinalWithSurchargeBal = sendingBankFinalBal - surchargeAmt;

        //Ensure 2dp accuracy
        sendingBankFinalBal = Math.round(sendingBankFinalBal * 100);
        sendingBankFinalBal /= 100;
        receivingBankFinalBal = Math.round(receivingBankFinalBal * 100);
        receivingBankFinalBal /= 100;
        if(overseasTransfer){
            sendingBankFinalWithSurchargeBal = Math.round(sendingBankFinalWithSurchargeBal * 100);
            sendingBankFinalWithSurchargeBal /= 100;
        }

        //Transfer Sending transaction record
        transferSending = new M_BalanceTransfer(true);
        transferSending.setTransactionType((short) 3);
        transferSending.setTransactionDescription(transactionDesc);
        transferSending.setTransactionOverseas(overseasTransfer);
        transferSending.setTransactionSrcBankAccID(MV_Global.sessionBankAcc.getBankAccID());
        transferSending.setTransactionBankAccInitialAmount(sendingBankInitialBal);
        transferSending.setTransactionAmount(transferAmt);
        transferSending.setTransactionBankAccFinalAmount(receivingBankFinalBal);
        transferSending.setTransactionTargetBankAccID(destBankAccID);
        transferSendingFinal = (M_IBalanceTransfer) transferSending;

        //Transfer Receiving transaction record
        transferReceiving = new M_BalanceTransfer(true);
        transferReceiving.setTransactionType((short) 4);
        transferReceiving.setTransactionDescription(transactionDesc);
        transferReceiving.setTransactionOverseas(overseasTransfer);
        transferReceiving.setTransactionSrcBankAccID(destBankAccID);
        transferReceiving.setTransactionBankAccInitialAmount(receivingBankInitialBal);
        transferReceiving.setTransactionAmount((overseasTransfer) ? transferAmtConverted :transferAmt);
        transferReceiving.setTransactionBankAccFinalAmount(receivingBankFinalBal);
        transferReceiving.setTransactionTargetBankAccID(MV_Global.sessionBankAcc.getBankAccID());
        transferReceivingFinal = (M_IBalanceTransfer) transferReceiving;

        //Surcharge transaction record
        if(overseasTransfer){
            surchargeTransaction = new M_BalanceChange(true);
            surchargeTransaction.setTransactionType((short) 2);
            surchargeTransaction.setTransactionAmount(surchargeAmt);
            surchargeTransaction.setTransactionDescription(transactionDesc + " [Surcharge]");
            surchargeTransaction.setTransactionOverseas(true);
            surchargeTransaction.setTransactionSrcBankAccID(MV_Global.sessionBankAcc.getBankAccID());
            surchargeTransaction.setTransactionBankAccInitialAmount(receivingBankFinalBal);
            surchargeTransaction.setTransactionBankAccFinalAmount(sendingBankFinalWithSurchargeBal);
            surchargeTransaction.executeOnAtm();
            surchargeTransaction.setExecutedOnPurchase(false);
            surchargeTransactionFinal = (M_IBalanceChange) surchargeTransaction;
        }

        //Sending party's bank account record
        sendingBankAcc.setBankAccBalance((overseasTransfer) ? sendingBankFinalWithSurchargeBal: sendingBankFinalBal);
        sendingBankAcc.updated();

        //Receiving party's bank account record
        receivingBankAcc.setBankAccBalance(receivingBankFinalBal);
        receivingBankAcc.updated();

        VTransfer_updateTransaction updateTxn = new VTransfer_updateTransaction(
            transferSendingFinal,
            transferReceivingFinal,
            surchargeTransactionFinal,
            overseasTransfer
        );
        Thread thread = new Thread(updateTxn);
        thread.start();

        //Post records to Data Access layer
        try{
            //Update receiving bank's record
            daStatusCode = bankAccountDA.dBankAccounts_Update(receivingBankAcc);
            if(daStatusCode != 0) throw new M_ModelViewException("DataAccess layer failed");

            //Update sending bank's record
            daStatusCode = bankAccountDA.dBankAccounts_Update(sendingBankAcc);
            if(daStatusCode != 0) throw new M_ModelViewException("DataAccess layer failed");

            //Update current session's bank account
            MV_Global.sessionBankAcc = sendingBankAcc;
        }
        catch(M_DataAccessException DAe){
            return 3;
        }
        catch(M_ModelViewException MVe){
            return 3;
        }

        try{
            thread.join();
            if(updateTxn.getValue() != 0) throw new M_ModelViewException("DataAccess layer failed");
        }
        catch(InterruptedException ie){
            return 3;
        }
        catch(M_ModelViewException MVe){
            return 3;
        }
        return 0;
    }
    //  V_Transfer: Isolated transaction update function for enabling async processing
    class VTransfer_updateTransaction implements Runnable{
        M_IBalanceTransfer sendingTxn, receivingTxn;
        M_IBalanceChange surcharge;
        boolean isOverseas;

        private volatile int status;

        VTransfer_updateTransaction(
            M_IBalanceTransfer sendingTxn, 
            M_IBalanceTransfer receivingTxn, 
            M_IBalanceChange surcharge, 
            boolean isOverseas){
                this.sendingTxn = sendingTxn;
                this.receivingTxn = receivingTxn;
                this.surcharge = surcharge;
                this.isOverseas = isOverseas;
        }

        @Override
        public void run() {
            DA_Transaction transactionDA = new DA_Transaction();
            short daStatusCode = 0;
            
            try{
                daStatusCode = transactionDA.dbBalanceTransfer_Create(sendingTxn);
                if(daStatusCode != 0) status = 1;

                //Create transfer receiving transaction record
                daStatusCode = transactionDA.dbBalanceTransfer_Create(receivingTxn);
                if(daStatusCode != 0) status = 1;

                //Create surcharge transaction record
                if(isOverseas){
                    daStatusCode = transactionDA.dbBalanceChange_Create(surcharge);
                    if(daStatusCode != 0) status = 1;
                }
            }
            catch(M_DataAccessException DAe){
                status = 2;
            }
            status = 0;
        }

        public int getValue() {
            return status;
        }
    }
    //  V_Transfer: Logic to heck and fetch destination bank account details
    public String VTransfer_checkDestBankAcc(String destBankAccID) throws M_ModelViewException{
        DA_BankAccount bankAccDA = new DA_BankAccount();
        M_IBankAccount returnSearch = null;
        try{
            returnSearch = bankAccDA.dBankAccounts_GetByID(destBankAccID);
        }
        catch(M_DataAccessException DAe){
            throw new M_ModelViewException(DAe);
        }
        
        if(returnSearch == null) return "**INVALID**";
        else if(destBankAccID.equals(MV_Global.sessionBankAcc.getBankAccID())) return "**INVALID**";
        else return returnSearch.getBankAccID();
    }
    //  V_Transfer: Logic to check if bank account is capable of transacting transferAmt
    public short VTransfer_checkBankCapable(double transferAmt) throws M_ModelViewException{
        //Status codes:
        //  0 - Ok
        //  1 - Not enough balance
        //  2 - Transaction limit triggered
        //  3 - Minimum balance triggered
        double 
            bankAccBal = MV_Global.sessionBankAcc.getBankAccBalance(),
            bankAccTxnLimit = MV_Global.sessionBankAcc.getBankAccTransactionLimit(),
            bankAccMinBal = MV_Global.sessionBankAcc.getBankAccMinBalance(),
            forecastedBal = bankAccBal - transferAmt;

        if(forecastedBal < 0) return 1;
        else if(bankAccTxnLimit != 0 && transferAmt > bankAccTxnLimit) return 2;
        else if(forecastedBal < bankAccMinBal) return 3;
        else return 0;
    }
    //  V_ViewDetails: Logic to allow user to change bank acc settings
    public short VViewDetails_updateBankAcc(String desc, double txnLimit, double minBal) throws Exception{
        short status = 0;
        DA_BankAccount bankAccDA = new DA_BankAccount();

        //Bank account updated record
        M_IBankAccount currentBankAcc = MV_Global.sessionBankAcc;
        currentBankAcc.setBankAccDescription(desc);
        currentBankAcc.setBankAccTransactionLimit(txnLimit);
        currentBankAcc.setBankAccMinBalance(minBal);

        //Put new transactions to Data Access layer
        try{
            //Update bank acc record
            status = bankAccDA.dBankAccounts_Update(currentBankAcc);
        }
        catch(Exception e){
            return -2;
        }

        //Set session bank acc to updated values
        if(status == 0) MV_Global.sessionBankAcc = currentBankAcc;

        return status;
    }

    //[ADMIN] View Layer Access Functions
    //  V_ChangeBal: Logic for balance change
    public short VChangeBal_changeBal(double inputAmt){
        //Authorization; Lvl Admin
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
        catch(M_DataAccessException DAe){
            return 1;
        }

        //Update session bank data
        MV_Global.sessionBankAcc.setBankAccBalance(newBal);

        return 0;
    }

    //Inter-Package Functions
    //  Load bankAccID into session
    public void loadBankAccIntoSession(String bankAccID) throws M_ModelViewException{
        //Authentication; A user must be logged on to access this function
		if(MV_Global.sessionUserAcc == null) throw new M_ModelViewException("No session user found.");

		//Variable declaration
        M_IBankAccount targetAcc;
        
        //Acquire bank account from DataAccess layer
        try{
            targetAcc = new DA_BankAccount().dBankAccounts_GetByID(bankAccID);
            //Code safety validation; ensure DataAccess layer returned something
		    if(targetAcc == null) throw new M_ModelViewException("No data returned from DataAccess layer.");
        }
        catch(M_DataAccessException DAe){
            throw new M_ModelViewException("No data returned from DataAccess layer.");
        }

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
    //  Check if bank acc and ATM are in different countries
    public boolean isBankAccOverseas(){
        return isBankAccOverseas("%SESSION%");
    }
    public boolean isBankAccOverseas(String bankAccID){
        if(bankAccID.equals("%SESSION%"))
            return !getBankAccCountryCode().equals(MV_Global.getAtmID().split("-")[1]);
        return !getBankAccCountryCode(bankAccID).equals(MV_Global.getAtmID().split("-")[1]);
    }
    //  Get currency symbol
    public String getCurrencySymbol(String currencyCode){
        for(String datum: MV_Global.getCurrencySymbols()){
            if(currencyCode.equals(datum.split("-")[0])){
                return datum.split("-")[1];
            }
        }
        return "";
    }
    //  Get locality country name
    public String getLocalityName(String countryCode){
        DA_Settings settings = new DA_Settings();
        try{
            String[] countryList = settings.dbSettings_GetByKey("BankCountry");
            
            for(int i = 0; i < countryList.length; i++){
                if(countryList[i].split("-")[0].equals(countryCode)){
                    return countryList[i].split("-")[1];
                }
            }
        }
        catch(Exception e){
            //Ostrich Algorithm
        }
        return "--";
    }
    //  Calculate number of denomination notes for specified amount
    public int[] denominationCal(double amount, boolean getNearestNotes){
        //noteCount1 remainingAmt1
        //  Checks if ATM has enough notes to meet withdraw amount
        //noteCount2 remainingAmt2
        //  Checks if withdraw amount can be met with available denominations

        int[][] availableDenominations = MV_Global.availableNotes;
        int[] notes1 = new int[availableDenominations.length], notes2 = new int[availableDenominations.length];
        int noteCount1 = 0, noteCount2 = 0;
        double remainingAmt1 = amount, remainingAmt2 = amount, currentDenomination;
        Arrays.fill(notes1, 0);
        Arrays.fill(notes2, 0);

        for(int i = availableDenominations.length - 1; i > -1; i--){
            currentDenomination = availableDenominations[i][0];

            noteCount1 = (int)(remainingAmt1 / currentDenomination);
            noteCount2 = (int)(remainingAmt2 / currentDenomination);

            //Calculate denomiation count while factoring available notes
            if(noteCount1 > 0 && noteCount1 <= availableDenominations[i][1]){
                remainingAmt1 -= noteCount1 * currentDenomination;
                notes1[i] = noteCount1;
            }
            else if(noteCount1 > 0 && noteCount1 > availableDenominations[i][1]){
                remainingAmt1 -= availableDenominations[i][1] * currentDenomination;
                notes1[i] = availableDenominations[i][1];
            }

            //Calculate denomiation count without factoring available notes
            if(noteCount2 > 0){
                remainingAmt2 -= noteCount2 * currentDenomination;
                notes2[i] = noteCount2;
            }
        }
    
        //If withdraw amount cannot be met with available denominations
        if(remainingAmt2 != 0 && !getNearestNotes) Arrays.fill(notes1, -1);
        //If withdraw amount cannot be met with availble ATM notes
        else if(remainingAmt1 != 0 && !getNearestNotes) Arrays.fill(notes1, -2);
        
        return (getNearestNotes)? notes2: notes1;
    }

    //Intra-Package Functions
    //  Convert to base currency; X to SGD
    protected double convertToBaseCurrency(double targetAmt, String countryCode) throws M_ModelViewException{
        try{
            String[] conversionRates = new DA_Settings().dbSettings_GetByKey("CurrencyRate");

            for(String conversionRate: conversionRates){
                if(conversionRate.split("-")[0].equals(countryCode)){
                    return targetAmt / (Double.parseDouble(conversionRate.split("-")[1]));
                }
            }
            return 0;
        }
        catch(M_DataAccessException DAe){
            throw new M_ModelViewException(DAe);
        }
    }
    //  Convert from base currency; SGD to X
    protected double convertFromBaseCurrency(double targetAmt, String countryCode) throws M_ModelViewException{
        try{
            String[] conversionRates = new DA_Settings().dbSettings_GetByKey("CurrencyRate");

            for(String conversionRate: conversionRates){
                if(conversionRate.split("-")[0].equals(countryCode)){
                    return targetAmt * (Double.parseDouble(conversionRate.split("-")[1]));
                }
            }
            return 0;
        }
        catch(M_DataAccessException DAe){
            throw new M_ModelViewException(DAe);
        }
    }
}