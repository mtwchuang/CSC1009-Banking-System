package View;

import ModelView.Global;
import ModelView.UserAccount.MV_UserAccount;

public class V_Login 
{
    public void run() throws Exception
    {
        boolean validationFlag;
        short authenticationStatus, i;
        String inputUsername, inputPassword;

        while(true){
            validationFlag = true;

            //Clear screen
            System.out.print("\033[H\033[2J");

            //Print bank logo
            this.logo();
            
            //Intake username
            System.out.print("Username: ");
            inputUsername = Global.input.nextLine();

            //Intake password
            inputPassword = new String(System.console().readPassword("Password: "));
            System.out.println("");

            //Password validation; check if input is integer
            try
            {
                Integer.parseInt(inputPassword);
            }
            catch(Exception e)
            {
                validationFlag = false;
            }

            //Call out VL_checkAcct() from MV_UserAccount in layer ModelView to check against stored credentials
            if(validationFlag)
            {
                authenticationStatus = new MV_UserAccount().VLogin_checkAcc(inputUsername, inputPassword);
                switch(authenticationStatus)
                {
                    //Authentication success
                    case(0):
                    {
                        System.out.print("Login successful. Loading");
                        for(i = 0; i < 3; i++){
                            Global.wait(1);
                            System.out.print(".");
                        }

                        //PAGE NAVIGATION FROM HERE AFTER LOGGING IN
                        System.exit(0);

                        break;
                    }
                    //Invalid login credentials
                    default:
                    {
                        System.out.println("Invalid username or password.\n");
                        System.out.print("Login page resetting");
                        for(i = 0; i < 3; i++){
                            Global.wait(1);
                            System.out.print(".");
                        }
                        break;
                    }
                }
            }
            else{
                System.out.println("Invalid username or password.\n");
                System.out.print("Login page resetting");
                for(i = 0; i < 3; i++){
                    Global.wait(1);
                    System.out.print(".");
                }
            }
        }
    }

    //Print fancy bank logo
    private void logo(){
        System.out.println("WELCOME TO\n");
        System.out.println("=========================================================");
        System.out.println("::::::::::: :::::::::      :::     ::::    ::: :::    :::");
        System.out.println("    :+:     :+:    :+:   :+: :+:   :+:+:   :+: :+:   :+: ");
        System.out.println("    +:+     +:+    +:+  +:+   +:+  :+:+:+  +:+ +:+  +:+  ");
        System.out.println("    +#+     +#++:++#+  +#++:++#++: +#+ +:+ +#+ +#++:++   ");
        System.out.println("    +#+     +#+    +#+ +#+     +#+ +#+  +#+#+# +#+  +#+  ");
        System.out.println("    #+#     #+#    #+# #+#     #+# #+#   #+#+# #+#   #+# ");
        System.out.println("    ###     #########  ###     ### ###    #### ###    ###");
        System.out.println("=========================================================\n");

        System.out.println("Login to get started...\n");
    }
}