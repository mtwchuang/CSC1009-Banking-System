package ModelView.UserAccount;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

import Model.UserAccount.M_IUserAccount;
import DataAccess.UserAccount.DA_UserAccount;

public class MV_UserAccount {

    public short VLogin_checkAcct(String userName, int userPassword) throws Exception
    {
        //Status Codes:
        //  0: Ok
        //  1: Invalid username
        //  2: Invalid password
        try
        {
            //Instantiate class and call method dbUserAccounts_GetByUserName() from library DataAccess.UserAccount.UserAccount()
            DA_UserAccount userAccountDB = new DA_UserAccount();
            M_IUserAccount testAcct = userAccountDB.dbUserAccounts_GetByUserName(userName);

            //If testAcct is null, account does not exists, return error 1
            if(testAcct == null) return 1;

            //Acquire hashed format of input password
            byte[] hashedPass = passwordHashing(userPassword);

            //Check if input password tallies with db record
            if(Arrays.equals(testAcct.getUserPassword(), hashedPass)) return 2;
        }
        catch(Exception e)
        {
            //Encountered fatal program error
            throw e;
        }

        return 0;
    }
    
    //Double layered hashing
    private byte[] passwordHashing(int userPassword)
    {
        byte[] hashedPassword = null;
        String targetString = userPassword + "";

        try
        {
            //Convert int password to string and hashes it in "SHA-256" format
            MessageDigest sha265Hash = MessageDigest.getInstance("SHA-256");
            hashedPassword = sha265Hash.digest(targetString.getBytes(StandardCharsets.UTF_8));
    
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