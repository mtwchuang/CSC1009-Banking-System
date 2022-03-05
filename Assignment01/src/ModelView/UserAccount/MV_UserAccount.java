package ModelView.UserAccount;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
// import DataAccess.UserAccount.UserAccount;
public class MV_UserAccount {

    public short ViewLogin_login(String userName, int userPassword) throws Exception
    {
        //Status Codes:
        //  0: Ok
        //  1: Invalid username
        //  2: Invalid password
        try
        {
            // instantiate and call method dbUserAccounts_GetByUserName() from library DataAccess.UserAccount.UserAccount()
            Model.UserAccount.M_IUserAccount testAcct =  
            new DataAccess.UserAccount.DA_UserAccount().dbUserAccounts_GetByUserName(userName);
            if(testAcct==null) return 1;

            // call local password hashing method
            String hashedPass = passwordHashing(userPassword);

            // check if password is not equal to stored password
            if(testAcct.getUserPassword()!=hashedPass) return 2;
        }
        catch(Exception e)
        {
            //Fatal Program Error
            throw e;
        }

        return 0;
    }
    private String passwordHashing(int userPassword)
    {
        String hashedPassword="";
        try
        {
            String targetString = userPassword+"";
            MessageDigest sha265Hash = MessageDigest.getInstance("SHA-256");
    
            byte[] hashLayer01 = sha265Hash.digest(targetString.getBytes(StandardCharsets.UTF_8));
    
            MessageDigest md5Hash = MessageDigest.getInstance("MD5");
            byte[] hashLayer02 = md5Hash.digest(hashLayer01);
            hashedPassword = new String(hashLayer02,StandardCharsets.UTF_8);
        }
        catch(Exception e)
        {
            System.out.println("Some error has happened in Hashing");
        }
        return hashedPassword;
    }
}