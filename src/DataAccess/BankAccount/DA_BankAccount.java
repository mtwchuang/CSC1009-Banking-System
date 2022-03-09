package DataAccess.BankAccount;

import java.io.BufferedReader;
import java.io.FileReader;

import Model.BankAccount.M_BankAccount;
import Model.BankAccount.M_CorporateBankAcc;
import Model.BankAccount.M_IBankAccount;
import Model.BankAccount.M_ICorporateBankAcc;
import Model.BankAccount.M_IJointBankAcc;
import Model.BankAccount.M_JointBankAcc;
import ModelView.MV_Global;

public class DA_BankAccount {
	//M_BankAccount
	//  00: createdBy - String
	//  01: createdAt - long
	//  02: updatedBy - String
	//  03: updatedAt - long
	//  04: bankAccID - String
	//  05: bankAccHolderID - String
	//  06: bankAccType - short
	//	07: bankAccDescription - String
	//  08: bankAccStatus - short
	//  09: bankAccBalance - double
	//  10: bankAccTransactionLimit - double
	//  11: bankAccMinBalance - double

	//  12: bankAccSubHolderIDs - String[]
	//  13: bankAccTransactOnlyIDs - String[]

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
}