package View;

import ModelView.MV_Global;

public class V_ViewManager{
    public static boolean redirect(String page){
        clearPage();
        try{
            switch(page.toLowerCase()){
                case "back":
                    MV_Global.pageDir.pop();
                    return redirect(MV_Global.pageDir.peek());

                case "login":
                    MV_Global.pageDir.push("login");
                    new View.Login.V_Login().run();
                    return true;

                case "useraccindex":
                    MV_Global.pageDir.push("useraccindex");
                    new View.UserAccount.V_UserAccIndex().run();
                    return true;

                case "bankaccindex":
                    MV_Global.pageDir.push("bankaccindex");
                    return new View.BankAccount.V_BankAccIndex().run();

                case "logout":
                    while(!MV_Global.pageDir.empty())
                        MV_Global.pageDir.pop();
                    new View.Login.V_Logout().run();
                    return true;
                    
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
