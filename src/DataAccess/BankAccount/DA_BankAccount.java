package DataAccess.BankAccount;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import Model.BankAccount.M_BankAccount;
import Model.BankAccount.M_CorporateBankAcc;
import Model.BankAccount.M_IBankAccount;
import Model.BankAccount.M_ICorporateBankAcc;
import Model.BankAccount.M_IJointBankAcc;
import Model.BankAccount.M_JointBankAcc;
import ModelView.MV_Global;

public class DA_BankAccount {
	//M_BankAccount
	//	00: createdBy - String
	//	01: createdAt - long
	//	02: updatedBy - String
	//	03: updatedAt - long
	//	04: bankAccID - String
	//	05: bankAccHolderID - String
	//	06: bankAccType - short
	//		Case 00: Normal bank acc
	//		Case 01: Joint bank acc
	//		Case 02: Corperate bank acc
	//	07: bankAccDescription - String
	//	08: bankAccStatus - short
	//		Case 00: Normal
	//		Case 01: Closed
	//	09: bankAccBalance - double
	//	10: bankAccTransactionLimit - double
	//	11: bankAccMinBalance - double
	//M_JointBankAcc
	//	12: bankAccSubHolderIDs - String[]
	//M_CorperateBankAcc
	//	13: bankAccTransactOnlyIDs - String[]

	//GET Methods
	private M_IBankAccount dBankAccounts_GetOne(int inputCase, String input) throws Exception{
		String line;
		String[] dataSegments;
		BufferedReader br = null;
		M_IBankAccount targetAccount = null;
		boolean targetFound = false;

		try{
			br = new BufferedReader(new FileReader(MV_Global.dbBankAccounts));
			//Skip first line; header line
			br.readLine();

			line = br.readLine();
			while(line != null){
				dataSegments = line.split("\\|");
				
				switch(inputCase){
					//Search by bankAccID
					case 1:
						targetFound = (dataSegments[4].equals(input));
						break;
				}

				if(targetFound){
					//Data mapping
					M_BankAccount currentAccount = new M_BankAccount();

					currentAccount.setCreatedBy(dataSegments[0]);
					currentAccount.setCreatedAt(Long.parseLong(dataSegments[1]));
					currentAccount.setUpdatedBy(dataSegments[2]);
					currentAccount.setUpdatedAt(Long.parseLong(dataSegments[3]));

					currentAccount.setBankAccID(dataSegments[4]);
					currentAccount.setBankAccHolderID(dataSegments[5]);
					currentAccount.setBankAccType(Short.parseShort(dataSegments[6]));
					currentAccount.setBankAccDescription(dataSegments[7]);
					currentAccount.setBankAccStatus(Short.parseShort(dataSegments[8]));
					currentAccount.setBankAccBalance(Double.parseDouble(dataSegments[9]));
					currentAccount.setBankAccTransactionLimit(Double.parseDouble(dataSegments[10]));
					currentAccount.setBankAccMinBalance(Double.parseDouble(dataSegments[11]));

					targetAccount = (M_IBankAccount)currentAccount;
					break;
				}
				line = br.readLine();
			}
		}
		finally{
			br.close();
		}
		return targetAccount;
	}
	public M_IBankAccount dBankAccounts_GetByID(String bankAccID) throws Exception{
		return dBankAccounts_GetOne(1, bankAccID);
	}

