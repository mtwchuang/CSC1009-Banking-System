package View;

public class V_ViewManager{
    public static boolean redirect(String page, String... params){
        clearPage();
        try{
            switch(page.toLowerCase()){
                //Main Operating Pages
                case "login": //Login page
                    new View.Login.V_Login().run();
                    return true;
                case "logout": //Logout
                    new View.Login.V_Logout().run();
                    return true;
                case "useraccindex": //User account page
                    new View.UserAccount.V_UserAccIndex().run();
                    return true;
                case "bankaccindex": //Bank account page
                    return new View.BankAccount.V_BankAccIndex().run();

                //Business Process Pages
                case "viewbankaccdetails": //ViewBalance page
                    return new View.BankAccount.V_ViewDetails().run();
                case "withdraw": //Withdraw page
                    new View.BankAccount.V_Withdraw().run(params);
                    return false;
                case "deposit": //Deposit page
                    new View.BankAccount.V_Deposit().run();
                    return false;

                //Admin Function Pages
                case "admin-atmdetails":
                    new View.AdminActions.V_AtmDetails().run();
                    return false;
                case "admin-changebal": //Change balance
                    new View.AdminActions.V_ChangeBalance().run();
                    return false;

                default:
                    throw new Exception("Navigation invalid page");
            }
        }
        catch(Exception e){
            return new View.V_Error().run(e);
        }
    }

    public static void clearPage(){
        //Clear screen
        System.out.print("\033[H\033[2J");
    }
}
