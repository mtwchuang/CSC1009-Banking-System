package DataAccess.Transaction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import Model.Global.M_DataAccessException;
import Model.Transaction.*;
import ModelView.Global.MV_Global;

public class DA_Transaction {
	/* Model:
	M_Transaction [Abstract Parent]
		00: createdBy - String
		01: createdAt - long
		02: updatedBy - String
		03: updatedAt - long
		04: transactionID - String
			Case 00: Withdrawals
			Case 01: Deposits
			Case 02: Charges/Purchases
			Case 03: Transfer Sending
			Case 04: Transfer Receiving
		05: transactionType - short
		06: transactionAmount - double
		07: transactionDescription - String
		08: transactionOverseas - boolean
		09: transactionBankAccID - String
		10: transactionBankAccInitialAmount - double
		11: transactionBankAccFinalAmount - double
	M_BalanceChange [Extends Parent]
		12: executedOnAtm - boolean
		13: AtmID - String
		14: executedOnPurchase - boolean
	M_BalanceTransfer [Extends Parent]
		15: transactionTargetBankAccID - String 
	*/
	
	//GET Methods for M_IBalanceTransfer
	//	Main GET function for acquiring 1 record of M_IBalanceTransfer
	private M_IBalanceTransfer dbBalanceTransfer_GetOne(int inputcase, String input) throws M_DataAccessException{
		// instantiate buffer reader, data line, dataSegments, targetBalanceTransfer
		String line;
		String[] dataSegments;
		BufferedReader br = null;
		M_IBalanceTransfer targetBalanceTransfer = null;
		boolean targetFound = false;
		try{
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
					currentBalanceTransfer.setTransactionOverseas(Boolean.parseBoolean(dataSegments[8]));
					currentBalanceTransfer.setTransactionSrcBankAccID(dataSegments[9]);
					currentBalanceTransfer.setTransactionBankAccInitialAmount(Double.parseDouble(dataSegments[10]));
					currentBalanceTransfer.setTransactionBankAccFinalAmount(Double.parseDouble(dataSegments[11]));

					currentBalanceTransfer.setTransactionTargetBankAccID(dataSegments[15]);

					// mask our model object with an interface to remove unnecessary methods
					targetBalanceTransfer = (M_BalanceTransfer) currentBalanceTransfer;
					// exit while loop and end search
					break;
				}
				// read next balance transfer record in text file
				line = br.readLine();
			}
		}
		catch(Exception e){
			throw new M_DataAccessException(e);
		}
        finally{
			try{
				br.close();
			}
			catch(Exception e){
				throw new M_DataAccessException(e);
			}
		}
		return targetBalanceTransfer;
	}
	//	GET function for acquiring 1 record of M_IBalanceTransfer by 04:transactionID
	public M_IBalanceTransfer dbBalanceTransfer_GetByTxnID(String transID) throws M_DataAccessException{
		return dbBalanceTransfer_GetOne(4, transID);
	}
	//	Main GET function for acquiring multiple records of M_IBalanceTransfer
	private M_IBalanceTransfer[] dbBalanceTransfer_GetMultiple(int inputcase, String input) throws M_DataAccessException{
		// create a temporary collection list that can stores values dynamically
		List<M_IBalanceTransfer> tempBalanceTransfers = new ArrayList<M_IBalanceTransfer>();
		String line;
		String[] dataSegments;
		boolean matchFound = false;
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(MV_Global.dbBalanceTransfers));
			//Skip first line; header line
			br.readLine();
			line = br.readLine();
			// transverse through balance transfer file while current line is not empty
			while(line!=null){
				// split into records based on delimiter
				dataSegments = line.split("\\|");
				// determine if match is found according to inputcase and sets matchFound accordinly
				switch(inputcase){
					// search by createdBy
					case(0):
						matchFound = (dataSegments[0].equals(input));
						break;
					// search by bankid
					case(9):
						matchFound = (dataSegments[9].equals(input));
						break;
				}
				if(matchFound){
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
					currentBalanceTransfer.setTransactionOverseas(Boolean.parseBoolean(dataSegments[8]));
					currentBalanceTransfer.setTransactionSrcBankAccID(dataSegments[9]);
					currentBalanceTransfer.setTransactionBankAccInitialAmount(Double.parseDouble(dataSegments[10]));
					currentBalanceTransfer.setTransactionBankAccFinalAmount(Double.parseDouble(dataSegments[11]));

					currentBalanceTransfer.setTransactionTargetBankAccID(dataSegments[15]);

					// add current balance change record to arraylist tempBalanceTransfer
					tempBalanceTransfers.add((M_IBalanceTransfer) currentBalanceTransfer);
					// reset matchFound to false
					matchFound = false;
				}
				line = br.readLine();
			}
		}
		catch(Exception e){
			throw new M_DataAccessException(e);
		}
        finally{
			try{
				br.close();
			}
			catch(Exception e){
				throw new M_DataAccessException(e);
			}
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
	//	GET function for acquiring multiple records of M_IBalanceTransfer by 00: createdBy
	public M_IBalanceTransfer[] dbBalanceTransfer_GetByUserID(String userID) throws M_DataAccessException
	{
		return dbBalanceTransfer_GetMultiple(0, userID);
	}
	//	GET function for acquiring multiple records of M_IBalanceTransfer by 09: transactionBankAccID
	public M_IBalanceTransfer[] dbBalanceTransfer_GetByBankID(String bankID) throws M_DataAccessException
	{
		return dbBalanceTransfer_GetMultiple(9, bankID);
	}    
	//	GET function to acquire all records of M_IBalanceTransfer
	public M_IBalanceTransfer[] dbBalanceTransfer_GetAll() throws M_DataAccessException{
		// create a temporary collection list that can stores values dynamically
		List<M_IBalanceTransfer> tempBalanceTransfers = new ArrayList<M_IBalanceTransfer>();
		String line;
		String[] dataSegments;
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(MV_Global.dbBalanceTransfers));
			//Skip first line; header line
			br.readLine();
			line = br.readLine();
			// transverse through balance transfer file while current line is not empty
			while(line!=null){
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
				currentBalanceTransfer.setTransactionOverseas(Boolean.parseBoolean(dataSegments[8]));
				currentBalanceTransfer.setTransactionSrcBankAccID(dataSegments[9]);
				currentBalanceTransfer.setTransactionBankAccInitialAmount(Double.parseDouble(dataSegments[10]));
				currentBalanceTransfer.setTransactionBankAccFinalAmount(Double.parseDouble(dataSegments[11]));

				currentBalanceTransfer.setTransactionTargetBankAccID(dataSegments[15]);
				
				// add current balance change record to arraylist tempBalanceTransfer
				tempBalanceTransfers.add((M_IBalanceTransfer) currentBalanceTransfer);
				line = br.readLine();
			}
		}
		catch(Exception e){
			throw new M_DataAccessException(e);
		}
        finally{
			try{
				br.close();
			}
			catch(Exception e){
				throw new M_DataAccessException(e);
			}
		}
		// instantiate a balance change array 
		M_IBalanceTransfer[] balanceTransfers = new M_IBalanceTransfer[tempBalanceTransfers.size()];
		// convert collections arraylist back into an array for M_IBalanceTransfer
		for(int i = 0; i<tempBalanceTransfers.size(); i++)
			balanceTransfers[i] = tempBalanceTransfers.get(i);
		return balanceTransfers;
	}

	//GET Methods for M_IBalanceChange
	//	Main GET function for acquiring 1 record of M_IBalanceChange
	private M_IBalanceChange dbBalanceChange_GetOne(int inputcase, String input) throws M_DataAccessException
	{
		// instantiate buffer reader, data line, dataSegments, targetBalanceChange
		String line;
		String[] dataSegments;
		BufferedReader br = null;
		M_IBalanceChange targetBalanceChange = null;
		boolean targetFound = false;
		try{
			br = new BufferedReader(new FileReader(MV_Global.dbBalanceChanges));
			//Skip first line; header line
			br.readLine();
			line = br.readLine();
			// transverse through balance change file while current line is not empty
			while(line!=null){
				// split into records based on delimiter
				dataSegments = line.split("\\|");
				// determine if target is found and sets targetFound accordingly
				switch(inputcase){
					// search by transaction id
					case(4):
					{
						targetFound = (dataSegments[4].equals(input));
						break;
					}
					// any future search implementations if any
				}
				// if transaction id is found,
				if(targetFound){
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
					currentBalanceChange.setTransactionOverseas(Boolean.parseBoolean(dataSegments[8]));
					currentBalanceChange.setTransactionSrcBankAccID(dataSegments[9]);
					currentBalanceChange.setTransactionBankAccInitialAmount(Double.parseDouble(dataSegments[10]));
					currentBalanceChange.setTransactionBankAccFinalAmount(Double.parseDouble(dataSegments[11]));

					currentBalanceChange.setExecutedOnAtm(Boolean.parseBoolean(dataSegments[12]));
					currentBalanceChange.setAtmID(dataSegments[13]);
					currentBalanceChange.setExecutedOnPurchase(Boolean.parseBoolean(dataSegments[14]));

					// mask our model object with an interface to remove unnecessary methods
					targetBalanceChange = (M_BalanceChange) currentBalanceChange;
					// exit while loop and end search
					break;
				}
				// read next balance change record in text file
				line = br.readLine();
			}
		}
		catch(Exception e){
			throw new M_DataAccessException(e);
		}
        finally{
			try{
				br.close();
			}
			catch(Exception e){
				throw new M_DataAccessException(e);
			}
		}
		return targetBalanceChange;
	}
	//	GET function for acquiring 1 record of M_IBalanceChange by 04:transactionID
	public M_IBalanceChange dbBalanceChange_GetByTxnID(String transID) throws M_DataAccessException
	{
		return dbBalanceChange_GetOne(4, transID);
	}
	//	Main GET function for acquiring multiple records of M_IBalanceChange
	private M_IBalanceChange[] dbBalanceChange_GetMultiple(int inputcase, String input) throws M_DataAccessException
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
					case(9):
					{
						matchFound = (dataSegments[9].equals(input));
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
					currentBalanceChange.setTransactionOverseas(Boolean.parseBoolean(dataSegments[8]));
					currentBalanceChange.setTransactionSrcBankAccID(dataSegments[9]);
					currentBalanceChange.setTransactionBankAccInitialAmount(Double.parseDouble(dataSegments[10]));
					currentBalanceChange.setTransactionBankAccFinalAmount(Double.parseDouble(dataSegments[11]));

					currentBalanceChange.setExecutedOnAtm(Boolean.parseBoolean(dataSegments[12]));
					currentBalanceChange.setAtmID(dataSegments[13]);
					currentBalanceChange.setExecutedOnPurchase(Boolean.parseBoolean(dataSegments[14]));

					// add current balance change record to arraylist tempBalanceChanges
					tempBalanceChanges.add((M_IBalanceChange) currentBalanceChange);
					// reset matchFound to false
					matchFound = false;
				}
				line = br.readLine();
			}
		}
		catch(Exception e){
			throw new M_DataAccessException(e);
		}
        finally{
			try{
				br.close();
			}
			catch(Exception e){
				throw new M_DataAccessException(e);
			}
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
	//	GET function for acquiring multiple records of M_IBalanceChange by 00: createdBy
	public M_IBalanceChange[] dbBalanceChange_GetByUserID(String userID) throws M_DataAccessException
	{
		return dbBalanceChange_GetMultiple(0, userID);
	}
	//	GET function for acquiring multiple records of M_IBalanceChange by 09: transactionBankAccID
	public M_IBalanceChange[] dbBalanceChange_GetByBankID(String bankID) throws M_DataAccessException
	{
		return dbBalanceChange_GetMultiple(9, bankID);
	}
	//	GET function to acquire all records of M_IBalanceChange
	public M_IBalanceChange[] dbBalanceChange_GetAll() throws M_DataAccessException
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
				currentBalanceChange.setTransactionOverseas(Boolean.parseBoolean(dataSegments[8]));
				currentBalanceChange.setTransactionSrcBankAccID(dataSegments[9]);
				currentBalanceChange.setTransactionBankAccInitialAmount(Double.parseDouble(dataSegments[10]));
				currentBalanceChange.setTransactionBankAccFinalAmount(Double.parseDouble(dataSegments[11]));

				currentBalanceChange.setExecutedOnAtm(Boolean.parseBoolean(dataSegments[12]));
				currentBalanceChange.setAtmID(dataSegments[13]);
				currentBalanceChange.setExecutedOnPurchase(Boolean.parseBoolean(dataSegments[14]));

				// add current balance change record to arraylist tempBalanceChanges
				tempBalanceChanges.add((M_IBalanceChange) currentBalanceChange);
				line = br.readLine();
			}
		}
		catch(Exception e)
		{
			throw new M_DataAccessException(e);
		}
		finally
		{
			try{
				br.close();
			}
			catch(Exception e){
				throw new M_DataAccessException(e);
			}
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
	
	//POST Methods (Update)
	//	POST function for updating 1 record of M_IBalanceChange
	public short dbBalanceChange_Update(M_IBalanceChange inputBalChange) throws M_DataAccessException
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
			newBalanceChange += (
				inputBalChange.getCreatedBy() + "|" +                       						//00: createdBy - String
				inputBalChange.getCreatedAt() + "|" +                       						//01: createdAt - long
				inputBalChange.getUpdatedBy() + "|" +                       						//02: updatedBy - String
				inputBalChange.getUpdatedBy() + "|" +                       						//03: updatedAt - long
	
				inputBalChange.getTransactionID() + "|" +                   						//04: transactionID - String
				inputBalChange.getTransactionType() + "|" +                 						//05: transactionType - short
				inputBalChange.getTransactionAmount() + "|" +               						//06: transactionAmount - double
				inputBalChange.getTransactionDescription() + "|" +          						//07: transactionDescription - String
				(inputBalChange.isTransactionOverseas() ? "1": "0") + "|" + 						//08: transactionOverseas - boolean
				inputBalChange.getTransactionSrcBankAccID() + "|" +         						//09: transactionBankAccID - String
				String.format("%.2f", inputBalChange.getTransactionBankAccInitialAmount()) + "|" +  //10: transactionBankAccInitialAmount - double
				String.format("%.2f", inputBalChange.getTransactionBankAccFinalAmount()) + "|" +  	//11: transactionBankAccFinalAmount - double
	
				(inputBalChange.isExecutedOnAtm() ? "1": "0") + "|" + 								//12: executedOnAtm - boolean
				inputBalChange.getAtmID() + "|" +													//13: AtmID - String
				(inputBalChange.isExecutedOnPurchase() ? "1": "0") + "|" +							//14: executedOnPurchase - boolean
	
				""																					//15: transactionTargetAccID - String 
			);

			writer = new PrintWriter(MV_Global.dbBalanceChanges);
			writer.print(newBalanceChange);
		}
		catch(Exception e)
		{
			return -1; // not sure if this is correct
		}
		finally{
			try{
				br.close();
				writer.flush();
				writer.close();
			}
			catch(Exception e){
				throw new M_DataAccessException(e);
			}
		}
		return 0;
	}
	//	POST function for updating 1 record of M_IBalanceTransfer
	public short dbBalanceTransfer_Update(M_IBalanceTransfer inputBalTransfer) throws M_DataAccessException
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

			newBalanceTransfer += (
				inputBalTransfer.getCreatedBy() + "|" +                       							//00: createdBy - String
				inputBalTransfer.getCreatedAt() + "|" +                       							//01: createdAt - long
				inputBalTransfer.getUpdatedBy() + "|" +                       							//02: updatedBy - String
				inputBalTransfer.getUpdatedBy() + "|" +                       							//03: updatedAt - long
			
				inputBalTransfer.getTransactionID() + "|" +                   							//04: transactionID - String
				inputBalTransfer.getTransactionType() + "|" +                 							//05: transactionType - short
				inputBalTransfer.getTransactionAmount() + "|" +               							//06: transactionAmount - double
				inputBalTransfer.getTransactionDescription() + "|" +          							//07: transactionDescription - String
				(inputBalTransfer.isTransactionOverseas() ? "1": "0") + "|" + 							//08: transactionOverseas - boolean
				inputBalTransfer.getTransactionSrcBankAccID() + "|" +         							//09: transactionBankAccID - String
				String.format("%.2f", inputBalTransfer.getTransactionBankAccInitialAmount()) + "|" + 	//10: transactionBankAccInitialAmount - double
				String.format("%.2f", inputBalTransfer.getTransactionBankAccFinalAmount()) + "|" +  	//11: transactionBankAccFinalAmount - double
	
				"|" +																					//12: executedOnAtm - boolean
				"|" +																					//13: AtmID - String
				"|" +																					//14: executedOnPurchase - boolean
	
				inputBalTransfer.getTransactionTargetBankAccID()										//15: transactionTargetAccID - String 
			);

			writer = new PrintWriter(MV_Global.dbBalanceTransfers);
			writer.print(newBalanceTransfer);
		}
		catch(Exception e)
		{
			return -1; // not sure if this is correct
		}
        finally{
			try{
				br.close();
				writer.flush();
				writer.close();
			}
			catch(Exception e){
				throw new M_DataAccessException(e);
			}
		}
		return 0;
	}
	
	//PUT Methods (Create)
	//	PUT function for creating 1 record of M_IBalanceChange
	public short dbBalanceChange_Create(M_IBalanceChange inputBalChange) throws M_DataAccessException
	{
		//Variable initialization
		BufferedWriter bw = null;

		//Prepare inputBalChange entry string
		String newEntry = ("\n" + 
			inputBalChange.getCreatedBy() + "|" +                       						//00: createdBy - String
			inputBalChange.getCreatedAt() + "|" +                       						//01: createdAt - long
			inputBalChange.getUpdatedBy() + "|" +                       						//02: updatedBy - String
			inputBalChange.getUpdatedAt() + "|" +                       						//03: updatedAt - long

			inputBalChange.getTransactionID() + "|" +                   						//04: transactionID - String
			inputBalChange.getTransactionType() + "|" +                 						//05: transactionType - short
			inputBalChange.getTransactionAmount() + "|" +               						//06: transactionAmount - double
			inputBalChange.getTransactionDescription() + "|" +          						//07: transactionDescription - String
			(inputBalChange.isTransactionOverseas() ? "1": "0") + "|" + 						//08: transactionOverseas - boolean
			inputBalChange.getTransactionSrcBankAccID() + "|" +         						//09: transactionBankAccID - String
			String.format("%.2f", inputBalChange.getTransactionBankAccInitialAmount()) + "|" +  //10: transactionBankAccInitialAmount - double
			String.format("%.2f", inputBalChange.getTransactionBankAccFinalAmount()) + "|" +  	//11: transactionBankAccFinalAmount - double

			(inputBalChange.isExecutedOnAtm() ? "1": "0") + "|" + 								//12: executedOnAtm - boolean
			inputBalChange.getAtmID() + "|" +													//13: AtmID - String
			(inputBalChange.isExecutedOnPurchase() ? "1": "0") + "|" +							//14: executedOnPurchase - boolean

			""																					//15: transactionTargetAccID - String 
		);

		try
		{
			//Open buffered writer to dbBalanceChanges
			File dbFile = new File(MV_Global.dbBalanceChanges);
			bw = new BufferedWriter(new FileWriter(dbFile.getAbsoluteFile(), true));
			bw.write(newEntry);
		}
		catch(Exception e)
		{
			return -1;
		}
        finally{
			try{
				bw.close();
			}
			catch(Exception e){
				throw new M_DataAccessException(e);
			}
		}
		return 0;
	}
	//	PUT function for creating 1 record of M_IBalanceTransfer
	public short dbBalanceTransfer_Create(M_IBalanceTransfer inputBalTransfer) throws M_DataAccessException
	{
		//Variable initialization
		BufferedWriter bw = null;

		//Prepare inputBalChange entry string
		String newEntry = ("\n" + 
			inputBalTransfer.getCreatedBy() + "|" +                       						//00: createdBy - String
			inputBalTransfer.getCreatedAt() + "|" +                       						//01: createdAt - long
			inputBalTransfer.getUpdatedBy() + "|" +                       						//02: updatedBy - String
			inputBalTransfer.getUpdatedAt() + "|" +                       						//03: updatedAt - long

			inputBalTransfer.getTransactionID() + "|" +                   						//04: transactionID - String
			inputBalTransfer.getTransactionType() + "|" +                 						//05: transactionType - short
			inputBalTransfer.getTransactionAmount() + "|" +               						//06: transactionAmount - double
			inputBalTransfer.getTransactionDescription() + "|" +          						//07: transactionDescription - String
			(inputBalTransfer.isTransactionOverseas() ? "1": "0") + "|" + 						//08: transactionOverseas - boolean
			inputBalTransfer.getTransactionSrcBankAccID() + "|" +         						//09: transactionBankAccID - String
			String.format("%.2f", inputBalTransfer.getTransactionBankAccInitialAmount()) + "|" +//10: transactionBankAccInitialAmount - double
			String.format("%.2f", inputBalTransfer.getTransactionBankAccFinalAmount()) + "|" + 	//11: transactionBankAccFinalAmount - double

			"|" + 																				//12: executedOnAtm - boolean
			"|" +																				//13: AtmID - String
			"|" +																				//14: executedOnPurchase - boolean

			inputBalTransfer.getTransactionTargetBankAccID()									//15: transactionTargetAccID - String 
		);

		try
		{
			//Open buffered writer to dbBalanceChanges
			File dbFile = new File(MV_Global.dbBalanceChanges);
			bw = new BufferedWriter(new FileWriter(dbFile.getAbsoluteFile(), true));
			bw.write(newEntry);
		}
		catch(Exception e)
		{
			return -1;
		}
        finally{
			try{
				bw.close();
			}
			catch(Exception e){
				throw new M_DataAccessException(e);
			}
		}
		return 0;
	}

	//DEL Methods
	//	Feature not required in solution; not implemented
}