	private M_ICorporateBankAcc dBankAccounts_GetOneCorporate(int inputCase, String input) throws Exception{
		String line;
		String[] dataSegments;
		BufferedReader br = null;
		M_ICorporateBankAcc targetAccount = null;
		boolean targetFound = false;

		try{
			br = new BufferedReader(new FileReader(MV_Global.dbBankAccounts));
			//Skip first line; header line
			br.readLine();

			line = br.readLine();
			while(line != null){
				dataSegments = line.split("\\|");
				
				switch(inputCase){
					//Search by bankAccID
					case 1:
						targetFound = (dataSegments[4].equals(input));
						break;
				}

				if(targetFound){
					//Data mapping
					M_CorporateBankAcc currentAccount = new M_CorporateBankAcc();

					currentAccount.setCreatedBy(dataSegments[0]);
					currentAccount.setCreatedAt(Long.parseLong(dataSegments[1]));
					currentAccount.setUpdatedBy(dataSegments[2]);
					currentAccount.setUpdatedAt(Long.parseLong(dataSegments[3]));

					currentAccount.setBankAccID(dataSegments[4]);
					currentAccount.setBankAccHolderID(dataSegments[5]);
					currentAccount.setBankAccType(Short.parseShort(dataSegments[6]));
					currentAccount.setBankAccDescription(dataSegments[7]);
					currentAccount.setBankAccStatus(Short.parseShort(dataSegments[8]));
					currentAccount.setBankAccBalance(Double.parseDouble(dataSegments[9]));
					currentAccount.setBankAccTransactionLimit(Double.parseDouble(dataSegments[10]));
					currentAccount.setBankAccMinBalance(Double.parseDouble(dataSegments[11]));

					currentAccount.setBankAccSubHolderIDs(dataSegments[12].split("&"));
					currentAccount.setBankAccTransactOnlyIDs(dataSegments[13].split("&"));

					targetAccount = (M_ICorporateBankAcc)currentAccount;
					break;
				}
				line = br.readLine();
			}
		}
		finally{
			br.close();
		}
		return targetAccount;
	}
	public M_ICorporateBankAcc dBankAccounts_GetByIDCorporate(String bankAccID) throws Exception{
		return dBankAccounts_GetOneCorporate(1, bankAccID);
	}

	private M_IJointBankAcc dBankAccounts_GetOneJoint(int inputCase, String input) throws Exception{
		String line;
		String[] dataSegments;
		BufferedReader br = null;
		M_IJointBankAcc targetAccount = null;
		boolean targetFound = false;

		try{
			br = new BufferedReader(new FileReader(MV_Global.dbBankAccounts));
			//Skip first line; header line
			br.readLine();

			line = br.readLine();
			while(line != null){
				dataSegments = line.split("\\|");
				
				switch(inputCase){
					//Search by bankAccID
					case 1:
						targetFound = (dataSegments[4].equals(input));
						break;
				}

				if(targetFound){
					//Data mapping
					M_JointBankAcc currentAccount = new M_JointBankAcc();

					currentAccount.setCreatedBy(dataSegments[0]);
					currentAccount.setCreatedAt(Long.parseLong(dataSegments[1]));
					currentAccount.setUpdatedBy(dataSegments[2]);
					currentAccount.setUpdatedAt(Long.parseLong(dataSegments[3]));

					currentAccount.setBankAccID(dataSegments[4]);
					currentAccount.setBankAccHolderID(dataSegments[5]);
					currentAccount.setBankAccType(Short.parseShort(dataSegments[6]));
					currentAccount.setBankAccDescription(dataSegments[7]);
					currentAccount.setBankAccStatus(Short.parseShort(dataSegments[8]));
					currentAccount.setBankAccBalance(Double.parseDouble(dataSegments[9]));
					currentAccount.setBankAccTransactionLimit(Double.parseDouble(dataSegments[10]));
					currentAccount.setBankAccMinBalance(Double.parseDouble(dataSegments[11]));

					currentAccount.setBankAccSubHolderID(dataSegments[12]);

					targetAccount = (M_IJointBankAcc)currentAccount;
					break;
				}
				line = br.readLine();
			}
		}
		finally{
			br.close();
		}
		return targetAccount;
	}
	public M_IJointBankAcc dBankAccounts_GetByIDJoint(String bankAccID) throws Exception{
		return dBankAccounts_GetOneJoint(1, bankAccID);
	}

