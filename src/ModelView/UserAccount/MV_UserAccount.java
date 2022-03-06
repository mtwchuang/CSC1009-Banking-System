package ModelView.UserAccount;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

import Model.UserAccount.M_IUserAccount;
import ModelView.Global;
import DataAccess.UserAccount.DA_UserAccount;

public class MV_UserAccount {

    public short VLogin_checkAcc(String userName, String userPassword) throws Exception
    {
        //Status Codes:
        //  0: Ok
        //  1: Invalid username
        //  2: Invalid password
        try
        {
            DA_UserAccount userAccDA = new DA_UserAccount();

            //Retrive UserAccount with corresponding userName
            M_IUserAccount targetAcc = userAccDA.dbUserAccounts_GetByUserName(userName);

            //Null detected; userName does not exist, return status code 1
            if(targetAcc == null) return 1;

            //Acquire hashed format of userPassword
            byte[] hashedPass = passwordHashing(userPassword);

            //Detect if userPassword does not tally with DB side, return status code 2
            if(!Arrays.equals(targetAcc.getUserPassword(), hashedPass)) return 2;

            //Successful login, put targetAcc in session storage
            Global.sessionUser = targetAcc;
        }
        catch(Exception e)
        {
            //Encountered fatal program error
            throw e;
        }

        //Successful login, return status code 0
        return 0;
    }
    
    //Double layered hashing
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