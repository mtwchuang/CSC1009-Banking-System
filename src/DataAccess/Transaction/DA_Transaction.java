package DataAccess.Transaction;
// import packages from model transactions
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import Model.Transaction.*;
import ModelView.MV_Global;
public class DA_Transaction 
{
    /********************************************************&*****/
    /* fields within M_BalanceChange.java / M_BalanceTransfer.java*/
    /**************************************************************/
    // fields for audit tracking 
    //     00: createdBy - String
    //     01: createdAt - long
    //     02: updatedBy - String
    //     03: updatedAt - long
    // fields for general transaction records
    //     04: transactionID - String
    //     05: transactionType - short
    //     06: transactionAmount - double
    //     07: transactionDescription - String
    //     08: transactionExecuted - boolean
    //     09: transactionOverseas - boolean
    //     10: transactionBankAccID - String
    // specific fields for M_BalanceChange.java
    //     11: executedOnAtm - boolean
    //     12: AtmID - String
    //     13: executedOnPurchase - boolean 
    // specific fields for M_BalanceTransfer.java
    //     11: transactionTargetAccID - String 
    
    /*********************************************************/
    /*GET FUNCTIONS FOR BALANCE CHANGES AND BALANCE TRANSFERS*/
    /*********************************************************/
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
    
    // public function to return balance change by transactionID
    public M_IBalanceChange dbBalanceChange_GetByTransID(String transID) throws Exception
    {
        return dbBalanceChange_GetOne(4, transID);
    }
    // public function to return multiple balance changes by userid
    public M_IBalanceChange[] dbBalanceChange_GetByUserID(String userID) throws Exception
    {
        return dbBalanceChange_GetMultiple(0, userID);
    }
    // public function to return multiple balance changes by bankid
    public M_IBalanceChange[] dbBalanceChange_GetByBankID(String bankID) throws Exception
    {
        return dbBalanceChange_GetMultiple(10, bankID);
    }
    // public function to return all balance changes
    public M_IBalanceChange[] dbBalanceChange_GetAll() throws Exception
    {
        // create a temporary collection list that can stores values dynamically
        List<M_IBalanceChange> tempBalanceChanges = new ArrayList<M_IBalanceChange>();
        String line;
		String[] dataSegments;
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

    // public function to return balance transfer by transactionID
    public M_IBalanceTransfer dbBalanceTransfer_GetByTransID(String transID) throws Exception
    {
        return dbBalanceTransfer_GetOne(4, transID);
    }
    // public function to return multiple balance transfers by userid
    public M_IBalanceTransfer[] dbBalanceTransfer_GetByUserID(String userID) throws Exception
    {
        return dbBalanceTransfer_GetMultiple(0, userID);
    }
    // public function to return multiple balance transfers by bankid
    public M_IBalanceTransfer[] dbBalanceTransfer_GetByBankID(String bankID) throws Exception
    {
        return dbBalanceTransfer_GetMultiple(10, bankID);
    }    
    // public function to return all balance transfers
    public M_IBalanceTransfer[] dbBalanceTransfer_GetAll() throws Exception
    {
        // create a temporary collection list that can stores values dynamically
        List<M_IBalanceTransfer> tempBalanceTransfers = new ArrayList<M_IBalanceTransfer>();
        String line;
		String[] dataSegments;
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

    /********************************************************************/
    /*UPDATE (WRITE) FUNCTIONS FOR BALANCE CHANGES AND BALANCE TRANSFERS*/
    /********************************************************************/
    // public function to rewrite balance change records + inputbalchange record, returns an error message if any
    public short dbBalanceChange_Update(M_IBalanceChange inputBalChange) throws Exception
    {
        String line = null;
        BufferedReader br = null;
        PrintWriter writer = null;
        try
        {
            br = new BufferedReader(new FileReader(MV_Global.dbBalanceChanges));
            // line starts off with header
            line = br.readLine();
            String newBalanceChange = "";
            while(line!=null)
            {
                newBalanceChange+=line+"\n";
                line = br.readLine();
            }
            // append values of inputBalChange into newBalanceChange and separate by delimiters
            newBalanceChange+=(inputBalChange.getCreatedBy()+"|"+inputBalChange.getCreatedAt()+
                "|"+inputBalChange.getUpdatedBy()+"|"+inputBalChange.getUpdatedBy()+"|"+inputBalChange.getTransactionDescription()+
                "|"+inputBalChange.getTransactionType()+"|"+inputBalChange.getTransactionAmount()+"|"+inputBalChange.getTransactionDescription()+
                "|"+inputBalChange.isTransactionExecuted()+"|"+inputBalChange.isTransactionOverseas()+"|"+inputBalChange.getTransactionSrcBankAccID()+
                "|"+inputBalChange.isExecutedOnAtm()+"|"+inputBalChange.getAtmID()+"|"+inputBalChange.isExecutedOnPurchase());

            writer = new PrintWriter(MV_Global.dbBalanceChanges);
            writer.print(newBalanceChange);
        }
        catch(Exception e)
        {
            return -1; // not sure if this is correct
        }
        finally
        {
            br.close();
            writer.flush();
            writer.close();
        }
        return 0;
    }
    // public function to rewrite balance transfer records + inputbaltransfer record, returns an error message if any
    public short dbBalanceTransfer_Update(M_IBalanceTransfer inputBalTransfer) throws Exception
    {
        String line = null;
        BufferedReader br = null;
        PrintWriter writer = null;
        try
        {
            br = new BufferedReader(new FileReader(MV_Global.dbBalanceTransfers));
            // line starts off with header
            line = br.readLine();
            String newBalanceTransfer = "";
            while(line!=null)
            {
                newBalanceTransfer+=line+"\n";
                line = br.readLine();
            }
            // append values of inputBalTransfer into newBalanceTransfer and separate by delimiters
            newBalanceTransfer+=(inputBalTransfer.getCreatedBy()+"|"+inputBalTransfer.getCreatedAt()+
                "|"+inputBalTransfer.getUpdatedBy()+"|"+inputBalTransfer.getUpdatedBy()+"|"+inputBalTransfer.getTransactionDescription()+
                "|"+inputBalTransfer.getTransactionType()+"|"+inputBalTransfer.getTransactionAmount()+"|"+inputBalTransfer.getTransactionDescription()+
                "|"+inputBalTransfer.isTransactionExecuted()+"|"+inputBalTransfer.isTransactionOverseas()+"|"+inputBalTransfer.getTransactionSrcBankAccID()+
                "|"+inputBalTransfer.getTransactionTargetBankAccID());

            writer = new PrintWriter(MV_Global.dbBalanceTransfers);
            writer.print(newBalanceTransfer);
        }
        catch(Exception e)
        {
            return -1; // not sure if this is correct
        }
        finally
        {
            br.close();
            writer.flush();
            writer.close();
        }
        return 0;
    }
    

    /***************************************************************/
    /*DELETE FUNCTIONS BY FOR BALANCE CHANGES AND BALANCE TRANSFERS*/
    /***************************************************************/
    

    // public static void main(String[] args) throws Exception
    // {
    //     // M_BalanceChange bc1 = new M_BalanceChange(false);
    //     M_BalanceTransfer bf1 = new M_BalanceTransfer(false);
    //     DA_Transaction da1 = new DA_Transaction();
    //     // da1.dbBalanceChange_Update((M_IBalanceChange) bc1);
    //     da1.dbBalanceTransfer_Update((M_IBalanceTransfer) bf1);
    // }

}