	//POST Methods (Update)
	// Update function for M_IBankAccount
	public short dBankAccounts_Update(M_IBankAccount inputBankAcc) throws Exception
	{
		//Variable initialization
		BufferedWriter bw = null; BufferedReader br = null;
		String[] dataSegments = {};
		String line, bankAccSubHolderIDs, bankAccTransactOnlyIDs;

		try{
			br = new BufferedReader(new FileReader(MV_Global.dbBankAccounts));
			//Skip first line; header line
			br.readLine();

			line = br.readLine();
			while(line != null){
				dataSegments = line.split("\\|");
				if(dataSegments[4].equals(inputBankAcc.getBankAccID())){
					break;
				}
				line = br.readLine();
			}
		}
		finally{
			br.close();
		}

		try{
			bankAccSubHolderIDs = dataSegments[12];
		}
		catch(Exception e){
			bankAccSubHolderIDs = "";
		}
		try{
			bankAccTransactOnlyIDs = dataSegments[13];
		}
		catch(Exception e){
			bankAccTransactOnlyIDs = "";
		}
		
		//Prepare inputBankAcc entry string
		String updatedEntry = (
			inputBankAcc.getCreatedBy() + "|" +					//00: createdBy - String
			inputBankAcc.getCreatedAt() + "|" +					//01: createdAt - long
			inputBankAcc.getUpdatedBy() + "|" +					//02: updatedBy - String
			inputBankAcc.getUpdatedAt() + "|" +					//03: updatedAt - long

			inputBankAcc.getBankAccID() + "|" +         		//04: bankAccID - String
			inputBankAcc.getBankAccHolderID() + "|" +         	//05: bankAccHolderID - String
			inputBankAcc.getBankAccType() + "|" +         		//06: bankAccType - short
			inputBankAcc.getBankAccDescription() + "|" +		//07: bankAccDescription - String
			inputBankAcc.getBankAccStatus() + "|" +				//08: bankAccStatus - short
			inputBankAcc.getBankAccBalance() + "|" +			//09: bankAccBalance - double
			inputBankAcc.getBankAccTransactionLimit() + "|" +	//10: bankAccTransactionLimit - double
			inputBankAcc.getBankAccMinBalance() + "|" +			//11: bankAccMinBalance - double

			bankAccSubHolderIDs + "|" + 						//12: bankAccSubHolderIDs - String[]
			bankAccTransactOnlyIDs								//13: bankAccTransactOnlyIDs - String[]
		);

		try
		{
			//Open buffered reader to db file
			br = new BufferedReader(new FileReader(MV_Global.dbBankAccounts));

			//Create temp file is does not exist
			File tempFile = new File(MV_Global.dbTemp);
			tempFile.createNewFile();
			//Open buffered writer to temp file
			bw = new BufferedWriter(new FileWriter(tempFile.getAbsoluteFile(), false));

			//Copy contents of db file to temp file
			line = br.readLine();
			while(line != null){
				dataSegments = line.split("\\|");

				//Ignore record with the same bank acc id of inputBankAcc
				if(!dataSegments[4].equals(inputBankAcc.getBankAccID())){
					bw.write(line + "\n");
				}
				line = br.readLine();
			}

			//Write in updated entry of inputJointAcc
			bw.write(updatedEntry);
		}
		catch(Exception e)
		{
			return -1;
		}
		finally
		{
			bw.close();
			br.close();
		}

		Path from = Paths.get(MV_Global.dbTemp); //convert from File to Path
		Path to = Paths.get(MV_Global.dbBankAccounts); //convert from String to Path
		Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);

