package View;

public class V_ViewManager{
    public static boolean redirect(String page, String... params){
        clearPage();
        try{
            switch(page.toLowerCase()){
                //Login page
                case "login":
                    new View.Login.V_Login().run();
                    return true;
                //User account page
                case "useraccindex":
                    new View.UserAccount.V_UserAccIndex().run();
                    return true;
                //Bank account page
                case "bankaccindex":
                    return new View.BankAccount.V_BankAccIndex().run();
                //Withdraw page
                case "withdraw":
                    new View.BankAccount.V_Withdraw().run(params);
                    return false;
                //Logout
                case "logout":
                    new View.Login.V_Logout().run();
                    return true;
                //ChangeBalance
                case "changebal":
                    new View.AdminActions.V_ChangeBalance().run();
                    return false;
                //ViewBalance
                case "viewbal":
                    return new View.BankAccount.V_ViewBalance().run();
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
