package DataAccess.UserAccount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import Model.UserAccount.M_IUserAccount;
import Model.UserAccount.M_UserAccount;
import Model.UserAccount.M_UserAccountLogin;
import ModelView.MV_Global;

public class DA_UserAccount {
	//M_UserAccount
	//  00: createdBy - String
	//  01: createdAt - long
	//  02: updatedBy - String
	//  03: updatedAt - long
	//  04: userID - String
	//  05: userType - short
	//  06: userName - String
	//  07: userFirstName - String
	//  08: userLastName - String
	//  09: userAddress - String
	//  10: userPhoneNumber - long
	//	11: userBankAccounts - Collection

	public M_IUserAccount[] dbUserAccounts_GetAll() throws Exception{
		List<M_IUserAccount> userAccounts = new ArrayList<M_IUserAccount>();

		String line;
		String[] dataSegments;
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(MV_Global.dbUserAccounts));
			//Skip first line; header line
			br.readLine();

			line = br.readLine();
			while(line != null){
				dataSegments = line.split("\\|");

				//Data mapping
				M_UserAccount currentAccount = new M_UserAccount();

				currentAccount.setCreatedBy(dataSegments[0]);
				currentAccount.setCreatedAt(Long.parseLong(dataSegments[1]));
				currentAccount.setUpdatedBy(dataSegments[2]);
				currentAccount.setUpdatedAt(Long.parseLong(dataSegments[3]));
				
				currentAccount.setUserID(dataSegments[4]);
				currentAccount.setUserAccType(Short.parseShort(dataSegments[5]));
				currentAccount.setUserName(dataSegments[6]);
				currentAccount.setUserFirstName(dataSegments[7]);
				currentAccount.setUserLastName(dataSegments[8]);
				currentAccount.setUserAddress(dataSegments[9]);
				currentAccount.setUserPhoneNumber(Long.parseLong(dataSegments[10]));

				currentAccount.setUserBankAccounts(dataSegments[11].split("&"));

				currentAccount.setUserPassword(dbUserAccounts_GetUserPassword(dataSegments[4]));

				userAccounts.add((M_IUserAccount)currentAccount);
			}
			line = br.readLine();
		}
		finally{
			br.close();
		}
		
		M_IUserAccount[] userAccountsArr = new M_IUserAccount[userAccounts.size()];
		for(int i = 0; i < userAccountsArr.length; i++)
			userAccountsArr[i] = userAccounts.get(i);
		return userAccountsArr;
	}

	private M_IUserAccount dbUserAccounts_GetOne(int inputCase, String input) throws Exception{
		String line;
		String[] dataSegments;
		BufferedReader br = null;
		M_IUserAccount targetAccount = null;
		boolean targetFound = false;

		try{
			br = new BufferedReader(new FileReader(MV_Global.dbUserAccounts));
			//Skip first line; header line
			br.readLine();

			line = br.readLine();
			while(line != null){
				dataSegments = line.split("\\|");
				
				switch(inputCase){
					//Search by userID
					case 1:
						targetFound = (dataSegments[4].equals(input));
						break;
					//Search by userName
					case 2:
						targetFound = (dataSegments[6].equals(input));
						break;
				}

				if(targetFound){
					//Data mapping
					M_UserAccount currentAccount = new M_UserAccount();

					currentAccount.setCreatedBy(dataSegments[0]);
					currentAccount.setCreatedAt(Long.parseLong(dataSegments[1]));
					currentAccount.setUpdatedBy(dataSegments[2]);
					currentAccount.setUpdatedAt(Long.parseLong(dataSegments[3]));
					
					currentAccount.setUserID(dataSegments[4]);
					currentAccount.setUserAccType(Short.parseShort(dataSegments[5]));
					currentAccount.setUserName(dataSegments[6]);
					currentAccount.setUserFirstName(dataSegments[7]);
					currentAccount.setUserLastName(dataSegments[8]);
					currentAccount.setUserAddress(dataSegments[9]);
					currentAccount.setUserPhoneNumber(Long.parseLong(dataSegments[10]));

					currentAccount.setUserBankAccounts(dataSegments[11].split("&"));

					currentAccount.setUserPassword(dbUserAccounts_GetUserPassword(dataSegments[4]));

					targetAccount = (M_IUserAccount)currentAccount;
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
	public M_IUserAccount dbUserAccounts_GetByUserID(String userID) throws Exception{
		return dbUserAccounts_GetOne(1, userID);
	}
	public M_IUserAccount dbUserAccounts_GetByUserName(String userName) throws Exception{
		return dbUserAccounts_GetOne(2, userName);
	}

	private byte[] dbUserAccounts_GetUserPassword(String userID) throws Exception{
		byte[] password = null;

		File dbAccountLogins = new File(MV_Global.dbUserAccountLogins);
		String[] accountLogins = dbAccountLogins.list();

		for(String loginEntry : accountLogins){
			if(loginEntry.equals(userID)){
				FileInputStream fileInputStream = new FileInputStream(
					MV_Global.dbUserAccountLogins + "\\" + loginEntry);
				ObjectInputStream objectInputStream = new ObjectInputStream(
					fileInputStream);
					
				M_UserAccountLogin targetLogin = (M_UserAccountLogin) objectInputStream.readObject();
				password = targetLogin.getUserPassword();
				objectInputStream.close();
				break;
			}
		}
		return password;
	}
}