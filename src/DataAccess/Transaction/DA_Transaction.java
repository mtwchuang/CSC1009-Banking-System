package DataAccess.Transaction;
// import packages from model transactions
import java.io.BufferedReader;
import java.io.FileReader;

import Model.Transaction.*;
import ModelView.MV_Global;
public class DA_Transaction 
{
    // fields "merchantID", "executedBy", "executedAt" omitted
    /* fields within M_Transaction.java
    fields for audit tracking 
	 00: createdBy - String
	 01: createdAt - long
	 02: updatedBy - String
	 03: updatedAt - long
    fields for general transaction records
     04: transactionID - String
     05: transactionType - short
     06: transactionAmount - double
     07: transactionDescription - String
     08: transactionExecuted - boolean
     09: transactionOverseas - boolean
    extra fields for M_BalanceTransfer.java
     10: transactionTargetBankAccID - String
    extra fields for M_BalanceChange.java
     11: executedOnAtm - boolean
     12: AtmID - String
     13: executedOnPurchase - boolean */
    // private function to return one balance transfer
    private M_IBalanceChange dbBalanceTransfer_GetOne(int inputcase, String input) throws Exception
    {
        // instantiate buffer reader, data line, dataSegments, targetBalanceTransfer
        String line;
        String[] dataSegments;
        BufferedReader br = null;
        M_IBalanceTransfer targetBalanceTransfer = null;
        boolean targetFound = false;
        try
        {
            br = new BufferedReader(new FileReader(MV_Global.dbBalanceTransfers));
			//Skip first line; header line
			br.readLine();
            line = br.readLine();
            // transverse through balance change file while current line is not empty
            while(line!=null)
            {
                // split into records based on delimiter
                dataSegments = line.split("\\|");
                // determine if target is found and sets targetFound accordingly
                switch(inputcase)
                {
                    // search by transaction id
                    case(04):
                    {
                        targetFound = (dataSegments[4].equals(input));
                        break;
                    }
                    // any future search implementations if any
                }
                // if transaction id is found,
                if(targetFound)
                {
                    // instantiate object in model layer, temporary object so parameter false
                    M_BalanceTransfer currentBalanceTransfer = new M_BalanceTransfer(false);
                    // call our interface methods to insert data into object
                    currentBalanceTransfer.setCreatedBy(dataSegments[0]);
                    currentBalanceTransfer.setCreatedAt(Long.parseLong(dataSegments[1]));
                    currentBalanceTransfer.setUpdatedBy(dataSegments[2]);
                    currentBalanceTransfer.setUpdatedAt(Long.parseLong(dataSegments[3]));

                    currentBalanceTransfer.setTransactionID(dataSegments[4]);
                    currentBalanceTransfer.setTransactionType(Short.parseShort(dataSegments[5]));
                    currentBalanceTransfer.setTransactionAmount(Double.parseDouble(dataSegments[6]));
                    currentBalanceTransfer.setTransactionDescription(dataSegments[7]);
                    currentBalanceTransfer.setTransactionExecuted(Boolean.parseBoolean(dataSegments[8]));
                    currentBalanceTransfer.setTransactionOverseas(Boolean.parseBoolean(dataSegments[9]));

                    currentBalanceTransfer.setExecutedOnAtm(Boolean.parseBoolean(dataSegments[10]));
                    // mask our model object with an interface to remove unnecessary methods
                    targetBalanceTransfer = (M_BalanceTransfer) currentBalanceTransfer;
                    // exit while loop
                    break;
                }
                // read next balance change record in text file
                line = br.readLine();
            }
        }
        finally
        {
            br.close();
        }
        return targetBalanceChange;
    }
    // private function to return multiple balance transfer
    // private function to return one balance change
    private M_IBalanceChange dbBalanceChange_GetOne(int inputcase, String input) throws Exception
    {
        // instantiate buffer reader, data line, dataSegments, targetBalanceChange
        String line;
        String[] dataSegments;
        BufferedReader br = null;
        M_IBalanceChange targetBalanceChange = null;
        boolean targetFound = false;
        try
        {
            br = new BufferedReader(new FileReader(MV_Global.dbBalanceChanges));
			//Skip first line; header line
			br.readLine();
            line = br.readLine();
            // transverse through balance change file while current line is not empty
            while(line!=null)
            {
                // split into records based on delimiter
                dataSegments = line.split("\\|");
                // determine if target is found and sets targetFound accordingly
                switch(inputcase)
                {
                    // search by transaction id
                    case(04):
                    {
                        targetFound = (dataSegments[4].equals(input));
                        break;
                    }
                    // any future search implementations if any
                }
                // if transaction id is found,
                if(targetFound)
                {
                    // instantiate object in model layer, temporary object so parameter false
                    M_BalanceChange currentBalanceChange = new M_BalanceChange(false);
                    // call our interface methods to insert data into object
                    currentBalanceChange.setCreatedBy(dataSegments[0]);
                    currentBalanceChange.setCreatedAt(Long.parseLong(dataSegments[1]));
                    currentBalanceChange.setUpdatedBy(dataSegments[2]);
                    currentBalanceChange.setUpdatedAt(Long.parseLong(dataSegments[3]));

                    currentBalanceChange.setTransactionID(dataSegments[4]);
                    currentBalanceChange.setTransactionType(Short.parseShort(dataSegments[5]));
                    currentBalanceChange.setTransactionAmount(Double.parseDouble(dataSegments[6]));
                    currentBalanceChange.setTransactionDescription(dataSegments[7]);
                    currentBalanceChange.setTransactionExecuted(Boolean.parseBoolean(dataSegments[8]));
                    currentBalanceChange.setTransactionOverseas(Boolean.parseBoolean(dataSegments[9]));

                    currentBalanceChange.setExecutedOnAtm(Boolean.parseBoolean(dataSegments[10]));
                    currentBalanceChange.setAtmID(dataSegments[11]);
                    currentBalanceChange.setExecutedOnPurchase(Boolean.parseBoolean(dataSegments[12]));
                    // mask our model object with an interface to remove unnecessary methods
                    targetBalanceChange = (M_BalanceChange) currentBalanceChange;
                    // exit while loop
                    break;
                }
                // read next balance change record in text file
                line = br.readLine();
            }
        }
        finally
        {
            br.close();
        }
        return targetBalanceChange;
    }
    // private function to return multiple balance change
    // public function to return transactions by transactionID
    public M_IBalanceChange dbBalanceChange_GetByTransID(String transID) throws Exception
    {
        return dbBalanceChange_GetOne(4, transID);
    }
    // public function to return transactions by bankAcct
    // observing the method in DA_Bankaccount, why is your return type M_IBankAccount, an interface? Not a class type?
    //


    /* public function to return transaction by corporateID */

}