package View;
import ModelView.UserAccount.MV_UserAccount;
import java.util.Scanner;
public class V_Login 
{
    Scanner input = new Scanner(System.in);
    public void Authentication() throws Exception
    {
        System.out.println("Welcome user!");
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

            // call out VL_checkAcct() from MV_UserAccount in layer ModelView to check against stored credentials
            short authStatus = new MV_UserAccount().VLogin_checkAcct(loginUser, intCheck);
            switch(authStatus)
            {
                // authentication success
                case(0):
                {
                    System.out.println("Welcome back to your account");
                    // call out to new overview method?
                }
                // wrong username
                case(1):
                {
                    System.out.println("Wrong username or password entered");
                    loginFlag= true;
                    break;
                }
                // wrong password
                case(2):
                {
                    System.out.println("Wrong username or password entered");
                    loginFlag = true;
                    break;
                }
            }
            // ask user if want to continue or break
            boolean choiceLoop = true;
            if(loginFlag==true)
            {
                while(choiceLoop==true)
                {
                    choiceLoop = false;
                    System.out.print("Do you still want to continue? Y/N");
                    String contChoice = input.nextLine();
                    if(contChoice=="N")
                    {
                        loginFlag = false;
                    }
                    else if(contChoice!="Y")
                    {
                        System.out.println("Your entered something besides Y/N. Please try again");
                        choiceLoop=true;
                    }
                }

            }
        }
        System.out.println("Thank you for using our service! Have a nice day!");
    }
}
