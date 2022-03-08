package View;

import ModelView.MV_Global;

public class V_ViewManager{
    public static void redirect(String page){
        clearPage();
        try{
            switch(page.toLowerCase()){
                case "login":
                    MV_Global.pageDir.push("login");
                    new View.Login.V_Login().run();
                    break;
                case "useraccindex":
                    MV_Global.pageDir.push("useraccindex");
                    new View.Index.V_UserAccIndex().run();
                    break;
                case "bankaccindex":
                    MV_Global.pageDir.push("bankaccindex");
                    new View.Index.V_BankAccIndex().run();
                    break;
                default:
                    throw new Exception("Navigation invalid page");
            }
        }
        catch(Exception e){
            new View.V_Error().run(e);
        }
    }

    private static void clearPage(){
        //Clear screen
        System.out.print("\033[H\033[2J");
    }
}
