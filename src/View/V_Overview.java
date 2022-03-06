package View;

import java.util.Scanner;

public class V_Overview 
{
    Scanner input = new Scanner(System.in);
    //Action selections
    public void userActions()
    {
        System.out.print("----------------------------Overview----------------------------\n");
        System.out.println("\n1. Check Account Balance\n2. Check Account Information");
        System.out.println("3. Money Transfer\n4. Deposit Money\n5. Withdraw Money\n6. Edit Settings");
        // get user input for user actions
        System.out.print("Enter choice(number): ");
        int actionChoice = input.nextInt();
        switch(actionChoice)
        {
            case(1):
            {
                // call method for balance checking
            }
            case(2):
            {
                // call method for account info checking
            }
            case(3):
            {
                // call money transfer
            }
            case(4):
            {
                // call deposit money
            }
            case(5):
            {
                // call withdraw money
            }
            case(6):
            {
                // call edit settings
            }
        }
    }
}
