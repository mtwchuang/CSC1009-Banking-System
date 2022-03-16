package DataAccess.Transaction;
// import packages from model transactions
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import Model.Transaction.*;
import ModelView.MV_Global;
// fields "merchantID", "executedBy", "executedAt" omitted
public class DA_Transaction 
{
    /* fields within M_BalanceChange.java / M_BalanceTransfer.java
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
     10: transactionBankAccID - String
    specific fields for M_BalanceChange.java
     11: executedOnAtm - boolean
     12: AtmID - String
     13: executedOnPurchase - boolean 
     specific fields for M_BalanceTransfer.java
     11: transactionTargetAccID - String 
    */
     
    // private function to return one balance transfer
    private M_IBalanceTransfer dbBalanceTransfer_GetOne(int inputcase, String input) throws Exception
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
                    case(4):
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
                    // call methods to insert data into object
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
                    currentBalanceTransfer.setTransactionSrcBankAccID(dataSegments[10]);

                    currentBalanceTransfer.setTransactionTargetBankAccID(dataSegments[11]);
                    // mask our model object with an interface to remove unnecessary methods
                    targetBalanceTransfer = (M_BalanceTransfer) currentBalanceTransfer;
                    // exit while loop and end search
                    break;
                }
                // read next balance transfer record in text file
                line = br.readLine();
            }
        }
        finally
        {
            br.close();
        }
        return targetBalanceTransfer;
    }
    // private function to return multiple balance transfer
    private M_IBalanceTransfer[] dbBalanceTransfer_GetMultiple(int inputcase, String input) throws Exception
    {
        // create a temporary collection list that can stores values dynamically
        List<M_IBalanceTransfer> tempBalanceTransfers = new ArrayList<M_IBalanceTransfer>();
        String line;
		String[] dataSegments;
        boolean matchFound = false;
		BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(MV_Global.dbBalanceTransfers));
			//Skip first line; header line
			br.readLine();
            line = br.readLine();
            // transverse through balance transfer file while current line is not empty
            while(line!=null)
            {
                // split into records based on delimiter
                dataSegments = line.split("\\|");
                // determine if match is found according to inputcase and sets matchFound accordinly
                switch(inputcase)
                {
                    // search by userid
                    case(0):
                    {
                        matchFound = (dataSegments[0].equals(input));
                        break;
                    }
                    // search by bankid
                    case(10):
                    {
                        matchFound = (dataSegments[10].equals(input));
                        break;
                    }
                }
                if(matchFound)
                {
                    // instantiate object in model layer, temporary object so parameter false
                    M_BalanceTransfer currentBalanceTransfer = new M_BalanceTransfer(false);
                    // call methods to insert data into object
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
                    currentBalanceTransfer.setTransactionSrcBankAccID(dataSegments[10]);

                    currentBalanceTransfer.setTransactionTargetBankAccID(dataSegments[11]);
                    // add current balance change record to arraylist tempBalanceTransfer
                    tempBalanceTransfers.add((M_IBalanceTransfer) currentBalanceTransfer);
                    // reset matchFound to false
                    matchFound = false;
                }
                line = br.readLine();
            }            
        }   
        finally
        {
            br.close();
        }
        // instantiate a balance change array 
        M_IBalanceTransfer[] balanceTransfers = new M_IBalanceTransfer[tempBalanceTransfers.size()];
        // convert collections arraylist back into an array for M_IBalanceTransfer
        for(int i = 0; i<tempBalanceTransfers.size(); i++)
        {
            balanceTransfers[i] = tempBalanceTransfers.get(i);
        }
        return balanceTransfers;
    }
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
                    case(4):
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
                    // call methods to insert data into object
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
                    currentBalanceChange.setTransactionSrcBankAccID(dataSegments[10]);

                    currentBalanceChange.setExecutedOnAtm(Boolean.parseBoolean(dataSegments[11]));
                    currentBalanceChange.setAtmID(dataSegments[12]);
                    currentBalanceChange.setExecutedOnPurchase(Boolean.parseBoolean(dataSegments[13]));
                    // mask our model object with an interface to remove unnecessary methods
                    targetBalanceChange = (M_BalanceChange) currentBalanceChange;
                    // exit while loop and end search
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
    private M_IBalanceChange[] dbBalanceChange_GetMultiple(int inputcase, String input) throws Exception
    {
        // create a temporary collection list that can stores values dynamically
        List<M_IBalanceChange> tempBalanceChanges = new ArrayList<M_IBalanceChange>();
        String line;
		String[] dataSegments;
        boolean matchFound = false;
		BufferedReader br = null;
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
                // determine if match is found according to inputcase and sets matchFound accordinly
                switch(inputcase)
                {
                    // search by userid
                    case(0):
                    {
                        matchFound = (dataSegments[0].equals(input));
                        break;
                    }
                    // search by bankid
                    case(10):
                    {
                        matchFound = (dataSegments[10].equals(input));
                        break;
                    }
                }
                if(matchFound)
                {
                    // instantiate object in model layer, temporary object so parameter false
                    M_BalanceChange currentBalanceChange = new M_BalanceChange(false);
                    // call methods to insert data into object
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
                    currentBalanceChange.setTransactionSrcBankAccID(dataSegments[10]);

                    currentBalanceChange.setExecutedOnAtm(Boolean.parseBoolean(dataSegments[11]));
                    currentBalanceChange.setAtmID(dataSegments[12]);
                    currentBalanceChange.setExecutedOnPurchase(Boolean.parseBoolean(dataSegments[13]));
                    // add current balance change record to arraylist tempBalanceChanges
                    tempBalanceChanges.add((M_IBalanceChange) currentBalanceChange);
                    // reset matchFound to false
                    matchFound = false;
                }
                line = br.readLine();
            }            
        }   
        finally
        {
            br.close();
        }
        // instantiate a balance change array 
        M_IBalanceChange[] balanceChanges = new M_IBalanceChange[tempBalanceChanges.size()];
        // convert collections arraylist back into an array for M_IBalanceChanges
        for(int i = 0; i<tempBalanceChanges.size(); i++)
        {
            balanceChanges[i] = tempBalanceChanges.get(i);
        }
        return balanceChanges;
    }
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