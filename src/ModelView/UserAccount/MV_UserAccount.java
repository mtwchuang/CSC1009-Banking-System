package ModelView.UserAccount;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

import Model.UserAccount.M_IUserAccount;
import ModelView.MV_Global;
import DataAccess.UserAccount.DA_UserAccount;

public class MV_UserAccount {

    //View Layer Access Functions
    //	V_Login: Logic for login function
	public short VLogin_checkAcc(String userName, String userPassword) throws Exception{
		//Status csodes:
		//  0: Ok
		//  1: Invalid username
		//  2: Invalid password
		try
		{
			//Variable declaration
			DA_UserAccount userAccDA = new DA_UserAccount();

			//Retrive UserAccount with corresponding userName
			M_IUserAccount targetAcc = userAccDA.dbUserAccounts_GetByUserName(userName);

			//Null detected; userName does not exist, return status code 1
			if(targetAcc == null) return 1;

			//Acquire hashed format of userPassword
			byte[] hashedPass = passwordHashing(userPassword);

			//Detect if userPassword does not tally with DB side, return status code 2
			if(!Arrays.equals(targetAcc.getUserPassword(), hashedPass)) return 2;

			//Successful login, load targetAcc into session storage
			MV_Global.sessionUserAcc = targetAcc;
		}
		catch(Exception e)
		{
			//Encountered fatal program error
			throw e;
		}

		//Successful login, return status code 0
		return 0;
	}
	//	V_UserAccIndex: Logic for changing of password function
	public short VUserAccIndex_changePassword(String userPassword) throws Exception{
		//Status codes:
		//	0: Ok
		//	1: Password too long
		//	2: Data access layer error
		//	3: Non-0 data access layer code
		
		//If password is too long
		if(userPassword.length() > 100) return 1;

		DA_UserAccount userAccDa = new DA_UserAccount();
		short daStatus;

		//Post to data access layer
		try{
			daStatus = userAccDa.dbUserAccounts_UpdateUserPassword(userPassword);
		}
		catch(Exception e){
			return 2;
		}

		//If data access layer returned non-Ok status code
		if(daStatus != 0) return 3;
		
		return 0;
	}

    //Internal Functions
	//	Logic for hashing password
	private byte[] passwordHashing(String userPassword)
	{
		byte[] hashedPassword = null;
		try
		{
			//Convert int password to string and hashes it in "SHA-256" format
			MessageDigest sha265Hash = MessageDigest.getInstance("SHA-256");
			hashedPassword = sha265Hash.digest(userPassword.getBytes(StandardCharsets.UTF_8));
	
			//Takes byte output and hashes it again in "MD5" format
			MessageDigest md5Hash = MessageDigest.getInstance("MD5");
			hashedPassword = md5Hash.digest(hashedPassword);
		}
		catch(Exception e)
		{
			System.out.println("Some error has happened in Hashing");
		}
		return hashedPassword;
	}
}