package ModelView.Transaction;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import DataAccess.Global.DA_Settings;
import DataAccess.Transaction.DA_Transaction;
import Model.Transaction.M_IBalanceChange;
import Model.Transaction.M_IBalanceTransfer;
import Model.Transaction.M_ITransaction;
import ModelView.BankAccount.MV_BankAccount;
import ModelView.Global.MV_Global;

public class MV_Transaction{
    //View Layer Access Functions
    //  V_TransactionIndex: Acquisition of all transactions
    public String[] VTransactionIndex_getAllTxn(int cacheInstance, int page) throws Exception{
        //Variable initialization
        List<M_ITransaction> displayTxn = new ArrayList<M_ITransaction>();
        DA_Settings settingsDA = new DA_Settings();
        DA_Transaction txnDA = new DA_Transaction();
        LocalDateTime txnTime;
        DateTimeFormatter dTimeFormatter;
        Instant instant;
        String[] timeZones;
        String atmTimeZone, dtFormatSettings, 
            atmLocality, bankAccLocality,
            currency, tnxTimeStr, tnxTypeStr, txnAmtStr;
        double txnAmt;
        long txnCreatedTime;
        int firstRecord, lastRecord, pagingSize, maxPage, i;
        short txnType;

        //Acquire paging size from settings
        pagingSize = Integer.parseInt(settingsDA.dbSettings_GetByKey("PagingSize")[0]);

        //Acquire ATM time zone form settings
        atmLocality = MV_Global.getAtmID().split("-")[1];
        timeZones = settingsDA.dbSettings_GetByKey("TimeZone");
        atmTimeZone = Arrays.stream(timeZones)
            .filter(x -> x.split("-")[0].equals(atmLocality))
            .findFirst()
            .get()
            .split("-")[1];

        //Acquire and initialize datetime format from settings
        dtFormatSettings = settingsDA.dbSettings_GetByKey("DateFormat")[0];
        dTimeFormatter = DateTimeFormatter.ofPattern(dtFormatSettings);

        //Acquire curreny symbol from settings
        bankAccLocality = new MV_BankAccount().getBankAccCountryCode();
        currency = Arrays.stream(MV_Global.getCurrencySymbols())
            .filter(x -> x.split("-")[0].equals(bankAccLocality))
            .findFirst()
            .get()
            .split("-")[1];

        //Caching instance invalid for current viewing session
        if(cacheInstance != MV_Global.cacheInstance){
            //Fetch balance changes from Data Access layer
            M_IBalanceChange[] balanceChanges = txnDA.dbBalanceChange_GetByBankID(MV_Global.sessionBankAcc.getBankAccID());

            //Fetch balance transfers from Data Access layer
            M_IBalanceTransfer[] balanceTransfers = txnDA.dbBalanceTransfer_GetByBankID(MV_Global.sessionBankAcc.getBankAccID());

            //Concate all transaction data and load into cache
            MV_Global.cacheTxn = mergeTxns(balanceChanges, balanceTransfers);
            
            //Update cache instance
            MV_Global.cacheInstance = cacheInstance;

            //Sort all transactions by createdAt
            Arrays.sort(MV_Global.cacheTxn, Comparator.comparingLong(x -> x.getCreatedAt()));
            //Reverse sorted transactions order
            M_ITransaction[] temp = new M_ITransaction[MV_Global.cacheTxn.length];
            for(i = 0; i < MV_Global.cacheTxn.length; i++)
                temp[i] = MV_Global.cacheTxn[MV_Global.cacheTxn.length - 1 - i];
            MV_Global.cacheTxn = temp;
        }

        //Acquire max page number
        maxPage = MV_Global.cacheTxn.length / pagingSize;
        if(maxPage == 0) maxPage = 1;
        if(page == 0) page = 1;
        if((MV_Global.cacheTxn.length % pagingSize) > 0) maxPage++;
        if(page > maxPage) page = maxPage;

        //Calculate page's first record's index
        firstRecord = (page - 1) * pagingSize;
        //Calculate page's last record's index
        lastRecord = (page * pagingSize) - 1;
        if(lastRecord >= MV_Global.cacheTxn.length) lastRecord = MV_Global.cacheTxn.length - 1;

        //Acquire page data
        for(i = firstRecord; i < lastRecord + 1; i++)
            displayTxn.add(MV_Global.cacheTxn[i]);

        //Transform data to display format
        String[] displayTxnTransformed = new String[displayTxn.size() + 1];
        for(i = 0; i < displayTxn.size(); i++){
            displayTxnTransformed[i] = "";

            //Format transaction created time to monkey readable format
            txnCreatedTime = displayTxn.get(i).getCreatedAt();
            instant = Instant.ofEpochMilli(txnCreatedTime);
            txnTime = LocalDateTime.ofInstant(instant, ZoneId.of(atmTimeZone));
            tnxTimeStr = txnTime.format(dTimeFormatter);
            //Append formatted time to current entry
            displayTxnTransformed[i] += tnxTimeStr + "\t";
            
            //Map transaction type
            txnType = displayTxn.get(i).getTransactionType();
            switch(txnType){
                case 0:
                    tnxTypeStr = "Withdrawal\t";
                    break;
                case 1:
                    tnxTypeStr = "Deposit\t\t";
                    break;
                case 2:
                    tnxTypeStr = "Charge/Purchase\t";
                    break;
                case 3:
                    tnxTypeStr = "Transfer [Sent]\t";
                    break;
                case 4:
                    tnxTypeStr = "Transfer [Received]";
                    break;
                default:
                    tnxTypeStr = "Transaction\t";
            }
            //Append transaction type to current entry
            displayTxnTransformed[i] += tnxTypeStr + "\t";

            //Format transaction amount
            txnAmt = displayTxn.get(i).getTransactionAmount();
            txnAmtStr = currency + " " + String.format("%.2f", txnAmt);
            //Append transaction type to current entry
            displayTxnTransformed[i] += txnAmtStr + "\t";

            //Append transaction description to current entry
            displayTxnTransformed[i] += displayTxn.get(i).getTransactionDescription();
        }
        
        displayTxnTransformed[displayTxn.size()] = 
            "Showing " + ((lastRecord - firstRecord) + 1) + " of " + MV_Global.cacheTxn.length + " records" +
            "\nPage " + page + " of " + maxPage + "|" + page;

        return displayTxnTransformed;
    }

    //Internal Functions
    //  Merge two transactions of different models
    private M_ITransaction[] mergeTxns(M_IBalanceChange[] balanceChanges, M_IBalanceTransfer[] balanceTransfers){
        M_ITransaction[] returnData = new M_ITransaction[balanceChanges.length + balanceTransfers.length];

        int i = 0, j;
        for(j = 0; j < balanceChanges.length; j++){
            returnData[i] = (M_ITransaction) balanceChanges[j];
            i++;
        }

        for(j = 0; j < balanceTransfers.length; j++){
            returnData[i] = (M_ITransaction) balanceTransfers[j];
            i++;
        }

        return returnData;
    }
}