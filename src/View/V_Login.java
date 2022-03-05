package View;

import java.util.Scanner;

import ModelView.UserAccount.MV_UserAccount;

public class V_Login 
{
    Scanner input = new Scanner(System.in);
    public void Authentication() throws Exception
    {
        boolean loginFlag = true; 
        while(loginFlag==true)
        {
            loginFlag = false;
            // take in username and password
            System.out.print("Enter in login username: ");
            String loginUser = input.nextLine();
            // ATM password integers
            System.out.print("Enter in login password: ");
            String loginPass = input.nextLine();
            int intCheck = 0;
            // statement to check if parameter is are numerics
            try
            {
                intCheck = Integer.parseInt(loginPass);
            }
            catch(Exception e)
            {
                // Function to clear page
                System.out.print("\033[H\033[2J");
                System.out.println("Error, String detected, only int allowed");
                loginFlag = true;
            }

            // check against previously defined credentials???
            // call out logical function from ModelView UserAccount_ModelView which will return integers for outputs
            short authStatus = new MV_UserAccount().ViewLogin_login(loginUser, intCheck);
            // int authStatus = 1; //temporary for now
            switch(authStatus)
            {
                case(0):
                {
                    System.out.println("Welcome back to your account");
                    // call out to new overview method?
                }
                case(1):
                {
                    System.out.println("Wrong username or password entered");
                    loginFlag= true;
                    break;
                }
                case(2):
                {
                    System.out.println("Wrong username or password entered");
                    loginFlag = true;
                    break;
                }
            }
            // check if user still wants to continue or break
            // need implementations
        }
    }
        
    public void UserActions()
    {
        System.out.print("hi");
    }
}
