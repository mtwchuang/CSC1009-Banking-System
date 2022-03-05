import java.util.Date;

import Model.UserAccount.IUserAccount;
import Model.UserAccount.UserAccount;

import ModelView.Global;

public class Main {
    public static void main(String[] args) throws Exception 
    {
        // clear ,essage
        System.out.print("\033[H\033[2J");
        View.Login v1 = new View.Login();
        v1.Authentication();

        
    }
}
