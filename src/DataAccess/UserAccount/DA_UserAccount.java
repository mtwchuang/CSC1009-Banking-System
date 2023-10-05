package DataAccess.UserAccount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import Model.Global.M_DataAccessException;
import Model.UserAccount.M_IUserAccount;
import Model.UserAccount.M_UserAccount;
import Model.UserAccount.M_UserAccountLogin;
import ModelView.Global.MV_Global;

public class DA_UserAccount {
	/* Model:
	M_UserAccount [StandAlone]
		00: createdBy - String
		01: createdAt - long
		02: updatedBy - String
		03: updatedAt - long
		04: userID - String
		05: userType - short
			Case 01: Normal User
			Case 02: Staff User [Feature not required in solution; not implemented]
			Default: Admin
		06: userName - String
		07: userFirstName - String
		08: userLastName - String
		09: userAddress - String
		10: userPhoneNumber - long
		11: userBankAccounts - String[]
	*/

	//GET Methods for M_IUserAccount
	//	Main GET function for acquiring 1 record of M_IUserAccount
	private M_IUserAccount dbUserAccounts_GetOne(int inputCase, String input) throws M_DataAccessException{
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
		return targetAccount;
	}
	//	GET function for acquiring 1 record of M_IUserAccount by 04: userID
	public M_IUserAccount dbUserAccounts_GetByUserID(String userID) throws M_DataAccessException{
		return dbUserAccounts_GetOne(1, userID);
	}
	//	GET function for acquiring 1 record of M_IUserAccount by 06: userName
	public M_IUserAccount dbUserAccounts_GetByUserName(String userName) throws M_DataAccessException{
		return dbUserAccounts_GetOne(2, userName);
	}
	//	GET function to acquire all records of M_IUserAccount
	public M_IUserAccount[] dbUserAccounts_GetAll() throws M_DataAccessException{
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
		
		M_IUserAccount[] userAccountsArr = new M_IUserAccount[userAccounts.size()];
		for(int i = 0; i < userAccountsArr.length; i++)
			userAccountsArr[i] = userAccounts.get(i);
		return userAccountsArr;
	}

	//GET Methods for M_UserAccountLogin
	//	GET function for acquiring password of 1 record of M_UserAccountLogin
	private byte[] dbUserAccounts_GetUserPassword(String userID) throws M_DataAccessException{
		byte[] password = null;
		ObjectInputStream objectInputStream = null;

		File dbAccountLogins = new File(MV_Global.dbUserAccountLogins);
		String[] accountLogins = dbAccountLogins.list();

		for(String loginEntry : accountLogins){
			if(loginEntry.equals(userID)){
				try{
					FileInputStream fileInputStream = new FileInputStream(
					MV_Global.dbUserAccountLogins + "\\" + loginEntry);
					objectInputStream = new ObjectInputStream(fileInputStream);
						
					M_UserAccountLogin targetLogin = (M_UserAccountLogin) objectInputStream.readObject();
					password = targetLogin.getUserPassword();
				}
				catch(Exception e){
					throw new M_DataAccessException(e);
				}
				finally{
					try{
						objectInputStream.close();
					}
					catch(Exception e){
						throw new M_DataAccessException(e);
					}
				}
				break;
			}
		}
		return password;
	}

	//POST Methods (Update) for M_UserAccountLogin
	//	POST function for updating 1 record of M_UserAccountLogin
	public short dbUserAccounts_UpdateUserPassword(String password){
		try{
			byte[] hashedPassword;
			MessageDigest sha265Hash = MessageDigest.getInstance("SHA-256");
			hashedPassword = sha265Hash.digest(password.getBytes(StandardCharsets.UTF_8));
			MessageDigest md5Hash = MessageDigest.getInstance("MD5");
			hashedPassword = md5Hash.digest(hashedPassword);
			
			M_UserAccountLogin login = new M_UserAccountLogin(hashedPassword);
	
			FileOutputStream fileOutputStream = new FileOutputStream(MV_Global.dbUserAccountLogins + "\\" + MV_Global.sessionUserAcc.getUserID());
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				
			objectOutputStream.writeObject(login);
			objectOutputStream.flush();
			objectOutputStream.close();
		}
		catch(Exception e){
			return 1;
		}
		return 0;
	}

	//PUT Methods (Create)
	//	Feature not required in solution; not implemented
	
	//DEL Methods
	//	Feature not required in solution; not implemented
}