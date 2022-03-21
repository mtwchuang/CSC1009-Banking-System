package View.Login;

import ModelView.MV_Global;

public class V_Logout {
    public void run() throws Exception{
        //Clear session data after logging out
        MV_Global.clearSession();

        System.out.println("\n===============================================================================");
        System.out.println("88888888888 888                        888                                     ");
        System.out.println("    888     888                        888                                     ");
        System.out.println("    888     88888b.   8888b.  88888b.  888  888      888  888  .d88b.  888  888");
        System.out.println("    888     888 \"88b     \"88b 888 \"88b 888 .88P      888  888 d88\"\"88b 888  888");
        System.out.println("    888     888  888 .d888888 888  888 888888K       888  888 888  888 888  888");
        System.out.println("    888     888  888 888  888 888  888 888 \"88b      Y88b 888 Y88..88P Y88b 888");
        System.out.println("    888     888  888 \"Y888888 888  888 888  888       \"Y88888  \"Y88P\"   \"Y88888");
        System.out.println("========================================================= 888 =================");
        System.out.println("                                                     Y8b d88P                  ");
        System.out.println("                                                      \"Y88P\"                   ");
        System.out.println("\nLogging out...");

        MV_Global.waitSuccess();
        MV_Global.waitSuccess();
    }
}
