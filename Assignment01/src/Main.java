import java.util.Date;

import Model.UserAccount.IUserAccount;
import Model.UserAccount.UserAccount;

import ModelView.Global;

public class Main {
    public static void main(String[] args) throws Exception 
    {
        System.out.println("SessionID: " + Global.sessionUserID.toString());

        System.out.print("\033[H\033[2J");

        IUserAccount temp = new UserAccount();
        System.out.println(
            "newAccID: " + temp.getUserID().toString() + "\n" +
            "createdBy: " + temp.getCreatedBy().toString() + "\n" + 
            "createdAt: " + new Date(temp.getCreatedAt()));

        
    }
}