		return 0;
	}
	// Update function for M_IJointBankAcc
	public short dBankAccounts_Update(M_IJointBankAcc inputJointAcc) throws Exception
	{
		//Variable initialization
		BufferedWriter bw = null; BufferedReader br = null;
		String[] dataSegments;
		String line;

		//Prepare inputJointAcc entry string
		String updatedEntry = (
			inputJointAcc.getCreatedBy() + "|" +					//00: createdBy - String
			inputJointAcc.getCreatedAt() + "|" +					//01: createdAt - long
			inputJointAcc.getUpdatedBy() + "|" +					//02: updatedBy - String
			inputJointAcc.getUpdatedAt() + "|" +					//03: updatedAt - long

			inputJointAcc.getBankAccID() + "|" +         		//04: bankAccID - String
			inputJointAcc.getBankAccHolderID() + "|" +         	//05: bankAccHolderID - String
			inputJointAcc.getBankAccType() + "|" +         		//06: bankAccType - short
			inputJointAcc.getBankAccDescription() + "|" +		//07: bankAccDescription - String
			inputJointAcc.getBankAccStatus() + "|" +				//08: bankAccStatus - short
			inputJointAcc.getBankAccBalance() + "|" +			//09: bankAccBalance - double
			inputJointAcc.getBankAccTransactionLimit() + "|" +	//10: bankAccTransactionLimit - double
			inputJointAcc.getBankAccMinBalance() + "|" +	

			inputJointAcc.getBankAccSubHolderID() + "|" + 		//12: bankAccSubHolderIDs - String[]
			""													//13: bankAccTransactOnlyIDs - String[]
		);

		try
		{
			//Open buffered reader to db file
			br = new BufferedReader(new FileReader(MV_Global.dbBankAccounts));

			//Create temp file is does not exist
			File tempFile = new File(MV_Global.dbTemp);
			tempFile.createNewFile();
			//Open buffered writer to temp file
			bw = new BufferedWriter(new FileWriter(tempFile.getAbsoluteFile(), false));

			//Copy contents of db file to temp file
			line = br.readLine();
			while(line != null){
				dataSegments = line.split("\\|");

				//Ignore record with the same bank acc id of inputJointAcc
				if(!dataSegments[4].equals(inputJointAcc.getBankAccID())){
					bw.write(line + "\n");
				}
				line = br.readLine();
			}

			//Write in updated entry of inputJointAcc
			bw.write(updatedEntry);
		}
		catch(Exception e)
		{
			return -1;
		}
		finally
		{
			bw.close();
			br.close();
		}
		return 0;
	}
	public short dBankAccounts_Update(M_ICorporateBankAcc inputCorporateAcc) throws Exception
	{
		//Variable initialization
		BufferedWriter bw = null; BufferedReader br = null;
		String[] dataSegments;
		String line;

		//Prepare inputCorporateAcc entry string
		String updatedEntry = (
			inputCorporateAcc.getCreatedBy() + "|" +					//00: createdBy - String
			inputCorporateAcc.getCreatedAt() + "|" +					//01: createdAt - long
			inputCorporateAcc.getUpdatedBy() + "|" +					//02: updatedBy - String
			inputCorporateAcc.getUpdatedAt() + "|" +					//03: updatedAt - long

			inputCorporateAcc.getBankAccID() + "|" +         		//04: bankAccID - String
			inputCorporateAcc.getBankAccHolderID() + "|" +         	//05: bankAccHolderID - String
			inputCorporateAcc.getBankAccType() + "|" +         		//06: bankAccType - short
			inputCorporateAcc.getBankAccDescription() + "|" +		//07: bankAccDescription - String
			inputCorporateAcc.getBankAccStatus() + "|" +				//08: bankAccStatus - short
			inputCorporateAcc.getBankAccBalance() + "|" +			//09: bankAccBalance - double
			inputCorporateAcc.getBankAccTransactionLimit() + "|" +	//10: bankAccTransactionLimit - double
			inputCorporateAcc.getBankAccMinBalance() + "|" +	

			inputCorporateAcc.getBankAccSubHolderIDs() + "|" + 		//12: bankAccSubHolderIDs - String[]
			inputCorporateAcc.getBankAccTransactOnlyIDs()			//13: bankAccTransactOnlyIDs - String[]
		);

		try
		{
			//Open buffered reader to db file
			br = new BufferedReader(new FileReader(MV_Global.dbBankAccounts));

			//Create temp file is does not exist
			File tempFile = new File(MV_Global.dbTemp);
			tempFile.createNewFile();
			//Open buffered writer to temp file
			bw = new BufferedWriter(new FileWriter(tempFile.getAbsoluteFile(), false));

			//Copy contents of db file to temp file
			line = br.readLine();
			while(line != null){
				dataSegments = line.split("\\|");

				//Ignore record with the same bank acc id of inputCorporateAcc
				if(!dataSegments[4].equals(inputCorporateAcc.getBankAccID())){
					bw.write(line + "\n");
				}
				line = br.readLine();
			}

			//Write in updated entry of inputJointAcc
			bw.write(updatedEntry);
		}
		catch(Exception e)
		{
			return -1;
		}
		finally
		{
			bw.close();
			br.close();
		}
		return 0;
	}


	//DEL Methods
	//	Feature not required in solution

	//PUT Methods (Create)
	//	Feature not required in solution
}